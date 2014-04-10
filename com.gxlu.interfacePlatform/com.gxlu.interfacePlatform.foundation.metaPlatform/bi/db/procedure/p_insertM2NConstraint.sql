Create Or Replace Procedure p_Insertm2nconstraint(p_Entityspecname    In Varchar2,
																									p_Entityspeccode    In Varchar2,
																									p_Classname         In Varchar2,
																									p_Relationspecnamea In Varchar2,
																									p_Relationspecnamez In Varchar2,
																									p_Relationspeccodea In Varchar2,
																									p_Relationspeccodez In Varchar2,
																									p_Tabnamea          In Varchar2,
																									p_Tabnamez          In Varchar2,
																									p_Colnamea          In Varchar2,
																									p_Colnamez          In Varchar2) As
	v_Entitytabname   Varchar2(255);
	v_Entityseqname   Varchar2(255);
	v_Entityspecid    Number(18);
	v_Entityspecida   Number(18);
	v_Entityspecidz   Number(18);
	v_Entityspeccodea Varchar2(255);
	v_Entityspeccodez Varchar2(255);
Begin
	/** created by wxy 200903 增加多对多关系的约束表的描述以及关系描述
   Exec p_insertM2NConstraint('产品实例和客户的关联','PRODINSCUSTASSOC','com.gxlu.ngrm.resService.ProdInsCustAssoc','产品实例和客户的关联对应的产品实例','产品实例和客户的关联对应的客户','PRODINSCUSTASSOC2PRODINS','PRODINSCUSTASSOC2CUST','产品实例','客户信息','PRODINSID','CUSTOMERID');
   1.创建约束表XB_PORTTYPEBANDWIDTHASSIGN
   2.X_VERSIONINSTANCE增加1+2条记录
   3.XM_ENTITYSPEC增加1条记录
   4.XM_ENTITYDESCRIPTOR增加该约束表的描述（p_insertentitydescriptor可以自动生成）
   5.XM_RELATIONSPEC增加2条记录
   6.XM_ENTITYSPEC2RELATIONSPEC增加4条记录
  */

	--p_entitySpecName := '端口类型和带宽的约束';
	--p_entitySpecCode := 'PORTTYPEBANDWIDTHASSIGN';
	--p_className := 'com.gxlu.ngrm.ne.PortTypeBandWidthAssign';
	--p_relationSpecNameA := '端口类型和带宽约束对应的端口类型';
	--p_relationSpecNameZ := '端口类型和带宽约束对应的带宽';
	--p_relationSpecCodeA := 'PBASSIGN2PORTTYPE';
	--p_relationSpecCodeZ := 'PBASSIGN2BANDWIDTH';

	--p_tabNameA := '端口类型';
	--p_tabNameZ := '带宽';
	--p_colNameA := 'PORTTYPEID';
	--p_colNameZ := 'BANDWIDTHID';

	v_Entitytabname := 'XB_' || p_Entityspeccode;
	v_Entityseqname := 'S_XB_' || p_Entityspeccode;

	Insert Into x_Versioninstance
		(Id, Category, Subcategory, Entityid, Maintainmethod, Versioncode, Revisionformat, Majorversion, Minorversion,
		 Buildnumber, Majorversionstep, Minorversionstep, Buildnumberstep, Validperiod, Version)
	Values
		(s_x_Versioninstance.Nextval, 'EntitySpec', p_Entityspeccode, Null, 0, '1.1.1',
		 '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1',
		 To_Date('12/31/2009 00:00:00',
							'MM/DD/YYYY HH24:MI:SS'), 1);

	Begin
		Insert Into x_Versionspec
			(Id, Category, Subcategory, Revisionformat, Majorversion, Minorversion, Buildnumber, Majorversionstep,
			 Minorversionstep, Buildnumberstep, Validperiod, Version)
		Values
			(s_x_Versioninstance.Nextval, 'RelationSpec', p_Relationspeccodea,
			 '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1',
			 To_Date('12/31/2009 00:00:00',
								'MM/DD/YYYY HH24:MI:SS'), 1);
	Exception
		When Others Then
			Null;
	End;

	Insert Into x_Versioninstance
		(Id, Category, Subcategory, Entityid, Maintainmethod, Versioncode, Revisionformat, Majorversion, Minorversion,
		 Buildnumber, Majorversionstep, Minorversionstep, Buildnumberstep, Validperiod, Version)
	Values
		(s_x_Versioninstance.Nextval, 'RelationSpec', p_Relationspeccodea, Null, 0, '1.1.1',
		 '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1',
		 To_Date('12/31/2009 00:00:00',
							'MM/DD/YYYY HH24:MI:SS'), 1);

	Begin
		Insert Into x_Versionspec
			(Id, Category, Subcategory, Revisionformat, Majorversion, Minorversion, Buildnumber, Majorversionstep,
			 Minorversionstep, Buildnumberstep, Validperiod, Version)
		Values
			(s_x_Versioninstance.Nextval, 'RelationSpec', p_Relationspeccodez,
			 '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1',
			 To_Date('12/31/2009 00:00:00',
								'MM/DD/YYYY HH24:MI:SS'), 1);
	Exception
		When Others Then
			Null;
	End;

	Insert Into x_Versioninstance
		(Id, Category, Subcategory, Entityid, Maintainmethod, Versioncode, Revisionformat, Majorversion, Minorversion,
		 Buildnumber, Majorversionstep, Minorversionstep, Buildnumberstep, Validperiod, Version)
	Values
		(s_x_Versioninstance.Nextval, 'RelationSpec', p_Relationspeccodez, Null, 0, '1.1.1',
		 '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1',
		 To_Date('12/31/2009 00:00:00',
							'MM/DD/YYYY HH24:MI:SS'), 1);

	--3
	Insert Into Xm_Entityspec
		(Id, Name, Code, Alias, Coretablename, Ctsequence, Classname, Status, Maintainindicator, Createdate,
		 Versioninstance, Version, Datasource)
	Values
		(s_Xm_Entityspec.Nextval, p_Entityspecname, p_Entityspeccode, p_Entityspecname, v_Entitytabname, v_Entityseqname,
		 p_Classname, 1, 3, Sysdate,
		 (Select Id
				 From x_Versioninstance
				Where Subcategory = p_Entityspeccode
					And Rownum = 1), 1, 1);

	Select s_Xm_Entityspec.Currval Into v_Entityspecid From Dual;

	--4 check
	p_Insertentitydescriptor(p_Entityspeccode,
													 v_Entitytabname);

	Update Xm_Entitydescriptor
		 Set Dicclassname = p_Entityspeccode, Dicattributename = Columnname
	 Where Entityspecid = v_Entityspecid
		 And Datatype = 10
		 And Dicclassname Is Null;

	Update Xm_Entitydescriptor
		 Set Ispk = 1
	 Where Columnname = 'ID'
		 And Entityspecid = v_Entityspecid;

	Update Xm_Entitydescriptor
		 Set Isversion = 1
	 Where Columnname = 'VERSION'
		 And Entityspecid = v_Entityspecid;

	--5
	Select Id, Code Into v_Entityspecida, v_Entityspeccodea From Xm_Entityspec Where Name = p_Tabnamea;

	Select Id, Code Into v_Entityspecidz, v_Entityspeccodez From Xm_Entityspec Where Name = p_Tabnamez;

	Insert Into Xm_Relationspec
		(Id, Name, Code, Relationinstancetablename, Multiple, Ownerentityspecid, Supplierentityspecid, Ownerattr,
		 Supplierattr, Versioninstance, Version, Maintainindicator, Createdate, Status)
	Values
		(s_Xm_Relationspec.Nextval, p_Relationspecnamea, p_Relationspeccodea, v_Entitytabname, 4, v_Entityspecid,
		 v_Entityspecida, 'ID', p_Colnamea,
		 (Select Id
				 From x_Versioninstance
				Where Subcategory = p_Relationspeccodea
					And Rownum = 1), 1, 3, Sysdate, 1);

	Insert Into Xm_Relationspec
		(Id, Name, Code, Relationinstancetablename, Multiple, Ownerentityspecid, Supplierentityspecid, Ownerattr,
		 Supplierattr, Versioninstance, Version, Maintainindicator, Createdate, Status)
	Values
		(s_Xm_Relationspec.Nextval, p_Relationspecnamez, p_Relationspeccodez, v_Entitytabname, 4, v_Entityspecid,
		 v_Entityspecidz, 'ID', p_Colnamez,
		 (Select Id
				 From x_Versioninstance
				Where Subcategory = p_Relationspeccodez
					And Rownum = 1), 1, 3, Sysdate, 1);

	--6
	Insert Into Xm_Entityspec2relationspec
		(Id, Entityspecid, Entityspecversioninstance, Relationspecid, Relationspecversioninstance, Status, Version,
		 Cascadedelete, Maintenaceindicator)
	Values
		(s_Xm_Entityspec2relationspec.Nextval, v_Entityspecida,
		 (Select Id
				 From x_Versioninstance
				Where Subcategory = v_Entityspeccodea
					And Rownum = 1), (Select Id From Xm_Relationspec Where Code = p_Relationspeccodea),
		 (Select Id
				 From x_Versioninstance
				Where Subcategory = p_Relationspeccodea
					And Rownum = 1), 1, 1, 2, 3);

	Insert Into Xm_Entityspec2relationspec
		(Id, Entityspecid, Entityspecversioninstance, Relationspecid, Relationspecversioninstance, Status, Version,
		 Cascadedelete, Maintenaceindicator)
	Values
		(s_Xm_Entityspec2relationspec.Nextval, v_Entityspecidz,
		 (Select Id
				 From x_Versioninstance
				Where Subcategory = v_Entityspeccodez
					And Rownum = 1), (Select Id From Xm_Relationspec Where Code = p_Relationspeccodez),
		 (Select Id
				 From x_Versioninstance
				Where Subcategory = p_Relationspeccodez
					And Rownum = 1), 1, 1, 2, 3);

	Insert Into Xm_Entityspec2relationspec
		(Id, Entityspecid, Entityspecversioninstance, Relationspecid, Relationspecversioninstance, Status, Version,
		 Cascadedelete, Maintenaceindicator)
	Values
		(s_Xm_Entityspec2relationspec.Nextval, v_Entityspecid,
		 (Select Id
				 From x_Versioninstance
				Where Subcategory = p_Entityspeccode
					And Rownum = 1), (Select Id From Xm_Relationspec Where Code = p_Relationspeccodea),
		 (Select Id
				 From x_Versioninstance
				Where Subcategory = p_Relationspeccodea
					And Rownum = 1), 1, 1, 2, 3);

	Insert Into Xm_Entityspec2relationspec
		(Id, Entityspecid, Entityspecversioninstance, Relationspecid, Relationspecversioninstance, Status, Version,
		 Cascadedelete, Maintenaceindicator)
	Values
		(s_Xm_Entityspec2relationspec.Nextval, v_Entityspecid,
		 (Select Id
				 From x_Versioninstance
				Where Subcategory = p_Entityspeccode
					And Rownum = 1), (Select Id From Xm_Relationspec Where Code = p_Relationspeccodez),
		 (Select Id
				 From x_Versioninstance
				Where Subcategory = p_Relationspeccodez
					And Rownum = 1), 1, 1, 2, 3);

	Update x_Versioninstance
		 Set Entityid = (Select Id From Xm_Entityspec Where Code = p_Entityspeccode)
	 Where Subcategory = p_Entityspeccode;

	Update x_Versioninstance
		 Set Entityid = (Select Id From Xm_Relationspec Where Code = p_Relationspeccodea)
	 Where Subcategory = p_Relationspeccodea;

	Update x_Versioninstance
		 Set Entityid = (Select Id From Xm_Relationspec Where Code = p_Relationspeccodez)
	 Where Subcategory = p_Relationspeccodez;

	--7
	--dictionary

	/**
  --1
  --create table XB_PORTTYPEBANDWIDTHASSIGN
  
  --2
  Insert into X_VERSIONINSTANCE
     (ID, CATEGORY, SUBCATEGORY, ENTITYID, MAINTAINMETHOD, VERSIONCODE, REVISIONFORMAT, MAJORVERSION, MINORVERSION, BUILDNUMBER, MAJORVERSIONSTEP, MINORVERSIONSTEP, BUILDNUMBERSTEP, VALIDPERIOD, VERSION)
   Values
     (366, 'EntitySpec', 'PORTTYPEBANDWIDTHASSIGN', 40, 0, '1.1.1', '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1', TO_DATE('12/31/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);
  Insert into X_VERSIONINSTANCE
     (ID, CATEGORY, SUBCATEGORY, ENTITYID, MAINTAINMETHOD, VERSIONCODE, REVISIONFORMAT, MAJORVERSION, MINORVERSION, BUILDNUMBER, MAJORVERSIONSTEP, MINORVERSIONSTEP, BUILDNUMBERSTEP, VALIDPERIOD, VERSION)
   Values
     (367, 'RelationSpec', 'VersionInstance', 87, 0, '1.1.1', '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1', TO_DATE('12/31/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);
  Insert into X_VERSIONINSTANCE
     (ID, CATEGORY, SUBCATEGORY, ENTITYID, MAINTAINMETHOD, VERSIONCODE, REVISIONFORMAT, MAJORVERSION, MINORVERSION, BUILDNUMBER, MAJORVERSIONSTEP, MINORVERSIONSTEP, BUILDNUMBERSTEP, VALIDPERIOD, VERSION)
   Values
     (368, 'RelationSpec', 'VersionInstance', 88, 0, '1.1.1', '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1', TO_DATE('12/31/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);
  
  --3
  Insert into XM_ENTITYSPEC
     (ID, NAME, CODE, ALIAS, CORETABLENAME, CTSEQUENCE, CLASSNAME, STATUS, MAINTAININDICATOR, CREATEDATE, VERSIONINSTANCE, VERSION, DATASOURCE)
   Values
     (40, '端口类型和带宽的约束', 'PORTTYPEBANDWIDTHASSIGN', '端口类型和带宽的约束', 'XB_PORTTYPEBANDWIDTHASSIGN', 'S_XB_PORTTYPEBANDWIDTHASSIGN', 'com.gxlu.ngrm.ne.PortTypeBandWidthAssign', 1, 2, TO_DATE('03/06/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 366, 1, 1);
  
  --4
  exec p_insertEntityDescriptor(40,'XB_PORTTYPEBANDWIDTHASSIGN');
  
  --5
  Insert into XM_RELATIONSPEC
     (ID, NAME, CODE, RELATIONINSTANCETABLENAME, MULTIPLE, OWNERENTITYSPECID, SUPPLIERENTITYSPECID, OWNERATTR, SUPPLIERATTR, VERSIONINSTANCE, VERSION, MAINTAININDICATOR, CREATEDATE, STATUS)
   Values
     (87, '端口类型和带宽约束对应的端口类型', 'PBASSIGN2PORTTYPE', 'XB_PORTTYPEBANDWIDTHASSIGN', 4, 40, 37, 'ID', 'PORTTYPEID', 367, 1, 2, TO_DATE('03/06/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);
  Insert into XM_RELATIONSPEC
     (ID, NAME, CODE, RELATIONINSTANCETABLENAME, MULTIPLE, OWNERENTITYSPECID, SUPPLIERENTITYSPECID, OWNERATTR, SUPPLIERATTR, VERSIONINSTANCE, VERSION, MAINTAININDICATOR, CREATEDATE, STATUS)
   Values
     (88, '端口类型和带宽约束对应的带宽', 'PBASSIGN2BANDWIDTH', 'XB_PORTTYPEBANDWIDTHASSIGN', 4, 40, 39, 'ID', 'BANDWIDTHID', 368, 1, 2, TO_DATE('03/06/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);
  
  --6
  Insert into XM_ENTITYSPEC2RELATIONSPEC
     (ID, ENTITYSPECID, ENTITYSPECVERSIONINSTANCE, RELATIONSPECID, RELATIONSPECVERSIONINSTANCE, STATUS, VERSION, CASCADEDELETE, MAINTENACEINDICATOR)
   Values
     (85, 37, 331, 87, 367, 1, 1, 1, 2);
  Insert into XM_ENTITYSPEC2RELATIONSPEC
     (ID, ENTITYSPECID, ENTITYSPECVERSIONINSTANCE, RELATIONSPECID, RELATIONSPECVERSIONINSTANCE, STATUS, VERSION, CASCADEDELETE, MAINTENACEINDICATOR)
   Values
     (86, 39, 334, 88, 368, 1, 1, 2, 2);
  Insert into XM_ENTITYSPEC2RELATIONSPEC
     (ID, ENTITYSPECID, ENTITYSPECVERSIONINSTANCE, RELATIONSPECID, RELATIONSPECVERSIONINSTANCE, STATUS, VERSION, CASCADEDELETE, MAINTENACEINDICATOR)
   Values
     (87, 40, 366, 87, 367, 1, 1, 2, 2);
  Insert into XM_ENTITYSPEC2RELATIONSPEC
     (ID, ENTITYSPECID, ENTITYSPECVERSIONINSTANCE, RELATIONSPECID, RELATIONSPECVERSIONINSTANCE, STATUS, VERSION, CASCADEDELETE, MAINTENACEINDICATOR)
   Values
     (88, 40, 366, 88, 368, 1, 1, 2, 2);
  
  --7
  --dictionary
  
  */

End;
/
