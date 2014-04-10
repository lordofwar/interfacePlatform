/**************************************************************************
 *
 * $RCSfile: FTPProgressMonitor.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:53 $
 *
 * $Log: FTPProgressMonitor.java,v $
 * Revision 1.1  2007/11/22 15:05:53  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/
package com.gxlu.interfacePlatform.foundation.ftp.component;

/**
 *  Allows the reporting of progress of the
 *  transfer of data
 *
 */
public interface FTPProgressMonitor {

    /**
     * Report the number of bytes transferred so far. This may
     * not be entirely accurate for transferring text files in ASCII
     * mode, as new line representations can be represented differently
     * on different platforms.
     * 
     * @param count  count of bytes transferred
     */
    public void bytesTransferred(long count);
}
