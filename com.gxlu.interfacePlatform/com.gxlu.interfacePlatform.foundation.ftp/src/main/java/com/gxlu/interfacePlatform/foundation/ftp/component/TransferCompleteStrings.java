/**************************************************************************
 *
 * $RCSfile: TransferCompleteStrings.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:55 $
 *
 * $Log: TransferCompleteStrings.java,v $
 * Revision 1.1  2007/11/22 15:05:55  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/
package com.gxlu.interfacePlatform.foundation.ftp.component;


final public class TransferCompleteStrings extends ServerStrings {

    /**
     * Server string transfer complete (proFTPD/TLS)
     */
    final private static String TRANSFER_COMPLETE = "TRANSFER COMPLETE";
    
    /**
     * Constructor. Adds the fragments to match on
     */
    public TransferCompleteStrings() {
        add(TRANSFER_COMPLETE);
    }

}
