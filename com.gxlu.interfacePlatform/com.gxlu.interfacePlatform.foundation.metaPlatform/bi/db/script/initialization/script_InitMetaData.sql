CREATE OR REPLACE Procedure p_Insertm2onerelation(p_Relationspecname In Varchar2,
																									p_Relationspeccode In Varchar2,
																									p_Ownentitycode    In Varchar2,
																									p_Suppentitycode   In Varchar2,
																									p_Columnname       In Varchar2,
																									p_Ownerattr        In Varchar2,
																									p_Bors             In Varchar2) As
	v_Versioninstanceid Number(18);
	v_Ownentityspecid   Number(18);
	v_Owntabname        Varchar2(255);
	v_oEntityspecver     Number(18);
  v_sEntityspecver     Number(18);
	v_Suppentityspecid  Number(18);
Begin

	/**created by wxy 200903 增加多对1的关系描述
   一般来说，p_ownerAttr='ID'，但是如果是子类建立关系，就需要改为p_ownerAttr='ENTITYID'
  exec p_insertM2OneRelation('端口对应的端口类型','PORT2PORTTYPE','PORT','PORTTYPE','PORTTYPEID','ID','B');
  端口和端口类型N：1
  p_relationSpecName := '端口对应的端口类型';
  p_relationSpecCode := 'PORT2PORTTYPE';
  p_ownTabName := 'XB_PORT';
  p_suppTabName := 'XB_PORTTYPE';
  p_columnName := 'PORTTYPEID';
  p_entityspecName := 'PORT';
  */

	If p_Bors = 'B' Then
		v_Owntabname := 'XB_' || p_Ownentitycode;
	Elsif p_Bors = 'S' Then
		v_Owntabname := 'XS_' || p_Ownentitycode;
  Elsif p_Bors = 'M' Then
		v_Owntabname := 'XM_' || p_Ownentitycode;
	Else
		v_Owntabname := 'X_' || p_Ownentitycode;
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
	Select Id, Versioninstance Into v_Ownentityspecid, v_oEntityspecver From Xm_Entityspec Where Code = p_Ownentitycode;
	Select Id, Versioninstance Into v_Suppentityspecid,v_sEntityspecver From Xm_Entityspec Where Code = p_Suppentitycode;

	--n:1
	Insert Into Xm_Relationspec
		(Id, Name, Code, Relationinstancetablename, Multiple, Ownerentityspecid, Supplierentityspecid, Ownerattr,
		 Supplierattr, Versioninstance, Version, Maintainindicator, Createdate, Status)
	Values
		(s_Xm_Relationspec.Nextval, p_Relationspecname, p_Relationspeccode, v_Owntabname, 4, v_Ownentityspecid,
		 v_Suppentityspecid, p_Ownerattr, p_Columnname, v_Versioninstanceid, 1, 3, Sysdate, 1);

	--
	Insert Into Xm_Entityspec2relationspec
		(Id, Entityspecid, Entityspecversioninstance, Relationspecid, Relationspecversioninstance, Status, Version,
		 Cascadedelete, Maintenaceindicator)
	Values
		(s_Xm_Entityspec2relationspec.Nextval, v_Ownentityspecid, v_oEntityspecver, s_Xm_Relationspec.Currval,
		 v_Versioninstanceid, 1, 1, 2, 3);

--如果是同一个实体之间的关系，只要在实体对应的关系表中插入1条记录即可。
	If (p_Ownentitycode <> p_Suppentitycode) Then
    Insert Into Xm_Entityspec2relationspec
  		(Id, Entityspecid, Entityspecversioninstance, Relationspecid, Relationspecversioninstance, Status, Version,
  		 Cascadedelete, Maintenaceindicator)
  	Values
  		(s_Xm_Entityspec2relationspec.Nextval, v_Suppentityspecid, v_sEntityspecver, s_Xm_Relationspec.Currval,
  		 v_Versioninstanceid, 1, 1, 2, 3);
  End If;

	Update x_Versioninstance Set Entityid = s_Xm_Relationspec.Currval Where Id = v_Versioninstanceid;

	/**
  --端口和端口类型N：1
  Insert into X_VERSIONINSTANCE
     (ID, CATEGORY, SUBCATEGORY, ENTITYID, MAINTAINMETHOD, VERSIONCODE, REVISIONFORMAT, MAJORVERSION, MINORVERSION, BUILDNUMBER, MAJORVERSIONSTEP, MINORVERSIONSTEP, BUILDNUMBERSTEP, VALIDPERIOD, VERSION)
   Values
     (385, 'RelationSpec', 'VersionInstance', 95, 0, '1.1.1', '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1', TO_DATE('12/31/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);

  --n:1
  Insert into XM_RELATIONSPEC
     (ID, NAME, CODE, RELATIONINSTANCETABLENAME, MULTIPLE, OWNERENTITYSPECID, SUPPLIERENTITYSPECID, OWNERATTR, SUPPLIERATTR, VERSIONINSTANCE, VERSION, MAINTAININDICATOR, CREATEDATE, STATUS)
   Values
     (95, '端口对应的端口类型', 'PORT2PORTTYPE', 'XB_PORT', 4, 4, 37, 'ID', 'PORTTYPEID', 385, 1, 2, SYSDATE, 1);

  --
  Insert into XM_ENTITYSPEC2RELATIONSPEC
     (ID, ENTITYSPECID, ENTITYSPECVERSIONINSTANCE, RELATIONSPECID, RELATIONSPECVERSIONINSTANCE, STATUS, VERSION, CASCADEDELETE, MAINTENACEINDICATOR)
   Values
     (122, 4, 104, 95, 385, 1, 1, 2, 3);

  Insert into XM_ENTITYSPEC2RELATIONSPEC
     (ID, ENTITYSPECID, ENTITYSPECVERSIONINSTANCE, RELATIONSPECID, RELATIONSPECVERSIONINSTANCE, STATUS, VERSION, CASCADEDELETE, MAINTENACEINDICATOR)
   Values
     (123, 37, 105, 95, 385, 1, 1, 2, 3);
  */

End;

/
--by wxy 20090608
exec p_insertEntitySpec('方案实例','PROJECTINSTANCE','com.gxlu.ngrm.resService.ProjectInstance','X_PROJECTINSTANCE','');
exec p_insertM2OneRelation('任务对应的方案实例','TASK2PROJECTINSTANCE','TASK','PROJECTINSTANCE','PROJECTINSTANCEID','ID','B');

--by chenlj 20090605
exec p_Insertentityspec('用户自定义拓扑图级联','MAPCASCADEUSERDEF','com.gxlu.ngrm.equipment.MAPCascadeUserDef','XB_MAPCASCADEUSERDEF','');
exec p_Insertentitydescriptor('MAPCASCADEUSERDEF','XB_MAPCASCADEUSERDEF');

EXEC p_insertM2OneRelation('用户自定义拓扑图级联对应父MAP','MAPCASCADEUSERDEF2PARENTMAP','MAPCASCADEUSERDEF','MAP','PARENTID','ID','B');
EXEC p_insertM2OneRelation('用户自定义拓扑图级联对应子MAP','MAPCASCADEUSERDEF2CHILDMAP','MAPCASCADEUSERDEF','MAP','CHILDID','ID','B');
EXEC p_insertM2OneRelation('用户自定义拓扑图级联对应节点','MAPCASCADEUSERDEF2NODE','MAPCASCADEUSERDEF','MAPNODE','MAPNODEID','ID','B');
EXEC p_insertM2OneRelation('用户自定义拓扑图级联对应线','MAPCASCADEUSERDEF2LINE','MAPCASCADEUSERDEF','MAPLINE','MAPLINEID','ID','B');

EXEC p_insertM2OneRelation('拓扑图连线所属拓扑图','MAPLINE2MAP','MAPLINE','MAP','MAPID','ID','B');
EXEC p_insertM2OneRelation('拓扑图连线对应A端节点','MAPLINE2ANODE','MAPLINE','MAPNODE','AMAPNODEID','ID','B');
EXEC p_insertM2OneRelation('拓扑图连线对应Z端节点','MAPLINE2ZNODE','MAPLINE','MAPNODE','ZMAPNODEID','ID','B');

EXEC p_insertM2OneRelation('拓扑图节点所在行政区域','MAPNODE2MANAGEMENTAREA','MAPNODE','MANAGEMENTAREA','REGIONID','ID','B');
EXEC p_insertM2OneRelation('拓扑图节点对应父节点','MAPNODE2PARENTNODE','MAPNODE','MAPNODE','PARENTNODEID','ID','B');
EXEC p_insertM2OneRelation('拓扑图节点所属拓扑图','MAPNODE2MAP','MAPNODE','MAP','MAPID','ID','B');
EXEC p_insertM2OneRelation('拓扑图节点对应节点类型','MAPNODE2NODETYPE','MAPNODE','MAPNODETYPE','MAPNODETYPEID','ID','B');
EXEC p_insertM2OneRelation('任务对应上一次任务','TASK2PRETASK','TASK','TASK','PRETASKID','ID','B');
commit;
--BY WXY 20090608
Update X_versioninstance Set subcategory = 'SERSPEC2SERSPECRESSPECASSIGN' Where subcategory='SERSPECRESSPECASSIGN2RESSERVICESPEC';
Update xm_relationspec Set code = 'SERSPEC2SERSPECRESSPECASSIGN' Where code='SERSPECRESSPECASSIGN2RESSERVICESPEC';
commit;

--by wxy 20090609
EXEC p_insertentitydescriptor('CODEPOOL','X_CODEPOOL');
EXEC p_insertentitydescriptor('IVPNNO','X_IVPNNO');
EXEC p_insertentitydescriptor('LOGICALAREAMAPPING','X_LOGICALAREAMAPPING');
EXEC p_insertentitydescriptor('CODE','XB_CODE');
commit;


--by chenlj 20090609
exec p_Insertentityspec('图标库','IMAGEREPOSITORY','com.gxlu.ngrm.equipment.ImageRepository','XB_IMAGEREPOSITORY','');
EXEC p_insertEntityDescriptor('IMAGEREPOSITORY','XB_IMAGEREPOSITORY');
EXEC p_insertM2OneRelation('拓扑图节点所在管理维护区','MAPNODE2MANAGEMENTAREA','MAPNODE','MANAGEMENTAREA','LOCATIONID','ID','B');
commit;
--BY WXY 20090609
exec p_InsertEntitySpec('电源板','POWERCARD','com.gxlu.ngrm.equipment.PowerCard','XB_CARD','');
exec p_InsertEntitySpec('公务板','ORDERWIRECARD','com.gxlu.ngrm.equipment.OrderWireCard','XB_CARD',''); 
exec p_InsertEntitySpec('监控板','SVCARD','com.gxlu.ngrm.equipment.SVCard','XB_CARD','');
exec p_InsertEntitySpec('控制板','SCCARD','com.gxlu.ngrm.equipment.SCCard','XB_CARD','');
exec p_InsertEntitySpec('时钟板','CLOCKCARD','com.gxlu.ngrm.equipment.ClockCard','XB_CARD','');
exec p_InsertEntitySpec('辅助通道板','AUXCARD','com.gxlu.ngrm.equipment.AuxCard','XB_CARD','');
exec p_InsertEntitySpec('Mux板','MUXCARD','com.gxlu.ngrm.equipment.MuxCard','XB_CARD','');
exec p_InsertEntitySpec('交叉连接板','CROSSCONNECTCARD','com.gxlu.ngrm.equipment.CrossConnectCard','XB_CARD','');
exec p_InsertEntitySpec('中继板','RELAYCARD','com.gxlu.ngrm.equipment.RelayCard','XB_CARD','');  
exec p_InsertEntitySpec('无线收发射板','WIRELESSCARD','com.gxlu.ngrm.equipment.WirelessCard','XB_CARD','');  
exec p_InsertEntitySpec('级联板','CASCADECARD','com.gxlu.ngrm.equipment.CascadeCard','XB_CARD','');
exec p_InsertEntitySpec('光支路板','OTRIBCARD','com.gxlu.ngrm.equipment.OTribCard','XB_CARD',''); 
exec p_InsertEntitySpec('光线路板','OLINECARD','com.gxlu.ngrm.equipment.OLineCard','XB_CARD','');
exec p_InsertEntitySpec('电支路板','ETRIBCARD','com.gxlu.ngrm.equipment.ETribCard','XB_CARD','');
exec p_InsertEntitySpec('电线路板','ELINECARD','com.gxlu.ngrm.equipment.ELineCard','XB_CARD','');
exec p_InsertEntitySpec('网络板','NETWORKCARD','com.gxlu.ngrm.equipment.NetworkCard','XB_CARD','');
exec p_InsertEntitySpec('信令板','SIGNALCARD','com.gxlu.ngrm.equipment.SignalCard','XB_CARD','');

Update Xm_Entityspec d Set parentid = 2 Where d.Coretablename='XB_CARD' And d.Maintainindicator=3;
commit;

--by wxy 20090612
exec p_Insertentitydescriptor('CODE','XB_CODE');

--by wxy 20090615
EXEC p_insertEntityDescriptor('MANAGEMENTAREA','XS_MANAGEMENTAREA');

Update Xm_Entityspec d Set d.Satellitetablename='XS_MANAGEMENTAREA',d.Stsequence=null Where d.code='MANAGEMENTAREA';
commit;

--by chenlj 20090616
exec p_InsertEntitySpec('DSLAM板','DSLAMCARD','com.gxlu.ngrm.equipment.DslamCard','XB_CARD','');

Update Xm_Entityspec d Set parentid = 2 Where d.Coretablename='XB_CARD' And d.Maintainindicator=3;
commit;

--by wxy 20090616
--metadata
Update xm_entityspec Set Name='渠道' Where code='CHANNEL';
Update xm_entityspec Set Name='维护管理区' Where code='MANAGEMENTAREA';

Update x_Versioninstance v Set v.Subcategory='CODEPOOL2MANAGEMENTAREA' Where v.Subcategory='CODEPOOL2LOCATION';
Update xm_relationspec Set Name = '号码池和维护管理区的关联',code = 'CODEPOOL2MANAGEMENTAREA',OWNERATTR = 'CODEPOOLID',SUPPLIERATTR = 'LOCATIONID' Where CODE = 'CODEPOOL2LOCATION';

Update x_Versioninstance v Set v.Subcategory='CODEPOOL2CHANNEL' Where v.Subcategory='CODEPOOLCHANNELASSIGN';
Update xm_relationspec Set Name = '号码池和渠道的关联',code = 'CODEPOOL2CHANNEL',Relationinstancetablename='XR_CODEPOOL2LOCATION',OWNERATTR = 'CODEPOOLID',SUPPLIERATTR = 'LOCATIONID'  Where CODE = 'CODEPOOLCHANNELASSIGN';
commit;

Update xm_relationspec Set OWNERATTR = 'CODEPOOLID',SUPPLIERATTR = 'CODEID' Where CODE = 'CODEPOOLCODEASSIGN';
Update xm_relationspec Set OWNERATTR = 'CODEID',SUPPLIERATTR = 'PSTNPORTID' Where CODE = 'CODEPSTNPORTRELATION';
Update xm_relationspec Set OWNERATTR = 'CODESEGMENTID',SUPPLIERATTR = 'PSTNNEID' Where CODE = 'CODESEGPSTNNERELATION';
Update xm_relationspec Set OWNERATTR = 'PHYSICALCONTAINERID',SUPPLIERATTR = 'CARDID' Where CODE = 'CONTAINERCARDASSIGN';
Update xm_relationspec Set OWNERATTR = 'IVPNNOID',SUPPLIERATTR = 'PSTNNEID' Where CODE = 'IVPNNOPSTNNERELATION';
Update xm_relationspec Set OWNERATTR = 'ALOCATIONID',SUPPLIERATTR = 'ZLOCATIONID' Where CODE = 'LOCATIONRELATION';
Update xm_relationspec Set OWNERATTR = 'LOCATIONID',SUPPLIERATTR = 'LOCATIONTYPEID' Where CODE = 'LOCATON2LOCATIONTYPE';
Update xm_relationspec Set OWNERATTR = 'SERVICEORDERID',SUPPLIERATTR = 'PRODINSID' Where CODE = 'ORDERTICKETPINSASSOCBAK';
Update xm_relationspec Set OWNERATTR = 'SERVICEORDERID',SUPPLIERATTR = 'PRODINSID' Where CODE = 'ORDERTICKETPRODINSASSOC';
Update xm_relationspec Set OWNERATTR = 'PARTYROLEID',SUPPLIERATTR = 'RESSERVICESPECID' Where CODE = 'PARTYROLERESSERVICESPEC';
Update xm_relationspec Set OWNERATTR = 'PARTYROLEID',SUPPLIERATTR = 'TASKID' Where CODE = 'PARTYROLETASKASSIGN';
Update xm_relationspec Set OWNERATTR = 'PRODINSID',SUPPLIERATTR = 'RESSERVINSID' Where CODE = 'PRODINSRESSERINSASSOCBAK';
Update xm_relationspec Set OWNERATTR = 'PRODINSID',SUPPLIERATTR = 'RESSERVINSID' Where CODE = 'PRODINSRESSERVINSASSOC';
Update xm_relationspec Set OWNERATTR = 'PARENTID',SUPPLIERATTR = 'CHILDID' Where CODE = 'RESSERVINSASSIGN';
Update xm_relationspec Set OWNERATTR = 'PARENTID',SUPPLIERATTR = 'CHILDID' Where CODE = 'RESSERVINSASSIGNBAK';
Update xm_relationspec Set OWNERATTR = 'ROOMID',SUPPLIERATTR = 'SUITEID' Where CODE = 'ROOM2SUITE';
COMMIT;

--BY WXY 20090616
delete from XM_ENTITYDESCRIPTOR d where d.ENTITYSPECID=(select ID from XM_ENTITYSPEC e where e.CODE='PSTNLOGICALCODE') and d.COLUMNNAME='STATUS';
delete from XM_ENTITYDESCRIPTOR d where d.ENTITYSPECID=(select ID from XM_ENTITYSPEC e where e.CODE='PHSLOGICALCODE') and d.COLUMNNAME='STATUS';
COMMIT;
update XM_ENTITYDESCRIPTOR d set d.DICCLASSNAME='CODE' ,d.DICATTRIBUTENAME='SERVICESTATUS' 
 where d.ENTITYSPECID=(select id from XM_ENTITYSPEC e where e.CODE='CODE') and d.COLUMNNAME='SERVICESTATUS';
COMMIT;

--by chenlj 20090617
exec p_insertentitydescriptor('PSTNLOGICALCODE','XS_PSTNLOGICALCODE');
exec p_insertentitydescriptor('PSTNCODESEGMENT','XS_PSTNCODESEGMENT');
exec p_insertentitydescriptor('PROJECTINSTANCE','X_PROJECTINSTANCE');
EXEC p_insertM2OneRelation('方案实例对应的资源服务实例','PROJECTINS2RESSERVINS','PROJECTINSTANCE','RESSERVINS','SERVICEINSID','ID','');
commit;
--BY WXY 20090617
exec p_insertEntityDescriptor('PHSSIM','X_CARD');
exec p_insertM2NRelation('小灵通卡和号码的关系','PHSSIM2PHSLOGICALCODE','XR_CARDCODEASSIGN','PHSSIM','PHSLOGICALCODE','CARDID','CODEID');

Delete From Xm_Entitydescriptor d Where d.Columnname='UTKID' and entityspecid=2;
Commit;

--by chenlj 20090618
EXEC p_insertentitydescriptor('PRODINS','XB_PRODINS');
EXEC p_insertentitydescriptor('RESSERVINS','XB_RESSERVINS');
commit;
EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
exec p_Insertentityspec('割接导入表','MIGRATIONIMPORT','com.gxlu.ngrm.resService.MigrationImport','XB_MIGRATIONIMPORT','');
exec p_Insertentitydescriptor('MIGRATIONIMPORT','XB_MIGRATIONIMPORT');
UPDATE XM_ENTITYDESCRIPTOR A 
SET DICCLASSNAME=
        (SELECT DICCLASSNAME 
         FROM XM_ENTITYDESCRIPTOR B 
         WHERE B.COLUMNNAME=A.COLUMNNAME 
         AND ENTITYSPECID=
           (SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MIGRATIONRESULTDETAIL'))
WHERE A.ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MIGRATIONIMPORT');

UPDATE XM_ENTITYDESCRIPTOR A 
SET DICATTRIBUTENAME=
        (SELECT DICATTRIBUTENAME 
         FROM XM_ENTITYDESCRIPTOR B 
         WHERE B.COLUMNNAME=A.COLUMNNAME 
         AND ENTITYSPECID=
           (SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MIGRATIONRESULTDETAIL'))
WHERE A.ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MIGRATIONIMPORT');
commit;


--by wxy 20090618
exec p_InsertEntitySpec('组群号','GROUPNOCODE','com.gxlu.ngrm.code.GroupNoCode','XB_CODE','');

Update Xm_Entityspec d Set parentid = (select id from xm_entityspec where code = 'CODE') 
 Where d.Coretablename='XB_CODE' And d.Maintainindicator=3 AND PARENTID IS NULL;
commit;

exec p_insertM2NRelation('组群号和交换机之间的关联','GROUPNOCODEPSTNNERELATION','XR_GROUPNOCODEPSTNNERELATION','GROUPNOCODE','PSTNNE','GROUPNOCODEID','PSTNNEID');
commit;

EXEC p_insertM2OneRelation('虚拟网网号对应的组群号','IVPNNO2GROUPNOCODE','IVPNNO','GROUPNOCODE','GROUPNOID','ID','');
COMMIT;


exec p_insertentitydescriptor('SERVICEORDER','X_SERVICEORDER');

exec p_Insertentityspec('连接类型','CROSSCONNECTIONTYPE','com.gxlu.ngrm.equipment.CrossConnectionType','XB_CROSSCONNECTIONTYPE','');
EXEC p_insertEntityDescriptor('CROSSCONNECTIONTYPE','XB_CROSSCONNECTIONTYPE');

--by chenlj 20090619
exec p_insertentitydescriptor('MIGRATIONACTIONDETAIL','XB_MIGRATIONACTIONDETAIL');

update xm_componentdescriptor set columnname='DEVICENO',ATTRNAME='DEVICENO' WHERE COLUMNNAME='LOGICALNO'
AND componentspecid=(select id from xm_componentspec where code='PSTNPORT');
COMMIT;


--BY WXY 20090619
update xm_entitydescriptor d set d.dicclassname='RESSERVINS',d.dicattributename='STATUS' where d.entityspecid=(select id from xm_entityspec where code='RESSERVINS') and d.columnname='STATUS';
COMMIT;


--by wxy 20090620
Delete From xm_entitydescriptor d
 Where d.entityspecid = (Select Id From xm_entityspec Where code='CHANNEL')
   And d.columnname='ID';
Update xm_entitydescriptor D Set D.ISPK=1,d.isfk=2 
 Where d.entityspecid = (Select Id From xm_entityspec Where code='CHANNEL')
   And d.columnname='ENTITYID';
Commit;


--by wxy
--号码池和小灵通号码，号码等级和小灵通号码，协议号和小灵通号码
--BY WXY 20090620
--comment on column XS_PSTNLOGICALCODE.ENTITYID IS '所属实体';
--comment on column XS_PSTNLOGICALCODE.ID is '';

Delete From XM_ENTITYDESCRIPTOR d 
Where d.entityspecid=(Select Id From xm_entityspec e Where e.code='PSTNLOGICALCODE')
  And d.columnname='ID';

Delete From XM_ENTITYDESCRIPTOR d 
Where d.entityspecid=(Select Id From xm_entityspec e Where e.code='PHSLOGICALCODE')
  And d.columnname='ID';

EXEC p_insertEntityDescriptor('PHSLOGICALCODE','XS_PSTNLOGICALCODE');

update XM_ENTITYDESCRIPTOR d set ispk=1,isfk=2 
 where d.entityspecid=(Select Id From xm_entityspec e Where e.code='PHSLOGICALCODE')
   and d.columnname = 'ENTITYID';
COMMIT;

 
Update xm_relationspec Set Name = '固网号码对应的协议号' where code = 'PSTNLOGICALCODE2CODEPROTOCOLNO';
Update xm_relationspec Set Name = '固网号码对应的号码等级' where code = 'PSTNLOGICALCODE2CODELEVEL';

EXEC p_insertM2OneRelation('小灵通号码对应的协议号','PHSLOGICALCODE2CODEPROTOCOLNO','PHSLOGICALCODE','CODEPROTOCOLNO','CODEPROTOCOLNOID','ID','S');
EXEC p_insertM2OneRelation('小灵通号码对应的号码等级','PHSLOGICALCODE2CODELEVEL','PHSLOGICALCODE','CODELEVEL','CODELEVELID','ID','S');

update xm_relationspec d set d.relationinstancetablename='XS_PSTNLOGICALCODE' where d.code='PHSLOGICALCODE2CODEPROTOCOLNO';
update xm_relationspec d set d.relationinstancetablename='XS_PSTNLOGICALCODE' where d.code='PHSLOGICALCODE2CODELEVEL';
COMMIT;

--号码池和号码的关系重复
delete from xm_entityspec2relationspec d where d.relationspecid=(select id from xm_relationspec r where r.code='CODEPOOL2CODE');
delete from x_versioninstance d where d.subcategory='CODEPOOL2CODE';
delete from xm_relationspec where code='CODEPOOL2CODE';
commit;

EXEC p_insertentitydescriptor('CODEPOOL','X_CODEPOOL');

--by chenlj 20090622
EXEC p_insertentitydescriptor('SERVICEORDER','X_SERVICEORDER');
EXEC p_insertentitydescriptor('CODE','XB_CODE');
EXEC p_insertentitydescriptor('CODESEGMENT','X_CODESEGMENT');
EXEC p_insertentitydescriptor('PHYSICALCONTAINER','XB_PHYSICALCONTAINER');
EXEC p_insertentitydescriptor('CARD','XB_CARD');
EXEC p_insertentitydescriptor('MANAGEDELEMENT','XB_MANAGEDELEMENT');
EXEC p_insertentitydescriptor('LINK','XB_CROSSCONNECTION');
EXEC p_insertentitydescriptor('CROSSCONNECTION','XB_CROSSCONNECTION');

--by wxy 20090622
--将C网的维护管理区迁移到公共区域的营销区，把原来迁移到公共区域的维护管理区的数据删掉。

delete from XM_ENTITYDESCRIPTOR D WHERE D.ENTITYSPECID =(SELECT ID FROM XM_ENTITYSPEC E WHERE E.CODE='MANAGEMENTAREA');
commit;

Update Xm_Entityspec d Set d.Satellitetablename='XS_MARKETINGAREA',d.Stsequence=null Where d.code='MARKETINGAREA';
Update Xm_Entityspec d Set d.Satellitetablename=null,d.Stsequence=null Where d.code='MANAGEMENTAREA';
commit;
EXEC p_insertEntityDescriptor('MARKETINGAREA','XS_MARKETINGAREA');
commit;
--by chenlj 20090623
exec p_insertentitydescriptor('RESSERVINS','XB_RESSERVINS');
COMMIT;

--by wxy 20090623

Update x_Versioninstance v Set v.Subcategory='CODEPOOL2MARKETINGAREA' Where v.Subcategory='CODEPOOL2MANAGEMENTAREA';
Update xm_relationspec Set Name = '号码池和营销区的关联',code = 'CODEPOOL2MARKETINGAREA',SUPPLIERENTITYSPECID=(select id from xm_entityspec where code = 'MARKETINGAREA'),modifydate=sysdate Where CODE = 'CODEPOOL2MANAGEMENTAREA';

Update x_Versioninstance v Set v.Subcategory='CODESEGMENT2MARKETINGAREA' Where v.Subcategory='CODESEGMENT2LOCATION';
Update xm_relationspec Set Name = '号码段对应的营销区',code = 'CODESEGMENT2MARKETINGAREA',SUPPLIERENTITYSPECID=(select id from xm_entityspec where code = 'MARKETINGAREA'),modifydate=sysdate Where CODE = 'CODESEGMENT2LOCATION';

Update x_Versioninstance v Set v.Subcategory='CODE2MARKETINGAREA' Where v.Subcategory='PSTNLOGICALCODE2LOCATION';
Update xm_relationspec Set Name = '号码对应的营销区',code = 'CODE2MARKETINGAREA',SUPPLIERENTITYSPECID=(select id from xm_entityspec where code = 'MARKETINGAREA'),modifydate=sysdate Where CODE = 'PSTNLOGICALCODE2LOCATION';

Update x_Versioninstance v Set v.Subcategory='CODELEVEL2MARKETINGAREA' Where v.Subcategory='CODELEVEL2LOCATION';
Update xm_relationspec Set Name = '号码等级对应的营销区',code = 'CODELEVEL2MARKETINGAREA',SUPPLIERENTITYSPECID=(select id from xm_entityspec where code = 'MARKETINGAREA'),modifydate=sysdate Where CODE = 'CODELEVEL2LOCATION';

Update x_Versioninstance v Set v.Subcategory='CODERULE2MARKETINGAREA' Where v.Subcategory='CODERULE2LOCATION';
Update xm_relationspec Set Name = '号码规则对应的营销区',code = 'CODERULE2MARKETINGAREA',SUPPLIERENTITYSPECID=(select id from xm_entityspec where code = 'MARKETINGAREA'),modifydate=sysdate Where CODE = 'CODERULE2LOCATION';

Update x_Versioninstance v Set v.Subcategory='CODEPROTOCOLNO2MARKETINGAREA' Where v.Subcategory='CODEPROTOCOLNO2LOCATION';
Update xm_relationspec Set Name = '号码协议号对应的营销区',code = 'CODEPROTOCOLNO2MARKETINGAREA',SUPPLIERENTITYSPECID=(select id from xm_entityspec where code = 'MARKETINGAREA'),modifydate=sysdate Where CODE = 'CODEPROTOCOLNO2LOCATION';
commit;

update xm_entitydescriptor d set d.columnname='MARKETINGAREAID',d.attrname='MARKETINGAREAID' where d.entityspecid=(select id from xm_entityspec e where e.code='CHANNEL') and d.columnname='MANAGEMENTAREAID';
commit;

Update x_Versioninstance v Set v.Subcategory='CHANNEL2MARKETINGAREA' Where v.Subcategory='CHANNEL2MANAGEMENTAREA';
Update xm_relationspec Set Name = '渠道对应的营销区',code = 'CHANNEL2MARKETINGAREA',SUPPLIERENTITYSPECID=(select id from xm_entityspec where code = 'MARKETINGAREA'),modifydate=sysdate Where CODE = 'CHANNEL2MANAGEMENTAREA';
commit;

update xm_relationspec set name = replace(name,'放号区','渠道') where name like '%放号区%';
commit;

exec p_insertentitydescriptor('PSTNLOGICALCODE','XS_PSTNLOGICALCODE');
exec p_insertentitydescriptor('PHSLOGICALCODE','XS_PSTNLOGICALCODE');


--BY WXY 20090624
--EXEC p_insertEntityDescriptor
/*
Select 'EXEC p_insertEntityDescriptor('''||d.code||''',''XS_'''||d.code||');' 
  From xm_entityspec d 
 Where d.satellitetablename Is Null And d.parentid Is Not Null
  Order By d.parentid
*/
Delete From xm_entitydescriptor d Where d.entityspecid=(select id from xm_entityspec where code='PSTNPORT');
commit;
Delete From xm_entitydescriptor d Where d.entityspecid=31; --site
commit; 
Delete From xm_entitydescriptor d Where d.entityspecid=32; --room
commit;

EXEC p_insertEntityDescriptor('SLOT','XS_SLOT');
EXEC p_insertEntityDescriptor('RACK','XS_RACK');
EXEC p_insertEntityDescriptor('SHELF','XS_SHELF');
EXEC p_insertEntityDescriptor('CHASSIS','XS_CHASSIS');
EXEC p_insertEntityDescriptor('POWERSUPPLY','XS_POWERSUPPLY');
EXEC p_insertEntityDescriptor('CONNECTMODULE','XS_CONNECTMODULE');
EXEC p_insertEntityDescriptor('SIMULATEUSERCARD','XS_SIMULATEUSERCARD');
EXEC p_insertEntityDescriptor('ISDNCARD','XS_ISDNCARD');
EXEC p_insertEntityDescriptor('DSLAMCARD','XS_DSLAMCARD');
EXEC p_insertEntityDescriptor('IAD','XS_IAD');
EXEC p_insertEntityDescriptor('AG','XS_AG');
EXEC p_insertEntityDescriptor('IDF','XS_IDF');
EXEC p_insertEntityDescriptor('IDP','XS_IDP');
EXEC p_insertEntityDescriptor('ONU','XS_ONU');
EXEC p_insertEntityDescriptor('LAN','XS_LAN');
EXEC p_insertEntityDescriptor('REMOTEMODULE','XS_REMOTEMODULE');
EXEC p_insertEntityDescriptor('EXCHANGE','XS_EXCHANGE');
EXEC p_insertEntityDescriptor('DSLAM','XS_DSLAM');
EXEC p_insertEntityDescriptor('BAS','XS_BAS');
EXEC p_insertEntityDescriptor('PSTNNE','XS_PSTNNE');
EXEC p_insertEntityDescriptor('ADSL2PORT','XS_ADSL2PORT');
EXEC p_insertEntityDescriptor('VLANPORT','XS_VLANPORT');
EXEC p_insertEntityDescriptor('ADSLPORT','XS_ADSLPORT');
EXEC p_insertEntityDescriptor('SNPORT','XS_SNPORT');
EXEC p_insertEntityDescriptor('OCONNECTOR','XS_OCONNECTOR');
EXEC p_insertEntityDescriptor('ECONNECTOR','XS_ECONNECTOR');
EXEC p_insertEntityDescriptor('LANPORT','XS_LANPORT');
EXEC p_insertEntityDescriptor('CSXL','XS_CSXL');
EXEC p_insertEntityDescriptor('PSTNPORT','XS_PSTNPORT');
EXEC p_insertEntityDescriptor('HDSLPORT','XS_HDSLPORT');
EXEC p_insertEntityDescriptor('VDSLPORT','XS_VDSLPORT');
EXEC p_insertEntityDescriptor('XDSLCON','XS_XDSLCON');
EXEC p_insertEntityDescriptor('PSTNCON','XS_PSTNCON');
EXEC p_insertEntityDescriptor('MANAGEMENTAREA','XS_MANAGEMENTAREA');
EXEC p_insertEntityDescriptor('OUTDOORADDRESS','XS_OUTDOORADDRESS');
EXEC p_insertEntityDescriptor('SITE','XS_SITE');
EXEC p_insertEntityDescriptor('ROOM','XS_ROOM');
EXEC p_insertEntityDescriptor('SUITE','XS_SUITE');
EXEC p_insertEntityDescriptor('PHYCODECONFIG','XS_PHYCODECONFIG');
EXEC p_insertEntityDescriptor('PSTNPHYSICALCODE','XS_PSTNPHYSICALCODE');
EXEC p_insertEntityDescriptor('GROUPNOCODE','XS_GROUPNOCODE');
commit;

--modify Xm_Entityspec
/*
Select 'Update Xm_Entityspec d Set d.Satellitetablename=''XS_'||CODE||''' Where d.satellitetablename Is Null And d.code = '''||d.code||''';' 
  From xm_entityspec d 
 Where d.satellitetablename Is Null And d.parentid Is Not Null
  Order By d.parentid
*/

Update Xm_Entityspec d Set d.Satellitetablename='XS_SHELF' Where d.satellitetablename Is Null And d.code = 'SHELF';
Update Xm_Entityspec d Set d.Satellitetablename='XS_CONNECTMODULE' Where d.satellitetablename Is Null And d.code = 'CONNECTMODULE';
Update Xm_Entityspec d Set d.Satellitetablename='XS_RACK' Where d.satellitetablename Is Null And d.code = 'RACK';
Update Xm_Entityspec d Set d.Satellitetablename='XS_SLOT' Where d.satellitetablename Is Null And d.code = 'SLOT';
Update Xm_Entityspec d Set d.Satellitetablename='XS_CHASSIS' Where d.satellitetablename Is Null And d.code = 'CHASSIS';
Update Xm_Entityspec d Set d.Satellitetablename='XS_POWERSUPPLY' Where d.satellitetablename Is Null And d.code = 'POWERSUPPLY';
Update Xm_Entityspec d Set d.Satellitetablename='XS_DSLAMCARD' Where d.satellitetablename Is Null And d.code = 'DSLAMCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_SIMULATEUSERCARD' Where d.satellitetablename Is Null And d.code = 'SIMULATEUSERCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_ISDNCARD' Where d.satellitetablename Is Null And d.code = 'ISDNCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_IAD' Where d.satellitetablename Is Null And d.code = 'IAD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_ONU' Where d.satellitetablename Is Null And d.code = 'ONU';
Update Xm_Entityspec d Set d.Satellitetablename='XS_LAN' Where d.satellitetablename Is Null And d.code = 'LAN';
Update Xm_Entityspec d Set d.Satellitetablename='XS_REMOTEMODULE' Where d.satellitetablename Is Null And d.code = 'REMOTEMODULE';
Update Xm_Entityspec d Set d.Satellitetablename='XS_EXCHANGE' Where d.satellitetablename Is Null And d.code = 'EXCHANGE';
Update Xm_Entityspec d Set d.Satellitetablename='XS_DSLAM' Where d.satellitetablename Is Null And d.code = 'DSLAM';
Update Xm_Entityspec d Set d.Satellitetablename='XS_BAS' Where d.satellitetablename Is Null And d.code = 'BAS';
Update Xm_Entityspec d Set d.Satellitetablename='XS_PSTNNE' Where d.satellitetablename Is Null And d.code = 'PSTNNE';
Update Xm_Entityspec d Set d.Satellitetablename='XS_IDP' Where d.satellitetablename Is Null And d.code = 'IDP';
Update Xm_Entityspec d Set d.Satellitetablename='XS_AG' Where d.satellitetablename Is Null And d.code = 'AG';
Update Xm_Entityspec d Set d.Satellitetablename='XS_IDF' Where d.satellitetablename Is Null And d.code = 'IDF';
Update Xm_Entityspec d Set d.Satellitetablename='XS_HDSLPORT' Where d.satellitetablename Is Null And d.code = 'HDSLPORT';
Update Xm_Entityspec d Set d.Satellitetablename='XS_VDSLPORT' Where d.satellitetablename Is Null And d.code = 'VDSLPORT';
Update Xm_Entityspec d Set d.Satellitetablename='XS_ADSL2PORT' Where d.satellitetablename Is Null And d.code = 'ADSL2PORT';
Update Xm_Entityspec d Set d.Satellitetablename='XS_OCONNECTOR' Where d.satellitetablename Is Null And d.code = 'OCONNECTOR';
Update Xm_Entityspec d Set d.Satellitetablename='XS_LANPORT' Where d.satellitetablename Is Null And d.code = 'LANPORT';
Update Xm_Entityspec d Set d.Satellitetablename='XS_ECONNECTOR' Where d.satellitetablename Is Null And d.code = 'ECONNECTOR';
Update Xm_Entityspec d Set d.Satellitetablename='XS_VLANPORT' Where d.satellitetablename Is Null And d.code = 'VLANPORT';
Update Xm_Entityspec d Set d.Satellitetablename='XS_ADSLPORT' Where d.satellitetablename Is Null And d.code = 'ADSLPORT';
Update Xm_Entityspec d Set d.Satellitetablename='XS_SNPORT' Where d.satellitetablename Is Null And d.code = 'SNPORT';
Update Xm_Entityspec d Set d.Satellitetablename='XS_PSTNPORT' Where d.satellitetablename Is Null And d.code = 'PSTNPORT';
Update Xm_Entityspec d Set d.Satellitetablename='XS_CSXL' Where d.satellitetablename Is Null And d.code = 'CSXL';
Update Xm_Entityspec d Set d.Satellitetablename='XS_XDSLCON' Where d.satellitetablename Is Null And d.code = 'XDSLCON';
Update Xm_Entityspec d Set d.Satellitetablename='XS_PSTNCON' Where d.satellitetablename Is Null And d.code = 'PSTNCON';
Update Xm_Entityspec d Set d.Satellitetablename='XS_OUTDOORADDRESS' Where d.satellitetablename Is Null And d.code = 'OUTDOORADDRESS';
Update Xm_Entityspec d Set d.Satellitetablename='XS_MANAGEMENTAREA' Where d.satellitetablename Is Null And d.code = 'MANAGEMENTAREA';
Update Xm_Entityspec d Set d.Satellitetablename='XS_SITE' Where d.satellitetablename Is Null And d.code = 'SITE';
Update Xm_Entityspec d Set d.Satellitetablename='XS_ROOM' Where d.satellitetablename Is Null And d.code = 'ROOM';
Update Xm_Entityspec d Set d.Satellitetablename='XS_SUITE' Where d.satellitetablename Is Null And d.code = 'SUITE';
Update Xm_Entityspec d Set d.Satellitetablename='XS_PHYCODECONFIG' Where d.satellitetablename Is Null And d.code = 'PHYCODECONFIG';
Update Xm_Entityspec d Set d.Satellitetablename='XS_GROUPNOCODE' Where d.satellitetablename Is Null And d.code = 'GROUPNOCODE';
Update Xm_Entityspec d Set d.Satellitetablename='XS_PSTNPHYSICALCODE' Where d.satellitetablename Is Null And d.code = 'PSTNPHYSICALCODE';
commit;
--by chenlj 20090624
EXEC p_insertEntitySpec('FTTH配ONU设备','FTTHCONFIG','com.gxlu.ngrm.resService.FTTHConfig','XB_TASK','XS_FTTHCONFIG');
EXEC p_insertEntityDescriptor('FTTHCONFIG','XS_FTTHCONFIG');  
commit;
EXEC p_insertEntitySpec('FTTH配ONU端口','FTTHPORTCONFIG','com.gxlu.ngrm.resService.FTTHPortConfig','XB_TASK','XS_FTTHPORTCONFIG');
EXEC p_insertEntityDescriptor('FTTHPORTCONFIG','XS_FTTHPORTCONFIG'); 
COMMIT;
EXEC p_insertEntitySpec('ONU型号','ONUMODELINI','com.gxlu.ngrm.resService.ONUModelIni','XB_ONUMODELINI','');
EXEC p_insertEntityDescriptor('ONUMODELINI','XB_ONUMODELINI'); 
COMMIT;  
exec p_Insertentityspec('改资源中间表','MODIFYDETAIL','com.gxlu.ngrm.resService.ModifyDetail','XB_MODIFYDETAIL','');
exec p_Insertentitydescriptor('MODIFYDETAIL','XB_MODIFYDETAIL');
commit;

--by wxy 20090625

UPDATE X_DICTIONARY XD SET VALUEEN = (SELECT CLASSNAME FROM XM_ENTITYSPEC XE WHERE XE.NAME = XD.DESCRIPTION) WHERE XD.ATTRIBUTEID = 'HOLDERSPEC';
commit;

--by chenlj 20090626
delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='PSTNCONFIG');
EXEC p_insertEntityDescriptor('PSTNCONFIG','XS_PSTNCONFIG');
commit;
delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='ADSLCONFIG');
EXEC p_insertEntityDescriptor('ADSLCONFIG','XS_ADSLCONFIG');  
commit;
update XM_ENTITYDESCRIPTOR SET ATTRDISPNAME='所属营销区' 
WHERE ENTITYSPECID=(select id from xm_entityspec where code='PRODUCT') AND COLUMNNAME='LOCATIONID';
update XM_ENTITYDESCRIPTOR SET ATTRDISPNAME='所属营销区' 
WHERE ENTITYSPECID=(select id from xm_entityspec where code='RESSERVICESPEC') AND COLUMNNAME='LOCATIONID';
COMMIT;

UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='TASK')
WHERE CODE IN ('FTTHCONFIG','FTTHPORTCONFIG');
COMMIT;

--by chenlj 20090701 
exec p_Insertentitydescriptor('PRODINS','XB_PRODINS');
commit;
DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='ONUCONFIG')
AND COLUMNNAME='ACCESSMODE';
commit;

--by chenlj 20090706
exec p_Insertentitydescriptor('PRODINS','XB_PRODINS');
commit;

EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
exec p_Insertentitydescriptor('MIGRATIONIMPORT','XB_MIGRATIONIMPORT');
exec p_Insertentitydescriptor('MODIFYDETAIL','XB_MODIFYDETAIL');
commit;

DELETE FROM XM_ENTITYDESCRIPTOR WHERE COLUMNNAME='NODEID' AND ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='NODEREF');
exec p_Insertentityspec('拓扑图连线连接','MAPLINELINK','com.gxlu.ngrm.equipment.MAPLineLink','XB_MAPLINE','');
update xm_entityspec set parentid=(select id from xm_entityspec where code='MAPLINE') WHERE CODE='MAPLINELINK';

exec p_Insertentityspec('拓扑图位置节点','MAPNODELOCATION','com.gxlu.ngrm.equipment.MAPNodeLocation','XB_MAPNODE','');
exec p_Insertentityspec('拓扑图容器节点','MAPNODECONTAINER','com.gxlu.ngrm.equipment.MAPNodeContainer','XB_MAPNODE','');
exec p_Insertentityspec('拓扑图网元节点','MAPNODEME','com.gxlu.ngrm.equipment.MAPNodeMe','XB_MAPNODE','');
update xm_entityspec set parentid=(select id from xm_entityspec where code='MAPNODE') WHERE CODE='MAPNODELOCATION';
update xm_entityspec set parentid=(select id from xm_entityspec where code='MAPNODE') WHERE CODE='MAPNODECONTAINER';
update xm_entityspec set parentid=(select id from xm_entityspec where code='MAPNODE') WHERE CODE='MAPNODEME';
COMMIT;
DELETE FROM XM_ENTITYDESCRIPTOR WHERE COLUMNNAME='NODECATEGORY' AND ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MAPNODE');
DELETE FROM XM_ENTITYDESCRIPTOR WHERE COLUMNNAME='LINECATEGORY' AND ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MAPLINE');
COMMIT;

--by wxy 20090701

EXEC p_insertEntitySpec('传输板','TRANSCARD','com.gxlu.ngrm.equipment.TransCard','XB_CARD','XS_TRANSCARD');
EXEC p_insertEntitySpec('处理板','HANDLECARD','com.gxlu.ngrm.equipment.HandleCard','XB_CARD','XS_HANDLECARD');
EXEC p_insertEntitySpec('接口板','INTFCARD','com.gxlu.ngrm.equipment.IntfCard','XB_CARD','XS_INTFCARD');
EXEC p_insertEntitySpec('其它板','OTHERCARD','com.gxlu.ngrm.equipment.OtherCard','XB_CARD','XS_OTHERCARD');
COMMIT;

EXEC p_insertEntityDescriptor('INTFCARD','XS_INTFCARD');
EXEC p_insertEntityDescriptor('POWERCARD','XS_POWERCARD');
EXEC p_insertEntityDescriptor('ORDERWIRECARD','XS_ORDERWIRECARD');
EXEC p_insertEntityDescriptor('SVCARD','XS_SVCARD');
EXEC p_insertEntityDescriptor('SCCARD','XS_SCCARD');
EXEC p_insertEntityDescriptor('CLOCKCARD','XS_CLOCKCARD');
EXEC p_insertEntityDescriptor('AUXCARD','XS_AUXCARD');
EXEC p_insertEntityDescriptor('MUXCARD','XS_MUXCARD');
EXEC p_insertEntityDescriptor('CROSSCONNECTCARD','XS_CROSSCONNECTCARD');
EXEC p_insertEntityDescriptor('RELAYCARD','XS_RELAYCARD');
EXEC p_insertEntityDescriptor('WIRELESSCARD','XS_WIRELESSCARD');
EXEC p_insertEntityDescriptor('CASCADECARD','XS_CASCADECARD');
EXEC p_insertEntityDescriptor('OTRIBCARD','XS_OTRIBCARD');
EXEC p_insertEntityDescriptor('OLINECARD','XS_OLINECARD');
EXEC p_insertEntityDescriptor('ETRIBCARD','XS_ETRIBCARD');
EXEC p_insertEntityDescriptor('ELINECARD','XS_ELINECARD');
EXEC p_insertEntityDescriptor('NETWORKCARD','XS_NETWORKCARD');
EXEC p_insertEntityDescriptor('SIGNALCARD','XS_SIGNALCARD');
EXEC p_insertEntityDescriptor('TRANSCARD','XS_TRANSCARD');
EXEC p_insertEntityDescriptor('HANDLECARD','XS_HANDLECARD');
EXEC p_insertEntityDescriptor('OTHERCARD','XS_OTHERCARD');
commit;

Update Xm_Entityspec d Set d.Satellitetablename='XS_INTFCARD' Where d.satellitetablename Is Null And d.code = 'INTFCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_POWERCARD' Where d.satellitetablename Is Null And d.code = 'POWERCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_ORDERWIRECARD' Where d.satellitetablename Is Null And d.code = 'ORDERWIRECARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_SVCARD' Where d.satellitetablename Is Null And d.code = 'SVCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_SCCARD' Where d.satellitetablename Is Null And d.code = 'SCCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_CLOCKCARD' Where d.satellitetablename Is Null And d.code = 'CLOCKCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_AUXCARD' Where d.satellitetablename Is Null And d.code = 'AUXCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_MUXCARD' Where d.satellitetablename Is Null And d.code = 'MUXCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_CROSSCONNECTCARD' Where d.satellitetablename Is Null And d.code = 'CROSSCONNECTCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_RELAYCARD' Where d.satellitetablename Is Null And d.code = 'RELAYCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_WIRELESSCARD' Where d.satellitetablename Is Null And d.code = 'WIRELESSCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_CASCADECARD' Where d.satellitetablename Is Null And d.code = 'CASCADECARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_OTRIBCARD' Where d.satellitetablename Is Null And d.code = 'OTRIBCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_OLINECARD' Where d.satellitetablename Is Null And d.code = 'OLINECARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_ETRIBCARD' Where d.satellitetablename Is Null And d.code = 'ETRIBCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_ELINECARD' Where d.satellitetablename Is Null And d.code = 'ELINECARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_NETWORKCARD' Where d.satellitetablename Is Null And d.code = 'NETWORKCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_SIGNALCARD' Where d.satellitetablename Is Null And d.code = 'SIGNALCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_TRANSCARD' Where d.satellitetablename Is Null And d.code = 'TRANSCARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_HANDLECARD' Where d.satellitetablename Is Null And d.code = 'HANDLECARD';
Update Xm_Entityspec d Set d.Satellitetablename='XS_OTHERCARD' Where d.satellitetablename Is Null And d.code = 'OTHERCARD';
commit;

UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='CARD')
WHERE CORETABLENAME ='XB_CARD' and MAINTAININDICATOR != 1 AND PARENTID IS NULL;
COMMIT;

INSERT INTO X_DICTIONARY(ID,CLASSID,ATTRIBUTEID,VALUE,DESCRIPTION,SEQNO,STATE) VALUES(S_X_DICTIONARY.NEXTVAL,'CARD','CARDSPEC',38,'传输板',38,1); 
INSERT INTO X_DICTIONARY(ID,CLASSID,ATTRIBUTEID,VALUE,DESCRIPTION,SEQNO,STATE) VALUES(S_X_DICTIONARY.NEXTVAL,'CARD','CARDSPEC',39,'处理板',39,1); 
INSERT INTO X_DICTIONARY(ID,CLASSID,ATTRIBUTEID,VALUE,DESCRIPTION,SEQNO,STATE) VALUES(S_X_DICTIONARY.NEXTVAL,'CARD','CARDSPEC',40,'接口板',40,1); 
INSERT INTO X_DICTIONARY(ID,CLASSID,ATTRIBUTEID,VALUE,DESCRIPTION,SEQNO,STATE) VALUES(S_X_DICTIONARY.NEXTVAL,'CARD','CARDSPEC',99,'其它板',99,1);
commit;

EXEC P_DICCATEGORY_DICTIONARY;

UPDATE X_DICTIONARY XD SET VALUEEN = (SELECT CLASSNAME FROM XM_ENTITYSPEC XE WHERE XE.NAME = XD.DESCRIPTION) 
 WHERE XD.ATTRIBUTEID = 'CARDSPEC' AND XD.VALUEEN IS NULL;
COMMIT;

update X_DICTIONARY set PARENTID=(select ID from X_DICTIONARY where CLASSID='METADATA' and ATTRIBUTEID='GROUPTYPE' and VALUE=2) 
 where CLASSID='CARD' and ATTRIBUTEID='CARDSPEC';
COMMIT;


Insert into XM_ENTITYSPEC
   (ID, NAME, CODE, ALIAS,  PARENTID, CORETABLENAME, CTSEQUENCE, SATELLITETABLENAME, STSEQUENCE, CLASSNAME, STATUS, MAINTAININDICATOR, CREATEDATE, MODIFYDATE, VERSIONINSTANCE, VERSION, INDICATORCOLUMNNAME, INDICATORCOLUMNVALUE, DATASOURCE)
 Values
   (1060, '局向', 'RESOURCECOVER', '局向',  9, 'XB_LOCATION', 'S_XB_LOCATION', 'XS_RESOURCECOVER', NULL, 'com.gxlu.ngrm.area.ResourceCover', 1, 2, TO_DATE('02/13/2009 11:16:17', 'MM/DD/YYYY HH24:MI:SS'), TO_DATE('02/13/2009 11:16:17', 'MM/DD/YYYY HH24:MI:SS'), 317, 1, 'CATEGORY', 'com.gxlu.ngrm.ne.RESOURCECOVER', 1);
Insert into XM_ENTITYSPEC
   (ID, NAME, CODE, ALIAS,  PARENTID, CORETABLENAME, CTSEQUENCE, SATELLITETABLENAME, STSEQUENCE, CLASSNAME, STATUS, MAINTAININDICATOR, CREATEDATE, MODIFYDATE, VERSIONINSTANCE, VERSION, INDICATORCOLUMNNAME, INDICATORCOLUMNVALUE, DATASOURCE)
 Values
   (1080, '土地', 'LAND', '土地',  10, 'XB_STRUCTURE', 'S_XB_STRUCTURE', 'XS_LAND', null, 'com.gxlu.ngrm.area.Land', 1, 2, TO_DATE('02/23/2009 10:47:16', 'MM/DD/YYYY HH24:MI:SS'), TO_DATE('02/23/2009 10:47:16', 'MM/DD/YYYY HH24:MI:SS'), 321, 1, 'CATEGORY', 'com.gxlu.ngrm.ne.LAND', 1);
Insert into XM_ENTITYSPEC
   (ID, NAME, CODE, ALIAS,  PARENTID, CORETABLENAME, CTSEQUENCE, SATELLITETABLENAME, STSEQUENCE, CLASSNAME, STATUS, MAINTAININDICATOR, CREATEDATE, MODIFYDATE, VERSIONINSTANCE, VERSION, INDICATORCOLUMNNAME, INDICATORCOLUMNVALUE, DATASOURCE)
 Values
   (1081, '房屋', 'BUILDING', '房屋',  10, 'XB_STRUCTURE', 'S_XB_STRUCTURE', 'XS_BUILDING', NULL, 'com.gxlu.ngrm.area.Building', 1, 2, TO_DATE('02/23/2009 10:59:15', 'MM/DD/YYYY HH24:MI:SS'), TO_DATE('02/23/2009 10:59:15', 'MM/DD/YYYY HH24:MI:SS'), 322, 1, 'CATEGORY', 'com.gxlu.ngrm.ne.BUILDING', 1);
Insert into XM_ENTITYSPEC
   (ID, NAME, CODE, ALIAS, PARENTID, CORETABLENAME, CTSEQUENCE, SATELLITETABLENAME, STSEQUENCE, CLASSNAME, STATUS, MAINTAININDICATOR, CREATEDATE, MODIFYDATE, VERSIONINSTANCE, VERSION, INDICATORCOLUMNNAME, INDICATORCOLUMNVALUE, DATASOURCE)
 Values
   (1083, '楼层', 'FLOOR', '楼层', 10, 'XB_STRUCTURE', 'S_XB_STRUCTURE', 'XS_FLOOR', NULL, 'com.gxlu.ngrm.area.Floor', 1, 2, TO_DATE('02/23/2009 11:17:13', 'MM/DD/YYYY HH24:MI:SS'), TO_DATE('02/23/2009 11:17:13', 'MM/DD/YYYY HH24:MI:SS'), 324, 1, 'CATEGORY', 'com.gxlu.ngrm.ne.FLOOR', 1);
COMMIT;


EXEC p_insertEntityDescriptor('LAND','XS_LAND');  
EXEC p_insertEntityDescriptor('BUILDING','XS_BUILDING');  
EXEC p_insertEntityDescriptor('FLOOR','XS_FLOOR');  
EXEC p_insertEntityDescriptor('SUITE','XS_SUITE');  
EXEC p_insertEntityDescriptor('RESOURCECOVER','XS_RESOURCECOVER');  
commit;

UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='STRUCTURE')
WHERE CODE in('LAND','BUILDING','FLOOR','SUITE');
COMMIT;

UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='LOCATION')
WHERE CODE in('RESOURCECOVER');
COMMIT;


declare
i number(5);
begin
	i := 1;
	for R in(Select NAME,CODE,CLASSNAME 
	           From XM_ENTITYSPEC  
					  Where PARENTID Is Null 
					    And MAINTAININDICATOR = 2 
					    And CODE Not Like '%TYPE'
						Order By ID) loop
	begin
		INSERT INTO X_DICTIONARY(ID,CLASSID,ATTRIBUTEID,VALUE,DESCRIPTION,SEQNO,STATE,VALUEEN) 			  
		 VALUES(S_X_DICTIONARY.NEXTVAL,'METADATA','OTHERGROUPTYPE',i,R.NAME,I,1,R.CLASSNAME);
		 exception when others then
		 	null;
	end;
	i := i + 1;
	end loop;

end;
/

EXEC P_DICCATEGORY_DICTIONARY;
COMMIT;



UPDATE X_DICTIONARY XD SET VALUEEN = (SELECT CLASSNAME FROM XM_ENTITYSPEC XE WHERE XE.NAME = XD.DESCRIPTION) WHERE XD.ATTRIBUTEID = 'NESPEC';
commit;


--by wxy 20090706
--PSTN交换机去掉
Update xm_relationspec d 
   Set d.supplierentityspecid=(Select Id From xm_entityspec e Where e.code='EXCHANGE')
 Where d.supplierentityspecid=(Select Id From xm_entityspec e Where e.code='PSTNNE');

update xm_entityspec2relationspec e2r 
   set e2r.entityspecid=(Select Id From xm_entityspec e Where e.code='EXCHANGE'),
       e2r.entityspecversioninstance=(Select xe.versioninstance From xm_entityspec xe Where xe.code='EXCHANGE')
 Where e2r.entityspecid=(Select Id From xm_entityspec e Where e.code='PSTNNE');
 
Delete From xm_entityspec2componentspec e2c 
 Where e2c.entityspecid=(Select Id From xm_entityspec e Where e.code='PSTNNE');

Delete From xm_entitydescriptor xe Where xe.entityspecid=(Select Id From xm_entityspec e Where e.code='PSTNNE');
Delete From xm_entityspec d Where d.code='PSTNNE';
Delete From x_versioninstance d Where d.category='EntitySpec' And d.subcategory='PSTNNE'; 

Commit;


--by wxy 20090706
exec p_insertEntitySpec('资源服务实例和资源实例的关联备份表','RESSERVINSRESINSASSOCHIS','com.gxlu.ngrm.resService.ResServInsResInsAssocHis','XB_RESSERVINSRESINSASSOCHIS','');
commit;

update xm_entitydescriptor d 
  set D.DICCLASSNAME='RESSERVINSRESINSASSOC' 
where d.entityspecid=(select id from xm_entityspec e where e.code='RESSERVINSRESINSASSOCHIS') 
  and d.columnname in('OPERFLAG','RESFLAG');
commit;

update xm_entityspec d set d.code = 'PRODUCTINSHIS' where d.code = 'PRODUCTINSBAK';
update xm_entityspec d set d.code = 'SERVPRODINSHIS' where d.code = 'SERVPRODINSBAK';
update xm_entityspec d set d.code = 'RESSERVINSHIS' where d.code = 'RESSERVINSBAK';
update xm_relationspec d set d.code = 'RESSERVINSASSIGNHIS' where d.code = 'RESSERVINSASSIGNBAK';
update xm_relationspec d set d.code = 'PRODINSRESSERINSASSOCHIS' where d.code = 'PRODINSRESSERINSASSOCBAK';
update xm_relationspec d set d.code = 'ORDERTICKETPRODINSASSOCHIS' where d.code = 'ORDERTICKETPINSASSOCBAK';
update xm_entityspec d set d.code = 'TICKETRESSERVICEASSOCHIS' where d.code = 'TICKETRESSERVICEASSOCBAK';
commit;

update x_versionInstance d set d.subcategory = 'PRODUCTINSHIS' where d.subcategory = 'PRODUCTINSBAK';
update x_versionInstance d set d.subcategory = 'SERVPRODINSHIS' where d.subcategory = 'SERVPRODINSBAK';
update x_versionInstance d set d.subcategory = 'RESSERVINSHIS' where d.subcategory = 'RESSERVINSBAK';
update x_versionInstance d set d.subcategory = 'RESSERVINSASSIGNHIS' where d.subcategory = 'RESSERVINSASSIGNBAK';
update x_versionInstance d set d.subcategory = 'PRODINSRESSERINSASSOCHIS' where d.subcategory = 'PRODINSRESSERINSASSOCBAK';
update x_versionInstance d set d.subcategory = 'ORDERTICKETPRODINSASSOCHIS' where d.subcategory = 'ORDERTICKETPINSASSOCBAK';
update x_versionInstance d set d.subcategory = 'TICKETRESSERVICEASSOCHIS' where d.subcategory = 'TICKETRESSERVICEASSOCBAK';
commit;


--by wxy 20090707
update x_dictionary d set d.parentid =(select d2.id from x_dictionary d2 where d2.classid='LOCATION' and d2.attributeid='GROUPCODE' and d2.description='维护管理区') where  CLASSID = 'LOCATION' and attributeid = 'LOCATIONSPEC' and description in('区域','站点','机房','设备安装点');
update x_dictionary d set d.parentid =(select d2.id from x_dictionary d2 where d2.classid='LOCATION' and d2.attributeid='GROUPCODE' and d2.description='营销区') where  CLASSID = 'LOCATION' and attributeid = 'LOCATIONSPEC' and description in('营销区');
Commit;
exec p_insertentitydescriptor('PORT','XB_PORT');
COMMIT;


exec p_insertentitydescriptor('SHELF','XS_SHELF');

UPDATE X_DICTIONARY d SET d.SEQNO = 5 WHERE  D.classid='LOCATION' And d.Attributeid='LOCATIONSPEC' and DESCRIPTION='区域';
UPDATE X_DICTIONARY d SET d.SEQNO = 6 WHERE  D.classid='LOCATION' And d.Attributeid='LOCATIONSPEC' and DESCRIPTION='站点';
UPDATE X_DICTIONARY d SET d.SEQNO = 7 WHERE  D.classid='LOCATION' And d.Attributeid='LOCATIONSPEC' and DESCRIPTION='机房';
UPDATE X_DICTIONARY d SET d.SEQNO = 8 WHERE  D.classid='LOCATION' And d.Attributeid='LOCATIONSPEC' and DESCRIPTION='设备安装点';
UPDATE X_DICTIONARY d SET d.SEQNO = 2 WHERE  D.classid='LOCATION' And d.Attributeid='LOCATIONSPEC' and DESCRIPTION='营销区';
UPDATE X_DICTIONARY d SET d.SEQNO = 9 WHERE  D.classid='LOCATION' And d.Attributeid='LOCATIONSPEC' and DESCRIPTION='户外安装地';

INSERT INTO X_DICTIONARY(ID,CLASSID,ATTRIBUTEID,VALUE,DESCRIPTION,SEQNO,STATE) VALUES(S_X_DICTIONARY.NEXTVAL,'LOCATION','LOCATIONSPEC',7,'放号区',3,1);
INSERT INTO X_DICTIONARY(ID,CLASSID,ATTRIBUTEID,VALUE,DESCRIPTION,SEQNO,STATE) VALUES(S_X_DICTIONARY.NEXTVAL,'LOCATION','LOCATIONSPEC',8,'资源覆盖区',4,1);
INSERT INTO X_DICTIONARY(ID,CLASSID,ATTRIBUTEID,VALUE,DESCRIPTION,SEQNO,STATE) VALUES(S_X_DICTIONARY.NEXTVAL,'LOCATION','LOCATIONSPEC',9,'维护管理区',1,1);

commit;
EXEC P_DICCATEGORY_DICTIONARY;

UPDATE X_DICTIONARY XD SET VALUEEN = (SELECT CLASSNAME FROM XM_ENTITYSPEC XE WHERE XE.NAME = XD.DESCRIPTION) WHERE XD.ATTRIBUTEID = 'LOCATIONSPEC' AND XD.VALUEEN IS NULL;
COMMIT;

--by chenlj 20090708
EXEC P_INSERTENTITYDESCRIPTOR('MAPNODELOCATION','XS_MAPNODELOCATION');
COMMIT;
EXEC P_INSERTENTITYDESCRIPTOR('MAPNODECONTAINER','XS_MAPNODECONTAINER');
COMMIT;
EXEC P_INSERTENTITYDESCRIPTOR('MAPNODEME','XS_MAPNODEME');
COMMIT;
Update Xm_Entityspec d Set d.Satellitetablename='XS_MAPNODELOCATION' Where d.satellitetablename Is Null And d.code = 'MAPNODELOCATION';
Update Xm_Entityspec d Set d.Satellitetablename='XS_MAPNODECONTAINER' Where d.satellitetablename Is Null And d.code = 'MAPNODECONTAINER';
Update Xm_Entityspec d Set d.Satellitetablename='XS_MAPNODEME' Where d.satellitetablename Is Null And d.code = 'MAPNODEME';
commit;
EXEC P_INSERTENTITYDESCRIPTOR('MAPLINELINK','XS_MAPLINELINK');
COMMIT;
Update Xm_Entityspec d Set d.Satellitetablename='XS_MAPLINELINK' Where d.satellitetablename Is Null And d.code = 'MAPLINELINK';
commit;

delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='MAPCASCADE');
delete from xm_entityspec2relationspec where relationspecid in (select id from xm_relationspec where code in ('MAPCASCADE2PARENTMAP','MAPCASCADE2CHILDMAP'));
delete from xm_relationspec where code in ('MAPCASCADE2PARENTMAP','MAPCASCADE2CHILDMAP');
delete from xm_entityspec where code='MAPCASCADE';
delete from x_versioninstance where subcategory='MAPCASCADE2PARENTMAP';
delete from x_versioninstance where subcategory='MAPCASCADE2CHILDMAP';
delete from x_versioninstance where subcategory='MAPCASCADE';
commit;

EXEC P_INSERTENTITYDESCRIPTOR('DOMAIN','XB_DOMAIN');
COMMIT;
EXEC P_INSERTENTITYDESCRIPTOR('NODEREF','XB_NODEREF');
COMMIT;
UPDATE XM_ENTITYDESCRIPTOR SET COLUMNTYPE=4,DATATYPE=4,DATALENGTH=255 WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='NODEREF') AND ATTRNAME='NODETYPE';
COMMIT;

DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MAPNODE') 
AND ATTRNAME IN ('ENTITYID','PARENTNODEID','SUBENTITYTYPE','LOCATIONID');
COMMIT;

exec p_insertM2NRelation('拓扑图级联关系','MAPCASCADE','XR_MAPCASCADE','MAP','MAP','PARENTMAPID','CHILDMAPID');
exec p_insertM2NRelation('拓扑图跳转关系','MAPREF','XR_MAPREF','MAPNODE','MAP','MAPNODEID','CHILDMAPID');
COMMIT;
--by chenlj 20090709
delete from xm_entityspec2relationspec where relationspecid in (select id from xm_relationspec where code in ('MAPNODE2PARENTNODE','MAPNODE2MANAGEMENTAREA','MAPCASCADE'));
delete from xm_relationspec where code in ('MAPNODE2PARENTNODE','MAPNODE2MANAGEMENTAREA','MAPCASCADE');
delete from x_versioninstance where subcategory in ('MAPNODE2PARENTNODE','MAPNODE2MANAGEMENTAREA','MAPCASCADE');
commit;
EXEC p_insertM2OneRelation('拓扑图位置节点所属位置','MAPNODELOCATION2LOCATION','MAPNODELOCATION','LOCATION','LOCATIONID','ENTITYID','S');
commit;
EXEC p_insertM2OneRelation('拓扑图容器节点所属容器','MAPNODECONTAINER2CONTAINER','MAPNODECONTAINER','PHYSICALCONTAINER','CONTAINERID','ENTITYID','S');
commit;
EXEC p_insertM2OneRelation('拓扑图网元节点所属网元','MAPNODEME2ME','MAPNODEME','MANAGEDELEMENT','MEID','ENTITYID','S');
commit;
EXEC p_insertM2OneRelation('域所在管理维护区','DOMAIN2MANAGEMENTAREA','DOMAIN','MANAGEMENTAREA','LOCATIONID','ID','B');
commit;

exec p_insertM2NRelation('拓扑图和子拓扑图的关系','MAP2CHILDMAP','XR_MAPCASCADE','MAP','MAP','PARENTMAPID','CHILDMAPID');
exec p_insertM2NRelation('拓扑图和父拓扑图的关系','MAP2PARENTMAP','XR_MAPCASCADE','MAP','MAP','CHILDMAPID','PARENTMAPID');
COMMIT;
exec p_Insertentityspec('更资源中间表','CHANGEDETAIL','com.gxlu.ngrm.resService.ChangeDetail','XB_CHANGEDETAIL','');
exec p_Insertentitydescriptor('CHANGEDETAIL','XB_CHANGEDETAIL');
commit;

--by wxy 20090709
exec p_InsertEntitySpec('资源覆盖区','RESCOVERAREA','com.gxlu.ngrm.area.ResCoverArea','XB_LOCATION','XS_RESCOVERAREA');

Update Xm_Entityspec d Set parentid = (select id from xm_entityspec where code = 'LOCATION') 
 Where d.Coretablename='XB_LOCATION' And d.Maintainindicator=3 AND PARENTID IS NULL;
commit;

UPDATE X_DICTIONARY XD SET VALUEEN = (SELECT CLASSNAME FROM XM_ENTITYSPEC XE WHERE XE.NAME = XD.DESCRIPTION) WHERE XD.ATTRIBUTEID = 'LOCATIONSPEC' AND XD.VALUEEN IS NULL;
COMMIT;

--by chenlj 20090710
delete from xm_entityspec2relationspec where relationspecid in (select id from xm_relationspec where code in ('NODEREF2ME','NODEREF2DOMAIN'));
delete from xm_relationspec where code in ('NODEREF2ME','NODEREF2DOMAIN');
delete from x_versioninstance where subcategory='NODEREF2DOMAIN';
delete from x_versioninstance where subcategory='NODEREF2ME';
commit;
exec p_Insertentitydescriptor('NODEREF','XB_NODEREF');
commit;
delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='NODEREF') AND COLUMNNAME='DOMAINID';
COMMIT;
EXEC p_insertM2OneRelation('域节点关联对应拓扑图','NODEREF2MAP','NODEREF','MAP','MAPID','ID','B');
commit;
exec p_Insertentitydescriptor('MAP','XB_MAP');
commit;
UPDATE XM_ENTITYSPEC SET NAME='拓扑图节点类型关联表' WHERE CODE='NODEREF';
COMMIT;
EXEC p_insertEntityDescriptor('CROSSCONNECTIONTYPE','XB_CROSSCONNECTIONTYPE');
commit;


--by wxy 20090710
exec p_insertentitydescriptor('DICTIONARYCATEGORY','X_DICTIONARYCATEGORY');
commit;


--去掉ME2PORT
Update Xm_Entityspec2relationspec d
	 Set d.Relationspecid = (Select Id From Xm_Relationspec r Where r.Code = 'PORT2ME'),
			 d.Relationspecversioninstance = (Select Xr.Versioninstance From Xm_Relationspec Xr Where Xr.Code = 'PORT2ME')
 Where d.Relationspecid = (Select Id From Xm_Relationspec Xrs Where Xrs.Code = 'ME2PORT');

delete from  x_Versioninstance where subcategory = 'ME2PORT';
delete from Xm_Relationspec Where Code = 'ME2PORT';
commit;


--by wxy 20090713
update xm_entitydescriptor d set d.columnname='LOCATIONCODE',d.attrname='LOCATIONCODE',d.attrdispname='区域编码' where d.entityspecid=(select id from xm_entityspec where code='RESCOVER') and d.columnname='RESOURCECOVERCODE';
update xm_entitydescriptor d set d.columnname='LOCATIONID',d.attrname='LOCATIONID',d.attrdispname='所属区域' where d.entityspecid=(select id from xm_entityspec where code='RESCOVER') and d.columnname='RESOURCECOVERID';
commit;

exec p_Insertentityspec('方案实例','PROJECTINSTANCE','com.gxlu.ngrm.resService.ProjectInstance','X_PROJECTINSTANCE','');
exec p_insertentitydescriptor('PROJECTINSTANCE','X_PROJECTINSTANCE');
exec p_insertM2OneRelation('方案实例对应的资源服务实例','PROJECTINS2RESSERVINS','PROJECTINSTANCE','RESSERVINS','SERVICEINSID','ID','');
commit;


--by wxy 20090714
exec p_insertEntityDescriptor('TASK','XB_TASK');
commit;
--by chenlj 20090715
exec p_Insertentityspec('产品共用的资源服务规格','PRODSERVSPECSHARE','com.gxlu.ngrm.resService.ProdServSpecShare','XB_PRODSERVSPECSHARE','');
exec p_insertentitydescriptor('PRODSERVSPECSHARE','XB_PRODSERVSPECSHARE');
commit;
exec p_insertentitydescriptor('PRODINS','XB_PRODINS');
commit;
exec p_Insertentityspec('拓扑图其他节点','MAPNODEMASC','com.gxlu.ngrm.equipment.MapNodeMasc','XB_MAPNODE','XS_MAPNODEMASC');
EXEC P_INSERTENTITYDESCRIPTOR('MAPNODEMASC','XS_MAPNODEMASC');
COMMIT;
UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MAPNODE') WHERE CODE='MAPNODEMASC';

EXEC P_INSERTENTITYDESCRIPTOR('MAPNODE','XB_MAPNODE');
EXEC p_insertM2OneRelation('拓扑图节点所在父节点','MAPNODE2PARENT','MAPNODE','MAPNODE','PARENTID','ID','B');
commit;
delete from xm_entitydescriptor where entityspecid =(select id from xm_entityspec where code='DOMAIN') AND COLUMNNAME='LOCATIONID';
DELETE FROM XM_ENTITYSPEC2RELATIONSPEC WHERE RELATIONSPECID=(SELECT ID FROM XM_RELATIONSPEC WHERE CODE='DOMAIN2MANAGEMENTAREA');
DELETE FROM XM_RELATIONSPEC WHERE CODE='DOMAIN2MANAGEMENTAREA';
delete from x_versioninstance where subcategory='DOMAIN2MANAGEMENTAREA';
COMMIT;

EXEC P_INSERTENTITYDESCRIPTOR('MAP','XB_MAP');
EXEC p_insertM2OneRelation('拓扑图所在管理维护区','MAP2MANAGEMENTAREA','MAP','MANAGEMENTAREA','LOCATIONID','ID','B');
commit;

--by wxy 20090716
Update xm_entityspec d Set d.satellitetablename='XS_MDFHCARD',d.stsequence=Null Where code='MDFHCARD';
Update xm_entityspec d Set d.satellitetablename='XS_MDFVCARD',d.stsequence=Null Where code='MDFVCARD';
Update xm_entityspec d Set d.satellitetablename='XS_IDFVCARD',d.stsequence=Null Where code='IDFVCARD';
Update xm_entityspec d Set d.satellitetablename='XS_IDFHCARD',d.stsequence=Null Where code='IDFHCARD';
Commit;

Update xm_entityspec d Set d.satellitetablename='XS_PHSLOGICALCODE',d.stsequence=Null Where code='PHSLOGICALCODE';
Commit;

Update xm_entityspec d Set d.satellitetablename='XS_PSTNPHYSICALCODESEGMENT',d.stsequence=Null Where code='PSTNPHYSICALCODESEGMENT';
Update xm_entityspec d Set d.satellitetablename='XS_PHSCODESEGMENT',d.stsequence=Null Where code='PHSCODESEGMENT';
commit;

--xdslport合并
Exec p_Insertentityspec('XDSL端口','XDSLPORT','com.gxlu.ngrm.equipment.XdslPort','XB_PORT','XS_XDSLPORT');
Exec p_Insertentitydescriptor('XDSLPORT','XS_XDSLPORT');

Update Xm_Entityspec Set Parentid = (Select Id From Xm_Entityspec Where Code = 'PORT') Where Code = 'XDSLPORT';
commit;

exec p_insertESpec2CSpec('XDSLPORT','XDSLPORT');
commit;

Delete From xm_entityspec2componentspec d Where d.entityspecid In (Select Id From xm_entityspec e Where e.code In ('ADSLPORT','ADSL2PORT','VDSLPORT','HDSLPORT'));
Delete From xm_entitydescriptor d Where d.entityspecid In (Select Id From xm_entityspec e Where e.code In ('ADSLPORT','ADSL2PORT','VDSLPORT','HDSLPORT'));

Update xm_entitymgttemplate d Set d.entityspecid = (Select Id From xm_entityspec Where code='XDSLPORT')
 Where d.entityspecid In (Select Id From xm_entityspec e Where e.code In ('ADSLPORT','ADSL2PORT','VDSLPORT','HDSLPORT'));

Delete From xm_entityspec Where Id In (Select Id From xm_entityspec e Where e.code In ('ADSLPORT','ADSL2PORT','VDSLPORT','HDSLPORT'));
Delete From x_versioninstance Where subcategory In ('ADSLPORT','ADSL2PORT','VDSLPORT','HDSLPORT');
commit;

--by chenlj 20090717
exec p_insertentitydescriptor('PRODINS','XB_PRODINS');
commit;

--by wxy 20090717

Update xm_entitydescriptor d Set d.dicattributename='CARD'
 Where D.DICCLASSNAME='PHSSIM'
   And D.entityspecid=(Select Id From XM_ENTITYSPEC Where CODE='PHSSIM');
COMMIT;

Alter Table xm_entitydescriptor Add
(
 Createdate Date,
 modifydate Date,
 maintainindicator Number(5) Default 1
);

Alter Table xm_componentdescriptor Add
(
 Createdate Date,
 modifydate Date
);

Update xm_entitydescriptor Set Createdate=to_date('20090601','yyyymmdd');
Update xm_componentdescriptor Set Createdate=to_date('20090601','yyyymmdd');


--用户名、操作时间、操作对象、操作sql（sql针对XM_表）
Create Table xm_operateLog
(
 Id Number(18) Primary Key,
 username Varchar2(255),
 operDate Date,
 operObject Varchar2(100),
 operSql Varchar2(1000)
);
Create Sequence s_xm_operatelog;

comment on column xm_operateLog.Id is 'ID';
comment on column xm_operateLog.username is '用户名';
comment on column xm_operateLog.operDate is '操作时间';
comment on column xm_operateLog.operObject is '操作对象';
comment on column xm_operateLog.operSql is '操作sql';
--by chenlj 20090720
exec p_Insertentityspec('拓扑图业务连接','MAPLINESERVICE','com.gxlu.ngrm.equipment.MapLineService','XB_MAPLINE','XS_MAPLINESERVICE');
EXEC P_INSERTENTITYDESCRIPTOR('MAPLINESERVICE','XS_MAPLINESERVICE');
update xm_entityspec set parentid=(select id from xm_entityspec where code='MAPLINE') WHERE CODE='MAPLINESERVICE';
COMMIT;
exec p_Insertentityspec('拓扑图业务连接','MAPLINEMASC','com.gxlu.ngrm.equipment.MapLineMasc','XB_MAPLINE','XS_MAPLINEMASC');
EXEC P_INSERTENTITYDESCRIPTOR('MAPLINEMASC','XS_MAPLINEMASC');
update xm_entityspec set parentid=(select id from xm_entityspec where code='MAPLINE') WHERE CODE='MAPLINEMASC';
COMMIT;
exec p_Insertentityspec('拓扑图连线类型关联','LINEREF','com.gxlu.ngrm.equipment.LineRef','XB_LINEREF','');
EXEC p_insertEntityDescriptor('LINEREF','XB_LINEREF');
COMMIT;

exec p_Insertentityspec('段','SECTION','com.gxlu.ngrm.equipment.Section','XB_LINK','XS_SECTION');
exec p_Insertentityspec('电路','CIRCUIT','com.gxlu.ngrm.equipment.Circuit','XB_LINK','XS_CIRCUIT');
exec p_Insertentityspec('光路','FIBERCIRCUIT','com.gxlu.ngrm.equipment.FiberCircuit','XB_LINK','XS_FIBERCIRCUIT');
exec p_Insertentityspec('ATM电路','ATMCIRCUIT','com.gxlu.ngrm.equipment.ATMCircuit','XB_LINK','XS_ATMCIRCUIT');
exec p_Insertentityspec('DDN电路','DDNCIRCUIT','com.gxlu.ngrm.equipment.DDNCircuit','XB_LINK','XS_DDNCIRCUIT');
exec p_Insertentityspec('FR电路','FRCIRCUIT','com.gxlu.ngrm.equipment.FRCircuit','XB_LINK','XS_FRCIRCUIT');
exec p_Insertentityspec('ATM端口电路','ATMOCIRCUIT','com.gxlu.ngrm.equipment.ATMOCircuit','XB_LINK','XS_ATMOCIRCUIT');
exec p_Insertentityspec('DDN端口电路','DDNOCIRCUIT','com.gxlu.ngrm.equipment.DDNOCircuit','XB_LINK','XS_DDNOCIRCUIT');
exec p_Insertentityspec('FR端口电路','FROCIRCUIT','com.gxlu.ngrm.equipment.FROCircuit','XB_LINK','XS_FROCIRCUIT');
exec p_Insertentityspec('DDN复用电路','DDNMUXCIRCUIT','com.gxlu.ngrm.equipment.DDNMuxCircuit','XB_LINK','XS_DDNMUXCIRCUIT');
exec p_Insertentityspec('用户电路','USERCIRCUIT','com.gxlu.ngrm.equipment.UserCircuit','XB_LINK','XS_USERCIRCUIT');
exec p_Insertentityspec('业务电路','BUSINESSCIRCUIT','com.gxlu.ngrm.equipment.BusinessCircuit','XB_LINK','XS_BUSINESSCIRCUIT');
exec p_Insertentityspec('中继电路','RELAYCIRCUIT','com.gxlu.ngrm.equipment.RelayCircuit','XB_LINK','XS_RELAYCIRCUIT');
exec p_Insertentityspec('MAN电路','MANCIRCUIT','com.gxlu.ngrm.equipment.MANCircuit','XB_LINK','XS_MANCIRCUIT');
exec p_Insertentityspec('LAN电路','LANCIRCUIT','com.gxlu.ngrm.equipment.LANCircuit','XB_LINK','XS_LANCIRCUIT');
exec p_Insertentityspec('DSL接入','DSLCIRCUIT','com.gxlu.ngrm.equipment.DSLCircuit','XB_LINK','XS_DSLCIRCUIT');
exec p_Insertentityspec('VPN','VPNCIRCUIT','com.gxlu.ngrm.equipment.VPNCircuit','XB_LINK','XS_VPNCIRCUIT');
exec p_Insertentityspec('传输电路','TRANSCIRCUIT','com.gxlu.ngrm.equipment.TransCircuit','XB_LINK','XS_TRANSCIRCUIT');
exec p_Insertentityspec('电缆连接','COPPERSECTION','com.gxlu.ngrm.equipment.CopperSection','XB_LINK','XS_COPPERSECTION');
exec p_Insertentityspec('光缆连接','FIBERSECTION','com.gxlu.ngrm.equipment.FiberSection','XB_LINK','XS_FIBERSECTION');
exec p_Insertentityspec('传输连接','TRANSSECTION','com.gxlu.ngrm.equipment.TransSection','XB_LINK','XS_TRANSSECTION');
exec p_Insertentityspec('无线连接','RADIOSECTION','com.gxlu.ngrm.equipment.RadioSection','XB_LINK','XS_RADIOSECTION');
COMMIT;

EXEC P_INSERTENTITYDESCRIPTOR('SECTION','XS_SECTION'); 
EXEC P_INSERTENTITYDESCRIPTOR('CIRCUIT','XS_CIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('FIBERCIRCUIT','XS_FIBERCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('ATMCIRCUIT','XS_ATMCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('DDNCIRCUIT','XS_DDNCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('FRCIRCUIT','XS_FRCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('ATMOCIRCUIT','XS_ATMOCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('DDNOCIRCUIT','XS_DDNOCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('FROCIRCUIT','XS_FROCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('DDNMUXCIRCUIT','XS_DDNMUXCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('USERCIRCUIT','XS_USERCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('BUSINESSCIRCUIT','XS_BUSINESSCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('RELAYCIRCUIT','XS_RELAYCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('MANCIRCUIT','XS_MANCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('LANCIRCUIT','XS_LANCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('DSLCIRCUIT','XS_DSLCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('VPNCIRCUIT','XS_VPNCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('TRANSCIRCUIT','XS_TRANSCIRCUIT'); 
EXEC P_INSERTENTITYDESCRIPTOR('COPPERSECTION','XS_COPPERSECTION'); 
EXEC P_INSERTENTITYDESCRIPTOR('FIBERSECTION','XS_FIBERSECTION'); 
EXEC P_INSERTENTITYDESCRIPTOR('TRANSSECTION','XS_TRANSSECTION'); 
EXEC P_INSERTENTITYDESCRIPTOR('RADIOSECTION','XS_RADIOSECTION'); 
COMMIT;

UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='LINK') WHERE CODE IN ('SECTION','CIRCUIT');
UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='SECTION') WHERE CODE IN ('COPPERSECTION','FIBERSECTION','TRANSSECTION','RADIOSECTION');
UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='CIRCUIT') WHERE CODE IN 
('FIBERCIRCUIT','ATMCIRCUIT','DDNCIRCUIT','FRCIRCUIT','ATMOCIRCUIT','DDNOCIRCUIT','FROCIRCUIT',
'DDNMUXCIRCUIT','USERCIRCUIT','BUSINESSCIRCUIT','RELAYCIRCUIT','MANCIRCUIT','LANCIRCUIT',
'DSLCIRCUIT','VPNCIRCUIT','TRANSCIRCUIT');

EXEC p_insertM2OneRelation('拓扑图连线类型关联对应拓扑图','LINEREF2MAP','LINEREF','MAP','MAPID','ID','B');
commit;
--by chenlj 20090721
UPDATE XM_ENTITYDESCRIPTOR SET COLUMNTYPE=4,DATATYPE=4,DATALENGTH=255  
WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='CUSTOMER') AND ATTRNAME='ADDRESSID';
COMMIT;

DELETE FROM XM_ENTITYDESCRIPTOR 
WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MAPLINE') AND ATTRNAME='ENTITYID';
COMMIT;
--by chenlj 20090722
UPDATE XM_ENTITYSPEC SET CTSEQUENCE='S_XB_MAPLINE' WHERE CODE='MAPNODE';
commit;

DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='LINK') AND COLUMNNAME='CCTYPEID';
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR SET DATATYPE=2,COLUMNTYPE=2,DATALENGTH=18,ISFK=1 WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MAPLINE' ) AND COLUMNNAME='TYPE';
COMMIT;

EXEC p_insertM2OneRelation('拓扑图连线连接对应连接','MAPLINELINK2LINK','MAPLINELINK','LINK','LINKID','ENTITYID','S');
commit;

--added by liurr at 2009.7.22
EXEC P_INSERTENTITYDESCRIPTOR('LINK','XB_LINK'); 
COMMIT;

exec p_insertM2OneRelation('连接对应的父连接','LINK2LINK','LINK','LINK','PARENTID','ID','B');
exec p_insertM2OneRelation('连接对应的A位置','LINK2ALOCATION','LINK','LOCATION','ALOCATIONID','ID','B');
exec p_insertM2OneRelation('连接对应的Z位置','LINK2ZLOCATION','LINK','LOCATION','ZLOCATIONID','ID','B');
commit;

EXEC P_INSERTENTITYDESCRIPTOR('LINK','XB_LINK'); 
COMMIT;

exec p_insertM2OneRelation('连接对应的保护实体','LINK2PROTECTINGOBJ','LINK','LINK','PROTECTINGOBJID','ID','B');
commit;

--added by liurr at 2009.7.23 for 主产品实例（ProdIns）增加属性
exec p_Insertentitydescriptor('PRODINS','XB_PRODINS');
commit;

--配设备号（PSTNConfig）去掉属性
delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='PSTNCONFIG')  AND COLUMNNAME IN ('PRIACCNBR','GROUPTYPE','GROUPNO','INNERGROUPNO');
commit;

--配ADSL端口（ADSLConfig）去掉属性
delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='ADSLCONFIG') AND COLUMNNAME IN ('GROUPNO','FACILITYTYPE','FACILITYCODE','ASSOCCODE');
commit;

--配LAN端口（LANConfig）去掉属性
delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='LANCONFIG') AND COLUMNNAME IN ('FACILITYTYPE','FACILITYCODE');
commit;

--配ONU端口（ONUConfig）去掉属性
delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='ONUCONFIG') AND COLUMNNAME IN ('FACILITYTYPE','FACILITYCODE');
commit;

--by chenlj 20090723
DELETE FROM xm_entitydescriptor WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='CODE') AND COLUMNNAME='CODESPEC';
COMMIT;

EXEC p_insertEntityDescriptor('CROSSCONNECTION','XB_CROSSCONNECTION');
DELETE FROM xm_entitydescriptor WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='CROSSCONNECTION') AND COLUMNNAME='CCTYPEID';
COMMIT;

DELETE FROM xm_entitydescriptor WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='CROSSCONNECTIONTYPE') AND COLUMNNAME='CCTYPE';
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR SET dicclassname='LINK',dicattributename='DIRECTION' WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='LINK' ) AND COLUMNNAME='DIRECTION';
COMMIT;

--by liurr 20090723
exec p_Insertentitydescriptor('PRODINS','XB_PRODINS');
commit;
--by liurr 20090724
EXEC P_INSERTENTITYDESCRIPTOR('MAPLINE','XB_MAPLINE');
COMMIT;
EXEC p_insertM2OneRelation('拓扑图连线对应的连线类型关联','MAPLINE2LINEREF','MAPLINE','LINEREF','LINEREFID','ID','B');
commit;

UPDATE XM_ENTITYDESCRIPTOR SET ATTRDISPNAME='逻辑连线类型' WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MAPLINEMASC')
AND ATTRNAME='MASCTYPE';
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR SET ATTRDISPNAME='用户序列号' WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MAP')
AND ATTRNAME='USERID';
UPDATE XM_ENTITYDESCRIPTOR SET ATTRDISPNAME='底图文件名' WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MAP')
AND ATTRNAME='BACKGROUND';
UPDATE XM_ENTITYDESCRIPTOR SET ATTRDISPNAME='创建方式' WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MAP')
AND ATTRNAME='SOURCE';
COMMIT;

update xm_componentdescriptor set dicclassname='COMMON',DICATTRIBUTENAME='BOOLEAN'  where componentspecid=(select id from xm_componentspec where code='PSTNPORT')
AND COLUMNNAME IN ('FJORNOT','LDORNOT','XNORNOT');
COMMIT;
--by chenlj 20090727
exec p_Insertentitydescriptor('LOGICALAREAMAPPING','X_LOGICALAREAMAPPING');
exec p_Insertentitydescriptor('ADDRESS','X_ADDRESS');
exec p_Insertentitydescriptor('PARTYROLE','X_PARTYROLE');
exec p_Insertentitydescriptor('CODEPOOL','X_CODEPOOL');
exec p_Insertentitydescriptor('CODELEVEL','X_CODELEVEL');
exec p_Insertentitydescriptor('CODERULE','X_CODERULE');
exec p_Insertentitydescriptor('CODEPROTOCOLNO','X_CODEPROTOCOLNO');
exec p_Insertentitydescriptor('CODEMETARULE','X_CODEMETARULE');
exec p_Insertentitydescriptor('VENDOR','XB_VENDOR');
exec p_Insertentitydescriptor('PANELRELATION','XB_PANELRELATION');
exec p_Insertentitydescriptor('MEFACILITYRELATION','XB_MEFACILITYRELATION');
exec p_Insertentitydescriptor('RESCOVER','XB_RESCOVER');
exec p_Insertentitydescriptor('CRMORDER','XB_CRMORDER');
exec p_Insertentitydescriptor('ONUMODELINI','XB_ONUMODELINI');
exec p_Insertentitydescriptor('PRODSERVSPECSHARE','XB_PRODSERVSPECSHARE');
exec p_Insertentitydescriptor('PRODUCT','X_PRODUCT');
exec p_Insertentitydescriptor('PRODUCTINSRELATION','XB_PRODUCTINSRELATION');
exec p_Insertentitydescriptor('PROJECTINSTANCE','X_PROJECTINSTANCE');
exec p_Insertentitydescriptor('SERVPRODINSHIS','XB_SERVPRODINSHIS');
exec p_Insertentitydescriptor('MODIFYDETAIL','XB_MODIFYDETAIL');
exec p_Insertentitydescriptor('MIGRATIONIMPORT','XB_MIGRATIONIMPORT');
exec p_Insertentitydescriptor('MIGRATIONACTION','XB_MIGRATIONACTION');
exec p_Insertentitydescriptor('MIGRATIONACTIONDETAIL','XB_MIGRATIONACTIONDETAIL');
exec p_Insertentitydescriptor('MIGRATIONRANGE','XB_MIGRATIONRANGE');
exec p_Insertentitydescriptor('MIGRATIONRANGEDETAIL','XB_MIGRATIONRANGEDETAIL');
exec p_Insertentitydescriptor('MIGRATIONPROJECT','XB_MIGRATIONPROJECT');
exec p_Insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
exec p_Insertentitydescriptor('MIGRATIONBATCH','XB_MIGRATIONBATCH');
exec p_Insertentitydescriptor('MIGRATIONLOG','XB_MIGRATIONLOG');
exec p_Insertentitydescriptor('MIGRATIONPRODINS','XB_MIGRATIONPRODINS');
exec p_Insertentitydescriptor('CHANGEDETAIL','XB_CHANGEDETAIL');
exec p_Insertentitydescriptor('WORKORDER','X_WORKORDER');
exec p_Insertentitydescriptor('TICKETRESSERVICEASSOC','XB_TICKETRESSERVICEASSOC');
exec p_Insertentitydescriptor('TICKETRESSERVICEASSOCHIS','XB_TICKETRESSERVICEASSOCHIS');
exec p_Insertentitydescriptor('TASKRESINSASSOC','XB_TASKRESINSASSOC');
exec p_Insertentitydescriptor('SERVICEORDER','X_SERVICEORDER');
exec p_Insertentitydescriptor('SERSPECRESSPECASSIGN','XB_SERSPECRESSPECASSIGN');
exec p_Insertentitydescriptor('RESSERVICESPEC','X_RESSERVICESPEC');
exec p_Insertentitydescriptor('RESSERVSPECPRODUCTASSIGN','X_RESSERVSPECPRODUCTASSIGN');
exec p_Insertentitydescriptor('RESSERVINSRESINSASSOC','XB_RESSERVINSRESINSASSOC');
exec p_Insertentitydescriptor('RESSERVINSRESINSASSOCHIS','XB_RESSERVINSRESINSASSOCHIS');
exec p_Insertentitydescriptor('CHANGEDETAIL','XB_CHANGEDETAIL');
exec p_Insertentitydescriptor('CHANGEDETAIL','XB_CHANGEDETAIL');
COMMIT;

--by chenlj 20090728
EXEC P_INSERTENTITYDESCRIPTOR('MAPLINE','XB_MAPLINE');
COMMIT;
EXEC P_INSERTENTITYDESCRIPTOR('MAPLINETYPE','XB_MAPLINETYPE');
COMMIT;

delete from xm_ENTITYDESCRIPTOR WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MAPLINETYPE')
AND COLUMNNAME IN ('RED','GREEN','BLUE','ARROW','THICKNESS');

UPDATE xm_ENTITYDESCRIPTOR SET DICCLASSNAME='LINEREF',DICATTRIBUTENAME='LINKMODE' WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MAPLINETYPE')
AND COLUMNNAME='LINKMODE';

UPDATE xm_ENTITYDESCRIPTOR SET COLUMNTYPE=4,DATATYPE=4,DATALENGTH=255 WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MAPLINETYPE')
AND COLUMNNAME='DASH';
COMMIT;
exec p_Insertentityspec('拓扑图过滤器','MAPFILTER','com.gxlu.ngrm.equipment.MapFilter','XB_MAPFILTER','');
EXEC p_insertEntityDescriptor('MAPFILTER','XB_MAPFILTER');
COMMIT;

exec p_Insertentityspec('拓扑图与拓扑图过滤器关联表','MAPFILTERREF','com.gxlu.ngrm.equipment.MapFilterRef','XB_MAPFILTERREF','');
EXEC p_insertEntityDescriptor('MAPFILTERREF','XB_MAPFILTERREF');
COMMIT;

exec p_insertM2OneRelation('拓扑图与拓扑图过滤器关联对应的拓扑图','MAPFILTERREF2MAP','MAPFILTERREF','MAP','MAPID','ID','B');
exec p_insertM2OneRelation('拓扑图与拓扑图过滤器关联对应的过滤器','MAPFILTERREF2MAPFILTER','MAPFILTERREF','MAPFILTER','MAPFILTERID','ID','B');
commit;

--added by liurr at 20090729 for  FTTH配置（FTTHConfig）中去掉去掉原来的属性“上联的pon口”，增加“主光路编码”
delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='FTTHCONFIG') AND COLUMNNAME = 'PONPORT';
commit;

exec p_Insertentitydescriptor('FTTHCONFIG','XS_FTTHCONFIG');
commit;

--added by liurr at 20090729 for X_IVPNNO表的模型上关联的还是SNNEID，应该要调整为Telant上的MEID       
delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='IVPNNO') AND COLUMNNAME = 'SNNEID';
commit;
EXEC P_INSERTENTITYDESCRIPTOR('IVPNNO','X_IVPNNO'); 
COMMIT;
exec p_insertM2OneRelation('虚拟网所属交换机','IVPNNO2ME','IVPNNO','MANAGEDELEMENT','MEID','ID','');
COMMIT;

--by chenlj 20090730
UPDATE XM_ENTITYDESCRIPTOR SET COLUMNTYPE=4,DATATYPE=4,DATALENGTH=255 WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='CUSTOMER') 
AND ATTRNAME IN('CUSTOMERBRAND','BIZNAVIGATORCATEGORY','EFAMILYCATEGORY');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR SET DICCLASSNAME='PHYSICALRESOURCE' WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MANAGEDELEMENT')
AND COLUMNNAME='NETWORKLAYER';
COMMIT;

delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='SERSPECRESSPECASSIGN') 
AND COLUMNNAME IN('BASEENTITYSPEC','ENTITYSPEC');
commit;

--by chenlj 20090731
exec p_Insertentityspec('VLAN','VLAN','com.gxlu.ngrm.code.VLAN','XB_CODE','XS_VLAN');
EXEC P_INSERTENTITYDESCRIPTOR('VLAN','XS_VLAN');
update xm_entityspec set parentid=(select id from xm_entityspec where code='CODE') WHERE CODE='VLAN';
COMMIT;

exec p_insertM2NRelation('VLAN和端口的关联','VLANPORTRELATION','XR_VLANPORTRELATION','VLAN','PORT','VLANID','PORTID');
commit;

exec p_Insertentityspec('PON口','PONPORT','com.gxlu.ngrm.equipment.PONPort','XB_PORT','XS_PONPORT');
EXEC p_insertComponetSpec('PON口组件','PONPORT');
EXEC p_insertESpec2CSpec('PONPORT','PONPORT');
update xm_entityspec set parentid=(select id from xm_entityspec where code='PORT') WHERE CODE='PONPORT';
COMMIT;

exec p_Insertentityspec('PON设备','PON','com.gxlu.ngrm.equipment.PON','XB_MANAGEDELEMENT','XS_PON');
exec p_Insertentitydescriptor('PON','XS_PON');
commit;
update xm_entityspec set parentid=(select id from xm_entityspec where code='MANAGEDELEMENT') WHERE CODE='PON';
COMMIT;

--BY WXY 20090731
exec p_insertM2NRelation('机房对应的房间','ROOM2SUITE','XR_ROOM2SUITERELATION','ROOM','SUITE','ROOMID','SUITEID');
COMMIT;

---by liurr at 200980731
EXEC P_INSERTENTITYDESCRIPTOR('PSTNPHYSICALCODESEGMENT','XS_PSTNPHYSICALCODESEGMENT'); 
EXEC P_INSERTENTITYDESCRIPTOR('PHSCODESEGMENT','XS_PHSCODESEGMENT'); 
COMMIT;

--by liurr at 20080731 for 联系人姓、联系人名：只要联系人姓名,父客户id：关联关系未建,customer.locationId：关系没有建立
delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='CUSTOMER') AND COLUMNNAME = 'LINKSURNAME';
update xm_entitydescriptor set attrdispname = '联系人姓名' where entityspecid=(select id from xm_entityspec where code='CUSTOMER') AND COLUMNNAME = 'LINKNAME';
COMMIT;
              
exec p_insertM2OneRelation('客户所属区域','CUSTOMER2LOCATION','CUSTOMER','LOCATION','LOCATIONID','ID','');
exec p_insertM2OneRelation('客户所属父客户','CUSTOMER2CUSTOMER','CUSTOMER','CUSTOMER','PARENTID','ID','');
COMMIT;

--把facility表logicalNo的描述改成：端子序
update xm_entitydescriptor set attrdispname = '端子序' where entityspecid=(select id from xm_entityspec where code='FACILITY') AND COLUMNNAME = 'LOGICALNO';
COMMIT;

--by liurr at 20090801 for ADSLConfig删除两属性，添加IsHighspeed属性，  FTTHPortConfig添加属性IsHighSpeed
delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='ADSLCONFIG') AND COLUMNNAME IN ('USPEED','DSPEED');
COMMIT;
EXEC P_INSERTENTITYDESCRIPTOR('ADSLCONFIG','XS_ADSLCONFIG'); 
COMMIT;

EXEC P_INSERTENTITYDESCRIPTOR('FTTHPORTCONFIG','XS_FTTHPORTCONFIG'); 
COMMIT;

--by liurr at 20090803
--port元表：有usefor属性，T3上没有这个属性，去掉
delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='PORT') 
       AND COLUMNNAME = 'USEFOR';
COMMIT;

--port元表：layerRate（数据库里少了一个r，是layeRate）
UPDATE xm_entitydescriptor SET COLUMNNAME = 'LAYERRATE',ATTRNAME = 'LAYERRATE' WHERE entityspecid=(select id from xm_entityspec where code='PORT') AND COLUMNNAME = 'LAYERATE';
COMMIT;

--xm_componentSpec表中多了组件（会造成误解），去掉记录并删除组件描述以及对该组件的引用（如果有）
---select * from xm_componentSpec WHERE NAME = '设备号' AND CODE = 'PORTEQUIPNO'
DELETE FROM XM_EntitySpec2ComponentSpec WHERE COMPONENTSPECID = (SELECT ID FROM xm_componentSpec WHERE NAME = '设备号' AND CODE = 'PORTEQUIPNO' );
DELETE FROM XM_COMPONENTDESCRIPTOR WHERE COMPONENTSPECID = (SELECT ID FROM xm_componentSpec WHERE NAME = '设备号' AND CODE = 'PORTEQUIPNO');
DELETE FROM XM_COMPONENTSPEC WHERE NAME = '设备号' AND CODE = 'PORTEQUIPNO';
COMMIT;

--XM_entitySpec中多了spec（会造成误解），去掉记录并删除所有描述以及改规格对component的引用（如果有）
---select * from Xm_Entityspec where name = '交换端口' and code = 'SNPORT'
DELETE from XM_EntitySpec2ComponentSpec WHERE ENTITYSPECID = (SELECT ID FROM Xm_Entityspec where name = '交换端口' and code = 'SNPORT');
DELETE from XM_ENTITYSPEC2RELATIONSPEC WHERE ENTITYSPECID = (SELECT ID FROM Xm_Entityspec where name = '交换端口' and code = 'SNPORT');
DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (SELECT ID FROM Xm_Entityspec where name = '交换端口' and code = 'SNPORT');
DELETE FROM Xm_Entityspec where name = '交换端口' and code = 'SNPORT';
COMMIT;

--by wxy 20090803

--by wxy 20090803
declare
v_count number(8);
begin
	select count(*) into v_count from Xm_Entityspec2relationspec 
		Where Relationspecid in(Select Id From XM_RELATIONSPEC Where Code IN('CC2APORT'));
	
	if v_count = 1 then	
		Delete
		     From Xm_Entityspec2relationspec
		     Where Entityspecid = (Select Id From Xm_Entityspec Where Code = 'CROSSCONNECTION');
		 
		Delete
		     From Xm_Entityspec2relationspec
		     Where Relationspecid in(Select Id From XM_RELATIONSPEC Where Code IN('CC2AME','CC2APORT','CC2ZPORT','CC2ZME','CROSSCONNECTION2LOCATION'));
		commit;
		 
		DELETE FROM XM_RELATIONSPEC WHERE CODE IN('CC2AME','CC2APORT','CC2ZPORT','CC2ZME','CROSSCONNECTION2LOCATION');
	end if;
end;
/
 
exec p_insertM2OneRelation('跳接对应的A网元','CC2AME','CROSSCONNECTION','MANAGEDELEMENT','AMANAGEDELEMENTID','ID','B');
exec p_insertM2OneRelation('跳接对应的A端口','CC2APORT','CROSSCONNECTION','PORT','APORTID','ID','B');
exec p_insertM2OneRelation('跳接对应的Z端口','CC2ZPORT','CROSSCONNECTION','PORT','ZPORTID','ID','B');
exec p_insertM2OneRelation('跳接对应的Z网元','CC2ZME','CROSSCONNECTION','MANAGEDELEMENT','ZMANAGEDELEMENTID','ID','B');
exec p_insertM2OneRelation('跳接对应的管理维护区','CROSSCONNECTION2LOCATION','CROSSCONNECTION','LOCATION','LOCATIONID','ID','B');
COMMIT;


--by wxy 20090804
exec p_Insertentityspec('审计对象配置','AUDITOBJ','com.gxlu.ngrm.metadata.AuditObj','X_AUDITOBJ','');
exec p_insertEntityDescriptor('AUDITOBJ','X_AUDITOBJ');

exec p_Insertentityspec('审计触发参数配置','AUDITTRIGGERPARAM','com.gxlu.ngrm.metadata.AuditTriggerParam','X_AUDITTRIGGERPARAM','');
exec p_insertEntityDescriptor('AUDITTRIGGERPARAM','X_AUDITTRIGGERPARAM');

exec p_Insertentityspec('审计结果参数配置','AUDITRESULTPARAM','com.gxlu.ngrm.metadata.AuditResultParam','X_AUDITRESULTPARAM','');
exec p_insertEntityDescriptor('AUDITRESULTPARAM','X_AUDITRESULTPARAM');

exec p_Insertentityspec('审计记录','AUDITRECORD','com.gxlu.ngrm.metadata.AuditRecord','X_AUDITRECORD','');
exec p_insertEntityDescriptor('AUDITRECORD','X_AUDITRECORD');
commit;

exec p_insertM2OneRelation('审计触发参数配置对应的审计对象配置','AUDITTRIGGERPARAM2AUDITOBJ','AUDITTRIGGERPARAM','AUDITOBJ','AUDITOBJID','ID','');
exec p_insertM2OneRelation('审计结果参数配置对应的审计对象配置','AUDITRESULTPARAM2AUDITOBJ','AUDITRESULTPARAM','AUDITOBJ,'AUDITOBJID'','ID','');
exec p_insertM2OneRelation('审计记录对应的审计对象配置','AUDITRECORD2AUDITOBJ','AUDITRECORD','AUDITOBJ','AUDITOBJID','ID','');
commit;

UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='CARD')
WHERE CORETABLENAME ='XB_CARD' and MAINTAININDICATOR != 1 AND PARENTID IS NULL;
COMMIT;


--新增加的类增缴到元数据的大类的字典上
--有了元数据才可以产生字典
declare
i number(5);
begin
	select max(seqno) into i from X_DICTIONARY where classid = 'METADATA' and attributeid = 'OTHERGROUPTYPE';
	for R in(Select NAME,CODE,CLASSNAME 
	           From XM_ENTITYSPEC  
					  Where PARENTID Is Null 
					    And MAINTAININDICATOR = 3 
					    And CODE Not Like '%TYPE'
						Order By ID) loop
	begin
		
		i := i + 1;
		
		INSERT INTO X_DICTIONARY(ID,CLASSID,ATTRIBUTEID,VALUE,DESCRIPTION,SEQNO,STATE,VALUEEN) 			  
		 VALUES(S_X_DICTIONARY.NEXTVAL,'METADATA','OTHERGROUPTYPE',i,R.NAME,I,1,R.CLASSNAME);
		
	exception
		when others then
			null;
	end;
	
	end loop;

end;
/

--by liurr at 20090804 for 主产品实例（xb_prodins）增加属性
EXEC P_INSERTENTITYDESCRIPTOR('PRODINS','XB_PRODINS'); 
COMMIT;

--by liurr at 20090804 for task元表上增加“执行时间”
EXEC p_insertentitydescriptor('TASK','XB_TASK');
COMMIT;

--by liurr at 20090805 for 现场库中存在脏数据，core库中也有
delete from xm_entitydescriptor where columnname='ID' and entityspecid in (select ID from xm_entityspec where code  = 'PSTNLOGICALCODE');
update xm_relationspec set ownerattr = 'ENTITYID' where code in ('PHSLOGICALCODE2CODEPROTOCOLNO','PHSLOGICALCODE2CODELEVEL','PSTNLOGICALCODE2CODEPROTOCOLNO','PSTNLOGICALCODE2CODELEVEL');
commit;

--by wxy 20090807
delete from xm_entitydescriptor d where d.entityspecid=(select id from xm_entityspec where code='MAPNODE') and d.columnname='MAPNODETYPE';
delete from xm_entitydescriptor d where d.entityspecid=(select id from xm_entityspec where code='MAPNODE') and d.columnname='REGIONID';

Update xm_entityspec d Set d.ctsequence='S_XB_MAPLINE' Where d.ctsequence='S_XB_MAPNODE';
commit;


--BY WXY 20090811
--metaData
delete from xm_entityspec2relationspec d where d.relationspecid in (select r.id from xm_relationspec r,xm_entityspec e where r.ownerentityspecid= e.id and e.code='LINK');
delete from xm_entityspec2relationspec d where d.relationspecid in (select r.id from xm_relationspec r,xm_entityspec e where r.supplierentityspecid= e.id and e.code='LINK');
delete from xm_relationspec r where r.ownerentityspecid = (select id from xm_entityspec e where e.code='LINK');
delete from xm_relationspec r where r.supplierentityspecid = (select id from xm_entityspec e where e.code='LINK');

delete from x_versionInstance where subcategory in ('LINK2APORT','LINK2ZPORT','LINK2AME','LINK2ZME','LINK2LOCATION');

delete from xm_entityDescriptor ed where ed.entitySpecId = (select id from xm_entityspec e where e.code='LINK');  
--delete from xm_entityspec e where e.code='LINK';
commit;

--LinkType
exec p_Insertentityspec('连接类型','LINKTYPE','com.gxlu.ngrm.network.LinkType','XB_LINKTYPE','');
exec p_Insertentitydescriptor('LINKTYPE','XB_LINKTYPE');

Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'LINK'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'LINKTYPE')
	 And d.Columnname = 'LINKSPEC';
commit;

--Link  
--exec p_Insertentityspec('连接','LINK','com.gxlu.ngrm.network.Link','XB_LINK','');

update xm_entityspec d set d.classname=REPLACE(d.classname,'equipment','network') where d.coretablename='XB_LINK';
commit;

exec p_Insertentitydescriptor('LINK','XB_LINK');

Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'PHYSICALRESOURCE'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'LINK')
	 And d.Columnname In ('DIRECTION', 'PHYSICALSTATUS', 'PHYSICALSTATUSDETAIL', 'SERVICESTATUS', 'SERVICESTATUSDETAIL',
				'MIGRATIONSTATUS', 'MIGRATIONSTATUSDETAIL', 'PROTECTIONSTATUS');
commit;

exec p_insertM2OneRelation('连接对应的父连接','LINK2PARENT','LINK','LINK','PARENTID','ID','B');
exec p_insertOne2MRelation('连接对应的子连接','LINK2CHILD','LINK','LINK','PARENTID','B');

exec p_insertM2OneRelation('连接对应的连接类型','LINK2LINKTYPE','LINK','LINKTYPE','LINKTYPEID','ID','B');
exec p_insertM2OneRelation('连接对应的保护连接','LINK2PROTECTOBJ','LINK','LINK','PROTECTINGOBJID','ID','B');

exec p_insertM2OneRelation('连接对应的ACTP','LINK2ACTP','LINK','PORT','ACTPID','ID','B');
exec p_insertM2OneRelation('连接对应的ZCTP','LINK2ZCTP','LINK','PORT','ZCTPID','ID','B');

exec p_insertM2OneRelation('连接对应的A端口','LINK2APORT','LINK','PORT','APORTID','ID','B');
exec p_insertM2OneRelation('连接对应的Z端口','LINK2ZPORT','LINK','PORT','ZPORTID','ID','B');

exec p_insertM2OneRelation('连接对应的A网元','LINK2AME','LINK','MANAGEDELEMENT','AMEID','ID','B');
exec p_insertM2OneRelation('连接对应的Z网元','LINK2ZME','LINK','MANAGEDELEMENT','ZMEID','ID','B');

exec p_insertM2OneRelation('连接对应的A位置','LINK2ALOC','LINK','LOCATION','ALOCATIONID','ID','B');  
exec p_insertM2OneRelation('连接对应的Z位置','LINK2ZLOC','LINK','LOCATION','ZLOCATIONID','ID','B');  

--LinkRoute
exec p_Insertentityspec('连接路由','LINKROUTE','com.gxlu.ngrm.network.LinkRoute','XB_LINKROUTE','');
exec p_Insertentitydescriptor('LINKROUTE','XB_LINKROUTE');

Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'PHYSICALRESOURCE'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'LINKROUTE')
	 And d.Columnname = 'PROTECTIONSTATUS';
commit;

exec p_insertM2OneRelation('连接路由对应的父连接','LINKROUTE2PLINK','LINKROUTE','LINK','PARENTLINKID','ID','B');
exec p_insertM2OneRelation('连接路由对应的子连接','LINKROUTE2CLINK','LINKROUTE','LINK','CHILDLINKID','ID','B');

--LinkEndConstraint
exec p_Insertentityspec('连接终端约束','LINKENDCONSTRAINT','com.gxlu.ngrm.network.LinkEndConstraint','XB_LINKENDCONSTRAINT','');
exec p_Insertentitydescriptor('LINKENDCONSTRAINT','XB_LINKENDCONSTRAINT');

exec p_insertM2OneRelation('连接终端约束对应的连接类型','LINKENDC2LINKTYPE','LINKENDCONSTRAINT','LINKTYPE','LINKTYPEID','ID','B');

--LinkRouteConstraint
exec p_Insertentityspec('路由约束','LINKROUTECONSTRAINT','com.gxlu.ngrm.network.LinkRouteConstraint','XB_LINKROUTECONSTRAINT','');
exec p_Insertentitydescriptor('LINKROUTECONSTRAINT','XB_LINKROUTECONSTRAINT');

Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'LINKROUTE'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'LINKROUTECONSTRAINT')
	 And d.Columnname = 'ROUTEMODE';
commit;

exec p_insertM2OneRelation('路由约束对应的父连接类型','LINKROUTEC2PLINKTYPE','LINKROUTECONSTRAINT','LINKTYPE','PARENTLINKTYPEID','ID','B');
exec p_insertM2OneRelation('路由约束对应的子连接类型','LINKROUTEC2CLINKTYPE','LINKROUTECONSTRAINT','LINKTYPE','CHILDLINKTYPEID','ID','B');

--TPAggregation
exec p_insertM2NRelation('端口聚合','TPAGGREGATION','XR_TPAGGREGATION','PORT','PORT','AGGREGATETPID','GRANULETPID');
commit;

exec p_insertM2NRelation('端口聚合子对父','TPAGGREGATIONC2P','XR_TPAGGREGATION','PORT','PORT','GRANULETPID','AGGREGATETPID');
commit;

exec p_Insertentityspec('时隙','TIMESLOT','com.gxlu.ngrm.network.TimeSlot','XB_LINK','XS_TIMESLOT');
exec p_Insertentitydescriptor('TIMESLOT','XS_TIMESLOT');

exec p_Insertentityspec('通路','TRAIL','com.gxlu.ngrm.network.Trail','XB_LINK','XS_TRAIL');
exec p_Insertentitydescriptor('TRAIL','XS_TRAIL');

exec p_Insertentityspec('反向复用产生的link','IMA','com.gxlu.ngrm.network.IMA','XB_LINK','XS_IMA');
exec p_Insertentitydescriptor('IMA','XS_IMA');

exec p_Insertentityspec('CTP','CTP','com.gxlu.ngrm.equipment.CTP','XB_PORT','XS_CTP');
exec p_Insertentitydescriptor('CTP','XS_CTP');

exec p_Insertentityspec('FTP','FTP','com.gxlu.ngrm.equipment.FTP','XB_PORT','XS_FTP');
exec p_Insertentitydescriptor('FTP','XS_FTP');
COMMIT;

update xm_entityspec set parentid=(select id from xm_entityspec where code='LINK') where code in('IMA','TIMESLOT','TRAIL');
COMMIT;

update xm_entityspec set parentid=(select id from xm_entityspec where code='PORT') where code in('CTP','FTP');
COMMIT;



--by wxy 20090812
exec p_Insertentityspec('层速率映射规则','LAYERRATEMAPRULE','com.gxlu.ngrm.equipment.LayerRateMapRule','XB_LAYERRATEMAPRULE','');
exec p_Insertentitydescriptor('LAYERRATEMAPRULE','XB_LAYERRATEMAPRULE');

exec p_Insertentityspec('层速率命名规则','LAYERRATENAMINGRULE','com.gxlu.ngrm.equipment.LayerRateNamingRule','XB_LAYERRATENAMINGRULE','');
exec p_Insertentitydescriptor('LAYERRATENAMINGRULE','XB_LAYERRATENAMINGRULE');

exec p_insertM2OneRelation('层速率命名规则对应的映射规则','LAYERRATENAMINGRULE2MAPRULE','LAYERRATENAMINGRULE','LAYERRATEMAPRULE','MAPRULEID','ID','B');
Commit;

 
Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'PHYSICALRESOURCE'
 Where d.Dicclassname = 'MANAGEDELEMENT'
	 And d.Attrname = 'NETWORKLAYER';
Commit;

Update Xm_Entityspec d Set d.Coretablename = 'X_LOGICALAREAMAPPING' Where d.Coretablename = 'XB_LOGICALAREAMAPPING';
Commit;

Update xm_entitydescriptor d Set d.dicattributename='CARD'
 Where D.DICCLASSNAME='PHSSIM'
   And D.entityspecid=(Select Id From XM_ENTITYSPEC Where CODE='PHSSIM');
commit;


--BY WXY 20090813

Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'PHYSICALRESOURCE'
 Where d.Dicclassname = 'PORT'
	 And d.Attrname = 'DIRECTION';
Commit;

Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'LINK'
 Where d.Dicclassname = 'PHYSICALRESOURCE'
	 And d.Attrname = 'DIRECTION'
	 And d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'LINK');
Commit;


----
exec p_insertEntitySpec('方案设计模版','PROJECTDESIGNTEMPLATE','com.gxlu.ngrm.resService.ProjectDesignTemplate','X_PROJECTDESIGNTEMPLATE','');
exec p_insertentitydescriptor('PROJECTDESIGNTEMPLATE','X_PROJECTDESIGNTEMPLATE');

exec p_insertM2OneRelation('方案设计模版对应的资源服务规格','PROJECTDESIGNTEMP2RESSERVSPEC','PROJECTDESIGNTEMPLATE','RESSERVICESPEC','RESSERVICESPECID','ID','');


----
exec p_insertEntitySpec('方案类型','PROJECTTYPE','com.gxlu.ngrm.resService.ProjectType','X_PROJECTTYPE','');
exec p_insertentitydescriptor('PROJECTTYPE','X_PROJECTTYPE');

exec p_insertM2OneRelation('方案实例对应的方案类型','PROJECTINSTANCE2PROJECTTYPE','PROJECTINSTANCE','PROJECTTYPE','PROJECTTYPEID','ID','');

----
exec p_insertEntitySpec('方案设计连线','PROJECTDESIGNLINK','com.gxlu.ngrm.resService.ProjectDesignLink','XB_MAPLINE','XS_PROJECTDESIGNLINK');
exec p_insertentitydescriptor('PROJECTDESIGNLINK','XS_PROJECTDESIGNLINK');

exec p_insertM2OneRelation('方案设计连线对应的方案设计模版','PROJECTDESIGNLINK2TEMPLATE','PROJECTDESIGNLINK','PROJECTDESIGNTEMPLATE','PROJECTDESIGNTEMPLATEID','ID','S');

--
exec p_insertEntitySpec('方案实例连线','PROJECTINSLINK','com.gxlu.ngrm.resService.ProjectInsLink','XB_MAPLINE','XS_PROJECTINSLINK');
exec p_insertentitydescriptor('PROJECTINSLINK','XS_PROJECTINSLINK');

exec p_insertM2OneRelation('方案实例连线对应的方案实例','PROJECTINSLINK2PROJECTINS','PROJECTINSLINK','PROJECTINSTANCE','PROJECTINSTANCEID','ID','S');

Update Xm_Entityspec
	 Set Parentid = (Select Id From Xm_Entityspec Where Code = 'MAPLINE')
 Where Code In ('PROJECTDESIGNLINK', 'PROJECTINSLINK');
Commit;


----
exec p_insertEntitySpec('方案设计节点','PROJECTDESIGNNODE','com.gxlu.ngrm.resService.ProjectDesignNode','XB_MAPNODE','XS_PROJECTDESIGNNODE');
exec p_insertentitydescriptor('PROJECTDESIGNNODE','XS_PROJECTDESIGNNODE');

exec p_insertM2OneRelation('方案设计节点对应的方案设计模版','PROJECTDESIGNNODE2TEMPLATE','PROJECTDESIGNNODE','PROJECTDESIGNTEMPLATE','PROJECTDESIGNTEMPLATEID','ID','S');
exec p_insertM2OneRelation('方案设计节点对应的资源服务规格','PROJECTDESIGNNODE2RESSERVSPEC','PROJECTDESIGNNODE','RESSERVICESPEC','RESSERVICESPECID','ID','S');
exec p_insertM2OneRelation('方案设计节点对应的子设计模版','PROJECTDESIGNNODE2CTEMPLATE','PROJECTDESIGNNODE','PROJECTDESIGNTEMPLATE','CHILDTEMPLATEID','ID','S');

--
exec p_insertEntitySpec('方案实例节点','PROJECTINSNODE','com.gxlu.ngrm.resService.ProjectInsNode','XB_MAPNODE','XS_PROJECTINSNODE');
exec p_insertentitydescriptor('PROJECTINSNODE','XS_PROJECTINSNODE');

exec p_insertM2OneRelation('方案实例节点对应的方案实例','PROJECTINSNODE2PROJECTINS','PROJECTINSNODE','PROJECTINSTANCE','PROJECTINSTANCEID','ID','S');
exec p_insertM2OneRelation('方案实例节点对应的资源服务实例','PROJECTINSNODE2RESSERVINS','PROJECTINSNODE','RESSERVINS','RESSERVINSID','ID','S');
exec p_insertM2OneRelation('方案实例节点对应的子实例','PROJECTINSNODE2CPROJECTINS','PROJECTINSNODE','PROJECTINSTANCE','CHILDPROJECTINSTANCEID','ID','S');


Update Xm_Entityspec
	 Set Parentid = (Select Id From Xm_Entityspec Where Code = 'MAPNODE')
 Where Code In ('PROJECTDESIGNNODE', 'PROJECTINSNODE');
COMMIT;


----
exec p_insertEntitySpec('特征值','CHARACTERVALUE','com.gxlu.ngrm.resService.CharacterValue','X_CHARACTERVALUE','');
exec p_insertentitydescriptor('CHARACTERVALUE','X_CHARACTERVALUE');


----
exec p_insertEntitySpec('策略','POLICY','com.gxlu.ngrm.resService.Policy','X_POLICY','');
exec p_insertentitydescriptor('POLICY','X_POLICY');

exec p_insertM2OneRelation('策略对应的维护管理区','POLICY2LOCATION','POLICY','LOCATION','LOCATIONID','ID','');


----
exec p_insertEntitySpec('策略规则','POLICYRULE','com.gxlu.ngrm.resService.PolicyRule','X_POLICYRULE','');
exec p_insertentitydescriptor('POLICYRULE','X_POLICYRULE');

exec p_insertM2OneRelation('策略规则对应的策略','POLICYRULE2POLICY','POLICYRULE','POLICY','POLICYID','ID','');
exec p_insertM2OneRelation('策略规则对应的父规则','POLICYRULE2PARENT','POLICYRULE','POLICYRULE','PARENTID','ID','');


----
exec p_insertEntitySpec('策略事件','POLICYEVENT','com.gxlu.ngrm.resService.PolicyEvent','X_POLICYEVENT','');
exec p_insertentitydescriptor('POLICYEVENT','X_POLICYEVENT');

exec p_insertM2OneRelation('策略事件对应的策略规则','POLICYEVENT2POLICYRULE','POLICYEVENT','POLICYRULE','POLICYRULEID','ID','');
exec p_insertM2OneRelation('策略事件对应的父事件','POLICYEVENT2PARENT','POLICYEVENT','POLICYEVENT','PARENTID','ID','');

--未完待续



--by liurr at 20090813
UPDATE XM_ENTITYDESCRIPTOR SET COLUMNTYPE=14,DATATYPE=14,DATALENGTH=15,DICCLASSNAME = NULL, DICATTRIBUTENAME = NULL  
WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='PANELRELATION') AND ATTRNAME='PRI';
COMMIT;

--by liurr at 20090813
EXEC p_insertEntitySpec('模拟中继板','SIMULATERELAYCARD','com.gxlu.ngrm.equipment.SimulateRelayCard','XB_CARD','XS_SIMULATERELAYCARD');
EXEC p_insertEntitySpec('接收器电路板','RECEIVERCIRCUITCARD','com.gxlu.ngrm.equipment.ReceiverCircuitCard','XB_CARD','XS_RECEIVERCIRCUITCARD');
EXEC p_insertEntitySpec('填充板','FILLCARD','com.gxlu.ngrm.equipment.FillCard','XB_CARD','XS_FILLCARD');
EXEC p_insertEntitySpec('告警板','ALARMCARD','com.gxlu.ngrm.equipment.AlarmCard','XB_CARD','XS_ALARMCARD');
EXEC p_insertEntitySpec('RPU板','RPUCARD','com.gxlu.ngrm.equipment.RPUCard','XB_CARD','XS_RPUCARD');
EXEC p_insertEntitySpec('磁带库','TAPEWAREHOUSE','com.gxlu.ngrm.equipment.TapeWarehouse','XB_CARD','XS_TAPEWAREHOUSE');
EXEC p_insertEntitySpec('数据库','DATABASE','com.gxlu.ngrm.equipment.Database','XB_CARD','XS_DATABASE');
EXEC p_insertEntitySpec('磁盘阵列','DISKARRAY','com.gxlu.ngrm.equipment.DiskArray','XB_CARD','XS_DISKARRAY');
EXEC p_insertEntitySpec('射频板','RADIOCARD','com.gxlu.ngrm.equipment.RadioCard','XB_CARD','XS_RADIOCARD');
EXEC p_insertEntitySpec('风扇板','FANCARD','com.gxlu.ngrm.equipment.FanCard','XB_CARD','XS_FANCARD');
EXEC p_insertEntitySpec('主控传输板','MASTERTRANSFERCARD','com.gxlu.ngrm.equipment.MasterTransferCard','XB_CARD','XS_MASTERTRANSFERCARD');
EXEC p_insertEntitySpec('信道处理板','CHANNELDEALCARD','com.gxlu.ngrm.equipment.ChannelDealCard','XB_CARD','XS_CHANNELDEALCARD');
EXEC p_insertEntitySpec('ATM板','ATMCARD','com.gxlu.ngrm.equipment.ATMCard','XB_CARD','XS_ATMCARD');
EXEC p_insertEntitySpec('FR板','FRCARD','com.gxlu.ngrm.equipment.FRCard','XB_CARD','XS_FRCARD');
EXEC p_insertEntitySpec('MODEM板','MODEMCARD','com.gxlu.ngrm.equipment.MODEMCard','XB_CARD','XS_MODEMCARD');
EXEC p_insertEntitySpec('BAS板','BASCARD','com.gxlu.ngrm.equipment.BASCard','XB_CARD','XS_BASCARD');
EXEC p_insertEntitySpec('IP板','IPCARD','com.gxlu.ngrm.equipment.IPCard','XB_CARD','XS_IPCARD');
EXEC p_insertEntitySpec('分组板','GROUPINGCARD','com.gxlu.ngrm.equipment.GroupingCard','XB_CARD','XS_GROUPINGCARD');
EXEC p_insertEntitySpec('EPON板','EPONCARD','com.gxlu.ngrm.equipment.EPONCard','XB_CARD','XS_EPONCARD');
EXEC p_insertEntitySpec('ATM/FR/DDN板','ATMFRDDNCARD','com.gxlu.ngrm.equipment.ATMFRDDNCard','XB_CARD','XS_ATMFRDDNCARD');

UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='CARD')
WHERE CORETABLENAME ='XB_CARD' and MAINTAININDICATOR != 1 AND PARENTID IS NULL;
commit;

--by liurr at 20090814
exec p_Insertentityspec('ATM端口','ATMPORT','com.gxlu.ngrm.equipment.ATMPort','XB_PORT','XS_ATMPORT');
exec p_Insertentityspec('SDH端口','SDHPORT','com.gxlu.ngrm.equipment.SDHPort','XB_PORT','XS_SDHPORT');
exec p_Insertentityspec('DDN/FR端口','DDNFRPORT','com.gxlu.ngrm.equipment.DDNFRPort','XB_PORT','XS_DDNFRPORT');
exec p_Insertentityspec('其它端口','OTHERPORT','com.gxlu.ngrm.equipment.OtherPort','XB_PORT','XS_OTHERPORT');

update xm_entityspec set parentid=(select id from xm_entityspec where code='PORT') WHERE CODE in ('ATMPORT','SDHPORT','DDNFRPORT','OTHERPORT');
COMMIT;

--by wxy 20090814
exec p_Insertentitydescriptor('PORT','XB_PORT');
commit;

Update Xm_Relationspec d
	 Set d.Ownerattr = 'AUDITOBJID', d.Supplierattr = 'ID'
 Where d.Code In ('AUDITTRIGGERPARAM2AUDITOBJ', 'AUDITRESULTPARAM2AUDITOBJ', 'AUDITRECORD2AUDITOBJ');
commit;

--BY WXY 20090814
Create Or Replace Procedure p_Insertone2mrelation(p_Relationspecname In Varchar2,
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
		v_Owntabname := 'XB_' || p_Ownentitycode;
	Elsif p_Bors = 'S' Then
		v_Owntabname := 'XS_' || p_Ownentitycode;
	Else
		v_Owntabname := 'X_' || p_Ownentitycode;
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

--by liurr at 20090814
delete from xm_entityspec2relationspec d where d.relationspecid=(select id from xm_relationspec r where r.code='PHSSIM2MANAGEMENTAREA');
delete from x_versioninstance d where d.subcategory='PHSSIM2MANAGEMENTAREA';
delete from xm_relationspec where code='PHSSIM2MANAGEMENTAREA';
commit;

--delete from xm_entityspec2relationspec d where d.relationspecid=(select id from xm_relationspec r where r.code='PHSSIM2MARKETINGAREA');
--delete from x_versioninstance d where d.subcategory='PHSSIM2MARKETINGAREA';
--delete from xm_relationspec where code='PHSSIM2MARKETINGAREA';
--commit;

exec p_insertM2OneRelation('小灵通SIM卡对应的营销区','PHSSIM2MARKETINGAREA','PHSSIM','MARKETINGAREA','LMANAGEMENTAREAID','ID','');
commit;

update xm_relationspec set RELATIONINSTANCETABLENAME='X_CARD' where RELATIONINSTANCETABLENAME='X_PHSSIM' and code = 'PHSSIM2MARKETINGAREA';
commit;

--by wxy 20090817
EXEC p_insertEntitySpec('WLAN AP板','WLANAPCARD','com.gxlu.ngrm.equipment.WlanAPCard','XB_CARD','XS_WLANAPCARD');
EXEC p_insertEntitySpec('ONU板','ONUCARD','com.gxlu.ngrm.equipment.ONUCard','XB_CARD','XS_ONUCARD');

UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='CARD')
WHERE CORETABLENAME ='XB_CARD' and MAINTAININDICATOR != 1 AND PARENTID IS NULL;
commit;

--by liurr at 20090818 for 这个语句在整理核心库的元数据前已存在，重新执行
update xm_entitydescriptor d set d.columnname='MARKETINGAREAID',d.attrname='MARKETINGAREAID' where d.entityspecid=(select id from xm_entityspec e where e.code='CHANNEL') and d.columnname='MANAGEMENTAREAID';
commit;

--by liurr at 20090818
EXEC p_insertEntityDescriptor('ROOM','XS_ROOM');
COMMIT;

--by wxy 20090818

delete from xm_entitydescriptor d where d.columnname='RECORDXML';
delete from xm_entitydescriptor d where d.columnname='TEMPLATEXML';
commit;

exec p_Insertentityspec('中继端口','RELAYPORT','com.gxlu.ngrm.equipment.RelayPort','XB_PORT','XS_RELAYPORT');
exec p_Insertentityspec('信令端口','SIGNALPORT','com.gxlu.ngrm.equipment.SignalPort','XB_PORT','XS_SIGNALPORT');
exec p_Insertentityspec('电源口','POWERPORT','com.gxlu.ngrm.equipment.PowerPort','XB_PORT','XS_POWERPORT');

update xm_entityspec set parentid=(select id from xm_entityspec where code='PORT') WHERE CODE in ('RELAYPORT','SIGNALPORT','POWERPORT');
COMMIT;

--by liurr at 20090818
EXEC p_insertEntityDescriptor('SITE','XS_SITE');
COMMIT;

---XS_CHANNEL中已经没有ID字段
DELETE FROM xm_entitydescriptor where entityspecid = (select id from xm_entityspec where code = 'CHANNEL' ) and columnname = 'ID';
COMMIT;

--by liurr at 20090819
EXEC p_insertComponentDescriptor('DSLAM','XC_DSLAM');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR SET COLUMNTYPE=4,DATATYPE=4,DATALENGTH=10 WHERE 
       ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='LOGICALAREAMAPPING') 
       AND COLUMNNAME = 'AREALEVEL';

COMMIT;

--by liurr at 20090819
delete from xm_entitydescriptor where entityspecid = (select id from xm_entityspec a where a.satellitetablename = 'XS_PHSLOGICALCODE') and columnname = 'ID';
COMMIT;

exec p_Insertentitydescriptor('PHSLOGICALCODE','XS_PHSLOGICALCODE');
commit;

--by wxy 20090819

exec p_insertM2OneRelation('端口对应的父端口','PORT2PARENT','PORT','PORT','PARENTID','ID','B');
exec p_insertOne2MRelation('端口对应的子端口','PORT2CHILD','PORT','PORT','PARENTID','B');
commit;

delete from xm_entitydescriptor d 
 where d.columnname='LAYERRATE' 
   and d.entityspecid =(select Id from xm_entityspec where code='LINKTYPE');
Commit;


delete from xm_entitydescriptor d 
 where d.columnname='LAYERRATE' 
   and d.entityspecid =(select Id from xm_entityspec where code='LINK');
Commit;

exec p_insertentitydescriptor('LINKTYPE','XB_LINKTYPE');
exec p_insertentitydescriptor('LINK','XB_LINK');
commit;

exec p_insertM2OneRelation('连接类型对应的带宽','LINKTYPE2BANDWIDTH','LINKTYPE','BANDWIDTH','BANDWIDTHID','ID','B');
exec p_insertM2OneRelation('连接对应的带宽','LINK2BANDWIDTH','LINK','BANDWIDTH','BANDWIDTHID','ID','B');
Commit;

exec p_Insertentityspec('路由终端','ROUTEEND','com.gxlu.ngrm.network.RouteEnd','XB_ROUTEEND','');
exec p_Insertentitydescriptor('ROUTEEND','XB_ROUTEEND');
COMMIT;

exec p_insertM2OneRelation('路由终端对应的连接','ROUTEEND2LINK','ROUTEEND','LINK','LINKID','ID','B');
exec p_insertM2OneRelation('路由终端对应的连接路由','ROUTEEND2LINKROUTE','ROUTEEND','LINKROUTE','LINKROUTEID','ID','B');
exec p_insertM2OneRelation('路由终端对应的终端点','ROUTEEND2PORT','ROUTEEND','PORT','ENDPOINTID','ID','B');
COMMIT;


--by liurr at 20090819 for 删除XS_RESOURCECOVER的描述，该表已去掉
delete from xm_entityspec2relationspec where entityspecid = (select id from xm_entityspec where code = 'RESOURCECOVER');
delete from xm_entitydescriptor where entityspecid=(select id from xm_entityspec where code='RESOURCECOVER');
delete from xm_entityspec where code='RESOURCECOVER';
delete from x_versioninstance where subcategory='RESOURCECOVER';
commit;

--现场没更新过来，重新添加前头的语句
Update xm_relationspec Set OWNERATTR = 'CODEPOOLID',SUPPLIERATTR = 'CODEID' Where CODE = 'CODEPOOLCODEASSIGN';
Update xm_relationspec Set OWNERATTR = 'CODEID',SUPPLIERATTR = 'PSTNPORTID' Where CODE = 'CODEPSTNPORTRELATION';
Update xm_relationspec Set OWNERATTR = 'CODESEGMENTID',SUPPLIERATTR = 'PSTNNEID' Where CODE = 'CODESEGPSTNNERELATION';
Update xm_relationspec Set OWNERATTR = 'PHYSICALCONTAINERID',SUPPLIERATTR = 'CARDID' Where CODE = 'CONTAINERCARDASSIGN';
Update xm_relationspec Set OWNERATTR = 'IVPNNOID',SUPPLIERATTR = 'PSTNNEID' Where CODE = 'IVPNNOPSTNNERELATION';
Update xm_relationspec Set OWNERATTR = 'ALOCATIONID',SUPPLIERATTR = 'ZLOCATIONID' Where CODE = 'LOCATIONRELATION';
Update xm_relationspec Set OWNERATTR = 'LOCATIONID',SUPPLIERATTR = 'LOCATIONTYPEID' Where CODE = 'LOCATON2LOCATIONTYPE';
Update xm_relationspec Set OWNERATTR = 'SERVICEORDERID',SUPPLIERATTR = 'PRODINSID' Where CODE = 'ORDERTICKETPINSASSOCBAK';
Update xm_relationspec Set OWNERATTR = 'SERVICEORDERID',SUPPLIERATTR = 'PRODINSID' Where CODE = 'ORDERTICKETPRODINSASSOC';
Update xm_relationspec Set OWNERATTR = 'PARTYROLEID',SUPPLIERATTR = 'RESSERVICESPECID' Where CODE = 'PARTYROLERESSERVICESPEC';
Update xm_relationspec Set OWNERATTR = 'PARTYROLEID',SUPPLIERATTR = 'TASKID' Where CODE = 'PARTYROLETASKASSIGN';
Update xm_relationspec Set OWNERATTR = 'PRODINSID',SUPPLIERATTR = 'RESSERVINSID' Where CODE = 'PRODINSRESSERINSASSOCBAK';
Update xm_relationspec Set OWNERATTR = 'PRODINSID',SUPPLIERATTR = 'RESSERVINSID' Where CODE = 'PRODINSRESSERVINSASSOC';
Update xm_relationspec Set OWNERATTR = 'PARENTID',SUPPLIERATTR = 'CHILDID' Where CODE = 'RESSERVINSASSIGN';
Update xm_relationspec Set OWNERATTR = 'PARENTID',SUPPLIERATTR = 'CHILDID' Where CODE = 'RESSERVINSASSIGNBAK';
Update xm_relationspec Set OWNERATTR = 'ROOMID',SUPPLIERATTR = 'SUITEID' Where CODE = 'ROOM2SUITE';
COMMIT;

--by liurr at 20090819
create or replace procedure p_update_dictionary_valueen as
begin
  for R in(Select DISTINCT ATTRIBUTEID
             From x_Dictionary
            Where attributeid like '%SPEC'
            AND VALUEEN IS NULL
           ) loop
	begin
       UPDATE X_DICTIONARY XD SET VALUEEN = 
              (SELECT CLASSNAME FROM XM_ENTITYSPEC XE WHERE XE.NAME = XD.DESCRIPTION) WHERE XD.ATTRIBUTEID = R.ATTRIBUTEID;
	end;
	end loop;
  COMMIT;
end;
/

exec p_update_dictionary_valueen;

--X_CUSTOMER上描述修改
UPDATE XM_ENTITYDESCRIPTOR SET COLUMNNAME = 'LINKMOBILEPHONE', ATTRNAME = 'LINKMOBILEPHONE' WHERE ENTITYSPECID =  (select ID from xm_entityspec where coretablename = 'X_CUSTOMER') AND COLUMNNAME = 'LINKMOBILE';
COMMIT;

delete from xm_entitydescriptor where entityspecid = ( select id from xm_entityspec where code = 'PHSCODESEGMENT') and columnname = 'ID';
COMMIT;

--by wxy 20090820

--C网实际上是6月24号在广州做好的，但是一直没有提交
--by wxy 20090624

--metadata
exec p_InsertEntitySpec('塔桅','TOWERMAST','com.gxlu.ngrm.equipment.TowerMast','XB_PHYSICALCONTAINER','XS_TOWERMAST');

update xm_EntitySpec d set d.Satellitetablename='XS_BTS' WHERE D.Code='BTS';
delete from Xm_Entitydescriptor d where d.Entityspecid=(select id from Xm_Entityspec e where e.Code='BTS');
commit;
exec p_insertEntityDescriptor('BTS','XS_BTS');
commit;

exec p_InsertEntitySpec('拉远模块','REMOTEAMPLIFIER','com.gxlu.ngrm.equipment.RemoteAmplifier','XB_MANAGEDELEMENT','XS_REMOTEAMPLIFIER');

exec p_InsertEntitySpec('直放站','REPEATOR','com.gxlu.ngrm.equipment.Repeator','XB_MANAGEDELEMENT','XS_REPEATOR');
exec p_InsertEntitySpec('扇区','SECTOR','com.gxlu.ngrm.equipment.Sector','XB_MANAGEDELEMENT','XS_SECTOR');
exec p_InsertEntitySpec('载频','CARRIERFREQUENCY','com.gxlu.ngrm.equipment.CarrierFrequency','XB_MANAGEDELEMENT','XS_CARRIERFREQUENCY');

exec p_InsertEntitySpec('室分系统','DISTRIBUTESYSTEM','com.gxlu.ngrm.equipment.DistributeSystem','XB_MANAGEDELEMENT','XS_DISTRIBUTESYSTEM');
exec p_InsertEntitySpec('合路器','JOINROUTE','com.gxlu.ngrm.equipment.JoinRoute','XB_MANAGEDELEMENT','XS_JOINROUTE');
exec p_InsertEntitySpec('功分器','WORKDISPARTINSTRUMENT','com.gxlu.ngrm.equipment.WorkDispartInstrument','XB_MANAGEDELEMENT','XS_WORKDISPARTINSTRUMENT');
exec p_InsertEntitySpec('耦合器','COUPLINGINSTRUMENT','com.gxlu.ngrm.equipment.CouplingInstrument','XB_MANAGEDELEMENT','XS_COUPLINGINSTRUMENT');
exec p_InsertEntitySpec('电桥','ELECTRICBRIDGE','com.gxlu.ngrm.equipment.ElectricBridge','XB_MANAGEDELEMENT','XS_ELECTRICBRIDGE');
exec p_InsertEntitySpec('负载','LOAD','com.gxlu.ngrm.equipment.Load','XB_MANAGEDELEMENT','XS_LOAD');
exec p_InsertEntitySpec('天线','ANTENNA','com.gxlu.ngrm.equipment.Antenna','XB_MANAGEDELEMENT','XS_ANTENNA');
exec p_InsertEntitySpec('馈线','PRESENTLINE','com.gxlu.ngrm.equipment.PresentLine','XB_MANAGEDELEMENT','XS_PRESENTLINE');

exec p_InsertEntitySpec('BSC','BSC','com.gxlu.ngrm.equipment.BSC','XB_MANAGEDELEMENT','XS_BSC');
exec p_InsertEntitySpec('PCF','PCF','com.gxlu.ngrm.equipment.PCF','XB_MANAGEDELEMENT','XS_PCF');

exec p_InsertEntitySpec('MSC','MSC','com.gxlu.ngrm.equipment.MSC','XB_MANAGEDELEMENT','XS_MSC');

exec p_InsertEntitySpec('HLR','HLR','com.gxlu.ngrm.equipment.HLR','XB_MANAGEDELEMENT','XS_HLR');
exec p_InsertEntitySpec('VLR','VLR','com.gxlu.ngrm.equipment.VLR','XB_MANAGEDELEMENT','XS_VLR');
exec p_InsertEntitySpec('H/LSTP','HLSTP','com.gxlu.ngrm.equipment.HLSTP','XB_MANAGEDELEMENT','XS_HLSTP');
exec p_InsertEntitySpec('IGW','IGW','com.gxlu.ngrm.equipment.IGW','XB_MANAGEDELEMENT','XS_IGW');
exec p_InsertEntitySpec('MGW','MGW','com.gxlu.ngrm.equipment.MGW','XB_MANAGEDELEMENT','XS_MGW');

exec p_InsertEntitySpec('PDSN','PDSN','com.gxlu.ngrm.equipment.PDSN','XB_MANAGEDELEMENT','XS_PDSN');
exec p_InsertEntitySpec('AAA','AAA','com.gxlu.ngrm.equipment.AAA','XB_MANAGEDELEMENT','XS_AAA');
exec p_InsertEntitySpec('FACN','FACN','com.gxlu.ngrm.equipment.FACN','XB_MANAGEDELEMENT','XS_FACN');
exec p_InsertEntitySpec('DNS','DNS','com.gxlu.ngrm.equipment.DNS','XB_MANAGEDELEMENT','XS_DNS');
exec p_InsertEntitySpec('NTP','NTP','com.gxlu.ngrm.equipment.NTP','XB_MANAGEDELEMENT','XS_NTP');
exec p_InsertEntitySpec('业务平台','PLATFORM','com.gxlu.ngrm.equipment.Platform','XB_MANAGEDELEMENT','XS_PLATFORM');

exec p_InsertEntitySpec('室分系统和信源设备之间的约束','DISTRSYS2BTSCONSTRAINT','com.gxlu.ngrm.equipment.Distrsys2BTSConstraint','XB_DISTRSYS2BTSCONSTRAINT','');

commit;

--组件
exec p_insertComponetSpec('HLR组件','HLR');
exec p_insertComponetSpec('VLR组件','VLR');
exec p_insertComponetSpec('H/LSTP组件','HLSTP');
exec p_insertComponetSpec('IGW组件','IGW');
exec p_insertComponetSpec('MGW组件','MGW');


--设置父亲
update Xm_Entityspec d set d.Parentid = 
				(select id from Xm_Entityspec p where d.Coretablename = p.Coretablename and p.Maintainindicator = 1 )
 where d.Coretablename = 'XB_PHYSICALCONTAINER' 
   and d.Maintainindicator = 3;
commit;

update Xm_Entityspec d set d.Parentid = 
				(select id from Xm_Entityspec p where d.Coretablename = p.Coretablename and p.Maintainindicator = 1 )
 where d.Coretablename = 'XB_MANAGEDELEMENT' 
   and d.Maintainindicator = 3;
commit;

update Xm_Entityspec d set d.Parentid = 
		(select id from xm_entityspec d where d.satellitetablename='XS_BTS')
 where d.satellitetablename ='XS_REMOTEAMPLIFIER';
commit;

Update Xm_Entityspec d Set d.Stsequence=Null Where d.parentid in(1,3,11) And d.Maintainindicator=3;
commit;

UPDATE X_DICTIONARY XD SET VALUEEN = (SELECT CLASSNAME FROM XM_ENTITYSPEC XE WHERE XE.NAME = XD.DESCRIPTION) WHERE XD.ATTRIBUTEID = 'HOLDERSPEC' AND XD.VALUEEN IS NULL;
COMMIT;

--字典出现了共用情况
update xm_EntityDescriptor d set d.Dicclassname = 'REPEATOR' 
 where d.Entityspecid = (select id from xm_entityspec e where e.Code = 'REMOTEAMPLIFIER') 
   and d.Dicattributename in ('INSTALLMODE','RECOVERTYPE','AMPLIFIERTYPE');
commit;

update xm_EntityDescriptor d set d.Dicclassname = 'MANAGEDELEMENT' 
 where d.Entityspecid in (select id from xm_entityspec e where e.Code in('BTS','REPEATOR','REMOTEAMPLIFIER') )
   and d.Dicattributename = 'RECOVERAREATYPE';
commit;



--增加外键引用的多对一的关系
exec p_insertM2OneRelation('塔桅所属直放站','TOWERMAST2REPEATOR','TOWERMAST','REPEATOR','REPEATORID','ENTITYID','S');

exec p_insertM2OneRelation('BTS归属的BSC','BTS2BSC','BTS','BSC','BSCID','ENTITYID','S');

exec p_insertM2OneRelation('直放站对应的近端/远端机设备','REPEATOR2ME','REPEATOR','MANAGEDELEMENT','SNNEID','ENTITYID','S');
exec p_insertM2OneRelation('直放站归属扇区','REPEATOR2SECTOR','REPEATOR','SECTOR','SECTORID','ENTITYID','S');

exec p_insertM2OneRelation('扇区归属的BTS','SECTOR2BTS','SECTOR','BTS','BTSID','ENTITYID','S');

exec p_insertM2OneRelation('载频归属的扇区','CARRIERFREQUENCY2SECTOR','CARRIERFREQUENCY','SECTOR','SECTORID','ENTITYID','S');
exec p_insertM2OneRelation('载频归属的BTS','CARRIERFREQUENCY2BTS','CARRIERFREQUENCY','BTS','BTSID','ENTITYID','S');

exec p_insertM2OneRelation('合路器所属室分系统','JOINROUTE2DISTRIBUTESYSTEM','JOINROUTE','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('功分器所属室分系统','WORKDISPARTINSTRUMENT2DISTRIBUTESYSTEM','WORKDISPARTINSTRUMENT','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('耦合器所属室分系统','COUPLINGINSTRUMENT2DISTRIBUTESYSTEM','COUPLINGINSTRUMENT','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('电桥所属室分系统','ELECTRICBRIDGE2DISTRIBUTESYSTEM','ELECTRICBRIDGE','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('负载所属室分系统','LOAD2DISTRIBUTESYSTEM','LOAD','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('天线关联扇区','ANTENNA2SECTOR','ANTENNA','SECTOR','SECTORID','ENTITYID','S');
exec p_insertM2OneRelation('天线所属BTS','ANTENNA2BTS','ANTENNA','BTS','BTSID','ENTITYID','S');
exec p_insertM2OneRelation('馈线关联天线','PRESENTLINE2ANTENNA','PRESENTLINE','ANTENNA','ANTENNAID','ENTITYID','S');

exec p_insertM2OneRelation('PDSN关联的AAA','PDSN2AAA','PDSN','AAA','AAAID','ENTITYID','S');
exec p_insertM2OneRelation('PDSN关联的FACN','PDSN2FACN','PDSN','FACN','FACNID','ENTITYID','S');

exec p_insertM2OneRelation('室分系统和信源设备约束对应的室分系统','DISTRSYS2BTSCONSTRAINT2DIST','DISTRSYS2BTSCONSTRAINT','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ID','B');
exec p_insertM2OneRelation('室分系统和信源设备约束对应的BTS','DISTRSYS2BTSCONSTRAINT2BTS','DISTRSYS2BTSCONSTRAINT','BTS','BTSID','ID','B');
exec p_insertM2OneRelation('室分系统和信源设备约束对应的扇区','DISTRSYS2BTSCONSTRAINT2SECTOR','DISTRSYS2BTSCONSTRAINT','SECTOR','SECTORID','ID','B');

commit;

--增加多对多的关系
--exec p_insertM2NRelation('BSC和MSC的关系','BSC2MSC','XR_BSC_MSC','BSC','MSC','BSCID','MSCID');
exec p_insertM2NRelation('BSC和PCF的关系','BSC2PCF','XR_BSC_PCF','BSC','PCF','BSCID','PCFID');

--

--增加实体和组件的关系
exec p_insertESpec2CSpec('MSC','VLR');
exec p_insertESpec2CSpec('MSC','HLSTP');

exec p_insertESpec2CSpec('HLR','HLR');
exec p_insertESpec2CSpec('VLR','VLR');

exec p_insertESpec2CSpec('HLSTP','HLSTP');
exec p_insertESpec2CSpec('IGW','IGW');
exec p_insertESpec2CSpec('MGW','MGW');

--by wxy 20090820

exec p_insertM2OneRelation('割接工程所属区域','MIGRATIONPROJECT2LOC','MIGRATIONPROJECT','LOCATION','LOCATIONID','ID','B');
exec p_insertM2OneRelation('割接批次所属区域','MIGRATIONBATCH2LOC','MIGRATIONBATCH','LOCATION','LOCATIONID','ID','B');
commit;

Update XM_ENTITYSPEC D Set D.CORETABLENAME='XB_PRODUCTINSHIS',D.CTSEQUENCE='S_XB_PRODUCTINSHIS' Where D.CODE = 'PRODUCTINSHIS';
Update XM_ENTITYSPEC D Set D.CORETABLENAME='XB_SERVPRODINSHIS',D.CTSEQUENCE='S_XB_SERVPRODINSHIS' Where D.CODE = 'SERVPRODINSHIS';
Update XM_ENTITYSPEC D Set D.CORETABLENAME='XB_RESSERVINSHIS',D.CTSEQUENCE='S_XB_RESSERVINSHIS' Where D.CODE = 'RESSERVINSHIS';
Update XM_ENTITYSPEC D Set D.CORETABLENAME='XB_TICKETRESSERVICEASSOCHIS',D.CTSEQUENCE='S_XB_TICKETRESSERVICEASSOCHIS' Where D.CODE = 'TICKETRESSERVICEASSOCHIS';
COMMIT;

Update XM_RELATIONSPEC D Set D.RELATIONINSTANCETABLENAME='XR_RESSERVINSASSIGNHIS' Where D.CODE = 'RESSERVINSASSIGNHIS';
Update XM_RELATIONSPEC D Set D.RELATIONINSTANCETABLENAME='XR_PRODINSRESSERINSASSOCHIS' Where D.CODE = 'PRODINSRESSERINSASSOCHIS';
Update XM_RELATIONSPEC D Set D.RELATIONINSTANCETABLENAME='XR_ORDERTICKETPINSASSOCHIS' Where D.CODE = 'ORDERTICKETPRODINSASSOCHIS';
Update XM_RELATIONSPEC D Set D.RELATIONINSTANCETABLENAME='XB_TICKETRESSERVICEASSOCHIS' Where D.RELATIONINSTANCETABLENAME='XB_TICKETRESSERVICEASSOCBAK' ;
COMMIT;

exec p_insertentitydescriptor('BSC','XS_BSC');
exec p_insertM2OneRelation('BSC关联的MGW','BSC2MGW','BSC','MGW','MGWID','ENTITYID','S');
COMMIT;

exec p_insertentitydescriptor('MGW','XS_MGW');
exec p_insertM2OneRelation('MGW关联的MSC','MGW2MSC','MGW','MSC','MSCID','ENTITYID','S');
COMMIT;

--by wxy 20090824
Update XM_ENTITYDESCRIPTOR SET COLUMNNAME = 'LASTWORKORDERCODE',ATTRNAME = 'LASTWORKORDERCODE' 
 WHERE COLUMNNAME='LASTWORKORDERCO' AND ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE='RESSERVINS');

COMMIT;

--by liurr at 20090824
exec p_insertM2OneRelation('产品实例所属的营销区','PRODINS2MARKETINGAREA','PRODINS','MARKETINGAREA','LOCATIONID','ID','B');
COMMIT;

--by liurr at 20090825
exec p_Insertentitydescriptor('FACILITY','XB_FACILITY');
COMMIT;   

--by wxy 20090825
--metaData
exec p_Insertentitydescriptor('PORT','XB_PORT');
exec p_Insertentitydescriptor('LINKTYPE','XB_LINKTYPE');
exec p_Insertentitydescriptor('BANDWIDTH','XB_BANDWIDTH');
exec p_Insertentitydescriptor('LINKENDCONSTRAINT','XB_LINKENDCONSTRAINT');
exec p_Insertentitydescriptor('LINKROUTECONSTRAINT','XB_LINKROUTECONSTRAINT');
exec p_Insertentitydescriptor('LAYERRATEMAPRULE','XB_LAYERRATEMAPRULE');
Commit;

Delete From Xm_Entitydescriptor
 Where Entityspecid = (Select Id From Xm_Entityspec Where Code = 'LINKROUTECONSTRAINT')
   And columnname = 'ROUTEMODE';
Delete From Xm_Entitydescriptor
 Where Entityspecid = (Select Id From Xm_Entityspec Where Code = 'LINKROUTE')
   And columnname = 'ROUTEMODE';
Delete From Xm_Entitydescriptor
 Where Entityspecid = (Select Id From Xm_Entityspec Where Code = 'ROUTEEND')
   And columnname = 'LABELXPOS';
Delete From Xm_Entitydescriptor
 Where Entityspecid = (Select Id From Xm_Entityspec Where Code = 'ROUTEEND')
   And columnname = 'LABELYPOS';
Commit;

Update Xm_Entitydescriptor d
   Set d.attrdispname = '层速率'
 Where Entityspecid = (Select Id From Xm_Entityspec Where Code = 'PORT')
   And columnname = 'LAYERRATE';
Commit;

update xm_entityspec d set d.coretablename='XB_ADDRESS',d.ctsequence='S_XB_ADDRESS' where code = 'ADDRESS';
Commit;

Exec p_insertentitydescriptor('ADDRESS','XB_ADDRESS');
Commit;

--by wxy 20090826
CREATE OR REPLACE Procedure p_Insertm2onerelation(p_Relationspecname In Varchar2,
																									p_Relationspeccode In Varchar2,
																									p_Ownentitycode    In Varchar2,
																									p_Suppentitycode   In Varchar2,
																									p_Supplierattr     In Varchar2,	
																									p_Ownerattr        In Varchar2,																																																	
																									p_Bors             In Varchar2) As
	v_Versioninstanceid Number(18);
	v_Ownentityspecid   Number(18);
	v_Owntabname        Varchar2(255);
	v_oEntityspecver     Number(18);
  v_sEntityspecver     Number(18);
	v_Suppentityspecid  Number(18);
Begin

	/**created by wxy 200903 增加多对1的关系描述
   exec p_insertM2OneRelation('连接对应的父连接','LINK2PARENT','LINK','LINK','PARENTID','ID','B');
  */

	If p_Bors = 'B' Then
		v_Owntabname := 'XB_' || p_Ownentitycode;
	Elsif p_Bors = 'S' Then
		v_Owntabname := 'XS_' || p_Ownentitycode;
  Elsif p_Bors = 'M' Then
		v_Owntabname := 'XM_' || p_Ownentitycode;
	Else
		v_Owntabname := 'X_' || p_Ownentitycode;
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
	Select Id, Versioninstance Into v_Ownentityspecid, v_oEntityspecver From Xm_Entityspec Where Code = p_Ownentitycode;
	Select Id, Versioninstance Into v_Suppentityspecid,v_sEntityspecver From Xm_Entityspec Where Code = p_Suppentitycode;

	--n:1
	Insert Into Xm_Relationspec
		(Id, Name, Code, Relationinstancetablename, Multiple, Ownerentityspecid, Supplierentityspecid, Ownerattr,
		 Supplierattr, Versioninstance, Version, Maintainindicator, Createdate, Status)
	Values
		(s_Xm_Relationspec.Nextval, p_Relationspecname, p_Relationspeccode, v_Owntabname, 4, v_Ownentityspecid,
		 v_Suppentityspecid, p_Ownerattr, p_Supplierattr, v_Versioninstanceid, 1, 3, Sysdate, 1);

	--
	Insert Into Xm_Entityspec2relationspec
		(Id, Entityspecid, Entityspecversioninstance, Relationspecid, Relationspecversioninstance, Status, Version,
		 Cascadedelete, Maintenaceindicator)
	Values
		(s_Xm_Entityspec2relationspec.Nextval, v_Ownentityspecid, v_oEntityspecver, s_Xm_Relationspec.Currval,
		 v_Versioninstanceid, 1, 1, 2, 3);

--如果是同一个实体之间的关系，只要在实体对应的关系表中插入1条记录即可。
	If (p_Ownentitycode <> p_Suppentitycode) Then
    Insert Into Xm_Entityspec2relationspec
  		(Id, Entityspecid, Entityspecversioninstance, Relationspecid, Relationspecversioninstance, Status, Version,
  		 Cascadedelete, Maintenaceindicator)
  	Values
  		(s_Xm_Entityspec2relationspec.Nextval, v_Suppentityspecid, v_sEntityspecver, s_Xm_Relationspec.Currval,
  		 v_Versioninstanceid, 1, 1, 2, 3);
  End If;

	Update x_Versioninstance Set Entityid = s_Xm_Relationspec.Currval Where Id = v_Versioninstanceid;

	/**
  --端口和端口类型N：1
  Insert into X_VERSIONINSTANCE
     (ID, CATEGORY, SUBCATEGORY, ENTITYID, MAINTAINMETHOD, VERSIONCODE, REVISIONFORMAT, MAJORVERSION, MINORVERSION, BUILDNUMBER, MAJORVERSIONSTEP, MINORVERSIONSTEP, BUILDNUMBERSTEP, VALIDPERIOD, VERSION)
   Values
     (385, 'RelationSpec', 'VersionInstance', 95, 0, '1.1.1', '${majorVersion}${delimiter}${minorVersion}${delimiter}${buildNumber}', '1', '1', '0', '0', '0', '1', TO_DATE('12/31/2009 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1);

  --n:1
  Insert into XM_RELATIONSPEC
     (ID, NAME, CODE, RELATIONINSTANCETABLENAME, MULTIPLE, OWNERENTITYSPECID, SUPPLIERENTITYSPECID, OWNERATTR, SUPPLIERATTR, VERSIONINSTANCE, VERSION, MAINTAININDICATOR, CREATEDATE, STATUS)
   Values
     (95, '端口对应的端口类型', 'PORT2PORTTYPE', 'XB_PORT', 4, 4, 37, 'ID', 'PORTTYPEID', 385, 1, 2, SYSDATE, 1);

  --
  Insert into XM_ENTITYSPEC2RELATIONSPEC
     (ID, ENTITYSPECID, ENTITYSPECVERSIONINSTANCE, RELATIONSPECID, RELATIONSPECVERSIONINSTANCE, STATUS, VERSION, CASCADEDELETE, MAINTENACEINDICATOR)
   Values
     (122, 4, 104, 95, 385, 1, 1, 2, 3);

  Insert into XM_ENTITYSPEC2RELATIONSPEC
     (ID, ENTITYSPECID, ENTITYSPECVERSIONINSTANCE, RELATIONSPECID, RELATIONSPECVERSIONINSTANCE, STATUS, VERSION, CASCADEDELETE, MAINTENACEINDICATOR)
   Values
     (123, 37, 105, 95, 385, 1, 1, 2, 3);
  */

End;
/


--by wxy 20090826
exec p_insertEntityDescriptor('LOCATION','XB_LOCATION');
exec p_insertEntityDescriptor('MANAGEDELEMENT','XB_MANAGEDELEMENT');
exec p_insertEntityDescriptor('PHYSICALCONTAINER','XB_PHYSICALCONTAINER');
exec p_insertEntityDescriptor('CARD','XB_CARD');
exec p_insertEntityDescriptor('PORT','XB_PORT');
Commit;

Delete From XM_ENTITYSPEC2RELATIONSPEC Where RELATIONSPECID In(Select Id From XM_RELATIONSPEC Where CODE = 'PORT2MANAGEMENTAREA');
Delete From XM_RELATIONSPEC Where CODE = 'PORT2MANAGEMENTAREA';
Delete From X_VERSIONINSTANCE Where SUBCATEGORY = 'PORT2MANAGEMENTAREA';
Commit;

Exec p_insertM2OneRelation('空间资源所属维护管理区','LOCATION2MANAGEMENTAREA','LOCATION','MANAGEMENTAREA','MANAGEMENTAREAID','ID','B');
Exec p_insertM2OneRelation('空间资源所属营销区','LOCATION2MARKETINGAREA','LOCATION','MARKETINGAREA','MARKETINGAREAID','ID','B');

Exec p_insertM2OneRelation('网元所属维护管理区','ME2MANAGEMENTAREA','MANAGEDELEMENT','MANAGEMENTAREA','MANAGEMENTAREAID','ID','B');
Exec p_insertM2OneRelation('容器所属维护管理区','CONTAINER2MANAGEMENTAREA','PHYSICALCONTAINER','MANAGEMENTAREA','MANAGEMENTAREAID','ID','B');
Exec p_insertM2OneRelation('板卡所属维护管理区','CARD2MANAGEMENTAREA','CARD','MANAGEMENTAREA','MANAGEMENTAREAID','ID','B');
Exec p_insertM2OneRelation('端口所属维护管理区','PORT2MANAGEMENTAREA','PORT','MANAGEMENTAREA','MANAGEMENTAREAID','ID','B');
Commit;


Update xm_entitydescriptor d 
   Set d.columntype=14,d.datatype=14,d.datalength=15,d.dicclassname=Null,d.dicattributename=Null
 Where d.columnname In('MAXBANDWIDTH','FREEBANDWIDTH')
   And d.entityspecid =(Select Id From xm_entityspec Where code='LINK');
Commit;

Update xm_entitydescriptor d 
   Set d.dicclassname='METADATA'
 Where d.columnname In('MAINTAININDICATOR')
   --And d.entityspecid =(Select Id From xm_entityspec Where code='LINKTYPE');
Commit;

exec p_InsertEntitySpec('方案设计记录表','PROJECTDESIGNRECORD','com.gxlu.ngrm.resService.ProjectDesignRecord','X_PROJECTDESIGNRECORD','');
exec p_insertEntityDescriptor('PROJECTDESIGNRECORD','X_PROJECTDESIGNRECORD');
Commit;

exec p_insertM2OneRelation('方案设计记录所属方案','DESIGNRECORD2PROJECTINS','PROJECTDESIGNRECORD','PROJECTINSTANCE','PROJECTINSTANCEID','ID','');
exec p_insertM2OneRelation('方案设计记录所属资源服务实例','DESIGNRECORD2RESSERVINS','PROJECTDESIGNRECORD','RESSERVINS','RESSERVINSID','ID','');
exec p_insertM2OneRelation('方案设计记录关联终端点','DESIGNRECORD2ENDPOINT','PROJECTDESIGNRECORD','PORT','ENDPOINTID','ID','');
Commit;

update xm_entitydescriptor d set d.dicclassname='PORT' where d.columnname='DIRECTION' and d.entityspecid=(select id from xm_entityspec where code='PORT');

Commit;

--by wxy 20090827
Update xm_entitydescriptor d 
   Set d.dicclassname='LINK'
 Where d.columnname In('DIRECTION')
   And d.entityspecid =(Select Id From xm_entityspec Where code='MAPLINE');
Commit;

Update xm_entitydescriptor d 
   Set d.dicclassname='PHYSICALRESOURCE'
 Where d.columnname In('DIRECTION')
   And d.entityspecid =(Select Id From xm_entityspec Where code='PORT');
Commit;

exec p_InsertEntitySpec('ATM','ATM','com.gxlu.ngrm.equipment.ATM','XB_MANAGEDELEMENT','XS_ATM');
exec p_InsertEntitySpec('DDN','DDN','com.gxlu.ngrm.equipment.DDN','XB_MANAGEDELEMENT','XS_DDN');
exec p_InsertEntitySpec('FR','FR','com.gxlu.ngrm.equipment.FR','XB_MANAGEDELEMENT','XS_FR');
exec p_InsertEntitySpec('ATM/DDN/FR','ATM/DDN/FR','com.gxlu.ngrm.equipment.ATMDDNFR','XB_MANAGEDELEMENT','XS_ATMDDNFR');
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'MANAGEDELEMENT')
 Where CODE IN('ATM','DDN','FR','ATM/DDN/FR');
Commit;
 
exec p_insertEntityDescriptor('ATM','XS_ATM');
exec p_insertEntityDescriptor('DDN','XS_DDN');
exec p_insertEntityDescriptor('FR','XS_FR');
exec p_insertEntityDescriptor('ATM/DDN/FR','XS_ATMDDNFR');
Commit;


exec p_insertM2OneRelation('字典对应的上级','DICTIONARY2PARENT','DICTIONARY','DICTIONARY','PARENTID','ID','');
exec p_insertOne2MRelation('字典对应的下级','DICTIONARY2CHILD','DICTIONARY','DICTIONARY','PARENTID','');
Commit;

exec p_insertOne2MRelation('逻辑号码段对应的子号码段','LOGICALCODESEGMENT2CHILD','LOGICALCODESEGMENT','LOGICALCODESEGMENT','PARENTID','');
exec p_insertOne2MRelation('客户对应的子客户','CUSTOMER2CHILD','CUSTOMER','CUSTOMER','PARENTID','');
exec p_insertOne2MRelation('域对应的下级域','DOMAIN2CHILD','DOMAIN','DOMAIN','PARENTID','B');
exec p_insertOne2MRelation('任务对应的子任务','TASK2CHILD','TASK','TASK','PARENTID','B');
exec p_insertOne2MRelation('拓扑图节点对应的子节点','MAPNODE2CHILD','MAPNODE','MAPNODE','PARENTID','B');
exec p_insertOne2MRelation('策略事件对应的子事件','POLICYEVENT2CHILD','POLICYEVENT','POLICYEVENT','PARENTID','');
exec p_insertOne2MRelation('策略规则对应的子规则','POLICYRULE2CHILD','POLICYRULE','POLICYRULE','PARENTID','');
Commit;

exec p_InsertEntitySpec('交换端口','SNPORT','com.gxlu.ngrm.equipment.SNPort','XB_PORT','XS_SNPORT');
exec p_insertEntityDescriptor('SNPORT','XS_SNPORT');
Commit;

Update Xm_Entityspec d Set parentid = (select id from xm_entityspec where code = 'PORT') 
 Where d.Code = 'SNPORT';
commit;


--by liurr at 20090827
update xm_relationspec set OWNERATTR = 'ID', SUPPLIERATTR = 'LOCATIONID' where code in ('MIGRATIONPROJECT2LOC','MIGRATIONBATCH2LOC');
commit;

--by liurr at 20090827
exec p_Insertentitydescriptor('MANAGEDELEMENT','XB_MANAGEDELEMENT');
COMMIT; 

exec p_Insertentitydescriptor('PORT','XB_PORT');
COMMIT; 

exec p_Insertentitydescriptor('CARD','XB_CARD');
COMMIT; 

update xm_componentdescriptor  set attrdispname = '域名' 
       where componentspecid = (select id from xm_componentspec where code = 'DSLAM') and columnname = 'DOMAINNAME';
commit;

EXEC p_insertComponentDescriptor('LAN','XC_LAN');
COMMIT;

EXEC p_insertComponentDescriptor('ONU','XC_ONU');
COMMIT;

EXEC p_insertentitydescriptor('PRODINS','XB_PRODINS');
COMMIT;                         

--by liurr at 20090828
EXEC p_insertentitydescriptor('TASK','XB_TASK');
COMMIT;

EXEC p_insertentitydescriptor('ADSLCONFIG','XS_ADSLCONFIG');
COMMIT;                              

EXEC p_insertentitydescriptor('PORT','XB_PORT');
COMMIT;
exec p_insertM2OneRelation('端口对应的反向端口','PORT2OPPPORT','PORT','PORT','OPPPORTID','ID','B');
exec p_insertM2OneRelation('端口对应的映射规则','PORT2LAYERRATEMAPRULE','PORT','LAYERRATEMAPRULE','MAPRULEID','ID','B');
Commit;         

DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (select ID FROM xm_entityspec where code  = 'LINK')
       AND COLUMNNAME IN ('ACTPID','ZCTPID');
COMMIT;

delete from xm_entityspec2relationspec d where d.relationspecid IN (select id from xm_relationspec r where r.code IN('LINK2ACTP','LINK2ZCTP'));
delete from x_versioninstance d where d.subcategory IN ('LINK2ACTP','LINK2ZCTP');
delete from xm_relationspec where code IN ('LINK2ACTP','LINK2ZCTP');
commit;      

--by liurr at 20090829
EXEC p_insertentitydescriptor('ROUTEEND','XB_ROUTEEND');
COMMIT;                  

--by liurr at 20090831
exec p_Insertentityspec('接口日志','INTFLOG','com.gxlu.ngrm.intf.InterfaceLog','X_INTF_LOG','');
exec p_insertentitydescriptor('INTFLOG','X_INTF_LOG');
commit;

--by liurr at 20090901
EXEC p_insertentitydescriptor('MANAGEDELEMENT','XB_MANAGEDELEMENT');
COMMIT;

--by liurr at 20090902
DELETE FROM XM_COMPONENTDESCRIPTOR WHERE COMPONENTSPECID = (SELECT ID FROM XM_COMPONENTSPEC WHERE CODE = 'ONU')
       AND COLUMNNAME IN ('SUBFIBERSERVICENO','OLTDEVICE','PONPORT');
COMMIT;

EXEC p_insertcomponentdescriptor('ONU','XC_ONU');
COMMIT;

--by wxy 20090902

--Update x_menuitem Set path = replace(path,'10.17.36.88','132.121.144.3');
Delete From xm_entityspec2relationspec Where relationspecid=(Select Id From xm_relationspec Where code='TASK2RESSERVINS');
Delete From xm_relationspec Where code='TASK2RESSERVINS';
Delete From x_versioninstance Where subcategory = 'TASK2RESSERVINS';
commit;

exec p_insertM2OneRelation('任务关联的资源服务实例','TASK2RESSERVINS','TASK','RESSERVINS','RESSERVINSID','ID','B');
Commit;         

Exec p_insertentitydescriptor('PORT','XB_PORT');
Commit;

--by liurr at 20090903
EXEC p_insertentitydescriptor('CODESEGMENT','X_CODESEGMENT');
COMMIT;
EXEC p_insertentitydescriptor('CODE','XB_CODE');
COMMIT;
exec p_insertM2NRelation('维护管理区和营销区域的映射','MANAGE2MARKETINGAREA','XR_MANAGE2MARKETINGAREA','MANAGEMENTAREA','MARKETINGAREA','MANAGEMENTAREAID','MARKETINGAREAID');
commit;

--by liurr at 20090903
DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'CODESEGMENT') AND COLUMNNAME = 'USEFOR';
DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'CODE') AND COLUMNNAME = 'USEFOR';
COMMIT;

EXEC p_insertentitydescriptor('PSTNPHYSICALCODE','XS_PSTNPHYSICALCODE');
COMMIT;

--by liurr at 20090903
update xm_entitydescriptor  set attrdispname = '位置'  where entityspecid = (select id from xm_entityspec where code = 'CODESEGMENT') and columnname = 'LOCATIONID';
DELETE FROM xm_entitydescriptor WHERE entityspecid = (select id from xm_entityspec where code = 'CODESEGMENT') and columnname = 'AREAID';
COMMIT;

UPDATE xm_relationspec SET NAME = '号码段对应的区域',SUPPLIERENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'LOCATION') WHERE CODE = 'CODESEGMENT2MARKETINGAREA';
COMMIT;

--by wxy 20090903
exec p_insertM2OneRelation('方案实例对应的资源服务规格','PROJECTINS2RESSERVICESPEC','PROJECTINSTANCE','RESSERVICESPEC','RESSERVICESPECID','ID','');
Commit;         
 
Exec p_insertentitydescriptor('LAYERRATEMAPRULE','XB_LAYERRATEMAPRULE');
Commit;

exec p_InsertEntitySpec('层速率映射子规则','LAYERRATEMAPSUBRULE','com.gxlu.ngrm.network.LayerRateMapSubRule','XB_LAYERRATEMAPSUBRULE','');
Exec p_insertentitydescriptor('LAYERRATEMAPSUBRULE','XB_LAYERRATEMAPSUBRULE');
Commit;

exec p_insertM2OneRelation('层速率映射子规则对应的映射规则','LAYERRATEMAPSUBRULE2MAPRULE','LAYERRATEMAPSUBRULE','LAYERRATEMAPRULE','LAYERRATEMAPRULEID','ID','B');
Commit;         

--Update x_menuitem Set path = replace(path,'10.17.36.88','132.121.144.3');
Delete From xm_entityspec2relationspec Where relationspecid=(Select Id From xm_relationspec Where code='LAYERRATENAMINGRULE2MAPRULE');

Delete From xm_relationspec Where code='LAYERRATENAMINGRULE2MAPRULE';
Delete From x_versioninstance Where subcategory = 'LAYERRATENAMINGRULE2MAPRULE';

Delete From xm_entityDescriptor Where entityspecId = (Select Id From xm_entityspec Where code='LAYERRATENAMINGRULE');
Delete From xm_entityspec Where code='LAYERRATENAMINGRULE';
Delete From x_versioninstance Where subcategory = 'LAYERRATENAMINGRULE';
commit;

--by liurr at 20090903
EXEC p_insertentitydescriptor('LOCATION','XB_LOCATION');
COMMIT;

--by liurr at 20090904
EXEC p_insertentitydescriptor('LOGICALAREAMAPPING','X_LOGICALAREAMAPPING');
COMMIT;

--by wxy 20090904
UPDATE X_DICTIONARY DC SET PARENTID = (SELECT ID FROM X_DICTIONARY DP WHERE DP.CLASSID = 'METADATA' AND DP.ATTRIBUTEID = 'GROUPTYPE' AND DP.DESCRIPTION = '网元')
 WHERE DC.CLASSID = 'MANAGEDELEMENT' AND DC.ATTRIBUTEID = 'NESPEC' AND DC.PARENTID IS NULL;
COMMIT;

UPDATE X_DICTIONARY XD SET VALUEEN = (SELECT CLASSNAME FROM XM_ENTITYSPEC XE WHERE XE.NAME = XD.DESCRIPTION) WHERE XD.ATTRIBUTEID = 'NESPEC' AND XD.VALUEEN IS NULL;
COMMIT;

Update xm_entityspec d Set d.Name ='跳接类型' Where code = 'CROSSCONNECTIONTYPE';
commit;


exec p_InsertEntitySpec('版本规格','VERSIONSPEC','com.gxlu.ngrm.resService.VersionSpec','X_VERSIONSPEC','');
Exec p_insertentitydescriptor('VERSIONSPEC','X_VERSIONSPEC');
Commit;

exec p_InsertEntitySpec('版本实例','VERSIONINSTANCE','com.gxlu.ngrm.resService.VersionInstance','X_VERSIONINSTANCE','');
Exec p_insertentitydescriptor('VERSIONINSTANCE','X_VERSIONINSTANCE');
Commit;

exec p_insertM2OneRelation('资源服务规格所属版本','RESSERVICESPEC2VERSIONINS','RESSERVICESPEC','VERSIONINSTANCE','VERSIONINSTANCEID','ID','');
Commit;


--by wxy 20090907
exec p_InsertEntitySpec('资源服务规格关系','RESSERVICESPECASSIGN','com.gxlu.ngrm.resService.ResServiceSpecAssign','X_RESSERVICESPECASSIGN','');
Exec p_insertentitydescriptor('RESSERVICESPECASSIGN','X_RESSERVICESPECASSIGN');
Commit;

exec p_insertM2OneRelation('资源服务规格关系对应的上级规格','RESSERVICESPECASSIGN2PARENT','RESSERVICESPECASSIGN','RESSERVICESPEC','PARENTID','ID','');
exec p_insertM2OneRelation('资源服务规格关系对应的下级规格','RESSERVICESPECASSIGN2CHILD','RESSERVICESPECASSIGN','RESSERVICESPEC','CHILDID','ID','');

exec p_insertM2OneRelation('资源服务规格关系所属版本','RESSERVICESPECASSIGN2VERSIONINS','RESSERVICESPECASSIGN','VERSIONINSTANCE','VERSIONINSTANCEID','ID','');
Commit;

Delete From xm_entitymgttemplaterelation 
 where SPEC2RELATIONID in
 	(Select Id From xm_entityspec2relationspec 
 	  Where relationspecid in
 			(Select Id From xm_relationspec Where code in('SERSPECRESSPECASSIGN2SERSPEC','SERSPEC2SERSPECRESSPECASSIGN')));

Delete From xm_entityspec2relationspec Where relationspecid in(Select Id From xm_relationspec Where code in('SERSPECRESSPECASSIGN2SERSPEC','SERSPEC2SERSPECRESSPECASSIGN'));

Delete From xm_relationspec Where code in('SERSPECRESSPECASSIGN2SERSPEC','SERSPEC2SERSPECRESSPECASSIGN');
Delete From x_versioninstance Where subcategory in('SERSPECRESSPECASSIGN2SERSPEC','SERSPEC2SERSPECRESSPECASSIGN');
Commit;

exec p_insertM2OneRelation('资源服务规格资源规格关系对应的资源服务规格','SERSPECRESSPECASSIGN2SERSPEC','SERSPECRESSPECASSIGN','RESSERVICESPEC','RESSERVICESPECID','ID','B');
Commit;


--by liurr at 20090907
exec p_Insertentityspec('区域路由码映射','INF_AREAROUTEMAPPING','com.gxlu.ngrm.intf.AreaRouteMapping','X_INF_AREAROUTEMAPPING','');
exec p_insertentitydescriptor('INF_AREAROUTEMAPPING','X_INF_AREAROUTEMAPPING');
commit;

exec p_insertM2OneRelation('区域路由码映射对应的营销区域','AREAROUTEMAPPING2MARKETINGAREA','INF_AREAROUTEMAPPING','MARKETINGAREA','MARKETINGAREAID','ID','');
Commit;         

--by wxy 20090909
Exec p_insertentitydescriptor('PROJECTDESIGNRECORD','X_PROJECTDESIGNRECORD');
Commit;

--by liurr 20090909
DELETE FROM Xm_Entityspec2relationspec WHERE RELATIONSPECID = (SELECT ID FROM XM_RELATIONSPEC WHERE CODE ='PORT2CARD');
DELETE FROM XM_RELATIONSPEC WHERE CODE ='PORT2CARD';
Delete From X_VERSIONINSTANCE Where SUBCATEGORY = 'PORT2CARD';
COMMIT;

Exec p_insertM2OneRelation('端口对应的板卡','PORT2CARD','PORT','CARD','CARDID','ID','B');
Commit;

--by liurr at 20090910
DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (select ID from xm_entityspec where code = 'INTFLOG') AND COLUMNNAME IN ('RECEIVECONTENT','RETURNCONTENT');
COMMIT;
EXEC p_insertentitydescriptor('INTFLOG','X_INTF_LOG');
COMMIT;

--BY WXY 20090910
--METADATA
Update XM_ENTITYDESCRIPTOR D Set D.ATTRDISPNAME = '时隙编码' 
 Where D.COLUMNNAME='TIMESLOT' And D.ENTITYSPECID = (Select Id From XM_ENTITYSPEC Where CODE = 'LINKROUTE');
Commit;

Exec p_insertentitydescriptor('LINKROUTE','XB_LINKROUTE');
Commit;
Exec p_insertM2OneRelation('连接路由对应的时隙','LINKROUTE2TIMESLOT','LINKROUTE','TIMESLOT','TIMESLOTID','ID','B');
Commit;

Exec p_insertentitydescriptor('LINK','XB_LINK');
Commit;
Exec p_insertM2OneRelation('连接对应的反向连接','LINK2OPPLINK','LINK','LINK','OPPLINKID','ID','B');
Exec p_insertM2OneRelation('连接对应的顶层连接','LINK2CONTAINLINK','LINK','LINK','CONTAINLINKID','ID','B');
Commit;

--by liurr at 20090911
EXEC p_insertEntitySpec('交换组群号配置','DUMMYNOCONFIG','com.gxlu.ngrm.resService.DummyNoConfig','XB_TASK','XS_DUMMYNOCONFIG');
EXEC p_insertentitydescriptor('DUMMYNOCONFIG','XS_DUMMYNOCONFIG');
COMMIT;

EXEC p_insertentitydescriptor('IVPNNO','X_IVPNNO');
COMMIT;

--by wxy 20090911
Update xm_entityspec Set ctsequence = 'S_XB_MAPLINE' Where ctsequence='S_XB_MAPNODE';
Commit;

--by liurr at 20090914
UPDATE XM_ENTITYDESCRIPTOR SET DICCLASSNAME = 'COMMON' , DICATTRIBUTENAME = 'BOOLEAN' WHERE ENTITYSPECID = (select ID from xm_entityspec where code = 'PHSSIM') 
       AND COLUMNNAME IN ('ISTALKOFBUY','ISBLANKCARD');
COMMIT;

--by liurr at 20090914
EXEC p_insertentitydescriptor('PRODINS','XB_PRODINS');
COMMIT;

--by liurr at 20090914
exec p_Insertentityspec('追单数据','INF_CHASEWORKORDER','com.gxlu.ngrm.intf.ChaseWorkOrder','X_INF_CHASEWORKORDER','');
exec p_insertentitydescriptor('INF_CHASEWORKORDER','X_INF_CHASEWORKORDER');
commit;

--by wxy 20090914
--
exec p_InsertEntitySpec('OBD','OBD','com.gxlu.ngrm.equipment.OBD','XB_MANAGEDELEMENT','XS_OBD');
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'MANAGEDELEMENT')
 Where CODE IN('OBD');
Commit;
 
exec p_insertEntityDescriptor('OBD','XS_OBD');
Commit;

exec p_InsertEntitySpec('OBD端口','OBDPORT','com.gxlu.ngrm.equipment.OBDPort','XB_PORT','XS_OBDPORT');
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'PORT')
 Where CODE IN('OBDPORT');
Commit;
 
exec p_insertEntityDescriptor('OBDPORT','XS_OBDPORT');
Commit;


--by wxy 20090915
EXEC p_insertcomponentdescriptor('PONPORT','XC_PONPORT');
COMMIT;

--METADATA
exec p_InsertEntitySpec('语音口','VOICEPORT','com.gxlu.ngrm.equipment.VoicePort','XB_PORT','XS_VOICEPORT');
exec p_InsertEntitySpec('FE口','FEPORT','com.gxlu.ngrm.equipment.FEPort','XB_PORT','XS_FEPORT');
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'PORT')
 Where CODE IN('VOICEPORT','FEPORT');
Commit;
 
exec p_insertEntityDescriptor('VOICEPORT','XS_VOICEPORT');
exec p_insertEntityDescriptor('FEPORT','XS_FEPORT');
Commit;

Update xm_entityspec d 
   Set d.Name='OLT',d.code = 'OLT',d.satellitetablename='XS_OLT',
   		 d.classname = 'com.gxlu.ngrm.equipment.OLT',d.alias = 'OLT设备'
 Where d.code = 'PON';
Update X_VERSIONINSTANCE Set SUBCATEGORY = 'OLT' Where SUBCATEGORY = 'PON';

--exec p_InsertEntitySpec('方案设计记录表','PROJECTDESIGNRECORD','com.gxlu.ngrm.resService.ProjectDesignRecord','X_PROJECTDESIGNRECORD','');

Delete From XM_ENTITYDESCRIPTOR D Where D.ENTITYSPECID=(Select Id From XM_ENTITYSPEC Where CODE='PROJECTDESIGNRECORD');
exec p_insertEntityDescriptor('PROJECTDESIGNRECORD','X_PROJECTDESIGNRECORD');
Commit;

Delete From xm_entityspec2relationspec d Where d.relationspecid =(Select Id From xm_relationspec Where code='DESIGNRECORD2ENDPOINT');
Delete From xm_relationspec Where code='DESIGNRECORD2ENDPOINT';
Delete From x_versioninstance Where subcategory = 'DESIGNRECORD2ENDPOINT';
Commit;

--exec p_insertM2OneRelation('方案设计记录所属方案','DESIGNRECORD2PROJECTINS','PROJECTDESIGNRECORD','PROJECTINSTANCE','PROJECTINSTANCEID','ID','');
--exec p_insertM2OneRelation('方案设计记录所属资源服务实例','DESIGNRECORD2RESSERVINS','PROJECTDESIGNRECORD','RESSERVINS','RESSERVINSID','ID','');
exec p_insertM2OneRelation('方案设计记录所属上级记录','DESIGNRECORD2PARENT','PROJECTDESIGNRECORD','PROJECTDESIGNRECORD','PARENTID','ID','');
exec p_insertOne2MRelation('方案设计记录对应的下级记录','DESIGNRECORD2CHILD','PROJECTDESIGNRECORD','PROJECTDESIGNRECORD','PARENTID','');
Commit;

--
exec p_InsertEntitySpec('PDH设备','PDH','com.gxlu.ngrm.equipment.Pdh','XB_MANAGEDELEMENT','XS_PDH');
exec p_InsertEntitySpec('SDH设备','SDH','com.gxlu.ngrm.equipment.Sdh','XB_MANAGEDELEMENT','XS_SDH');
exec p_InsertEntitySpec('DWDM设备','DWDM','com.gxlu.ngrm.equipment.Dwdm','XB_MANAGEDELEMENT','XS_DWDM');
exec p_InsertEntitySpec('PTN设备','PTN','com.gxlu.ngrm.equipment.Ptn','XB_MANAGEDELEMENT','XS_PTN');
exec p_InsertEntitySpec('SPDH','SPDH','com.gxlu.ngrm.equipment.Spdh','XB_MANAGEDELEMENT','XS_SPDH');
exec p_InsertEntitySpec('SONET','SONET','com.gxlu.ngrm.equipment.Sonet','XB_MANAGEDELEMENT','XS_SONET');
exec p_InsertEntitySpec('ASON','ASON','com.gxlu.ngrm.equipment.Ason','XB_MANAGEDELEMENT','XS_ASON');
exec p_InsertEntitySpec('MSAP','MSAP','com.gxlu.ngrm.equipment.Msap','XB_MANAGEDELEMENT','XS_MSAP');
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'MANAGEDELEMENT')
 Where CODE IN('PDH','SDH','DWDM','PTN','SPDH','SONET','ASON','MSAP');
Commit;
 
exec p_insertEntityDescriptor('PDH','XS_PDH');
exec p_insertEntityDescriptor('SDH','XS_SDH');
exec p_insertEntityDescriptor('DWDM','XS_DWDM');
exec p_insertEntityDescriptor('PTN','XS_PTN');
exec p_insertEntityDescriptor('SPDH','XS_SPDH');
exec p_insertEntityDescriptor('SONET','XS_SONET');
exec p_insertEntityDescriptor('ASON','XS_ASON');
exec p_insertEntityDescriptor('MSAP','XS_MSAP');
Commit;

--by liurr at 20090915
EXEC p_insertentitydescriptor('MODIFYDETAIL','XB_MODIFYDETAIL');
COMMIT;

exec p_insertM2OneRelation('资源中间表对应的原物理号码辅号','MODIFYDETAIL2OLDCODE','MODIFYDETAIL','CODE','OLDAUXPHYCODEID','ID','B');
exec p_insertM2OneRelation('资源中间表对应的新物理号码辅号','MODIFYDETAIL2NEWCODE','MODIFYDETAIL','CODE','NEWAUXPHYCODEID','ID','B');
COMMIT;

EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
COMMIT;

exec p_insertM2OneRelation('割接明细表对应的原物理号码辅号','MIGRATIONRESULTDETAIL2OLDCODE','MIGRATIONRESULTDETAIL','CODE','OLDAUXPHYCODEID','ID','B');
exec p_insertM2OneRelation('割接明细表对应的新物理号码辅号','MIGRATIONRESULTDETAIL2NEWCODE','MIGRATIONRESULTDETAIL','CODE','NEWAUXPHYCODEID','ID','B');
COMMIT;

EXEC p_insertentitydescriptor('MIGRATIONIMPORT','XB_MIGRATIONIMPORT');
COMMIT;

exec p_insertM2OneRelation('割接导入表对应的原物理号码辅号','MIGRATIONIMPORT2OLDCODE','MIGRATIONIMPORT','CODE','OLDAUXPHYCODEID','ID','B');
exec p_insertM2OneRelation('割接导入表对应的新物理号码辅号','MIGRATIONIMPORT2NEWCODE','MIGRATIONIMPORT','CODE','NEWAUXPHYCODEID','ID','B');
COMMIT;

--BY WXY 20090916
Update Xm_Entitydescriptor d
	 Set d.Columntype = 15, d.Datatype = 15,d.Dicclassname = 'BANDWIDTH', d.Dicattributename = 'LAYERRATE'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'PORT')
	 And d.Columnname = 'LAYERRATE';
Commit;


Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'BANDWIDTH', d.Dicattributename = 'LAYERRATE'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'LAYERRATEMAPRULE')
	 And d.Columnname In ('PARENTRATE', 'CHILDRATE');
Commit;

--by liurr at 20090916
UPDATE xm_entitydescriptor A SET A.Columntype = 15,a.datatype=15,A.DICCLASSNAME = 'PSTNCONFIG',A.DICATTRIBUTENAME = 'NWK'
WHERE entityspecid = (select id from xm_entityspec where code = 'PRODINS') 
       AND A.COLUMNNAME = 'FUNC';
COMMIT;

--by liurr at 20090917
EXEC p_insertentitydescriptor('PSTNCONFIG','XS_PSTNCONFIG');
EXEC p_insertentitydescriptor('ADSLCONFIG','XS_ADSLCONFIG');
COMMIT;

--by liurr at 20090917
EXEC p_insertentitydescriptor('PORT','XB_PORT');
COMMIT;

--by liurr at 20090918
exec p_Insertentityspec('客户经理','CUSTOMERMANAGER','com.gxlu.ngrm.resService.CustomerManager','X_CUSTOMERMANAGER','');
exec p_insertentitydescriptor('CUSTOMERMANAGER','X_CUSTOMERMANAGER');
commit;

exec p_insertM2OneRelation('客户经理对应的上级客户经理','CUSTOMERMANAGER2PARENT','CUSTOMERMANAGER','CUSTOMERMANAGER','PARENTID','ID','');
exec p_insertOne2MRelation('客户经理对应的下级客户经理','CUSTOMERMANAGER2CHILD','CUSTOMERMANAGER','CUSTOMERMANAGER','PARENTID','');
commit;

exec p_Insertentityspec('客户和客户经理关联','MGRCUSTASSIGN','com.gxlu.ngrm.resService.MgrCustAssign','X_MGRCUSTASSIGN','');
exec p_insertentitydescriptor('MGRCUSTASSIGN','X_MGRCUSTASSIGN');
commit;

exec p_insertM2OneRelation('客户和客户经理关联对应的客户经理','MGRCUSTASSIGN2CUSTOMERMANAGER','MGRCUSTASSIGN','CUSTOMERMANAGER','MANAGERID','ID','');
exec p_insertM2OneRelation('客户和客户经理关联对应的客户','MGRCUSTASSIGN2CUSTOMER','MGRCUSTASSIGN','CUSTOMER','CUSTID','ID','');
exec p_insertM2OneRelation('客户和客户经理关联对应的区域','MGRCUSTASSIGN2LOCATION','MGRCUSTASSIGN','MARKETINGAREA','LOCATIONID','ID','');
exec p_insertM2OneRelation('客户和客户经理关联对应的本地网','MGRCUSTASSIGN2MARKETINGAREA','MGRCUSTASSIGN','MARKETINGAREA','AREAID','ID','');
COMMIT;

--by wxy 20090918
Delete From Xm_Entitydescriptor d
 Where d.Entityspecid In
			 (Select Id From Xm_Entityspec Where Code In ('OTRIBCARD', 'OLINECARD', 'ETRIBCARD', 'ELINECARD'));
Delete From Xm_Entityspec Where Code In ('OTRIBCARD', 'OLINECARD', 'ETRIBCARD', 'ELINECARD');
Delete From x_Versioninstance Where Subcategory In ('OTRIBCARD', 'OLINECARD', 'ETRIBCARD', 'ELINECARD');
Commit;

Delete From x_Dictionary d
 Where d.Attributeid = 'CARDSPEC'
	 And d.Value In (31,32,33, 34);
Commit;

--METADATA
exec p_InsertEntitySpec('OLTE板','OLTECARD','com.gxlu.ngrm.equipment.OlteCard','XB_CARD','XS_OLTECARD');
exec p_InsertEntitySpec('OE板','OECARD','com.gxlu.ngrm.equipment.OECard','XB_CARD','XS_OECARD');
exec p_InsertEntitySpec('SDH板','SDHCARD','com.gxlu.ngrm.equipment.SdhCard','XB_CARD','XS_SDHCARD');
exec p_InsertEntitySpec('WDM板','WDMCARD','com.gxlu.ngrm.equipment.WdmCard','XB_CARD','XS_WDMCARD');
exec p_InsertEntitySpec('微波插盘','MICROWAVECARD','com.gxlu.ngrm.equipment.MicroWaveCard','XB_CARD','XS_MICROWAVECARD');
exec p_InsertEntitySpec('MSTP板','MSTPCARD','com.gxlu.ngrm.equipment.MstpCard','XB_CARD','XS_MSTPCARD');
exec p_InsertEntitySpec('支路板','TRIBCARD','com.gxlu.ngrm.equipment.TribCard','XB_CARD','XS_TRIBCARD');
exec p_InsertEntitySpec('线路板','LINECARD','com.gxlu.ngrm.equipment.LineCard','XB_CARD','XS_LINECARD');
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'CARD')
 Where CODE IN('OLTECARD','OECARD','SDHCARD','WDMCARD','MICROWAVECARD','MSTPCARD','TRIBCARD','LINECARD');
Commit;
 
exec p_insertEntityDescriptor('OLTECARD','XS_OLTECARD');
exec p_insertEntityDescriptor('OECARD','XS_OECARD');
exec p_insertEntityDescriptor('SDHCARD','XS_SDHCARD');
exec p_insertEntityDescriptor('WDMCARD','XS_WDMCARD');
exec p_insertEntityDescriptor('MICROWAVECARD','XS_MICROWAVECARD');
exec p_insertEntityDescriptor('MSTPCARD','XS_MSTPCARD');
exec p_insertEntityDescriptor('TRIBCARD','XS_TRIBCARD');
exec p_insertEntityDescriptor('LINECARD','XS_LINECARD');
Commit;

Exec p_insertentitydescriptor('BANDWIDTH','XB_BANDWIDTH');
Commit;

--by liurr at 20090921
EXEC p_insertentitydescriptor('CUSTOMER','X_CUSTOMER');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR A SET A.COLUMNTYPE = 4,A.DATATYPE = 4,A.DATALENGTH = 255,A.DICCLASSNAME = NULL,A.DICATTRIBUTENAME = NULL
       WHERE A.ENTITYSPECID = (select ID from xm_entityspec where code = 'CUSTOMER')
       AND A.COLUMNNAME ='GRADE';
COMMIT;

--by liurr at 20090921
EXEC p_insertentitydescriptor('IVPNNO','X_IVPNNO');
COMMIT;

--BY WXY 20090921
Exec p_insertentitydescriptor('PROJECTDESIGNRECORD','X_PROJECTDESIGNRECORD');
Commit;

Delete From XM_ENTITYDESCRIPTOR D 
 Where D.ENTITYSPECID=(Select Id From XM_ENTITYSPEC Where CODE='PROJECTDESIGNRECORD') 
   And D.ColumNNAME='PARENTID';
Commit;

--by liurr at 20090921
exec p_Insertentityspec('拆业务数据表','INF_REMOVEPRODINSRESSERINS','com.gxlu.ngrm.intf.RemoveProdinsResserins','X_INF_REMOVEPRODINSRESSERINS','');
exec p_insertentitydescriptor('INF_REMOVEPRODINSRESSERINS','X_INF_REMOVEPRODINSRESSERINS');
commit;

exec p_insertM2OneRelation('拆业务数据表对应的产品实例','REMOVEPRODINSRESSERINS2PRODINS','INF_REMOVEPRODINSRESSERINS','PRODINS','PRODINSID','ID','');
COMMIT;


--BY WXY 20090922
Exec p_insertentitydescriptor('PROJECTINSTANCE','X_PROJECTINSTANCE');
Commit;

--METADATA
exec p_InsertEntitySpec('PDH端口','PDHPORT','com.gxlu.ngrm.equipment.PDHPort','XB_PORT','XS_PDHPORT');
exec p_InsertEntitySpec('SPDH端口','SPDHPORT','com.gxlu.ngrm.equipment.SPDHPort','XB_PORT','XS_SPDHPORT');
exec p_InsertEntitySpec('WDM端口','WDMPORT','com.gxlu.ngrm.equipment.WDMPort','XB_PORT','XS_WDMPORT');
exec p_InsertEntitySpec('MSAP端口','MSAPPORT','com.gxlu.ngrm.equipment.MSAPPort','XB_PORT','XS_MSAPPORT');
Commit;

exec p_insertEntityDescriptor('PDHPORT','XS_PDHPORT');
exec p_insertEntityDescriptor('SPDHPORT','XS_SPDHPORT');
exec p_insertEntityDescriptor('WDMPORT','XS_WDMPORT');
exec p_insertEntityDescriptor('MSAPPORT','XS_MSAPPORT');
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'PORT')
 Where CODE IN('PDHPORT','SPDHPORT','WDMPORT','MSAPPORT');
Commit;
 


Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'BANDWIDTH', d.Dicattributename = 'LAYERRATE',d.columntype=10,d.datatype=10,d.datalength=5
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'LAYERRATEMAPSUBRULE')
	 And d.Columnname In ('LAYERRATE');
Commit;

--by liurr at 20090922
update xm_relationspec a set a.supplierentityspecid = (select id from xm_entityspec where code = 'MARKETINGAREA') where a.code ='IVPNNO2LOCATION';
commit; 

--by wxy 20090923
Update XM_RELATIONSPEC D Set D.OWNERATTR='ENTITYID' Where D.OWNERATTR='ID' And d.relationinstancetablename Like 'XS%';
Commit;

EXEC p_insertM2OneRelation('拓扑图连线连接对应连接','MAPLINELINK2LINK','MAPLINELINK','LINK','LINKID','ENTITYID','S');
commit;

Exec p_insertentitydescriptor('BANDWIDTH','XB_BANDWIDTH');
Commit;

--METADATA
Update xm_entityspec Set Name ='通道' Where Name ='通路';
Commit;

exec p_InsertEntitySpec('高阶通道','HCHANNEL','com.gxlu.ngrm.network.HChannel','XB_LINK','XS_HCHANNEL');
exec p_InsertEntitySpec('低阶通道','LCHANNEL','com.gxlu.ngrm.network.LChannel','XB_LINK','XS_LCHANNEL');
exec p_InsertEntitySpec('SDH路由段','SDHTRAIL','com.gxlu.ngrm.network.SDHTrail','XB_LINK','XS_SDHTRAIL');
exec p_InsertEntitySpec('SDH段','SDHSECTION','com.gxlu.ngrm.network.SDHSection','XB_LINK','XS_SDHSECTION');
exec p_InsertEntitySpec('WDM段','WDMSECTION','com.gxlu.ngrm.network.WDMSection','XB_LINK','XS_WDMSECTION');
exec p_InsertEntitySpec('SDH槽路','SDHCIRCUIT','com.gxlu.ngrm.network.SDHCircuit','XB_LINK','XS_SDHCIRCUIT');
exec p_InsertEntitySpec('光通道','OCHTRAIL','com.gxlu.ngrm.network.OCHTrail','XB_LINK','XS_OCHTRAIL');
exec p_InsertEntitySpec('配线路由','LINEROUTE','com.gxlu.ngrm.network.LineRoute','XB_LINK','XS_LINEROUTE');
Commit;


exec p_insertEntityDescriptor('HCHANNEL','XS_HCHANNEL');
exec p_insertEntityDescriptor('LCHANNEL','XS_LCHANNEL');
exec p_insertEntityDescriptor('SDHTRAIL','XS_SDHTRAIL');
exec p_insertEntityDescriptor('SDHSECTION','XS_SDHSECTION');
exec p_insertEntityDescriptor('WDMSECTION','XS_WDMSECTION');
exec p_insertEntityDescriptor('SDHCIRCUIT','XS_SDHCIRCUIT');
exec p_insertEntityDescriptor('OCHTRAIL','XS_OCHTRAIL');
exec p_insertEntityDescriptor('LINEROUTE','XS_LINEROUTE');
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'TRAIL')
 Where CODE IN('SDHTRAIL','OCHTRAIL','HCHANNEL');
Commit;
Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'SECTION')
 Where CODE IN('SDHSECTION','WDMSECTION');
Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'CIRCUIT')
 Where CODE IN('SDHCIRCUIT');
Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'LINK')
 Where CODE IN('LINEROUTE');
Commit;
 
 --by liurr at 20090923
Delete From XM_ENTITYSPEC2RELATIONSPEC Where RELATIONSPECID In(Select Id From XM_RELATIONSPEC Where CODE IN ('AREAROUTEMAPPING2MARKETINGAREA','REMOVEPRODINSRESSERINS2PRODINS'));
Delete From XM_RELATIONSPEC Where CODE IN ('AREAROUTEMAPPING2MARKETINGAREA','REMOVEPRODINSRESSERINS2PRODINS');
Delete From X_VERSIONINSTANCE Where SUBCATEGORY IN ('AREAROUTEMAPPING2MARKETINGAREA','REMOVEPRODINSRESSERINS2PRODINS');
DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID IN (SELECT ID FROM XM_ENTITYSPEC WHERE CODE IN ('INF_AREAROUTEMAPPING','INF_CHASEWORKORDER','INF_REMOVEPRODINSRESSERINS'));
DELETE FROM XM_ENTITYSPEC WHERE CODE IN ('INF_AREAROUTEMAPPING','INF_CHASEWORKORDER','INF_REMOVEPRODINSRESSERINS');
Commit;

exec p_Insertentityspec('区域路由码映射','INTF_AREAROUTEMAPPING','com.gxlu.ngrm.intf.AreaRouteMapping','X_INTF_AREAROUTEMAPPING','');
exec p_insertentitydescriptor('INTF_AREAROUTEMAPPING','X_INTF_AREAROUTEMAPPING');
commit;

exec p_insertM2OneRelation('区域路由码映射对应的营销区域','AREAROUTEMAPPING2MARKETINGAREA','INTF_AREAROUTEMAPPING','MARKETINGAREA','MARKETINGAREAID','ID','');
Commit; 

exec p_Insertentityspec('追单数据','INTF_CHASEWORKORDER','com.gxlu.ngrm.intf.ChaseWorkOrder','X_INTF_CHASEWORKORDER','');
exec p_insertentitydescriptor('INTF_CHASEWORKORDER','X_INTF_CHASEWORKORDER');
commit;

exec p_Insertentityspec('拆业务数据表','INTF_REMOVEPRODINSRESSERINS','com.gxlu.ngrm.intf.RemoveProdinsResserins','X_INTF_REMOVEPRODINSRESSERINS','');
exec p_insertentitydescriptor('INTF_REMOVEPRODINSRESSERINS','X_INTF_REMOVEPRODINSRESSERINS');
commit;

UPDATE xm_entityspec A SET A.CTSEQUENCE = 'S_X_INTFREMOVEPRODINSRESSERINS' WHERE CODE = 'INTF_REMOVEPRODINSRESSERINS' ;
COMMIT;

exec p_insertM2OneRelation('拆业务数据表对应的产品实例','REMOVEPRODINSRESSERINS2PRODINS','INTF_REMOVEPRODINSRESSERINS','PRODINS','PRODINSID','ID','');
exec p_insertM2OneRelation('拆业务数据表对应的资源服务实例','REMOVEPRODINSRESSERINS2RESSERVINS','INTF_REMOVEPRODINSRESSERINS','RESSERVINS','RESSERVINSID','ID','');
COMMIT;

--by wxy 20090924
Exec p_insertentitydescriptor('LINKTYPE','XB_LINKTYPE');
Commit;

Delete From Xm_Entitydescriptor d
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'LINKTYPE')
	 And d.Columnname In('ENDPOINTINICATOR','CIRCUITBEHAVIOR');
Commit;

--by liurr at 20090924
DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'PSTNCODESEGMENT') AND COLUMNNAME = 'ID';
COMMIT;
DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'PHSCODESEGMENT') AND COLUMNNAME = 'ID';
COMMIT;

--by liurr at 20090925
DELETE FROM XM_COMPONENTDESCRIPTOR WHERE COMPONENTSPECID = (SELECT ID FROM XM_COMPONENTSPEC WHERE CODE = 'PSTNPORT') AND COLUMNNAME = 'PORTROLE';
COMMIT;

EXEC p_insertentitydescriptor('PORT','XB_PORT');
COMMIT;

UPDATE xm_entitydescriptor A SET A.Columntype = 15,a.datatype=15,A.DICCLASSNAME = 'PORT',A.DICATTRIBUTENAME = 'PORTROLE'
WHERE entityspecid = (select id from xm_entityspec where code = 'PORT') 
       AND A.COLUMNNAME = 'PORTROLE';
COMMIT;

--by wxy 20090925
--METADATA
Update XM_ENTITYSPEC Set Name = '局向光路' Where Name = '光缆连接';
Commit;

exec p_InsertEntitySpec('PDH通道','PDHCHANNEL','com.gxlu.ngrm.network.PDHChannel','XB_LINK','XS_PDHCHANNEL');
exec p_InsertEntitySpec('PDH路由段','PDHTRAIL','com.gxlu.ngrm.network.PDHTrail','XB_LINK','XS_PDHTRAIL');
Commit;

exec p_insertEntityDescriptor('PDHCHANNEL','XS_PDHCHANNEL');
exec p_insertEntityDescriptor('PDHTRAIL','XS_PDHTRAIL');
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'TRAIL')
 Where CODE IN('PDHCHANNEL','PDHTRAIL');
Commit;

--
/*
Delete From Xm_Entityspec2relationspec d
 Where d.Relationspecid = (Select Id From Xm_Relationspec Where Code = 'RESSERVINSASSIGNC2P');

Delete From Xm_Relationspec Where Code = 'RESSERVINSASSIGNC2P';
Delete From x_versioninstance Where subcategory = 'RESSERVINSASSIGNC2P';
*/

exec p_insertM2NRelation('资源服务实例从属关系子对父','RESSERVINSASSIGNC2P','XR_RESSERVINSASSIGN','RESSERVINS','RESSERVINS','CHILDID','PARENTID');
commit;

--by liurr at 20090925
UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'CARD',A.DICATTRIBUTENAME = 'MSISDNFLAG'
       WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'PHSSIM')
             AND A.COLUMNNAME = 'MSISDNFLAG';
UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'CARD',A.DICATTRIBUTENAME = 'PHYCONNECTSTATUS'
       WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'PHSSIM')
             AND COLUMNNAME = 'PHYCONNECTSTATUS';
UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'CARD',A.DICATTRIBUTENAME = 'BROCHUREFLAG'
       WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'PHSSIM')
             AND COLUMNNAME = 'BROCHUREFLAG';
UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'CARD',A.DICATTRIBUTENAME = 'USERPASSFLAG'
       WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'PHSSIM')
             AND COLUMNNAME = 'USERPASSFLAG';
UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'CARD',A.DICATTRIBUTENAME = 'STATUS'
       WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'PHSSIM')
             AND COLUMNNAME = 'STATUS';
UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'CARD',A.DICATTRIBUTENAME = 'TROUBLEREASON'
       WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'PHSSIM')
             AND COLUMNNAME = 'TROUBLEREASON';
UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'COMMON',A.DICATTRIBUTENAME = 'BOOLEAN'
       WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'PHSSIM')
             AND COLUMNNAME = 'ISBLANKCARD';
UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'COMMON',A.DICATTRIBUTENAME = 'BOOLEAN'
       WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'PHSSIM')
             AND COLUMNNAME = 'ISTALKOFBUY';
COMMIT;

--by liurr at 20090927
EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
COMMIT;

--by liurr at 20090927
EXEC p_insertentitydescriptor('PRODINS','XB_PRODINS');
COMMIT;

--by liurr at 20090927
EXEC p_insertentitydescriptor('MANAGEDELEMENT','XB_MANAGEDELEMENT');
COMMIT;

EXEC p_insertcomponentdescriptor('LANPORT','XC_LANPORT');
EXEC p_insertcomponentdescriptor('PSTNPORT','XC_PSTNPORT');
EXEC p_insertcomponentdescriptor('XDSLPORT','XC_XDSLPORT');
COMMIT;

--by wxy 20090927
--METADATA
exec p_InsertEntitySpec('SPDH段','SPDHSECTION','com.gxlu.ngrm.network.SpdhSection','XB_LINK','XS_SPDHSECTION');
exec p_InsertEntitySpec('SPDH路由段','SPDHTRAIL','com.gxlu.ngrm.network.SpdhTrail','XB_LINK','XS_SPDHTRAIL');
exec p_InsertEntitySpec('SPDH槽路','SPDHCIRCUIT','com.gxlu.ngrm.network.SpdhCircuit','XB_LINK','XS_SPDHCIRCUIT');
Commit;

exec p_insertEntityDescriptor('SPDHSECTION','XS_SPDHSECTION');
exec p_insertEntityDescriptor('SPDHTRAIL','XS_SPDHTRAIL');
exec p_insertEntityDescriptor('SPDHCIRCUIT','XS_SPDHCIRCUIT');
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'SECTION')
 Where CODE ='SPDHSECTION';
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'TRAIL')
 Where CODE ='SPDHTRAIL';
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'CIRCUIT')
 Where CODE ='SPDHCIRCUIT';
Commit;

--by wxy 20090928
Exec p_insertentitydescriptor('PROJECTINSNODE','XS_PROJECTINSNODE');
Commit;

--by liurr adding
Exec p_insertentityspec('策略条件','POLICYCONDITION','com.gxlu.ngrm.resService.PolicyCondition','X_POLICYCONDITION','');
Exec p_insertentityspec('策略动作','POLICYACTION','com.gxlu.ngrm.resService.PolicyAction','X_POLICYACTION','');

Exec p_insertentitydescriptor('POLICYCONDITION','X_POLICYCONDITION');
Exec p_insertentitydescriptor('POLICYACTION','X_POLICYACTION');
Commit;


--by liurr at 20090930
EXEC p_insertentitydescriptor('MIGRATIONRANGE','XB_MIGRATIONRANGE');
COMMIT;

EXEC p_insertentitydescriptor('PRODINS','XB_PRODINS');
COMMIT;

--by liurr at 20090930
EXEC p_insertentitydescriptor('TASKRESINSASSOC','XB_TASKRESINSASSOC');
COMMIT;
EXEC p_insertentitydescriptor('SERVICEORDER','X_SERVICEORDER');
COMMIT;

--by liurr at 20091006
exec p_Insertentityspec('配置结果历史表','CONFIGRESULTHIS','com.gxlu.ngrm.resService.ConfigResultHis','XB_CONFIGRESULTHIS','');
exec p_insertentitydescriptor('CONFIGRESULTHIS','XB_CONFIGRESULTHIS');
commit;

--by liurr at 20091008
Update XM_RELATIONSPEC Set Relationinstancetablename='X_LOGICALAREAMAPPING' Where code = 'LOGICALAREAMAPPING2LOCATION' and Relationinstancetablename='XB_LOGICALAREAMAPPING';
commit;

--by wxy 20091009
--metadata
exec p_Insertentitydescriptor('SUBNETWORK','XB_SUBNETWORK');
exec p_Insertentitydescriptor('LINK','XB_LINK');
Commit;

Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'SUBNETWORK', d.Dicattributename = 'SUBNETWORKSPEC'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'SUBNETWORK')
	 And d.Columnname = 'METACATEGORY';

Update Xm_Entitydescriptor d
	 Set d.Columntype = 15, d.Datatype = 15, d.Dicclassname = 'BANDWIDTH', d.Dicattributename = 'LAYERRATE'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'SUBNETWORK')
	 And d.Columnname = 'LAYERRATE';
Commit;

--
exec p_Insertentityspec('传输系统','TRANSPORTSYSTEM','com.gxlu.ngrm.network.TransportSystem','XB_SUBNETWORK','XS_TRANSPORTSYSTEM');
exec p_Insertentitydescriptor('TRANSPORTSYSTEM','XS_TRANSPORTSYSTEM');
COMMIT;

--
exec p_Insertentityspec('SDH子网','SDHNETWORK','com.gxlu.ngrm.network.SDHNetwork','XB_SUBNETWORK','XS_SDHNETWORK');
exec p_Insertentitydescriptor('SDHNETWORK','XS_SDHNETWORK');
COMMIT;

--
exec p_Insertentityspec('ATM子网','ATMNETWORK','com.gxlu.ngrm.network.ATMNetwork','XB_SUBNETWORK','XS_ATMNETWORK');
exec p_Insertentitydescriptor('ATMNETWORK','XS_ATMNETWORK');
COMMIT;

--
exec p_Insertentityspec('主干电缆','MAINECABLE','com.gxlu.ngrm.network.MainECable','XB_SUBNETWORK','XS_MAINECABLE');
exec p_Insertentitydescriptor('MAINECABLE','XS_MAINECABLE');
COMMIT;

--
exec p_Insertentityspec('配线电缆','LAYOUTECABLE','com.gxlu.ngrm.network.LayoutECable','XB_SUBNETWORK','XS_LAYOUTECABLE');
exec p_Insertentitydescriptor('LAYOUTECABLE','XS_LAYOUTECABLE');
COMMIT;

--
exec p_Insertentityspec('中继电缆','RELAYECABLE','com.gxlu.ngrm.network.RelayECable','XB_SUBNETWORK','XS_RELAYECABLE');
exec p_Insertentitydescriptor('RELAYECABLE','XS_RELAYECABLE');
COMMIT;

update xm_entityspec set parentid=(select id from xm_entityspec where code='SUBNETWORK') where code in('TRANSPORTSYSTEM','SDHNETWORK','ATMNETWORK','MAINECABLE','LAYOUTECABLE','RELAYECABLE');
COMMIT;


exec p_Insertentitydescriptor('LAYERRATEMAPRULE','XB_LAYERRATEMAPRULE');

Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'PHYSICALRESOURCE'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'LAYERRATEMAPRULE')
	 And d.Columnname = 'ISPARTIALPROTECT';
commit;

exec p_insertM2OneRelation('子网对应的父亲','SUBNETWORK2PARENT','SUBNETWORK','SUBNETWORK','PARENTID','ID','B');
exec p_insertOne2MRelation('子网对应的儿子','SUBNETWORK2CHILD','SUBNETWORK','SUBNETWORK','PARENTID','B');

exec p_insertM2OneRelation('子网对应的厂商','SUBNETWORK2VENDOR','SUBNETWORK','VENDOR','VENDORID','ID','B');
exec p_insertM2OneRelation('子网所属维护管理区','SUBNETWORK2MANAGEMENTAREA','SUBNETWORK','MANAGEMENTAREA','LOCATIONID','ID','B');
Commit;

exec p_insertM2OneRelation('连接所属的子网','LINK2SUBNETWORK','LINK','SUBNETWORK','SUBNETWORKID','ID','B');
Commit;

--by liurr at 20091009
EXEC p_insertcomponentdescriptor('PSTNPORT','XC_PSTNPORT');
COMMIT;

--by liurr at 20091009
EXEC p_insertentitydescriptor('MANAGEDELEMENT','XB_MANAGEDELEMENT');
COMMIT;

--by liurr at 20091010
EXEC p_insertentitydescriptor('EXCHANGE','XS_EXCHANGE');
COMMIT;

Delete From xm_entityspec2componentspec d Where d.entityspecid In (Select Id From xm_entityspec e Where e.code In ('REMOTEMODULE'));
Delete From xm_entitydescriptor d Where d.entityspecid In (Select Id From xm_entityspec e Where e.code In ('REMOTEMODULE'));

Delete From xm_entityspec Where Id In (Select Id From xm_entityspec e Where e.code In ('REMOTEMODULE'));
Delete From x_versioninstance Where subcategory In ('REMOTEMODULE');
commit;

--by liurr at 20091010
DELETE FROM XM_COMPONENTDESCRIPTOR WHERE COMPONENTSPECID = (SELECT ID FROM XM_COMPONENTSPEC WHERE CODE = 'XDSLPORT') AND COLUMNNAME IN ('VPI','VCI');
COMMIT;

--by liurr at 20091011
UPDATE XM_ENTITYDESCRIPTOR D SET D.DATALENGTH = 11 WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'BANDWIDTH') AND COLUMNNAME = 'VALUENUM';
COMMIT;

UPDATE xm_componentdescriptor B SET B.DICCLASSNAME = 'BANDWIDTH',B.DICATTRIBUTENAME = 'LAYERRATE' WHERE B.COMPONENTSPECID = (select id from xm_componentspec where code = 'XDSLPORT')
       AND B.COLUMNNAME IN ('UPRATE','DOWNRATE');
COMMIT;

--by liurr at 20091012
EXEC p_insertentitydescriptor('TASK','XB_TASK');
COMMIT;

--by liurr at 20091012
exec p_insertM2NRelation('物理号码与逻辑号码关联','PHYLOGICALCODERELATION','XR_PHYLOGICALCODERELATION','PSTNPHYSICALCODE','PSTNLOGICALCODE','PHYSICALCODEID','LOGICALCODEID');
commit;

--by wxy 20091012
Exec p_insertentitydescriptor('PROJECTDESIGNNODE','XS_PROJECTDESIGNNODE');
Commit;

Update Xm_Entitydescriptor d
	 Set d.Columnname = 'VENDORID', d.Attrname = 'VENDORID'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'SUBNETWORK')
	 And d.Columnname = 'VERDORID';
Commit;


exec p_insertM2OneRelation('字典对应的字典种类','DICTIONARY2CATEGORY','DICTIONARY','DICTIONARYCATEGORY','DICTIONARYCATEGORYID','ID','');
Commit;

--by liurr at 20091012
delete from xm_entitydescriptor where entityspecid = (select id from xm_entityspec where code= 'CUSTOMER') and columnname = 'LINKMOBILE';
exec p_insertentitydescriptor('CUSTOMER','X_CUSTOMER');
COMMIT;

--by wxy 20091013
Exec p_insertentitydescriptor('MANAGEDELEMENT','XB_MANAGEDELEMENT');
Commit;

exec p_insertM2OneRelation('网元所属的子网','ME2SUBNETWORK','MANAGEDELEMENT','SUBNETWORK','SUBNETWORKID','ID','B');
Commit;

--by liurr at 20091014
EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '原XDSL数据横列' WHERE 
       entityspecid = (select id from xm_entityspec where code = 'MIGRATIONRESULTDETAIL') and a.columnname = 'OLDXDSLROWCON';

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '原XDSL数据横列ID' WHERE 
       entityspecid = (select id from xm_entityspec where code = 'MIGRATIONRESULTDETAIL') and a.columnname = 'OLDXDSLROWCONID';
commit;

exec p_insertM2OneRelation('割接明细表对应的原XDSL语音横列','MIGRATIONRESULTDETAIL2OLDECONNECTOR','MIGRATIONRESULTDETAIL','ECONNECTOR','OLDXDSLROWCON2ID','ID','B');
COMMIT;

--by liurr at 20091014
DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (select id from xm_entityspec where code = 'CUSTOMERMANAGER') AND COLUMNNAME = 'STAFFAREACODE';
COMMIT;
EXEC p_insertentitydescriptor('CUSTOMERMANAGER','X_CUSTOMERMANAGER');
COMMIT;
exec p_insertM2OneRelation('客户经理对应的员工区域','CUSTOMERMANAGER2MARKETINGAREA','CUSTOMERMANAGER','MARKETINGAREA','STAFFAREAID','ID','');
COMMIT;


--by liurr at 20091015
EXEC p_insertentitydescriptor('LOCATION','XB_LOCATION');
COMMIT;

--by liurr at 20091015
EXEC p_insertentitydescriptor('CONFIGRESULTHIS','XB_CONFIGRESULTHIS');
COMMIT;

exec p_insertM2OneRelation('配置结果历史表对应的服务定单','CONFIGRESULTHIS2SERVICEORDER','CONFIGRESULTHIS','SERVICEORDER','SERVICEORDERID','ID','B');
exec p_insertM2OneRelation('配置结果历史表对应的任务','CONFIGRESULTHIS2TASK','CONFIGRESULTHIS','TASK','TASKID','ID','B');
COMMIT;

--by liurr at 20091016
exec p_insertM2OneRelation('客户所属父客户','CUSTOMER2CUSTOMER','CUSTOMER','CUSTOMER','PARENTID','ID','');
COMMIT;

--by liurr at 20091016
UPDATE XM_ENTITYDESCRIPTOR A SET A.COLUMNNAME = 'ACTIVEMODE',A.ATTRNAME = 'ACTIVEMODE',A.ATTRDISPNAME = '活动状态'
       WHERE A.ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'CHANNEL') AND A.COLUMNNAME = 'STATUS';
COMMIT;

--by liurr at 20091017
UPDATE XM_ENTITYSPEC H SET H.NAME = 'ONU',H.ALIAS = 'ONU' WHERE CODE = 'ONU';
COMMIT;

--by liurr at 20091017
exec p_insertM2OneRelation('字典对应的字典种类','DICTIONARY2CATEGORY','DICTIONARY','DICTIONARYCATEGORY','DICTIONARYCATEGORYID','ID','');
COMMIT;

exec p_insertM2OneRelation('字典种类对应的父字典种类','DICTIONARYCATEGORY2PARENT','DICTIONARYCATEGORY','DICTIONARYCATEGORY','PARENTID','ID','');
exec p_insertOne2MRelation('字典种类对应的子字典种类','DICTIONARYCATEGORY2CHILD','DICTIONARYCATEGORY','DICTIONARYCATEGORY','PARENTID','');
commit;

--by liurr at 20091019
UPDATE XM_ENTITYDESCRIPTOR A SET A.DATALENGTH = 1000 WHERE ENTITYSPECID = (select ID from xm_entityspec where code = 'CONFIGRESULTHIS') 
       AND A.COLUMNNAME = 'OLDRESSTRING';
COMMIT;

--by liurr at 20091020
EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '新XDSL数据横列' WHERE 
       entityspecid = (select id from xm_entityspec where code = 'MIGRATIONRESULTDETAIL') and a.columnname = 'NEWXDSLROWCON';

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '新XDSL数据横列ID' WHERE 
       entityspecid = (select id from xm_entityspec where code = 'MIGRATIONRESULTDETAIL') and a.columnname = 'NEWXDSLROWCONID';
commit;

exec p_insertM2OneRelation('割接明细表对应的新XDSL语音横列','MIGRATIONRESULTDETAIL2NEWECONNECTOR','MIGRATIONRESULTDETAIL','ECONNECTOR','NEWXDSLROWCON2ID','ID','B');
COMMIT;

--by liurr at 20091020
DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONBATCH') AND COLUMNNAME = 'CONFLICTMECHANISM';
COMMIT;

DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONACTIONDETAIL') 
       AND COLUMNNAME IN ('OLDRESSERVSTATUS','OLDRESMIGSTATUS','NEWRESCATEGORY','NEWRESSERVSTATUS','NEWRESMIGSTATUS');
COMMIT;

EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
COMMIT;

DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONRESULTDETAIL') 
       AND COLUMNNAME IN ('CENTREXGROUPCODE','ACCESSMODE','CENTREXSUBCODE','STATUS','OPERINFO','OLDLOGICALCODE','OLDLOGICALCODEID','OLDROWCONID',
'OLDXDSLPORTCATEGORY','OLDLANFACILITYCATEGORY','OLDLANFACILITYCODE','OLDLANCON','OLDONU','OLDONUID','OLDONUPORTCATEGORY',
'OLDONUPORT','OLDONUPORTID','OLDONUFACILITYCATEGORY','OLDONUFACILITYCODE','OLDONUCON','OLDFUNC','NEWLOGICALCODE',
'NEWLOGICALCODEID','NEWROWCONID','NEWXDSLPORTCATEGORY','NEWLANFACILITYCATEGORY','NEWLANFACILITYCODE','NEWLANCON',
'NEWONU','NEWONUID','NEWONUPORTCATEGORY','NEWONUPORT','NEWONUPORTID','NEWONUFACILITYCATEGORY','NEWONUFACILITYCODE','NEWONUCON','NEWFUNC'
       );
COMMIT;

EXEC p_insertentitydescriptor('MIGRATIONIMPORT','XB_MIGRATIONIMPORT');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '原XDSL数据横列' WHERE 
       entityspecid = (select id from xm_entityspec where code = 'MIGRATIONIMPORT') and a.columnname = 'OLDXDSLROWCON';

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '原XDSL数据横列ID' WHERE 
       entityspecid = (select id from xm_entityspec where code = 'MIGRATIONIMPORT') and a.columnname = 'OLDXDSLROWCONID';
commit;

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '新XDSL数据横列' WHERE 
       entityspecid = (select id from xm_entityspec where code = 'MIGRATIONIMPORT') and a.columnname = 'NEWXDSLROWCON';

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '新XDSL数据横列ID' WHERE 
       entityspecid = (select id from xm_entityspec where code = 'MIGRATIONIMPORT') and a.columnname = 'NEWXDSLROWCONID';
commit;


DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONIMPORT') 
       AND COLUMNNAME IN ('CENTREXGROUPCODE','ACCESSMODE','CENTREXSUBCODE','STATUS','OPERINFO','OLDLOGICALCODE','OLDLOGICALCODEID','OLDROWCONID',
'OLDXDSLPORTCATEGORY','OLDLANFACILITYCATEGORY','OLDLANFACILITYCODE','OLDLANCON','OLDONU','OLDONUID','OLDONUPORTCATEGORY',
'OLDONUPORT','OLDONUPORTID','OLDONUFACILITYCATEGORY','OLDONUFACILITYCODE','OLDONUCON','OLDFUNC','NEWLOGICALCODE',
'NEWLOGICALCODEID','NEWROWCONID','NEWXDSLPORTCATEGORY','NEWLANFACILITYCATEGORY','NEWLANFACILITYCODE','NEWLANCON',
'NEWONU','NEWONUID','NEWONUPORTCATEGORY','NEWONUPORT','NEWONUPORTID','NEWONUFACILITYCATEGORY','NEWONUFACILITYCODE','NEWONUCON','NEWFUNC'
       );
COMMIT;

exec p_insertM2OneRelation('割接导入表对应的原XDSL语音横列','MIGRATIONIMPORT2OLDECONNECTOR','MIGRATIONIMPORT','ECONNECTOR','OLDXDSLROWCON2ID','ID','B');
COMMIT;

exec p_insertM2OneRelation('割接导入表对应的新XDSL语音横列','MIGRATIONIMPORT2NEWECONNECTOR','MIGRATIONIMPORT','ECONNECTOR','NEWXDSLROWCON2ID','ID','B');
COMMIT;

--7. 建立割接封网
exec p_Insertentityspec('割接封网','MIGRATIONBLOCK','com.gxlu.ngrm.resService.MigrationBlock','XB_MIGRATIONBLOCK','');
exec p_insertentitydescriptor('MIGRATIONBLOCK','XB_MIGRATIONBLOCK');
commit;
exec p_insertM2OneRelation('割接封网对应的割接批次','MIGRATIONBLOCK2MIGRATIONBATCH','MIGRATIONBLOCK','MIGRATIONBATCH','OLDXDSLROWCON2ID','ID','B');
COMMIT;

--by liurr at 20091021
EXEC p_insertentityspec('800号码','SPECIALCODE','com.gxlu.ngrm.code.SpecialCode','XB_CODE','XS_SPECIALCODE');
COMMIT;
update xm_entityspec set parentid=(select id from xm_entityspec where code='CODE') WHERE CODE='SPECIALCODE';
COMMIT;

--by liurr at 20091021
EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
COMMIT;

exec p_insertM2OneRelation('割接明细表对应的线路关联产品实例','MIGRATIONRESULTDETAIL2LINERELAPRODINS','MIGRATIONRESULTDETAIL','PRODINS','LINERELAPRODINSID','ID','B');
exec p_insertM2OneRelation('割接明细表对应的端口关联产品实例','MIGRATIONRESULTDETAIL2PORTRELAPRODINS','MIGRATIONRESULTDETAIL','PRODINS','PORTRELAPRODINSID','ID','B');
exec p_insertM2OneRelation('割接明细表对应的线路资源服务实例','MIGRATIONRESULTDETAIL2LINESERVINS','MIGRATIONRESULTDETAIL','RESSERVINS','LINESERVINSID','ID','B');
COMMIT;

EXEC p_insertentitydescriptor('MIGRATIONIMPORT','XB_MIGRATIONIMPORT');
COMMIT;

exec p_insertM2OneRelation('割接导入表对应的线路关联产品实例','MIGRATIONIMPORT2LINERELAPRODINS','MIGRATIONIMPORT','PRODINS','LINERELAPRODINSID','ID','B');
exec p_insertM2OneRelation('割接导入表对应的端口关联产品实例','MIGRATIONIMPORT2PORTRELAPRODINS','MIGRATIONIMPORT','PRODINS','PORTRELAPRODINSID','ID','B');
exec p_insertM2OneRelation('割接导入表对应的线路资源服务实例','MIGRATIONIMPORT2LINESERVINS','MIGRATIONIMPORT','RESSERVINS','LINESERVINSID','ID','B');
COMMIT;

--by wxy 20091021

--
exec p_InsertEntitySpec('子网类型','SUBNETWORKTYPE','com.gxlu.ngrm.network.SubnetWorkType','XB_SUBNETWORKTYPE','');
exec p_insertEntityDescriptor('SUBNETWORKTYPE','XB_SUBNETWORKTYPE');
Commit;

exec p_InsertEntitySpec('子网类型保护类型约束','SNTYPEPSTYPECONSTRAINT','com.gxlu.ngrm.network.SNTypePSTypeConstraint','XB_SNTYPEPSTYPECONSTRAINT','');
exec p_insertEntityDescriptor('SNTYPEPSTYPECONSTRAINT','XB_SNTYPEPSTYPECONSTRAINT');
Commit;

Update Xm_Entitydescriptor d
	 Set d.Columntype = 15, d.Datatype = 15, d.Dicclassname = 'BANDWIDTH', d.Dicattributename = 'LAYERRATE'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'SUBNETWORKTYPE')
	 And d.Columnname = 'LAYERRATE';
Commit;

Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'METADATA', d.Dicattributename = 'MAINTAININDICATOR'
 Where d.Entityspecid In (Select Id From Xm_Entityspec Where Code In ('SUBNETWORKTYPE','SNTYPEPSTYPECONSTRAINT'))
	 And d.Columnname = 'MAINTAININDICATOR';
Commit;

Exec p_insertentitydescriptor('SUBNETWORK','XB_SUBNETWORK');
Commit;

exec p_insertM2OneRelation('子网所属子网类型','SUBNETWORK2SUBNETWORKTYPE','SUBNETWORK','SUBNETWORKTYPE','SUBNETWORKTYPEID','ID','B');
Commit;

exec p_insertM2OneRelation('子网类型保护类型约束对应的子网类型','SNTYPEPSTYPECONSTRAINT2SUBNETWORKTYPE','SNTYPEPSTYPECONSTRAINT','SUBNETWORKTYPE','SUBNETWORKTYPEID','ID','B');
Commit;

Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'SUBNETWORK'
 Where d.Entityspecid In (Select Id From Xm_Entityspec Where Code In ('SUBNETWORKTYPE'))
	 And d.Columnname In ('SUBNETWORKSPEC','TOPOLOGYTYPE');
Commit;

Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'SUBNETWORK'
 Where d.Entityspecid In (Select Id From Xm_Entityspec Where Code In ('SNTYPEPSTYPECONSTRAINT'))
	 And d.Columnname In ('PROTECTIONTYPE');
Commit;

Exec p_insertentitydescriptor('SUBNETWORKTYPE','XB_SUBNETWORKTYPE');
Commit;

--by liurr at 20091021
EXEC p_insertentitydescriptor('MIGRATIONBATCH','XB_MIGRATIONBATCH');
COMMIT;

--by liurr at 20091022
DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'PORT') AND COLUMNNAME = 'SERVICENO';
COMMIT;

exec p_insertentitydescriptor('PORT','XB_PORT');
Commit;

--by wxy 20091026
exec p_insertOne2MRelation('保护连接对应的连接','PROTECTINGOBJ2LINK','LINK','LINK','PROTECTINGOBJID','B');
commit;

--by liurr at 20091026
EXEC p_insertentitydescriptor('ONUMODELINI','XB_ONUMODELINI');
COMMIT;

--by liurr at 20091027
EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
COMMIT;

EXEC p_insertentitydescriptor('MIGRATIONBLOCK','XB_MIGRATIONBLOCK');
COMMIT;

--by wxy 20091026
            
exec p_Insertentityspec('菜单','MENUITEM','com.gxlu.ngrm.basic.MenuItem','X_MENUITEM','');
exec p_insertentitydescriptor('MENUITEM','X_MENUITEM');
commit;
exec p_Insertentityspec('实体管理模版','ENTITYMGTTEMPLATE','com.gxlu.ngrm.metadata.EntityMgtTemplate','XM_ENTITYMGTTEMPLATE','');
exec p_insertentitydescriptor('ENTITYMGTTEMPLATE','XM_ENTITYMGTTEMPLATE');
commit;
exec p_Insertentityspec('菜单和实体管理模版关联表','MENUITEMMGTTEMPLATEASSOC','com.gxlu.ngrm.metadata.MenuItemMgtTemplateAssoc','XM_MENUITEMMGTTEMPLATEASSOC','');
exec p_insertentitydescriptor('MENUITEMMGTTEMPLATEASSOC','XM_MENUITEMMGTTEMPLATEASSOC');
commit;

exec p_insertM2OneRelation('实体管理模版所属维护管理区','ENTITYMGTTEMPLATE2LOCATION','ENTITYMGTTEMPLATE','MANAGEMENTAREA','LOCATIONID','ID','M');
exec p_insertM2OneRelation('菜单和实体管理模版关联表对应的菜单','MENUITEMMGTTEMPLATEASSOC2MENUITEM','MENUITEMMGTTEMPLATEASSOC','MENUITEM','MENUITEMID','ID','M');
exec p_insertM2OneRelation('菜单和实体管理模版关联表对应的实体管理模版','MENUITEMMGTTEMPLATEASSOC2MGTTEMPLATE','MENUITEMMGTTEMPLATEASSOC','ENTITYMGTTEMPLATE','ENTITYMGTTEMPLATEID','ID','M');
COMMIT;

--by wxy 20091027
Exec p_insertentitydescriptor('LINK','XB_LINK');
Commit;

--by liurr at 20091028
EXEC p_insertcomponentdescriptor('PSTNPORT','XC_PSTNPORT');
EXEC p_insertcomponentdescriptor('XDSLPORT','XC_XDSLPORT');
EXEC p_insertcomponentdescriptor('LANPORT','XC_LANPORT');
COMMIT;

--by liurr at 20091028
EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
EXEC p_insertentitydescriptor('MIGRATIONIMPORT','XB_MIGRATIONIMPORT');
COMMIT;

--by liurr at 20091030
DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'SERVICEORDER') AND COLUMNNAME = 'CONFIGRESULT';
COMMIT;

EXEC p_insertentitydescriptor('SERVICEORDER','X_SERVICEORDER');
COMMIT;

UPDATE xm_entitydescriptor A SET A.Columntype = 15,a.datatype=15,A.DICCLASSNAME = 'PSTNCONFIG',A.DICATTRIBUTENAME = 'NWK'
WHERE entityspecid = (select id from xm_entityspec where code = 'SERVICEORDER') 
       AND A.COLUMNNAME = 'FUNC';
       
UPDATE xm_entitydescriptor A SET A.DICCLASSNAME = 'PRODINS',A.DICATTRIBUTENAME = 'ACCESSMODE'
WHERE entityspecid = (select id from xm_entityspec where code = 'SERVICEORDER') 
       AND A.COLUMNNAME = 'ACCESSMODE';
       
UPDATE xm_entitydescriptor A SET A.DICCLASSNAME = 'PRODINS',A.DICATTRIBUTENAME = 'BRANDTYPE'
WHERE entityspecid = (select id from xm_entityspec where code = 'SERVICEORDER') 
       AND A.COLUMNNAME = 'BRANDTYPE';
COMMIT;

EXEC p_insertentitydescriptor('WORKORDER','X_WORKORDER');
COMMIT;

UPDATE xm_entitydescriptor A SET A.DICCLASSNAME = 'TASK',A.DICATTRIBUTENAME = 'ACTION'
WHERE entityspecid = (select id from xm_entityspec where code = 'WORKORDER') 
       AND A.COLUMNNAME = 'ACTION';
COMMIT;

EXEC p_insertentitydescriptor('CONFIGRESULTHIS','XB_CONFIGRESULTHIS');
COMMIT;

UPDATE xm_entitydescriptor A SET A.DICCLASSNAME = 'SERVICEORDER',A.DICATTRIBUTENAME = 'EVENTTYPE'
WHERE entityspecid = (select id from xm_entityspec where code = 'CONFIGRESULTHIS') 
       AND A.COLUMNNAME = 'SERVICEORDERACTION';

UPDATE xm_entitydescriptor A SET A.DICCLASSNAME = 'TASK',A.DICATTRIBUTENAME = 'ACTION'
WHERE entityspecid = (select id from xm_entityspec where code = 'CONFIGRESULTHIS') 
       AND A.COLUMNNAME = 'WORKORDERACTION';
COMMIT;

--by liurr at 20091102
update xm_entityspec a set a.parentid = (select id from xm_entityspec where code = 'TASK') where a.code = 'DUMMYNOCONFIG';
commit;

--by liurr at 20091102
EXEC p_insertentitydescriptor('IVPNNO','X_IVPNNO');
COMMIT;

--by liurr at 20091103
EXEC p_insertentitydescriptor('PRODINS','XB_PRODINS');
EXEC p_insertentitydescriptor('SERVICEORDER','X_SERVICEORDER');
COMMIT;

--by liurr at 20091103
EXEC p_insertentitydescriptor('INTF_AREAROUTEMAPPING','X_INTF_AREAROUTEMAPPING');
COMMIT;

exec p_insertM2OneRelation('区域路由码映射对应的网元','AREAROUTEMAPPING2MANAGEDELEMENT','INTF_AREAROUTEMAPPING','MANAGEDELEMENT','MEID','ID','');
COMMIT;

--by wxy 20091103
exec p_insertentitydescriptor('MENUITEMMGTTEMPLATEASSOC','XM_MENUITEMMGTTEMPLATEASSOC');
commit;

--by liurr at 20091104
EXEC p_insertentitydescriptor('PRODUCTINSRELATION','XB_PRODUCTINSRELATION');
COMMIT;

--by liurr at 20091104
exec p_insertM2OneRelation('网元对应的父网元','MANAGEDELEMENT2PARENT','MANAGEDELEMENT','MANAGEDELEMENT','PARENTID','ID','B');
exec p_insertOne2MRelation('网元对应的子网元','MANAGEDELEMENT2CHILD','MANAGEDELEMENT','MANAGEDELEMENT','PARENTID','B');
commit;

--by liurr at 20091104
exec p_Insertentityspec('割接激活参数','MIGRATIONPARAM','com.gxlu.ngrm.resService.MigrationParam','XB_MIGRATIONPARAM','');
exec p_insertentitydescriptor('MIGRATIONPARAM','XB_MIGRATIONPARAM');
commit;
exec p_insertM2OneRelation('割接激活参数对应的产品实例','MIGRATIONPARAM2PRODINS','MIGRATIONPARAM','PRODINS','PRODINSID','ID','B');
COMMIT;
exec p_insertM2OneRelation('割接激活参数对应的割接批次','MIGRATIONPARAM2MIGRATIONBATCH','MIGRATIONPARAM','MIGRATIONBATCH','BATCHID','ID','B');
COMMIT;

--by liurr at 20091104
EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
COMMIT;

--by wxy 20091104
exec p_Insertentityspec('资源字符串','RESSTRINGMGR','com.gxlu.ngrm.basic.ResStringMgr','XB_RESSTRINGMGR','');
exec p_insertentitydescriptor('RESSTRINGMGR','XB_RESSTRINGMGR');
commit;

--by liurr at 20091105
EXEC p_insertentitydescriptor('MIGRATIONPARAM','XB_MIGRATIONPARAM');
COMMIT;

EXEC p_insertentitydescriptor('MIGRATIONRANGE','XB_MIGRATIONRANGE');
COMMIT;

EXEC p_insertentitydescriptor('PRODINS','XB_PRODINS');
EXEC p_insertentitydescriptor('SERVICEORDER','X_SERVICEORDER');
COMMIT;

--by liurr at 20091105
EXEC p_insertentitydescriptor('PRODINS','XB_PRODINS');
EXEC p_insertentitydescriptor('SERVICEORDER','X_SERVICEORDER');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'PRODINS', A.DICATTRIBUTENAME = 'CONNECTMETHOD' 
       WHERE A.ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'SERVICEORDER') AND A.COLUMNNAME = 'CONNECTMETHOD';
       
UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'PRODINS', A.DICATTRIBUTENAME = 'PRODUCTTYPE' 
       WHERE A.ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'SERVICEORDER') AND A.COLUMNNAME = 'PRODUCTTYPE';
COMMIT;

EXEC p_insertentitydescriptor('MIGRATIONACTION','XB_MIGRATIONACTION');
COMMIT;

--by liurr at 20091105
DELETE FROM XM_COMPONENTDESCRIPTOR A WHERE A.COMPONENTSPECID IN (SELECT ID FROM XM_COMPONENTSPEC WHERE CODE IN ('PSTNPORT','XDSLPORT','LANPORT'))
AND A.COLUMNNAME = 'COVERAGEADDRESS';
COMMIT;

EXEC p_insertentitydescriptor('PORT','XB_PORT');
COMMIT;

EXEC p_insertentitydescriptor('PRODUCTINSRELATION','XB_PRODUCTINSRELATION');
COMMIT;

--by liurr at 20091106
UPDATE XM_ENTITYDESCRIPTOR A SET A.COLUMNTYPE = 8, A.DATATYPE = 8 
       WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE='INTFLOG') AND A.COLUMNNAME IN ('RECEIVECONTENT','RETURNCONTENT');
       
UPDATE XM_ENTITYDESCRIPTOR A SET A.COLUMNTYPE = 7, A.DATATYPE = 7 
       WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE='PROJECTDESIGNTEMPLATE') AND A.COLUMNNAME = 'TEMPLATEXML';
       
COMMIT;

--by wxy 20091106
exec p_Insertentityspec('用户','USER','com.gxlu.ngrm.basic.User','X_USER','');
exec p_insertentitydescriptor('USER','X_USER');
commit;

exec p_insertM2OneRelation('用户所属上级用户','USER2PARENT','USER','USER','PARENTID','ID','');
exec p_insertOne2MRelation('用户包含的下级用户','USER2CHILD','USER','USER','PARENTID','');
exec p_insertM2NRelation('工位和用户的关系','PARTYROLE2USER','X_PARTYROLE_USER','PARTYROLE','USER','PARTYROLEID','USERID');
commit;

exec p_Insertentityspec('实体','ENTITYSPEC','com.gxlu.ngrm.metadata.EntitySpec','XM_ENTITYSPEC','');
exec p_insertentitydescriptor('ENTITYSPEC','XM_ENTITYSPEC');
commit;

exec p_Insertentityspec('实体属性','ENTITYDESCRIPTOR','com.gxlu.ngrm.metadata.EntityDescriptor','XM_ENTITYDESCRIPTOR','');
exec p_insertentitydescriptor('ENTITYDESCRIPTOR','XM_ENTITYDESCRIPTOR');
commit;

exec p_Insertentityspec('关系规格','RELATIONSPEC','com.gxlu.ngrm.metadata.RelationSpec','XM_RELATIONSPEC','');
exec p_insertentitydescriptor('RELATIONSPEC','XM_RELATIONSPEC');
commit;

exec p_insertM2OneRelation('实体所属上级实体','ENTITYSPEC2PARENT','ENTITYSPEC','ENTITYSPEC','PARENTID','ID','M');
exec p_insertOne2MRelation('实体所包含的下级实体','ENTITYSPEC2CHILD','ENTITYSPEC','ENTITYSPEC','PARENTID','M');

exec p_insertM2OneRelation('实体属性所属实体','ENTITYDESCRIPTOR2ENTITYSPEC','ENTITYDESCRIPTOR','ENTITYSPEC','ENTITYSPECID','ID','M');

exec p_insertM2OneRelation('实体关系对应的拥有实体','RELATIONSPEC2OWNERENTITYSPEC','RELATIONSPEC','ENTITYSPEC','OWNERENTITYSPECID','ID','M');
exec p_insertM2OneRelation('实体关系对应的提供实体','RELATIONSPEC2SUPPLIERENTITYSPEC','RELATIONSPEC','ENTITYSPEC','SUPPLIERENTITYSPECID','ID','M');
Commit;

Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'METADATA'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'ENTITYDESCRIPTOR')
	 And d.Columnname = 'DATATYPE';
commit;

exec p_insertentitydescriptor('DICTIONARY','X_DICTIONARY');
commit;

Update xm_relationspec d Set d.multiple = 1 Where d.code='TPAGGREGATION';
Update xm_relationspec d Set d.multiple = 4 Where d.code='TPAGGREGATIONC2P';
Commit;

--by liurr at 20091107
EXEC p_insertentitydescriptor('PORT','XB_PORT');
COMMIT;

--by liurr at 20091110
EXEC p_insertcomponentdescriptor('ONU','XC_ONU');
COMMIT;

--by liurr at 20091111
exec p_Insertentityspec('门户插件','PORTLET','com.gxlu.ngrm.basic.Portlet','XB_PORTLET','');
exec p_insertentitydescriptor('PORTLET','XB_PORTLET');
commit;

exec p_Insertentityspec('用户门户插件关联','USERPORTLETASSOC','com.gxlu.ngrm.basic.UserPortletAssoc','XB_USERPORTLETASSOC','');
exec p_insertentitydescriptor('USERPORTLETASSOC','XB_USERPORTLETASSOC');
commit;
exec p_insertM2OneRelation('用户门户插件关联对应的用户','USERPORTLETASSOC2USER','USERPORTLETASSOC','USER','USERID','ID','B');
COMMIT;
exec p_insertM2OneRelation('用户门户插件关联对应的门户插件','USERPORTLETASSOC2PORTLET','USERPORTLETASSOC','PORTLET','PORTLETID','ID','B');
COMMIT;

--by liurr at 20091111
exec p_InsertEntitySpec('局向','BSITE','com.gxlu.ngrm.area.BSite','XB_LOCATION','XS_BSITE');
COMMIT;

Update Xm_Entityspec d Set parentid = (select id from xm_entityspec where code = 'LOCATION') 
 Where d.Coretablename='XB_LOCATION' And d.Maintainindicator=3 AND PARENTID IS NULL;
commit;

--by liurr at 20091112
exec p_Insertentityspec('参数配置','SYSPARAMCONFIG','com.gxlu.ngrm.resService.SysParamConfig','XB_SYSPARAMCONFIG','');
exec p_insertentitydescriptor('SYSPARAMCONFIG','XB_SYSPARAMCONFIG');
commit;
exec p_insertM2OneRelation('参数配置对应的营销区域','SYSPARAMCONFIG2MARKETINGAREA','SYSPARAMCONFIG','MARKETINGAREA','MARKETINGAREAID','ID','B');
COMMIT;

--by liurr at 20091112
UPDATE XM_RELATIONSPEC A SET A.SUPPLIERENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'LOCATION') WHERE CODE = 'CODE2MARKETINGAREA';
COMMIT;

--by liurr at 20091112
EXEC p_insertentitydescriptor('BSITE','XS_BSITE');
COMMIT;

--by liurr at 20091113
UPDATE xm_entitydescriptor A SET A.Columntype = 15,a.datatype=15
WHERE entityspecid IN (select id from xm_entityspec where code IN ('MIGRATIONRESULTDETAIL','MIGRATIONIMPORT')) 
       AND A.COLUMNNAME = 'FUNC';
COMMIT;

delete from xm_entityspec2relationspec d where d.relationspecid IN 
       (select id from xm_relationspec r where r.code IN ('MIGRATIONLOG2PROJECT','MIGRATIONLOG2BATCH'));
delete from x_versioninstance d where d.subcategory IN ('MIGRATIONLOG2PROJECT','MIGRATIONLOG2BATCH');
delete from xm_relationspec where code IN ('MIGRATIONLOG2PROJECT','MIGRATIONLOG2BATCH');
commit;
DELETE FROM XM_ENTITYDESCRIPTOR WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONLOG');
COMMIT;

EXEC p_insertentitydescriptor('MIGRATIONLOG','XB_MIGRATIONLOG');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR B SET B.DICCLASSNAME = 'MIGRATIONRESULTDETAIL',B.DICATTRIBUTENAME = 'DELINDICATOR'  
       WHERE B.ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONLOG') AND B.COLUMNNAME = 'DELINDICATOR';
       
UPDATE XM_ENTITYDESCRIPTOR B SET B.DICCLASSNAME = 'MIGRATIONRESULTDETAIL', B.DICATTRIBUTENAME = 'FUNC' 
       WHERE B.ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONLOG') AND B.COLUMNNAME = 'FUNC';
COMMIT;

--by liurr at 20091113
EXEC p_insertentitydescriptor('MIGRATIONLOG','XB_MIGRATIONLOG');
COMMIT; 

--by liurr at 20091114
EXEC p_insertentitydescriptor('CODE','XB_CODE');
COMMIT;
exec p_insertM2OneRelation('号码对应的号码池','CODE2CODEPOOL','CODE','CODEPOOL','CODEPOOLID','ID','B');
COMMIT;

--删除原来号码和号码池的关系表
delete from xm_entityspec2relationspec d where d.relationspecid=(select id from xm_relationspec r where r.code='CODEPOOLCODEASSIGN');
delete from x_versioninstance d where d.subcategory='CODEPOOLCODEASSIGN';
delete from xm_relationspec where code='CODEPOOLCODEASSIGN';
commit;

--by liurr at 20091114
exec p_Insertentityspec('资产共用的资源服务实例','PRODINSSHARERESSERVINS','com.gxlu.ngrm.resService.ProdinsShareResservins','XB_PRODINSSHARERESSERVINS','');
exec p_insertentitydescriptor('PRODINSSHARERESSERVINS','XB_PRODINSSHARERESSERVINS');
commit;

--by wxy 20091116
Update Xm_Entitydescriptor d Set d.Isversion = 2
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'DICTIONARY')
	 And d.Columnname In ('CLASSID', 'ATTRIBUTEID');
Commit;

exec p_insertM2OneRelation('连接对应的保护连接','LINK2PROTECTOBJ','LINK','LINK','PROTECTINGOBJID','ID','B');
Commit;

--by liurr at 20091115 for 关系名称不变
update x_versioninstance set subcategory='CODEPOOLCODEASSIGN' where subcategory='CODE2CODEPOOL';
update xm_relationspec set code = 'CODEPOOLCODEASSIGN' where code='CODE2CODEPOOL';
commit;

--by liurr at 20091117
EXEC p_insertentitydescriptor('TASK','XB_TASK');
COMMIT;
exec p_insertM2OneRelation('任务对应的用户','TASK2USER','TASK','USER','USERID','ID','B');
COMMIT;  

--by wxy 20091118

exec p_insertM2OneRelation('连接类型对应的带宽','LINKTYPE2BANDWIDTH','LINKTYPE','BANDWIDTH','BANDWIDTHID','ID','B');
exec p_insertM2OneRelation('连接对应的带宽','LINK2BANDWIDTH','LINK','BANDWIDTH','BANDWIDTHID','ID','B');
Commit;

--by wxy 20091119
exec p_insertEntityDescriptor('SDHTRAIL','XS_SDHTRAIL');
Commit;


Update Xm_Componentdescriptor c
	 Set c.Dicclassname = 'MAITAINANCE', c.Dicattributename = 'MAITAINANCETYPE'
 Where c.Componentspecid = (Select Id From Xm_Componentspec Where Code = 'MAINTAINANCE')
	 And c.Columnname = 'MAITAINANCETYPE';
Commit;

--by wxy 20091120
Update Xm_Entitydescriptor d
	 Set d.Columntype = 15, d.Datatype = 15, d.Dicclassname = 'BANDWIDTH', d.Dicattributename = 'LAYERRATE'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'SUBNETWORKTYPE')
	 And d.Columnname = 'LAYERRATE';
Commit;

Update Xm_Entitydescriptor d
	 Set d.Columntype = 15, d.Datatype = 15, d.Dicclassname = 'BANDWIDTH', d.Dicattributename = 'LAYERRATE'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'SUBNETWORK')
	 And d.Columnname = 'LAYERRATE';
Commit;

exec p_insertESpec2CSpec('MANAGEDELEMENT','MAINTAINANCE');
exec p_insertESpec2CSpec('MANAGEDELEMENT','BENEFIT');
Commit;

--by wxy 20091121
exec p_Insertentityspec('任务资源信息','TASKPRERESINFO','com.gxlu.ngrm.resService.TaskPreResInfo','XB_TASKPRERESINFO','');
exec p_insertentitydescriptor('TASKPRERESINFO','XB_TASKPRERESINFO');
Commit;

exec p_insertM2OneRelation('任务资源信息所属站点','TASKPRERESINFO2SITE','TASKPRERESINFO','SITE','SITEID','ID','B');
exec p_insertM2OneRelation('任务资源信息所属机房','TASKPRERESINFO2ROOM','TASKPRERESINFO','ROOM','ROOMID','ID','B');
exec p_insertM2OneRelation('任务资源信息所属户外安装地','TASKPRERESINFO2OUTDOORADDRESS','TASKPRERESINFO','OUTDOORADDRESS','OUTDOORADDRESSID','ID','B');
exec p_insertM2OneRelation('任务资源信息所属网元','TASKPRERESINFO2MANAGEDELEMENT','TASKPRERESINFO','MANAGEDELEMENT','MEID','ID','B');
exec p_insertM2OneRelation('任务资源信息所属端口','TASKPRERESINFO2PORT','TASKPRERESINFO','PORT','PORTID','ID','B');
Commit;

--by liurr at 20091123
EXEC p_insertentitydescriptor('SYSPARAMCONFIG','XB_SYSPARAMCONFIG');
COMMIT;

EXEC p_insertentitydescriptor('MIGRATIONBATCH','XB_MIGRATIONBATCH');
COMMIT;

--by liurr at 20091123
EXEC p_insertcomponentdescriptor('XDSLPORT','XC_XDSLPORT');
COMMIT;

EXEC p_insertcomponentdescriptor('LANPORT','XC_LANPORT');
COMMIT;

UPDATE XM_COMPONENTDESCRIPTOR A SET A.DICCLASSNAME = 'XDSLPORT',A.DICATTRIBUTENAME = 'BUSINESSUSEFOR' 
       WHERE A.COMPONENTSPECID = (SELECT ID FROM XM_COMPONENTSPEC WHERE CODE = 'LANPORT') AND A.COLUMNNAME = 'BUSINESSUSEFOR';
COMMIT;

--by liurr at 20091123
UPDATE XM_ENTITYDESCRIPTOR A SET A.DATALENGTH = 1000 WHERE A.ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'CONFIGRESULTHIS')
       AND A.COLUMNNAME = 'RESSTRING';
COMMIT;

--by liurr at 20091124
EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
COMMIT;

--by liurr at 20091125
EXEC p_insertentitydescriptor('PHYCODECONFIG','XS_PHYCODECONFIG');
COMMIT;

--by liurr at 20091125
UPDATE XM_ENTITYDESCRIPTOR SET COLUMNTYPE=4,DATATYPE=4,DATALENGTH=255,ISFK = 2
WHERE ENTITYSPECID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='MODIFYDETAIL') 
AND ATTRNAME = 'ADDRESSID';
COMMIT;

--by wxy 20091127
exec p_insertentitydescriptor('PSTNPORT','XS_PSTNPORT');
Commit;

--by liurr at 20091127
EXEC p_insertcomponentdescriptor('PSTNNE','XC_PSTNNE');
COMMIT;

--by liurr at 20091127
EXEC p_insertentitydescriptor('MIGRATIONACTIONDETAIL','XB_MIGRATIONACTIONDETAIL');
COMMIT;

exec p_insertM2OneRelation('割接动作详情对应的新XDSL数据横列端子','MIGRATIONACTIONDETAIL2NEWXDSLROWCON','MIGRATIONACTIONDETAIL','ECONNECTOR','NEWXDSLROWCONID','ID','B');
exec p_insertM2OneRelation('割接动作详情对应的新XDSL语音横列端子','MIGRATIONACTIONDETAIL2NEWXDSLROWCON2','MIGRATIONACTIONDETAIL','ECONNECTOR','NEWXDSLROWCONID2','ID','B');
exec p_insertM2OneRelation('割接动作详情对应的新设备横列MDF','MIGRATIONACTIONDETAIL2NEWDEVICEMDF','MIGRATIONACTIONDETAIL','MDF','NEWDEVICEMDFID','ID','B');
COMMIT;

--by liurr at 20091129
EXEC p_insertentitydescriptor('MIGRATIONBATCH','XB_MIGRATIONBATCH');
COMMIT;

--by liurr at 20091202
EXEC p_insertentitydescriptor('PRODINS','XB_PRODINS');
COMMIT;

EXEC p_insertentitydescriptor('SERVICEORDER','X_SERVICEORDER');
COMMIT;

--by zw 20091127
--------------------------
--修改原BAS为‘ACCESSDEVICE’
UPDATE XM_COMPONENTSPEC SET NAME = '接入服务器组件',ALIAS = '接入服务器组件',COMPONENTTABLENAME = 'XC_ACCESSDEVICE',SEQUENCE = 'S_XC_ACCESSDEVICE',CODE = 'ACCESSDEVICE' WHERE CODE = 'BAS';
COMMIT;
	                             
--exec p_InsertEntitySpec('交换设备','SNME','com.gxlu.ngrm.equipment.SNME','XB_MANAGEDELEMENT','XS_SNME');
exec p_InsertEntitySpec('软交换交换机','SOFTSWITCH','com.gxlu.ngrm.equipment.SoftSwitch','XB_MANAGEDELEMENT','XS_SOFTSWITCH');

exec p_insertComponetSpec('交换设备','SNME');
exec p_insertComponetSpec('MODEM组件','MODEM');
exec p_insertComponetSpec('短消息中心组件','SC');
exec p_insertComponetSpec('信令转接点组件','STP');
exec p_insertComponetSpec('ATM/DDN/FR组件','ATMDDNFR');
exec p_insertComponetSpec('交换机组件','SWITCH');
exec p_insertComponetSpec('路由器组件','ROUTER');
exec p_insertComponetSpec('OLT组件','OLT');
exec p_insertComponetSpec('OBD组件','OBD');
exec p_insertComponetSpec('光端机组件','OPTICALMODEM');
exec p_insertComponetSpec('用户设备组件','USERDEVICE');
exec p_insertComponetSpec('数据端口组件','DNPORT');
exec p_insertComponetSpec('磁盘阵列组件','DISKARRAY');
exec p_insertComponetSpec('DRV组件','DRV');
exec p_insertComponetSpec('服务器组件','SERVER');
exec p_insertComponetSpec('主机组件','MAINFRAMECOMPUTER');
exec p_insertComponetSpec('存储服务器组件','STORAGESERVER');
exec p_insertComponetSpec('视频分配器组件','VIDEODISTRIBUTION');
exec p_insertComponetSpec('全球眼编码器组件','VIDEOFRONTEND');
exec p_insertComponetSpec('摄像枪组件','VIDEOHEAD');
exec p_insertComponetSpec('视频矩阵组件','VIDEOMATRIX');
exec p_insertComponetSpec('视频分隔器组件','VIDEOSEPARATE');
exec p_insertComponetSpec('监视器组件','VIDEOWALL');

EXEC p_Insertcomponentdescriptor('PSTNPORT','XC_PSTNPORT');
EXEC p_Insertcomponentdescriptor('DSLAM','XC_DSLAM');
EXEC p_Insertcomponentdescriptor('ONU','XC_ONU');

--新增的字段增加到xm_entitydescriptor表中

Update Xm_Entitydescriptor d
	 Set d.Columntype = 10, d.Datatype = 10, d.Datalength = 5, d.Dicclassname = 'PHYSICALCONTAINER',
			 d.Dicattributename = 'MOUNTAINPOSITION'
 Where Entityspecid In (Select Id From Xm_Entityspec Where Code In ('PHYSICALCONTAINER', 'CARD', 'PORT'))
	 And Columnname = 'MOUNTAINPOSITION';
Commit;

exec p_insertentitydescriptor('PHYSICALCONTAINER','XB_PHYSICALCONTAINER');
EXEC p_insertentitydescriptor('CARD','XB_CARD');
EXEC p_insertentitydescriptor('MANAGEDELEMENT','XB_MANAGEDELEMENT');
EXEC p_insertentitydescriptor('PORT','XB_PORT');
EXEC p_insertentitydescriptor('EXCHANGE','XS_EXCHANGE');

COMMIT;

--------------------------------

--字典值特例
UPDATE XM_ENTITYDESCRIPTOR D SET D.DICCLASSNAME = 'PHYSICALRESOURCE' 
 WHERE D.ENTITYSPECID IN (SELECT ID FROM XM_ENTITYSPEC E WHERE E.CODE IN ('PHYSICALCONTAINER','CARD','MANAGEDELEMENT','PORT') )
   AND D.DICATTRIBUTENAME = 'DATESOURCE';
COMMIT;

UPDATE XM_COMPONENTDESCRIPTOR D SET D.DICCLASSNAME = 'DSLAM' 
 WHERE D.COMPONENTSPECID IN (SELECT ID FROM XM_COMPONENTSPEC E WHERE E.CODE IN ('ATMDDNFR','SWITCH') )
   AND D.DICATTRIBUTENAME = 'BINDMODE';

UPDATE xm_EntityDescriptor d set d.Dicclassname = 'EXCHANGE' ,D.DICATTRIBUTENAME = 'ISHOME'
 where d.Entityspecid = (select id from xm_entityspec e where e.Code = 'EXCHANGE' )
   and d.ATTRNAME = 'ISHOME';
COMMIT;

UPDATE xm_EntityDescriptor d set d.Dicclassname = 'EXCHANGE' ,D.DICATTRIBUTENAME = 'ISGATEOFFICE'
 where d.Entityspecid = (select id from xm_entityspec e where e.Code = 'EXCHANGE' )
   and d.ATTRNAME = 'ISGATEOFFICE';
COMMIT;

--路由器的RouterProtocol使用SWITCH.RouterProtocol
UPDATE XM_COMPONENTDESCRIPTOR D SET D.DICCLASSNAME = 'SWITCH' 
 WHERE D.COMPONENTSPECID = (SELECT ID FROM XM_COMPONENTSPEC E WHERE E.CODE = 'ROUTER' )
   AND D.DICATTRIBUTENAME = 'ROUTERPROTOCOL';

COMMIT;

--by wxy 20091130
Exec p_insertentitydescriptor('PHYSICALCONTAINER','XB_PHYSICALCONTAINER');
Exec p_insertentitydescriptor('MANAGEDELEMENT','XB_MANAGEDELEMENT');
Commit;

Exec p_insertComponentdescriptor('MEMORY','XC_MEMORY');
Commit;

exec p_insertComponetSpec('动力组件','POWER');
Exec p_insertComponentdescriptor('POWER','XC_POWER');
Commit;

exec p_insertComponetSpec('资产组件','ASSETATTR');
Exec p_insertComponentdescriptor('ASSETATTR','XC_ASSETATTR');
Commit;

exec p_insertESpec2CSpec('ASSET','ASSETATTR');
Commit;

exec p_insertComponetSpec('工程组件','PROJECTATTR');
Exec p_insertComponentdescriptor('PROJECTATTR','XC_PROJECTATTR');
Commit;

exec p_insertESpec2CSpec('PROJECT','PROJECTATTR');
Commit;

Exec p_insertentitydescriptor('AUDITRECORD','X_AUDITRECORD');
Commit;

--metadata
exec p_InsertEntitySpec('短消息中心','SC','com.gxlu.ngrm.equipment.SC','XB_MANAGEDELEMENT','XS_SC');
exec p_InsertEntitySpec('软交换交换机','NGN','com.gxlu.ngrm.equipment.NGN','XB_MANAGEDELEMENT','XS_NGN');
exec p_InsertEntitySpec('数据网通用设备','COMMONDEVICE','com.gxlu.ngrm.equipment.CommonDevice','XB_MANAGEDELEMENT','XS_COMMONDEVICE');
exec p_InsertEntitySpec('交换机','SWITCH','com.gxlu.ngrm.equipment.Switch','XB_MANAGEDELEMENT','XS_SWITCH');
exec p_InsertEntitySpec('路由器','ROUTER','com.gxlu.ngrm.equipment.Router','XB_MANAGEDELEMENT','XS_ROUTER');
exec p_InsertEntitySpec('窄带接入服务器','NAS','com.gxlu.ngrm.equipment.NAS','XB_MANAGEDELEMENT','XS_NAS');
exec p_InsertEntitySpec('DVR','DVR','com.gxlu.ngrm.equipment.DVR','XB_MANAGEDELEMENT','XS_DVR');
exec p_InsertEntitySpec('防火墙','FIREWALL','com.gxlu.ngrm.equipment.Firewall','XB_MANAGEDELEMENT','XS_FIREWALL');
exec p_InsertEntitySpec('服务器','SERVER','com.gxlu.ngrm.equipment.Server','XB_MANAGEDELEMENT','XS_SERVER');
exec p_InsertEntitySpec('存储服务器','STORAGESERVER','com.gxlu.ngrm.equipment.StorageServer','XB_MANAGEDELEMENT','XS_STORAGESERVER');
exec p_InsertEntitySpec('主机','MAINFRAMECOMPUTER','com.gxlu.ngrm.equipment.MainFrameComputer','XB_MANAGEDELEMENT','XS_MAINFRAMECOMPUTER');
exec p_InsertEntitySpec('光电转换器','OETRANS','com.gxlu.ngrm.equipment.OETrans','XB_MANAGEDELEMENT','XS_OETRANS');
exec p_InsertEntitySpec('光端机','OPTICALMODEM','com.gxlu.ngrm.equipment.OpticalModem','XB_MANAGEDELEMENT','XS_OPTICALMODEM');
exec p_InsertEntitySpec('协议转换器','PROTOCOLTRANS','com.gxlu.ngrm.equipment.ProtocolTrans','XB_MANAGEDELEMENT','XS_PROTOCOLTRANS');
exec p_InsertEntitySpec('弹性分组环','RPR','com.gxlu.ngrm.equipment.RPR','XB_MANAGEDELEMENT','XS_RPR');
exec p_InsertEntitySpec('用户设备','USERDEVICE','com.gxlu.ngrm.equipment.UserDevice','XB_MANAGEDELEMENT','XS_USERDEVICE');
exec p_InsertEntitySpec('视频分配器','VIDEODISTRIBUTION','com.gxlu.ngrm.equipment.VideoDistribution','XB_MANAGEDELEMENT','XS_VIDEODISTRIBUTION');
exec p_InsertEntitySpec('全球眼编码器','VIDEOFRONTEND','com.gxlu.ngrm.equipment.VideoFrontEnd','XB_MANAGEDELEMENT','XS_VIDEOFRONTEND');
exec p_InsertEntitySpec('摄像枪','VIDEOHEAD','com.gxlu.ngrm.equipment.VideoHead','XB_MANAGEDELEMENT','XS_VIDEOHEAD');
exec p_InsertEntitySpec('视频矩阵系统','VIDEOMATRIX','com.gxlu.ngrm.equipment.VideoMatrix','XB_MANAGEDELEMENT','XS_VIDEOMATRIX');
exec p_InsertEntitySpec('视频分隔器','VIDEOSEPARATE','com.gxlu.ngrm.equipment.VideoSeparate','XB_MANAGEDELEMENT','XS_VIDEOSEPARATE');
exec p_InsertEntitySpec('监视器','VIDEOWALL','com.gxlu.ngrm.equipment.VideoWall','XB_MANAGEDELEMENT','XS_VIDEOWALL');
exec p_InsertEntitySpec('WLANAP','WLANAP','com.gxlu.ngrm.equipment.WLANAP','XB_MANAGEDELEMENT','XS_WLANAP');
exec p_InsertEntitySpec('工作站','WORKSTATION','com.gxlu.ngrm.equipment.WorkStation','XB_MANAGEDELEMENT','XS_WORKSTATION');
exec p_InsertEntitySpec('MSTP设备','MSTP','com.gxlu.ngrm.equipment.Mstp','XB_MANAGEDELEMENT','XS_MSTP');
exec p_InsertEntitySpec('HDSL设备','HDSL','com.gxlu.ngrm.equipment.Hdsl','XB_MANAGEDELEMENT','XS_HDSL');
exec p_InsertEntitySpec('光MODEM设备','MODEM','com.gxlu.ngrm.equipment.Modem','XB_MANAGEDELEMENT','XS_MODEM');
Commit;

Update Xm_Entityspec
	 Set Parentid = (Select Id
											From Xm_Entityspec d
										 Where d.Coretablename = 'XB_MANAGEDELEMENT'
											 And d.Maintainindicator = 1)
 Where Coretablename = 'XB_MANAGEDELEMENT'
   And Parentid is null
   And Maintainindicator = 3;
Commit;
			 
exec p_insertESpec2CSpec('EXCHANGE','SNME');
exec p_insertESpec2CSpec('SC','SC');
exec p_insertESpec2CSpec('ATM','ATMDDNFR');
exec p_insertESpec2CSpec('DDN','ATMDDNFR');
exec p_insertESpec2CSpec('FR','ATMDDNFR');
exec p_insertESpec2CSpec('ATM/DDN/FR','ATMDDNFR');
exec p_insertESpec2CSpec('SWITCH','SWITCH');
exec p_insertESpec2CSpec('ROUTER','ROUTER');
exec p_insertESpec2CSpec('NAS','ACCESSDEVICE');
exec p_insertESpec2CSpec('DISKARRAY','DISKARRAY');
exec p_insertESpec2CSpec('DVR','DVR');
exec p_insertESpec2CSpec('SERVER','SERVER');
exec p_insertESpec2CSpec('STORAGESERVER','STORAGESERVER');
exec p_insertESpec2CSpec('MAINFRAMECOMPUTER','MAINFRAMECOMPUTER');
exec p_insertESpec2CSpec('OPTICALMODEM','OPTICALMODEM');
exec p_insertESpec2CSpec('USERDEVICE','USERDEVICE');
exec p_insertESpec2CSpec('VIDEODISTRIBUTION','VIDEODISTRIBUTION');
exec p_insertESpec2CSpec('VIDEOFRONTEND','VIDEOFRONTEND');
exec p_insertESpec2CSpec('VIDEOHEAD','VIDEOHEAD');
exec p_insertESpec2CSpec('VideoMatrix','VIDEOMATRIX');
exec p_insertESpec2CSpec('VIDEOSEPARATE','VIDEOSEPARATE');
exec p_insertESpec2CSpec('VIDEOWALL','VIDEOWALL');
exec p_insertESpec2CSpec('WLANAP','WLANAP');
exec p_insertESpec2CSpec('MODEM','MODEM');

Commit;

--by wxy 20091203
Update Xm_Relationspec r
	 Set r.Relationinstancetablename = 'XB_TASKPRERESINFO'
 Where r.Relationinstancetablename = 'X_TASKPRERESINFO';
Commit;

--by liurr at 20091203
update xm_entitydescriptor t0 set t0.attrdispname='回收模式' where t0.entityspecid =(select id from xm_entityspec b where b.code = 'CODEPOOL') and t0.columnname='CALLBACKMODEL';
update xm_entitydescriptor t0 set t0.attrdispname='回收日期' where t0.entityspecid =(select id from xm_entityspec b where b.code = 'CODEPOOL') and t0.columnname='CALLBACKDATE';
update xm_entitydescriptor t0 set t0.attrdispname='回收原因' where t0.entityspecid =(select id from xm_entityspec b where b.code = 'CODEPOOL') and t0.columnname='CALLBACKPERSON';
update xm_entitydescriptor t0 set t0.attrdispname='自动回收日期' where t0.entityspecid =(select id from xm_entityspec b where b.code = 'CODEPOOL') and t0.columnname='AUTOCALLBACKDATE';
update xm_entitydescriptor t0 set t0.attrdispname='展现标志' where t0.entityspecid =(select id from xm_entityspec b where b.code = 'CODEPOOL') and t0.columnname='NETFLAG';
update xm_entitydescriptor t0 set t0.attrdispname='渠道类别' where t0.entityspecid =(select id from xm_entityspec b where b.code = 'CODEPOOL') and t0.columnname='CHANNELFLAG';
commit;

--by liurr at 20091204
update xm_entitydescriptor t0 set t0.attrdispname='类型' where t0.entityspecid =(select id from xm_entityspec b where code = 'CODEPROTOCOLNO') and t0.columnname='TYPE';
commit;

--by liurr at 20091208
exec p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
COMMIT;

--by wxy 20091208
exec p_insertentitydescriptor('TASKPRERESINFO','XB_TASKPRERESINFO');
Commit;

--metadata
exec p_Insertentityspec('文本路由','TEXTROUTE','com.gxlu.ngrm.network.TextRoute','XB_LINK','XS_TEXTROUTE');
exec p_insertEntityDescriptor('TEXTROUTE','XS_TEXTROUTE');
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'TRAIL')
 Where CODE IN('TEXTROUTE','LINEROUTE');
Commit;

exec p_Insertentityspec('信令链路组','SIGNALINGLINKSET','com.gxlu.ngrm.network.SignalingLinkSet','XB_LINK','XS_SIGNALINGLINKSET');
exec p_insertEntityDescriptor('SIGNALINGLINKSET','XS_SIGNALINGLINKSET');
Commit;

exec p_Insertentityspec('信令链路','SIGNALINGLINK','com.gxlu.ngrm.network.SignalingLink','XB_LINK','XS_SIGNALINGLINK');
exec p_insertEntityDescriptor('SIGNALINGLINK','XS_SIGNALINGLINK');
Commit;

exec p_Insertentityspec('中继群','TRUNKGROUP','com.gxlu.ngrm.network.TrunkGroup','XB_LINK','XS_TRUNKGROUP');
exec p_insertEntityDescriptor('TRUNKGROUP','XS_TRUNKGROUP');
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'LINK')
 Where CODE IN('SIGNALINGLINKSET','SIGNALINGLINK','TRUNKGROUP');
Commit;

exec p_Insertentityspec('中继电路','TRUNK','com.gxlu.ngrm.network.Trunk','XB_LINK','XS_TRUNK');
exec p_insertEntityDescriptor('TRUNK','XS_TRUNK');
Commit;

Update XM_ENTITYSPEC 
   Set PARENTID = (Select Id from XM_ENTITYSPEC Where CODE = 'CIRCUIT')
 Where CODE IN('TRUNK');
Commit;

exec p_insertEntityDescriptor('CIRCUIT','XS_CIRCUIT');
Commit;

exec p_insertComponetSpec('中继','RELAYPATH');
exec p_insertComponetSpec('ATM电路','ATMCIRCUIT');
exec p_insertComponetSpec('DDN电路','DDNCIRCUIT');
exec p_insertComponetSpec('DDN复用电路','DDNSUBCIRCUIT');
exec p_insertComponetSpec('FR电路','FRCIRCUIT');
exec p_insertComponetSpec('传输电路','TRANSCIRCUIT');
Commit;

--ESPEC2CSPEC
exec p_insertESpec2CSpec('RELAYCIRCUIT','RELAYPATH');

exec p_insertESpec2CSpec('ATMCIRCUIT','ATMCIRCUIT');
exec p_insertESpec2CSpec('ATMOCIRCUIT','ATMCIRCUIT');

exec p_insertESpec2CSpec('DDNCIRCUIT','DDNCIRCUIT');
exec p_insertESpec2CSpec('DDNOCIRCUIT','DDNCIRCUIT');

exec p_insertESpec2CSpec('FRCIRCUIT','FRCIRCUIT');
exec p_insertESpec2CSpec('FROCIRCUIT','FRCIRCUIT');

exec p_insertESpec2CSpec('DDNMUXCIRCUIT','DDNSUBCIRCUIT');

exec p_insertESpec2CSpec('TRANSCIRCUIT','TRANSCIRCUIT');

Commit;

--by wxy 20091209
exec p_Insertentityspec('实体规格','ENTITYSPEC','com.gxlu.ngrm.metadata.EntitySpec','XM_ENTITYSPEC','');
exec p_insertentitydescriptor('ENTITYSPEC','XM_ENTITYSPEC');
commit;

exec p_Insertentityspec('实体属性','ENTITYDESCRIPTOR','com.gxlu.ngrm.metadata.EntityDescriptor','XM_ENTITYDESCRIPTOR','');
exec p_insertentitydescriptor('ENTITYDESCRIPTOR','XM_ENTITYDESCRIPTOR');
commit;

exec p_Insertentityspec('关系规格','RELATIONSPEC','com.gxlu.ngrm.metadata.RelationSpec','XM_RELATIONSPEC','');
exec p_insertentitydescriptor('RELATIONSPEC','XM_RELATIONSPEC');
commit;

exec p_Insertentityspec('实体规格与关系规格的映射','ENTITYSPEC2RELATIONSPEC','com.gxlu.ngrm.metadata.EntitySpec2RealtionSpec','XM_ENTITYSPEC2RELATIONSPEC','');
exec p_insertentitydescriptor('ENTITYSPEC2RELATIONSPEC','XM_ENTITYSPEC2RELATIONSPEC');
commit;

exec p_insertM2OneRelation('实体与关系规格映射对应的实体规格','ESPEC2RSPEC2ENTITYSPEC','ENTITYSPEC2RELATIONSPEC','ENTITYSPEC','ENTITYSPECID','ID','M');
exec p_insertM2OneRelation('实体与关系规格映射对应的关系规格','ESPEC2RSPEC2RELATIONSPEC','ENTITYSPEC2RELATIONSPEC','RELATIONSPEC','RELATIONSPECID','ID','M');
Commit;

exec p_Insertentityspec('组件规格','COMPONENTSPEC','com.gxlu.ngrm.metadata.ComponentSpec','XM_COMPONENTSPEC','');
exec p_insertentitydescriptor('COMPONENTSPEC','XM_COMPONENTSPEC');
commit;

exec p_Insertentityspec('组件描述','COMPONENTDESCRIPTOR','com.gxlu.ngrm.metadata.ComponentDescriptor','XM_COMPONENTDESCRIPTOR','');
exec p_insertentitydescriptor('COMPONENTDESCRIPTOR','XM_COMPONENTDESCRIPTOR');
Commit;


exec p_insertM2OneRelation('组件描述对应的组件规格','COMPONENTDESCRITPOR2COMPONENTSPEC','COMPONENTDESCRITPOR','COMPONENTSPEC','COMPONENTSPECID','ID','M');
commit;

exec p_Insertentityspec('实体规格与组件规格的映射','ENTITYSPEC2COMPONENTSPEC','com.gxlu.ngrm.metadata.EntitySpec2ComponentSpec','XM_ENTITYSPEC2COMPONENTSPEC','');
exec p_insertentitydescriptor('ENTITYSPEC2COMPONENTSPEC','XM_ENTITYSPEC2COMPONENTSPEC');
commit;

exec p_insertM2OneRelation('实体与组件规格映射对应的实体规格','ESPEC2CSPEC2ENTITYSPEC','ENTITYSPEC2COMPONENTSPEC','ENTITYSPEC','ENTITYSPECID','ID','M');
exec p_insertM2OneRelation('实体与组件规格映射对应的组件规格','ESPEC2CSPEC2COMPONENTSPEC','ENTITYSPEC2COMPONENTSPEC','COMPONENTSPEC','COMPONENTSPECID','ID','M');
Commit;

--XM_ENTITYMGTTEMPLATE
exec p_Insertentityspec('实体管理模版','ENTITYMGTTEMPLATE','com.gxlu.ngrm.metadata.EntityMgtTemplate','XM_ENTITYMGTTEMPLATE','');
exec p_insertentitydescriptor('ENTITYMGTTEMPLATE','XM_ENTITYMGTTEMPLATE');
commit;

exec p_Insertentityspec('组件管理模板','COMPMGTTEMPLATE','com.gxlu.ngrm.metadata.CompMgtTemplate','XM_COMPMGTTEMPLATE','');
exec p_insertentitydescriptor('COMPMGTTEMPLATE','XM_COMPMGTTEMPLATE');

exec p_insertM2OneRelation('组件管理模板对应的组件规格','COMPMGTTEMPLATE2COMPONENTSPEC','COMPMGTTEMPLATE','COMPONENTSPEC','COMPONENTSPECID','ID','M');
Commit;

exec p_Insertentityspec('实体管理模板关系','ENTITYMGTTEMPLATERELATION','com.gxlu.ngrm.metadata.EntityMgtTemplateRelation','XM_ENTITYMGTTEMPLATERELATION','');
exec p_insertentitydescriptor('ENTITYMGTTEMPLATERELATION','XM_ENTITYMGTTEMPLATERELATION');
commit;

exec p_insertM2OneRelation('实体管理模板关系对应的实体管理模板','EMGTTMPLRELATION2ENTITYMGTTEMPLATE','ENTITYMGTTEMPLATERELATION','ENTITYMGTTEMPLATE','ENTITYMGTTEMPLATEID','ID','M');
exec p_insertM2OneRelation('实体管理模板关系对应的实体与关系指配','EMGTTMPLRELATION2SPEC2RELATION','ENTITYMGTTEMPLATERELATION','ENTITYSPEC2RELATIONSPEC','SPEC2RELATIONID','ID','M');
exec p_insertM2OneRelation('实体管理模板关系对应的对端实体管理模版','EMGTTMPLRELATION2RELAMGTTEMPLATE','ENTITYMGTTEMPLATERELATION','ENTITYMGTTEMPLATE','RELAMGTTEMPLATEID','ID','M');
exec p_insertM2OneRelation('实体管理模板关系对应的对端实体显示属性','EMGTTMPLRELATION2RELAENTITYDISPATTR','ENTITYMGTTEMPLATERELATION','ENTITYDESCRIPTOR','RELAENTITYDISPATTRID','ID','M');
commit;

exec p_Insertentityspec('实体管理模板属性','MGTTEMPLATEATTR','com.gxlu.ngrm.metadata.MgtTemplateAttr','XM_MGTTEMPLATEATTR','');
exec p_insertentitydescriptor('MGTTEMPLATEATTR','XM_MGTTEMPLATEATTR');

exec p_insertM2OneRelation('实体管理模板属性对应的实体管理模版','MGTTEMPLATEATTR2ENTITYMGTTEMPLATE','MGTTEMPLATEATTR','ENTITYMGTTEMPLATE','ENTITYMGTTEMPLATEID','ID','M');
exec p_insertM2OneRelation('实体管理模板属性对应的组件管理模版','MGTTEMPLATEATTR2COMPMGTTEMPLATE','MGTTEMPLATEATTR','COMPMGTTEMPLATE','COMPMGTTEMPLATEID','ID','M');
commit;

exec p_Insertentityspec('实体管理模板属性效验','ENTITYMGTTMPLATTRVALID','com.gxlu.ngrm.metadata.EntityMgtTmplAttrValid','XM_ENTITYMGTTMPLATTRVALID','');
exec p_insertentitydescriptor('ENTITYMGTTMPLATTRVALID','XM_ENTITYMGTTMPLATTRVALID');

exec p_insertM2OneRelation('实体管理模板属性效验对应的实体管理模板属性','ENTITYMGTTMPLATTRVALID2MGTTEMPLATEATTR','ENTITYMGTTMPLATTRVALID','MGTTEMPLATEATTR','ENTITYMGTTEMPLATEATTRID','ID','M');
commit;

exec p_Insertentityspec('实体管理模板表格','MGTTEMPLATETBL','com.gxlu.ngrm.metadata.MgtTemplateTbl','XM_MGTTEMPLATETBL','');
exec p_insertentitydescriptor('MGTTEMPLATETBL','XM_MGTTEMPLATETBL');

exec p_insertM2OneRelation('实体管理模板表格对应的实体管理模板','MGTTEMPLATETBL2ENTITYMGTTEMPLATE','MGTTEMPLATETBL','ENTITYMGTTEMPLATE','ENTITYMGTTEMPLATEID','ID','M');
commit;
      
exec p_Insertentityspec('实体管理模板表单','MGTTEMPLATEFRM','com.gxlu.ngrm.metadata.MgtTemplateFrm','XM_MGTTEMPLATEFRM','');
exec p_insertentitydescriptor('MGTTEMPLATEFRM','XM_MGTTEMPLATEFRM');

exec p_insertM2OneRelation('实体管理模板表单对应的实体管理模板','MGTTEMPLATEFRM2ENTITYMGTTEMPLATE','MGTTEMPLATEFRM','ENTITYMGTTEMPLATE','ENTITYMGTTEMPLATEID','ID','M');
commit;

exec p_Insertentityspec('实体管理模板表单分组','MGTTEMPLATEFRMGRP','com.gxlu.ngrm.metadata.MgtTemplateFrmGrp','XM_MGTTEMPLATEFRMGRP','');
exec p_insertentitydescriptor('MGTTEMPLATEFRMGRP','XM_MGTTEMPLATEFRMGRP');

exec p_insertM2OneRelation('实体管理模板表单分组对应的管理模板表单','MGTTEMPLATEFRMGRP2MGTTEMPLATEFRM','MGTTEMPLATEFRMGRP','MGTTEMPLATEFRM','MGTTEMPLATEFRMID','ID','M');
commit;

exec p_Insertentityspec('实体管理模板表单元素','MGTTEMPLATEFRMELEMENT','com.gxlu.ngrm.metadata.MgtTemplateFrmElement','XM_MGTTEMPLATEFRMELEMENT','');
exec p_insertentitydescriptor('MGTTEMPLATEFRMELEMENT','XM_MGTTEMPLATEFRMELEMENT');

exec p_insertM2OneRelation('实体管理模板表单元素对应的实体管理模版属性','MGTTEMPLATEFRMELEMENT2MGTTEMPLATEATTR','MGTTEMPLATEFRMELEMENT','MGTTEMPLATEATTR','MGTTEMPLATEATTRID','ID','M');
exec p_insertM2OneRelation('实体管理模板表单元素对应的管理模版表单分组','MGTTEMPLATEFRMELEMENT2MGTTEMPLATEFRMGRP','MGTTEMPLATEFRMELEMENT','MGTTEMPLATEFRMGRP','MGTTEMPLATEFRMGRPID','ID','M');
commit;

exec p_Insertentityspec('实体管理模板表格元素','MGTTEMPLATETBLELEMENT','com.gxlu.ngrm.metadata.MgtTemplateTblElement','XM_MGTTEMPLATETBLELEMENT','');
exec p_insertentitydescriptor('MGTTEMPLATETBLELEMENT','XM_MGTTEMPLATETBLELEMENT');

exec p_insertM2OneRelation('实体管理模板表格元素对应的实体管理模版表格','MGTTEMPLATETBLELEMENT2MGTTEMPLATETBL','MGTTEMPLATETBLELEMENT','MGTTEMPLATETBL','MGTTEMPLATETBLID','ID','M');
exec p_insertM2OneRelation('实体管理模板表格元素对应的管理模版表单属性','MGTTEMPLATETBLELEMENT2MGTTEMPLATEATTR','MGTTEMPLATETBLELEMENT','MGTTEMPLATEATTR','MGTTEMPLATEATTRID','ID','M');
commit;

--by liurr at 20091210
EXEC p_insertentitydescriptor('SERVICEORDER','X_SERVICEORDER');
COMMIT;

--by liurr at 20091214
exec p_Insertentityspec('割接用户导入','MIGRATIONPRODIMPORT','com.gxlu.ngrm.resService.MigrationProdImport','XB_MIGRATIONPRODIMPORT','');
exec p_insertentitydescriptor('MIGRATIONPRODIMPORT','XB_MIGRATIONPRODIMPORT');
commit;
exec p_insertentitydescriptor('MODIFYDETAIL','XB_MODIFYDETAIL');
commit;

--by liurr at 20091215
EXEC p_insertentitydescriptor('MODIFYDETAIL','XB_MODIFYDETAIL');
commit;

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '原XDSL数据横列端子ID' 
       WHERE A.ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MODIFYDETAIL')
             AND A.COLUMNNAME = 'OLDXDSLROWCONID';
UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '新XDSL数据横列端子ID' 
       WHERE A.ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MODIFYDETAIL')
             AND A.COLUMNNAME = 'NEWXDSLROWCONID';
COMMIT;

exec p_insertM2OneRelation('改资源中间表对应的原XDSL数据横列端子','MODIFYDETAIL2OLDXDSLROWCON','MODIFYDETAIL','ECONNECTOR','OLDXDSLROWCONID','ID','B');
exec p_insertM2OneRelation('改资源中间表对应的原XDSL语音横列端子','MODIFYDETAIL2OLDXDSLROWCON2','MODIFYDETAIL','ECONNECTOR','OLDXDSLROWCONID2','ID','B');
exec p_insertM2OneRelation('改资源中间表对应的新XDSL数据横列端子','MODIFYDETAIL2NEWXDSLROWCON','MODIFYDETAIL','ECONNECTOR','NEWXDSLROWCONID','ID','B');
exec p_insertM2OneRelation('改资源中间表对应的新XDSL语音横列端子','MODIFYDETAIL2NEWXDSLROWCON2','MODIFYDETAIL','ECONNECTOR','NEWXDSLROWCONID2','ID','B');
COMMIT;

--by liurr at 20091215
exec p_Insertentityspec('消息重发','INTF_RESENT','com.gxlu.ngrm.intf.Resent','X_INTF_RESENT','');
exec p_insertentitydescriptor('INTF_RESENT','X_INTF_RESENT');
commit;


--by wxy 20091215
exec p_Insertentityspec('组件管理模版引用','COMPTEMPLATEREF','com.gxlu.ngrm.metadata.CompTemplateRef','XM_COMPTEMPLATEREF','');
exec p_insertentitydescriptor('COMPTEMPLATEREF','XM_COMPTEMPLATEREF');
commit;

exec p_insertM2OneRelation('组件管理模版引用对应的实体管理模版','COMPTEMPLATEREF2ENTITYMGTTEMPLATE','COMPTEMPLATEREF','ENTITYMGTTEMPLATE','ENTITYMGTTEMPLATEID','ID','M');
exec p_insertM2OneRelation('组件管理模版引用对应的组件管理模版','COMPTEMPLATEREF2COMPMGTTEMPLATE','COMPTEMPLATEREF','COMPMGTTEMPLATE','COMPMGTTEMPLATEID','ID','M');
Commit;

Update Xm_Entityspec Set Parentid = (Select Id From Xm_Entityspec Where Code = 'TRAIL') Where Code = 'LCHANNEL';
Commit;

--by liurr at 20091216
UPDATE XM_ENTITYSPEC A SET A.CODE = 'INTF_RESEND', A.CORETABLENAME = 'X_INTF_RESEND', 
       A.CTSEQUENCE = 'S_X_INTF_RESEND',A.CLASSNAME = 'com.gxlu.ngrm.intf.Resend' where code = 'INTF_RESENT';
COMMIT;
UPDATE XM_ENTITYDESCRIPTOR B SET B.DICCLASSNAME = 'INTF_RESEND',B.ATTRDISPNAME = '状态'
       WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'INTF_RESEND') AND COLUMNNAME = 'STAUTS';
COMMIT;

--by liurr at 20091216
EXEC p_insertentitydescriptor('MODIFYDETAIL','XB_MODIFYDETAIL');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '原XDSL数据横列端子' 
       WHERE A.ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MODIFYDETAIL')
             AND A.COLUMNNAME = 'OLDXDSLROWCON';
UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '新XDSL数据横列端子' 
       WHERE A.ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MODIFYDETAIL')
             AND A.COLUMNNAME = 'NEWXDSLROWCON';
COMMIT;

--by liurr at 20091218
DELETE FROM XM_ENTITYDESCRIPTOR A
 WHERE ENTITYSPECID =
       (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MODIFYDETAIL')
   AND A.COLUMNNAME IN
       ('OLDLOGICALCODE', 'OLDLOGICALCODEID', 'OLDLANFACILITYCATEGORY',
        'OLDLANFACILITYCODE', 'OLDLANCON', 'OLDONU', 'OLDONUID',
        'OLDONUPORTCATEGORY', 'OLDONUPORT', 'OLDONUPORTID',
        'OLDONUFACILITYCATEGORY', 'OLDONUFACILITYCODE', 'OLDONUCON',
        'NEWLOGICALCODE', 'NEWLOGICALCODEID', 'NEWLANFACILITYCATEGORY',
        'NEWLANFACILITYCODE', 'NEWLANCON', 'NEWONU', 'NEWONUID',
        'NEWONUPORTCATEGORY', 'NEWONUPORT', 'NEWONUPORTID',
        'NEWONUFACILITYCATEGORY', 'NEWONUFACILITYCODE', 'NEWONUCON');
COMMIT;

EXEC p_insertentitydescriptor('MODIFYDETAIL','XB_MODIFYDETAIL');
COMMIT;

exec p_insertM2OneRelation('改资源中间表对应的原电话关联MDF','MODIFYDETAIL2OLDDEVICEMDF','MODIFYDETAIL','MANAGEDELEMENT','OLDDEVICEMDFID','ID','B');
exec p_insertM2OneRelation('改资源中间表对应的新电话关联MDF','MODIFYDETAIL2NEWDEVICEMDF','MODIFYDETAIL','MANAGEDELEMENT','NEWDEVICEMDFID','ID','B');
COMMIT;

--by wxy 20091217
exec p_insertentitydescriptor('USER','X_USER');
commit;

Update XM_ENTITYSPEC D Set D.CORETABLENAME='XM_COMPONENTDESCRIPTOR' Where D.CORETABLENAME='XM_COMPONENTDESCRIPTOR';
Commit;
exec p_insertentitydescriptor('COMPONENTDESCRIPTOR','XM_COMPONENTDESCRIPTOR');
Commit;

--by wxy 20091223
Exec p_insertentitydescriptor('MANAGEDELEMENT','XB_MANAGEDELEMENT');
Commit;

--by liurr at 20091223
EXEC p_insertentitydescriptor('MODIFYDETAIL','XB_MODIFYDETAIL');
COMMIT;

--by liurr at 20091224
EXEC p_insertentitydescriptor('TASK','XB_TASK');
COMMIT;

--by liurr at 20091224
UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = 'dsl连接方式'
 WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'PRODINS')
   AND A.COLUMNNAME = 'CONNECTMETHOD';
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '物理号码变更'
 WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONBATCH')
   AND A.COLUMNNAME = 'PHY_NBR_FLAG';
COMMIT;

--by liurr at 20091224
EXEC p_insertentitydescriptor('MIGRATIONBATCH','XB_MIGRATIONBATCH');
COMMIT;

--by liurr at 20091225
exec p_Insertentityspec('更改资源激活参数表','MODIFYPARAM','com.gxlu.ngrm.resService.ModifyParam','XB_MODIFYPARAM','');
exec p_insertentitydescriptor('MODIFYPARAM','XB_MODIFYPARAM');
commit;
exec p_insertM2OneRelation('更改资源激活参数表对应的产品实例','MODIFYPARAM2PRODINS','MODIFYPARAM','PRODINS','PRODINSID','ID','B');
COMMIT;

--by liurr at 20091226
EXEC p_insertentitydescriptor('CODE','XB_CODE');
COMMIT;

--by liurr at 20091228
EXEC p_insertentitydescriptor('PHYCODECONFIG','XS_PHYCODECONFIG');
COMMIT;

--by liurr at 20091228
EXEC p_insertentitydescriptor('MODIFYDETAIL','XB_MODIFYDETAIL');
COMMIT;

--by wxy 20091227
Update Xm_Relationspec
  Set Supplierattr = 'CREATORID'
 Where Code = 'PARTYROLE2CREATOR'
  And Supplierattr = 'USERID';
  
Update Xm_Entitydescriptor
  Set Columnname = 'CREATORID', Attrname = 'CREATORID'
 Where Entityspecid = (Select Id From Xm_Entityspec Where Code = 'PARTYROLE')
  And Columnname = 'USERID';
Commit;
 
exec p_insertM2OneRelation('资源服务规格所属营销区','RESSERVICESPEC2MARKETINGAREA','RESSERVICESPEC','MARKETINGAREA','LOCATIONID','ID','');
Commit;

--by liurr at 20091229
EXEC p_insertentitydescriptor('CUSTOMERMANAGER','X_CUSTOMERMANAGER');
EXEC p_insertentitydescriptor('MGRCUSTASSIGN','X_MGRCUSTASSIGN');
commit;

UPDATE xm_entitydescriptor A SET A.DICCLASSNAME = 'CUSTOMERMANAGER',A.DICATTRIBUTENAME = 'SYSFLAG' 
WHERE A.ENTITYSPECID = (select id from xm_entityspec where code = 'MGRCUSTASSIGN')
      AND A.COLUMNNAME = 'SYSFLAG';
commit;   

--by liurr at 20091229
EXEC p_insertentitydescriptor('PORT','XB_PORT');
COMMIT;

--by liurr at 20091229
UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '原xDSL节点机拼装编码' 
WHERE A.ENTITYSPECID= (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONRESULTDETAIL') 
AND A.COLUMNNAME = 'OLDDSLAM';

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '新xDSL节点机拼装编码' 
WHERE A.ENTITYSPECID= (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONRESULTDETAIL') 
AND A.COLUMNNAME = 'NEWDSLAM';

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '原xDSL端口编码' 
WHERE A.ENTITYSPECID= (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONRESULTDETAIL') 
AND A.COLUMNNAME = 'OLDXDSLPORT';

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '新xDSL端口编码' 
WHERE A.ENTITYSPECID= (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONRESULTDETAIL') 
AND A.COLUMNNAME = 'NEWXDSLPORT';

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '原LAN节点机编码' 
WHERE A.ENTITYSPECID= (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONRESULTDETAIL') 
AND A.COLUMNNAME = 'OLDLANSWITCH';

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '新LAN节点机编码' 
WHERE A.ENTITYSPECID= (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONRESULTDETAIL') 
AND A.COLUMNNAME = 'NEWLANSWITCH';

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '原LAN端口拼装编码' 
WHERE A.ENTITYSPECID= (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONRESULTDETAIL') 
AND A.COLUMNNAME = 'OLDLANPORT';

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '新LAN端口拼装编码' 
WHERE A.ENTITYSPECID= (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONRESULTDETAIL') 
AND A.COLUMNNAME = 'NEWLANPORT';

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '原交换机拼装编码' 
WHERE A.ENTITYSPECID= (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONRESULTDETAIL') 
AND A.COLUMNNAME = 'OLDSWITCH';

UPDATE XM_ENTITYDESCRIPTOR A SET A.ATTRDISPNAME = '新交换机拼装编码' 
WHERE A.ENTITYSPECID= (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONRESULTDETAIL') 
AND A.COLUMNNAME = 'NEWSWITCH';
COMMIT;

--by liurr at 20091230
EXEC p_insertcomponentdescriptor('LAN','XC_LAN');
COMMIT;

--by liurr at 20091231
EXEC p_insertentitydescriptor('MODIFYDETAIL','XB_MODIFYDETAIL');
COMMIT;

--by wxy 20100102
Exec p_insertentitydescriptor('CODEPOOL','X_CODEPOOL');
Commit;

--by wxy 20100105
Exec p_insertentitydescriptor('PRODINS','XB_PRODINS');
Commit;

Exec p_insertentitydescriptor('ADDRESS','XB_ADDRESS');
Commit;

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

--by wxy 20100107
Alter Table XM_MGTTEMPLATETBL Rename Column Verion To Version;
Comment On Column XM_MGTTEMPLATETBL.VERSION                 Is '版本';

Update Xm_Entitydescriptor
	 Set Columnname = 'VERSION', Attrname = 'VERSION'
 Where Columnname = 'VERION'
	 And Entityspecid = (Select Id From Xm_Entityspec Where Code = 'MGTTEMPLATETBL');
Commit;

--by wxy 20100108
Exec p_insertentitydescriptor('TASK','XB_TASK');
Commit;
 
exec p_insertM2OneRelation('任务对应的GIS局向','TASK2BSITE','TASK','BSITE','BSITEID','ID','B');
Commit;
 
 Exec p_insertentitydescriptor('SITE','XS_SITE');
Exec p_insertentitydescriptor('OUTDOORADDRESS','XS_OUTDOORADDRESS');
Commit;
 
exec p_insertM2OneRelation('局站所属维护管理区','SITE2MANAGEMENTAREA','SITE','MANAGEMENTAREA','MANAGEMENTAREAID','ENTITYID','S');
exec p_insertM2OneRelation('户外安装站点所属维护管理区','OUTDOORADDR2MANAGEMENTAREA','OUTDOORADDRESS','MANAGEMENTAREA','MANAGEMENTAREAID','ENTITYID','S');
Commit;
 
Delete From Xm_Entityspec2relationspec d
 Where d.Relationspecid = (Select Id From Xm_Relationspec r Where r.Code = 'LOCATION2MANAGEMENTAREA');
Delete From x_Versioninstance d Where d.Subcategory = 'LOCATION2MANAGEMENTAREA';
Delete From Xm_Relationspec Where Code = 'LOCATION2MANAGEMENTAREA';
Commit;
 
Delete From Xm_Entitydescriptor d
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'LOCATION')
	 And d.Columnname = 'MANAGEMENTAREAID';
Commit;

--by wxy 20100111
Exec p_insertentitydescriptor('MODIFYDETAIL','XB_MODIFYDETAIL');
Commit;

Update Xm_Entitydescriptor d
	 Set d.Dicclassname = 'COMMON', d.Dicattributename = 'BOOLEAN'
 Where d.Entityspecid = (Select Id From Xm_Entityspec Where Code = 'MODIFYDETAIL')
	 And d.Columnname = 'ARCHIVEFLAG';
Commit;

--
Delete From Xm_Entityspec2relationspec d
 Where d.Relationspecid = (Select Id From Xm_Relationspec r Where r.Code = 'CHANNEL2MANAGEMENTAREA');
Delete From x_Versioninstance d Where d.Subcategory = 'CHANNEL2MANAGEMENTAREA';
Delete From Xm_Relationspec Where Code = 'CHANNEL2MANAGEMENTAREA';
Commit;

exec p_insertM2OneRelation('放号区对应的营销区','CHANNEL2MARKETINGAREA','CHANNEL','MARKETINGAREA','MARKETINGAREAID','ENTITYID','S');
Commit;

--by wxy 20100119
/*Select *
	From Xm_Entityspec
 Where Parentid Is Null
	 And Id Not In (Select Entityspecid From Xm_Entitydescriptor Where Columnname = 'ID')
*/

Update Xm_Entityspec
	 Set Parentid = (Select Id From Xm_Entityspec Where Code = 'TASK')
 Where Code In ('LINKCONFIG');
Commit;
Update Xm_Entityspec
	 Set Parentid = (Select Id From Xm_Entityspec Where Code = 'SUBNETWORK')
 Where Code In ('TRANSPORTGROUP');
Commit;

--
Delete From Xm_Entitymgttemplate
 Where Entityspecid = (Select Id From Xm_Entityspec Where Code = 'SUBNETWORKCONNECTION');
Delete From xm_entityspec Where code='SUBNETWORKCONNECTION';
Delete From x_versioninstance v Where v.entityid = (Select Id From xm_entityspec Where code='SUBNETWORKCONNECTION');
Commit;

exec p_Insertentitydescriptor('DISTRSYS2BTSCONSTRAINT','XB_DISTRSYS2BTSCONSTRAINT');
Commit;

--
Delete From xm_entityspec2relationspec Where relationspecid=(Select Id From xm_relationspec Where code='LAYERRATENAMINGRULE2MAPRULE');

Delete From xm_relationspec Where code='LAYERRATENAMINGRULE2MAPRULE';
Delete From x_versioninstance Where subcategory = 'LAYERRATENAMINGRULE2MAPRULE';

Delete From xm_entityDescriptor Where entityspecId = (Select Id From xm_entityspec Where code='LAYERRATENAMINGRULE');
Delete From xm_entityspec Where code='LAYERRATENAMINGRULE';
Delete From x_versioninstance Where subcategory = 'LAYERRATENAMINGRULE';
commit;


--by liurr at 20100120
exec p_insertentitydescriptor('CODELEVEL','X_CODELEVEL');
COMMIT;

--by liurr 20100120
Exec p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
Exec p_insertentitydescriptor('MIGRATIONIMPORT','XB_MIGRATIONIMPORT');
Exec p_insertentitydescriptor('MIGRATIONLOG','XB_MIGRATIONLOG');
Commit;

Update Xm_Entitydescriptor d
  Set d.Attrdispname = '原交换机拼装编码'
 Where d.Columnname = 'OLDSWITCH'
  And d.Entityspecid In
    (Select Id From Xm_Entityspec Where Code In ('MIGRATIONRESULTDETAIL', 'MIGRATIONIMPORT', 'MIGRATIONLOG')); 
Commit;
Update Xm_Entitydescriptor d
  Set d.Attrdispname = '原PSTN端口拼装编码'
 Where d.Columnname = 'OLDPSTNPORT'
  And d.Entityspecid In
    (Select Id From Xm_Entityspec Where Code In ('MIGRATIONRESULTDETAIL', 'MIGRATIONIMPORT', 'MIGRATIONLOG')); 
Commit;
Update Xm_Entitydescriptor d
  Set d.Attrdispname = '原xDSL节点机拼装编码'
 Where d.Columnname = 'OLDDSLAM'
  And d.Entityspecid In
    (Select Id From Xm_Entityspec Where Code In ('MIGRATIONRESULTDETAIL', 'MIGRATIONIMPORT', 'MIGRATIONLOG')); 
Commit;
Update Xm_Entitydescriptor d
  Set d.Attrdispname = '原xDSL端口拼装编码'
 Where d.Columnname = 'OLDXDSLPORT'
  And d.Entityspecid In
    (Select Id From Xm_Entityspec Where Code In ('MIGRATIONRESULTDETAIL', 'MIGRATIONIMPORT', 'MIGRATIONLOG')); 
Commit;
Update Xm_Entitydescriptor d
  Set d.Attrdispname = '原LAN节点机拼装编码'
 Where d.Columnname = 'OLDLANSWITCH'
  And d.Entityspecid In
    (Select Id From Xm_Entityspec Where Code In ('MIGRATIONRESULTDETAIL', 'MIGRATIONIMPORT', 'MIGRATIONLOG')); 
Commit;
Update Xm_Entitydescriptor d
  Set d.Attrdispname = '原LAN端口拼装编码'
 Where d.Columnname = 'OLDLANPORT'
  And d.Entityspecid In
    (Select Id From Xm_Entityspec Where Code In ('MIGRATIONRESULTDETAIL', 'MIGRATIONIMPORT', 'MIGRATIONLOG')); 
Commit;
Update Xm_Entitydescriptor d
  Set d.Attrdispname = '新交换机拼装编码'
 Where d.Columnname = 'NEWSWITCH'
  And d.Entityspecid In
    (Select Id From Xm_Entityspec Where Code In ('MIGRATIONRESULTDETAIL', 'MIGRATIONIMPORT', 'MIGRATIONLOG')); 
Commit;
Update Xm_Entitydescriptor d
  Set d.Attrdispname = '新PSTN端口拼装编码'
 Where d.Columnname = 'NEWPSTNPORT'
  And d.Entityspecid In
    (Select Id From Xm_Entityspec Where Code In ('MIGRATIONRESULTDETAIL', 'MIGRATIONIMPORT', 'MIGRATIONLOG')); 
Commit;
Update Xm_Entitydescriptor d
  Set d.Attrdispname = '新xDSL节点机拼装编码'
 Where d.Columnname = 'NEWDSLAM'
  And d.Entityspecid In
    (Select Id From Xm_Entityspec Where Code In ('MIGRATIONRESULTDETAIL', 'MIGRATIONIMPORT', 'MIGRATIONLOG')); 
Commit;
Update Xm_Entitydescriptor d
  Set d.Attrdispname = '新xDSL端口拼装编码'
 Where d.Columnname = 'NEWXDSLPORT'
  And d.Entityspecid In
    (Select Id From Xm_Entityspec Where Code In ('MIGRATIONRESULTDETAIL', 'MIGRATIONIMPORT', 'MIGRATIONLOG')); 
Commit;
Update Xm_Entitydescriptor d
  Set d.Attrdispname = '新LAN节点机拼装编码'
 Where d.Columnname = 'NEWLANSWITCH'
  And d.Entityspecid In
    (Select Id From Xm_Entityspec Where Code In ('MIGRATIONRESULTDETAIL', 'MIGRATIONIMPORT', 'MIGRATIONLOG')); 
Commit;
Update Xm_Entitydescriptor d
  Set d.Attrdispname = '新LAN端口拼装编码'
 Where d.Columnname = 'NEWLANPORT'
  And d.Entityspecid In
    (Select Id From Xm_Entityspec Where Code In ('MIGRATIONRESULTDETAIL', 'MIGRATIONIMPORT', 'MIGRATIONLOG')); 
Commit;

--by liurr at 20100120
exec p_Insertentityspec('割接日志详情','MIGLOG','com.gxlu.ngrm.resService.MigLog','X_MIGLOG','');
exec p_insertentitydescriptor('MIGLOG','X_MIGLOG');
commit;

--by liurr at 20100127
EXEC p_insertentitydescriptor('CODEPOOL','X_CODEPOOL');
COMMIT;

--by liurr at 20100203
UPDATE XM_ENTITYDESCRIPTOR A SET A.DATALENGTH = 2000 WHERE 
ENTITYSPECID = (select ID from xm_entityspec where code = 'SERVICEORDER')
AND A.COLUMNNAME = 'COMMENTS';

UPDATE XM_ENTITYDESCRIPTOR A SET A.DATALENGTH = 1000 WHERE 
ENTITYSPECID = (select ID from xm_entityspec where code = 'CHARACTERVALUE')
AND A.COLUMNNAME = 'ATTRVALUESTRING';
COMMIT;

--by wxy 20100209
Exec p_insertentitydescriptor('ROUTEEND','XB_ROUTEEND');
Commit;

--by liurr at 20100310
EXEC p_insertentitydescriptor('FTTHCONFIG','XS_FTTHCONFIG');
COMMIT;

EXEC p_insertcomponentdescriptor('ONU','XC_ONU');
COMMIT;

--by liurr at 20100323
EXEC p_insertentitydescriptor('TASK','XB_TASK');
COMMIT;

update XM_ENTITYDESCRIPTOR d set d.DICCLASSNAME='ADSLCONFIG' ,d.DICATTRIBUTENAME='ISHIGHSPEED' 
 where d.ENTITYSPECID=(select id from XM_ENTITYSPEC e where e.CODE='ADSLCONFIG') and d.COLUMNNAME='ISHIGHSPEED';
COMMIT;

EXEC p_insertentitydescriptor('PRODSERVSPECSHARE','XB_PRODSERVSPECSHARE');
COMMIT;

--by wxy 20100323
EXEC P_INSERTENTITYDESCRIPTOR('ENTITYMGTTEMPLATERELATION','XM_ENTITYMGTTEMPLATERELATION');
COMMIT;

--by liurr at 20100330
exec p_Insertentityspec('C网产品目录/规格','PRODUCTSPEC','com.gxlu.ngrm.resService.ProductSpec','XO_PRODUCTSPEC','');
exec p_insertentitydescriptor('PRODUCTSPEC','XO_PRODUCTSPEC');
commit;  

exec p_Insertentityspec('C网产品实例','PRODUCTINSTANCE','com.gxlu.ngrm.resService.ProductInstance','XO_PRODUCTINSTANCE','');
exec p_insertentitydescriptor('PRODUCTINSTANCE','XO_PRODUCTINSTANCE');
commit;

EXEC p_insertentitydescriptor('PRODUCTINSRELATION','XB_PRODUCTINSRELATION');
COMMIT;

exec p_insertM2OneRelation('产品实例群组关系表对应的C网产品实例','PRODUCTINSRELATION2PRODUCTINSTANCE','PRODUCTINSRELATION','PRODUCTINSTANCE','C_RELAPRODINSID','ID','B');
Commit;

--by liurr at 2010.4.6
exec p_Insertentityspec('公告栏','MSGBOARD','com.gxlu.ngrm.basic.MsgBoard','XB_MSGBOARD','');
exec p_insertentitydescriptor('MSGBOARD','XB_MSGBOARD');
commit;

exec p_Insertentityspec('公告话题','MSGTOPIC','com.gxlu.ngrm.basic.MsgTopic','XB_MSGTOPIC','');
exec p_insertentitydescriptor('MSGTOPIC','XB_MSGTOPIC');
commit;

exec p_insertM2OneRelation('公告话题对应的公告栏','MSGTOPIC2MSGBOARD','MSGTOPIC','MSGBOARD','MSGBOARDID','ID','B');
COMMIT;

exec p_Insertentityspec('公告话题回复详情','MSGTOPICREP','com.gxlu.ngrm.basic.MsgTopicRep','XB_MSGTOPICREP','');
exec p_insertentitydescriptor('MSGTOPICREP','XB_MSGTOPICREP');
commit;

exec p_insertM2OneRelation('公告话题回复详情对应的公告话题','MSGTOPICREP2MSGTOPIC','MSGTOPICREP','MSGTOPIC','MSGTOPICID','ID','B');
COMMIT;

exec p_Insertentityspec('公告派发','MSGASSIGN','com.gxlu.ngrm.basic.MsgAssign','XB_MSGASSIGN','');
exec p_insertentitydescriptor('MSGASSIGN','XB_MSGASSIGN');
commit;

exec p_insertM2OneRelation('公告派发对应的公告栏','MSGASSIGN2MSGBOARD','MSGASSIGN','MSGBOARD','MSGBOARDID','ID','B');
exec p_insertM2OneRelation('公告派发对应的用户','MSGASSIGN2USER','MSGASSIGN','USER','USERID','ID','B');
COMMIT;

--by liurr at 20100408
EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'PRODINS', A.DICATTRIBUTENAME = 'CONNECTMETHOD' WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONRESULTDETAIL') AND A.COLUMNNAME = 'CONNECTMETHOD';
UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'PRODINS', A.DICATTRIBUTENAME = 'PRODUCTTYPE' WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONRESULTDETAIL') AND A.COLUMNNAME = 'PRODUCTTYPE';
COMMIT;

--by liurr at 20100408
exec p_Insertentityspec('角色','ROLE','com.gxlu.ngrm.security.Role','X_ROLE','');
exec p_insertentitydescriptor('ROLE','X_ROLE');
commit; 

exec p_Insertentityspec('组织机构','ORGANIZATION','com.gxlu.ngrm.security.Organization','ORGANIZATION','');
exec p_insertentitydescriptor('ORGANIZATION','ORGANIZATION');
commit;  

--by liurr at 20100409
update xm_relationspec b set b.ownerattr = 'ID' , b.supplierattr = 'AUDITOBJID' where b.code = 'AUDITRESULTPARAM2AUDITOBJ';
commit;

DELETE FROM XM_ENTITYDESCRIPTOR 
  WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'AUDITRECORD')
		AND COLUMNNAME = 'OBJCATEGORY';
COMMIT;

EXEC P_INSERTENTITYDESCRIPTOR('AUDITRECORD','X_AUDITRECORD');
COMMIT;

--by liurr at 20100413
EXEC p_insertentitydescriptor('MIGRATIONIMPORT','XB_MIGRATIONIMPORT');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'PRODINS', A.DICATTRIBUTENAME = 'CONNECTMETHOD' WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONIMPORT') AND A.COLUMNNAME = 'CONNECTMETHOD';
UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'PRODINS', A.DICATTRIBUTENAME = 'PRODUCTTYPE' WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONIMPORT') AND A.COLUMNNAME = 'PRODUCTTYPE';
COMMIT;

--by liurr at 20100414
DELETE FROM XM_ENTITYDESCRIPTOR A WHERE A.ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONRESULTDETAIL')
AND A.COLUMNNAME IN ('CONNECTMETHOD','PRODUCTTYPE');

DELETE FROM XM_ENTITYDESCRIPTOR A WHERE A.ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MIGRATIONIMPORT')
AND A.COLUMNNAME IN ('CONNECTMETHOD','PRODUCTTYPE');
COMMIT;

EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
EXEC p_insertentitydescriptor('MIGRATIONIMPORT','XB_MIGRATIONIMPORT');
COMMIT;

--by liurr at 20100419
EXEC p_insertentitydescriptor('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
EXEC p_insertentitydescriptor('MIGRATIONIMPORT','XB_MIGRATIONIMPORT');
COMMIT;

--by liurr at 20100420
EXEC p_insertentitydescriptor('MANAGEDELEMENT','XB_MANAGEDELEMENT');
commit;

EXEC p_insertentitydescriptor('TASK','XB_TASK');
commit;

EXEC p_insertentitydescriptor('ONUMODELINI','XB_ONUMODELINI');
commit;

EXEC p_insertentitydescriptor('CODE','XB_CODE');
commit;

DELETE FROM XM_ENTITYDESCRIPTOR B WHERE B.ENTITYSPECID IN (SELECT ID FROM XM_ENTITYSPEC WHERE CODE IN ('PSTNLOGICALCODE','PHSLOGICALCODE'))
AND B.COLUMNNAME = 'AREACODE';
COMMIT;

--by liurr at 20100420
EXEC p_insertentitydescriptor('PRODINS','XB_PRODINS');
COMMIT;

--by liurr at 20100428
exec p_Insertentityspec('万位号段','TENTHOUSANDCODESEG','com.gxlu.ngrm.resService.TenThousandCodeSeg','XB_TENTHOUSANDCODESEG','');
exec p_insertentitydescriptor('TENTHOUSANDCODESEG','XB_TENTHOUSANDCODESEG');
commit;

exec p_insertM2OneRelation('万位号段对应的维护管理区','TENTHOUSANDCODESEG2MANAGEMENTAREA','TENTHOUSANDCODESEG','MANAGEMENTAREA','LOCATIONID','ID','B');
COMMIT;

exec p_insertentitydescriptor('CODESEGMENT','X_CODESEGMENT');
commit;

exec p_insertM2OneRelation('号码段对应的万位号段','CODESEGMENT2TENTHOUSANDCODESEG','CODESEGMENT','TENTHOUSANDCODESEG','TENTHOUSANDCODESEGID','ID','');
COMMIT;

--by liurr at 20100429
EXEC p_insertentitydescriptor('PHSSIM','X_CARD');
COMMIT;

--by liurr at 20100507
delete from xm_entitydescriptor a where entityspecid = (select id from xm_entityspec where code = 'SYSPARAMCONFIG') and a.columnname = 'MARKETINGAREAID';
commit;

delete from xm_entityspec2relationspec d where d.relationspecid=(select id from xm_relationspec r where r.code='SYSPARAMCONFIG2MARKETINGAREA');
delete from x_versioninstance d where d.subcategory='SYSPARAMCONFIG2MARKETINGAREA';
delete from xm_relationspec where code='SYSPARAMCONFIG2MARKETINGAREA';
commit;

exec p_insertentitydescriptor('SYSPARAMCONFIG','XB_SYSPARAMCONFIG');
commit;

exec p_insertentitydescriptor('PSTNPHYSICALCODE','XS_PSTNPHYSICALCODE');
commit;
exec p_insertM2OneRelation('PSTN物理号码对应的网元','PSTNPHYSICALCODE2ME','PSTNPHYSICALCODE','MANAGEDELEMENT','MEID','ENTITYID','S');
COMMIT;

--by liurr at 20100510
exec p_insertentitydescriptor('IVPNNO','X_IVPNNO');
commit;

exec p_insertM2OneRelation('虚拟网对应的父虚拟网','IVPNNO2PARENT','IVPNNO','IVPNNO','PARENTID','ID','');
exec p_insertOne2MRelation('虚拟网对应的子虚拟网','IVPNNO2CHILD','IVPNNO','IVPNNO','PARENTID','');
commit;

exec p_insertentitydescriptor('PRODINS','XB_PRODINS');
commit;

--by liurr at 20100510
exec p_Insertentityspec('机型优先配置','MODELPRIORITY','com.gxlu.ngrm.resService.ModelPriority','XB_MODELPRIORITY','');
exec p_insertentitydescriptor('MODELPRIORITY','XB_MODELPRIORITY');
commit;

UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'PRODINS',A.DICATTRIBUTENAME = 'ACCESSMODE' 
WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'MODELPRIORITY') AND A.COLUMNNAME = 'ACCESSMODE';
COMMIT;

--by liurr at 20100510
exec p_insertentitydescriptor('IAD','XS_IAD');
COMMIT;

--by liurr at 20100510
exec p_insertentitydescriptor('ONU','XS_ONU');
commit;

exec p_insertM2OneRelation('ONU对应的OLT','ONU2OLT','ONU','OLT','OLTID','ENTITYID','S');
COMMIT;

--by liurr at 20100510
exec p_insertentitydescriptor('ONUMODELINI','XB_ONUMODELINI');
commit;

--by liurr at 20100512
--将局向的元数据记录parentid设为MARKETINGAREA
update xm_entityspec set parentid = (select id from xm_entityspec where code = 'MARKETINGAREA') where code = 'BSITE';
commit;

--by liurr at 20100513
EXEC p_insertentitydescriptor('IVPNNO','X_IVPNNO');
COMMIT;

--by liurr at 20100517
update xm_entityspec set classname = 'com.gxlu.ngrm.code.TenThousandCodeSeg' where code = 'TENTHOUSANDCODESEG' and classname = 'com.gxlu.ngrm.resService.TenThousandCodeSeg';
commit;

exec p_insertentitydescriptor('TENTHOUSANDCODESEG','XB_TENTHOUSANDCODESEG');
commit;

--by liurr at 20100518
exec p_insertentitydescriptor('LOCATION','XB_LOCATION');
commit;

--by liurr at 20100519
exec p_insertentitydescriptor('MANAGEMENTAREA','XS_MANAGEMENTAREA');
commit;

--added by liurr at 20100519 for 万位号段表locationid表述为所属营销区
update xm_entitydescriptor a set a.attrdispname = '所属营销区ID' 
       where entityspecid = (select ID from xm_entityspec where code = 'TENTHOUSANDCODESEG')
             and a.columnname = 'LOCATIONID' and a.attrdispname = '所属维护管理区ID';
commit;


delete from xm_entityspec2relationspec d where d.relationspecid=(select id from xm_relationspec r where r.code='TENTHOUSANDCODESEG2MANAGEMENTAREA');
delete from x_versioninstance d where d.subcategory='TENTHOUSANDCODESEG2MANAGEMENTAREA';
delete from xm_relationspec where code='TENTHOUSANDCODESEG2MANAGEMENTAREA';
commit;

exec p_insertM2OneRelation('万位号段对应的营销区','TENTHOUSANDCODESEG2MARKETINGAREA','TENTHOUSANDCODESEG','MARKETINGAREA','LOCATIONID','ID','B');
COMMIT;

--by liurr at 20100519
exec p_insertentitydescriptor('MIGRATIONBATCH','XB_MIGRATIONBATCH');
commit;

--by liurr at 20100519
delete from xm_entitydescriptor where entityspecid = (select id from xm_entityspec where code = 'IAD')
and columnname in ('IP','DOMAIN');
commit;

--by liurr at 20100524
EXEC P_INSERTENTITYDESCRIPTOR('MIGRATIONRESULTDETAIL','XB_MIGRATIONRESULTDETAIL');
COMMIT;
EXEC P_INSERTENTITYDESCRIPTOR('MODIFYDETAIL','XB_MODIFYDETAIL');
COMMIT;

--by liurr at 20100531
exec p_insertentitydescriptor('MODIFYDETAIL','XB_MODIFYDETAIL');
commit;

--by liurr at 20100621
exec p_insertentitydescriptor('INTF_AREAROUTEMAPPING','X_INTF_AREAROUTEMAPPING');
commit;
exec p_insertM2OneRelation('区域路由码映射对应的C5营销区域','AREAROUTEMAPPING2C5MARKETINGAREA','INTF_AREAROUTEMAPPING','MARKETINGAREA','C5LOCATIONID','ID','');
Commit; 

exec p_insertentitydescriptor('TASK','XB_TASK');
commit;
exec p_insertM2OneRelation('任务对应的C5营销区域','TASK2C5MARKETINGAREA','TASK','MARKETINGAREA','C5LOCATIONID','ID','B');
COMMIT;  

--by liurr at 20100628
update xm_entitydescriptor a set a.columntype = 4, a.datatype = 4, a.datalength = 20
       where entityspecid = (select id from xm_entityspec where code = 'PHYSICALCONTAINER') AND A.COLUMNNAME = 'COLINDEX';
commit;

--by liurr at 20100629
EXEC p_insertentitydescriptor('PHSSIM','X_CARD');
COMMIT;

--by liurr at 20100709
EXEC p_insertEntitySpec('GPON板','GPONCARD','com.gxlu.ngrm.equipment.GPONCard','XB_CARD','XS_GPONCARD');
EXEC p_insertEntitySpec('SPA板','SPACARD','com.gxlu.ngrm.equipment.SPACard','XB_CARD','XS_SPACARD');
commit;

UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='CARD')
WHERE CORETABLENAME ='XB_CARD' and MAINTAININDICATOR != 1 AND PARENTID IS NULL;
commit;

--by liurr at 20100715
EXEC p_insertentitydescriptor('SERVICEORDER','X_SERVICEORDER');
COMMIT;

--by liurr at 20100724
EXEC p_insertEntitySpec('防雷板','LIGHTNINGPROTECT','com.gxlu.ngrm.equipment.LightningProtectCard','XB_CARD','XS_LIGHTNINGPROTECT');
EXEC p_insertEntitySpec('双音驱动板','DUALSOUNDDRIVECARD','com.gxlu.ngrm.equipment.DualToneDriveCard','XB_CARD','XS_DUALTONEDRIVECARD');
EXEC p_insertEntitySpec('双音收发板','DUALTONETRANSCEIVINGCARD','com.gxlu.ngrm.equipment.DualToneTransceivingCard','XB_CARD','XS_DUALTONETRANSCEIVINGCARD');
EXEC p_insertEntitySpec('双机倒换板','TWOCOMPUTERROTATECARD','com.gxlu.ngrm.equipment.TwoComputerRotateCard','XB_CARD','XS_TWOCOMPUTERROTATECARD');
EXEC p_insertEntitySpec('监控板','MONITORCARD','com.gxlu.ngrm.equipment.MonitorCard','XB_CARD','XS_MONITORCARD');
EXEC p_insertEntitySpec('处理板','PROCESSORCARD','com.gxlu.ngrm.equipment.ProcessorCard','XB_CARD','XS_PROCESSORCARD');
EXEC p_insertEntitySpec('通信板','COMMUNICATIONCARD','com.gxlu.ngrm.equipment.CommunicationCard','XB_CARD','XS_COMMUNICATIONCARD');
EXEC p_insertEntitySpec('测试板','TESTCARD','com.gxlu.ngrm.equipment.TestCard','XB_CARD','XS_TESTCARD');
EXEC p_insertEntitySpec('信号音板','SIGNALTONECARD','com.gxlu.ngrm.equipment.SignalToneCard','XB_CARD','XS_SIGNALTONECARD');
EXEC p_insertEntitySpec('智能语音板','INTELLIGENTVOICECARD','com.gxlu.ngrm.equipment.IntelligentVoiceCard','XB_CARD','XS_INTELLIGENTVOICECARD');
EXEC p_insertEntitySpec('共享资源板','SHARERESOURCECARD','com.gxlu.ngrm.equipment.ShareResourceCard','XB_CARD','XS_SHARERESOURCECARD');
EXEC p_insertEntitySpec('适配板','ADAPTIVECARD','com.gxlu.ngrm.equipment.AdaptiveCard','XB_CARD','XS_ADAPTIVECARD');
EXEC p_insertEntitySpec('GPS板','GPSCARD','com.gxlu.ngrm.equipment.GPSCard','XB_CARD','XS_GPSCARD');
EXEC p_insertEntitySpec('T1/E1接口板','T1E1INTERFACECARD','com.gxlu.ngrm.equipment.T1E1InterfaceCard','XB_CARD','XS_T1E1INTERFACECARD');
EXEC p_insertEntitySpec('无线接入板','WIRELESSACCESSCARD','com.gxlu.ngrm.equipment.WirelessAccessCard','XB_CARD','XS_WIRELESSACCESSCARD');
EXEC p_insertEntitySpec('双工器','DUPLEXERCARD','com.gxlu.ngrm.equipment.DuplexerCard','XB_CARD','XS_DUPLEXERCARD');
EXEC p_insertEntitySpec('滤波器','FILTERCARD','com.gxlu.ngrm.equipment.FilterCard','XB_CARD','XS_FILTERCARD');
EXEC p_insertEntitySpec('频率参数卡','FREQUENCYPARAMETERCARD','com.gxlu.ngrm.equipment.FrequencyParameterCard','XB_CARD','XS_FREQUENCYPARAMETERCARD');
EXEC p_insertEntitySpec('模块内通信板','INNERMODULECOMMUNICATECARD','com.gxlu.ngrm.equipment.InnermoduleCommunicateCard','XB_CARD','XS_INNERMODULECOMMUNICATECARD');
EXEC p_insertEntitySpec('外围单元处理器板','PERIPHERALCELLPROCESSORCARD','com.gxlu.ngrm.equipment.PeripheralCellProcessorCard','XB_CARD','XS_PERIPHERALCELLPROCESSORCARD');
EXEC p_insertEntitySpec('网元控制处理板','NECONTROLPROCESSORCARD','com.gxlu.ngrm.equipment.NEControlProcessorCard','XB_CARD','XS_NECONTROLPROCESSORCARD');
EXEC p_insertEntitySpec('主处理机系统','HOSTPROCESSORSYSTEMCARD','com.gxlu.ngrm.equipment.HostProcessorSystemCard','XB_CARD','XS_HOSTPROCESSORSYSTEMCARD');
EXEC p_insertEntitySpec('信令和交换单元板','SIGNALLINGSWITCHUNITCARD','com.gxlu.ngrm.equipment.SignallingSwitchUnitCard','XB_CARD','XS_SIGNALLINGSWITCHUNITCARD');
EXEC p_insertEntitySpec('模块间通信板','INTERMODULECOMMUNICATECARD','com.gxlu.ngrm.equipment.IntermoduleCommunicateCard','XB_CARD','XS_INTERMODULECOMMUNICATECARD');
EXEC p_insertEntitySpec('数字中继板','DIGITALTRUNKCARD','com.gxlu.ngrm.equipment.DigitalTrunkCard','XB_CARD','XS_DIGITALTRUNKCARD');
EXEC p_insertEntitySpec('资源板','RESOURCECARD','com.gxlu.ngrm.equipment.ResourceCard','XB_CARD','XS_RESOURCECARD');
EXEC p_insertEntitySpec('铃流板','RINGINGCARD','com.gxlu.ngrm.equipment.RingingCard','XB_CARD','XS_RINGINGCARD');
EXEC p_insertEntitySpec('基带无线单元','BASEBANDWIRELESSUNITCARD','com.gxlu.ngrm.equipment.BasebandWirelessUnitCard','XB_CARD','XS_BASEBANDWIRELESSUNITCARD');
EXEC p_insertEntitySpec('功率放大器','POWERAMPLIFIERCARD','com.gxlu.ngrm.equipment.PowerAmplifierCard','XB_CARD','XS_POWERAMPLIFIERCARD');
EXEC p_insertEntitySpec('通用时钟转换单元','GENERALCLOCKTRANSCARD','com.gxlu.ngrm.equipment.GeneralClockTransCard','XB_CARD','XS_GENERALCLOCKTRANSCARD');
EXEC p_insertEntitySpec('通用电源转换单元','GENERALPOWERTRANSCARD','com.gxlu.ngrm.equipment.GeneralPowerTransCard','XB_CARD','XS_GENERALPOWERTRANSCARD');
EXEC p_insertEntitySpec('时钟频率单元','CLOCKFREQUENCYCARD','com.gxlu.ngrm.equipment.ClockFrequencyCard','XB_CARD','XS_CLOCKFREQUENCYCARD');
EXEC p_insertEntitySpec('通用CDMA无线单元','GENERALCDMAWIRELESSCARD','com.gxlu.ngrm.equipment.GeneralCDMAWirelessCard','XB_CARD','XS_GENERALCDMAWIRELESSCARD');
EXEC p_insertEntitySpec('晶振单元','CRYSTALOSCILLATORCARD','com.gxlu.ngrm.equipment.CrystalOscillatorCard','XB_CARD','XS_CRYSTALOSCILLATORCARD');
EXEC p_insertEntitySpec('光接口模块','OIMCARD','com.gxlu.ngrm.equipment.OIMCard','XB_CARD','XS_OIMCARD');
EXEC p_insertEntitySpec('通用无线控制器','GENERALRADIOCONTROLCARD','com.gxlu.ngrm.equipment.GeneralRadioControlCard','XB_CARD','XS_GENERALRADIOCONTROLCARD');
EXEC p_insertEntitySpec('输入/输出单元','IOUNITCARD','com.gxlu.ngrm.equipment.IOUnitCard','XB_CARD','XS_IOUNITCARD');
EXEC p_insertEntitySpec('分组控制功能单元','PCFUNITCARD','com.gxlu.ngrm.equipment.PCFUnitCard','XB_CARD','XS_PCFUNITCARD');
EXEC p_insertEntitySpec('BSC控制设备接口板','BSCCUDICARD','com.gxlu.ngrm.equipment.BSCCUDICard','XB_CARD','XS_BSCCUDICARD');
EXEC p_insertEntitySpec('CM板','CMCARD','com.gxlu.ngrm.equipment.CMCard','XB_CARD','XS_CMCARD');
EXEC p_insertEntitySpec('DO信道板','DONCHPCARD','com.gxlu.ngrm.equipment.DONCHPCard','XB_CARD','XS_DONCHPCARD');
EXEC p_insertEntitySpec('配线腔面板','WIRINGCARD','com.gxlu.ngrm.equipment.WiringCard','XB_CARD','XS_WIRINGCARD');
EXEC p_insertEntitySpec('数据处理板','DATAPROCESSCARD','com.gxlu.ngrm.equipment.DataProcessCard','XB_CARD','XS_DATAPROCESSCARD');
EXEC p_insertEntitySpec('BSC的后台处理模块','BSCBPMODULECARD','com.gxlu.ngrm.equipment.BSCBPModuleCard','XB_CARD','XS_BSCBPMODULECARD');
EXEC p_insertEntitySpec('语音处理板','VOICEPROCESSCARD','com.gxlu.ngrm.equipment.VoiceProcessCard','XB_CARD','XS_VOICEPROCESSCARD');
commit;

UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='CARD')
WHERE CORETABLENAME ='XB_CARD' and MAINTAININDICATOR != 1 AND PARENTID IS NULL;
commit;

--by liurr at 20100724
UPDATE XM_ENTITYDESCRIPTOR A
   SET A.DATALENGTH = 4000
 WHERE ENTITYSPECID =
       (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'CONFIGRESULTHIS')
   AND A.COLUMNNAME IN ('OLDRESSTRING', 'RESSTRING')
   AND A.DATALENGTH <> 4000;
COMMIT;

--by liurr at 20100826
EXEC p_insertEntitySpec('产品数据统计报表','PRODUCTREPORT','com.gxlu.ngrm.resService.ProductReport','XB_PRODUCTREPORT','');
EXEC p_insertentitydescriptor('PRODUCTREPORT','XB_PRODUCTREPORT');
COMMIT;

EXEC p_insertEntitySpec('资源配置报表','RESCONFIGREPORT','com.gxlu.ngrm.resService.ResConfigReport','XB_RESCONFIGREPORT','');
EXEC p_insertentitydescriptor('RESCONFIGREPORT','XB_RESCONFIGREPORT');
COMMIT;

EXEC p_insertEntitySpec('定单数据监控','ORDERREPORT','com.gxlu.ngrm.resService.OrderReport','XB_ORDERREPORT','');
EXEC p_insertentitydescriptor('ORDERREPORT','XB_ORDERREPORT');
COMMIT;

--by liurr at 20100830
exec p_insertentitydescriptor('RESCONFIGREPORT','XB_RESCONFIGREPORT');
commit;
exec p_insertentitydescriptor('ORDERREPORT','XB_ORDERREPORT');
commit;

--by liurr at 20100907
exec p_insertentitydescriptor('RESSERVINSRESINSASSOC','XB_RESSERVINSRESINSASSOC');
commit;

exec p_insertentitydescriptor('CONFIGRESULTHIS','XB_CONFIGRESULTHIS');
commit;

exec p_insertentitydescriptor('TASK','XB_TASK');
commit;

--by liurr at 20100920
exec p_insertentitydescriptor('PRODINS','XB_PRODINS');
commit;

exec p_insertentitydescriptor('SERVICEORDER','X_SERVICEORDER');
commit;

--by liurr at 20100925
exec p_insertentitydescriptor('PSTNLOGICALCODE','XS_PSTNLOGICALCODE');
commit;

exec p_insertentitydescriptor('OLT','XS_OLT');
commit;

exec p_insertentitydescriptor('ONU','XS_ONU');
commit;

exec p_insertentitydescriptor('FTTHCONFIG','XS_FTTHCONFIG');
commit;

EXEC p_insertEntitySpec('VLAN段','VLANSEGMENT','com.gxlu.ngrm.code.VLANSegment','X_CODESEGMENT','XS_VLANSEGMENT');
commit;

UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='CODESEGMENT')
WHERE CORETABLENAME ='X_CODESEGMENT' and MAINTAININDICATOR != 1 AND PARENTID IS NULL;
commit;

exec p_insertM2OneRelation('VLAN段对应的外层VLAN','VLANSEGMENT2VLAN','VLANSEGMENT','VLAN','VLANID','ENTITYID','S');
exec p_insertM2OneRelation('VLAN段对应的PON端口','VLANSEGMENT2PONPORT','VLANSEGMENT','PONPORT','PORTID','ENTITYID','S');
COMMIT;  

exec p_insertentitydescriptor('VLAN','XS_VLAN');
commit;

--by liurr at 20101008
EXEC p_insertEntitySpec('空卡序列号','BLANKCARD_SERIALNO','com.gxlu.ngrm.code.BlankCardSerialNo','X_BLANKCARD_SERIALNO','');
EXEC p_insertentitydescriptor('BLANKCARD_SERIALNO','X_BLANKCARD_SERIALNO');
COMMIT;

--by liurr at 20101009
exec p_insertentitydescriptor('MANAGEDELEMENT','XB_MANAGEDELEMENT');
commit;

exec p_insertentitydescriptor('ADSLCONFIG','XS_ADSLCONFIG');
commit;

--by liurr at 20101012
exec p_insertentitydescriptor('PHSLOGICALCODE','XS_PHSLOGICALCODE');
commit;

exec p_insertentitydescriptor('CODELEVEL','X_CODELEVEL');
commit;

--by liurr at 20101014
exec p_insertentitydescriptor('NETYPE','XB_NETYPE');
commit;
exec p_insertcomponentdescriptor('DSLAM','XC_DSLAM');
commit;

exec p_Insertentityspec('割接资产回退表','MIGRATIONCANCELPRODINS','com.gxlu.ngrm.resService.MigrationCancelProdins','XB_MIGRATIONCANCELPRODINS','');
exec p_insertentitydescriptor('MIGRATIONCANCELPRODINS','XB_MIGRATIONCANCELPRODINS');
commit;

--by liurr at 20101014
exec p_insertentitydescriptor('IVPNNO','X_IVPNNO');
commit;

exec p_insertM2OneRelation('虚拟网对应的综合虚拟网','IVPNNO2IVPN','IVPNNO','IVPNNO','IVPNID','ID','');
exec p_insertOne2MRelation('综合虚拟网对应的虚拟网','IVPN2IVPNNO','IVPNNO','IVPNNO','IVPNID','');
commit;

--by liurr at 20101015
delete from xm_entitydescriptor where entityspecid = (select id from xm_entityspec where code = 'CODELEVEL') and columnname = 'INISERVICESTATUS';

exec p_insertentitydescriptor('CODELEVEL','X_CODELEVEL');
commit;

update xm_entitydescriptor a
   set a.dicclassname = 'CODE', a.dicattributename = 'SERVICESTATUS'
 where entityspecid =
       (select id from xm_entityspec where code = 'CODELEVEL')
   and a.columnname = 'INISERVICESTATUS';
commit;

--by liurr at 20101018
delete from xm_entitydescriptor where entityspecid = (select id from xm_entityspec where code = 'IVPNNO') and columnname = 'IVPNID';
commit;

delete from xm_entityspec2relationspec d where d.relationspecid=(select id from xm_relationspec r where r.code='IVPNNO2IVPN');
delete from x_versioninstance d where d.subcategory='IVPNNO2IVPN';
delete from xm_relationspec where code='IVPNNO2IVPN';
commit;

delete from xm_entityspec2relationspec d where d.relationspecid=(select id from xm_relationspec r where r.code='IVPN2IVPNNO');
delete from x_versioninstance d where d.subcategory='IVPN2IVPNNO';
delete from xm_relationspec where code='IVPN2IVPNNO';
commit;

exec p_insertentitydescriptor('IVPNNO','X_IVPNNO');
commit;

--by liurr at 20101021
exec p_insertentitydescriptor('VLANSEGMENT','XS_VLANSEGMENT');
commit;

exec p_insertM2OneRelation('VLAN段对应的设备','VLANSEGMENT2ME','VLANSEGMENT','MANAGEDELEMENT','MEID','ENTITYID','S');
commit;

exec p_insertM2NRelation('VLAN段和端口的关联','VLANSEGMENTPORTRELATION','XR_VLANSEGMENTPORTRELATION','VLANSEGMENT','PORT','VLANSEGMENTID','PORTID');
commit;

--by liurr at 20101021
EXEC p_insertEntitySpec('EPON-ONU端口统计报表','ONUPORTREPORT','com.gxlu.ngrm.resService.OnuportReport','XB_ONUPORTREPORT','');
EXEC p_insertentitydescriptor('ONUPORTREPORT','XB_ONUPORTREPORT');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR A SET A.DICCLASSNAME = 'MANAGEDELEMENT', A.DICATTRIBUTENAME = 'ACCESSMODE' WHERE ENTITYSPECID = (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'ONUPORTREPORT') AND A.COLUMNNAME = 'ACCESSMODE';
COMMIT;

exec p_insertM2OneRelation('EPON-ONU端口统计报表对应的维护管理区','ONUPORTREPORT2MANAGEMENTAREA','ONUPORTREPORT','MANAGEMENTAREA','MANAGEMENTAREAID','ID','B');
exec p_insertM2OneRelation('EPON-ONU端口统计报表对应的站点','ONUPORTREPORT2SITE','ONUPORTREPORT','SITE','SITEID','ID','B');
exec p_insertM2OneRelation('EPON-ONU端口统计报表对应的OLT','ONUPORTREPORT2OLT','ONUPORTREPORT','OLT','OLTID','ID','B');
exec p_insertM2OneRelation('EPON-ONU端口统计报表对应的网元类型','ONUPORTREPORT2NETYPE','ONUPORTREPORT','NETYPE','NETYPEID','ID','B');
COMMIT;  

EXEC p_insertEntitySpec('局站统计报表','SITERESREPORT','com.gxlu.ngrm.resService.SiteResReport','XB_SITERESREPORT','');
EXEC p_insertentitydescriptor('SITERESREPORT','XB_SITERESREPORT');
COMMIT;

exec p_insertM2OneRelation('局站统计报表对应的维护管理区','SITERESREPORT2MANAGEMENTAREA','SITERESREPORT','MANAGEMENTAREA','MANAGEMENTAREAID','ID','B');
exec p_insertM2OneRelation('局站统计报表对应的站点','SITERESREPORT2SITE','SITERESREPORT','SITE','SITEID','ID','B');
COMMIT;

EXEC p_insertEntitySpec('区域统计报表','MANAGEMENTAREARESREPORT','com.gxlu.ngrm.resService.ManagementAreaResReport','XB_MANAGEMENTAREARESREPORT','');
EXEC p_insertentitydescriptor('MANAGEMENTAREARESREPORT','XB_MANAGEMENTAREARESREPORT');
COMMIT;

exec p_insertM2OneRelation('区域统计报表对应的维护管理区','MANAGEMENTAREARESREPORT2MANAGEMENTAREA','MANAGEMENTAREARESREPORT','MANAGEMENTAREA','MANAGEMENTAREAID','ID','B');
COMMIT; 

EXEC p_insertEntitySpec('资源配置工位统计报表','RCJOBNOREPORT','com.gxlu.ngrm.resService.RCJobNoReport','XB_RCJOBNOREPORT','');
EXEC p_insertentitydescriptor('RCJOBNOREPORT','XB_RCJOBNOREPORT');
COMMIT;

exec p_insertM2OneRelation('资源配置工位统计报表对应的营销区域','RCJOBNOREPORT2MARKETINGAREA','RCJOBNOREPORT','MARKETINGAREA','LOCATIONID','ID','B');
COMMIT;  

--by liurr at 20101027
exec p_insertcomponentdescriptor('LAN','XC_LAN');
exec p_insertcomponentdescriptor('ONU','XC_ONU');
commit;

--by liurr at 20101102
exec p_Insertentityspec('更改资源中间表','RESCHANGEPARAM','com.gxlu.ngrm.resService.ResChangeParam','XB_RESCHANGEPARAM','');
exec p_insertentitydescriptor('RESCHANGEPARAM','XB_RESCHANGEPARAM');
commit;

--by liurr at 20101102
EXEC p_insertEntitySpec('分离板','SEPARATIONCARD','com.gxlu.ngrm.equipment.SeparationCard','XB_CARD','XS_SEPARATIONCARD');
EXEC p_insertEntitySpec('业务用户板','BUSINESSCARD','com.gxlu.ngrm.equipment.BusinessCard','XB_CARD','XS_BUSINESSCARD');
EXEC p_insertEntitySpec('CN2板','CN2CARD','com.gxlu.ngrm.equipment.CN2Card','XB_CARD','XS_CN2CARD');
EXEC p_insertEntitySpec('IDC板','IDCCARD','com.gxlu.ngrm.equipment.IDCCard','XB_CARD','XS_IDCCARD');
EXEC p_insertEntitySpec('发送器电路板','TRANSMITTERCIRCUITCARD','com.gxlu.ngrm.equipment.TransmitterCircuitCard','XB_CARD','XS_TRANSMITTERCIRCUITCARD');
EXEC p_insertEntitySpec('信道保护板','CHANNELPROTECTIONCARD','com.gxlu.ngrm.equipment.ChannelProtectionCard','XB_CARD','XS_CHANNELPROTECTIONCARD');
commit;

UPDATE XM_ENTITYSPEC SET PARENTID=(SELECT ID FROM XM_ENTITYSPEC WHERE CODE='CARD')
WHERE CORETABLENAME ='XB_CARD' and MAINTAININDICATOR != 1 AND PARENTID IS NULL;
commit;

--by liurr at 20101104 for 广东使用了设备数据字典的 123值，所以，需要先在库中加上占用
exec p_InsertEntitySpec('动力设备','POWERNE','com.gxlu.ngrm.equipment.PowerNE','XB_MANAGEDELEMENT','XS_POWERNE');
exec p_insertEntityDescriptor('POWERNE','XS_POWERNE');
commit;

--建立对象之间的关系
update Xm_Entityspec d set d.Parentid = 
(select id from Xm_Entityspec p where p.code = 'MANAGEDELEMENT' )
 where code = 'POWERNE';
Commit;

--by liurr at 20101109
EXEC p_insertEntitySpec('ADSL端口统计报表','ADSLPORTREPORT','com.gxlu.ngrm.resService.AdslportReport','XB_ADSLPORTREPORT','');
EXEC p_insertentitydescriptor('ADSLPORTREPORT','XB_ADSLPORTREPORT');
COMMIT;

exec p_insertM2OneRelation('ADSL端口统计报表对应的维护管理区','ADSLPORTREPORT2MANAGEMENTAREA','ADSLPORTREPORT','MANAGEMENTAREA','MANAGEMENTAREAID','ID','B');
exec p_insertM2OneRelation('ADSL端口统计报表对应的站点','ADSLPORTREPORT2SITE','ADSLPORTREPORT','SITE','SITEID','ID','B');
exec p_insertM2OneRelation('ADSL端口统计报表对应的网元类型','ADSLPORTREPORT2NETYPE','ADSLPORTREPORT','NETYPE','NETYPEID','ID','B');
COMMIT;  

EXEC p_insertEntitySpec('PSTN端口统计报表','PSTNPORTREPORT','com.gxlu.ngrm.resService.PstnportReport','XB_PSTNPORTREPORT','');
EXEC p_insertentitydescriptor('PSTNPORTREPORT','XB_PSTNPORTREPORT');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR A
   SET A.DICCLASSNAME = 'PSTN', A.DICATTRIBUTENAME = 'ISHOME'
 WHERE ENTITYSPECID =
       (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'PSTNPORTREPORT')
   AND A.COLUMNNAME = 'ISHOME';
COMMIT;

exec p_insertM2OneRelation('PSTN端口统计报表对应的维护管理区','PSTNPORTREPORT2MANAGEMENTAREA','PSTNPORTREPORT','MANAGEMENTAREA','MANAGEMENTAREAID','ID','B');
exec p_insertM2OneRelation('PSTN端口统计报表对应的站点','PSTNPORTREPORT2SITE','PSTNPORTREPORT','SITE','SITEID','ID','B');
exec p_insertM2OneRelation('PSTN端口统计报表对应的网元类型','PSTNPORTREPORT2NETYPE','PSTNPORTREPORT','NETYPE','NETYPEID','ID','B');
COMMIT;  

EXEC p_insertEntitySpec('母局PSTN端口统计报表','HOSTPSTNPORTREPORT','com.gxlu.ngrm.resService.HostPstnportReport','XB_HOSTPSTNPORTREPORT','');
EXEC p_insertentitydescriptor('HOSTPSTNPORTREPORT','XB_HOSTPSTNPORTREPORT');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR A
   SET A.DICCLASSNAME = 'PSTN', A.DICATTRIBUTENAME = 'ISHOME'
 WHERE ENTITYSPECID =
       (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'HOSTPSTNPORTREPORT')
   AND A.COLUMNNAME = 'ISHOME';
COMMIT;

exec p_insertM2OneRelation('母局PSTN端口统计报表对应的维护管理区','HOSTPSTNPORTREPORT2MANAGEMENTAREA','HOSTPSTNPORTREPORT','MANAGEMENTAREA','MANAGEMENTAREAID','ID','B');
exec p_insertM2OneRelation('母局PSTN端口统计报表对应的站点','HOSTPSTNPORTREPORT2SITE','HOSTPSTNPORTREPORT','SITE','SITEID','ID','B');
exec p_insertM2OneRelation('母局PSTN端口统计报表对应的网元类型','HOSTPSTNPORTREPORT2NETYPE','HOSTPSTNPORTREPORT','NETYPE','NETYPEID','ID','B');
COMMIT;  

--by liurr at 20101111
EXEC p_insertEntitySpec('资源异常原因','RESEXCEPTION','com.gxlu.ngrm.resService.ResException','XB_RESEXCEPTION','');
EXEC p_insertentitydescriptor('RESEXCEPTION','XB_RESEXCEPTION');
COMMIT;

UPDATE XM_ENTITYDESCRIPTOR A
   SET A.DICCLASSNAME = 'TASK', A.DICATTRIBUTENAME = 'ACTION'
 WHERE ENTITYSPECID =
       (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'RESEXCEPTION')
   AND A.COLUMNNAME = 'TASKACTION';
   
UPDATE XM_ENTITYDESCRIPTOR A
   SET A.DICCLASSNAME = 'RESEXCEPTION', A.DICATTRIBUTENAME = 'HANDLESYS'
 WHERE ENTITYSPECID =
       (SELECT ID FROM XM_ENTITYSPEC WHERE CODE = 'RESEXCEPTION')
   AND A.COLUMNNAME = 'TARGETSYS';
COMMIT;

EXEC p_insertentitydescriptor('TASK','XB_TASK');
COMMIT;

exec p_insertM2OneRelation('任务对应的资源异常原因','TASK2RESEXCEPTION','TASK','RESEXCEPTION','RESEXPID','ID','B');
COMMIT;  

--by liurr at 20101119
exec p_insertcomponentdescriptor('LAN','XC_LAN');
exec p_insertcomponentdescriptor('DSLAM','XC_DSLAM');
exec p_insertcomponentdescriptor('ONU','XC_ONU');
commit;

--added by liurr at 20101209 
create table xm_relationspec_bk1209 as select * from xm_relationspec;
create table xm_entityspec2relspec_bk1209 as select * from Xm_Entityspec2relationspec;

update xm_relationspec xr
   set xr.supplierentityspecid = (select id
                                    from xm_entityspec
                                   where code = 'MANAGEDELEMENT')
 where xr.code = 'CODESEGPSTNNERELATION'
   and xr.supplierentityspecid =
       (select id from xm_entityspec where code = 'EXCHANGE');

update Xm_Entityspec2relationspec xer
   set xer.entityspecid              = (select id
                                          from xm_entityspec
                                         where code = 'MANAGEDELEMENT'),
       xer.entityspecversioninstance = (Select Id
                                          From x_Versioninstance
                                         Where Subcategory = 'MANAGEDELEMENT'
                                           And Rownum = 1)
 where xer.relationspecid =
       (select id from xm_relationspec where code = 'CODESEGPSTNNERELATION')
   and xer.entityspecid =
       (select id from xm_entityspec where code = 'EXCHANGE');
commit;

--by liurr at 20101215
EXEC p_insertEntitySpec('监控资源数据表','MONEXPRES','com.gxlu.ngrm.resService.MonExpRes','XB_MONEXPRES','');
EXEC p_insertentitydescriptor('MONEXPRES','XB_MONEXPRES');
COMMIT;
