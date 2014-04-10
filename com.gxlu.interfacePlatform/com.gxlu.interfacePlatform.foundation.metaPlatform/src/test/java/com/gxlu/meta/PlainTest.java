/**************************************************************************
 * $RCSfile: PlainTest.java,v $  $Revision: 1.1 $  $Date: 2008/12/24 07:05:06 $
 *
 * $Log: PlainTest.java,v $
 * Revision 1.1  2008/12/24 07:05:06  richie
 * *** empty log message ***
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : PlainTest.java
 * Created on : Dec 24, 2008 2:58:05 PM
 * Creator : RichieJin
 */
package com.gxlu.meta;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.gxlu.meta.cfg.Configuration;
import com.gxlu.meta.tools.XmlReaderException;

/**
 * <pre>
 * Description : TODO
 * @author RichieJin
 * </pre>
 */
@Ignore
public class PlainTest {

	@Test
	public void test() throws XmlReaderException {
		MetaDBManager metaClassManager = (new Configuration("conf/meta.cfg.xml"))
				.getMetaDBManager();
		MetaClass metaClass = metaClassManager
				.getMetaClass("com.gxlu.ngrm.equipment.ManagedElement");

		Assert.assertNotNull(metaClass);
		
	}
}
