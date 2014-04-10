/**************************************************************************
 *
 * $RCSfile: FTPReply.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:53 $
 *
 * $Log: FTPReply.java,v $
 * Revision 1.1  2007/11/22 15:05:53  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/

package com.gxlu.interfacePlatform.foundation.ftp.component;

/**
 *  Encapsulates the FTP server reply
 *
 */
public class FTPReply {


    /**
     *  Reply code
     */
    private String replyCode;

    /**
     *  Reply text
     */
    private String replyText;

    /**
     * Lines of data returned, e.g. FEAT
     */
    private String[] data;

    /**
     *  Constructor. Only to be constructed
     *  by this package, hence package access
     *
     *  @param  replyCode  the server's reply code
     *  @param  replyText  the server's reply text
     */
    FTPReply(String replyCode, String replyText) {
        this.replyCode = replyCode;
        this.replyText = replyText;
    }
    
    
    /**
     *  Constructor. Only to be constructed
     *  by this package, hence package access
     *
     *  @param  replyCode  the server's reply code
     *  @param  replyText  the server's full reply text
     *  @param  data       data lines contained in reply text
     */
    FTPReply(String replyCode, String replyText, String[] data) {
        this.replyCode = replyCode;
        this.replyText = replyText;
        this.data = data;
    }
    
    
    /**
     *  Constructor. Only to be constructed
     *  by this package, hence package access
     *
     *  @param  rawReply  the server's raw reply
     */
    FTPReply(String rawReply) {        
        // all reply codes are 3 chars long
        rawReply = rawReply.trim();
        replyCode = rawReply.substring(0, 3);
        if (rawReply.length() > 3)
            replyText = rawReply.substring(4);
        else
            replyText = "";
    }

    /**
     *  Getter for reply code
     *
     *  @return server's reply code
     */
    public String getReplyCode() {
        return replyCode;
    }

    /**
     *  Getter for reply text
     * 
     *  @return server's reply text
     */
    public String getReplyText() {
        return replyText;
    }
    
    /**
     * Getter for reply data lines
     * 
     * @return array of data lines returned (if any). Null
     *          if no data lines
     */
    public String[] getReplyData() {
        return data;
    }

}
