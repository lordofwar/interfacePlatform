/**************************************************************************
 * $RCSfile: DefaultMetaComponentObject.java,v $  $Revision: 1.3 $  $Date: 2009/05/21 07:33:50 $
 *
 * $Log: DefaultMetaComponentObject.java,v $
 * Revision 1.3  2009/05/21 07:33:50  liuding
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/31 04:44:41  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/26 05:55:23  richie
 * *** empty log message ***
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : DefaultMetaComponentObject.java
 * Created on : Dec 25, 2008 7:43:57 PM
 * Creator : RichieJin
 */
package com.gxlu.meta.cfg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gxlu.meta.MetaAttribute;
import com.gxlu.meta.MetaComponent;
import com.gxlu.meta.MetaComponentObject;

/**
 * @author RichieJin
 */
public class DefaultMetaComponentObject extends DefaultMetaData implements MetaComponentObject, Cloneable {

	private static final long serialVersionUID = 4993724155556971250L;

	public DefaultMetaComponentObject(MetaComponent metaComponent) {
		super();
		this.metaComponent = metaComponent;
	}

	@Override
	public List<MetaAttribute> getAttributeList() {

		return metaComponent.getMetaAttributes();
	}

	public MetaComponent getMetaComponent() {
		return this.metaComponent;
	}

	private MetaComponent metaComponent;

	public Object clone() throws CloneNotSupportedException {
		DefaultMetaComponentObject clone = (DefaultMetaComponentObject) super.clone();

		Map<String, Object> mapsClone = null;
		if (getMaps() != null) {
			mapsClone = new HashMap<String, Object>();
			mapsClone.putAll(getMaps());
		}
		clone.setMaps(mapsClone);
		return clone;
	}
}
