CREATE OR REPLACE Procedure p_Deleteentityspec(p_Entityspecode In Varchar2) Is
	v_Entityspecid Number(18);
Begin
	Select Id
		Into v_Entityspecid
		From Xm_Entityspec
	 Where Code = p_Entityspecode;

	/*
  For r In (Select x.Columnname
              From Xm_Entitydescriptor x
             Where Entityspecid In (Select Id
                                      From Xm_Entityspec
                                     Start With Id = v_Entityspecid
                                    Connect By Prior Parentid = Id)) Loop
    Begin
      Execute Immediate 'CALL p_Deleteentitydescriptor(''' || p_Entityspecode || ''', ''' || r.Columnname || ''')';
    End;
  End Loop;*/

	--删除模版表单分组元素中的列
	Delete From Xm_Mgttemplatefrmelement
	 Where Mgttemplatefrmgrpid In (Select g.Id
																	 From Xm_Entitymgttemplate t, Xm_Mgttemplatefrm f, Xm_Mgttemplatefrmgrp g
																	Where g.Mgttemplatefrmid = f.Id
																		And f.Entitymgttemplateid = t.Id
																		And t.Entityspecid = v_Entityspecid);
	--删除模版表格元素中的列
	Delete From Xm_Mgttemplatetblelement
	 Where Mgttemplatetblid In (Select Tb.Id
																From Xm_Mgttemplatetbl Tb, Xm_Entitymgttemplate t
															 Where Tb.Entitymgttemplateid = t.Id
																 And t.Entityspecid = v_Entityspecid);

	--删除字段校验
	Delete From Xm_Entitymgttmplattrvalid x
	 Where x.Entitymgttemplateattrid In
				 (Select Id
						From Xm_Mgttemplateattr
					 Where Entitymgttemplateid In (Select Id
																					 From Xm_Entitymgttemplate
																					Where Entityspecid = v_Entityspecid));

	--删除模版属性表中的列
	Delete From Xm_Mgttemplateattr
	 Where Entitymgttemplateid In (Select Id
																	 From Xm_Entitymgttemplate
																	Where Entityspecid = v_Entityspecid);

	--模版表格
	Delete From Xm_Mgttemplatetbl
	 Where Entitymgttemplateid In (Select Id
																	 From Xm_Entitymgttemplate
																	Where Entityspecid = v_Entityspecid);
	--模版表单分组
	Delete From Xm_Mgttemplatefrmgrp
	 Where Mgttemplatefrmid In (Select f.Id
																From Xm_Entitymgttemplate t, Xm_Mgttemplatefrm f
															 Where f.Entitymgttemplateid = t.Id
																 And t.Entityspecid = v_Entityspecid);
	--模版表单
	Delete From Xm_Mgttemplatefrm
	 Where Entitymgttemplateid In (Select Id
																	 From Xm_Entitymgttemplate
																	Where Entityspecid = v_Entityspecid);
	--删除表单和菜单的关系
	Delete From Xm_Menuitemmgttemplateassoc Xmp
	 Where Xmp.Entitymgttemplateid In (Select Id
																			 From Xm_Entitymgttemplate
																			Where Entityspecid = v_Entityspecid);

	--删除本身关联模版关系
	Delete From Xm_Entitymgttemplaterelation
	 Where Entitymgttemplateid In (Select Id
																	 From Xm_Entitymgttemplate
																	Where Entityspecid = v_Entityspecid);

	--删除被关联模版关系 关联的关系属性
	For r In (Select X1.Id, X3.Code, X1.Entitymgttemplateid
							From Xm_Entitymgttemplaterelation X1, Xm_Entityspec2relationspec X2, Xm_Relationspec X3
						 Where X1.Spec2relationid = X2.Id
							 And X2.Relationspecid = X3.Id
							 And X1.Relamgttemplateid In (Select Id
																							From Xm_Entitymgttemplate
																						 Where Entityspecid = v_Entityspecid)) Loop
		Delete From Xm_Entitymgttemplaterelation
		 Where Id = r.Id;
	
		Delete From Xm_Mgttemplatefrmelement
		 Where Mgttemplatefrmgrpid In (Select g.Id
																		 From Xm_Mgttemplatefrm f, Xm_Mgttemplatefrmgrp g
																		Where g.Mgttemplatefrmid = f.Id
																			And f.Entitymgttemplateid = r.Entitymgttemplateid)
			 And Name Like '''' || r.Code || '/_%'' escape ''/''';
	
		Delete From Xm_Mgttemplatetblelement
		 Where Mgttemplatetblid In (Select Tb.Id
																	From Xm_Mgttemplatetbl Tb
																 Where Tb.Entitymgttemplateid = r.Entitymgttemplateid)
			 And Name Like '''' || r.Code || '/_%'' escape ''/''';
	
	End Loop;

	--删除和组件的关系
	Delete From Xm_Comptemplateref
	 Where Xm_Comptemplateref.Entitymgttemplateid In
				 (Select Id
						From Xm_Entitymgttemplate
					 Where Entityspecid = v_Entityspecid);

	--模版
	Delete From Xm_Entitymgttemplate
	 Where Entityspecid = v_Entityspecid;

	--删除关系
	Delete From Xm_Entityspec2relationspec
	 Where Entityspecid = v_Entityspecid
	 Or Relationspecid in (select id From Xm_Relationspec
 																	Where Ownerentityspecid = v_Entityspecid
																		 Or Supplierentityspecid = v_Entityspecid);
	Delete From Xm_Entityspec2componentspec
	 Where Entityspecid = v_Entityspecid;
	Delete From Xm_Relationspec
	 Where Ownerentityspecid = v_Entityspecid
			Or Supplierentityspecid = v_Entityspecid;
	Delete From Xm_Entitydescriptor
	 Where Entityspecid = v_Entityspecid;
	Delete From Xm_Entityspec
	 Where Id = v_Entityspecid;

	Commit;

End p_Deleteentityspec;
/
