package com.gxlu.meta.exception;

/**
 * <pre>
 * File name : BasicException.java
 * Created on : 2007-11-21 13:46:03
 * Description : ҵ���߼��쳣�����пɼ���쳣�̳и��ࡣ
 * @author Jonathan Wong
 * </pre>
 */
public class BasicException extends Exception {

	private static final long serialVersionUID = 3571983037374003218L;

	/**
	 * �����ռ䣬����ģ������
	 */
	private String namingSpace;

	/**
	 * �������
	 */
	private int errCode;

	protected BasicException() {
		this(null, -1);
	}

	public BasicException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause);
		//super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public BasicException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BasicException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	protected BasicException(String message) {
		this(null, -1, message);
	}

	protected BasicException(String namingSpace, int errCode) {
		this(namingSpace, errCode, "Application error.");
	}

	protected BasicException(String namingSpace, int errCode, String msg) {
		this(namingSpace, errCode, msg, null);
	}

	protected BasicException(String namingSpace, int errCode, String msg,
			Throwable cause) {
		super(msg, cause);
		this.namingSpace = namingSpace;
		this.errCode = errCode;
	}

	public String getMessage() {
		if (errCode > 0) {
			StringBuffer errMsg = new StringBuffer();
			errMsg.append("[");
			errMsg.append("ERROR CODE : ");
			errMsg.append(getFullErrCode());
			errMsg.append("] ");
			errMsg.append(super.getMessage());
			return errMsg.toString();
		}
		return super.getMessage();
	}

	/**
	 * �õ������Ĵ������
	 * 
	 * @return
	 */
	public String getFullErrCode() {
		return namingSpace.toUpperCase() + "_" + String.valueOf(errCode);
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getNamingSpace() {
		return namingSpace;
	}

	public void setNamingSpace(String namingSpace) {
		this.namingSpace = namingSpace;
	}
}
