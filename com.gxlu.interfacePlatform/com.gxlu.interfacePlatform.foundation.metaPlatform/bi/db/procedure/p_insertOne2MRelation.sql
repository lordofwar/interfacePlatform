CREATE OR REPLACE Procedure p_Insertone2mrelation(p_Relationspecname In Varchar2,
																									p_Relationspeccode In Varchar2,
																									p_Ownentitycode    In Varchar2,
																									p_Suppentitycode   In Varchar2,
																									p_Columnname       In Varchar2,
																									p_Bors             In Varchar2) As
	v_Versioninstanceid Number(18);
	v_Ownentityspecid   Number(18);
	v_Owntabname        Varchar2(255);
	v_Entityspecver     Number(18);
	v_Suppentityspecid  Number(18);
Begin

	/**created by wxy 200905 增加1对多的关系描述
 exec p_insertOne2MRelation('连接对应的子连接','LINK2CHILD','LINK','LINK','PARENTID','B');
  */

	If p_Bors = 'B' Then
		v_Owntabname := 'XB_' || p_Suppentitycode;
	Elsif p_Bors = 'S' Then
		v_Owntabname := 'XS_' || p_Suppentitycode;
	Else
		v_Owntabname := 'X_' || p_Suppentitycode;
	End If;

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

	Select s_x_Versioninstance.Currval Into v_Versioninstanceid From Dual;
	Select Id, Versioninstance Into v_Ownentityspecid, v_Entityspecver From Xm_Entityspec Where Code = p_Ownentitycode;
	Select Id Into v_Suppentityspecid From Xm_Entityspec Where Code = p_Suppentitycode;

	--n:1
	Insert Into Xm_Relationspec
		(Id, Name, Code, Relationinstancetablename, Multiple, Ownerentityspecid, Supplierentityspecid, Ownerattr,
		 Supplierattr, Versioninstance, Version, Maintainindicator, Createdate, Status)
	Values
		(s_Xm_Relationspec.Nextval, p_Relationspecname, p_Relationspeccode, v_Owntabname, 1, v_Ownentityspecid,
		 v_Suppentityspecid, p_Columnname, 'ID', v_Versioninstanceid, 1, 3, Sysdate, 1);

	--
	Insert Into Xm_Entityspec2relationspec
		(Id, Entityspecid, Entityspecversioninstance, Relationspecid, Relationspecversioninstance, Status, Version,
		 Cascadedelete, Maintenaceindicator)
	Values
		(s_Xm_Entityspec2relationspec.Nextval, v_Ownentityspecid, v_Entityspecver, s_Xm_Relationspec.Currval,
		 v_Versioninstanceid, 1, 0, 2, 3);

	Update x_Versioninstance Set Entityid = s_Xm_Relationspec.Currval Where Id = v_Versioninstanceid;

	/**
  --网元和端口N:1
  Insert into X_VERSIONINSTANCE
     (ID, CATEGORY, SUBCATEGORY, ENTITYID, MAINTAINMETHOD, VERSIONCODE, REVISIONFORMAT, MAJORVERSION, MINORVERSION, BUILDNUMBER, MAJORVERSIONSTEP, MINORVERSIONSTEP, BUILDNUMBERSTEP, VALIDPERIOD, VERSION)
   Values
     (385, 'RelationSpec', 'VersionInstance', 95, 0, '1.1.1', '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1', TO_DATE('12/31/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);

  --n:1
  Insert into XM_RELATIONSPEC
     (ID, NAME, CODE, RELATIONINSTANCETABLENAME, MULTIPLE, OWNERENTITYSPECID, SUPPLIERENTITYSPECID, OWNERATTR, SUPPLIERATTR, VERSIONINSTANCE, VERSION, MAINTAININDICATOR, CREATEDATE, STATUS)
   Values
     (95, '网元对应的端口', 'ME2PORT', 'XB_PORT', 1, 4, 37, 'ID', 'MEID', 385, 1, 2, SYSDATE, 1);

  --
  Insert into XM_ENTITYSPEC2RELATIONSPEC
     (ID, ENTITYSPECID, ENTITYSPECVERSIONINSTANCE, RELATIONSPECID, RELATIONSPECVERSIONINSTANCE, STATUS, VERSION, CASCADEDELETE, MAINTENACEINDICATOR)
   Values
     (122, 4, 104, 95, 385, 1, 0, 2, 1);
  */

End;
/
