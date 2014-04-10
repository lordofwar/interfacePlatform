--by wxy 20090605
--元数据
--目前只有贵州用到C网设备，所以C网设备的元数据描述单独拿出来，其他地方需要的时候再执行

/*
范例 
exec p_insertEntityDescriptor('MAPLINE','XB_MAPLINE');

exec p_insertComponetSpec('PSTN交换机组件','PSTNNE');
exec p_InsertEntitySpec('拓扑图连线','MAPLINE','com.gxlu.ngrm.equipment.MAPLine','XB_MAPLINE');

exec p_insertESpec2CSpec('EXCHANGE','PSTNNE');
exec p_insertM2NConstraint('产品实例和客户的关联','PRODINSCUSTASSOC','com.gxlu.ngrm.resService.ProdInsCustAssoc','产品实例和客户的关联对应的产品实例','产品实例和客户的关联对应的客户','PRODINSCUSTASSOC2PRODINS','PRODINSCUSTASSOC2CUST','产品实例','客户信息','PRODINSID','CUSTOMERID');
exec p_insertM2NRelation('资源服务实例从属关系','RESSERVINSASSIGN','XR_RESSERVINSASSIGN','RESSERVINS','RESSERVINS','PARENTID','CHILDID');
exec p_insertM2OneRelation('端口对应的端口类型','PORT2PORTTYPE','PORT','PORTTYPE','PORTTYPEID','B');
exec p_insertOne2MRelation('网元对应的端口','ME2PORT','MANAGEDELEMENT','PORT','MEID','B');
*/

--BY WXY 20090603
--生成实体/组件的时候会同时生成实体/组件的描述。后续增加字段，只要单独执行生成描述的过程。

--组件
exec p_insertComponetSpec('CPU组件','CPU');
exec p_insertComponetSpec('Memory组件','MEMORY');

--属性增加
exec p_insertComponentDescriptor('MAINTAINANCE','XC_MAINTAINANCE');
exec p_insertEntityDescriptor('MANAGEDELEMENT','XB_MANAGEDELEMENT');

--实体
update xm_EntitySpec d set d.Satellitetablename='XS_SITE',d.Stsequence = 'S_XS_SITE' WHERE D.Code='SITE';
commit;
update xm_EntitySpec d set d.Satellitetablename='XS_ROOM',d.Stsequence = 'S_XS_ROOM' WHERE D.Code='ROOM';
commit;

exec p_insertEntityDescriptor('SITE','XS_SITE');
exec p_insertEntityDescriptor('ROOM','XS_ROOM');
commit;

update xm_EntitySpec d set d.Satellitetablename='XS_BTS',d.Stsequence = 'S_XS_BTS' WHERE D.Code='BTS';
delete from Xm_Entitydescriptor d where d.Entityspecid=(select id from Xm_Entityspec e where e.Code='BTS');
commit;
exec p_insertEntityDescriptor('BTS','XS_BTS');
commit;

--exec p_InsertEntitySpec('BTS','BTS','com.gxlu.ngrm.equipment.BTS','XS_BTS');

