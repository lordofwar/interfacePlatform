/**************************************************************************
 * $RCSfile: InsertMeteObjectTest.java,v $  $Revision: 1.8 $  $Date: 2009/02/11 09:22:28 $
 *
 * $Log: InsertMeteObjectTest.java,v $
 * Revision 1.8  2009/02/11 09:22:28  lvyg
 * *** empty log message ***
 *
 * Revision 1.7  2009/02/11 02:04:41  lvyg
 * *** empty log message ***
 *
 * Revision 1.6  2009/02/10 06:41:53  lvyg
 * *** empty log message ***
 *
 * Revision 1.5  2009/02/10 02:21:00  lvyg
 * *** empty log message ***
 *
 * Revision 1.3  2009/01/04 10:03:34  richie
 * *** empty log message ***
 *
 * Revision 1.2  2008/12/26 05:55:47  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/24 09:30:49  richie
 * *** empty log message ***
 *
 **************************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : InsertMeteObjectTest.java
 * Created on : Dec 24, 2008 4:20:47 PM
 * Creator : RichieJin
 */

package com.gxlu.meta;

import java.util.LinkedList;
import java.util.List;

import org.junit.Ignore;

import com.gxlu.meta.cfg.Configuration;
import com.gxlu.meta.cfg.DefaultMetaObject;

//���԰汾1�� lvyonggang

/**
 * <pre>
 * Description : TODO
 * @author RichieJin
 * </pre>
 */
@Ignore
public class InsertMeteObjectTest {
	public InsertMeteObjectTest(String name) throws Exception {

		metaDBManager = (new Configuration("conf/meta.cfg.xml"))
				.getMetaDBManager();

	}

	public void testInsert1() throws Exception {

		/*
		 * create by lvyongang create date:2009.2.4 ����:��������A�����ö���A���������ԣ��־û�A
		 * Ԥ�ڽ��:A����������ȷ�־û������ݿ� ��������׼��:��
		 */

		MetaClass bts = metaDBManager.getMetaClass("com.gxlu.ngrm.ne.BTS");
		MetaObject object = new DefaultMetaObject(bts);
		object.setValue("CODE", "TESTCODE1");

		object.setValue("NAME", "TESTNAME");
		object.setValue("ALIAS", "TESTALIAS");
		object.setValue("USERLABEL", "TESTUSERLABEL");
		object.setValue("NATIVEEMSNAME", "TESTNATIVEEMSNAME");
		object.setValue("ASSEMBLENAME", "TESTASSEMBLENAME");
		object.setValue("ASSEMBLECODE", "TESTASSEMBLECODE");
		object.setValue("TEMPLATENAME", "TESTTEMPLATENAME");
		object.setValue("IPADDRESS", "TESTIPADDRESS");
		object.setValue("NETMASK", "TESTNETMASK");
		object.setValue("COMMENTS", "TESTCOMMENTS");
		object.setValue("VERSION", 11111);
		object.setValue("CATEGORY", 11111);
		object.setValue("SUBCATEGORY", 11111);
		object.setValue("ENTITYTYPEID", 11111);
		object.setValue("ENTITYMGTTEMPLATEID", 11111);
		object.setValue("VENDORID", 11111);
		object.setValue("MODELID", 11111);
		object.setValue("SOFTWAREVERSION", 11111);
		object.setValue("HARDWAREVERSION", 11111);
		object.setValue("INSTALLADDRESSID", 11111);
		object.setValue("PARENTID", 11111);
		object.setValue("SITEID", 11111);
		object.setValue("HOSTID", 11111);
		object.setValue("AREAID", 11111);
		object.setValue("INDICATOR", 11111);
		object.setValue("ISTEMPLATE", 11111);
		object.setValue("NETWORKLAYER", 11111);
		object.setValue("PHYSICALSTATUS", 11111);
		object.setValue("PHYSICALSTATUSDETAIL", 11111);
		object.setValue("SERVICESTATUS", 11111);
		object.setValue("SERVICESTATUSDETAIL", 11111);
		object.setValue("PROTECTIONSTATUS", 11111);
		object.setValue("REDUNDANCYSTATUS", 11111);
		object.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		metaDBManager.persist(object);
	}

