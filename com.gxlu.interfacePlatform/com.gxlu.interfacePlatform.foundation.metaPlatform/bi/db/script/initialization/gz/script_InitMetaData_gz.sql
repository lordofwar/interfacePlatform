--by wxy 20090605
--Ԫ����
--Ŀǰֻ�й����õ�C���豸������C���豸��Ԫ�������������ó����������ط���Ҫ��ʱ����ִ��

/*
���� 
exec p_insertEntityDescriptor('MAPLINE','XB_MAPLINE');

exec p_insertComponetSpec('PSTN���������','PSTNNE');
exec p_InsertEntitySpec('����ͼ����','MAPLINE','com.gxlu.ngrm.equipment.MAPLine','XB_MAPLINE');

exec p_insertESpec2CSpec('EXCHANGE','PSTNNE');
exec p_insertM2NConstraint('��Ʒʵ���Ϳͻ��Ĺ���','PRODINSCUSTASSOC','com.gxlu.ngrm.resService.ProdInsCustAssoc','��Ʒʵ���Ϳͻ��Ĺ�����Ӧ�Ĳ�Ʒʵ��','��Ʒʵ���Ϳͻ��Ĺ�����Ӧ�Ŀͻ�','PRODINSCUSTASSOC2PRODINS','PRODINSCUSTASSOC2CUST','��Ʒʵ��','�ͻ���Ϣ','PRODINSID','CUSTOMERID');
exec p_insertM2NRelation('��Դ����ʵ��������ϵ','RESSERVINSASSIGN','XR_RESSERVINSASSIGN','RESSERVINS','RESSERVINS','PARENTID','CHILDID');
exec p_insertM2OneRelation('�˿ڶ�Ӧ�Ķ˿�����','PORT2PORTTYPE','PORT','PORTTYPE','PORTTYPEID','B');
exec p_insertOne2MRelation('��Ԫ��Ӧ�Ķ˿�','ME2PORT','MANAGEDELEMENT','PORT','MEID','B');
*/

--BY WXY 20090603
--����ʵ��/�����ʱ���ͬʱ����ʵ��/��������������������ֶΣ�ֻҪ����ִ�����������Ĺ��̡�

--���
exec p_insertComponetSpec('CPU���','CPU');
exec p_insertComponetSpec('Memory���','MEMORY');

--��������
exec p_insertComponentDescriptor('MAINTAINANCE','XC_MAINTAINANCE');
exec p_insertEntityDescriptor('MANAGEDELEMENT','XB_MANAGEDELEMENT');

--ʵ��
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

