/**************************************************************************
 *
 * $RCSfile: FTPConnectMode.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:52 $
 *
 * $Log: FTPConnectMode.java,v $
 * Revision 1.1  2007/11/22 15:05:52  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/

package com.gxlu.interfacePlatform.foundation.ftp.component;

/**
 *  Enumerates the connect modes that are possible,
 *  active & PASV
 *
 *
 */
 public class FTPConnectMode {


     /**
      *   Represents active connect mode
      */
     public static final FTPConnectMode ACTIVE = new FTPConnectMode();

     /**
      *   Represents PASV connect mode
      */
     public static final FTPConnectMode PASV = new FTPConnectMode();

     /**
      *  Private so no-one else can instantiate this class
      */
     private FTPConnectMode() {
     }
 }
