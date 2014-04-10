/**************************************************************************
 * $RCSfile: MetaConsts.java,v $  $Revision: 1.24 $  $Date: 2009/09/25 01:35:13 $
 *
 * $Log: MetaConsts.java,v $
 * Revision 1.24  2009/09/25 01:35:13  liuding
 * *** empty log message ***
 *
 * Revision 1.23  2009/09/15 05:03:49  liuding
 * *** empty log message ***
 *
 * Revision 1.22  2009/08/25 11:26:47  liuding
 * *** empty log message ***
 *
 * Revision 1.21  2009/07/21 09:38:13  liuding
 * *** empty log message ***
 *
 * Revision 1.20  2009/05/06 08:35:02  liuding
 * *** empty log message ***
 *
 * Revision 1.19  2009/04/10 08:24:28  liuding
 * *** empty log message ***
 *
 * Revision 1.18  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.17  2009/02/18 03:40:40  shenwq
 * *** empty log message ***
 *
 * Revision 1.16  2009/02/11 09:48:06  shenwq
 * *** empty log message ***
 *
 * Revision 1.15  2009/02/02 08:45:20  fly_zzz
 * no message
 *
 * Revision 1.14  2009/02/02 08:42:21  fly_zzz
 * no message
 *
 * Revision 1.13  2009/01/14 11:21:02  shenwq
 * *** empty log message ***
 *
 * Revision 1.12  2009/01/14 08:57:05  shenwq
 * *** empty log message ***
 *
 * Revision 1.11  2009/01/13 07:39:22  fly_zzz
 * no message
 *
 * Revision 1.10  2009/01/07 09:03:08  fly_zzz
 * no message
 *
 * Revision 1.9  2008/12/26 05:55:25  richie
 * *** empty log message ***
 *
 * Revision 1.8  2008/12/24 02:59:34  richie
 * *** empty log message ***
 *
 * Revision 1.7  2008/12/23 03:10:34  fly_zzz
 * no message
 *
 * Revision 1.6  2008/12/22 09:10:04  richie
 * *** empty log message ***
 *
 * Revision 1.5  2008/12/22 06:29:09  richie
 * *** empty log message ***
 *
 * Revision 1.4  2008/12/22 06:01:37  fly_zzz
 * no message
 *
 * Revision 1.3  2008/12/19 09:39:56  fly_zzz
 * no message
 *
 * Revision 1.2  2008/12/19 09:20:06  richie
 * *** empty log message ***
 *
 * Revision 1.1  2008/12/18 09:24:34  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.tools;

/**
 * @author K
 */
public interface MetaConsts {

  final String METACLASSLOAD_SQL_TABLE_NAME = "XM_LOAD_SQL";
  final String METACLASSLOAD_SQL_COLUMN_NAME = "SQLNAME";
  final String METACLASSLOAD_SQL_COLUMN_VALUE = "SQLVALUE";
  final String METACLASSLOAD_SQL_OPERATION_QUERYMETACLASSNAMES = "QUERYMETACLASSNAMES";
  final String METACLASSLOAD_SQL_OPERATION_QUERYMETACLASSSPEC = "QUERYMETACLASSSPEC";
  final String METACLASSLOAD_SQL_OPERATION_QUERYCOMPONENTPEC = "QUERYCOMPONENTPEC";
  final String METACLASSLOAD_SQL_OPERATION_QUERYRELATEENTITYSPEC = "QUERYRELATEENTITYSPEC";

  final String METACLASSLOAD_SQL_OPERATION_QUERYSATELLITEDEFINE = "QUERYSATELLITEDEFINE";
  final String METACLASSLOAD_SQL_OPERATION_QUERYCOREDEFINE = "QUERYCOREDEFINE";
  final String METACLASSLOAD_SQL_OPERATION_QUERYMETACOMPONETDEFINE = "QUERYMETACOMPONETDEFINE";
//  final String METACLASSLOAD_SQL_OPERATION_QUERYRELATEENTITYDEFINE = "QUERYRELATEENTITYDEFINE";
  final String METACLASSLOAD_SQL_OPERATION_QUERYPARENTCLASSNAME = "QUERYPARENTCLASSNAME";
  final String METACLASSLOAD_SQL_OPERATION_EXISTPARENTCLASSNAME = "EXISTPARENTCLASSNAME";
  final String METACLASSLOAD_SQL_OPERATION_EXISTRELATIONDEFINED = "EXISTRELATIONDEFINED";
  
