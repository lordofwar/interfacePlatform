package com.gxlu.international.common.obj;

public class XB_INTPT_LOG {
	private String ID;
	private String VERSION;
	private String METACATEGORY;
	private String THREADID;
	private String INTERFACENAME;
	private String COMMENTS;
	private String CREATEDATE;
	private String LOGLEVEL;
	private String MESSAGE;
	private String TARGETCODE;
	private String TARGETSYS;
	private int SERIALNO;
	private String NMAREACODE;

	public XB_INTPT_LOG(String mETACATEGORY,String tHREADID, String iNTERFACENAME, String cOMMENTS, String lOGLEVEL, String mESSAGE, String tARGETCODE, String tARGETSYS,
			int sERIALNO, String nMAREACODE) {
		METACATEGORY = mETACATEGORY;
		THREADID = tHREADID;
		INTERFACENAME = iNTERFACENAME;
		COMMENTS = cOMMENTS;
		LOGLEVEL = lOGLEVEL;
		MESSAGE = mESSAGE;
		TARGETCODE = tARGETCODE;
		TARGETSYS = tARGETSYS;
		SERIALNO = sERIALNO;
		NMAREACODE = nMAREACODE;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getVERSION() {
		return VERSION;
	}

	public void setVERSION(String vERSION) {
		VERSION = vERSION;
	}

	public String getMETACATEGORY() {
		return METACATEGORY;
	}

	public void setMETACATEGORY(String mETACATEGORY) {
		METACATEGORY = mETACATEGORY;
	}

	public String getTHREADID() {
		return THREADID;
	}

	public void setTHREADID(String tHREADID) {
		THREADID = tHREADID;
	}

	public String getINTERFACENAME() {
		return INTERFACENAME;
	}

	public void setINTERFACENAME(String iNTERFACENAME) {
		INTERFACENAME = iNTERFACENAME;
	}

	public String getCOMMENTS() {
		return COMMENTS;
	}

	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
	}

	public String getCREATEDATE() {
		return CREATEDATE;
	}

	public void setCREATEDATE(String cREATEDATE) {
		CREATEDATE = cREATEDATE;
	}

	public String getLOGLEVEL() {
		return LOGLEVEL;
	}

	public void setLOGLEVEL(String lOGLEVEL) {
		LOGLEVEL = lOGLEVEL;
	}

	public String getMESSAGE() {
		return MESSAGE;
	}

	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}

	public String getTARGETCODE() {
		return TARGETCODE;
	}

	public void setTARGETCODE(String tARGETCODE) {
		TARGETCODE = tARGETCODE;
	}

	public String getTARGETSYS() {
		return TARGETSYS;
	}

	public void setTARGETSYS(String tARGETSYS) {
		TARGETSYS = tARGETSYS;
	}

	public int getSERIALNO() {
		return SERIALNO;
	}

	public void setSERIALNO(int sERIALNO) {
		SERIALNO = sERIALNO;
	}

	public String getNMAREACODE() {
		return NMAREACODE;
	}

	public void setNMAREACODE(String nMAREACODE) {
		NMAREACODE = nMAREACODE;
	}

	public String getInsertSql() {
		return "INSERT INTO XB_INTPT_LOG(ID,VERSION,METACATEGORY,THREADID,INTERFACENAME,COMMENTS,CREATEDATE,LOGLEVEL,MESSAGE,TARGETCODE,TARGETSYS,"
				+ "SERIALNO,NMAREACODE) VALUES(S_XB_INTPT_LOG.NEXTVAL,'1','"+METACATEGORY+"','" + THREADID + "','" + INTERFACENAME + "','"
				+ COMMENTS + "',sysdate,'" + LOGLEVEL + "','" + MESSAGE + "','" + TARGETCODE + "','" + TARGETSYS + "','" + SERIALNO + "','" + NMAREACODE + "')";
	}

}