exec p_InsertEntitySpec('����','SECTOR','com.gxlu.ngrm.equipment.Sector','XB_MANAGEDELEMENT','XS_SECTOR');
exec p_InsertEntitySpec('��Φ','TOWERMAST','com.gxlu.ngrm.equipment.TowerMast','XB_MANAGEDELEMENT','XS_TOWERMAST');
exec p_InsertEntitySpec('����','ANTENNA','com.gxlu.ngrm.equipment.Antenna','XB_MANAGEDELEMENT','XS_ANTENNA');
exec p_InsertEntitySpec('����','PRESENTLINE','com.gxlu.ngrm.equipment.PresentLine','XB_MANAGEDELEMENT','XS_PRESENTLINE');
exec p_InsertEntitySpec('�ҷ�ϵͳ','DISTRIBUTESYSTEM','com.gxlu.ngrm.equipment.DistributeSystem','XB_MANAGEDELEMENT','XS_DISTRIBUTESYSTEM');
exec p_InsertEntitySpec('��������','INDOORANTENNA','com.gxlu.ngrm.equipment.IndoorAntenna','XB_MANAGEDELEMENT','XS_INDOORANTENNA');
exec p_InsertEntitySpec('��·��','JOINROUTE','com.gxlu.ngrm.equipment.JoinRoute','XB_MANAGEDELEMENT','XS_JOINROUTE');
exec p_InsertEntitySpec('������','WORKDISPARTINSTRUMENT','com.gxlu.ngrm.equipment.WorkDispartInstrument','XB_MANAGEDELEMENT','XS_WORKDISPARTINSTRUMENT');
exec p_InsertEntitySpec('�����','COUPLINGINSTRUMENT','com.gxlu.ngrm.equipment.CouplingInstrument','XB_MANAGEDELEMENT','XS_COUPLINGINSTRUMENT');
exec p_InsertEntitySpec('����','ELECTRICBRIDGE','com.gxlu.ngrm.equipment.ElectricBridge','XB_MANAGEDELEMENT','XS_ELECTRICBRIDGE');
exec p_InsertEntitySpec('����','LOAD','com.gxlu.ngrm.equipment.Load','XB_MANAGEDELEMENT','XS_LOAD');
exec p_InsertEntitySpec('ֱ��վ','REPEATOR','com.gxlu.ngrm.equipment.Repeator','XB_MANAGEDELEMENT','XS_REPEATOR');
exec p_InsertEntitySpec('��Զģ��','REMOTEAMPLIFIER','com.gxlu.ngrm.equipment.RemoteAmplifier','XB_MANAGEDELEMENT','XS_REMOTEAMPLIFIER');
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
exec p_InsertEntitySpec('ҵ��ƽ̨','PLATFORM','com.gxlu.ngrm.equipment.Platform','XB_MANAGEDELEMENT','XS_PLATFORM');
commit;

Update Xm_Entityspec d Set d.Stsequence=Null Where d.parentid=3 And d.Maintainindicator=3;
commit;

--���ø���
update Xm_Entityspec d set d.Parentid = 
				(select id from Xm_Entityspec p where d.Coretablename = p.Coretablename and p.Maintainindicator = 1 )
 where d.Coretablename = 'XB_MANAGEDELEMENT' 
   and d.Maintainindicator = 3;
commit;

--�ֵ�����˹������
update xm_EntityDescriptor d set d.Dicclassname = 'REPEATOR' 
 where d.Entityspecid = (select id from xm_entityspec e where e.Code = 'REMOTEAMPLIFIER') 
   and d.Dicattributename in ('INSTALLMODE','RECOVERTYPE','AMPLIFIERTYPE');
commit;

update xm_EntityDescriptor d set d.Dicclassname = 'MANAGEDELEMENT' 
 where d.Entityspecid in (select id from xm_entityspec e where e.Code in('BTS','REPEATOR','REMOTEAMPLIFIER') )
   and d.Dicattributename = 'RECOVERAREATYPE';
commit;


--���ӹ�ϵ
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


--����������õĶ��һ�Ĺ�ϵ
exec p_insertM2OneRelation('BTS������BSC','BTS2BSC','BTS','BSC','BSCID','ENTITYID','S');
exec p_insertM2OneRelation('BTS������MSC','BTS2MSC','BTS','MSC','BSCID','ENTITYID','S');
exec p_insertM2OneRelation('BTS������Φ','BTS2TOWERMAST','BTS','TOWERMAST','TOWERMASTID','ENTITYID','S');

exec p_insertM2OneRelation('����������BTS','SECTOR2BTS','SECTOR','BTS','BTSID','ENTITYID','S');
exec p_insertM2OneRelation('������Ӧ����Ƶ','SECTOR2PHYSICALCONTAINER','SECTOR','PHYSICALCONTAINER','PHYSICALCONTAINERID','ENTITYID','B');

exec p_insertM2OneRelation('��Φ������Զģ��','TOWERMAST2AMPLIFIERNE','TOWERMAST','REMOTEAMPLIFIER','AMPLIFIERNEID','ENTITYID','S');
exec p_insertM2OneRelation('��Φ����ֱ��վ','TOWERMAST2REPEATOR','TOWERMAST','REPEATOR','REPEATORID','ENTITYID','S');

