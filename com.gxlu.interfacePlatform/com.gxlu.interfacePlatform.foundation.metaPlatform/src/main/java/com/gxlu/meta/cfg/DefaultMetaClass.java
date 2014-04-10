/**************************************************************************
 * $RCSfile: DefaultMetaClass.java,v $  $Revision: 1.12 $  $Date: 2010/04/07 05:21:30 $
 *
 * $Log: DefaultMetaClass.java,v $
 * Revision 1.12  2010/04/07 05:21:30  richie
 * *** empty log message ***
 *
 * Revision 1.11  2009/08/09 08:06:01  liuding
 * *** empty log message ***
 *
 * Revision 1.10  2009/07/30 00:53:23  richie
 * *** empty log message ***
 *
 * Revision 1.9  2009/07/29 09:10:42  richie
 * *** empty log message ***
 *
 * Revision 1.8  2009/07/29 08:20:56  richie
 * *** empty log message ***
 *
 * Revision 1.7  2009/07/07 12:58:27  liuding
 * *** empty log message ***
 *
 * Revision 1.6  2009/03/23 01:31:01  richie
 * *** empty log message ***
 *
 * Revision 1.5  2009/02/27 02:02:00  richie
 * *** empty log message ***
 *
 * Revision 1.4  2009/02/26 06:16:46  richie
 * *** empty log message ***
 *
 * Revision 1.3  2008/12/31 04:44:42  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/26 05:55:24  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/18 09:24:13  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/17 05:50:01  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gxlu.meta.MetaAttribute;
import com.gxlu.meta.MetaClass;
import com.gxlu.meta.MetaClassRelation;
import com.gxlu.meta.MetaComponentRelation;
import com.gxlu.meta.MetaObject;

/**
 * @author K
 */
public class DefaultMetaClass implements MetaClass {

	
  public List<MetaAttribute> getMetaAttributes() {
    return metaAttributes;
  }

  public MetaAttribute findMetaAttribute(String key) {
    if (metaAttributesMap == null) {
      metaAttributesMap = new HashMap<String, MetaAttribute>();
      for (MetaAttribute attribute : metaAttributes) {
        metaAttributesMap.put(attribute.getAttributeName(), attribute);
      }
    }

    return metaAttributesMap.get(key);
  }

