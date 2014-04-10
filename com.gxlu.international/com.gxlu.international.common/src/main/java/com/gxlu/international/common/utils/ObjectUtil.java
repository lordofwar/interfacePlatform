package com.gxlu.international.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ObjectUtil {
	public static boolean isEmpty(Object obj, String ...args) {
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			boolean exclude = false;
			if (args != null && args.length > 0) {
				for (String exAttr : args) {
					if (exAttr.equalsIgnoreCase(fieldName)) {
						exclude = true;
					}
				}
			}
			if (exclude)
				continue;
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getMethodName = "get" + firstLetter + fieldName.substring(1);
			try {
				Method getMethod = clazz.getMethod(getMethodName, new Class[] {});
				Object value = getMethod.invoke(obj, new Object[] {});
				if (value != null && !value.equals("")) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;

	}

}