exec p_insertM2OneRelation('���߹�������','ANTENNA2SECTOR','ANTENNA','SECTOR','SECTORID','ENTITYID','S');
exec p_insertM2OneRelation('��������BTS','ANTENNA2BTS','ANTENNA','BTS','BTSID','ENTITYID','S');
exec p_insertM2OneRelation('����������Զģ��','ANTENNA2AMPLIFIERNE','ANTENNA','REMOTEAMPLIFIER','AMPLIFIERNEID','ENTITYID','S');
exec p_insertM2OneRelation('��������ֱ��վ','ANTENNA2REPEATOR','ANTENNA','REPEATOR','REPEATORID','ENTITYID','S');

exec p_insertM2OneRelation('���߹�������','PRESENTLINE2ANTENNA','PRESENTLINE','ANTENNA','ANTENNAID','ENTITYID','S');

exec p_insertM2OneRelation('�ҷ�ϵͳ��������','DISTRIBUTESYSTEM2SECTOR','DISTRIBUTESYSTEM','SECTOR','SECTORID','ENTITYID','S');
exec p_insertM2OneRelation('�ҷ�ϵͳ������Դ�豸','DISTRIBUTESYSTEM2BTS','DISTRIBUTESYSTEM','BTS','BTSID','ENTITYID','S');

exec p_insertM2OneRelation('ֱ��վ��Ӧ�Ľ���/Զ�˻��豸','REPEATOR2ME','REPEATOR','MANAGEDELEMENT','SNNEID','ENTITYID','B');
exec p_insertM2OneRelation('ֱ��վ��������','REPEATOR2SECTOR','REPEATOR','SECTOR','SECTORID','ENTITYID','S');
 
exec p_insertM2OneRelation('��Զģ���������','REMOTEAMPLIFIER2SECTOR','REMOTEAMPLIFIER','SECTOR','SECTORID','ENTITYID','S');
   
exec p_insertM2OneRelation('BSC����MSC','BSC2MSC','BSC','MSC','MSCID','ENTITYID','S');
              
exec p_insertM2OneRelation('MSC�������Ƿ�Χ','MSC2RECOVERAREA','MSC','RESCOVER','RESCOVERID','ENTITYID','B');
  
exec p_insertM2OneRelation('PDSN������AAA','PDSN2AAA','PDSN','AAA','AAAID','ENTITYID','S');
exec p_insertM2OneRelation('PDSN������FACN','PDSN2FACN','PDSN','FACN','FACNID','ENTITYID','S');
exec p_insertM2OneRelation('PDSN�������Ƿ�Χ','PDSN2RECOVERAREA','PDSN','RESCOVER','RESCOVERID','ENTITYID','B');
                                              
exec p_insertM2OneRelation('AAA�������Ƿ�Χ','AAA2RECOVERAREA','AAA','RESCOVER','RESCOVERID','ENTITYID','B');
                                              
exec p_insertM2OneRelation('�������������ҷ�ϵͳ','INDOORANTENNA2DISTRIBUTESYSTEM','INDOORANTENNA','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('��·�������ҷ�ϵͳ','JOINROUTE2DISTRIBUTESYSTEM','JOINROUTE','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('�����������ҷ�ϵͳ','WORKDISPARTINSTRUMENT2DISTRIBUTESYSTEM','WORKDISPARTINSTRUMENT','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('����������ҷ�ϵͳ','COUPLINGINSTRUMENT2DISTRIBUTESYSTEM','COUPLINGINSTRUMENT','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('���������ҷ�ϵͳ','ELECTRICBRIDGE2DISTRIBUTESYSTEM','ELECTRICBRIDGE','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');
exec p_insertM2OneRelation('���������ҷ�ϵͳ','LOAD2DISTRIBUTESYSTEM','LOAD','DISTRIBUTESYSTEM','DISTRIBUTESYSTEMID','ENTITYID','S');

COMMIT;
    
