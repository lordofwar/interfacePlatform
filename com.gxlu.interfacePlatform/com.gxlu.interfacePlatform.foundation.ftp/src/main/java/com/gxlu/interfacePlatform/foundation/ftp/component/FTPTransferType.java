/**************************************************************************
 *
 * $RCSfile: FTPTransferType.java,v $  $Revision: 1.1 $  $Date: 2007/11/22 15:05:54 $
 *
 * $Log: FTPTransferType.java,v $
 * Revision 1.1  2007/11/22 15:05:54  fuzhw
 * MR#:NM60-0000
 * ftp tool
 *
 *
***************************************************************************/

package com.gxlu.interfacePlatform.foundation.ftp.component;

/**
 *  Enumerates the transfer types possible. We
 *  support only the two common types, ASCII and
 *  Image (often called binary).
 *
 *
 */
 public class FTPTransferType {

     /**
      *   Represents ASCII transfer type
      */
     public static final FTPTransferType ASCII = new FTPTransferType();

     /**
      *   Represents Image (or binary) transfer type
      */
     public static final FTPTransferType BINARY = new FTPTransferType();

     /**
      *   The char sent to the server to set ASCII
      */
     static String ASCII_CHAR = "A";

     /**
      *   The char sent to the server to set BINARY
      */
     static String BINARY_CHAR = "I";

     /**
      *  Private so no-one else can instantiate this class
      */
     private FTPTransferType() {
     }
 }
