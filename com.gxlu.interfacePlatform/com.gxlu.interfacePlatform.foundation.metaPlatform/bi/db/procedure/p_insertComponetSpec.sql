Create Or Replace Procedure p_Insertcomponetspec(p_Componentspecname In Varchar2,
																								 p_Componentspeccode In Varchar2) As
	v_Entitytabname Varchar2(255);
	v_Entityseqname Varchar2(255);
	v_Entityspecid  Number(18);
Begin
	/**
   created by wxy 200903 增加组件规格
   1.创建约束表XC_ADSLPORT
   2.X_VERSIONINSTANCE增加1条记录
   3.XM_COMPONENTSPEC增加1条记录
   4.XM_COMPONENTDESCRIPTOR增加该约束表的描述（p_insertComponentDescriptor可以自动生成）
  */
	--p_entitySpecName := 'ADSL端口'
	--p_entitySpecCode := 'ADSLPORT'

	v_Entitytabname := 'XC_' || p_Componentspeccode;
	v_Entityseqname := 'S_XC_' || p_Componentspeccode;

	--1

	Begin
		Insert Into x_Versionspec
			(Id, Category, Subcategory, Revisionformat, Majorversion, Minorversion, Buildnumber, Majorversionstep,
			 Minorversionstep, Buildnumberstep, Validperiod, Version)
		Values
			(s_x_Versioninstance.Nextval, 'ComponentSpec', p_Componentspeccode,
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
		(s_x_Versioninstance.Nextval, 'ComponentSpec', p_Componentspeccode,
		 (Select Id From Xm_Componentspec Where Code = p_Componentspeccode), 0, '1.1.1',
		 '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1',
		 To_Date('12/31/2009 00:00:00',
							'MM/DD/YYYY HH24:MI:SS'), 1);

	--2
	Insert Into Xm_Componentspec
		(Id, Name, Code, Alias, Componenttablename, Sequence, Status, Maintainindicator, Createdate, Versioninstance,
		 Version)
	Values
		(s_Xm_Componentspec.Nextval, p_Componentspecname, p_Componentspeccode, p_Componentspecname, v_Entitytabname,
		 v_Entityseqname, 1, 3, Sysdate, s_x_Versioninstance.Currval, 1);

	--3
	Select s_Xm_Componentspec.Currval Into v_Entityspecid From Xm_Componentspec Where Code = p_Componentspeccode;

	p_Insertcomponentdescriptor(p_Componentspeccode,
															v_Entitytabname);

	--4
	--dictionary

	/**
    Insert into X_VERSIONINSTANCE
       (ID, CATEGORY, SUBCATEGORY, ENTITYID, MAINTAINMETHOD, VERSIONCODE, REVISIONFORMAT, MAJORVERSION, MINORVERSION, BUILDNUMBER, MAJORVERSIONSTEP, MINORVERSIONSTEP, BUILDNUMBERSTEP, VALIDPERIOD, VERSION)
     Values
       (387, 'EntitySpec', 'ADSLPORT', 49, 0, '1.1.1', '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1', TO_DATE('12/31/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);
  
    Insert into XM_COMPONENTSPEC
       (ID, NAME, CODE, ALIAS, COMPONENTTABLENAME, SEQUENCE, STATUS, MAINTAININDICATOR, CREATEDATE, VERSIONINSTANCE, VERSION)
     Values
       (25, 'ADSL端口', 'ADSLPORT', 'ADSL端口', 'XC_ADSLPORT', 'S_XC_ADSLPORT', 1, 2, SYSDATE, 383, 1);
  
    exec p_insertComponentDescriptor(25,'XC_ADSLPORT');
  
  */

End;
/
