/**************************************************************************
 * $RCSfile: Select.java,v $  $Revision: 1.4 $  $Date: 2009/02/26 06:16:46 $
 *
 * $Log: Select.java,v $
 * Revision 1.4  2009/02/26 06:16:46  richie
 * *** empty log message ***
 *
 * Revision 1.3  2009/01/04 01:21:04  richie
 * *** empty log message ***
 *
 **************************************************************************/package com.gxlu.meta.engine.persister.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.hibernate.dialect.Dialect;

/**
 * @author K
 */
public class Select {

  public Select(Dialect dialect){
    this.dialect = dialect;
    joins = new ArrayList<Join>();
    conditions = new ArrayList<String>();
    params = new ArrayList<Object>();
    tableAlias = new HashMap<String, String>();
  }

  protected Dialect getDialect() {
    return dialect;
  }

  public void addCondition(String condition, Object[] values) {
    conditions.add(condition);
    if (values != null) {
      for (Object object : values) {
        params.add(object);
      }
    }
  }

  public void addJoinTable(String oppositeTableName, String oppositeKeyColumn, String joinTableName, String coreKey, String oppositeKey, List<String> columnNames) {
    joinSequence++;
    joins.add(new Join(joinSequence, oppositeTableName, oppositeKeyColumn, joinTableName, coreKey, oppositeKey, columnNames));
  }
  
  public void addJoinTable(String oppositeTableName, String oppositeKeyColumn, String joinTableName, String coreKey, String oppositeKey, String columnName) {
    joinSequence++;
    joins.add(new Join(joinSequence, oppositeTableName, oppositeKeyColumn, joinTableName, coreKey, oppositeKey, columnName));
  }

  public void setCoreTable(String coreTableName, String coreKeyColumn, List<String> coreColumnNames) {
    this.coreTableName = coreTableName;
    this.coreKeyColumn = coreKeyColumn;
    this.coreColumnNames = coreColumnNames;
    tableAlias.put(joinSequence + "_" + coreTableName, "CORET");
  }

  public Object[] toQueryParams() {
    return params.toArray();
  }

  public List<String> toOrderStatementString(Vector<String> orderColumns) {
    List<String> orders = new ArrayList<String>();
    for (String columnWithTable : orderColumns) {
      String[] column = columnWithTable.split(".");
      String tableName = column[0];
      String alias = tableAlias.get(tableName);
      orders.add(alias + "." + column[1]);
    }

    return orders;
  }

  public int getJoinSequence() {
    return joinSequence;
  }

  public void setJoinSequence(int joinSequence) {
    this.joinSequence = joinSequence;
  }

  public String getTableAlias(String tableName) {

    return tableAlias.get(tableName);
  }

  public String toQueryStatementString() {
    StringBuffer columnAlias = new StringBuffer();
    StringBuffer prefix = new StringBuffer();
    StringBuffer conditionBuffer = new StringBuffer();
    columnAlias.append("SELECT DISTINCT ");

    addColumnAlias("CORET", coreColumnNames, columnAlias, false);
    
    prefix.append(" FROM ");
    prefix.append(coreTableName);
    prefix.append(" CORET");
    
    if (joins.size() > 0) {
      int point = 0;
      for (Join join : joins) {
        point++;
        if (point > 1) {
          conditionBuffer.append(" AND ");
        }
        if (join.getJoinTableName() == coreTableName) {
          prefix.append(", ");
          prefix.append(join.oppositeTableName);
          prefix.append(" OPPT");
          prefix.append(point);
          tableAlias.put(join.getJoinSequence() + "_" + join.oppositeTableName, "OPPT" + point);

          conditionBuffer.append(" OPPT");
          conditionBuffer.append(point);
          conditionBuffer.append(".");
          conditionBuffer.append(join.oppositeKeyColumn);
          conditionBuffer.append(" (+) ");
          conditionBuffer.append("=");
          conditionBuffer.append(" CORET.");
          conditionBuffer.append(join.oppositeKey);
        }
        else if (join.getJoinTableName() == join.oppositeTableName) {
          prefix.append(" ,");
          prefix.append(join.oppositeTableName);
          prefix.append(" OPPT");
          prefix.append(point);
          tableAlias.put(join.getJoinSequence() + "_" + join.oppositeTableName, "OPPT" + point);
          
          conditionBuffer.append(" OPPT");
          conditionBuffer.append(point);
          conditionBuffer.append(".");
          conditionBuffer.append(join.coreKey);
          conditionBuffer.append(" (+) ");
          conditionBuffer.append("=");
          conditionBuffer.append(" CORET.");
          conditionBuffer.append(coreKeyColumn);
        }
        else{
          prefix.append(" ,");
          prefix.append(join.oppositeTableName);
          prefix.append(" OPPT");
          prefix.append(point);
          prefix.append(" ,");
          prefix.append(join.joinTableName);
          prefix.append(" JOINT");
          prefix.append(point);
          tableAlias.put(join.getJoinSequence() + "_" + join.oppositeTableName, "OPPT" + point);
          tableAlias.put(join.getJoinSequence() + "_" + join.joinTableName, "JOINT" + point);
          
          conditionBuffer.append(" OPPT");
          conditionBuffer.append(point);
          conditionBuffer.append(".");
          conditionBuffer.append(join.oppositeKeyColumn);
          conditionBuffer.append("=");
          conditionBuffer.append(" JOINT");
          conditionBuffer.append(point);
          conditionBuffer.append(".");
          conditionBuffer.append(join.oppositeKey);

          conditionBuffer.append(" AND");
          
          conditionBuffer.append(" CORET");
          conditionBuffer.append(".");
          conditionBuffer.append(coreKeyColumn);
          conditionBuffer.append("=");
          conditionBuffer.append(" JOINT");
          conditionBuffer.append(point);
          conditionBuffer.append(".");
          conditionBuffer.append(join.coreKey);
        }
        addColumnAlias("OPPT"+ point, join.columnNames, columnAlias, true);
      }
    }
    prefix.append(" ");

    for (String condtion : conditions) {
      if (conditionBuffer.length() > 0) {
        conditionBuffer.append(" AND ");
      }
      conditionBuffer.append(condtion);
    }

    if (conditionBuffer.length() > 0) {
      conditionBuffer.insert(0, "WHERE ");
    }

    String conditionString = conditionBuffer.toString();
    for (String tableName : tableAlias.keySet()) {
      String alias = tableAlias.get(tableName);
      conditionString = conditionString.replaceAll(tableName, alias);
    }

    return columnAlias.toString() + prefix.toString() + conditionString;
  }

