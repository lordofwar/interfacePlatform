/**************************************************************************
 * $RCSfile: RelationDescriptor.java,v $  $Revision: 1.2 $  $Date: 2008/12/18 01:57:21 $
 *
 * $Log: RelationDescriptor.java,v $
 * Revision 1.2  2008/12/18 01:57:21  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/12 07:05:04  richie
 * no message
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : MetaTableRelationDescriptor.java
 * Created on : Dec 11, 2008 9:42:15 AM
 * Creator : RichieJin
 */
package com.gxlu.meta.cfg.dbdescriptor;

/**
 * Relation table to connect MetaClass to MetaClass or MetaClass to 
 * Component or Component to MetaClass. 
 *
 * @author K
 */
public class RelationDescriptor {

  /**
   * another table' descriptor of a table relationship.
   *
   * @return
   */
  public TableDescriptor getOppositeTable() {
    return oppositeTable;
  }

  /**
   * set another table' descriptor of a table relationship.
   *
   * @param oppositeTable
   */
  public void setOppositeTable(TableDescriptor oppositeTable) {
    this.oppositeTable = oppositeTable;
  }

  /**
   * table name to persist table relationship.
   *
   * @return
   */
  public String getJoinTableName() {
    return joinTableName;
  }

  /**
   * set the table name which persists table relationship.
   *
   * @param joinTableName
   */
  public void setJoinTableName(String joinTableName) {
    this.joinTableName = joinTableName;
  }

  /**
   * column name to point host table in relation table.
   *
   * @return
   */
  public String getShelfColumn() {
    return shelfColumn;
  }

  /**
   * set column name to point host table in relation table.
   *
   * @param shelfColumn
   */
  public void setShelfColumn(String shelfColumn) {
    this.shelfColumn = shelfColumn;
  }

  public boolean isIndependentTable() {
    return isIndependentTable;
  }

  public void setIndependentTable(boolean isIndependentTable) {
    this.isIndependentTable = isIndependentTable;
  }

  /**
   * column name to point opposite table in relation table.
   *
   * @return
   */
  public String getOppositeColumn() {
    return oppositeColumn;
  }

  /**
   * set column name to point opposite table in relation table.
   *
   * @param oppositeColumn
   */
  public void setOppositeColumn(String oppositeColumn) {
    this.oppositeColumn = oppositeColumn;
  }

  
  private TableDescriptor oppositeTable;
  private String joinTableName;
  private String shelfColumn;
  private String oppositeColumn;
  private boolean isIndependentTable;
}