  final String META_CONFIG_DATASOURCE_BEANNAME = "dataSource";
  final String META_CONFIG_DATASOURCE_DRIVER = "driver";
  final String META_CONFIG_DATASOURCE_URL = "url";
  final String META_CONFIG_DATASOURCE_USER = "username";
  final String META_CONFIG_DATASOURCE_PASSWORD = "password";
  final String META_CONFIG_DATASOURCE_PRINTSQL = "printSql";
  
  // �������ԣ�����meta�����������
  final String META_CATEGORY="METACATEGORY";
  
  //by wxy 20081219
  
  //Ԫ���ݹ����ֵ�
	String CLASSNAME_BOOLEAN = "METADATA";
	
	//����ֵ
	String ATTRIBUTENAME_METADATA_BOOLEAN = "BOOLEAN";	
	int VALUE_METADATA_BOOLEAN_TRUE = 1;  //true
	int VALUE_METADATA_BOOLEAN_FALSE = 2; //false
	
	//״̬
	String ATTRIBUTENAME_METADATA_STATUS = "STATUS";	
	int VALUE_METADATA_STATUS_AVAILABLE = 1;    //����
	int VALUE_METADATA_STATUS_NOTAVAILABLE = 2; //������
	
	//ά����ʶ
	String ATTRIBUTENAME_METADATA_MAINTAININDICATOR = "MAINTAININDICATOR";	
	int VALUE_METADATA_MAINTAININDICATOR_SYSTEM = 1;  //ϵͳά��
	int VALUE_METADATA_MAINTAININDICATOR_USER = 2;    //�û�ά��
	int VALUE_METADATA_MAINTAININDICATOR_USERCREATE = 9;    //�û�ͨ�����洴��  ֻ������״̬�������ſ���ɾ��
	
	//��������
	String ATTRIBUTENAME_METADATA_DATATYPE = "DATATYPE";	
	int VALUE_METADATA_DATATYPE_BYTE = 1;    		//byte
	int VALUE_METADATA_DATATYPE_LONG = 2;    		//long
	int VALUE_METADATA_DATATYPE_FLOAT = 3;   		//float
	int VALUE_METADATA_DATATYPE_STRING = 4;  		//String
	int VALUE_METADATA_DATATYPE_DATE = 5;    		//Date
	int VALUE_METADATA_DATATYPE_DATETIME = 6;		//DateTime
	int VALUE_METADATA_DATATYPE_BLOB = 7;    		//blob
	int VALUE_METADATA_DATATYPE_CLOB = 8;    		//clob
	int VALUE_METADATA_DATATYPE_URL = 9;     		//url
	int VALUE_METADATA_DATATYPE_DICTIONARY = 10;//�����ֵ�
	int VALUE_METADATA_DATATYPE_PASSWORD = 11;  //password
	int VALUE_METADATA_DATATYPE_IMAGE = 12;     //ͼƬ
	int VALUE_METADATA_DATATYPE_FILE = 13;      //�ļ�	
	int VALUE_METADATA_DATATYPE_INT = 14;      //int
	int VALUE_METADATA_DATATYPE_MULTIDICTIONARY = 15;      //��ѡ�����ֵ�
	
	//��ϵ���
	String CLASSNAME_RELATIONSPEC = "RELATIONSPEC";
	
	//��ϵ��
	String ATTRIBUTENAME_RELATIONSPEC_MULTIPLE = "MULTIPLE";	
	int VALUE_RELATIONSPEC_MULTIPLE_ONE2N = 1;   //1:N
	int VALUE_RELATIONSPEC_MULTIPLE_M2N= 2;    //M:N
	int VALUE_RELATIONSPEC_MULTIPLE_ONE2ONE = 3; //1:1
	int VALUE_RELATIONSPEC_MULTIPLE_N2ONE = 4;   //N:1
	