	public void testInsert2() throws Exception {
		MetaClass bts = metaDBManager.getMetaClass("com.gxlu.ngrm.ne.BTS");
		MetaObject object = new DefaultMetaObject(bts);

		List<MetaClassRelation> listrelation = bts.getMetaClassRelations();

		System.out.println(listrelation.size());
		for (MetaClassRelation relation2 : listrelation) {

			if (relation2.getRelationMetaClass().getClassName()
					.equalsIgnoreCase("com.gxlu.ngrm.ne.LOCATION")) {

				System.out.println("111"
						+ relation2.getRelationMetaClass().getClassName());
				MetaClass bcs = metaDBManager
						.getMetaClass("com.gxlu.ngrm.ne.LOCATION");
				MetaObject objectbcs = new DefaultMetaObject(bcs);
				objectbcs.setValue("CODE", "LOCATION");
				objectbcs.setValue("NAME", "TESTNAME");
				objectbcs.setValue("CATEGORY", 22);
				objectbcs.setPersistType(MetaObject.PERSIST_TYPE_ADD);
				List<MetaObject> metaList = new LinkedList<MetaObject>();
				metaList.add(objectbcs);
				object.setRelateObject(metaList, relation2);

			}

			System.out.println("222"
					+ relation2.getRelationMetaClass().getClassName());

		}

		object.setValue("CODE", "TESTCODE1");
		object.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		metaDBManager.persist(object);

	}

	public void testInsert3() throws Exception {

		/*
		 * created by lvyongang
		 * 
		 * create date:2009.2.4
		 * 
		 * ����:��������A,B,C��A:B=1:1 A:C=1��1 �־û�A
		 * 
		 * Ԥ�ڽ��:A,B,C����������ȷ�־û������ݿ⣬A��B��A��C�Ĺ�ϵ��ȷ�־û�
		 * 
		 * ��������׼��:port������3��entitySpec��A=MDF���ж��ӣ�B=MDFֱ�ж��ӣ�C=PSTN�˿�
		 */

		MetaClass MDFH = metaDBManager.getMetaClass("com.gxlu.ngrm.ne.MDFH");
		MetaClass MDFZ = metaDBManager.getMetaClass("com.gxlu.ngrm.ne.MDFZ");
		MetaClass PSTN = metaDBManager
				.getMetaClass("com.gxlu.ngrm.ne.PSTNPORT2");

		MetaObject objectA = new DefaultMetaObject(MDFH);
		objectA.setValue("CODE", "TESTCODE120090211");

		MetaObject objectB = new DefaultMetaObject(MDFZ);
		objectB.setValue("CODE", "TESTCODE220090211");

		MetaObject objectC = new DefaultMetaObject(PSTN);
		objectC.setValue("CODE", "TESTCODE320090211");

		List<MetaClassRelation> listrelationMDFH = MDFH.getMetaClassRelations();
		// List<MetaClassRelation> listrelationMDFZ =
		// MDFZ.getMetaClassRelations();
		// List<MetaClassRelation> listrelationPSTN =
		// PSTN.getMetaClassRelations();

		System.out.println("listrelationMdfhTest3" + listrelationMDFH.size());

		for (MetaClassRelation relation2 : listrelationMDFH) {

			if (relation2.getRelationMetaClass().getClassName()
					.equalsIgnoreCase("com.gxlu.ngrm.ne.MDFZ")) {

				System.out.println("last test"
						+ relation2.getRelationMetaClass().getClassName());

				List<MetaObject> metaList = new LinkedList<MetaObject>();
				objectB.setPersistType(MetaObject.PERSIST_TYPE_ADD);
				metaList.add(objectB);

				objectA.setRelateObject(metaList, relation2);
			}

			if (relation2.getRelationMetaClass().getClassName()
					.equalsIgnoreCase("com.gxlu.ngrm.ne.PSTNPORT2")) {

				System.out.println("last test"
						+ relation2.getRelationMetaClass().getClassName());
				List<MetaObject> metaList = new LinkedList<MetaObject>();
				objectC.setPersistType(MetaObject.PERSIST_TYPE_ADD);

				metaList.add(objectC);
				objectA.setRelateObject(metaList, relation2);
			}

			// System.out.println("222"+relation2.getRelationMetaClass().getClassName());

		}

		objectA.setPersistType(MetaObject.PERSIST_TYPE_ADD);

		metaDBManager.persist(objectA);

	}

