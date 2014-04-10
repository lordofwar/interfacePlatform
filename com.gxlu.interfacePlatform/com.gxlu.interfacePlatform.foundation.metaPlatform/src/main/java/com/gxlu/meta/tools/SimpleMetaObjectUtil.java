package com.gxlu.meta.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gxlu.meta.MetaClass;
import com.gxlu.meta.MetaClassRelation;
import com.gxlu.meta.MetaComponentObject;
import com.gxlu.meta.MetaComponentRelation;
import com.gxlu.meta.MetaObject;
import com.gxlu.meta.cfg.DefaultMetaComponentObject;
import com.gxlu.meta.cfg.DefaultMetaObject;
import com.gxlu.meta.cfg.SimpleMetaComponentObject;
import com.gxlu.meta.cfg.SimpleMetaObject;

public class SimpleMetaObjectUtil {

  public static List<MetaObject> fromDefaultMetaObject(List<MetaObject> houseList) {
    List<MetaObject> simples = new ArrayList<MetaObject>();
    for (MetaObject object : houseList) {
      SimpleMetaObject metaObject = SimpleMetaObject.fromDefaultMetaObject((DefaultMetaObject) object);
      simples.add(metaObject);
    }
    
    return simples;
  }

  public static List<MetaObject> toDefaultMetaObjectList(List<SimpleMetaObject> lists, MetaClass metaClass) {
    List<MetaObject> results = new ArrayList<MetaObject>();
    for (MetaObject simpleMetaObject : lists) {
      results.add(SimpleMetaObjectUtil.toDefaultMetaObject((SimpleMetaObject) simpleMetaObject, metaClass));
    }

    return results;
  }

  public static MetaObject toDefaultMetaObject(SimpleMetaObject simpleMetaObject, MetaClass metaClass) {
    DefaultMetaObject defaultMetaObject = new DefaultMetaObject(metaClass);
    for (String keyName : simpleMetaObject.getMaps().keySet()) {
      defaultMetaObject.setValue(keyName, simpleMetaObject.getValue(keyName));
    }
    
    for (String componentCode : simpleMetaObject.getComponentsData().keySet()) {
      if (defaultMetaObject.getComponents() == null) {
        Map<MetaComponentRelation, MetaComponentObject> componentmaps = new HashMap<MetaComponentRelation, MetaComponentObject>();
        defaultMetaObject.setComponents(componentmaps);
      }
      MetaComponentRelation componentRelation = metaClass.findMetaComponentRelation(componentCode);
      DefaultMetaComponentObject defaultMetaComponentObject = new DefaultMetaComponentObject(componentRelation.getRelationMetaComponent());
      defaultMetaObject.setComponentObject(defaultMetaComponentObject, componentRelation);
      for (String keyName : ((SimpleMetaComponentObject) simpleMetaObject.getRelateComponentObject(componentCode)).getMaps().keySet()) {
        defaultMetaComponentObject.setValue(keyName, simpleMetaObject.getValue(keyName));
      }
    }
    
    for (String relationCode : simpleMetaObject.getRelationsData().keySet()) {
      if (defaultMetaObject.getRelations() == null) {
        Map<MetaClassRelation, List<MetaObject>> relations = new HashMap<MetaClassRelation, List<MetaObject>>();
        defaultMetaObject.setRelations(relations);
      }

      MetaClassRelation relation = metaClass.findMetaClassRelation(relationCode);
      List<MetaObject> relationObjects = new ArrayList<MetaObject>();
      defaultMetaObject.getRelations().put(relation, relationObjects);
      for (MetaObject oneObject : simpleMetaObject.getRelateObject(relationCode)) {
        relationObjects.add(SimpleMetaObjectUtil.toDefaultMetaObject((SimpleMetaObject) oneObject, relation.getRelationMetaClass()));
      }
    }

    return defaultMetaObject;
  }
}
