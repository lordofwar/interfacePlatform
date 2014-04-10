package com.gxlu.meta;
import com.gxlu.meta.cfg.Configuration;
import com.gxlu.meta.tools.XmlReaderException;

public class MetaServiceFacade {
	private static MetaDBManager metaManager = null;
	public static MetaDBManager getMetaDbManager() {
		if(metaManager==null){
			try {
				metaManager = (new Configuration("conf/meta.cfg.xml")).getMetaDBManager();
			} catch (XmlReaderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return metaManager;
	}
}