	public void testInsert4() throws Exception {

		/*
		 * created by lvyongang
		 * 
		 * create date:2009.2.5
		 * 
		 * ����:��������A,B,D��A:B=1:1 B:D=1:1 �־û�A
		 * 
		 * Ԥ�ڽ��:��������A,B,D��A:B=1:1 B:D=1:1 �־û�A
		 * 
		 * ��������׼��:port������3��entitySpec��A=MDF���ж��ӣ�B=MDFֱ�ж��ӣ�D=PSTN�˿�
		 */

		MetaClass ClassA = metaDBManager.getMetaClass("ObjectA");
		MetaClass ClassB = metaDBManager.getMetaClass("ObjectB");
		MetaClass ClassD = metaDBManager.getMetaClass("ObjectD");

		MetaObject objectA = new DefaultMetaObject(ClassA);
		MetaObject objectB = new DefaultMetaObject(ClassB);
		MetaObject objectD = new DefaultMetaObject(ClassD);

		objectA.setValue("CODE", "TESTCODE1");
		objectB.setValue("CODE", "TESTCODE2");
		objectD.setValue("CODE", "TESTCODE3");

		List<MetaClassRelation> listrelationA = ClassA.getMetaClassRelations();
		List<MetaClassRelation> listrelationB = ClassB.getMetaClassRelations();
		List<MetaClassRelation> listrelationD = ClassD.getMetaClassRelations();

		for (MetaClassRelation relation2 : listrelationA) {

			if (relation2.getRelationMetaClass().getClassName()
					.equalsIgnoreCase("AAAA")) {

				List<MetaObject> metaList = new LinkedList<MetaObject>();
				metaList.add(objectB);
				objectA.setRelateObject(metaList, relation2);
			}
		}

		for (MetaClassRelation relation2 : listrelationB) {

			if (relation2.getRelationMetaClass().getClassName()
					.equalsIgnoreCase("BBBB")) {

				List<MetaObject> metaList = new LinkedList<MetaObject>();
				metaList.add(objectD);
				objectB.setRelateObject(metaList, relation2);
			}
		}

		objectA.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectB.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectD.setPersistType(MetaObject.PERSIST_TYPE_ADD);

		metaDBManager.persist(objectA);
		metaDBManager.persist(objectB);

	}

	public void testInsert5() throws Exception {
		/*
		 * created by lvyongang
		 * 
		 * create date:2009.2.4
		 * 
		 * ����:��������A������A��������BΪ���ݿ�����ʵ�塣A:B=1:1���־û�A
		 * 
		 * Ԥ�ڽ��:A����������ȷ�־û���A��B�Ĺ�ϵ��ȷ�־û�
		 * 
		 * ��������׼��:port����������entitySpec��A=PSTN�˿ڣ�B=MDF���ж���
		 */

		MetaClass ClassA = metaDBManager
				.getMetaClass("com.gxlu.ngrm.ne.PSTNPORT2");
		MetaClass ClassB = metaDBManager.getMetaClass("com.gxlu.ngrm.ne.MDFH");

		MetaObject objectA = new DefaultMetaObject(ClassA);

		QueryCriteria criteria = new QueryCriteria(ClassB);

		MetaObject metaObject = metaDBManager.query(criteria).getList().get(0); // ���һ��ObjectB����

		MetaObject objectB = metaObject;

		objectA.setValue("CODE", "TESTCODE1");

		List<MetaClassRelation> listrelationA = ClassA.getMetaClassRelations();

		for (MetaClassRelation relation2 : listrelationA) {
			System.out.println("last test"
					+ relation2.getRelationMetaClass().getClassName());
			if (relation2.getRelationMetaClass().getClassName()
					.equalsIgnoreCase("com.gxlu.ngrm.ne.MDFH")) {

				List<MetaObject> metaList = new LinkedList<MetaObject>();
				metaList.add(objectB);
				objectA.setRelateObject(metaList, relation2);
			}
		}

		objectA.setPersistType(MetaObject.PERSIST_TYPE_ADD);

		metaDBManager.persist(objectA);

	}

