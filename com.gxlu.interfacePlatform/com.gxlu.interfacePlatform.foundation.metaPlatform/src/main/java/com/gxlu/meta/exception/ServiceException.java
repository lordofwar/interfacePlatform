package com.gxlu.meta.exception;

public class ServiceException extends BasicException {

	private static final long serialVersionUID = 1173732573551510412L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	protected ServiceException(String message) {
		super(message);
	}

	public ServiceException(String namingSpace, int errCode) {
		super(namingSpace, errCode);
	}

	public ServiceException(String namingSpace, int errCode, String msg) {
		super(namingSpace, errCode, msg, null);
	}

	public ServiceException(String namingSpace, int errCode, String msg,
			Throwable cause) {
		super(namingSpace, errCode, msg, cause);
	}

}
