package com.gxlu.meta.engine.jdbc;

import java.util.Date;

public class QueryBuilder
{
	/**
	 * 
	 * @param column
	 * @param value  is nullable
	 * @param operator
	 * @param link
	 * @return
	 */
    public String addSqlCondition(String column, String value, String operator, String link)
    {
        String condition;
        if (value == null || value.trim().equals(""))
        {
            condition = "";
        }
        else
        {
            
            StringBuffer buffer = new StringBuffer();
            buffer.append(" ");
            buffer.append(operator);
            buffer.append(" '");
            if (operator == LIKE)
            {
                buffer.append("%");
                buffer.append(value);
                buffer.append("%");  
            }
            else
            {
                buffer.append(value);
            }
            buffer.append("' ");
            
            condition = assembleSqlCondition(column, buffer.toString(), link);
        }

        return condition;
    }
    
    /***@param isNeedComma:ÊÇ·ñÐèÒª×Ö·û´®*/
    public String addSqlCondition(String column, String value, String operator, String link, boolean isNeedComma)
    {
        String condition;
        if (value == null || value.trim().equals(""))
        {
            condition = "";
        }
        else
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append(" ");
            buffer.append(operator);
            if (isNeedComma)
            {
                buffer.append(" '");    
            }
            
            if (operator == LIKE)
            {
                buffer.append("%");
                buffer.append(value);
                buffer.append("%");  
            }
            else
            {
                buffer.append(value);
            }
            if (isNeedComma)
            {
                buffer.append("' ");
            }
            condition = assembleSqlCondition(column, buffer.toString(), link);
        }

        return condition;
    }

    public String addSqlCondition(String column, Integer value, String operator, String link)
    {
        String condition;
        if (value == null)
        {
            condition = "";
        }
        else
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append(operator);
            buffer.append(value.intValue());
            condition = assembleSqlCondition(column, buffer.toString(), link);
        }

        return condition;
    }
    
    public String addSqlCondition(String column, Byte value, String operator, String link)
    {
        String condition;
        if (value == null)
        {
            condition = "";
        }
        else
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append(operator);
            buffer.append(value.byteValue());
            condition = assembleSqlCondition(column, buffer.toString(), link);
        }

        return condition;
    }

    public String addSqlCondition(String column, Long value, String operator, String link)
    {
        String condition;
        if (value == null)
        {
            condition = "";
        }
        else
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append(operator);
            buffer.append(value.longValue());
            condition = assembleSqlCondition(column, buffer.toString(), link);
        }

        return condition;
    }

    public String addSqlCondition(String column, Double value, String operator, String link)
    {
        String condition;
        if (value == null)
        {
            condition = "";
        }
        else
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append(operator);
            buffer.append(value.doubleValue());
            condition = assembleSqlCondition(column, buffer.toString(), link);
        }

        return condition;
    }

    public String addSqlCondition(String column, Float value, String operator, String link)
    {
        String condition;
        if (value == null)
        {
            condition = "";
            
            
        }
        else
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append(operator);
            buffer.append(value.floatValue());
            condition = assembleSqlCondition(column, buffer.toString(), link);
        }

        return condition;
    }

    public String addSqlCondition(String column, Date value, String operator, String link)
    {
        String condition;
        if (value == null)
        {
            condition = "";
        }
        else
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append(operator);
            buffer.append(value);
            condition = assembleSqlCondition(column, buffer.toString(), link);
        }

        return condition;
    }

    private String assembleSqlCondition(String column, String valueWithOpertator, String link)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" ");
        buffer.append(link);
        buffer.append(" ");
        buffer.append(column);
        buffer.append(valueWithOpertator);
        buffer.append(" ");

        return buffer.toString();
    }
    
    public static final String EQUALS = "=";
    public static final String NOT_EQUALS = "<>";
    public static final String LIKE = "LIKE";
    public static final String AND="AND";
    public static final String OR="OR";
    public static final String MORETHAN = ">=";
    public static final String LESSTHAN = "<=";
}
