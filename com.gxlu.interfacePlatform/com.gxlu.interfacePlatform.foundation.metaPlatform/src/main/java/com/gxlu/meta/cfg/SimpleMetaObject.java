package com.gxlu.meta.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gxlu.meta.MetaAttribute;
import com.gxlu.meta.MetaClass;
import com.gxlu.meta.MetaClassRelation;
import com.gxlu.meta.MetaComponentObject;
import com.gxlu.meta.MetaComponentRelation;
import com.gxlu.meta.MetaObject;

public class  SimpleMetaObject extends SimpleMetaData implements MetaObject {

  public SimpleMetaObject() {
    componentsData = new HashMap<String, MetaComponentObject>();
    relationsData = new HashMap<String, List<MetaObject>>();
  }

  public static SimpleMetaObject fromDefaultMetaObject(MetaObject metaObject) {
    DefaultMetaObject defautMetaObject = (DefaultMetaObject) metaObject;
    SimpleMetaObject simple = new SimpleMetaObject();
    simple.setMaps(defautMetaObject.getMaps());
    simple.setMetaClassName(defautMetaObject.getMetaClass().getClassName());

    Map<MetaComponentRelation, MetaComponentObject> components = defautMetaObject.getComponents();
    for (MetaComponentObject component : components.values()) {
      SimpleMetaComponentObject simpleComponentObject = new SimpleMetaComponentObject();
      simpleComponentObject.setMaps(((DefaultMetaData) component).getMaps());

      simple.componentsData.put(component.getMetaComponent().getCode(), simpleComponentObject);
    }

    Map<MetaClassRelation, List<MetaObject>> relations = defautMetaObject.getRelations();
    for (MetaClassRelation relation: relations.keySet()) {
      List<MetaObject> objects = relations.get(relation);
      
      List<MetaObject> oneObjects = new ArrayList<MetaObject>();
      for (MetaObject object : objects) {
        SimpleMetaObject simpleOne = SimpleMetaObject.fromDefaultMetaObject((DefaultMetaObject) object);
//        simpleOne.setMaps(((DefaultMetaObject) object).getMaps());
        oneObjects.add(simpleOne);
      }
      simple.relationsData.put(relation.getAlias(), oneObjects);
    }

    return simple;
  }

  public MetaComponentObject getRelateComponentObject(MetaComponentRelation metaComponentRelation) {
   
    return componentsData.get(metaComponentRelation.getAlias());
  }

  public MetaComponentObject createRelateComponentObject(MetaComponentRelation metaComponentRelation) {
    return new SimpleMetaComponentObject();
  }

  public MetaComponentObject createRelateComponentObject(String componentCode) {
    return  new SimpleMetaComponentObject();
  }

  public List<MetaObject> getRelateObject(MetaClassRelation metaClassRelation) {
    return (List<MetaObject>) relationsData.get(metaClassRelation.getAlias());
  }

  public MetaComponentObject getRelateComponentObject(String componentCode) {
    
    return componentsData.get(componentCode);
  }

  public List<MetaObject> getRelateObject(String alias) {
    return (List<MetaObject>) relationsData.get(alias);
  }
  public Map<String, MetaComponentObject> getComponentsData() {
    return componentsData;
  }

  public void setComponentsData(Map<String, MetaComponentObject> componentsData) {
    this.componentsData = componentsData;
  }

  public Map<String, List<MetaObject>> getRelationsData() {
    return relationsData;
  }

  public void setRelationsData(Map<String, List<MetaObject>> relationsData) {
    this.relationsData = relationsData;
  }

  public void addFech(String fech) {
    // TODO Auto-generated method stub
    
  }

  public void removeFech(String fech) {
    // TODO Auto-generated method stub
    
  }

  public void fromJavaBean(Object javaBean) {
    // TODO Auto-generated method stub
    
  }

  public String getMetaClassName() {
    return metaClassName;
  }

  public void setMetaClassName(String metaClassName) {
    this.metaClassName = metaClassName;
  }

  public List<MetaAttribute> getAttributeList() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setComponentObject(MetaComponentObject metaComponentObject, String metaComponentRelationCode) {
    // TODO Auto-generated method stub
    
  }

  public void setRelateObject(List<MetaObject> metaObjects, String metaClassRelationCode) {
    // TODO Auto-generated method stub
    
  }

  public String [] getFeches() {
    // TODO Auto-generated method stub
    return null;
  }

  public MetaClass getMetaClass() {
    // TODO Auto-generated method stub
    return null;
  }

  public byte getPersistType() {
    // TODO Auto-generated method stub
    return 0;
  }

  public void setComponentObject(MetaComponentObject metaComponentObject, MetaComponentRelation metaComponentRelation) {
    // TODO Auto-generated method stub
    
  }

  public void setPersistType(byte persistType) {
    // TODO Auto-generated method stub
    
  }

  public void setRelateObject(List<MetaObject> metaObjects, MetaClassRelation metaClassRelation) {
    // TODO Auto-generated method stub
    
  }

  public Object toJavaBean() {
    // TODO Auto-generated method stub
    return null;
  }

  public Object toJavaBean(Object javaBean) {
    // TODO Auto-generated method stub
    return null;
  }


  private Map<String, MetaComponentObject> componentsData;
  private Map<String, List<MetaObject>> relationsData;
  private String metaClassName;
}
