Create Or Replace Procedure p_Insertentityspec(p_Entityspecname In Varchar2, p_Entityspeccode In Varchar2,
																							 p_Classname In Varchar2, p_Coretablename In Varchar2,
																							 p_Sttablename In Varchar2) As
	v_Coretabname  Varchar2(255);
	v_Coreseqname  Varchar2(255);
	v_Sttabname    Varchar2(255);
	v_Entityspecid Number(18);
Begin
	/**
   created by wxy 200903 增加实体规格
   1.创建约束表XB_PORTTYPE
   2.X_VERSIONINSTANCE增加1条记录
   3.XM_ENTITYSPEC增加1条记录
   4.XM_ENTITYDESCRIPTOR增加该约束表的描述（p_insertentitydescriptor可以自动生成）
  */
	--p_entitySpecName := '端口类型'
	--p_entitySpecCode := 'PORTTYPE'
	--p_className := 'com.gxlu.ngrm.ne.PortType'

	--v_entityTabName := 'XB_' || p_entitySpecCode;
	--v_entitySeqName := 'S_XB_' || p_entitySpecCode;
	v_Coretabname := p_Coretablename;
	If v_Coretabname Is Not Null Then
		v_Coreseqname := 'S_' || p_Coretablename;
	End If;

	v_Sttabname := p_Sttablename;

	--1

	Begin
		Insert Into x_Versionspec
			(Id, Category, Subcategory, Revisionformat, Majorversion, Minorversion, Buildnumber, Majorversionstep,
			 Minorversionstep, Buildnumberstep, Validperiod, Version)
		Values
			(s_x_Versioninstance.Nextval, 'EntitySpec', p_Entityspeccode,
			 '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1',
			 To_Date('12/31/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);
	Exception
		When Others Then
			Null;
	End;

	Insert Into x_Versioninstance
		(Id, Category, Subcategory, Entityid, Maintainmethod, Versioncode, Revisionformat, Majorversion, Minorversion,
		 Buildnumber, Majorversionstep, Minorversionstep, Buildnumberstep, Validperiod, Version)
	Values
		(s_x_Versioninstance.Nextval, 'EntitySpec', p_Entityspeccode, Null, 0, '1.1.1',
		 '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1',
		 To_Date('12/31/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);

	--2
	Insert Into Xm_Entityspec
		(Id, Name, Code, Alias, Coretablename, Ctsequence, Satellitetablename, Classname, Status, Maintainindicator,
		 Createdate, Versioninstance, Version, Datasource)
	Values
		(s_Xm_Entityspec.Nextval, p_Entityspecname, p_Entityspeccode, p_Entityspecname, v_Coretabname, v_Coreseqname,
		 v_Sttabname, p_Classname, 1, 3, Sysdate,
		 (Select Id
				 From x_Versioninstance
				Where Subcategory = p_Entityspeccode
					And Rownum = 1), 1, 1);

	Select s_Xm_Entityspec.Currval Into v_Entityspecid From Dual;
	--set parent
	Begin
		If p_Sttablename Is Not Null Then
			Update Xm_Entityspec
				 Set Parentid = (Select Id
														From Xm_Entityspec d
													 Where d.Coretablename = p_Coretablename
														 And d.Satellitetablename Is Null)
			 Where Id = v_Entityspecid;
		End If;
	Exception
		When Others Then
			Null;
	End;

	If Length(p_Sttablename) > 0 Then
		p_Insertentitydescriptor(p_Entityspeccode, p_Sttablename);
	Else
		p_Insertentitydescriptor(p_Entityspeccode, p_Coretablename);
	End If;

	--4
	--dictionary

	/**
  Insert into X_VERSIONINSTANCE
     (ID, CATEGORY, SUBCATEGORY, ENTITYID, MAINTAINMETHOD, VERSIONCODE, REVISIONFORMAT, MAJORVERSION, MINORVERSION, BUILDNUMBER, MAJORVERSIONSTEP, MINORVERSIONSTEP, BUILDNUMBERSTEP, VALIDPERIOD, VERSION)
   Values
     (386, 'EntitySpec', 'SNPORT', 48, 0, '1.1.1', '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1', TO_DATE('12/31/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);
  
  Insert into XM_ENTITYSPEC
     (ID, NAME, CODE, ALIAS, CORETABLENAME, CTSEQUENCE, CLASSNAME, STATUS, MAINTAININDICATOR, CREATEDATE, VERSIONINSTANCE, VERSION, DATASOURCE)
   Values
     (48, '交换端口', 'SNPORT', '交换端口', 'XB_PORT', 'S_XB_PORT', 'com.gxlu.ngrm.ne.SNPort', 1, 2, SYSDATE, 386, 1, 1);
  
  exec p_insertEntityDescriptor(48, 'XB_PORT');
  
  */

End;
/
