package com.gxlu.meta;

import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gxlu.meta.exception.ServiceException;
import com.gxlu.meta.help.MetaObjectHelper;
@Ignore
public class TransactTest {

  /**
   * @param args
   * @throws ServiceException 
   * @throws Exception 
   */
  public static void main(String [] args) throws ServiceException, Exception {
   
//    String color = "444444";
//    String red = color.substring(0, 2);
//    String green = color.substring(2, 4);
//    String blue = color.substring(4, 6);
//    
    ApplicationContext context = new ClassPathXmlApplicationContext("test-metaplatform-ds-context.xml");
    MetaDBManager metaDBManager = (MetaDBManager)context.getBean("metaDBManager");
//    
    MetaClass  metaClass = MetaObjectHelper.getMetaClass("com.gxlu.ngrm.code.PSTNPhysicalCodeSegment");
//    MetaObject segement = MetaObjectHelper.newInstance("com.gxlu.ngrm.code.PSTNPhysicalCodeSegment");
//
//    MetaClass  code = MetaObjectHelper.getMetaClass("com.gxlu.ngrm.code.PSTNCode");
    //code.setValue("NAME", "");
    
    
   // metaDBManager.persist(mo);
    
//    QueryCriteria criteria = new QueryCriteria(metaClass);
//    criteria.addRelationQueryCriteria("CODESEGMENT2MARKETINGAREA").addRelationQueryCriteria("MANAGE2MARKETINGAREA").addExpression(QueryExpressionUtil.equals("ID",2));                 
//    criteria.addExpression(QueryExpressionUtil.equals("USEFOR",1));
//    MetaObjectQueryResult result = metaDBManager.query(criteria);

//    
//    String groupNo = "";//–Èƒ‚Õ¯»∫∫≈
//    MetaClass metaClass = MetaObjectHelper.getMetaClass("com.gxlu.ngrm.equipment.PSTNPhysicalCode"); 
//    QueryCriteria criteria = new QueryCriteria(metaClass);          
//    criteria.addRelationQueryCriteria("CODEPSTNPORTRELATION").addExpression(QueryExpressionUtil.like("CODE","%0%"));
//    MetaObjectQueryResult result = metaDBManager.query(criteria);
//    System.out.println(result.getList().size());
  }
}
