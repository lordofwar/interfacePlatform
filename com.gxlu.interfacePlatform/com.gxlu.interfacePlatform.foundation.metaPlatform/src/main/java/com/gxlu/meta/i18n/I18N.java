package com.gxlu.meta.i18n;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * 
 * @author Jame
 *
 */
public class I18N {
	
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("metaPlatform",Locale.getDefault());
	
	public static String getString(String key){
		return resourceBundle.getString(key);
	}
}

  