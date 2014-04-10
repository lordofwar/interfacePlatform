/**************************************************************************
 * $RCSfile: MetaPlatformPersisterException.java,v $  $Revision: 1.4 $  $Date: 2009/06/17 05:56:10 $
 *
 * $Log: MetaPlatformPersisterException.java,v $
 * Revision 1.4  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.3  2009/03/23 01:31:01  richie
 * *** empty log message ***
 *
 * Revision 1.2  2009/01/04 01:21:03  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/31 05:24:24  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.engine.persister.entity;

import com.gxlu.meta.MetaPlatformException;


/**
 * @author K
 */
public class MetaPlatformPersisterException extends MetaPlatformException {

  public MetaPlatformPersisterException() {
    super();
  }
  
  public MetaPlatformPersisterException(String namingSpace, int errCode,
		String msg, Throwable cause) {
	super(namingSpace, errCode, msg, cause);
	// TODO Auto-generated constructor stub
}

public MetaPlatformPersisterException(String namingSpace, int errCode,
		String msg) {
	super(namingSpace, errCode, msg);
	// TODO Auto-generated constructor stub
}

public MetaPlatformPersisterException(String namingSpace, int errCode) {
	super(namingSpace, errCode);
	// TODO Auto-generated constructor stub
}

public MetaPlatformPersisterException(String message) {
    super(message);
  }

  public MetaPlatformPersisterException(Throwable throwable) {
    super(throwable);
  }

  public MetaPlatformPersisterException(String message, Throwable throwable) {
    super(message, throwable);
  }

  private static final long serialVersionUID = 1L;
}