	//BY WXY 20090107
	//ʵ�����·��
	String CLASSNAME_ENTITYACCESSURL = "ENTITYACCESSURL";
	
	//��������
	String ATTRIBUTENAME_ENTITYACCESSURL_OPEREATIONTYPE = "OPEREATIONTYPE";	
	int VALUE_ENTITYACCESSURL_OPEREATIONTYPE_ADD = 1;   //����
	int VALUE_ENTITYACCESSURL_OPEREATIONTYPE_MODIFY = 2;   //�޸�
	int VALUE_ENTITYACCESSURL_OPEREATIONTYPE_DELETE = 3;   //ɾ��
	int VALUE_ENTITYACCESSURL_OPEREATIONTYPE_PROPERTY = 4;   //����
	int VALUE_ENTITYACCESSURL_OPEREATIONTYPE_LIST = 5;   //�б�
	int VALUE_ENTITYACCESSURL_OPEREATIONTYPE_TREE = 6;   //����ʾ
	int VALUE_ENTITYACCESSURL_OPEREATIONTYPE_QUERY = 7;   //��ѯ
	int VALUE_ENTITYACCESSURL_OPEREATIONTYPE_STOREQUERYKEY = 8;   //�洢��ѯ����
	int VALUE_ENTITYACCESSURL_OPEREATIONTYPE_QUIT = 9;   //�˳�
	int VALUE_ENTITYACCESSURL_OPEREATIONTYPE_RETURN = 10;   //����
	int VALUE_ENTITYACCESSURL_OPEREATIONTYPE_OTHER = 99;   //����
	
	
	//ʵ�����ģ������
	String CLASSNAME_ENTITYMGTTEMPLATEATTR = "ENTITYMGTTEMPLATEATTR";
	
	//indicator
	String ATTRIBUTENAME_ENTITYMGTTEMPLATEATTR_INDICATOR = "INDICATOR";	
	int VALUE_ENTITYMGTTEMPLATEATTR_INDICATOR_ENTITY = 1;   //ʵ��
	int VALUE_ENTITYMGTTEMPLATEATTR_INDICATOR_COMPONENT = 2;   //���
	
	//UI�������
	String ATTRIBUTENAME_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE = "UICOMPONENTTYPE";	
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_LABEL = 1;      //��ǩ
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_DATALABEL = 2;  //���ֱ�ǩ
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_DATELABLE = 3;  //���ڱ�ǩ
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_TEXTFIELD = 4;  //�ı���
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_DATEFIELD = 5;  //���ڿ�
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_TEXTAREA = 6;   //�����ı�
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_COMBOBOX = 7;   //������
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_RATIOBOX = 8;   //��ѡ��
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_CHECKBOX = 9;   //��ѡ��
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_QUERYFIELD = 10;//��ѯ��
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_LINKURL = 11;   //URL����
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_PASSWORD = 12;  //����
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_IMAGE = 13;     //ͼƬ
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_ATTACHMENT = 14;//����
	int VALUE_ENTITYMGTTEMPLATEATTR_UICOMPONENTTYPE_SEQUENCE = 15;  //���
	
	
	//ʵ�����ģ���ϵ
	String CLASSNAME_ENTITYMGTTEMPLATERELATION = "ENTITYMGTTEMPLATERELATION";
	
	//װ��ģʽ
	String ATTRIBUTENAME_ENTITYMGTTEMPLATERELATION_LOADMODE = "LOADMODE";	
	int VALUE_ENTITYMGTTEMPLATERELATION_LOADMODE_LAZYLOAD = 1;   //lazyLoad
	int VALUE_ENTITYMGTTEMPLATERELATION_LOADMODE_AUTOLOAD = 2;   //autoLoad
	
	//��ʾģʽ
	String ATTRIBUTENAME_ENTITYMGTTEMPLATERELATION_DISPMODE = "DISPMODE";	
	int VALUE_ENTITYMGTTEMPLATERELATION_DISPMODE_INDEPENDENT = 1;   //�ڶ����ı�ǩ��ʾ�Զ�ʵ��
	int VALUE_ENTITYMGTTEMPLATERELATION_DISPMODE_DEPENDENT = 2;   //���ڶ����ı�ǩ��ʾ�Զ�ʵ��

	
	//ʵ����
	String CLASSNAME_ENTITYSPEC = "ENTITYSPEC";
	
