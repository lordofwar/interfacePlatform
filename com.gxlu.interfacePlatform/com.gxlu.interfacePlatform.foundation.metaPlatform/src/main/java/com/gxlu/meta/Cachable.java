/**************************************************************************
 * $RCSfile: Cachable.java,v $  $Revision: 1.4 $  $Date: 2009/03/03 06:25:12 $
 *
 * $Log: Cachable.java,v $
 * Revision 1.4  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.3  2008/12/18 09:24:50  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/17 01:19:11  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/16 09:23:39  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/16 09:17:44  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta;

import java.util.Map;

/**
 * provide a method for reset cache data. any data that can be changed in runtime
 * and cost a lot to retrieve from source should be cached and can be update 
 * when source data is changed.
 *
 * @author K
 */
public interface Cachable<K, V> extends Map<K, V> {

}