  public String getClassName() {
    
    return className;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getVersionInstanceId() {
    return versionInstanceId;
  }

  public void setVersionInstanceId(long versionInstanceId) {
    this.versionInstanceId = versionInstanceId;
  }

  public String getCode() {
   
    return code;
  }

  public String getDisplayName() {

    return displayName;
  }

  public List<MetaClassRelation> getMetaClassRelations() {

    return metaClassRelations;
  }

  
  public List<MetaClassRelation> getMetaHiddenClassRelations() {

    return metaHiddenClassRelations;
  }

  public void setMetaHiddenClassRelations(List<MetaClassRelation> metaHiddenClassRelations) {
    this.metaHiddenClassRelations = metaHiddenClassRelations;
  }

  public List<MetaComponentRelation> getMetaComponentRelations() {

    return metaComponentRelations;
  }

  public String getParentClassName() {

    return parentClassName;
  }

  public MetaClass getParentMetaClass() {

    return parentMetaClass;
  }

  public void setParentClassName(String parentClassName) {
    this.parentClassName = parentClassName;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public void setParentMetaClass(MetaClass parentMetaClass) {
    this.parentMetaClass = parentMetaClass;
  }

  public List<MetaClass> getChildren() {
    return children;
  }

  public void addChild(MetaClass child) {
    if (children == null) {
      children = new ArrayList<MetaClass>();
    }
    children.add(child);
  }

  public boolean isInstance(MetaObject mo) {
    
    return checkInstance(mo.getMetaClass());
  }

  private boolean checkInstance(MetaClass metaClass) {
    if (metaClass.getClassName().equals(this.className)) {
      return true;
    }
    else if (metaClass.getParentMetaClass() != null){
      return checkInstance(metaClass.getParentMetaClass());
    }
    else {
      return false;
    }
  }

  public void setMetaAttributes(List<MetaAttribute> metaAttributes) {
    this.metaAttributes = metaAttributes;
  }

  public void setMetaComponentRelations(List<MetaComponentRelation> metaComponentRelations) {
    this.metaComponentRelations = metaComponentRelations;
  }

  public void setMetaClassRelations(List<MetaClassRelation> metaClassRelations) {
    this.metaClassRelations = metaClassRelations;
  }

  public MetaComponentRelation findMetaComponentRelation(String componentName) {
    if (metaComponentRelationMap == null) {
      metaComponentRelationMap = new HashMap<String, MetaComponentRelation>();
      for (MetaComponentRelation relation : metaComponentRelations) {
        metaComponentRelationMap.put(relation.getRelationMetaComponent().getCode(), relation);
      }
    }
    
    return metaComponentRelationMap.get(componentName);
  }
  
  public MetaClassRelation findMetaClassRelation(String alias) {
    if (metaClassRelationMap == null) {
      metaClassRelationMap = new HashMap<String, MetaClassRelation>();
      for (MetaClassRelation relation : metaClassRelations) {
        metaClassRelationMap.put(relation.getAlias(), relation);
      }
    }
    
    MetaClassRelation relation =  metaClassRelationMap.get(alias);
    if (relation == null && getChildren() != null) {
      for (MetaClass subClass : getChildren()) {
        relation = subClass.findMetaClassRelation(alias);
        if (relation == null) {
          relation = ((DefaultMetaClass) subClass).findHiddenMetaClassRelation(alias);
        }
        if (relation != null) {
          break;
        }
      }
    }

    return relation;
  }
  
  public MetaClassRelation findHiddenMetaClassRelation(String alias) {
    if (metaHiddenClassRelationMap == null) {
      metaHiddenClassRelationMap = new HashMap<String, MetaClassRelation>();
      for (MetaClassRelation relation : metaHiddenClassRelations) {
        metaHiddenClassRelationMap.put(relation.getAlias(), relation);
      }
    }
    
    MetaClassRelation relation =  metaHiddenClassRelationMap.get(alias);
    if (relation == null && getChildren() != null) {
      for (MetaClass subClass : getChildren()) {
        relation = ((DefaultMetaClass) subClass).findHiddenMetaClassRelation(alias);
        if (relation == null) {
          relation = subClass.findMetaClassRelation(alias);
        }
        if (relation != null) {
          break;
        }
      }
    }

    return relation;
  }
  	public MetaAttribute getPrimitiveAttribute() {
		return primitiveAttribute;
	}

	public void setPrimitiveAttribute(MetaAttribute primitiveAttribute) {
		this.primitiveAttribute = primitiveAttribute;
	}
	@Override
	public String toString() {
		StringBuffer toString = new StringBuffer();
		if(className!=null){
			toString.append(className);
			toString.append(" | ");
		}
		if(displayName!=null){
			toString.append(displayName);
		}
		return toString.toString();
	}
  private long id;
  private long versionInstanceId;
  private List<MetaAttribute> metaAttributes;
  private MetaAttribute primitiveAttribute;
  private List<MetaComponentRelation> metaComponentRelations;
  private List<MetaClassRelation> metaClassRelations;
  private List<MetaClassRelation> metaHiddenClassRelations;
  private String parentClassName;
  private String className;
  private String code;
  private String displayName;
  private MetaClass parentMetaClass;
  private List<MetaClass> children;
  private Map<String, MetaAttribute> metaAttributesMap;
  private Map<String, MetaClassRelation> metaClassRelationMap;
  private Map<String, MetaClassRelation> metaHiddenClassRelationMap;
  private Map<String, MetaComponentRelation> metaComponentRelationMap;

}

