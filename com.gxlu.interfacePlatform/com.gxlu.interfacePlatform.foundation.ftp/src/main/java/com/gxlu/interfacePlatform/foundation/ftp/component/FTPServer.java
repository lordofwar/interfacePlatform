/**************************************************************************
 *
 * $RCSfile: FTPServer.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:53 $
 *
 * $Log: FTPServer.java,v $
 * Revision 1.1  2007/11/22 15:05:53  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/

package com.gxlu.interfacePlatform.foundation.ftp.component;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *  Supports client-side FTP. Most common
 *  FTP operations are present in this class.
 */
public class FTPServer implements FTPServerInterface {

    
    /**
     * Default byte interval for transfer monitor
     */
    final private static int DEFAULT_MONITOR_INTERVAL = 65535;
    
    /**
     * Default transfer buffer size
     */
    final private static int DEFAULT_BUFFER_SIZE = 16384;
    
    /**
     * Maximum port number
     */
    final private static int MAX_PORT = 65535;
    
    /**
     * Short value for a timeout
     */
    final private static int SHORT_TIMEOUT = 500;
    
    /**
     * Default encoding used for control data
     */
    final public static String DEFAULT_ENCODING = "US-ASCII";
    
    /**
     * SOCKS port property name
     */
    final private static String SOCKS_PORT = "socksProxyPort";

    /**
     * SOCKS host property name
     */
    final private static String SOCKS_HOST = "socksProxyHost";
    
    /**
     * Line separator
     */
    final private static byte[] LINE_SEPARATOR = System.getProperty("line.separator").getBytes();
    
    /**
     * Used for ASCII translation
     */
    final private static byte CARRIAGE_RETURN = 13;
    
    /**
     * Used for ASCII translation
     */
    final private static byte LINE_FEED = 10;
                
    /**
     * Marker in reply for STOU reply with filename
     */
    final private static String STOU_FILENAME_MARKER = "FILE:";
       
    /**
     * Logging object
     */
    private Log log = LogFactory.getLog(getClass());
    
    /**
     *  Format to interpret MTDM timestamp
     */
    private SimpleDateFormat tsFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    
    /**
     *  Socket responsible for controlling
     *  the connection
     */
	protected FTPControlSocket control = null;

    /**
     *  Socket responsible for transferring
     *  the data
     */
    protected FTPDataSocket data = null;
    
    /**
     *  Socket timeout for both data and control. In
     *  milliseconds
     */
    protected int timeout = 0;
    
    /**
     * Interval in seconds in between server wakeups. O is
     * not enabled
     */
    protected int serverWakeupInterval = 0;
    
    /**
     * Address of the remote server.
     */
    protected InetAddress remoteAddr;
    
    /**
     * Name/IP of remote host
     */
    protected String remoteHost;
    
    /**
     * Id of instance
     */
    protected String id;
    
    /**
     * Control port number.
     */
    protected int controlPort = FTPControlSocket.CONTROL_PORT;
    
    /**
     * If true, uses the original host IP if an internal IP address
     * is returned by the server in PASV mode
     */
    private boolean autoPassiveIPSubstitution = false;
    
    /**
     * Encoding used on control socket
     */
    protected String controlEncoding = DEFAULT_ENCODING;
      
    /**
     * Use strict return codes if true
     */
    private boolean strictReturnCodes = true;
    
    /**
     * Matcher for directory empty
     */
    protected DirectoryEmptyStrings dirEmptyStrings = new DirectoryEmptyStrings();
    
    /**
     * Matcher for transfer complete
     */
    protected TransferCompleteStrings transferCompleteStrings = new TransferCompleteStrings();
    
    /**
     * Matcher for permission denied
     */
    protected FileNotFoundStrings fileNotFoundStrings = new FileNotFoundStrings();
    /**
     *  Can be used to cancel a transfer
     */
    private boolean cancelTransfer = false;
    
    /**
     * If true, a file transfer is being resumed
     */
    private boolean resume = false;
    
    /**
     * MDTM supported flag
     */
    private boolean mdtmSupported = true;
    
    /**
     * SIZE supported flag
     */
    private boolean sizeSupported = true;
    
    /**
     * Resume byte marker point
     */
    private long resumeMarker = 0;
    
    /**
     * If true, filetypes are autodetected and transfer mode changed to binary/ASCII as 
     * required
     */
    private boolean detectTransferMode = false;
    
    /**
     * Bytes transferred in between monitor callbacks
     */
    private long monitorInterval = DEFAULT_MONITOR_INTERVAL;
    
    /**
     * Size of transfer buffers
     */
    private int transferBufferSize = DEFAULT_BUFFER_SIZE;
    
    /**
     * Parses LIST output
     */
    private FTPFileFactory fileFactory = null;
    
    /**
     * Locale for date parsing
     */
    private Locale listingLocale = Locale.getDefault();
    
    /**
     * Parses the MLSD and MLST formats
     */
    private MLSXEntryParser mlsxParser = new MLSXEntryParser();
    
    /**
     *  Progress monitor
     */
    private FTPProgressMonitor monitor = null;  
    
    /**
     * Message listener
     */
    protected FTPMessageListener messageListener = null;

    /**
     *  Record of the transfer type - make the default ASCII
     */
    private FTPTransferType transferType = FTPTransferType.ASCII;

    /**
     *  Record of the connect mode - make the default PASV (as this was
     *  the original mode supported)
     */
    private FTPConnectMode connectMode = FTPConnectMode.PASV;

    /**
     *  Holds the last valid reply from the server on the control socket
     */
	protected FTPReply lastValidReply;    
    
    /**
     *  Instance initializer. Sets formatter to GMT.
     */
    {
        tsFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }  

    
    /**
     *  Default constructor should now always be used together with setter methods
     *  in preference to other constructors (now deprecated). The {@link #connect()}
     *  method is used to perform the actual connection to the remote host - but only
     *  for this constructor. Deprecated constructors connect in the constructor and
     *  connect() is not required (and cannot be called).
     */
    public FTPServer() 
    {

    }
    
    
    /**
     * Connects to the server at the address and port number defined
     * in the constructor. Must be performed <b>before</b> login() or user() is
     * called.
     * 
     * @throws IOException Thrown if there is a TCP/IP-related error.
     * @throws FTPException Thrown if there is an error related to the FTP protocol. 
     */
    public void connect() throws IOException, FTPException {

        checkConnection(false);
       
//        log.debug("Connecting to " + remoteAddr + ":" + controlPort);
        
        initialize(new FTPControlSocket(remoteAddr, controlPort, timeout, 
                                         controlEncoding, messageListener));
    }
    
    /**
     * Is this client connected?
     * 
     * @return true if connected, false otherwise
     */
    public boolean connected() {
        return control != null;
    }
    
