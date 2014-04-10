/**************************************************************************
 * $RCSfile: SqlQueryResult.java,v $  $Revision: 1.1 $  $Date: 2009/01/05 03:33:00 $
 *
 * $Log: SqlQueryResult.java,v $
 * Revision 1.1  2009/01/05 03:33:00  richie
 * change QueryResult name to SqlQueryResult
 *
 * Revision 1.1  2008/12/12 07:05:04  richie
 * no message
 *
 **************************************************************************/
package com.gxlu.meta.engine.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Query Result contains two part, one is a list of record.
 * another is total records count that match the where conditions. 
 * 
 * @author K
 */
public class SqlQueryResult
{

    /**
     * Default constructor.
     * 
     * @param total total records count that match the where conditions.
     * @param beans a list of records.
     */
    public SqlQueryResult(long total, List<Map<String, Object>> beans)
    {
        this.total = total;
        this.list = beans;
    }

    public List<Map<String, Object>> getList()
    {
        return list;
    }

    public void setList(List<Map<String, Object>> list)
    {
        this.list = list;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }


    private List<Map<String, Object>> list;
    private long total;
    public static SqlQueryResult  EMPTY_QUERYRESULT = new SqlQueryResult(0,new ArrayList<Map<String, Object>>());
}
