/**************************************************************************
 *
 * $RCSfile: FileNotFoundStrings.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:51 $
 *
 * $Log: FileNotFoundStrings.java,v $
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
 *
 */
final public class FileNotFoundStrings extends ServerStrings {

    /**
     * Server string indicating file not found
     */
    final public static String FILE_NOT_FOUND = "NOT FOUND";
    
    /**
     * Server string indicating file not found
     */
    final public static String NO_SUCH_FILE = "NO SUCH FILE";
    
    /**
     * Constructor. Adds the fragments to match on
     */
    public FileNotFoundStrings() {
        add(FILE_NOT_FOUND);
        add(NO_SUCH_FILE);
    }

}
