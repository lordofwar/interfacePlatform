package com.gxlu.meta.tools;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gxlu.meta.MetaComponentObject;
import com.gxlu.meta.MetaObject;
import com.gxlu.meta.cfg.SimpleMetaComponentObject;
import com.gxlu.meta.cfg.SimpleMetaObject;
import com.gxlu.meta.i18n.I18N;

public class SimpleMetaObjectJsonUtil {

  public static String toJSONString(List<MetaObject> simpleList) {
    return  JSONArray.fromCollection(simpleList).toString();
  }

  //add by wujun 2011-04-22begin
  public static String toMyDeviceString(List<MetaObject> simpleList)
  {
	  String result = null;
	    if(simpleList!=null && simpleList.size()>0)
	    {
	    	MetaObject mo = simpleList.get(0);
	    	if(mo!=null)
	    	{
	    		result = "ID"+mo.getValue("ID")+"次MC"+mo.getValue("METACATEGORY")+"次NAME"+mo.getValue("NAME")+"次BC"+mo.getValue("BELONGSPECIALITY").toString();
	    	}
	    }
	    return result;
  }
  public static String toMyString(List<MetaObject> simpleList) {
	    String result = null;
	    if(simpleList!=null && simpleList.size()>0)
	    {
	    	for(MetaObject mo : simpleList)
	    	{
	    		if(mo!=null)
	    		{
	    			String id ="";
	    			String name ="";
	    			String aportid="";
	    			String zportid="";
	    			if(mo.getValue("ID")!=null)
	    			{
	    				id = mo.getValue("ID").toString();
	    			}
	    			if(mo.getValue("NAME")!=null)
	    			{
	    				name = mo.getValue("NAME").toString();
	    			}
	    			if(mo.getValue("APORTID")!=null)
	    			{
	    				aportid = mo.getValue("APORTID").toString();
	    			}
	    			if(mo.getValue("ZPORTID")!=null)
	    			{
	    				zportid = mo.getValue("ZPORTID").toString();
	    			}
	    			if(result==null)
	    	    	{
	    				result ="I"+id+"次C"+name+"次A"+aportid+"次Z"+zportid+"朵";
	    	    	}
	    			else
	    			{
	    				result =result+"I"+id+"次C"+name+"次A"+aportid+"次Z"+zportid+"朵";
	    			}
	    		}
	    	}
	    	
	    }
	    if(result!=null)
	    {
	    	result=result.substring(0,result.lastIndexOf("朵"));
	    }
	    return result;
	  }
  public static  List<Map<Object,Object>> convertFromMyString(String myString) {
	    List<Map<Object,Object>> metaObjects = new ArrayList<Map<Object,Object>>();
	    if(myString != null && !myString.equals("")) {
	    	String [] result = myString.split("朵");
	    	if(result!=null && result.length>0)
	    	{
	    	   for(int i =0;i<result.length;i++)
	    	   {
	    		   String str = result[i];
	    		   String id ="";
	    		   String name ="";
	    		   String aportid="";
	    		   String zportid="";
	    		   if(str!=null)
	    		   {
	    			   String [] inner = str.split("次");
	    			   if(inner!=null && inner.length>0)
	    			   {
	    				   if(inner[0]!=null)
	    				   {
	    					   id = inner[0].substring(1);
	    				   }
	    				   if(inner[1]!=null)
	    				   {
	    					   name = inner[1].substring(1);
	    				   }
	    				   if(inner[2]!=null)
	    				   {
	    					   aportid = inner[2].substring(1);
	    				   }
	    				   if(inner[3]!=null)
	    				   {
	    					   zportid = inner[3].substring(1);
	    				   }
	    			   }
	    		   }
	    		   Map<Object,Object> mapValue = new HashMap<Object,Object>();
	    		   mapValue.put("ID", id);
	    		   mapValue.put("NAME", name);
	    		   mapValue.put("APORTID", aportid);
	    		   mapValue.put("ZPORTID", zportid);
	 	           metaObjects.add(mapValue);
	    	   }
	    	}
	    }
	    return metaObjects;
	  }
  //add by wujun end
  public static List<MetaObject> toList(JSONArray jsonArray) {
    List<MetaObject> result = new ArrayList<MetaObject>();
    
    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      JSONObject maps = (JSONObject) jsonObject.get("maps");
      JSONObject relationsData = (JSONObject) jsonObject.get("relationsData");
      JSONObject componentsData = (JSONObject) jsonObject.get("componentsData");
      SimpleMetaObject metaObject = new SimpleMetaObject();
      metaObject.setMaps((Map) JSONObject.toBean(maps, HashMap.class));
//      metaObject.setRelationsData(relationsData);
      if (componentsData != null && componentsData.length() > 0) {
        metaObject.setComponentsData(toComponentMap(componentsData));  
      }
      if (relationsData != null && relationsData.length() > 0) {
        metaObject.setRelationsData(toObjectMap(relationsData));
      }
      
      result.add(metaObject);
    }