	//
	// public void testInsert6() throws Exception {
	//
	// /*
	// * created by lvyongang
	// *
	// * create date:2009.2.5
	// *
	// * ����:��������A��D������A��������BΪ���ݿ�����ʵ�壬A:B=1:1,B:D=1:1 �־û�A
	// *
	// * Ԥ�ڽ��:A,D����������ȷ�־û���A��B��B��D�Ĺ�ϵ��ȷ�־û�
	// *
	// * ��������׼��:port������3��entitySpec��A=MDF���ж��ӣ�B=MDFֱ�ж��ӣ�C=PSTN�˿�
	// * */
	//
	//
	//
	//
	// }

	public void testInsert7() throws Exception {

		/*
		 * created by lvyongang
		 * 
		 * create date:2009.2.5
		 * 
		 * ����:��������A��3��B��A:B=1:N �־û�A
		 * 
		 * Ԥ�ڽ��:A��3��B����������ȷ�־û���A��B�Ĺ�ϵ��ȷ�־û�
		 * 
		 * ��������׼��:ME����entitySpec,A=PSTN��Ԫ��Card������B=�����忨
		 */

		MetaClass ClassA = metaDBManager.getMetaClass("ObjectA");
		MetaClass ClassB = metaDBManager.getMetaClass("ObjectB");

		MetaObject objectA = new DefaultMetaObject(ClassA);
		MetaObject objectB1 = new DefaultMetaObject(ClassB);
		MetaObject objectB2 = new DefaultMetaObject(ClassB);
		MetaObject objectB3 = new DefaultMetaObject(ClassB);

		// ����һ��A����,3��B����

		objectA.setValue("CODE", "TESTCODE1");
		objectB1.setValue("CODE", "TESTCODEB1");
		objectB2.setValue("CODE", "TESTCODEB2");
		objectB3.setValue("CODE", "TESTCODEB3");

		// ����صĶ��������趨ֵ

		List<MetaClassRelation> listrelationA = ClassA.getMetaClassRelations();

		for (MetaClassRelation relation2 : listrelationA) {
			// �ҳ�A��B��صĹ�ϵ
			if (relation2.getRelationMetaClass().getClassName()
					.equalsIgnoreCase("AAAA")) {

				List<MetaObject> metaList = new LinkedList<MetaObject>();
				metaList.add(objectB1);
				metaList.add(objectB2);
				metaList.add(objectB3);
				objectA.setRelateObject(metaList, relation2);
			}
		}

		objectA.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectB1.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectB2.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectB3.setPersistType(MetaObject.PERSIST_TYPE_ADD);

		metaDBManager.persist(objectA);

		// ��ɲ���....

	}

