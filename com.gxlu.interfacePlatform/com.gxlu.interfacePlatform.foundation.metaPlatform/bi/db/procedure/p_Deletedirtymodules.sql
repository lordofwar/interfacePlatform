--删除找不到表结构或属性的元数据描述
Create or Replace Procedure p_Deletedirtymodules As
	Begin
	--表结构描述错误,系统中无此表结构;删除表结构描述
		FOR r1 In (Select es.name,es.code
								 From xm_entityspec es
         				Where (es.satellitetablename Is Null And Not Exists (Select 1 From User_tables Where table_name = es.coretablename ))
         				   Or (es.satellitetablename Is Not Null And Not Exists (Select 1 From user_tables Where table_name = es.satellitetablename))) Loop
			Begin
				If r1.code <> 'COMMON' Then
					p_Deleteentityspec(r1.code);
				End If;
				Commit;
    		Exception
    			When Others Then
    		Null;
   		End;
		End Loop;
	--属性描述错误，系统中无此属性;删除属性描述
		For r2 In (Select es.code entityspeccode ,ec.columnname
       					 From xm_entityspec es, xm_entitydescriptor ec
         				Where ec.entityspecid = es. Id 
         				  And ((es.satellitetablename Is Null And Not Exists (Select 1 From User_Tab_Cols Where table_name = es.coretablename And column_name = ec.columnname))
        				   Or (es.satellitetablename Is Not Null And Not Exists (Select 1 From User_Tab_Cols Where table_name = es.satellitetablename And column_name = ec.columnname)))) Loop
			Begin
				p_Deleteentitydescriptor(r2.entityspeccode,r2.columnname);
				Commit;
				Exception
					When Others Then
				Null;
			End;
		End Loop;
	End p_Deletedirtymodules;
/