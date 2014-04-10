package com.gxlu.international.jdbc;

import java.util.HashMap;
import java.util.Map;

public class SysConst {
	
	//故障单对应现象表
	public static final String TABLENAME_FAULTTICKET = "XB_FAULTTICKET";	
	public static final String PREFIX_FAULTTICKET = "vw_nbi_tbFTFaultTicket";
	
	//确认/测试信息表
	public static final String TABLENAME_CONFIRM = "XB_CONFIRM";
	public static final String PREFIX_CONFIRM = "vw_nbi_tbFTConfirm";
	
	//受理表
	public static final String TABLENAME_ACCEPT = "XB_ACCEPT";
	public static final String PREFIX_ACCEPT = "vw_nbi_tbFTAccept";
	
	//评估/归档信息表
	public static final String TABLENAME_ARCHIVE = "XB_ARCHIVE";
	public static final String PREFIX_ARCHIVE = "vw_nbi_tbFTArchive";
	
	//客响表
	public static final String TABLENAME_CUSTRESPONSE = "XB_CUSTRESPONSE";
	public static final String PREFIX_CUSTRESPONSE = "vw_nbi_tbFTCustResponse";
	
	//故障扩展表
	public static final String TABLENAME_EXPAND = "XB_EXPAND";
	public static final String PREFIX_EXPAND = "vw_nbi_tbFTExpand";
	
	//专业故障种类表
	public static final String TABLENAME_FAULTCATEGORY = "XB_FAULTCATEGORY";
	public static final String PREFIX_FAULTCATEGORY = "vw_nbi_tbFTFaultCategory";
	
	//单据历时记录表
	public static final String TABLENAME_OVERTIMELOG = "XB_OVERTIMELOG";
	public static final String PREFIX_OVERTIMELOG = "vw_nbi_tbFTOverTimeLog";
	
	//符合故障通报策略表的通报记录
	public static final String TABLENAME_REPRELDEPT = "XB_REPRELDEPT";
	public static final String PREFIX_REPRELDEPT = "vm_nbi_tbFTRepRelDept";
	
	//回访记录表
	public static final String TABLENAME_TALKBACK = "XB_TALKBACK";
	public static final String PREFIX_TALKBACK = "vw_nbi_tbFTTalkBack";
	
	//专业故障种类表
	public static final String TABLENAME_WORKTICKET = "XB_WORKTICKET";
	public static final String PREFIX_WORKTICKET = "vw_nbi_tbFTWorkTicket";
	
	public static final String LOCAL_ENCODING = "GB2312";
	
	public static final String DATEPATTERN = "yyyyMMdd"; //ftp文件的日期格式
	
	public static final String DATETIMEPATTERN = "yyyyMMdd hh:mm:ss";
	
	public static final int DATABASE_TYPE_ORACLE = 0;
    public static final int DATABASE_TYPE_SYBASE = 1;
    
    public static final String FTPSITE_GZRESRC = "GZRESRC";
    
    public static final String FTPSITE_GZRESRC_REVERSE = "GZRESRC_REVERSE";
    
    public static final String FTPSITE_GZRESRC2 = "GZRESRC2";
    
    public static final String TABLENAME_DUTYDEPT="XB_DUTYDEPT";//故障单责任部门
    
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
    
 //--------fengzhou 无线
   public static final String EOMS_TEMP_FILE = "eomsTempFile";
    
   public static final String VIEWNAME_PZ_BSC = "TOOSS_VIEW_PZ_BSC";
   
   public static final String PREFIX_PZ_BSC = "ccssoft_BSC";
   
   public static final String TITLE_PZ_BSC = "所属地市#@网格化区域#@机房编号#@网元类型#@生产厂商#@网元名称#@设备ID后缀#@网元编号#@使用状态#@软件版本#@工程编码#@工程状态#@投产时间#@保修期开始时间#@保修期结束时间#@信令点编码#@所属营销中心#@A接口传输设备型号#@Abis接口传输设备型号#@传输方式#@设备编码";
   
