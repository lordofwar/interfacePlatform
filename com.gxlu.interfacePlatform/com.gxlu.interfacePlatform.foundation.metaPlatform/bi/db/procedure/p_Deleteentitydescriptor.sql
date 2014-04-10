CREATE OR REPLACE Procedure p_Deleteentitydescriptor
(
	p_Entityspecdoe In Varchar2,
	p_Columnname    In Varchar2
) Is
	v_Count        Number(18);
	v_Rows         Number(2);
	v_Entityspecid Number(18);
Begin

	Select Count(*)
		Into v_Count
		From Xm_Entityspec
	 Where Code = p_Entityspecdoe;

	If v_Count = 1 Then
	
		--��ɾ��ģ���еļ�¼
		--ģ��� xm_entitymgttemplate  ENTITYSPECID����Xm_entityspec
		--ģ�����Ա� xm_mgttemplateattr  ENTITYMGTTEMPLATEID����xm_entitymgttemplate.id;ATTRIBUTEID����xm_entitydescriptor.id
		--ģ����� xm_mgttemplatefrm ENTITYMGTTEMPLATEID����xm_entitymgttemplate.id��
		--ģ�������� xm_mgttemplatefrmgrp MGTTEMPLATEFRMID����xm_mgttemplatefrm.id;
		--ģ�������Ԫ�ر� xm_mgttemplatefrmelement MGTTEMPLATEFRMGRPID����xm_mgttemplatefrmgrp.id;NAME��xm_entitydescpritor.columnname��ͬ
		--ģ����� xm_mgttemplatetbl  ENTITYMGTTEMPLATEID����xm_entitymgttemplate.id
		--ģ����Ԫ�ر� xm_mgttemplatetblelement MGTTEMPLATETBLID����xm_mgttemplatetbl.id
	
		Select Id
			Into v_Entityspecid
			From Xm_Entityspec
		 Where Code = p_Entityspecdoe;
	
		--ɾ��ģ�������Ԫ���е���
		Delete From Xm_Mgttemplatefrmelement
		 Where Name = p_Columnname
			 And Mgttemplatefrmgrpid In (Select g.Id
																		 From Xm_Entitymgttemplate t, Xm_Mgttemplatefrm f, Xm_Mgttemplatefrmgrp g
																		Where g.Mgttemplatefrmid = f.Id
																			And f.Entitymgttemplateid = t.Id
																			And t.Entityspecid = v_Entityspecid);
		--ɾ��ģ����Ԫ���е���
		Delete From Xm_Mgttemplatetblelement
		 Where Name = p_Columnname
			 And Mgttemplatetblid In (Select Tb.Id
																	From Xm_Mgttemplatetbl Tb, Xm_Entitymgttemplate t
																 Where Tb.Entitymgttemplateid = t.Id
																	 And t.Entityspecid = v_Entityspecid);
	
		--ɾ���ֶ�У��
		Delete From Xm_Entitymgttmplattrvalid
		 Where Entitymgttemplateattrid In
					 (Select Id
							From Xm_Mgttemplateattr
						 Where Entitymgttemplateid In (Select Id
																						 From Xm_Entitymgttemplate
																						Where Entityspecid = v_Entityspecid)
							 And Attributeid In (Select Id
																		From Xm_Entitydescriptor
																	 Where Columnname = p_Columnname
																		 And Entityspecid In (Select Id
																														From Xm_Entityspec
																													 Start With Id = v_Entityspecid
																													Connect By Prior Id = Parentid)));
	
		--ɾ��ģ�����Ա��е���
		Delete From Xm_Mgttemplateattr
		 Where Entitymgttemplateid In (Select Id
																		 From Xm_Entitymgttemplate
																		Where Entityspecid = v_Entityspecid)
			 And Attributeid In (Select Id
														From Xm_Entitydescriptor
													 Where Columnname = p_Columnname
														 And Entityspecid In (Select Id
																										From Xm_Entityspec
																									 Start With Id = v_Entityspecid
																									Connect By Prior Id = Parentid));
	
		--ɾ��Ԫ��������
		Delete From Xm_Entitydescriptor
		 Where Entityspecid = v_Entityspecid
			 And Columnname = p_Columnname;
	
		v_Rows := Sql%Rowcount; --��Ӱ������
	
		Dbms_Output.Put_Line('ɾ��' || v_Rows || '����¼');
	
	Else
		Dbms_Output.Put_Line(p_Entityspecdoe || '����������¼���޷�ɾ��');
	End If;

Exception
	When Others Then
		Begin
			Rollback;
			Dbms_Output.Put_Line('ɾ��ʧ�ܣ�');
		End;
	
End p_Deleteentitydescriptor;
/
