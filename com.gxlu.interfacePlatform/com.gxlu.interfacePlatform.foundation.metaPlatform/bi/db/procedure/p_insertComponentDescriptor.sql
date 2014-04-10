Create Or Replace Procedure p_Insertcomponentdescriptor(p_Componentspeccode In Varchar2,
																												p_Tablename         In Varchar2) As
	v_Columntype    Number(5);
	v_Datalength    Number(5);
	v_Datapercision Number(5);
	v_Count         Number(5);
	/**created by wxy 200903 增加组件的描述*/
Begin
	For r In (Select Utc.Column_Name, Utc.Data_Type, Utc.Data_Length, Utc.Data_Precision, Utc.Data_Scale, Ucc.Comments
							From User_Tab_Columns Utc, User_Col_Comments Ucc
						 Where Utc.Table_Name = Ucc.Table_Name
							 And Utc.Column_Name = Ucc.Column_Name
							 And Utc.Table_Name = p_Tablename
							 And Ucc.Table_Name = p_Tablename
							 And Ucc.Comments Is Not Null) Loop
		Begin
		
			v_Columntype    := Null;
			v_Datalength    := 0;
			v_Datapercision := 0;
		
			If r.Data_Type = 'VARCHAR2' Then
				v_Columntype := 4;
				v_Datalength := r.Data_Length;
			Elsif (r.Data_Type = 'NUMBER' And r.Data_Scale = 0) Then
				Begin
					If r.Data_Precision = 18 Then
						v_Columntype := 2; --id,fkid
						v_Datalength := 18;
					Elsif r.Data_Precision Between 6 And 17 Then
						v_Columntype := 14; --int
						v_Datalength := 15;
					Elsif r.Data_Precision Between 1 And 5 Then
						v_Columntype := 10; --字典
						v_Datalength := 5;
					Else
						v_Columntype := Null; --字典
						v_Datalength := Null;
					End If;
				End;
			Elsif (r.Data_Type = 'NUMBER' And r.Data_Scale > 0) Then
				v_Columntype    := 3; --float
				v_Datalength    := r.Data_Precision;
				v_Datapercision := r.Data_Scale;
			Elsif (r.Data_Type = 'DATE') Then
				v_Columntype    := 5;
				v_Datalength    := r.Data_Precision;
				v_Datapercision := r.Data_Scale;
			Else
				v_Columntype    := Null;
				v_Datalength    := Null;
				v_Datapercision := Null;
			End If;
		
			Select Count(*)
				Into v_Count
				From Xm_Componentdescriptor
			 Where Componentspecid = (Select Id From Xm_Componentspec Where Code = p_Componentspeccode)
				 And Columnname = r.Column_Name;
		
			If v_Count = 0 Then
				Insert Into Xm_Componentdescriptor
					(Id, Componentspecid, Componentversioninstance, Columnname, Attrname, Attrdispname, Columntype, Datatype,
					 Datalength, Datapercision, Createdate)
				Values
					(s_Xm_Entitydescriptor.Nextval, (Select Id From Xm_Componentspec Where Code = p_Componentspeccode),
					 (Select Versioninstance From Xm_Componentspec Where Code = p_Componentspeccode), r.Column_Name, r.Column_Name,
					 r.Comments, v_Columntype, v_Columntype, v_Datalength, v_Datapercision, Sysdate);
			End If;
		
		End;
	End Loop;

	Update Xm_Componentdescriptor
		 Set Dicclassname = 'COMMON', Dicattributename = 'BOOLEAN'
	 Where Componentspecid = (Select Id From Xm_Componentspec Where Code = p_Componentspeccode)
		 And Datatype = 10
		 And (Columnname Like 'IS%' Or Columnname Like 'HAS%')
		 And Dicclassname Is Null;

	Update Xm_Componentdescriptor
		 Set Dicclassname = p_Componentspeccode, Dicattributename = Columnname
	 Where Componentspecid = (Select Id From Xm_Componentspec Where Code = p_Componentspeccode)
		 And Datatype = 10
		 And Dicclassname Is Null;

	Update Xm_Componentdescriptor
		 Set Ispk = 1
	 Where Columnname = 'ID'
		 And Componentspecid = (Select Id From Xm_Componentspec Where Code = p_Componentspeccode);

	Update x_Versioninstance
		 Set Entityid = (Select Id From Xm_Componentspec Where Code = p_Componentspeccode)
	 Where Subcategory = p_Componentspeccode;

	/**
    --insert into versioninstance
    --insert into entityspec
  
    --update xm_componentdescriptor set dicclassname = 'PORT' , dicattributename= columnname where componentspecid= 37 and datatype=10;
    --update xm_componentdescriptor set ispk = 1 where columnName = 'ID' and componentspecid = 37;
    --update xm_componentdescriptor set isversion = 1 where columnName = 'VERSION' and componentspecid = 37;
  
     2 long
     3 float
     4 String
     5 Date
     10 数据字典
     14 int
  */

End;
/