	public void testInsert8() throws Exception {

		/*
		 * created by lvyongang
		 * 
		 * create date:2009.2.5
		 * 
		 * ����:��������A��3��B��A:B=N:M �־û�A
		 * 
		 * Ԥ�ڽ��:A��3��B����������ȷ�־û���A��B�Ĺ�ϵ��ȷ�־û�
		 * 
		 * ��������׼��:ME����entitySpec A=PSTN��Ԫ,EquipmentHolder����entitySpec B=��������
		 */

		// ���Դ���ģʽͬtestInsert7
		MetaClass ClassA = metaDBManager
				.getMetaClass("com.gxlu.ngrm.ne.PSTNME2");
		MetaClass ClassB = metaDBManager
				.getMetaClass("com.gxlu.ngrm.ne.TRANSRANK");

		MetaObject objectA = new DefaultMetaObject(ClassA);
		MetaObject objectB1 = new DefaultMetaObject(ClassB);
		MetaObject objectB2 = new DefaultMetaObject(ClassB);
		MetaObject objectB3 = new DefaultMetaObject(ClassB);

		// ����һ��A����,3��B����

		objectA.setValue("CODE", "TESTCODE1");
		objectB1.setValue("CODE", "TESTCODEB1");
		objectB2.setValue("CODE", "TESTCODEB2");
		objectB3.setValue("CODE", "TESTCODEB3");

		// ����صĶ��������趨ֵ

		List<MetaClassRelation> listrelationA = ClassA.getMetaClassRelations();

		for (MetaClassRelation relation2 : listrelationA) {
			// �ҳ�A��B��صĹ�ϵ
			if (relation2.getRelationMetaClass().getClassName()
					.equalsIgnoreCase("com.gxlu.ngrm.ne.TRANSRANK")) {

				List<MetaObject> metaList = new LinkedList<MetaObject>();
				metaList.add(objectB1);
				metaList.add(objectB2);
				metaList.add(objectB3);
				objectA.setRelateObject(metaList, relation2);
			}
		}

		objectA.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectB1.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectB2.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectB3.setPersistType(MetaObject.PERSIST_TYPE_ADD);

		metaDBManager.persist(objectA);

		// ��ɲ���....

	}

	public void testInsert9() throws Exception {

		/*
		 * created by lvyongang
		 * 
		 * create date:2009.2.5
		 * 
		 * ����:��������A��3��B��3��C��A:B=1:N A:C=1��N �־û�A
		 * 
		 * Ԥ�ڽ��:A��3��B��3��C����������ȷ�־û���A��B��A��C�Ĺ�ϵ��ȷ�־û�
		 * 
		 * ��������׼��:Location����entitySpec
		 * A=������ME����entitySpec,B=PSTN��Ԫ��ME����entitySpec,C=BTS��Ԫ
		 */

		// ���Դ���ģʽͬtestInsert7
		MetaClass ClassA = metaDBManager.getMetaClass("ObjectA");
		MetaClass ClassB = metaDBManager.getMetaClass("ObjectB");
		MetaClass ClassC = metaDBManager.getMetaClass("ObjectC");

		MetaObject objectA = new DefaultMetaObject(ClassA);
		MetaObject objectB1 = new DefaultMetaObject(ClassB);
		MetaObject objectB2 = new DefaultMetaObject(ClassB);
		MetaObject objectB3 = new DefaultMetaObject(ClassB);

		MetaObject objectC1 = new DefaultMetaObject(ClassC);
		MetaObject objectC2 = new DefaultMetaObject(ClassC);
		MetaObject objectC3 = new DefaultMetaObject(ClassC);

		// ����һ��A����,3��B����,3��C����

		objectA.setValue("CODE", "TESTCODE1");
		objectB1.setValue("CODE", "TESTCODEB1");
		objectB2.setValue("CODE", "TESTCODEB2");
		objectB3.setValue("CODE", "TESTCODEB3");

		objectC1.setValue("CODE", "TESTCODEC1");
		objectC2.setValue("CODE", "TESTCODEC2");
		objectC3.setValue("CODE", "TESTCODEC3");

		// ����صĶ��������趨ֵ

		List<MetaClassRelation> listrelationA = ClassA.getMetaClassRelations();

		for (MetaClassRelation relation2 : listrelationA) {
			// �ҳ�A��B��صĹ�ϵ
			if (relation2.getRelationMetaClass().getClassName()
					.equalsIgnoreCase("A:B=1:N")) {

				List<MetaObject> metaList = new LinkedList<MetaObject>();
				metaList.add(objectB1);
				metaList.add(objectB2);
				metaList.add(objectB3);
				objectA.setRelateObject(metaList, relation2);
			}
			// �ҳ�A��C��صĹ�ϵ
			if (relation2.getRelationMetaClass().getClassName()
					.equalsIgnoreCase("A:C=1��N")) {

				List<MetaObject> metaList = new LinkedList<MetaObject>();
				metaList.add(objectC1);
				metaList.add(objectC2);
				metaList.add(objectC3);
				objectA.setRelateObject(metaList, relation2);
			}

		}

		objectA.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectB1.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectB2.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectB3.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectC1.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectC2.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectC3.setPersistType(MetaObject.PERSIST_TYPE_ADD);

		metaDBManager.persist(objectA);

		// ��ɲ���....

	}

