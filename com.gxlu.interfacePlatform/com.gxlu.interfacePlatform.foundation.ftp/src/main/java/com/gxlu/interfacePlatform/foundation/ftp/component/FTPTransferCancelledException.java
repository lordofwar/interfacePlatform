/**************************************************************************
 *
 * $RCSfile: FTPTransferCancelledException.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:54 $
 *
 * $Log: FTPTransferCancelledException.java,v $
 * Revision 1.1  2007/11/22 15:05:54  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/
package com.gxlu.interfacePlatform.foundation.ftp.component;

/**
 *  Thrown when an FTP transfer has been cancelled
 *
 *
 */
 public class FTPTransferCancelledException extends FTPException {

    /**
     * Serial uid
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Message
     */
    private static final String MESSAGE = "Transfer was cancelled";

    /**
     *   Constructor. Delegates to super.
     */
    public FTPTransferCancelledException() {
        super(MESSAGE);
    }
}
