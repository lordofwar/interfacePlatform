/**************************************************************************
 *
 * $RCSfile: UnixFileParser.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:55 $
 *
 * $Log: UnixFileParser.java,v $
 * Revision 1.1  2007/11/22 15:05:55  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/
package com.gxlu.interfacePlatform.foundation.ftp.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *  Represents a remote Unix file parser
 *
 */
public class UnixFileParser extends FTPFileParser {


    /**
     * Symbolic link symbol
     */
    private final static String SYMLINK_ARROW = "->";
    
    /**
     * Indicates symbolic link
     */
    private final static char SYMLINK_CHAR = 'l';
        
    /**
     * These chars indicates ordinary files
     */
    private final static char[] FILE_CHARS = {'-', 'p'};
    
    /**
     * Indicates directory
     */
    private final static char DIRECTORY_CHAR = 'd';
    
    /**
     * Date formatter type 1
     */
    private SimpleDateFormat formatter1;
    
    /**
     * Date formatter type 2
     */
    private SimpleDateFormat formatter2;
    
    /**
     * Minimum number of expected fields
     */
    private final static int MIN_FIELD_COUNT = 8;
    
    /**
     * Constructor
     */
    public UnixFileParser() {
        setLocale(Locale.getDefault());
    }
    
    /**
     * Set the locale for date parsing of listings
     * 
     * @param locale    locale to set
     */
    public void setLocale(Locale locale) {
        formatter1 = new SimpleDateFormat("MMM-dd-yyyy", locale);
        formatter2 = new SimpleDateFormat("MMM-dd-yyyy-HH:mm", locale);
    }  
    
    /**
     * Is this a Unix format listing?
     * 
     * @param raw   raw listing line
     * @return true if Unix, false otherwise
     */
    public static boolean isUnix(String raw) {
        char ch = raw.charAt(0);
        if (ch == DIRECTORY_CHAR || ch == SYMLINK_CHAR)
            return true;
        for (int i = 0; i < FILE_CHARS.length; i++)
        	if (ch == FILE_CHARS[i])
        		return true;
        return false;
    }
    
    /**
     * Parse server supplied string, e.g.:
     * 
     * lrwxrwxrwx   1 wuftpd   wuftpd         14 Jul 22  2002 MIRRORS -> README-MIRRORS
     * -rw-r--r--   1 b173771  users         431 Mar 31 20:04 .htaccess
     * 
     * @param raw   raw string to parse
     */
    public FTPFile parse(String raw) throws ParseException {
        
        // test it is a valid line, e.g. "total 342522" is invalid
        if (!isUnix(raw))
            return null;
        
        String[] fields = split(raw);
         
        if (fields.length < MIN_FIELD_COUNT) {
            StringBuffer listing = new StringBuffer("Unexpected number of fields in listing '");
            listing.append(raw).append("' - expected minimum ").append(MIN_FIELD_COUNT). 
                    append(" fields but found ").append(fields.length).append(" fields");
            throw new ParseException(listing.toString(), 0);
        }
        
        // field pos
        int index = 0;
        
        // first field is perms
        char ch = raw.charAt(0);
        String permissions = fields[index++];
        ch = permissions.charAt(0);
        boolean isDir = false;
        boolean isLink = false;
        if (ch == DIRECTORY_CHAR)
            isDir = true;
        else if (ch == SYMLINK_CHAR)
            isLink = true;
        
        // some servers don't supply the link count
        int linkCount = 0;
        if (Character.isDigit(fields[index].charAt(0))) {
            try {
                linkCount = Integer.parseInt(fields[index++]);
            }
            catch (NumberFormatException ignore) {}
        }
        
        // owner and group
        String owner = fields[index++];
        String group = fields[index++];
        
        // size
        long size = 0L;
        String sizeStr = fields[index];
        // some listings don't have group - make group -> size in
        // this case, and use the sizeStr for the start of the date
        if (!Character.isDigit(sizeStr.charAt(0)) && Character.isDigit(group.charAt(0))) {
            sizeStr = group;  
            group = "";
        }
        else {
            index++; 
        }
        try {
            size = Long.parseLong(sizeStr);
        }
        catch (NumberFormatException ex) {
            throw new ParseException("Failed to parse size: " + sizeStr, 0);
        }
        
        // next 3 are the date time
        int dateTimePos = index;
        Date lastModified = null;
        StringBuffer stamp = new StringBuffer(fields[index++]);
        stamp.append('-').append(fields[index++]).append('-');
        
        String field = fields[index++];
        if (field.indexOf(':') < 0) {
            stamp.append(field); // year
            lastModified = formatter1.parse(stamp.toString());
        }
        else { // add the year ourselves as not present
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            stamp.append(year).append('-').append(field);
            lastModified = formatter2.parse(stamp.toString());
            
            // can't be in the future - must be the previous year
            if (lastModified.after(cal.getTime())) {
                cal.setTime(lastModified);
                cal.add(Calendar.YEAR, -1);
                lastModified = cal.getTime();
            }
        }
            
        // name of file or dir. Extract symlink if possible
        String name = null;
        String linkedname = null;
        
        // we've got to find the starting point of the name. We
        // do this by finding the pos of all the date/time fields, then
        // the name - to ensure we don't get tricked up by a userid the
        // same as the filename,for example
        int pos = 0;
        boolean ok = true;
        for (int i = dateTimePos; i < dateTimePos+3; i++) {
            pos = raw.indexOf(fields[i], pos);
            if (pos < 0) {
                ok = false;
                break;
            }
            else { // move on the length of the field
                pos += fields[i].length();
            }
        }
        if (ok) {
            String remainder = raw.substring(pos).trim();
            if (!isLink) 
                name = remainder;
            else { // symlink, try to extract it
                pos = remainder.indexOf(SYMLINK_ARROW);
                if (pos <= 0) { // couldn't find symlink, give up & just assign as name
                    name = remainder;
                }
                else { 
                    int len = SYMLINK_ARROW.length();
                    name = remainder.substring(0, pos).trim();
                    if (pos+len < remainder.length())
                        linkedname = remainder.substring(pos+len);
                }
            }
        }
        else {
            throw new ParseException("Failed to retrieve name: " + raw, 0);  
        }
        
        FTPFile file = new FTPFile(raw, name, size, isDir, lastModified);
        file.setGroup(group);
        file.setOwner(owner);
        file.setLink(isLink);
        file.setLinkCount(linkCount);
        file.setLinkedName(linkedname);
        file.setPermissions(permissions);
        return file;
    }
}
