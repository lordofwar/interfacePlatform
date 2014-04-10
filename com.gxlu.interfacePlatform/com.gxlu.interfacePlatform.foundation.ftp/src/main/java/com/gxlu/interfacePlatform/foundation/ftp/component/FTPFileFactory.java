/**************************************************************************
 *
 * $RCSfile: FTPFileFactory.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:53 $
 *
 * $Log: FTPFileFactory.java,v $
 * Revision 1.1  2007/11/22 15:05:53  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/
package com.gxlu.interfacePlatform.foundation.ftp.component;

import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *  Factory for creating FTPFile objects
 *
 */
public class FTPFileFactory {
    

    /**
     * Logging object
     */
	private Log log = LogFactory.getLog(getClass());

    /**
     * Windows server comparison string
     */
    final static String WINDOWS_STR = "WINDOWS";
                  
    /**
     * UNIX server comparison string
     */
    final static String UNIX_STR = "UNIX";
    
    /**
     * VMS server comparison string
     */
    final static String VMS_STR = "VMS";
        
    /**
     * SYST string
     */
    private String system;
    
    /**
     * Cached windows parser
     */
    private FTPFileParser windows = new WindowsFileParser();
    
    /**
     * Cached unix parser
     */
    private FTPFileParser unix = new UnixFileParser();
    
    /**
     * Cached vms parser
     */
    private FTPFileParser vms = new VMSFileParser();
    
    /**
     * Does the parsing work
     */
    private FTPFileParser parser = null;
    
    /**
     * True if using VMS parser
     */
    private boolean usingVMS = false;
    
    /**
     * Rotate parsers when a ParseException is thrown?
     */
    private boolean rotateParsers = true;
     
    /**
     * Constructor
     * 
     * @param system    SYST string
     */
    public FTPFileFactory(String system) throws FTPException {
        setParser(system);
    }
    
    /**
     * Constructor. User supplied parser. Note that parser
     * rotation (in case of a ParseException) is disabled if
     * a parser is explicitly supplied
     * 
     * @param parser   the parser to use
     */
    public FTPFileFactory(FTPFileParser parser) {
        this.parser = parser;
        rotateParsers = false;
    }   
    
    /**
     * Set the locale for date parsing of listings
     * 
     * @param locale    locale to set
     */
    public void setLocale(Locale locale) {
        windows.setLocale(locale);
        unix.setLocale(locale);
        vms.setLocale(locale);
        parser.setLocale(locale); // might be user supplied
    }
    
    /**
     * Set the remote server type
     * 
     * @param system    SYST string
     */
    private void setParser(String system) {
        this.system = system;
        if (system.toUpperCase().startsWith(WINDOWS_STR))
            parser = windows;
        else if (system.toUpperCase().startsWith(UNIX_STR))
            parser = unix;
        else if (system.toUpperCase().startsWith(VMS_STR)) {
            parser = vms;
            usingVMS = true;
        }
        else {
            parser = unix;
            log.warn("Unknown SYST '" + system + "' - defaulting to Unix parsing");
        }
    }
    
    
    /**
     * Parse an array of raw file information returned from the
     * FTP server
     * 
     * @param files     array of strings
     * @return array of FTPFile objects
     */
    public FTPFile[] parse(String[] files) throws ParseException {
               
        FTPFile[] temp = new FTPFile[files.length];
        
        // quick check if no files returned
        if (files.length == 0)
            return temp;
                
        int count = 0;
        boolean checkedUnix = false;
        boolean reparse = false;
        int reparseCount = 1;
        for (int i = 0; i < files.length; i++) {
            if (reparse) { // rotated parsers, try this line again
                i -= reparseCount;
                reparse = false;
                reparseCount = 1;
            }
            try {
                if (files[i] == null || files[i].trim().length() == 0)
                    continue;
                
                // swap to Unix if looks like Unix listing
                if (!checkedUnix && parser != unix && UnixFileParser.isUnix(files[i])) {
                    parser = unix;
                    checkedUnix = true;
                    log.info("Swapping Windows parser to Unix");
                }
                
                FTPFile file = null;
                if(usingVMS) {
                    // vms uses more than 1 line for some file listings. We must keep going
                	// thru till we've got everything
                	reparseCount = 1;
                	StringBuffer filename = new StringBuffer(files[i]);
                	while (i+1 < files.length && files[i+1].indexOf(';') < 0) {
                		filename.append(" ").append(files[i+1]);
                		i++;
                		reparseCount++;
                	}
                	file = parser.parse(filename.toString());
                }
                else {
                    file = parser.parse(files[i]);
                }
                // we skip null returns - these are duff lines we know about and don't
                // really want to throw an exception
                if (file != null) {
                    temp[count++] = file;
                }
            }
            catch (ParseException ex) {
                StringBuffer msg = new StringBuffer("Failed to parse line '");
                msg.append(files[i]).append("' (").append(ex.getMessage()).append(")");
                log.info(msg.toString());
                if (rotateParsers) { // first error, let's try swapping parsers
                    rotateParsers = false; // only do once
                    rotateParsers();
                    reparse = true;
                }
                else {// rethrow
                    throw new ParseException(msg.toString(), ex.getErrorOffset());
                }
            }
        }
        FTPFile[] result = new FTPFile[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }
    
    /**
     * Swap from one parser to the other. We can just check
     * object references
     */
    private void rotateParsers() {
        usingVMS = false;
        if (parser == unix) {
            parser = windows;
            log.info("Rotated parser to Windows");
        }
        else if (parser == windows){
            parser = unix;
            log.info("Rotated parser to Unix");
        }
    }

    /**
     * Get the SYST string
     * 
     * @return the system string.
     */
    public String getSystem() {
        return system;
    }


}
