/**************************************************************************
 * $RCSfile: Configuration.java,v $  $Revision: 1.7 $  $Date: 2009/01/05 03:18:34 $
 *
 * $Log: Configuration.java,v $
 * Revision 1.7  2009/01/05 03:18:34  richie
 * *** empty log message ***
 *
 * Revision 1.6  2009/01/05 02:34:46  richie
 * change for support spring configuration
 *
 * Revision 1.5  2008/12/24 09:31:39  richie
 * *** empty log message ***
 *
 * Revision 1.4  2008/12/24 07:06:32  richie
 * no message
 *
 * Revision 1.3  2008/12/19 09:40:06  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/18 09:25:07  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/18 09:24:13  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.cfg;

import org.apache.commons.dbcp.BasicDataSource;

import com.gxlu.meta.MetaClassManager;
import com.gxlu.meta.MetaClassManagerImpl;
import com.gxlu.meta.MetaDBManager;
import com.gxlu.meta.MetaDBManagerImpl;
import com.gxlu.meta.engine.jdbc.OracleResultPagination;
import com.gxlu.meta.engine.jdbc.ResultPagination;
import com.gxlu.meta.engine.jdbc.SpringJdbcSqlExecutor;
import com.gxlu.meta.engine.jdbc.SqlExecutor;
import com.gxlu.meta.engine.persister.entity.EntityPersister;
import com.gxlu.meta.tools.MetaConsts;
import com.gxlu.meta.tools.XmlReader;
import com.gxlu.meta.tools.XmlReaderDom4J;
import com.gxlu.meta.tools.XmlReaderException;

/**
 * @author RichieJin
 */
public class Configuration {

  public Configuration(String filePath) throws XmlReaderException {

    xmlReader = new XmlReaderDom4J(filePath);
    driver = xmlReader.getValue(MetaConsts.META_CONFIG_DATASOURCE_BEANNAME, MetaConsts.META_CONFIG_DATASOURCE_DRIVER);
    url = xmlReader.getValue(MetaConsts.META_CONFIG_DATASOURCE_BEANNAME, MetaConsts.META_CONFIG_DATASOURCE_URL);
    username = xmlReader.getValue(MetaConsts.META_CONFIG_DATASOURCE_BEANNAME, MetaConsts.META_CONFIG_DATASOURCE_USER);
    password = xmlReader.getValue(MetaConsts.META_CONFIG_DATASOURCE_BEANNAME, MetaConsts.META_CONFIG_DATASOURCE_PASSWORD);
    isPrintSql = Boolean.parseBoolean(xmlReader.getValue(MetaConsts.META_CONFIG_DATASOURCE_BEANNAME, MetaConsts.META_CONFIG_DATASOURCE_PRINTSQL));
  }

  public MetaDBManager getMetaDBManager() {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName(driver);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    
    SqlExecutor sqlExecutor = new SpringJdbcSqlExecutor();
    sqlExecutor.setDataSource(dataSource);
    sqlExecutor.setPrintSql(isPrintSql);
    ResultPagination resultPagination = new OracleResultPagination();
    sqlExecutor.setResultPagination(resultPagination);
    
    MetaClassManager metaClassManager = new MetaClassManagerImpl();
    metaClassManager.setSqlExecutor(sqlExecutor);
    
    EntityPersister persister = new EntityPersister();
    persister.setSqlExecutor(sqlExecutor);
    persister.setMetaClassManager(metaClassManager);
    
    MetaDBManager metaDBManager = new MetaDBManagerImpl();
    metaDBManager.setPersister(persister);
    metaDBManager.setMetaClassManager(metaClassManager);
    
    
    return metaDBManager;
  }

  private boolean isPrintSql;
  private String driver;
  private String username;
  private String password;
  private String url;
  private XmlReader xmlReader;
}

