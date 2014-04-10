CREATE OR REPLACE Procedure p_Insertm2nrelation(p_Relationspecname In Varchar2,
																								p_Relationspeccode In Varchar2,
																								p_Realtiontabname  In Varchar2,
																								p_Entityspeccodea  In Varchar2,
																								p_Entityspeccodez  In Varchar2,
																								p_Aend             In Varchar2,
																								p_Zend             In Varchar2) As

Begin
	/**created by wxy 200903 增加关系规格
   1.创建关系表XR_RESSERVINSASSIGN
   2.X_VERSIONINSTANCE增加1条记录
   3.XM_RELATIONSPEC增加1条记录
  */
	--exec p_insertM2NRelation('资源服务实例从属关系','RESSERVINSASSIGN','XR_RESSERVINSASSIGN','RESSERVINS','RESSERVINS');

	--V_AEND := 'CRMORDERID';
	--V_ZEND := 'RESSERVICEID';

	--1

	Begin
		Insert Into x_Versionspec
			(Id, Category, Subcategory, Revisionformat, Majorversion, Minorversion, Buildnumber, Majorversionstep,
			 Minorversionstep, Buildnumberstep, Validperiod, Version)
		Values
			(s_x_Versioninstance.Nextval, 'RelationSpec', p_Relationspeccode,
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
		(s_x_Versioninstance.Nextval, 'RelationSpec', p_Relationspeccode, Null, 0, '1.1.1',
		 '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1',
		 To_Date('12/31/2009 00:00:00',
							'MM/DD/YYYY HH24:MI:SS'), 1);

	--2
	Insert Into Xm_Relationspec
		(Id, Name, Code, Relationinstancetablename, Multiple, Ownerentityspecid, Supplierentityspecid, Ownerattr,
		 Supplierattr, Versioninstance, Version, Maintainindicator, Createdate, Status)
	Values
		(s_Xm_Relationspec.Nextval, p_Relationspecname, p_Relationspeccode, p_Realtiontabname, 2,
		 (Select Id From Xm_Entityspec Where Code = p_Entityspeccodea),
		 (Select Id From Xm_Entityspec Where Code = p_Entityspeccodez), p_Aend, p_Zend, s_x_Versioninstance.Currval, 1, 3,
		 Sysdate, 1);

	--3 check
	Update x_Versioninstance
		 Set Entityid = (Select Id
												From Xm_Relationspec
											 Where Code = p_Relationspeccode
												 And Rownum = 1)
	 Where Subcategory = p_Relationspeccode;

	--4
	Insert Into Xm_Entityspec2relationspec
		(Id, Entityspecid, Entityspecversioninstance, Relationspecid, Relationspecversioninstance, Status, Version,
		 Cascadedelete, Maintenaceindicator)
	Values
		(s_Xm_Entityspec2relationspec.Nextval, (Select Id From Xm_Entityspec Where Code = p_Entityspeccodea),
		 (Select Id
				 From x_Versioninstance
				Where Subcategory = p_Entityspeccodea
					And Rownum = 1), s_Xm_Relationspec.Currval, s_x_Versioninstance.Currval, 1, 1, 2, 2);

	--如果是同一个实体之间的关系，只要在实体对应的关系表中插入1条记录即可。
	If (p_Entityspeccodea <> p_Entityspeccodez) Then
		Insert Into Xm_Entityspec2relationspec
			(Id, Entityspecid, Entityspecversioninstance, Relationspecid, Relationspecversioninstance, Status, Version,
			 Cascadedelete, Maintenaceindicator)
		Values
			(s_Xm_Entityspec2relationspec.Nextval, (Select Id From Xm_Entityspec Where Code = p_Entityspeccodez),
			 (Select Id
					 From x_Versioninstance
					Where Subcategory = p_Entityspeccodez
						And Rownum = 1), s_Xm_Relationspec.Currval, s_x_Versioninstance.Currval, 1, 1, 2, 3);
	End If;

	--5
	--dictionary

	/**
   Insert into X_VERSIONINSTANCE
      (ID, CATEGORY, SUBCATEGORY, ENTITYID, MAINTAINMETHOD, VERSIONCODE, REVISIONFORMAT, MAJORVERSION, MINORVERSION, BUILDNUMBER, MAJORVERSIONSTEP, MINORVERSIONSTEP, BUILDNUMBERSTEP, VALIDPERIOD, VERSION)
    Values
      (328, 'RelationSpec', 'RESSERVINSASSIGN', 48, 0, '1.1.1', '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1', TO_DATE('12/31/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);

   Insert into XM_RELATIONSPEC
    (ID, NAME, CODE, RELATIONINSTANCETABLENAME, MULTIPLE, OWNERENTITYSPECID, SUPPLIERENTITYSPECID, OWNERATTR, SUPPLIERATTR, VERSIONINSTANCE, VERSION, MAINTAININDICATOR, CREATEDATE, STATUS)
  Values
    (85, '资源服务实例从属关系', 'RESSERVINSASSIGN', 'XR_RESSERVINSASSIGN', 2, 1060, 31, 'OWNERID', 'SUPPLIERID', 328, 1, 2, TO_DATE('02/24/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);

   */

End;
/

