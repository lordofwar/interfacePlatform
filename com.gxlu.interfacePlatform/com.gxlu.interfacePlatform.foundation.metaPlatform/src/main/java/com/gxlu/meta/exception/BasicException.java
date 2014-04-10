package com.gxlu.meta.exception;

/**
 * <pre>
 * File name : BasicException.java
 * Created on : 2007-11-21 13:46:03
 * Description : 业务逻辑异常，所有可检查异常继承该类。
 * @author Jonathan Wong
 * </pre>
 */
public class BasicException extends Exception {

	private static final long serialVersionUID = 3571983037374003218L;

	/**
	 * 命名空间，用于模块区分
	 */
	private String namingSpace;

	/**
	 * 错误代码
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
	 * 得到完整的错误代码
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
