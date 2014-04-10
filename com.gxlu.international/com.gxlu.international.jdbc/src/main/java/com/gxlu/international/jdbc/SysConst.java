package com.gxlu.international.jdbc;

import java.util.HashMap;
import java.util.Map;

public class SysConst {
	
	//���ϵ���Ӧ�����
	public static final String TABLENAME_FAULTTICKET = "XB_FAULTTICKET";	
	public static final String PREFIX_FAULTTICKET = "vw_nbi_tbFTFaultTicket";
	
	//ȷ��/������Ϣ��
	public static final String TABLENAME_CONFIRM = "XB_CONFIRM";
	public static final String PREFIX_CONFIRM = "vw_nbi_tbFTConfirm";
	
	//�����
	public static final String TABLENAME_ACCEPT = "XB_ACCEPT";
	public static final String PREFIX_ACCEPT = "vw_nbi_tbFTAccept";
	
	//����/�鵵��Ϣ��
	public static final String TABLENAME_ARCHIVE = "XB_ARCHIVE";
	public static final String PREFIX_ARCHIVE = "vw_nbi_tbFTArchive";
	
	//�����
	public static final String TABLENAME_CUSTRESPONSE = "XB_CUSTRESPONSE";
	public static final String PREFIX_CUSTRESPONSE = "vw_nbi_tbFTCustResponse";
	
	//������չ��
	public static final String TABLENAME_EXPAND = "XB_EXPAND";
	public static final String PREFIX_EXPAND = "vw_nbi_tbFTExpand";
	
	//רҵ���������
	public static final String TABLENAME_FAULTCATEGORY = "XB_FAULTCATEGORY";
	public static final String PREFIX_FAULTCATEGORY = "vw_nbi_tbFTFaultCategory";
	
	//������ʱ��¼��
	public static final String TABLENAME_OVERTIMELOG = "XB_OVERTIMELOG";
	public static final String PREFIX_OVERTIMELOG = "vw_nbi_tbFTOverTimeLog";
	
	//���Ϲ���ͨ�����Ա��ͨ����¼
	public static final String TABLENAME_REPRELDEPT = "XB_REPRELDEPT";
	public static final String PREFIX_REPRELDEPT = "vm_nbi_tbFTRepRelDept";
	
	//�طü�¼��
	public static final String TABLENAME_TALKBACK = "XB_TALKBACK";
	public static final String PREFIX_TALKBACK = "vw_nbi_tbFTTalkBack";
	
	//רҵ���������
	public static final String TABLENAME_WORKTICKET = "XB_WORKTICKET";
	public static final String PREFIX_WORKTICKET = "vw_nbi_tbFTWorkTicket";
	
	public static final String LOCAL_ENCODING = "GB2312";
	
	public static final String DATEPATTERN = "yyyyMMdd"; //ftp�ļ������ڸ�ʽ
	
	public static final String DATETIMEPATTERN = "yyyyMMdd hh:mm:ss";
	
	public static final int DATABASE_TYPE_ORACLE = 0;
    public static final int DATABASE_TYPE_SYBASE = 1;
    
    public static final String FTPSITE_GZRESRC = "GZRESRC";
    
    public static final String FTPSITE_GZRESRC_REVERSE = "GZRESRC_REVERSE";
    
    public static final String FTPSITE_GZRESRC2 = "GZRESRC2";
    
    public static final String TABLENAME_DUTYDEPT="XB_DUTYDEPT";//���ϵ����β���
    
    public static final String PREFIX_DUTYDEPT="vw_nbi_tbFTDutyDept";
    
    
    
    public static final String PREFIX_ADSLPORTTJNODE = "adsl_port_tj_node";
    
    public static final String TABLENAME_ADSLPORTTJNODE = "ADSLPORT_TJ_NODE";
    
    public static final String PREFIX_ADSLPORTTJTEST  = "adsl_port_tj_test";
    	
    public static final String TABLENAME_ADSLPORTTJTEST = "ADSLPORT_TJ_TEST";
    
    public static final String PREFIX_SNUSERPORTTJTEST  = "gh_port_detail_tj";
	
    public static final String TABLENAME_SNUSERPORTTJTEST = "SNUSERPORT_TJ_TEST";
    
    public static final String PREFIX_SNUSERADSLREADY  = "ywzc_zyzx";
	
    public static final String TABLENAME_SNUSERADSLREADY = "SNUSER_ADSL_READY";
    
    public static final String TABLENAME_ADSLPORTWARNING = "ADSLPORT_WARNING";
    
    public static final String TABLENAME_SNUSERPORTWARNING = "SNUSERPORT_WARNING";
    
 //--------fengzhou ����
   public static final String EOMS_TEMP_FILE = "eomsTempFile";
    
   public static final String VIEWNAME_PZ_BSC = "TOOSS_VIEW_PZ_BSC";
   