   public static final String VIEWNAME_PZ_BTS = "TOOSS_VIEW_PZ_BTS";
   
   public static final String PREFIX_PZ_BTS = "ccssoft_BTS";
   
   public static final String TITLE_PZ_BTS = "网元号#@网元类型#@地市#@所属行政区域#@机房（或安装点）编号#@营销中心ID#@网格编号#@站点级别(基站等级)#@版本信息#@传输电路编号#@传输属性#@传输方式#@基站业务类型#@1X基站站型#@DO基站站型#@网元设备型号#@基站设备类型#@覆盖区域类型#@覆盖属性#@覆盖道路类型#@是否边界#@是否室分信源#@投产时间#@基站备注#@工程名称#@工程编码#@工程状态#@开通日期#@使用状态#@保修期开始时间#@保修期结束时间#@设备安装方式#@设备来源#@2M用于1X数量#@2M用于DO数量#@1XDO混用2M数量#@已用GE数#@已用FE数#@E1数#@生产厂商#@BSC名称#@传输设备型号#@网元名称#@设备编码";
   
   public static final String VIEWNAME_TJ_BTS = "TOOSS_VIEW_TJ_BTS";
   
   public static final String PREFIX_TJ_BTS = "ccssoft_BTSResStatic";
   
   public static final String TITLE_TJ_BTS = "地市#@区域#@网元号#@网元名称#@基站类型#@TCH数#@Ce数目#@无线容量#@覆盖半径#@生产厂商#@基站语音站型#@基站数据站型#@基站站型#@载频数#@1X板CE数#@EV-DO CE数量#@开销信道数#@1X载扇数#@Do载扇数#@1X载频数#@Do载扇数#@1X载频数#@DO载频数#@版本信息#@创建时间#@BTS编号#@所属BSC名称#@CE数量#@1X无线网络容量#@1X实占无线网络容量#@EV-DO无线网络容量#@EV-DO实占无线网络容量#@2M用于1X数量#@2M用于DO数量#@1X/DO混用2M数量#@已用GE数量#@设备编码";
   
   public static final String VIEWNAME_PZ_RRU = "TOOSS_VIEW_PZ_RRU";
   
   public static final String PREFIX_PZ_RRU = "ccssoft_RRU";
   
   public static final String TITLE_PZ_RRU = "地市#@所属行政区域#@所属机房或安装点编号#@网元类型#@生产厂商#@名称#@所属基站名称#@所属基站网元号#@RRU网元编号#@使用状态#@工程编码#@投产时间#@设备安装方式#@设备安装位置#@载频数目#@设备级联级别#@覆盖属性#@覆盖区域类型#@覆盖道路类型#@光路编号#@光纤长度#@网格化区域#@所属营销中心#@设备编码";
	   
   public static final Map<String, String> nmAreaCode = new HashMap<String, String>(){{
		put("广州", "200");
		put("汕尾", "660");
		put("阳江", "662");
		put("揭阳", "663");
		put("茂名", "668");
		put("江门", "750");
		put("韶关", "751");
		put("惠州", "752");
		put("梅州", "753");
		put("汕头", "754");
		put("深圳", "755");
		put("珠海", "756");
		put("佛山", "757");
		put("肇庆", "758");
		put("湛江", "759");
		put("中山", "760");
		put("河源", "762");
		put("清远", "763");
		put("云浮", "766");
		put("潮州", "768");
		put("东莞", "769");
	}
	};
	public static final String SITE_FTP_LLPM_HUAWEI="SITE_FTP_LLPM_HUAWEI";
	public static final String FTP_SITE_RANHUAWEI = "SITE_FTP_RAN_HUAWEI";
	public static final String FTP_SITE_VASSQM = "SITE_FTP_VAS_SQM";
	public static final String FTP_SITE_RANERICSSON = "SITE_FTP_RAN_ERICSSON";
	public static final String SITE_FTP_DOCU_MANAGMENT = "SITE_FTP_DOCU_MANAGMENT";
}
