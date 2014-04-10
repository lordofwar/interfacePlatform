/**************************************************************************
 *
 * $RCSfile: FTPServerInterface.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:54 $
 *
 * $Log: FTPServerInterface.java,v $
 * Revision 1.1  2007/11/22 15:05:54  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/
package com.gxlu.interfacePlatform.foundation.ftp.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;

/**
 * Defines operations in common with a number of FTP implementations.
 * 
 * @author     Hans Andersen
 * @version    $Revision: 1.1 $
 */
public interface FTPServerInterface {
   	
    /**
     * Get the identifying string for this instance
     * 
     * @return identifying string
     */
    public String getId();
    
    /**
     * Set the identifying string for this instance
     * 
     * @param id    identifying string
     */
    public void setId(String id);
    
    /**
     * Returns the IP address or name of the remote host.
     * 
     * @return Returns the remote host.
     */
    public String getRemoteHost();

    /**
     * Set the IP address or name of the remote host
     * 
     * This may only be done if the client is not already connected to the server.
     * 
     * @param remoteHost The IP address or name of the remote host
     * @throws FTPException Thrown if the client is already connected to the server.
     */
    public void setRemoteHost(String remoteHost) throws IOException, FTPException;
    
    /**
     * Returns the port being connected to on the remote server. 
     * 
     * @return Returns the port being connected to on the remote server. 
     */
    public int getRemotePort();
    
    /** 
     * Set the port to connect to on the remote server. Can only do this if
     * not already connected.
     * 
     * @param remotePort The port to use. 
     * @throws FTPException Thrown if the client is already connected to the server.
     */
    public void setRemotePort(int remotePort) throws FTPException;

    /**
     *   Get the TCP timeout on the underlying socket(s).
     * 
     *   A value of 0 (the default) means that there
     *   are no timeouts.
     *  
     *  @return timeout that is used, in milliseconds
     */
    public int getTimeout();
    
	/**
	 *   Set the TCP timeout on the underlying socket(s).
	 *
	 *   Timeouts should be set before connections are made.
	 *   If a timeout is set, then any operation which
	 *   takes longer than the timeout value will be
	 *   killed with a java.io.InterruptedException.
	 *   The default is 0 meaning that the connection
	 *   never times out.  
	 *
	 *   @param millis The length of the timeout, in milliseconds
	 */
	public void setTimeout(int millis) throws IOException, FTPException;

	/**
	 *  Set a progress monitor for callbacks. The bytes transferred in
	 *  between callbacks is only indicative. In many cases, the data is
	 *  read in chunks, and if the interval is set to be smaller than the
	 *  chunk size, the callback will occur after after chunk transfer rather
	 *  than the interval. Depending on the implementation, the chunk size can
     *  be as large as 64K.
	 *
	 *  @param  monitor   the monitor object
	 *  @param  interval  bytes transferred in between callbacks
	 */
	public void setProgressMonitor(FTPProgressMonitor monitor,
			long interval);

	/**
	 *  Set a progress monitor for callbacks. Uses default callback
	 *  interval
	 *
	 *  @param  monitor   the monitor object
	 */
	public void setProgressMonitor(FTPProgressMonitor monitor);

	/**
	 *  Get the bytes transferred between each callback on the
	 *  progress monitor
	 * 
	 * @return long     bytes to be transferred before a callback
	 */
	public long getMonitorInterval();
    
    /**
     * Set autodetect of filetypes on or off. If on, the transfer mode is
     * switched from ASCII to binary and vice versa depending on the extension
     * of the file. After the transfer, the mode is always returned to what it
     * was before the transfer was performed. The default is off.
     * 
     * If the filetype is unknown, the transfer mode is unchanged
     * 
     * @param detectTransferMode    true if detecting transfer mode, false if not
     */
    public void setDetectTransferMode(boolean detectTransferMode);
    
    /**
     * Get the detect transfer mode
     * 
     * @return true if we are detecting binary and ASCII transfers from the file type
     */
    public boolean getDetectTransferMode();   

    /**
     * Connects to the server at the address and port number defined
     * in the constructor.
     * 
     * @throws IOException Thrown if there is a TCP/IP-related error.
     * @throws FTPException Thrown if there is an error related to the FTP protocol. 
     */
    public void connect() throws IOException, FTPException;

	/**
	 *  Get the size of a remote file. This is not a standard FTP command, it
	 *  is defined in "Extensions to FTP", a draft RFC 
	 *  (draft-ietf-ftpext-mlst-16.txt)
	 *
	 *  @param  remoteFile  name or path of remote file in current directory
	 *  @return size of file in bytes      
	 */
	public long size(String remoteFile) throws IOException,
	    FTPException;
    
    /**
     * Does the named file exist in the current server directory?
     * 
     * @param remoteFile        name of remote file
     * @return true if exists, false otherwise
     * @throws IOException
     * @throws FTPException
     */
    public boolean exists(String remoteFile) throws IOException,
        FTPException;
    