   public static final String PREFIX_PZ_BSC = "ccssoft_BSC";
   
   public static final String TITLE_PZ_BSC = "��������#@��������#@�������#@��Ԫ����#@��������#@��Ԫ����#@�豸ID��׺#@��Ԫ���#@ʹ��״̬#@����汾#@���̱���#@����״̬#@Ͷ��ʱ��#@�����ڿ�ʼʱ��#@�����ڽ���ʱ��#@��������#@����Ӫ������#@A�ӿڴ����豸�ͺ�#@Abis�ӿڴ����豸�ͺ�#@���䷽ʽ#@�豸����";
   
   public static final String VIEWNAME_PZ_BTS = "TOOSS_VIEW_PZ_BTS";
   
   public static final String PREFIX_PZ_BTS = "ccssoft_BTS";
   
   public static final String TITLE_PZ_BTS = "��Ԫ��#@��Ԫ����#@����#@������������#@��������װ�㣩���#@Ӫ������ID#@������#@վ�㼶��(��վ�ȼ�)#@�汾��Ϣ#@�����·���#@��������#@���䷽ʽ#@��վҵ������#@1X��վվ��#@DO��վվ��#@��Ԫ�豸�ͺ�#@��վ�豸����#@������������#@��������#@���ǵ�·����#@�Ƿ�߽�#@�Ƿ��ҷ���Դ#@Ͷ��ʱ��#@��վ��ע#@��������#@���̱���#@����״̬#@��ͨ����#@ʹ��״̬#@�����ڿ�ʼʱ��#@�����ڽ���ʱ��#@�豸��װ��ʽ#@�豸��Դ#@2M����1X����#@2M����DO����#@1XDO����2M����#@����GE��#@����FE��#@E1��#@��������#@BSC����#@�����豸�ͺ�#@��Ԫ����#@�豸����";
   
   public static final String VIEWNAME_TJ_BTS = "TOOSS_VIEW_TJ_BTS";
   
   public static final String PREFIX_TJ_BTS = "ccssoft_BTSResStatic";
   
   public static final String TITLE_TJ_BTS = "����#@����#@��Ԫ��#@��Ԫ����#@��վ����#@TCH��#@Ce��Ŀ#@��������#@���ǰ뾶#@��������#@��վ����վ��#@��վ����վ��#@��վվ��#@��Ƶ��#@1X��CE��#@EV-DO CE����#@�����ŵ���#@1X������#@Do������#@1X��Ƶ��#@Do������#@1X��Ƶ��#@DO��Ƶ��#@�汾��Ϣ#@����ʱ��#@BTS���#@����BSC����#@CE����#@1X������������#@1Xʵռ������������#@EV-DO������������#@EV-DOʵռ������������#@2M����1X����#@2M����DO����#@1X/DO����2M����#@����GE����#@�豸����";
   
   public static final String VIEWNAME_PZ_RRU = "TOOSS_VIEW_PZ_RRU";
   
   public static final String PREFIX_PZ_RRU = "ccssoft_RRU";
   
   public static final String TITLE_PZ_RRU = "����#@������������#@����������װ����#@��Ԫ����#@��������#@����#@������վ����#@������վ��Ԫ��#@RRU��Ԫ���#@ʹ��״̬#@���̱���#@Ͷ��ʱ��#@�豸��װ��ʽ#@�豸��װλ��#@��Ƶ��Ŀ#@�豸��������#@��������#@������������#@���ǵ�·����#@��·���#@���˳���#@��������#@����Ӫ������#@�豸����";
	   
   public static final Map<String, String> nmAreaCode = new HashMap<String, String>(){{
		put("����", "200");
		put("��β", "660");
		put("����", "662");
		put("����", "663");
		put("ï��", "668");
		put("����", "750");
		put("�ع�", "751");
		put("����", "752");
		put("÷��", "753");
		put("��ͷ", "754");
		put("����", "755");
		put("�麣", "756");
		put("��ɽ", "757");
		put("����", "758");
		put("տ��", "759");
		put("��ɽ", "760");
		put("��Դ", "762");
		put("��Զ", "763");
		put("�Ƹ�", "766");
		put("����", "768");
		put("��ݸ", "769");
	}
	};
	public static final String SITE_FTP_LLPM_HUAWEI="SITE_FTP_LLPM_HUAWEI";
	public static final String FTP_SITE_RANHUAWEI = "SITE_FTP_RAN_HUAWEI";
	public static final String FTP_SITE_VASSQM = "SITE_FTP_VAS_SQM";
	public static final String FTP_SITE_RANERICSSON = "SITE_FTP_RAN_ERICSSON";
	public static final String SITE_FTP_DOCU_MANAGMENT = "SITE_FTP_DOCU_MANAGMENT";
}