  private boolean addColumnAlias(String tableName, List<String> columnNames, StringBuffer columnAlias, boolean needPrintComma) {
    for (String column : columnNames) {
      if (needPrintComma) {
        columnAlias.append(",");
      }
      needPrintComma = true;
      columnAlias.append(" ").append(tableName).append(".");
      columnAlias.append(column);
      columnAlias.append(" ").append(tableName).append("_");
      columnAlias.append(column);
    }
    return needPrintComma;
  }

  private Dialect dialect;
  private String coreTableName;
  private String coreKeyColumn;
  private List<Join> joins;
  private List<String> conditions;
  private List<Object> params;
  private Map<String, String> tableAlias;
  private List<String> coreColumnNames;
  private int joinSequence = 0;
  
  private class Join {
    
    public Join(int joinSequence, String oppositeTableName, String oppositeKeyColumn, String joinTableName, String coreKey, String oppositeKey, List<String> columnNames){
      this.oppositeTableName = oppositeTableName;
      this.oppositeKeyColumn = oppositeKeyColumn;
      this.joinTableName = joinTableName;
      this.coreKey = coreKey;
      this.oppositeKey = oppositeKey;
      this.columnNames = columnNames;
      this.joinSequence = joinSequence;
    }

    public Join(int joinSequence, String oppositeTableName, String oppositeKeyColumn, String joinTableName, String coreKey, String oppositeKey, String columnName){
      this.oppositeTableName = oppositeTableName;
      this.oppositeKeyColumn = oppositeKeyColumn;
      this.joinTableName = joinTableName;
      this.coreKey = coreKey;
      this.oppositeKey = oppositeKey;
      columnNames = new ArrayList<String>();
      columnNames.add(columnName);
      this.joinSequence = joinSequence;
    }

    public String getOppositeTableName() {
      return oppositeTableName;
    }

    public void setOppositeTableName(String oppositeTableName) {
      this.oppositeTableName = oppositeTableName;
    }

    public String getOppositeKeyColumn() {
      return oppositeKeyColumn;
    }

    public void setOppositeKeyColumn(String oppositeKeyColumn) {
      this.oppositeKeyColumn = oppositeKeyColumn;
    }

    public String getJoinTableName() {
      return joinTableName;
    }

    public void setJoinTableName(String joinTableName) {
      this.joinTableName = joinTableName;
    }

    public String getCoreKey() {
      return coreKey;
    }

    public void setCoreKey(String coreKey) {
      this.coreKey = coreKey;
    }

    public List<String> getColumnNames() {
      return columnNames;
    }

    public int getJoinSequence() {
      return joinSequence;
    }

    public void setJoinSequence(int joinSequence) {
      this.joinSequence = joinSequence;
    }

    public void setColumnNames(List<String> columnNames) {
      this.columnNames = columnNames;
    }

    public String getOppositeKey() {
      return oppositeKey;
    }

    public void setOppositeKey(String oppositeKey) {
      this.oppositeKey = oppositeKey;
    }

    private String oppositeTableName;
    private String oppositeKeyColumn;
    private String joinTableName;
    private String coreKey;
    private String oppositeKey;
    private List<String> columnNames;
    private int joinSequence;
  }
}