    /**
     * Checks if the client has connected to the server and throws an exception if it hasn't.
     * This is only intended to be used by subclasses
     * 
     * @throws FTPException Thrown if the client has not connected to the server.
     */
    protected void checkConnection(boolean shouldBeConnected) throws FTPException {
    	if (shouldBeConnected && !connected())
    		throw new FTPException("The FTP client has not yet connected to the server.  "
    				+ "The requested action cannot be performed until after a connection has been established.");
    	else if (!shouldBeConnected && connected())
    		throw new FTPException("The FTP client has already been connected to the server.  "
    				+"The requested action must be performed before a connection is established.");
    }
    	
    /**
     * Set the control socket explicitly
     * 
     * @param control   control socket reference
     */
	protected void initialize(FTPControlSocket control) throws IOException {
		this.control = control;
        control.setMessageListener(messageListener);
        control.setStrictReturnCodes(strictReturnCodes);
        control.setTimeout(timeout);
        control.setAutoPassiveIPSubstitution(autoPassiveIPSubstitution);
	}
    
 
    
    /**
     * Get the identifying string for this instance
     */
    public String getId() {
        return id;
    }

    /**
     * Set the identifying string for this instance
     * 
     * @param id    identifying string
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Set strict checking of FTP return codes. If strict 
     * checking is on (the default) code must exactly match the expected 
     * code. If strict checking is off, only the first digit must match.
     * 
     * @param strict    true for strict checking, false for loose checking
     */
    public void setStrictReturnCodes(boolean strict) {
        this.strictReturnCodes = strict;
        if (control != null)
            control.setStrictReturnCodes(strict);
    }
    
    /**
     * Determine if strict checking of return codes is switched on. If it is 
     * (the default), all return codes must exactly match the expected code.  
     * If strict checking is off, only the first digit must match.
     * 
     * @return  true if strict return code checking, false if non-strict.
     */
    public boolean isStrictReturnCodes() {
        return strictReturnCodes;
    }
    
    /* (non-Javadoc)
     * @see com.enterprisedt.net.ftp.FTPClientInterface#setDetectTransferMode(boolean)
     */
    public void setDetectTransferMode(boolean detectTransferMode) {
        this.detectTransferMode = detectTransferMode;
    }

    public boolean getDetectTransferMode() {
         return detectTransferMode;
    }
    
    /**
     * Switch the transfer mode if requested and if necessary
     * 
     * @param filename      filename of file to be transferred
     */
    private void chooseTransferMode(String filename) {
        if (filename == null) {
            log.warn("Cannot choose transfer mode as filename not supplied");
            return;
        }
            
        if (detectTransferMode) {
            if (FileTypes.ASCII.matches(filename) && 
                transferType.equals(FTPTransferType.BINARY)) {
                transferType = FTPTransferType.ASCII;
                log.debug("Autodetect on - changed transfer type to ASCII");
            }
            else if (FileTypes.BINARY.matches(filename) && 
                      transferType.equals(FTPTransferType.ASCII)) {
                transferType = FTPTransferType.BINARY;
                log.debug("Autodetect on - changed transfer type to binary");
            }
        }
    }

    /**
     *   Set the SO_TIMEOUT in milliseconds on the underlying socket.
     *   If set this means the socket will block in a read operation
     *   only for this length of time - useful if the FTP sever has 
     *   hung, for example. The default is 0, which is an infinite timeout. 
     *
     *   Note that for JREs 1.4+, the timeout is also used when first 
     *   connecting to the remote host. 
     *
     *   @param millis The length of the timeout, in milliseconds
     */
    public void setTimeout(int millis)
        throws IOException {

        this.timeout = millis;
        if (control != null)
            control.setTimeout(millis);
    }
    
 
    /**
     *  Get the TCP timeout 
     *  
     *  @return timeout that is used, in milliseconds
     */
    public int getTimeout() {
        return timeout;
    }
    
    /**
     * Returns the control-port being connected to on the remote server. 
     * 
     * Note that this method replaces {@link #getControlPort()}.
     * 
     * @return Returns the port being connected to on the remote server. 
     */
    public int getRemotePort() {
        return controlPort;
    }
    
    /** 
     * Set the control to connect to on the remote server. Can only do this if
     * not already connected.
     * 
     * Note that this method replaces {@link #setControlPort(int)}.
     * 
     * @param remotePort The port to use. 
     * @throws FTPException Thrown if the client is already connected to the server.
     */
    public void setRemotePort(int remotePort) throws FTPException {
        checkConnection(false);
        this.controlPort = remotePort;
    }

    
    /**
     * @return Returns the remoteAddr.
     */
    public InetAddress getRemoteAddr() {
        return remoteAddr;
    }
    
    /**
     * Set the remote address
     * 
     * @param remoteAddr The remoteAddr to set.
     * @throws FTPException
     */
    public void setRemoteAddr(InetAddress remoteAddr) throws FTPException {
        checkConnection(false);
        this.remoteAddr = remoteAddr;
        this.remoteHost = remoteAddr.getHostName();
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) throws IOException, FTPException {
        checkConnection(false);
        this.remoteHost = remoteHost;
        this.remoteAddr = InetAddress.getByName(remoteHost);
    }
    
    /**
     * Is automatic substitution of the remote host IP set to
     * be on for passive mode connections?
     * 
     * @return true if set on, false otherwise
     */
    public boolean isAutoPassiveIPSubstitution() {
        return autoPassiveIPSubstitution;
    }

    /**
     * Set automatic substitution of the remote host IP on if
     * in passive mode
     * 
     * @param autoPassiveIPSubstitution true if set to on, false otherwise
     */
    public void setAutoPassiveIPSubstitution(boolean autoPassiveIPSubstitution) {
        this.autoPassiveIPSubstitution = autoPassiveIPSubstitution;
        if (control != null)
            control.setAutoPassiveIPSubstitution(autoPassiveIPSubstitution);
    }
    
    /**
     * Get server wakeup interval in seconds. A value of 0
     * means it is disabled (the default).
     * 
     * @return interval in seconds
     */
    public int getServerWakeupInterval() {
        return serverWakeupInterval;
    }
    
    /**
     * Set server wakeup interval in seconds. A value of 0 
     * means it is disabled (the default). This may hang or confuse 
     * the FTP server - use with caution.
     * 
     * @param interval  interval in seconds
     */
    public void setServerWakeupInterval(int interval) {
        this.serverWakeupInterval = interval;
    }
    
    
    /**
     * Get the encoding used for the control connection
     * 
     * @return Returns the current controlEncoding.
     */
    public String getControlEncoding() {
        return controlEncoding;
    }
    
    /**
     * Set the control socket's encoding. Can only do this if
     * not connected
     * 
     * @param controlEncoding The controlEncoding to set, which is the name of a Charset
     * @see java.nio.charset.Charset
     * @throws FTPException
     */
    public void setControlEncoding(String controlEncoding) throws FTPException {
        checkConnection(false);
        this.controlEncoding = controlEncoding;
    }
    /**
     * @return Returns the messageListener.
     */
    public FTPMessageListener getMessageListener() {
        return messageListener;
    }
    
