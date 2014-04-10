/**************************************************************************
 * $RCSfile: $  $Revision: $  $Date:  $
 *
 * $Log: $
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : DicConst.java
 * Created on : Jul 23, 2013 11:45:06 AM
 * Creator : freeyfh
 */
package com.gxlu.international.common;

/**
 * <pre>
 * Description : TODO
 * @author freeyfh
 * </pre>
 */
public class DicConst {
  public final static String COMMON_CONNECT = "_";
  //Convert_Type
  public static int CONVERT_TYPE_ERICSSON = 1;
  public static int CONVERT_TYPE_HUAWEI = 2;
  
  //splitsign
  public static String SPACE_SPLITSIGN = " ";
  
  //LINKTYEP
  public static String LINKTYPE_SCTPLINK_IP = "SCTPLink(IP)";
  public static String LINKTYPE_SCTPLINK_TDM = "SCTPLink(TDM)";
  public static String LINKTYPE_PAYLOADLINK_IP = "PayloadLink(IP)";
  public static String LINKTYPE_PAYLOADLINK_TDM = "PayloadLink(TDM)";
  public static String LINKTYPE_SCTPLINK = LINKTYPE_SCTPLINK_IP;
  public static String LINKTYPE_PAYLOADLINK = LINKTYPE_PAYLOADLINK_IP;
  public static String LINKTYPE_ABIS = "ABIS"; //BTS-BSC
  public static String LINKTYPE_A = "A"; //BSC-MGW
  public static String LINKTYPE_GB = "GB";//BSC-SGSN
  public static String LINKTYPE_GN = "GN"; //SGSN-GGSN
  public static String LINKTYPE_MC = "MC"; //MGW-MSC
  public static String LINKTYPE_IUB = "IUB";//NODEB-RNC
  public static String LINKTYEP_IUPS = "IUPS";//RNC-SGSN
  public static String LINKTYPE_IUCS = "IUCS"; //RNC-MGW
  public static String LINKTYPE_E = "E"; //MSC-MSC
  public static String LINKTYPE_NB = "NB"; //MGW-MGW
  
  //Interfacelib
  public final static String INTERFACECODE_MOBILE_FTP_M2000 = "DRM_MOBILE_FTP_M2000";
  public final static String INTERFACECODE_MOBILE_FTP_ERICSSON = "DRM_MOBILE_FTP_ERICSSON";
  public final static String INTERFACECODE_MOBILE_TELNET_ERICSSON = "DRM_MOBILE_TELNET_ERICSSON";
  
  public final static String TELNET_EXECUTOR_ERS_CORE = INTERFACECODE_MOBILE_TELNET_ERICSSON + "_"+"CORE";
  public final static String TELNET_EXECUTOR_ERS_RAN = INTERFACECODE_MOBILE_TELNET_ERICSSON + "_"+"RAN";
  public final static String TELNET_EXECUTOR_ERS_PSCORE = INTERFACECODE_MOBILE_TELNET_ERICSSON + "_"+"PSCORE";
  
  //M2000设备/机框/板卡/端口的一些初始化信息
  public final static int BACK_BOARD_M2000 = 1;
  public final static int FRONT_BOARD_M2000 = 0;
  
  public final static String BOARDTYPE_M2000_UMG8900_MPU = "MPU";
  public final static String BOARDTYPE_M2000_UMG8900_TNU = "TNU";
  
  public final static String MPUDEFAULTFRAMENO_M2000_UMG8900 = "0"; 
  public final static String TNUDEFAULTFRAMENO_M2000_UMG8900 = "1";
  public final static String DEFAULTPORTNO_M2000_UMG8900 = "0";
  
  public final static String DEFAULTPORTNO_M2000 = "0";
  public final static String DEFAULTSLOTPOS_M2000 = "0";
  
  public final static String DEFAULT_NODEBTYPE_M2000 = "BTS3900 WCDMA";
  
  //爱立信设备
  public final static String ERS_PORTTYPE_GE = "GE";
  public final static String ERS_PORTTYPE_FE = "FE";
  public final static String ERS_PORTTYPE_TRANS = "SDH";
  public final static String ERS_PORTTYPE_IPPORT = "IPPORT"; 
  public final static String ERS_PORTTYPE_ETH = "ETH";
  
  public final static String DEFUALTPORTNO_ETIP_RNC = "1";
  public final static String DEFUALTPORTNO_GPB_RNC = DEFUALTPORTNO_ETIP_RNC;
  public final static String DEFUALTPORTNO_SPB_RNC = DEFUALTPORTNO_ETIP_RNC;
  
  public final static String RNC3820_SHELFTYPE_CPPHCSBP = "CPP HCS BP";
  public final static String RNC3820_SHELFTYPE_HPS = "HPS";
  public final static String RNC3820_FANTYPE = "FAN";  
  
  public final static String APZ_SHELFTYPE_GEM = "GEM";
  public final static String APZ_SHELFTYPE_GDM = "GDM";
  public final static String APZ_SHELFTYPE_EGEM = "EGEM";
  //前后框
  public final static int ERS_SHELF_POS_FRONT = 0;
  public final static int ERS_SHELF_POS_BP = 1;
  
  //155M
  public final static String ERS_PORT_RATE_155M = "155M";
  
  //nemodel
  public final static String ERS_NEMODEL_MSC_BLADE214_33 = "APZ_214_3";
  public final static String ERS_NEMODEL_BSC_212_33 = "APZ_212_33";
  public final static String ERS_2G_NEMODEL_PRE = "APZ";
  public final static String ERS_2G_NEMODEL_SPARATE = "_";
  
  //boardtype
  public final static String ERS_BOARD_CPUB = "CPUB-24";
  public final static String ERS_BOARD_CPUB_ETH0 = "ETH-0";
  
  public static String ERICSSON_ROWSEPARATOR_SPLIT = ",";
}
