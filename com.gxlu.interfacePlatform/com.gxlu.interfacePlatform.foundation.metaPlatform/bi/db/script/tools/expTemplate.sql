--by wxy 20090819

--元数据模版初次发布采用dmp的方式，但是之后都是通过脚本来进行升级
--发布到现场的表的ID从某一个值开始，公司里的定制库（开发or核心）按照常规的sequence增加
--上次升级之后的纪录都产生Insert语句

/*	
@@XM_ENTITYMGTTEMPLATE.SQL;
@@XM_COMPMGTTEMPLATE.SQL;
@@XM_MGTTEMPLATEATTR.SQL;
@@XM_ENTITYMGTTEMPLATERELATION.SQL;
@@XM_ENTITYMGTTMPLATTRVALID.SQL;
@@XM_MENUITEMMGTTEMPLATEASSOC.SQL;
*/

--XM_ENTITYMGTTEMPLATE
Select 'Insert into XM_ENTITYMGTTEMPLATE (ID, NAME, ENTITYSPECID, STATUS, CREATEDATE, MODIFYDATE, VERSION)
 Values (' || Id || ',''' || Name || ''',(Select Id From Xm_Entityspec Where Code=''' ||
				(Select Code From Xm_Entityspec Where Id = Entityspecid) || '''),' ||Status ||
				',to_date(''' || To_Char(Createdate,'yyyymmdd hh24') || ''',''yyyymmdd hh24'')' || ',to_date(''' ||
				To_Char(Modifydate,'yyyymmdd hh24') || ''',''yyyymmdd hh24''),' || Version || ');'
	From Xm_Entitymgttemplate;


--XM_COMPMGTTEMPLATE
Select 'Insert into XM_COMPMGTTEMPLATE (ID, NAME, COMPONENTSPECID, LAYOUTSTYLE,STATUS, CREATEDATE, MODIFYDATE)
 Values (' || Id || ',''' || Name || ''',(Select Id From Xm_Componentspec Where Code=''' ||
				(Select Code From Xm_Componentspec Where Id = Componentspecid) || '''),' || Layoutstyle || ',' || Status ||
				',to_date(''' || To_Char(Createdate,'yyyymmdd hh24') || ''',''yyyymmdd hh24'')' || ',to_date(''' ||
				To_Char(Modifydate,'yyyymmdd hh24') || ''',''yyyymmdd hh24''));'
	From Xm_Compmgttemplate;


--XM_MGTTEMPLATEATTR
  Select 'Insert into XM_MGTTEMPLATEATTR
   (ID, ENTITYMGTTEMPLATEID, INDICATOR, ATTRIBUTEID, STATUS, CREATEDATE, MODIFYDATE, VERSION, ISQUERIABLE, SEQ, ISREADONLY,COMPMGTTEMPLATEID)
 Values (' || Attr.Id || ',' || '(Select Id From xm_Entitymgttemplate Where name = '''||(Select Name from xm_Entitymgttemplate where Id =Attr.Entitymgttemplateid)||''')'  || ',' || Attr.Indicator ||
        ',(Select xd.Id From Xm_EntityDescriptor xd,Xm_EntitySpec xe Where xd.EntitySpecId= xe.id and xe.code=''' ||
        (Select Code From Xm_Entityspec Where Id = (select entityspecid from xm_entitydescriptor where id=Attr.ATTRIBUTEID)) || ''' and columnname = ''' ||
        (Select Xd.Columnname From Xm_Entitydescriptor Xd Where Xd.Id = Attr.Attributeid) || '''),' || Attr.Status ||
        ',to_date(''' || To_Char(Attr.Createdate,'yyyymmdd hh24') || ''',''yyyymmdd hh24'')' || ',to_date(''' ||
        To_Char(Attr.Modifydate,'yyyymmdd hh24') || ''',''yyyymmdd hh24''),' || Attr.Version || ',' || Attr.Isqueriable || ',' ||
        Attr.Seq || ',' || Attr.Isreadonly || ',' || '(Select Id From xm_compmgttemplate Where name = '''||(Select Name from xm_compmgttemplate where Id =Attr.Compmgttemplateid)||''')'  || ');'
  From Xm_Mgttemplateattr Attr


--XM_ENTITYMGTTEMPLATERELATION
 Select 'Insert into XM_ENTITYMGTTEMPLATERELATION
	 (ID, ENTITYMGTTEMPLATEID, SPEC2RELATIONID, NAME, ENABLECREATE, ENABLEMODIFY, ENABLEDELETE, LOADMODE, DISPMODE,RELAMGTTEMPLATEID, RELAENTITYDISPATTRID, STATUS, CREATEDATE, MODIFYDATE, VERSION,dispname )
 Values (' || Xmr.Id || ',' || '(Select Id From xm_Entitymgttemplate Where name = '''||(Select Name from xm_Entitymgttemplate where Id =Xmr.Entitymgttemplateid)||''')' ||
				',(Select xe2r.Id From xm_entityspec2relationspec xe2r,Xm_EntitySpec xe,xm_relationspec xr Where xe2r.EntitySpecId= xe.id and xe2r.RelationSpecId = xr.Id and xe.code=''' ||
				(Select Xe.Code
					 From Xm_Entityspec Xe, Xm_Entityspec2relationspec Xer
					Where Xe.Id = Xer.Entityspecid
						And Xer.Id = Xmr.Spec2relationid) || ''' and xr.code = ''' ||
				(Select Xr.Code
					 From Xm_Relationspec Xr, Xm_Entityspec2relationspec Xer
          Where Xr.Id = Xer.Relationspecid
            And Xer.Id = Xmr.Spec2relationid) || '''),''' || Xmr.Name || ''',' || Xmr.Enablecreate || ',' ||
        Xmr.Enablemodify || ',' || Xmr.Enabledelete || ',' || Xmr.Loadmode || ',' || Xmr.Dispmode ||
        ',(Select Id From xm_Entitymgttemplate Where name = '''||(Select Name from xm_Entitymgttemplate where Id =Xmr.RELAMGTTEMPLATEID)||''')'||
        ',(Select xd.Id From Xm_EntityDescriptor xd,Xm_EntitySpec xe Where xd.EntitySpecId= xe.id and xe.code=''' ||
        (Select Code From Xm_Entityspec Where Id = (select entityspecid from xm_entitydescriptor where id=Xmr.Relaentitydispattrid)) || ''' and columnname = ''' ||
        (Select Xd.Columnname From Xm_Entitydescriptor Xd Where Xd.Id = Xmr.Relaentitydispattrid) || '''),' ||
        Xmr.Status || ',to_date(''' || To_Char(Xmr.Createdate,'yyyymmdd hh24') || ''',''yyyymmdd hh24'')' || ',to_date(''' ||
        To_Char(Xmr.Modifydate,'yyyymmdd hh24') || ''',''yyyymmdd hh24''),' || Xmr.Version || ',''' || Xmr.dispname || ''');'
  From Xm_Entitymgttemplaterelation Xmr, Xm_Entitymgttemplate Mgt
 Where Xmr.Entitymgttemplateid = Mgt.Id;
 

--XM_MENUITEMMGTTEMPLATEASSOC
Select 'Insert into XM_MENUITEMMGTTEMPLATEASSOC
	 (MENUITEMID, ENTITYMGTTEMPLATEID, ISPRIMARYENTITY, STATUS, CREATEDATE, MODIFYDATE, COMMENTS, VIEWMODEL)
 Values('||'(Select Id From x_menuitem Where path = ''' ||(Select path From x_menuitem Where Id = Menuitemid) || ''')'  || 
        ',(Select Id From xm_Entitymgttemplate Where name = ''' ||
				(Select Name From Xm_Entitymgttemplate Where Id = Entitymgttemplateid) || ''')' || ',' || Isprimaryentity || ',' ||
				Status || ',to_date(''' || To_Char(Createdate,'yyyymmdd hh24') || ''',''yyyymmdd hh24'')' || ',to_date(''' ||
				To_Char(Modifydate,'yyyymmdd hh24') || ''',''yyyymmdd hh24''),''' || Comments || ''',''' || Viewmodel || ''');'
	From Xm_Menuitemmgttemplateassoc;



--XM_ENTITYMGTTMPLATTRVALID
/*
--Select 'Insert into XM_ENTITYMGTTMPLATTRVALID
--	 (ID, ENTITYMGTTEMPLATEATTRID, VALIDATORTYPE, MAXVALUE, MAXMATCHRULE, MINVALUE, MINMATCHRULE, CREATEDATE,MODIFYDATE,COMMENTS)
-- Values(' || Id || ',' || Entitymgttemplateattrid || ',' || Validatortype || ',' || Maxvalue || ',' ||
--				Maxmatchrule || ',' || Minvalue || ',' || Minmatchrule || ',to_date(''' ||
--				To_Char(Createdate,'yyyymmdd hh24') || ''',''yyyymmdd hh24'')' || ',to_date(''' ||
--				To_Char(Modifydate,'yyyymmdd hh24') || ''',''yyyymmdd hh24''),' ||
--				Nvl(Comments,'Null') || ');'
--	From Xm_Entitymgttmplattrvalid;
*/


Drop Sequence S_XM_MGTTEMPLATEATTR;
Create Sequence  S_XM_MGTTEMPLATEATTR start with 20000;

Drop Sequence S_XM_ENTITYMGTTEMPLATE;
Create Sequence S_XM_ENTITYMGTTEMPLATE START WITH 20000;

Drop Sequence S_XM_ENTITYMGTTEMPLATERELATION;
Create Sequence S_XM_ENTITYMGTTEMPLATERELATION START WITH 20000;

Drop Sequence S_XM_COMPMGTTEMPLATE;
Create Sequence S_XM_COMPMGTTEMPLATE START WITH 20000;