    return result;
  }

  public static MetaObject toMetaObject(String string) {
    JSONObject jsonObject = JSONObject.fromString(MetaObjectStringConvertor.txmlToXml(string));
    
    JSONObject maps = (JSONObject)jsonObject.get("maps");
    JSONObject relationsData = (JSONObject)jsonObject.get("relationsData");
    JSONObject componentsData = (JSONObject)jsonObject.get("componentsData");
    SimpleMetaObject metaObject = new SimpleMetaObject();
    metaObject.setMaps((Map) JSONObject.toBean(maps, HashMap.class));
//    metaObject.setRelationsData(relationsData);
    if (componentsData != null && componentsData.length() > 0) {
      metaObject.setComponentsData(toComponentMap(componentsData));  
    }
    if (relationsData != null && relationsData.length() > 0) {
      metaObject.setRelationsData(toObjectMap(relationsData));
    }

    return metaObject;
  }

  public static Map<String, List<MetaObject>> toObjectMap(JSONObject jsonMap) {
    Map<String, List<MetaObject>> result = new HashMap<String, List<MetaObject>>();
    Iterator iterator = jsonMap.keys();
    while (iterator.hasNext()) {
      String relationName = (String) iterator.next();
      
      JSONArray array = (JSONArray) jsonMap.get(relationName);
      
      result.put(relationName, toList(array));
    }

    return result;
  }

  public static Map<String, MetaComponentObject> toComponentMap(JSONObject jsonMap) {
    Map<String, MetaComponentObject> result = new HashMap<String, MetaComponentObject>();
    Iterator iterator = jsonMap.keys();
    while (iterator.hasNext()) {
      String componentName = (String) iterator.next();
      
      SimpleMetaComponentObject component = new SimpleMetaComponentObject();
      JSONObject componentData = jsonMap.getJSONObject(componentName);
      if (componentData != null) {
        JSONObject maps = componentData.getJSONObject("maps");
        component.setMaps((Map) JSONObject.toBean(maps, HashMap.class));
      
        result.put(componentName, component);
      }
    }

    return result;
  }

  public static void main(String[] args) {
    String name = "[{#maps#:{#GRIDY#:null,#GRIDZ#:null,#OLDNAME#:null,#PARENTID#:41,#LOAD#:null,#REGISTRATIONNO#:null,#STRUCTURETYPE#:null,#STATUS#:null,#FOUNDATIONAREA#:null,#ALIAS#:null,#LOCATIONID#:null,#COMMENTS#:null,#STRUCTURETYPEID#:null,#USEPURPOSETYPE#:null,#USELIFE#:null,#ISSUEDATE#:null,#ORIENTATION#:null,#AREAID#:null,#FINISHDATE#:null,#FLOORNUM#:null,#NAMEABBRPY#:null,#DISPNAME#:null,#COORDINATESYSTEM#:null,#SAFELEVEL#:null,#VERSION#:0,#NAMEABBRCN#:null,#LATESTMAINTAINDATE#:null,#DISPWIDTH#:null,#ADDRESSID#:null,#LATITUDE#:null,#ID#:42,#ESTATEPIC#:null,#MODIFYDATE#:null,#LENGTH#:null,#ENTITYID#:42,#VERTICALFACEPIC#:null,#ESTATENO#:null,#CATEGORY#:#com.gxlu.ngrm.area.Building#,#DISPHEIGHT#:null,#GROUPCODE#:1,#UNIT#:null,#LONGITUDE#:null,#BUILDINGAREA#:null,#DISPLENGTH#:null,#ATTACHMENT#:null,#LOCALADDRESS#:null,#CREATEDATE#:null,#HEIGHT#:null,#CODE#:#B1#,#NAME#:#"+ I18N.getString ("FirstFloor") +"#,#GRIDX#:null,#WIDTH#:null},#persistType#:0,#feches#:[],#attributeList#:[],#metaClass#:null,#componentsData#:{},#relationsData#:{}}]";
    System.out.println(SimpleMetaObjectJsonUtil.toList(JSONArray.fromString(MetaObjectStringConvertor.txmlToXml(name))).size());
  }
}
