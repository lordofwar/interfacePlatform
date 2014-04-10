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
	
		--先删除模版中的记录
		--模版表 xm_entitymgttemplate  ENTITYSPECID关联Xm_entityspec
		--模版属性表 xm_mgttemplateattr  ENTITYMGTTEMPLATEID关联xm_entitymgttemplate.id;ATTRIBUTEID关联xm_entitydescriptor.id
		--模版表单表 xm_mgttemplatefrm ENTITYMGTTEMPLATEID关联xm_entitymgttemplate.id；
		--模版表单分组表 xm_mgttemplatefrmgrp MGTTEMPLATEFRMID关联xm_mgttemplatefrm.id;
		--模版表单分组元素表 xm_mgttemplatefrmelement MGTTEMPLATEFRMGRPID关联xm_mgttemplatefrmgrp.id;NAME与xm_entitydescpritor.columnname相同
		--模版表格表 xm_mgttemplatetbl  ENTITYMGTTEMPLATEID关联xm_entitymgttemplate.id
		--模版表格元素表 xm_mgttemplatetblelement MGTTEMPLATETBLID关联xm_mgttemplatetbl.id
	
		Select Id
			Into v_Entityspecid
			From Xm_Entityspec
		 Where Code = p_Entityspecdoe;
	
		--删除模版表单分组元素中的列
		Delete From Xm_Mgttemplatefrmelement
		 Where Name = p_Columnname
			 And Mgttemplatefrmgrpid In (Select g.Id
																		 From Xm_Entitymgttemplate t, Xm_Mgttemplatefrm f, Xm_Mgttemplatefrmgrp g
																		Where g.Mgttemplatefrmid = f.Id
																			And f.Entitymgttemplateid = t.Id
																			And t.Entityspecid = v_Entityspecid);
		--删除模版表格元素中的列
		Delete From Xm_Mgttemplatetblelement
		 Where Name = p_Columnname
			 And Mgttemplatetblid In (Select Tb.Id
																	From Xm_Mgttemplatetbl Tb, Xm_Entitymgttemplate t
																 Where Tb.Entitymgttemplateid = t.Id
																	 And t.Entityspecid = v_Entityspecid);
	
		--删除字段校验
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
	
		--删除模版属性表中的列
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
	
		--删除元数据描述
		Delete From Xm_Entitydescriptor
		 Where Entityspecid = v_Entityspecid
			 And Columnname = p_Columnname;
	
		v_Rows := Sql%Rowcount; --受影响行数
	
		Dbms_Output.Put_Line('删除' || v_Rows || '条记录');
	
	Else
		Dbms_Output.Put_Line(p_Entityspecdoe || '存在两条记录！无法删除');
	End If;

Exception
	When Others Then
		Begin
			Rollback;
			Dbms_Output.Put_Line('删除失败！');
		End;
	
End p_Deleteentitydescriptor;
/