    /**
     * Set a listener that handles all FTP messages
     * 
     * @param listener  message listener
     */
    public void setMessageListener(FTPMessageListener listener) {
        this.messageListener = listener;
        if (control != null)
           control.setMessageListener(listener);
    }
    
    /**
     *  Set the connect mode
     *
     *  @param  mode  ACTIVE or PASV mode
     */
    public void setConnectMode(FTPConnectMode mode) {
        connectMode = mode;
    }
    
    /**
     * @return Returns the connectMode.
     */
    public FTPConnectMode getConnectMode() {
        return connectMode;
    }

    public void setProgressMonitor(FTPProgressMonitor monitor, long interval) {
        this.monitor = monitor;
        this.monitorInterval = interval;
    }    

    public void setProgressMonitor(FTPProgressMonitor monitor) {
        this.monitor = monitor;
    }    

    public long getMonitorInterval() {
        return monitorInterval;
    }
    
    /**
     * Set the size of the buffers used in writing to and reading from
     * the data sockets
     * 
     * @param size  new size of buffer in bytes
     */
    public void setTransferBufferSize(int size) {
        transferBufferSize = size;
    }
    
    /**
     * Get the size of the buffers used in writing to and reading from
     * the data sockets
     * 
     * @return  transfer buffer size
     */
    public int getTransferBufferSize() {
        return transferBufferSize;
    }
    
    public void cancelTransfer() {
        cancelTransfer = true;
        log.warn("cancelTransfer() called");
    } 
    
    /**
     * We can force PORT to send a fixed IP address, which can be useful with certain
     * NAT configurations. Must be connected to the remote host to call this method.
     * 
     * @param forcedActiveIP     IP address to force, in 192.168.1.0 form or in IPV6 form, e.g.
     *                            1080::8:800:200C:417A
     */
    public void setActiveIPAddress(String forcedActiveIP) 
        throws FTPException {
        
        checkConnection(true);
        control.setActivePortIPAddress(forcedActiveIP);
    }
    
    
    /**
     * Force a certain range of ports to be used in active mode. This is
     * generally so that a port range can be configured in a firewall. Note
     * that if lowest == highest, a single port will be used. This works well
     * for uploads, but downloads generally require multiple ports, as most
     * servers fail to create a connection repeatedly for the same port.
     * 
     * @param lowest     Lower limit of range.
     * @param highest    Upper limit of range.
     */
    public void setActivePortRange(int lowest, int highest) 
        throws FTPException {
        
        checkConnection(true);
        
        if (lowest < 0 || lowest > highest || highest > MAX_PORT)
            throw new FTPException("Invalid port range specified");
        
        control.setActivePortRange(lowest, highest);
        
        log.debug("setActivePortRange(" + lowest + "," + highest + ")");
    }
    
       
    /**
     *  Login into an account on the FTP server. This
     *  call completes the entire login process. Note that
     *  connect() must be called first.
     *
     *  @param   user       user name
     *  @param   password   user's password
     */
    public void login(String user, String password)
        throws IOException, FTPException {
    	
    	checkConnection(true);
        
        user(user);

        if (lastValidReply.getReplyCode().equals("230"))
            return;
        else {
            password(password);
        }
    }
    
    /**
     *  Login into an account on the FTP server. This call completes the 
     *  entire login process. This method permits additional account information 
     *  to be supplied. FTP servers can use combinations of these parameters in 
     *  many different ways, e.g. to pass in proxy details via this method, some 
     *  servers use the "user" as 'ftpUser + "@" + ftpHost + " " + ftpProxyUser', 
     *  the "password" as the FTP user's password, and the accountInfo as the proxy 
     *  password. Note that connect() must be called first.
     *
     *  @param   user           user name
     *  @param   password       user's password
     *  @param   accountInfo    account info string
     */
    public void login(String user, String password, String accountInfo)
        throws IOException, FTPException {
        
        checkConnection(true);
        
        user(user);

        if (lastValidReply.getReplyCode().equals("230")) // no pwd
            return;
        else {
            password(password);
            if (lastValidReply.getReplyCode().equals("332")) // requires acct info
                account(accountInfo);
        }
    }    
    
    /**
     *  Supply the user name to log into an account
     *  on the FTP server. Must be followed by the
     *  password() method - but we allow for no password.
     *  Note that connect() must be called first.
     *
     *  @param   user       user name
     */
    public void user(String user)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("USER " + user);

