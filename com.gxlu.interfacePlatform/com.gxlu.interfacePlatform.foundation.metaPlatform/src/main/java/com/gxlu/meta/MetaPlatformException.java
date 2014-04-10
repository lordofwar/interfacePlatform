package com.gxlu.meta;

import com.gxlu.meta.exception.ServiceException;

/**
 * @author K
 */
public class MetaPlatformException extends ServiceException {

	public MetaPlatformException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public MetaPlatformException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MetaPlatformException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	public MetaPlatformException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MetaPlatformException(String namingSpace, int errCode, String msg,
			Throwable cause) {
		super(namingSpace, errCode, msg, cause);
		// TODO Auto-generated constructor stub
	}

	public MetaPlatformException(String namingSpace, int errCode, String msg) {
		super(namingSpace, errCode, msg);
		// TODO Auto-generated constructor stub
	}

	public MetaPlatformException(String namingSpace, int errCode) {
		super(namingSpace, errCode);
		// TODO Auto-generated constructor stub
	}

	public MetaPlatformException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