    /**
     *  Get the current transfer type
     *
     *  @return  the current type of the transfer,
     *           i.e. BINARY or ASCII
     */
    public FTPTransferType getType();
    
    /**
     *  Set the transfer type
     *
     *  @param  type  the transfer type to
     *                set the server to
     */
    public void setType(FTPTransferType type)
        throws IOException, FTPException; 
    
    /**
     * Make the next file transfer (put or get) resume. For puts(), the
     * bytes already transferred are skipped over, while for gets(), if 
     * writing to a file, it is opened in append mode, and only the bytes
     * required are transferred.
     * 
     * Currently resume is only supported for BINARY transfers (which is
     * generally what it is most useful for).
     * 
     * @throws FTPException
     */
    public void resume() throws FTPException;
    
    /**
     * Cancel the resume. Use this method if something goes wrong
     * and the server is left in an inconsistent state
     * 
     * @throws IOException
     * @throws FTPException
     */
    public void cancelResume() throws IOException, FTPException;
    
    /**
     *  Cancels the current transfer. Generally called from a separate
     *  thread. Note that this may leave partially written files on the
     *  server or on local disk, and should not be used unless absolutely
     *  necessary. After the transfer is cancelled the connection may be in
     *  an inconsistent state, therefore it is best to quit and reconnect.
     *  It may cause exceptions to be thrown depending on the underlying protocol
     *  being used
     */
    public void cancelTransfer();

	/**
	 *  Put a local file onto the FTP server. It
	 *  is placed in the current directory. If a remote file name is supplied,
     *  it is stored as that name on the server. If null is supplied, the server
     *  will generate a unique filename (via STOU) if it supports this option.
	 *
	 *  @param  localPath   path of the local file
	 *  @param  remoteFile  name of remote file in
	 *                      current directory, or null if 
     *                       a unique filename is to be generated by the server
     *  @return The name of the remote file - normally the name supplied, or else
     *           the unique name generated by the server.
	 */
	public String put(String localPath, String remoteFile)
			throws IOException, FTPException;

	/**
	 *  Put a stream of data onto the FTP server. It
	 *  is placed in the current directory. If a remote file name is supplied,
     *  it is stored as that name on the server. If null is supplied, the server
     *  will generate a unique filename (via STOU) if it supports this option.
	 *
	 *  @param  srcStream   input stream of data to put
	 *  @param  remoteFile  name of remote file in
	 *                      current directory, or null if 
     *                       a unique filename is to be generated by the server
     *  @return The name of the remote file - normally the name supplied, or else
     *           the unique name generated by the server.
	 */
	public String put(InputStream srcStream, String remoteFile)
			throws IOException, FTPException;
    
    /**
     *  Put a stream of data onto the FTP server. It
     *  is placed in the current directory. If a remote file name is supplied,
     *  it is stored as that name on the server. If null is supplied, the server
     *  will generate a unique filename (via STOU) if it supports this option.
     *  Allows appending if current file exists. 
     *
     *  @param  srcStream   input stream of data to put
     *  @param  remoteFile  name of remote file in
     *                      current directory, or null if 
     *                       a unique filename is to be generated by the server
     *  @param  append      true if appending, false otherwise
     *  @return The name of the remote file - normally the name supplied, or else
     *           the unique name generated by the server.
     */
    public String put(InputStream srcStream, String remoteFile,
                    boolean append)
        throws IOException, FTPException;

	/**
	 *  Put data onto the FTP server. It
	 *  is placed in the current directory. If a remote file name is supplied,
     *  it is stored as that name on the server. If null is supplied, the server
     *  will generate a unique filename (via STOU) if it supports this option.
	 *
	 *  @param  bytes        array of bytes
	 *  @param  remoteFile  name of remote file in
	 *                      current directory, or null if 
     *                       a unique filename is to be generated by the server
     *  @return The name of the remote file - normally the name supplied, or else
     *           the unique name generated by the server.
	 */
	public String put(byte[] bytes, String remoteFile)
			throws IOException, FTPException;
    
    /**
     *  Put data onto the FTP server. It
     *  is placed in the current directory. If a remote file name is supplied,
     *  it is stored as that name on the server. If null is supplied, the server
     *  will generate a unique filename (via STOU) if it supports this option.
     *  Allows appending if current file exists.
     *
     *  @param  bytes        array of bytes
     *  @param  remoteFile  name of remote file in
     *                      current directory, or null if 
     *                       a unique filename is to be generated by the server
     *  @param  append      true if appending, false otherwise
     *  @return The name of the remote file - normally the name supplied, or else
     *           the unique name generated by the server.
     */
    public String put(byte[] bytes, String remoteFile, boolean append)
        throws IOException, FTPException;   

