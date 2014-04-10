/**************************************************************************
 *
 * $RCSfile: DirectoryEmptyStrings.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:51 $
 *
 * $Log: DirectoryEmptyStrings.java,v $
 * Revision 1.1  2007/11/22 15:05:51  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/
package com.gxlu.interfacePlatform.foundation.ftp.component;

/**
 *  Contains fragments of server replies that indicate no files were
 *  found in a supplied directory.
 */
final public class DirectoryEmptyStrings extends ServerStrings {

    /**
     * Server string indicating no files found (NO_FILES)
     */
    final public static String NO_FILES = "NO FILES";
        
    /**
     * Server string indicating no files found (wu-ftpd) (NO_SUCH_FILE_OR_DIR)
     */
    final public static String NO_SUCH_FILE_OR_DIR = "NO SUCH FILE OR DIRECTORY";
    
    /**  
     * Server string indicating no files found (EMPTY_DIR)
     */
    final public static String EMPTY_DIR = "EMPTY";
    
    /**
     * Server string for OS/390 indicating no files found (NO_DATA_SETS_FOUND)
     */
    final public static String NO_DATA_SETS_FOUND = "NO DATA SETS FOUND";
    
    /**
     * Constructor. Adds the fragments to match on
     */
    public DirectoryEmptyStrings() {
        add(NO_FILES);
        add(NO_SUCH_FILE_OR_DIR);
        add(EMPTY_DIR);
        add(NO_DATA_SETS_FOUND);
    }

}
