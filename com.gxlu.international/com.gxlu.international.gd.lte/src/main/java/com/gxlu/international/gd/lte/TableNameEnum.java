package com.gxlu.international.gd.lte;

import java.util.HashMap;
import java.util.Map;

public enum TableNameEnum {
	NE_TABLE("XMID_ENODEBNE_HW"), 
	eNodeBCell("XMID_ENODEBCELL_HW"), 
	eNodeBDevIP("XMID_ENODEBDEVIP_HW"), 
	eNodeBEUtranExternalCell("XMID_ENODEBEUTEXCELL_HW"), 
	eNodeBRRU("XMID_ENODEBRRU_HW"), 
	eNodeBRscGroup("XMID_ENODEBRSCGRP_HW"), 
	eNodeBSector("XMID_ENODEBSECTOR_HW");

	public String tableName;

	private TableNameEnum(String tableName) {
		this.tableName = tableName;
	}

	public static Map<String,String> getAttrCorrectMap(String tableName) {
		Map<String,String> correctMap = new HashMap<String,String>();
		if(TableNameEnum.NE_TABLE.tableName.equals(tableName)){
			//没有
		}else if(TableNameEnum.eNodeBCell.equals(tableName)){
			correctMap.put("SectorNo", "SectorId");
		}else if(TableNameEnum.eNodeBDevIP.tableName.equals(tableName)){
			correctMap.put("CN", "CabinetNo");
			correctMap.put("SRN", "SubrackNo");
			correctMap.put("SN", "SlotNo");
			correctMap.put("PT", "PortType");
			correctMap.put("PN", "PortNo");
			correctMap.put("SBT", "SubboardType");
		}else if(TableNameEnum.eNodeBEUtranExternalCell.tableName.equals(tableName)){
			//没有
		}else if(TableNameEnum.eNodeBRRU.tableName.equals(tableName)){
			correctMap.put("CN", "CabinetNo");
			correctMap.put("SRN", "SubrackNo");
			correctMap.put("SN", "SlotNo");
		}else if(TableNameEnum.eNodeBRscGroup.tableName.equals(tableName)){
			correctMap.put("CN", "CabinetNo");
			correctMap.put("SRN", "SubrackNo");
			correctMap.put("SN", "SlotNo");
			correctMap.put("BEAR", "BearType");
			correctMap.put("SBT", "SubboardType");
			correctMap.put("PT", "PhyPortType");
			correctMap.put("PN", "PhyPortNo");
			correctMap.put("RSCGRPID", "RscGrpNo");
			correctMap.put("RU", "RateUnit");
			correctMap.put("OID", "OperatorID");
			correctMap.put("WEIGHT", "SchedulerWeight");
		}else if(TableNameEnum.eNodeBSector.tableName.equals(tableName)){
			correctMap.put("TxRxMode", "AntennaMode");
			correctMap.put("SECTORID", "SectorNo");
			correctMap.put("SECNAME", "SectorName");
		}
		return correctMap;
	}

}
