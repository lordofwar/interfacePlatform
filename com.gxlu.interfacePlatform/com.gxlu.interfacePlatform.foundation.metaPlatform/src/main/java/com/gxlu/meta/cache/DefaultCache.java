/**************************************************************************
 * $RCSfile: DefaultCache.java,v $  $Revision: 1.1 $  $Date: 2008/12/18 09:23:51 $
 *
 * $Log: DefaultCache.java,v $
 * Revision 1.1  2008/12/18 09:23:51  richie
 * *** empty log message ***
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : DefaultCache.java
 * Created on : Dec 18, 2008 2:15:07 PM
 * Creator : RichieJin
 */
package com.gxlu.meta.cache;

import java.util.HashMap;

import com.gxlu.meta.Cachable;

/**
 * <pre>
 * Description : TODO
 * @author RichieJin
 * </pre>
 */
public class DefaultCache<K, V> extends HashMap<K, V> implements Cachable<K, V> {
}