    /**
     *  Put a local file onto the FTP server. It
     *  is placed in the current directory. If a remote file name is supplied,
     *  it is stored as that name on the server. If null is supplied, the server
     *  will generate a unique filename (via STOU) if it supports this option. 
     *  Allows appending if current file exists.
     *
     *  @param  localPath   path of the local file
     *  @param  remoteFile  name of remote file in current directory, or null if 
     *                       a unique filename is to be generated by the server
     *  @param  append      true if appending, false otherwise
     *  @return The name of the remote file - normally the name supplied, or else
     *           the unique name generated by the server.
     */
    public String put(String localPath, String remoteFile,
                    boolean append)
        throws IOException, FTPException;
    
	/**
	 *  Get data from the FTP server. Uses the currently
	 *  set transfer mode.
	 *
	 *  @param  localPath   local file to put data in
	 *  @param  remoteFile  name of remote file in
	 *                      current directory
	 */
	public void get(String localPath, String remoteFile)
			throws IOException, FTPException;

	/**
	 *  Get data from the FTP server. Uses the currently
	 *  set transfer mode.
	 *
	 *  @param  destStream  data stream to write data to
	 *  @param  remoteFile  name of remote file in
	 *                      current directory
	 */
	public void get(OutputStream destStream, String remoteFile)
			throws IOException, FTPException;

	/**
	 *  Get data from the FTP server. Transfers in
	 *  whatever mode we are in. Retrieve as a byte array. Note
	 *  that we may experience memory limitations as the
	 *  entire file must be held in memory at one time.
	 *
	 *  @param  remoteFile  name of remote file in
	 *                      current directory
	 */
	public byte[] get(String remoteFile) throws IOException,
			FTPException;

	/**
	 *  List a directory's contents as an array of FTPFile objects.
	 *  Should work for Windows and most Unix FTP servers - let us know
	 *  about unusual formats (http://www.enterprisedt.com/forums/index.php).
     *  If accurate timestamps are required (i.e. to the second), it is 
     *  generally better to use @see #modtime(String).
	 *
	 *  @param   dirname  name of directory (some servers permit a filemask)
	 *  @return  an array of FTPFile objects
	 */
	public FTPFile[] dirDetails(String dirname) throws IOException,
			FTPException, ParseException;

	/**
	 *  List current directory's contents as an array of strings of
	 *  filenames.
	 *
	 *  @return  an array of current directory listing strings
	 */
	public String[] dir() throws IOException, FTPException;

	/**
	 *  List a directory's contents as an array of strings of filenames.
	 *
	 *  @param   dirname  name of directory OR filemask
	 *  @return  an array of directory listing strings
	 */
	public String[] dir(String dirname) throws IOException,
			FTPException;

	/**
	 *  List a directory's contents as an array of strings. A detailed
	 *  listing is available, otherwise just filenames are provided.
	 *  The detailed listing varies in details depending on OS and
	 *  FTP server. Note that a full listing can be used on a file
	 *  name to obtain information about a file
	 *
	 *  @param  dirname  name of directory OR filemask
	 *  @param  full     true if detailed listing required
	 *                   false otherwise
	 *  @return  an array of directory listing strings
	 */
	public String[] dir(String dirname, boolean full)
			throws IOException, FTPException;

	/**
	 *  Delete the specified remote file
	 *
	 *  @param  remoteFile  name of remote file to
	 *                      delete
	 */
	public void delete(String remoteFile) throws IOException,
			FTPException;

	/**
	 *  Rename a file or directory
	 *
	 * @param from  name of file or directory to rename
	 * @param to    intended name
	 */
	public void rename(String from, String to) throws IOException,
			FTPException;

	/**
	 *  Delete the specified remote working directory
	 *
	 *  @param  dir  name of remote directory to
	 *               delete
	 */
	public void rmdir(String dir) throws IOException, FTPException;

	/**
	 *  Create the specified remote working directory
	 *
	 *  @param  dir  name of remote directory to
	 *               create
	 */
	public void mkdir(String dir) throws IOException, FTPException;

	/**
	 *  Change the remote working directory to
	 *  that supplied
	 *
	 *  @param  dir  name of remote directory to
	 *               change to
	 */
	public void chdir(String dir) throws IOException, FTPException;
    
    /**
     *  Change the remote working directory to
     *  the parent directory
     */
    public void cdup() throws IOException, FTPException;

	/**
	 *  Get modification time for a remote file. For accurate
     *  modification times (e.g. to the second) this method is to
     *  be preferred over @see #dirDetails(java.lang.String) which
     *  parses a listing returned by the server. The timezone is GMT.
	 *
	 *  @param    remoteFile   name of remote file
	 *  @return   modification time of file as a date
	 */
	public Date modtime(String remoteFile) throws IOException,
			FTPException;

	/**
	 *  Get the current remote working directory
	 *
	 *  @return   the current working directory
	 */
	public String pwd() throws IOException, FTPException;

	/**
	 *  Quit the FTP session
	 *
	 */
	public void quit() throws IOException, FTPException;
    
    /**
     *  Quit the FTP session immediately. If a transfer is underway
     *  it will be terminated.
     *
     */
    //public void quitImmediately() throws IOException, FTPException; 
    
}