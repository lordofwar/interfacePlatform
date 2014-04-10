/**************************************************************************
 * $RCSfile: MetaAttributeObjectJsonUtil.java,v $  $Revision: 1.1 $  $Date: 2010/05/28 08:54:28 $
 *
 * $Log: MetaAttributeObjectJsonUtil.java,v $
 * Revision 1.1  2010/05/28 08:54:28  agazeng
 * 图形展示显示中文名称
 *
 **************************************************************************/
 
package com.gxlu.meta.tools;

import net.sf.json.JSONArray;

import com.gxlu.meta.cfg.MetaAttributeObject;
 
/**
 * <p>Title: MetaAttributeObjectUtil.java </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: GXLU </p>
 * @author ZengXiangGuo
 * @see 
 * @version 1.0
 */
public class MetaAttributeObjectJsonUtil {
	
	  public static String toJSONString(MetaAttributeObject mo) {
		    return  JSONArray.fromObject(mo).toString();
		  }
}
