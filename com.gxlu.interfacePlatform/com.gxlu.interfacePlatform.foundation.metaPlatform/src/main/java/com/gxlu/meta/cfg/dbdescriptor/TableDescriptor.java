/**************************************************************************
 * $RCSfile: TableDescriptor.java,v $  $Revision: 1.3 $  $Date: 2008/12/18 09:05:04 $
 *
 * $Log: TableDescriptor.java,v $
 * Revision 1.3  2008/12/18 09:05:04  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/18 03:24:45  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/12 07:05:04  richie
 * no message
 *
 **************************************************************************/
package com.gxlu.meta.cfg.dbdescriptor;

import java.util.List;

/**
 * Describe a table information for part of a MetaClass, include table belong 
 * to MetaClass and its properties, a table should persist part of a meta 
 * object's data. it also represents its relation map to other table, but it is
 * not designed to describe the connection between MetaClass and MetaClass or 
 * MetaClass to Component.
 *
 * @author K
 */
public class TableDescriptor {

  /**
   * retrieve table's name in database.
   *
   * @return table table's name in database
   */
  public String getTable() {
    return table;
  }

  /**
   * set this table's name in database.
   *
   * @param table table's name in database
   */
  public void setTable(String table) {
    this.table = table;
  }

  /**
   * retrieve table id sequence generator in Oracle database.
   *
   * @return table id sequence generator in Oracle database
   */
  public String getSequence() {
    return sequence;
  }

  /**
   * set table id sequence generator in Oracle database.
   *
   * @param sequence table id sequence generator in Oracle database
   */
  public void setSequence(String sequence) {
    this.sequence = sequence;
  }

  /**
   * get version control column name of this table, if version property 
   * set to null, it means this table is not be version controlled.
   *
   * @return version control column name
   */
  public PropertyDescriptor getVersionProperty() {
    return versionProperty;
  }

  /**
   * set version control column name.
   *
   * @param version version control column name
   */
  public void setVersionProperty(PropertyDescriptor versionProperty) {
    this.versionProperty = versionProperty;
  }

  /**
   * retrieve all defined properties in this table and used in a MetaClass.
   *
   * @return properties in this table
   */
  public List<PropertyDescriptor> getMetaProerties() {
    return metaProerties;
  }

  /**
   * set all defined properties in this table and used in a MetaClass.
   *
   * @param metaProerties properties in this table
   */
  public void setMetaProerties(List<PropertyDescriptor> metaProerties) {
    this.metaProerties = metaProerties;
  }

  /**
   * retrieve all plain relation descriptors, plain relation means this relation is not represent 
   * MetaClass to MetaClass or MetaClass to Component. it always represents the relationship 
   * between major table and attached table.
   *
   * @return all plain relation descriptors
   */
  public List<RelationDescriptor> getPlainTableRelations() {
    return plainTableRelations;
  }

  /**
   * set all plain relation descriptors.
   *
   * @param plainTableRelations all plain relation descriptors
   */
  public void setPlainTableRelations(List<RelationDescriptor> plainTableRelations) {
    this.plainTableRelations = plainTableRelations;
  }

  public PropertyDescriptor getPrimitiveProperty() {
    return primitiveProperty;
  }

  public void setPrimitiveProperty(PropertyDescriptor primitiveProperty) {
    this.primitiveProperty = primitiveProperty;
  }

  private String table;
  private String sequence;
  private PropertyDescriptor versionProperty;
  private PropertyDescriptor primitiveProperty;
  private List<PropertyDescriptor> metaProerties;
  private List<RelationDescriptor> plainTableRelations;
}
