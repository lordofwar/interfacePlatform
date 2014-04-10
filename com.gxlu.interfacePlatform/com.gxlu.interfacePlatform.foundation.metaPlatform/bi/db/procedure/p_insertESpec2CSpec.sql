create or replace procedure p_insertESpec2CSpec(p_entitySpecCode    in varchar2,
																								p_ComponentSpecCode in Varchar2) as
/**created by wxy 200903 增加实体规格同组件规格的关系*/
Begin

	--p_entitySpecName := 'ADSLPORT'
	--p_componentSpecCode := 'ADSLPORT'

	Insert into XM_ENTITYSPEC2COMPONENTSPEC
		(ID, ENTITYSPECID, ENTITYSPECVERSIONINSTANCE, COMPONENTSPECID, COMPONENTSPECVERSIONINSTANCE, COMPONENTSPECALIAS,
		 STATUS, VERSION, MAINTENACEINDICATOR)
	Values
		(S_XM_ENTITYSPEC2COMPONENTSPEC.Nextval,
		 (Select id
				 From xm_entityspec
				Where code = p_entitySpecCode),
		 (Select id
				 From x_versionInstance
				Where subcategory = p_entitySpecCode And rownum = 1),
		 (Select id
				 From xm_componentSpec
				Where code = p_ComponentSpecCode),
		 (Select id
				 From x_versionInstance
				Where subcategory = p_ComponentSpecCode And rownum = 1),
		 (Select Name
				 From xm_componentSpec
				Where code = p_ComponentSpecCode), 1, 1, 3);

    /**
    Insert into XM_ENTITYSPEC2COMPONENTSPEC
       (ID, ENTITYSPECID, ENTITYSPECVERSIONINSTANCE, COMPONENTSPECID, COMPONENTSPECVERSIONINSTANCE, COMPONENTSPECALIAS, STATUS, VERSION, MAINTENACEINDICATOR)
     Values
       (87, 49, 387, 25, 383, 'ADSL端口', 1, 1, 2);
    */
end;
/