        // we allow for a site with no password - 230 response
        String[] validCodes = {"230", "331"};
        lastValidReply = control.validateReply(reply, validCodes);
    }


    /**
     *  Supplies the password for a previously supplied
     *  username to log into the FTP server. Must be
     *  preceeded by the user() method
     *
     *  @param   password       The password.
     */
    public void password(String password)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("PASS " + password);

        // we allow for a site with no passwords (202) or requiring
        // ACCT info (332)
        String[] validCodes = {"230", "202", "332"};
        lastValidReply = control.validateReply(reply, validCodes);
    }
    
    
    /**
     *  Supply account information string to the server. This can be
     *  used for a variety of purposes - for example, the server could
     *  indicate that a password has expired (by sending 332 in reply to
     *  PASS) and a new password automatically supplied via ACCT. It
     *  is up to the server how it uses this string.
     *
     *  @param   accountInfo    account information string
     */
    public void account(String accountInfo)
        throws IOException, FTPException {
        
        checkConnection(true);
        
        FTPReply reply = control.sendCommand("ACCT " + accountInfo);

        // ok or not implemented
        String[] validCodes = {"230", "202"};
        lastValidReply = control.validateReply(reply, validCodes);
    }


    /**
     *  Set up SOCKS v4/v5 proxy settings. This can be used if there
     *  is a SOCKS proxy server in place that must be connected thru.
     *  Note that setting these properties directs <b>all</b> TCP
     *  sockets in this JVM to the SOCKS proxy
     *
     *  @param  port  SOCKS proxy port
     *  @param  host  SOCKS proxy hostname
     */
    public static void initSOCKS(String port, String host) {
        Properties props = System.getProperties();
        props.put(SOCKS_PORT, port);
        props.put(SOCKS_HOST, host);
        System.setProperties(props);
    }

    /**
     *  Set up SOCKS username and password for SOCKS username/password
     *  authentication. Often, no authentication will be required
     *  but the SOCKS server may be configured to request these.
     *
     *  @param  username   the SOCKS username
     *  @param  password   the SOCKS password
     */
    public static void initSOCKSAuthentication(String username,
                                               String password) {
        Properties props = System.getProperties();
        props.put("java.net.socks.username", username);
        props.put("java.net.socks.password", password);
        System.setProperties(props);
    }
    
    /**
     * Clear SOCKS settings. Note that setting these properties affects 
     * <b>all</b> TCP sockets in this JVM
     */
    public static void clearSOCKS() {
        
        Properties prop = System.getProperties(); 
        prop.remove(SOCKS_HOST); 
        prop.remove(SOCKS_PORT); 
        System.setProperties(prop); 
    }

    /**
     *  Get the name of the remote host
     *
     *  @return  remote host name
     */
    String getRemoteHostName() {
        return control.getRemoteHostName();
    }

    /**
     *  Issue arbitrary ftp commands to the FTP server.
     *
     *  @param command     ftp command to be sent to server
     *  @param validCodes  valid return codes for this command. If null
     *                      is supplied no validation is performed
     * 
     *  @return  the text returned by the FTP server
     */
    public String quote(String command, String[] validCodes)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand(command);

        // allow for no validation to be supplied
        if (validCodes != null) {
            lastValidReply = control.validateReply(reply, validCodes);
        }
        else { // no validation
            lastValidReply = reply; // assume valid
        }
        return lastValidReply.getReplyText();       
    }
    
    public boolean exists(String remoteFile) throws IOException, FTPException {
        checkConnection(true);
        
        // first try the SIZE command
        if (sizeSupported)
        {
            FTPReply reply = control.sendCommand("SIZE " + remoteFile);
            char ch = reply.getReplyCode().charAt(0);
            if (ch == '2')
                return true;
            if (ch == '5' && fileNotFoundStrings.matches(reply.getReplyText()))
                return false;
                
            sizeSupported = false;
            log.debug("SIZE not supported - trying MDTM");
        }

        // then try the MDTM command
        if (mdtmSupported)
        {
            FTPReply reply = control.sendCommand("MDTM " + remoteFile);
            char ch = reply.getReplyCode().charAt(0);
            if (ch == '2')
                return true;
            if (ch == '5' && fileNotFoundStrings.matches(reply.getReplyText()))
                return false;
             
            mdtmSupported = false;
            log.debug("MDTM not supported - trying RETR");
        }

        // ok, now try RETR since nothing else is supported
        ServerSocket sock = new ServerSocket(0);
        short port = (short)sock.getLocalPort();
        sock.close();
        control.sendPORTCommand(port);
        
        // send the retrieve command
        FTPReply reply = control.sendCommand("RETR " + remoteFile);
        char ch = reply.getReplyCode().charAt(0);
        
        // normally return 125 etc. But could return 425 unable to create data
        // connection, which means the file exists but can't connect to our (non-
        // existent) server socket
        if (ch == '1' || ch == '2' || ch == '4')
            return true;
        if (ch == '5' && fileNotFoundStrings.matches(reply.getReplyText()))
            return false;
        
        String msg = "Unable to determine if file '" + remoteFile + "' exists.";
        log.warn(msg);
        throw new FTPException(msg);
     }

     public long size(String remoteFile)
         throws IOException, FTPException {
     	
     	 checkConnection(true);
     	
         FTPReply reply = control.sendCommand("SIZE " + remoteFile);
         lastValidReply = control.validateReply(reply, "213");

         // parse the reply string .
         String replyText = lastValidReply.getReplyText();
         
         // trim off any trailing characters after a space, e.g. webstar
         // responds to SIZE with 213 55564 bytes
         int spacePos = replyText.indexOf(' ');
         if (spacePos >= 0)
             replyText = replyText.substring(0, spacePos);
         
         // parse the reply
         try {
             return Long.parseLong(replyText);
         }
         catch (NumberFormatException ex) {
             throw new FTPException("Failed to parse reply: " + replyText);
         }         
     }
     
     public void resume() throws FTPException {
         if (transferType.equals(FTPTransferType.ASCII))
             throw new FTPException("Resume only supported for BINARY transfers");
         resume = true;
         log.info("Resume=true");
     }
     
     public void cancelResume() 
         throws IOException, FTPException {
         restart(0);
         resume = false;
     }
     
     /**
      * Issue the RESTart command to the remote server. This indicates the byte
      * position that REST is performed at. For put, bytes start at this point, while
      * for get, bytes are fetched from this point.
      * 
      * @param size     the REST param, the mark at which the restart is 
      *                  performed on the remote file. For STOR, this is retrieved
      *                  by SIZE
      * @throws IOException
      * @throws FTPException
      */
     public void restart(long size) 
         throws IOException, FTPException {
         FTPReply reply = control.sendCommand("REST " + size);
         lastValidReply = control.validateReply(reply, "350");
     }

    public String put(String localPath, String remoteFile)
        throws IOException, FTPException {

        return put(localPath, remoteFile, false);
    }

    public String put(InputStream srcStream, String remoteFile)
        throws IOException, FTPException {

        return put(srcStream, remoteFile, false);
    }

    public String put(String localPath, String remoteFile, boolean append)
        throws IOException, FTPException {
    	    	
        InputStream srcStream = new FileInputStream(localPath);
        return put(srcStream, remoteFile, append);
     }

    public String put(InputStream srcStream, String remoteFile, boolean append)
        throws IOException, FTPException {

        FTPTransferType currentType = transferType;
        chooseTransferMode(remoteFile);
        try {        
            remoteFile = putData(srcStream, remoteFile, append);
            validateTransfer();
        }
        catch (IOException ex) {
            validateTransferOnError();
            throw ex;        
        }
        finally {
            transferType = currentType;
        }
        return remoteFile;
    }

    /**
     * Validate that the put() or get() was successful.  This method is not
     * for general use.
     */
    public void validateTransfer()
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        // check the control response
        String[] validCodes = {"225", "226", "250", "426", "450"};
        FTPReply reply = control.readReply();
        
        // permit 426/450 error if we cancelled the transfer, otherwise
        // throw an exception
        String code = reply.getReplyCode();
        if ( (code.equals("426")||code.equals("450")) && !cancelTransfer )
            throw new FTPException(reply);
        
        lastValidReply = control.validateReply(reply, validCodes);
    }
    
    /**
     * Validate a transfer when an error has occurred on the data channel.
     * Set a very short transfer in case things have hung. Set it back
     * at the end.
     * 
     * @throws IOException
     * @throws FTPException
     */
    private void validateTransferOnError() 
        throws IOException, FTPException {
        
        checkConnection(true);
        control.setTimeout(SHORT_TIMEOUT);
        try {
            validateTransfer();
        }
        finally {
            control.setTimeout(timeout);
        }
    }
    
    /**
     * Close the data socket
     */
    private void closeDataSocket() {
        if (data != null) {
            try {
                data.close();
                data = null;
            } catch (IOException ex) {
                log.warn("Caught exception closing data socket", ex);
            }
        }
    }
    
       
    /**
     * Close stream for data socket
     * 
     * @param stream    stream reference
     */
    private void closeDataSocket(InputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } 
            catch (IOException ex) {
                log.warn("Caught exception closing data socket", ex);
            }
        }

        closeDataSocket();
    }
    
    
    /**
     * Close stream for data socket
     * 
     * @param stream    stream reference
     */
    private void closeDataSocket(OutputStream stream) {
        if (stream != null) {
            try {
                 stream.close();
            } 
            catch (IOException ex) {
                log.warn("Caught exception closing data socket", ex);
            }
        }

        closeDataSocket();
    }
    
    
    /**
     * Set up the data socket
     * 
     * @throws FTPException 
     * @throws IOException 
     */
    protected void setupDataSocket() 
        throws IOException, FTPException {
        
        data = control.createDataSocket(connectMode);
        data.setTimeout(timeout);
     }

    /**
     * Request the server to set up the put
     * 
     * @param remoteFile
     *            name of remote file in current directory
     * @param append
     *            true if appending, false otherwise
     */
    private String initPut(String remoteFile, boolean append)
        throws IOException, FTPException {
    	
    	checkConnection(true);
        
        // if a remote filename isn't supplied, assume STOU is to be used
        boolean storeUnique = (remoteFile == null || remoteFile.length() == 0);
        if (storeUnique) {
            remoteFile = "";
            // check STOU isn't used with append
            if (append) {
                String msg = "A remote filename must be supplied when appending";
                log.error(msg);
                throw new FTPException(msg);
            }
        }
    	
        // reset the cancel flag
        cancelTransfer = false;

        boolean close = false;
        try {
            // set up data channel
            setupDataSocket();
            
            // if resume is requested, we must obtain the size of the
            // remote file and issue REST
            if (resume) {
                if (transferType.equals(FTPTransferType.ASCII))
                    throw new FTPException("Resume only supported for BINARY transfers");
                resumeMarker = size(remoteFile);
                restart(resumeMarker);
            }
    
            // send the command to store
            String cmd = append ? "APPE " : (storeUnique ? "STOU" : "STOR ");
            FTPReply reply = control.sendCommand(cmd + remoteFile);
    
            // Can get a 125 or a 150, also allow 350 (for Global eXchange Services server)
            String[] validCodes = {"125", "150", "350"};
            lastValidReply = control.validateReply(reply, validCodes);
            
            String replyText = lastValidReply.getReplyText();
            if (storeUnique) {
                int pos = replyText.indexOf(STOU_FILENAME_MARKER);
                if (pos >= 0) {
                    pos += STOU_FILENAME_MARKER.length();
                    remoteFile = replyText.substring(pos).trim();
                }
                else { // couldn't find marker, just return last word of reply
                       // e.g. 150 Opening BINARY mode data connection for FTP0000004.
                    log.debug("Could not find " + STOU_FILENAME_MARKER + " in reply - using last word instead.");
                    pos = replyText.lastIndexOf(' ');
                    remoteFile = replyText.substring(++pos);
                    int len = remoteFile.length();
                    if (len > 0 && remoteFile.charAt(len-1) == '.') {
                        remoteFile = remoteFile.substring(0, len-1);
                    }  
                }
            }
            return remoteFile;
        }
        catch (IOException ex) {
            close = true;
            throw ex;
        }
        catch (FTPException ex) {
            close = true;
            throw ex;
        }
        finally {
            if (close) {
                resume = false;
                closeDataSocket();
            }
        }
    }

    /**
     *  Put as binary, i.e. read and write raw bytes
     *
     *  @param  srcStream   input stream of data to put
     *  @param  remoteFile  name of remote file we are writing to
     *  @param  append      true if appending, false otherwise
     */
    private String putData(InputStream srcStream, String remoteFile,
                           boolean append)
        throws IOException, FTPException {

        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        long size = 0;
        try {
            in = new BufferedInputStream(srcStream);
    
            remoteFile = initPut(remoteFile, append);
    
            // get an output stream
            out = new BufferedOutputStream(
                    new DataOutputStream(data.getOutputStream()), transferBufferSize*2);
            
            // if resuming, we skip over the unwanted bytes
            if (resume) {
                in.skip(resumeMarker);
            }
    
            byte[] buf = new byte[transferBufferSize];
            byte[] prevBuf = null;
    
            // read a chunk at a time and write to the data socket            
            long monitorCount = 0;
            int count = 0;
            boolean isASCII = getType() == FTPTransferType.ASCII;
            int separatorPos = 0;
            long start = System.currentTimeMillis();
            
            while ((count = in.read(buf)) > 0 && !cancelTransfer) {
                if (isASCII) {
                    for (int i = 0; i < count; i++) {
                        // at each byte pos, check if there's a match along the line sep array  
                        boolean found = true;
                        int skip = 0;
                        for (; separatorPos < LINE_SEPARATOR.length && i+separatorPos < count; skip++,separatorPos++) {
                            if (buf[i+separatorPos] != LINE_SEPARATOR[separatorPos]) {
                                found = false;
                                break;
                            }
                        }
                        if (found) { // either found match or run out of buffer
                            if (separatorPos == LINE_SEPARATOR.length) { // found line separator
                                out.write(CARRIAGE_RETURN);
                                out.write(LINE_FEED);
                                size += 2;
                                monitorCount += 2;
                                separatorPos = 0;
                                // now must skip over bytes that match
                                i += (skip-1);  // we are already skipping current byte
                                prevBuf = null;
                            }
                            else { // reached end of buffer && are matching so far
                                   // Do nothing, we'll pick it up next time
                                // we need to save the bytes matched so far
                                prevBuf = new byte[skip];
                                for (int k = 0; k < skip; k++) {
                                    prevBuf[k] = buf[i+k];
                                }
                            }
                        }
                        else { // match failed, write out this byte && any prev ones from last buf
                            if (prevBuf != null) {
                                out.write(prevBuf);
                                size += prevBuf.length;
                                monitorCount += prevBuf.length;
                                prevBuf = null;
                            }
                            out.write(buf[i]);
                            size++;
                            monitorCount++;
                            separatorPos = 0;
                        }
                    }
                }
                else {
                    out.write(buf, 0, count);
                    size += count;
                    monitorCount += count;
                }
                
                // write out saved chunk if it exists
                if (prevBuf != null) {
                    out.write(prevBuf);
                    size += prevBuf.length;
                    monitorCount += prevBuf.length;
                }
                    
                if (monitor != null && monitorCount > monitorInterval) {
                    monitor.bytesTransferred(size); 
                    monitorCount = 0;  
                }
                if (serverWakeupInterval > 0 && System.currentTimeMillis() - start > serverWakeupInterval*1000) {
                    start = System.currentTimeMillis();
                    sendServerWakeup();
                }
            }
        }
        finally {
            resume = false;
            try {
                if (in != null)
                    in.close();
            }
            catch (IOException ex) {
                log.warn("Caught exception closing input stream", ex);
            }
                
            closeDataSocket(out);
            
            // notify the final transfer size
            if (monitor != null)
                monitor.bytesTransferred(size);  
            // log bytes transferred
            log.debug("Transferred " + size + " bytes to remote host");           
        }
        return remoteFile;
    }

    public String put(byte[] bytes, String remoteFile)
        throws IOException, FTPException {

        return put(bytes, remoteFile, false);
    }

    public String put(byte[] bytes, String remoteFile, boolean append)
        throws IOException, FTPException {
        
        FTPTransferType currentType = transferType;
        chooseTransferMode(remoteFile);
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            return put(input, remoteFile, append);
        }
        finally {
            transferType = currentType;
        }
    }

    public void get(String localPath, String remoteFile)
        throws IOException, FTPException {

        FTPTransferType currentType = transferType;
        chooseTransferMode(remoteFile);
        try {        
            getData(localPath, remoteFile);
            validateTransfer();
        }
        catch (IOException ex) {
            validateTransferOnError();
            throw ex;        
        }
        finally {
            transferType = currentType;
        }
    }

    public void get(OutputStream destStream, String remoteFile)
        throws IOException, FTPException {

        FTPTransferType currentType = transferType;
        chooseTransferMode(remoteFile);
        try {        
            getData(destStream, remoteFile);
            validateTransfer();
        }
        catch (IOException ex) {
            validateTransferOnError();
            throw ex;        
        }
        finally {
            transferType = currentType;
        }
    }


    /**
     *  Request to the server that the get is set up
     *
     *  @param  remoteFile  name of remote file
     */
    private void initGet(String remoteFile)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        // reset the cancel flag
        cancelTransfer = false;            

        boolean close = false;
        try {
            // set up data channel
            setupDataSocket();
            
            // if resume is requested, we must issue REST
            if (resume) {
                if (transferType.equals(FTPTransferType.ASCII))
                    throw new FTPException("Resume only supported for BINARY transfers");
                restart(resumeMarker);
            }
    
            // send the retrieve command
            FTPReply reply = control.sendCommand("RETR " + remoteFile);
    
            // Can get a 125 or a 150
            String[] validCodes1 = {"125", "150"};
            lastValidReply = control.validateReply(reply, validCodes1);
        }
        catch (IOException ex) {
            close = true;
            throw ex;
        }
        catch (FTPException ex) {
            close = true;
            throw ex;
        }
        finally {
            if (close) {
                resume = false;
                closeDataSocket();
            }
        }
    }
    

    /**
     *  Get as binary file, i.e. straight transfer of data
     *
     *  @param localPath   full path of local file to write to
     *  @param remoteFile  name of remote file
     */
    private void getData(String localPath, String remoteFile)
        throws IOException, FTPException {

        // B. McKeown: Need to store the local file name so the file can be
        // deleted if necessary.
        File localFile = new File(localPath);
        
        // if resuming, we must find the marker
        if (localFile.exists()) {
            if (!localFile.canWrite())
                throw new FTPException(localPath + " is readonly - cannot write");
            if (resume)
                resumeMarker = localFile.length();
        }           

        // B.McKeown:
        // Call initGet() before creating the FileOutputStream.
        // This will prevent being left with an empty file if a FTPException
        // is thrown by initGet().
        initGet(remoteFile);

        // create the buffered output stream for writing the file
        FileOutputStream out =
                new FileOutputStream(localPath, resume);
        
        try {
            getDataAfterInitGet(out);
        }
        catch (IOException ex) {
            localFile.delete();
            log.debug("Deleting local file '" + localFile.getAbsolutePath() + "'");
            throw ex;
        }        
    }

    /**
     *  Get as binary file, i.e. straight transfer of data
     *
     *  @param destStream  stream to write to
     *  @param remoteFile  name of remote file
     */
    private void getData(OutputStream destStream, String remoteFile)
        throws IOException, FTPException {

        initGet(remoteFile);
        
        getDataAfterInitGet(destStream);
    }
    
        
    /**
     *  Get as binary file, i.e. straight transfer of data
     *
     *  @param destStream  stream to write to
     */
    private void getDataAfterInitGet(OutputStream destStream)
        throws IOException, FTPException {

        // create the buffered output stream for writing the file
        BufferedOutputStream out =
            new BufferedOutputStream(destStream);
        
        BufferedInputStream in = null;
        long size = 0;
        IOException storedEx = null;
        try {
            // get an input stream to read data from ... AFTER we have
            // the ok to go ahead AND AFTER we've successfully opened a
            // stream for the local file
            in = new BufferedInputStream(
                    new DataInputStream(data.getInputStream()));
        
            // do the retrieving
            long monitorCount = 0; 
            byte [] chunk = new byte[transferBufferSize];
            int count;
            boolean isASCII = getType() == FTPTransferType.ASCII;
            boolean crFound = false;
            long start = System.currentTimeMillis();

            // read from socket & write to file in chunks        
            while ((count = readChunk(in, chunk, transferBufferSize)) >= 0 && !cancelTransfer) {
                if (isASCII) {
                    // transform CRLF
                    boolean lfFound = false;
                    for (int i = 0; i < count; i++) {
                        lfFound = chunk[i] == LINE_FEED;
                        // if previous is a CR, write it out if current is LF, otherwise
                        // write out the previous CR
                        if (crFound) {
                            if (lfFound) {
                               out.write(LINE_SEPARATOR, 0, LINE_SEPARATOR.length);
                               size += LINE_SEPARATOR.length;
                               monitorCount += LINE_SEPARATOR.length;
                            }
                            else {
                                // not CR LF so write out previous CR
                                out.write(CARRIAGE_RETURN); 
                                size++;
                                monitorCount++;
                            }                           
                        }
                        
                        // now check if current is CR
                        crFound = chunk[i] == CARRIAGE_RETURN;
                       
                        // if we didn't find a LF this time, write current byte out
                        // unless it is a CR - in that case save it
                        if (!lfFound && !crFound) {
                            out.write(chunk[i]);
                            size++;
                            monitorCount++;
                        }
                    }
                }
                else { // binary
                    out.write(chunk, 0, count);
                    size += count;
                    monitorCount += count;
                }
                
                if (monitor != null && monitorCount > monitorInterval) {
                    monitor.bytesTransferred(size); 
                    monitorCount = 0;  
                }    
    
                if (serverWakeupInterval > 0 && System.currentTimeMillis() - start > serverWakeupInterval*1000) {
                    start = System.currentTimeMillis();
                    sendServerWakeup();
                }
            }            
            
            // account for last byte if necessary
            if (isASCII && crFound) { 
                out.write(CARRIAGE_RETURN); 
                size++;
                monitorCount++;
            }
        }
        catch (IOException ex) {
            storedEx = ex;
        }
        finally {
            try {
                if (out != null)
                    out.close();
            }
            catch (IOException ex) {
                log.warn("Caught exception closing output stream", ex);
            }

            resume = false;
    
            // close streams
            closeDataSocket(in);
    
            // if we failed to write the file, rethrow the exception
            if (storedEx != null)
                throw storedEx;
            else if (monitor != null)
                monitor.bytesTransferred(size);  
    
            // log bytes transferred
//            log.debug("Transferred " + size + " bytes from remote host");
        }
    }

    public byte[] get(String remoteFile)
        throws IOException, FTPException {

        FTPTransferType currentType = transferType;
        chooseTransferMode(remoteFile);
        try {                
            ByteArrayOutputStream result = new ByteArrayOutputStream(transferBufferSize);
            getData(result, remoteFile);
            validateTransfer();
            return result == null ? null : result.toByteArray();
        }
        catch (IOException ex) {
            validateTransferOnError();
            throw ex;        
        }
        finally {
            transferType = currentType;
        }
    }


    /**
     *  Run a site-specific command on the
     *  server. Support for commands is dependent
     *  on the server
     *
     *  @param  command   the site command to run
     *  @return true if command ok, false if
     *          command not implemented
     */
    public boolean site(String command)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        // send the retrieve command
        FTPReply reply = control.sendCommand("SITE " + command);

        // Can get a 200 (ok) or 202 (not impl). Some
        // FTP servers return 502 (not impl)
        String[] validCodes = {"200", "202", "502"};
        lastValidReply = control.validateReply(reply, validCodes);

        // return true or false? 200 is ok, 202/502 not
        // implemented
        if (reply.getReplyCode().equals("200"))
            return true;
        else
            return false;
    }
    
    
    /**
     * Override the chosen file factory with a user created one - meaning
     * that a specific parser has been selected
     * 
     * @param fileFactory
     */
    public void setFTPFileFactory(FTPFileFactory fileFactory) {
        this.fileFactory = fileFactory;
    }
    
    /**
     * Set the locale for date parsing of dir listings
     * 
     * @param locale    new locale to use
     */
    public void setParserLocale(Locale locale) {
        listingLocale = locale;
    }
    
    /**
     * Uses the MLST command to find out details about the
     * named file. A single filename should be supplied. Note
     * that the MLST command is not supported by many servers.
     * 
     * @param name   name of a file
     * @return  if it exists, an FTPFile object
     */
    public FTPFile fileDetails(String name)  
        throws IOException, FTPException, ParseException {
        
        checkConnection(true);
        
        FTPReply reply = control.sendCommand("MLST " + name);
        lastValidReply = control.validateReply(reply, "250");
        
        if (reply.getReplyData() != null)
            return mlsxParser.parse(reply.getReplyData()[0]);
        
        log.warn("No file data returned from MLST");
        return null;
    }

    public FTPFile[] dirDetails(String dirname) 
        throws IOException, FTPException, ParseException {
        
        // create the factory
        if (fileFactory == null)
            fileFactory = new FTPFileFactory(system());
        fileFactory.setLocale(listingLocale);
                
        // get the details and parse
        return fileFactory.parse(dir(dirname, true));
    }

    public String[] dir()
        throws IOException, FTPException {

        return dir(null, false);
    }

    public String[] dir(String dirname)
        throws IOException, FTPException {

        return dir(dirname, false);
    }

    public String[] dir(String dirname, boolean full)
        throws IOException, FTPException {
    	
    	checkConnection(true);
        
        try {
            // set up data channel
            setupDataSocket();
    
            // send the retrieve command
            String command = full ? "LIST ":"NLST ";
            if (dirname != null)
                command += dirname;
    
            // some FTP servers bomb out if NLST has whitespace appended
            command = command.trim();
            FTPReply reply = control.sendCommand(command);
    
            // check the control response. wu-ftp returns 550 if the
            // directory is empty, so we handle 550 appropriately. Similarly
            // proFTPD returns 450 or 226 (depending on NLST or LIST)
            String[] validCodes1 = {"125", "150", "226", "450", "550"};
            lastValidReply = control.validateReply(reply, validCodes1);  
    
            // an empty array of files for 450/550
            String[] result = new String[0];
            
            // a normal reply ... extract the file list
            String replyCode = lastValidReply.getReplyCode();
            if (!replyCode.equals("450") && !replyCode.equals("550") && !replyCode.equals("226")) {
    			// get a character input stream to read data from .
    			LineNumberReader in = null;
                Vector lines = new Vector();    
                try {
    				in = new LineNumberReader(
    					    new InputStreamReader(data.getInputStream(), controlEncoding));
    
        			// read a line at a time
        			String line = null;
        			while ((line = readLine(in)) != null) {
                        lines.addElement(line);
//                        log.log(Level.ALL, line, null);
        			}
                }
                finally {
                    try {
                        if (in != null)
                            in.close();
                    }
                    catch (IOException ex) {
                        log.error("Failed to close socket in dir()", ex);
                    }
                    closeDataSocket();
                }
                    
                // check the control response
                String[] validCodes2 = {"226", "250"};
                reply = control.readReply();
                lastValidReply = control.validateReply(reply, validCodes2);
    
                // empty array is default
                if (!lines.isEmpty()) {
                    result = new String[lines.size()];
                    lines.copyInto(result);
                }
            }
            else { // throw exception if not "No files" or other message
            	String replyText = lastValidReply.getReplyText().toUpperCase();
				if (!dirEmptyStrings.matches(replyText)
						&& !transferCompleteStrings.matches(replyText))
					throw new FTPException(reply);
            }
            return result;
        }
        finally {
            closeDataSocket();
        }        
    }
    
    /**
	 * Attempts to read a specified number of bytes from the given
	 * <code>InputStream</code> and place it in the given byte-array. The
	 * purpose of this method is to permit subclasses to execute any additional
	 * code necessary when performing this operation.
	 * 
	 * @param in
	 *            The <code>InputStream</code> to read from.
	 * @param chunk
	 *            The byte-array to place read bytes in.
	 * @param chunksize
	 *            Number of bytes to read.
	 * @return Number of bytes actually read.
	 * @throws IOException
	 *             Thrown if there was an error while reading.
	 */
    protected int readChunk(BufferedInputStream in, byte[] chunk, int chunksize) 
    	throws IOException {
    		
    	return in.read(chunk, 0, chunksize);
    }
    
    /**
     * Attempts to read a single character from the given <code>InputStream</code>. 
     * The purpose of this method is to permit subclasses to execute
     * any additional code necessary when performing this operation. 
     * @param in The <code>LineNumberReader</code> to read from.
     * @return The character read.
     * @throws IOException Thrown if there was an error while reading.
     */
    protected int readChar(LineNumberReader in) 
    	throws IOException {
    		
    	return in.read();
    }
    
    /**
     * Attempts to read a single line from the given <code>InputStream</code>. 
     * The purpose of this method is to permit subclasses to execute
     * any additional code necessary when performing this operation. 
     * @param in The <code>LineNumberReader</code> to read from.
     * @return The string read.
     * @throws IOException Thrown if there was an error while reading.
     */
    protected String readLine(LineNumberReader in) 
    	throws IOException {
    		
    	return in.readLine();
    }

    /**
     *  Gets the latest valid reply from the server
     *
     *  @return  reply object encapsulating last valid server response
     */
    public FTPReply getLastValidReply() {
        return lastValidReply;
    }


    /**
     *  Get the current transfer type
     *
     *  @return  the current type of the transfer,
     *           i.e. BINARY or ASCII
     */
    public FTPTransferType getType() {
        return transferType;
    }

    /**
     *  Set the transfer type
     *
     *  @param  type  the transfer type to
     *                set the server to
     */
    public void setType(FTPTransferType type)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        // determine the character to send
        String typeStr = FTPTransferType.ASCII_CHAR;
        if (type.equals(FTPTransferType.BINARY))
            typeStr = FTPTransferType.BINARY_CHAR;

        // send the command
        FTPReply reply = control.sendCommand("TYPE " + typeStr);
        lastValidReply = control.validateReply(reply, "200");

        // record the type
        transferType = type;
    }

    public void delete(String remoteFile)
        throws IOException, FTPException {
    	
    	checkConnection(true);
        String[] validCodes = {"200", "250"};
        FTPReply reply = control.sendCommand("DELE " + remoteFile);
        lastValidReply = control.validateReply(reply, validCodes);
    }

    public void rename(String from, String to)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("RNFR " + from);
        lastValidReply = control.validateReply(reply, "350");

        reply = control.sendCommand("RNTO " + to);
        lastValidReply = control.validateReply(reply, "250");
    }

   
    public void rmdir(String dir)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("RMD " + dir);

        // some servers return 200,257, technically incorrect but
        // we cater for it ...
        String[] validCodes = {"200", "250", "257"};
        lastValidReply = control.validateReply(reply, validCodes);
    }



    public void mkdir(String dir)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("MKD " + dir);
        
        // some servers return 200,257, technically incorrect but
        // we cater for it ...
        String[] validCodes = {"200", "250", "257"};
        lastValidReply = control.validateReply(reply, validCodes);
    }


    public void chdir(String dir)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("CWD " + dir);
        lastValidReply = control.validateReply(reply, "250");
    }
    

    public void cdup()
        throws IOException, FTPException {
        
        checkConnection(true);
        
        FTPReply reply = control.sendCommand("CDUP");
        String[] validCodes = {"200", "250"};
        lastValidReply = control.validateReply(reply, validCodes);
    }


    public Date modtime(String remoteFile)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("MDTM " + remoteFile);
        lastValidReply = control.validateReply(reply, "213");

        // parse the reply string ...
        Date ts = tsFormat.parse(lastValidReply.getReplyText(),
                                 new ParsePosition(0));
        return ts;
    }

    public String pwd()
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("PWD");
        lastValidReply = control.validateReply(reply, "257");

        // get the reply text and extract the dir
        // listed in quotes, if we can find it. Otherwise
        // just return the whole reply string
        String text = lastValidReply.getReplyText();
        int start = text.indexOf('"');
        int end = text.lastIndexOf('"');
        if (start >= 0 && end > start)
            return text.substring(start+1, end);
        else
            return text;
    }
    
    
    /**
     *  Get the server supplied features
     *
     *  @return   string containing server features, or null if no features or not
     *             supported
     */
    public String[] features()
        throws IOException, FTPException {
        
        checkConnection(true);
        
        FTPReply reply = control.sendCommand("FEAT");
        String[] validCodes = {"211", "500", "502"};
        lastValidReply = control.validateReply(reply, validCodes);
        if (lastValidReply.getReplyCode().equals("211"))
            return lastValidReply.getReplyData();
        else
            throw new FTPException(reply);
    }
    
    /**
     *  Get the type of the OS at the server
     *
     *  @return   the type of server OS
     */
    public String system()
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("SYST");
        String[] validCodes = {"200", "213", "215"};
        lastValidReply = control.validateReply(reply, validCodes);
        return lastValidReply.getReplyText();
    }
    
    
    /**
     *  Send a "no operation" message that does nothing. Can be
     *  called periodically to prevent the connection timing out
     */
    public void noOperation()
        throws IOException, FTPException {
        
        checkConnection(true);
        
        FTPReply reply = control.sendCommand("NOOP");
        lastValidReply = control.validateReply(reply, "200");
    }

    /**
     *  Sends stat message to enquire about the status of a
     *  transfer. 
     */
    public String stat()
        throws IOException, FTPException {
        
        checkConnection(true);
        
        FTPReply reply = control.sendCommand("STAT");
        String[] validCodes = {"211", "212", "213"};
        lastValidReply = control.validateReply(reply, validCodes);
        return lastValidReply.getReplyText();
    }
    
    /**
     * Wake up the server during a transfer to prevent a
     * timeout from occuring. This may hang or confuse the server -
     * use with caution.
     * 
     * @throws IOException 
     * @throws FTPException 
     *
     */
    public void sendServerWakeup() throws IOException, FTPException {
        noOperation();
     }
    
    /**
     *  Get the help text for the specified command
     *
     *  @param  command  name of the command to get help on
     *  @return help text from the server for the supplied command
     */
    public String help(String command)
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        FTPReply reply = control.sendCommand("HELP " + command);
        String[] validCodes = {"211", "214"};
        lastValidReply = control.validateReply(reply, validCodes);
        return lastValidReply.getReplyText();
    }
    
     /**
     *  Abort the current action
     */
    protected void abort()
        throws IOException, FTPException {
        
        checkConnection(true);
        
        FTPReply reply = control.sendCommand("ABOR");
        String[] validCodes = {"426", "226"};
        lastValidReply = control.validateReply(reply, validCodes);
    }

    public void quit()
        throws IOException, FTPException {
    	
    	checkConnection(true);
    	
        try {
            FTPReply reply = control.sendCommand("QUIT");
            String[] validCodes = {"221", "226"};
            lastValidReply = control.validateReply(reply, validCodes);
        }
        finally { // ensure we clean up the connection
            control.logout();
            control = null;
        }
    }
    
    public void quitImmediately() 
        throws IOException, FTPException {
        
        checkConnection(true);
        
        cancelTransfer();
        control.controlSock.close();
    }
    
    /**
     * String representation
     */
    public String toString() {
        StringBuffer result = new StringBuffer("[");
        result.append("FTP").append(",").append(remoteHost).append(",").append(controlPort).
            append(",").append(getId()).append("]");
        return result.toString();
    }

}


