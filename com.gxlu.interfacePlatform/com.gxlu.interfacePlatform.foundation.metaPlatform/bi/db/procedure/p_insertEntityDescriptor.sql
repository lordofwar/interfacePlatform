CREATE OR REPLACE Procedure p_Insertentitydescriptor(p_Entityspeccode In Varchar2,
                                                     p_Tablename      In Varchar2) As
  v_Columntype    Number(5);
  v_Datalength    Number(5);
  v_Datapercision Number(5);
  v_Entityspecid  Number(18);
  v_Count         Number(18);
  /**created by wxy 200903 增加实体描述*/
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
            v_Datalength := r.Data_Precision;
          Elsif r.Data_Precision Between 1 And 5 Then
            v_Columntype := 10; --字典
            v_Datalength := r.Data_Precision;
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
      Elsif (r.Data_Type = 'BLOB') Then
        v_Columntype    := 7;
        v_Datalength    := NULL;
      Elsif (r.Data_Type = 'CLOB') Then
        v_Columntype    := 8;
        v_Datalength    := NULL;
      Else
        v_Columntype    := Null;
        v_Datalength    := Null;
        v_Datapercision := Null;
      End If;
    
      Select Count(*)
        Into v_Count
        From Xm_Entitydescriptor
       Where Entityspecid = (Select Id From Xm_Entityspec Where Code = p_Entityspeccode)
         And Columnname = r.Column_Name;
    
      If v_Count = 0 Then
        Insert Into Xm_Entitydescriptor
          (Id, Entityspecid, Entityspecversioninstance, Columnname, Attrname, Attrdispname, Columntype, Datatype,
           Datalength, Datapercision, Createdate)
        Values
          (s_Xm_Entitydescriptor.Nextval, (Select Id From Xm_Entityspec Where Code = p_Entityspeccode),
           (Select Versioninstance From Xm_Entityspec Where Code = p_Entityspeccode), r.Column_Name, r.Column_Name,
           r.Comments, v_Columntype, v_Columntype, v_Datalength, v_Datapercision, Sysdate);
      End If;
    
    End;
  End Loop;

  Select Id Into v_Entityspecid From Xm_Entityspec Where Code = p_Entityspeccode;

  Update Xm_Entitydescriptor
     Set Dicclassname = 'COMMON', Dicattributename = 'BOOLEAN'
   Where Entityspecid = v_Entityspecid
     And Datatype = 10
     And (Columnname Like 'IS%' Or Columnname Like 'HAS%')
     And Dicclassname Is Null;

  Update Xm_Entitydescriptor
     Set Dicclassname = p_Entityspeccode, Dicattributename = Columnname
   Where Entityspecid = v_Entityspecid
     And Datatype = 10
     And Dicclassname Is Null;

  Update Xm_Entitydescriptor
     Set Ispk = 1
   Where Columnname = 'ID'
     And Entityspecid = v_Entityspecid;

  Update Xm_Entitydescriptor
     Set Isfk = 1
   Where Datatype = 2
     And Datalength = 18
     And Ispk = 2
     And Entityspecid = v_Entityspecid;

  Update Xm_Entitydescriptor
     Set Isversion = 1
   Where Columnname = 'VERSION'
     And Entityspecid = v_Entityspecid;

  Update x_Versioninstance
     Set Entityid = (Select Id
                        From Xm_Entityspec
                       Where Code = p_Entityspeccode
                         And Rownum = 1)
   Where Subcategory = p_Entityspeccode;

  /**
  --insert into versioninstance
  --insert into entityspec
  
  --update xm_entitydescriptor set dicclassname = 'PORT' , dicattributename= columnname where entityspecid= 37 and datatype=10;
  --update xm_entitydescriptor set ispk = 1 where columnName = 'ID' and entityspecid = 37;
  --update xm_entitydescriptor set isversion = 1 where columnName = 'VERSION' and entityspecid = 37;
  
   2 long
   3 float
   4 String
   5 Date
   10 数据字典
   14 int
  */

End;
/
