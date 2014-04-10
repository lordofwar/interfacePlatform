/**************************************************************************
 * $RCSfile: SqlExecutorException.java,v $  $Revision: 1.2 $  $Date: 2009/06/17 05:56:10 $
 *
 * $Log: SqlExecutorException.java,v $
 * Revision 1.2  2009/06/17 05:56:10  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/12 07:05:04  richie
 * no message
 *
 **************************************************************************/
package com.gxlu.meta.engine.jdbc;

import com.gxlu.meta.exception.ServiceException;

/**
 * Exception for SqlExecutor.
 *  
 * @author K
 */
public class SqlExecutorException extends ServiceException
{

    /**
     * @see java.lang.Exception#Exception(java.lang.String) 
     */
    public SqlExecutorException (String message)
    {
        super(message);
    }

    /**
     * @see java.lang.Exception#Exception(java.lang.String, java.lang.Throwable) 
     */
    public SqlExecutorException (String message, Throwable ex)
    {
        super("METAPLATFORM", -1, message, ex);
    }

    private static final long serialVersionUID = -5444189254235272223L;
}
