/**************************************************************************
 *
 * $RCSfile: WindowsFileParser.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:55 $
 *
 * $Log: WindowsFileParser.java,v $
 * Revision 1.1  2007/11/22 15:05:55  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/
package com.gxlu.interfacePlatform.foundation.ftp.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *  Represents a remote Windows file parser
 *
 *  @author      Bruce Blackshaw
 *  @version     $Revision: 1.1 $
 */
public class WindowsFileParser extends FTPFileParser {


    /**
     * Date formatter
     */
    private SimpleDateFormat formatter;
    
    /**
     * Directory field
     */
    private final static String DIR = "<DIR>";
    
    /**
     * Number of expected fields
     */
    private final static int MIN_EXPECTED_FIELD_COUNT = 4;

    /**
     * Constructor
     */
     public WindowsFileParser() {
         setLocale(Locale.getDefault());
     }
     
    /**
     * Set the locale for date parsing of listings
     * 
     * @param locale    locale to set
     */
    public void setLocale(Locale locale) {
        formatter = new SimpleDateFormat("MM-dd-yy hh:mma", locale);
    }    


    /**
     * Parse server supplied string. Should be in
     * form 
     * 
     * 05-17-03  02:47PM                70776 ftp.jar
     * 08-28-03  10:08PM       <DIR>          EDT SSLTest
     * 
     * @param raw   raw string to parse
     */
    public FTPFile parse(String raw) throws ParseException {
        String[] fields = split(raw);
        
        if (fields.length < MIN_EXPECTED_FIELD_COUNT)
            throw new ParseException("Unexpected number of fields: " + fields.length, 0);
        
        // first two fields are date time
        Date lastModified = formatter.parse(fields[0] + " " + fields[1]);
        
        // dir flag
        boolean isDir = false;
        long size = 0L;
        if (fields[2].equalsIgnoreCase(DIR))
            isDir = true;
        else {
            try {
                size = Long.parseLong(fields[2]);
            }
            catch (NumberFormatException ex) {
                throw new ParseException("Failed to parse size: " + fields[2], 0);
            }
        }
        
        // we've got to find the starting point of the name. We
        // do this by finding the pos of all the date/time fields, then
        // the name - to ensure we don't get tricked up by a date or dir the
        // same as the filename, for example
        int pos = 0;
        boolean ok = true;
        for (int i = 0; i < 3; i++) {
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
            String name = raw.substring(pos).trim();
            return new FTPFile(raw, name, size, isDir, lastModified); 
        }
        else {
            throw new ParseException("Failed to retrieve name: " + raw, 0);  
        }
    }
  
}