exec p_InsertEntitySpec('扇区','SECTOR','com.gxlu.ngrm.equipment.Sector','XB_MANAGEDELEMENT','XS_SECTOR');
exec p_InsertEntitySpec('塔桅','TOWERMAST','com.gxlu.ngrm.equipment.TowerMast','XB_MANAGEDELEMENT','XS_TOWERMAST');
exec p_InsertEntitySpec('天线','ANTENNA','com.gxlu.ngrm.equipment.Antenna','XB_MANAGEDELEMENT','XS_ANTENNA');
exec p_InsertEntitySpec('馈线','PRESENTLINE','com.gxlu.ngrm.equipment.PresentLine','XB_MANAGEDELEMENT','XS_PRESENTLINE');
exec p_InsertEntitySpec('室分系统','DISTRIBUTESYSTEM','com.gxlu.ngrm.equipment.DistributeSystem','XB_MANAGEDELEMENT','XS_DISTRIBUTESYSTEM');
exec p_InsertEntitySpec('室内天线','INDOORANTENNA','com.gxlu.ngrm.equipment.IndoorAntenna','XB_MANAGEDELEMENT','XS_INDOORANTENNA');
exec p_InsertEntitySpec('合路器','JOINROUTE','com.gxlu.ngrm.equipment.JoinRoute','XB_MANAGEDELEMENT','XS_JOINROUTE');
exec p_InsertEntitySpec('功分器','WORKDISPARTINSTRUMENT','com.gxlu.ngrm.equipment.WorkDispartInstrument','XB_MANAGEDELEMENT','XS_WORKDISPARTINSTRUMENT');
exec p_InsertEntitySpec('耦合器','COUPLINGINSTRUMENT','com.gxlu.ngrm.equipment.CouplingInstrument','XB_MANAGEDELEMENT','XS_COUPLINGINSTRUMENT');
exec p_InsertEntitySpec('电桥','ELECTRICBRIDGE','com.gxlu.ngrm.equipment.ElectricBridge','XB_MANAGEDELEMENT','XS_ELECTRICBRIDGE');
exec p_InsertEntitySpec('负载','LOAD','com.gxlu.ngrm.equipment.Load','XB_MANAGEDELEMENT','XS_LOAD');
exec p_InsertEntitySpec('直放站','REPEATOR','com.gxlu.ngrm.equipment.Repeator','XB_MANAGEDELEMENT','XS_REPEATOR');
exec p_InsertEntitySpec('拉远模块','REMOTEAMPLIFIER','com.gxlu.ngrm.equipment.RemoteAmplifier','XB_MANAGEDELEMENT','XS_REMOTEAMPLIFIER');
exec p_InsertEntitySpec('BSC','BSC','com.gxlu.ngrm.equipment.BSC','XB_MANAGEDELEMENT','XS_BSC');
exec p_InsertEntitySpec('MSC','MSC','com.gxlu.ngrm.equipment.MSC','XB_MANAGEDELEMENT','XS_MSC');
exec p_InsertEntitySpec('IGW','IGW','com.gxlu.ngrm.equipment.IGW','XB_MANAGEDELEMENT','XS_IGW');
exec p_InsertEntitySpec('MSCe','MSCE','com.gxlu.ngrm.equipment.MSCe','XB_MANAGEDELEMENT','XS_MSCE');
exec p_InsertEntitySpec('HLR','HLR','com.gxlu.ngrm.equipment.HLR','XB_MANAGEDELEMENT','XS_HLR');
exec p_InsertEntitySpec('HLRe','HLRE','com.gxlu.ngrm.equipment.HLRe','XB_MANAGEDELEMENT','XS_HLRE');
exec p_InsertEntitySpec('H/LSTP','HLSTP','com.gxlu.ngrm.equipment.HLSTP','XB_MANAGEDELEMENT','XS_HLSTP');
exec p_InsertEntitySpec('MGW','MGW','com.gxlu.ngrm.equipment.MGW','XB_MANAGEDELEMENT','XS_MGW');
exec p_InsertEntitySpec('PDSN','PDSN','com.gxlu.ngrm.equipment.PDSN','XB_MANAGEDELEMENT','XS_PDSN');
exec p_InsertEntitySpec('AAA','AAA','com.gxlu.ngrm.equipment.AAA','XB_MANAGEDELEMENT','XS_AAA');
exec p_InsertEntitySpec('FACN','FACN','com.gxlu.ngrm.equipment.FACN','XB_MANAGEDELEMENT','XS_FACN');
exec p_InsertEntitySpec('DNS','DNS','com.gxlu.ngrm.equipment.DNS','XB_MANAGEDELEMENT','XS_DNS');
exec p_InsertEntitySpec('NTP','NTP','com.gxlu.ngrm.equipment.NTP','XB_MANAGEDELEMENT','XS_NTP');
exec p_InsertEntitySpec('业务平台','PLATFORM','com.gxlu.ngrm.equipment.Platform','XB_MANAGEDELEMENT','XS_PLATFORM');
commit;

Update Xm_Entityspec d Set d.Stsequence=Null Where d.parentid=3 And d.Maintainindicator=3;
commit;

--设置父亲
update Xm_Entityspec d set d.Parentid = 
				(select id from Xm_Entityspec p where d.Coretablename = p.Coretablename and p.Maintainindicator = 1 )
 where d.Coretablename = 'XB_MANAGEDELEMENT' 
   and d.Maintainindicator = 3;
commit;

--字典出现了共用情况
update xm_EntityDescriptor d set d.Dicclassname = 'REPEATOR' 
 where d.Entityspecid = (select id from xm_entityspec e where e.Code = 'REMOTEAMPLIFIER') 
   and d.Dicattributename in ('INSTALLMODE','RECOVERTYPE','AMPLIFIERTYPE');
commit;

update xm_EntityDescriptor d set d.Dicclassname = 'MANAGEDELEMENT' 
 where d.Entityspecid in (select id from xm_entityspec e where e.Code in('BTS','REPEATOR','REMOTEAMPLIFIER') )
   and d.Dicattributename = 'RECOVERAREATYPE';
commit;


--增加关系
exec p_insertESpec2CSpec('SITE','BENEFIT');
exec p_insertESpec2CSpec('ROOM','BENEFIT');

exec p_insertESpec2CSpec('ROOM','MAINTAINANCE');

exec p_insertESpec2CSpec('MANAGEDELEMENT','BENEFIT');
exec p_insertESpec2CSpec('MANAGEDELEMENT','MAINTAINANCE');

exec p_insertESpec2CSpec('AAA','CPU');
exec p_insertESpec2CSpec('AAA','MEMORY');

exec p_insertESpec2CSpec('FACN','CPU');
exec p_insertESpec2CSpec('FACN','MEMORY');

