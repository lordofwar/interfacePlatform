/**************************************************************************
 *
 * $RCSfile: VMSFileParser.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:55 $
 *
 * $Log: VMSFileParser.java,v $
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
 *  Represents a remote OpenVMS file parser. Thanks to Jason Schultz for contributing
 *  significantly to this class 
 */
public class VMSFileParser extends FTPFileParser {
    
    /**
     * Directory field
     */
    private final static String DIR = ".DIR";
    
    /**
     * Directory line
     */
    private final static String HDR = "Directory";
    
    /**
     * Total line
     */
    private final static String TOTAL = "Total";
    
    /**
     * Blocksize for calculating file sizes
     */
    private final int BLOCKSIZE = 512*1024;
    
    /**
     * Number of expected fields
     */
    private final static int MIN_EXPECTED_FIELD_COUNT = 4;
    
    /**
     * Date formatter
     */
    private SimpleDateFormat formatter1;
    
    /**
     * Date formatter
     */
    private SimpleDateFormat formatter2;
    
    /**
     * Constructor
     */
    public VMSFileParser() {
         setLocale(Locale.getDefault());
    }

    /**
     * Parse server supplied string
     *
     * OUTPUT: <begin>
     * 
     * Directory <dir>
     *  
     * <filename>
     *      used/allocated  dd-MMM-yyyy HH:mm:ss [unknown]      (PERMS)
     * <filename>
     *      used/allocated  dd-MMM-yyyy HH:mm:ss [unknown]      (PERMS)
     * ...
     * 
     * Total of <> files, <>/<> blocks
     *
     * @param raw   raw string to parse
     */
    public FTPFile parse(String raw) throws ParseException {
        String[] fields = split(raw);
        
        // skip blank lines
        if(fields.length <= 0)
        	return null;
        // skip line which lists Directory
        if (fields.length >= 2 && fields[0].compareTo(HDR) == 0)
        	return null;
        // skip line which lists Total
        if (fields.length > 0 && fields[0].compareTo(TOTAL) == 0)
        	return null;
        // probably the remainder of a listing on 2nd line
        if (fields.length < MIN_EXPECTED_FIELD_COUNT) 
            return null; 
        
        // first field is name
        String name = fields[0];
        
        // make sure it is the name (ends with ';<INT>')
        int semiPos = name.lastIndexOf(';');
        // check for ;
        if(semiPos <= 0) {
            throw new ParseException("File version number not found in name '" + name + "'", 0);
        }
        name = name.substring(0, semiPos);
        
        // check for version after ;
        String afterSemi = fields[0].substring(semiPos + 1);
		try{
			Integer.parseInt(afterSemi);
			// didn't throw exception yet, must be number
			// we don't use it currently but we might in future
		}
		catch(NumberFormatException ex) {
		    // don't worry about version number
		}
        
        // test is dir
        boolean isDir = false;
        if (semiPos < 0) {
        	semiPos = fields[0].length();
        }
        if( semiPos <= 4) {
        	// string to small to have a .DIR
        }
        else {
        	// look for .DIR
        	String tstExtnsn = fields[0].substring(semiPos-4, semiPos);
        	if(tstExtnsn.compareTo(DIR) == 0) {
        		isDir = true;
                name = name.substring(0, semiPos-4);
            }
        }
        
        // 2nd field is size USED/ALLOCATED format
        int slashPos = fields[1].indexOf('/');
        String sizeUsed = fields[1];
        if (slashPos > 0)
            sizeUsed = fields[1].substring(0, slashPos);
        long size = Long.parseLong(sizeUsed) * BLOCKSIZE;
        
        // 3 & 4 fields are date time
        Date lastModified = null;
        try {
            lastModified = formatter1.parse(fields[2] + " " + fields[3]);
        }
        catch (ParseException ex) {
            lastModified = formatter2.parse(fields[2] + " " + fields[3]);
        }
        
        // 5th field is [group,owner]
        String group = null;
        String owner = null;
        if (fields.length >= 5) {     
            if (fields[4].charAt(0) == '[' && fields[4].charAt(fields[4].length()-1) == ']') {
                int commaPos = fields[4].indexOf(',');
                if (commaPos < 0) {
                    group = owner = fields[4]; // just make them the same, e.g. SYSTEM
                }
                else {
	                group = fields[4].substring(1, commaPos);
	                owner = fields[4].substring(commaPos+1, fields[4].length()-1);
                }
            }
        }
        
        // 6th field is permissions e.g. (RWED,RWED,RE,)
        String permissions = null;
        if (fields.length >= 6) {     
            if (fields[5].charAt(0) == '(' && fields[5].charAt(fields[5].length()-1) == ')') {
                permissions = fields[5].substring(1, fields[5].length()-2);
            }
        }
        
        FTPFile file = new FTPFile(raw, name, size, isDir, lastModified); 
        file.setGroup(group);
        file.setOwner(owner);
        file.setPermissions(permissions);
        return file;        
    }

    public void setLocale(Locale locale) {
        formatter1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", locale);
        formatter2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm", locale);
    }
  
}