	//����Դ
	String ATTRIBUTENAME_ENTITYSPEC_DATASOURCE = "DATASOURCE";	
	int VALUE_ENTITYSPEC_DATASOURCE_TELANT = 1;   //Telant
	int VALUE_ENTITYSPEC_DATASOURCE_NM = 2;        //NM
	
	//VersionSpec��ComponentSpec��CATEGORY��SUBCATEGORYzֵ
  String VERSION_CATEGORY_COMPONENT_SPEC = "ComponentSpec";
  String VERSION_SUB_CATEGORY_COMPONENT_SPEC = "VersionInstance";
  
//VersionSpec��RelationSpec��CATEGORY��SUBCATEGORYzֵ
  String VERSION_CATEGORY_RELATION_SPEC = "RelationSpec";
  String VERSION_SUB_CATEGORY_RELATION_SPEC = "VersionInstance";
	
	
	//BY WXY 20090202
	//���ܷ��ʵ�
	String CLASSNAME_FUNCACCESSPOINT = "FUNCACCESSPOINT";
	
	//������ڵ�����
	String ATTRIBUTENAME_FUNCACCESSPOINT_ACCESSPOINTTYPE = "ACCESSPOINTTYPE";	
	int VALUE_FUNCACCESSPOINT_ACCESSPOINTTYPE_MENUITEM = 1;      //�˵�
	int VALUE_FUNCACCESSPOINT_ACCESSPOINTTYPE_SUBMENUITEM = 2;   //�Ӳ˵�
	
	
	//ʵ�岼��ģ��
	String CLASSNAME_ENTITYLAYOUTTEMPLATE = "ENTITYLAYOUTTEMPLATE";
	
	//���ַ��
	String ATTRIBUTENAME_ENTITYLAYOUTTEMPLATE_STYLE = "STYLE";	
	int VALUE_ENTITYLAYOUTTEMPLATE_STYLE_FORM = 1;      //��
	int VALUE_ENTITYLAYOUTTEMPLATE_STYLE_TABLE = 2;     //���
	
	int VALUE_DORADO_CHECKBOX_ONVALUE = 1;		//checkBox ѡ��
	int VALUE_DORADO_CHECKBOX_OFFVALUE = 0;		//checkBox ûѡ��
	
	String CLASSNAME_BASE_ENTITY_LIST = "com.gxlu.ngrm.equipment.PhysicalContainer"+
										",com.gxlu.ngrm.equipment.Card"+
										",com.gxlu.ngrm.equipment.ManagedElement"+
										",com.gxlu.ngrm.equipment.Port"+
										",com.gxlu.ngrm.equipment.CrossConnection"+
										",com.gxlu.ngrm.equipment.SubNetwork"+
										",com.gxlu.ngrm.equipment.Link"+
										",com.gxlu.ngrm.equipment.SubNetworkConnection"+
										",com.gxlu.ngrm.area.Location"+
										",com.gxlu.ngrm.area.Structure"+
										",com.gxlu.ngrm.equipment.Facility"+
										",com.gxlu.ngrm.code.CodeSegment"+
										",com.gxlu.ngrm.code.Code" +
										",com.gxlu.ngrm.network.Link" +
										",com.gxlu.ngrm.common.Basic"
										;
	String WEB_CONTROL_TEXTEDITOR = "TextEditor";
	String WEB_CONTROL_LABEL = "Label";
	String WEB_CONTROL_PASSWORD = "TextEditor-Password";
	String WEB_CONTROL_TEXTAREA = "TextEditor-TextArea";
	String WEB_CONTROL_CHECKBOX = "CheckBox";
	String WEB_CONTROL_RADIOGROUP = "RadioGroup";
	
	String SEPARATOR_MULTI_DICTIONARY = "//";
	String SEPARATOR_MULTI_DICTIONARY_DISPLAY = "/";
	
}