exec p_insertESpec2CSpec('PLATFORM','CPU');
exec p_insertESpec2CSpec('PLATFORM','MEMORY');


--增加外键引用的多对一的关系
exec p_insertM2OneRelation('BTS归属的BSC','BTS2BSC','BTS','BSC','BSCID','ENTITYID','S');
exec p_insertM2OneRelation('BTS归属的MSC','BTS2MSC','BTS','MSC','BSCID','ENTITYID','S');
exec p_insertM2OneRelation('BTS所属塔桅','BTS2TOWERMAST','BTS','TOWERMAST','TOWERMASTID','ENTITYID','S');

exec p_insertM2OneRelation('扇区归属的BTS','SECTOR2BTS','SECTOR','BTS','BTSID','ENTITYID','S');
exec p_insertM2OneRelation('扇区对应的载频','SECTOR2PHYSICALCONTAINER','SECTOR','PHYSICALCONTAINER','PHYSICALCONTAINERID','ENTITYID','B');

exec p_insertM2OneRelation('塔桅所属拉远模块','TOWERMAST2AMPLIFIERNE','TOWERMAST','REMOTEAMPLIFIER','AMPLIFIERNEID','ENTITYID','S');
exec p_insertM2OneRelation('塔桅所属直放站','TOWERMAST2REPEATOR','TOWERMAST','REPEATOR','REPEATORID','ENTITYID','S');

exec p_insertM2OneRelation('天线关联扇区','ANTENNA2SECTOR','ANTENNA','SECTOR','SECTORID','ENTITYID','S');
exec p_insertM2OneRelation('天线所属BTS','ANTENNA2BTS','ANTENNA','BTS','BTSID','ENTITYID','S');
exec p_insertM2OneRelation('天线所属拉远模块','ANTENNA2AMPLIFIERNE','ANTENNA','REMOTEAMPLIFIER','AMPLIFIERNEID','ENTITYID','S');
exec p_insertM2OneRelation('天线所属直放站','ANTENNA2REPEATOR','ANTENNA','REPEATOR','REPEATORID','ENTITYID','S');

exec p_insertM2OneRelation('馈线关联天线','PRESENTLINE2ANTENNA','PRESENTLINE','ANTENNA','ANTENNAID','ENTITYID','S');

exec p_insertM2OneRelation('室分系统归属扇区','DISTRIBUTESYSTEM2SECTOR','DISTRIBUTESYSTEM','SECTOR','SECTORID','ENTITYID','S');
exec p_insertM2OneRelation('室分系统所属信源设备','DISTRIBUTESYSTEM2BTS','DISTRIBUTESYSTEM','BTS','BTSID','ENTITYID','S');

exec p_insertM2OneRelation('直放站对应的近端/远端机设备','REPEATOR2ME','REPEATOR','MANAGEDELEMENT','SNNEID','ENTITYID','B');
exec p_insertM2OneRelation('直放站归属扇区','REPEATOR2SECTOR','REPEATOR','SECTOR','SECTORID','ENTITYID','S');
 
exec p_insertM2OneRelation('拉远模块归属扇区','REMOTEAMPLIFIER2SECTOR','REMOTEAMPLIFIER','SECTOR','SECTORID','ENTITYID','S');
   
exec p_insertM2OneRelation('BSC归属MSC','BSC2MSC','BSC','MSC','MSCID','ENTITYID','S');
              
exec p_insertM2OneRelation('MSC所属覆盖范围','MSC2RECOVERAREA','MSC','RESCOVER','RESCOVERID','ENTITYID','B');
  
exec p_insertM2OneRelation('PDSN关联的AAA','PDSN2AAA','PDSN','AAA','AAAID','ENTITYID','S');
exec p_insertM2OneRelation('PDSN关联的FACN','PDSN2FACN','PDSN','FACN','FACNID','ENTITYID','S');
exec p_insertM2OneRelation('PDSN所属覆盖范围','PDSN2RECOVERAREA','PDSN','RESCOVER','RESCOVERID','ENTITYID','B');
                                              
exec p_insertM2OneRelation('AAA所属覆盖范围','AAA2RECOVERAREA','AAA','RESCOVER','RESCOVERID','ENTITYID','B');
                                              
exec p_insertM2OneRelation('室内天线所属室分系统','INDOORANTENNA2DISTRIBUTESYSTEM','INDOORANTENNA','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('合路器所属室分系统','JOINROUTE2DISTRIBUTESYSTEM','JOINROUTE','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('功分器所属室分系统','WORKDISPARTINSTRUMENT2DISTRIBUTESYSTEM','WORKDISPARTINSTRUMENT','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('耦合器所属室分系统','COUPLINGINSTRUMENT2DISTRIBUTESYSTEM','COUPLINGINSTRUMENT','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('电桥所属室分系统','ELECTRICBRIDGE2DISTRIBUTESYSTEM','ELECTRICBRIDGE','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('负载所属室分系统','LOAD2DISTRIBUTESYSTEM','LOAD','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');

COMMIT;
    