	public void testInsert10() throws Exception {
		/*
		 * created by lvyongang
		 * 
		 * create date:2009.2.5
		 * 
		 * ����:��������A��D��3��B��A:B=1:N B:D=1:1 �־û�A
		 * 
		 * Ԥ�ڽ��:A��3��B��D����������ȷ�־û���A��B��B��D�Ĺ�ϵ��ȷ�־û�
		 * 
		 * ��������׼��:Card�������� A=�����忨��Port�������� B=pstn�˿ڣ�Port�������� D=MDF���ж���
		 */

		// ���Դ���ģʽͬtestInsert7
		MetaClass ClassA = metaDBManager.getMetaClass("ObjectA");
		MetaClass ClassB = metaDBManager.getMetaClass("ObjectB");
		MetaClass ClassD = metaDBManager.getMetaClass("ObjectC");

		MetaObject objectA = new DefaultMetaObject(ClassA);

		MetaObject objectB1 = new DefaultMetaObject(ClassB);
		MetaObject objectB2 = new DefaultMetaObject(ClassB);
		MetaObject objectB3 = new DefaultMetaObject(ClassB);

		MetaObject objectD = new DefaultMetaObject(ClassD);

		// ����һ��A����,3��B����,1��D����

		objectA.setValue("CODE", "TESTCODE1");

		objectB1.setValue("CODE", "TESTCODEB1");
		objectB2.setValue("CODE", "TESTCODEB2");
		objectB3.setValue("CODE", "TESTCODEB3");

		objectD.setValue("CODE", "TESTCODED1");

		// ����صĶ��������趨ֵ

		List<MetaClassRelation> listrelationA = ClassA.getMetaClassRelations();

		for (MetaClassRelation relation2 : listrelationA) {
			// �ҳ�A��B��صĹ�ϵ
			if (relation2.getRelationMetaClass().getClassName()
					.equalsIgnoreCase("A:B=1:N")) {

				List<MetaObject> metaList = new LinkedList<MetaObject>();
				metaList.add(objectB1);
				metaList.add(objectB2);
				metaList.add(objectB3);
				objectA.setRelateObject(metaList, relation2);
			}

		}

		objectA.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectB1.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectB2.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectB3.setPersistType(MetaObject.PERSIST_TYPE_ADD);
		objectD.setPersistType(MetaObject.PERSIST_TYPE_ADD);

		metaDBManager.persist(objectA);

		// ��ɲ���....

	}

	//
	// public void testInsert11() throws Exception {
	//
	// /*
	// * created by lvyongang
	// *
	// * create date:2009.2.5
	// *
	// * ����:��������A��3��B��A:B=1:N,��������1��B��������DΪ���ݿ�����ʵ�壬B:D=1:1 �־û�A
	// *
	// * Ԥ�ڽ��:A��3��B����������ȷ�־û���A��B��B��D�Ĺ�ϵ��ȷ�־û�
	// *
	// * ��������׼��:Card�������� A=�����忨��Port�������� B=pstn�˿ڣ�Port�������� D=MDF���ж���
	// * */
	//
	//
	//
	//
	// }
	//
	// public void testInsert12() throws Exception {
	//
	// /*
	// * created by lvyongang
	// *
	// * create date:2009.2.5
	// *
	// * ����:��������A��D������A����3��BΪ���ݿ�����ʵ�壬A:B=1:N B:D=1:1 �־û�A
	// *
	// * Ԥ�ڽ��:A��D����������ȷ�־û���A��B��B��D�Ĺ�ϵ��ȷ�־û�
	// *
	// * ��������׼��:Card�������� A=�����忨��Port�������� B=pstn�˿ڣ�Port�������� D=MDF���ж���
	// * */
	//
	//
	//
	//
	//
	// }
	//

	private MetaDBManager metaDBManager;
}
