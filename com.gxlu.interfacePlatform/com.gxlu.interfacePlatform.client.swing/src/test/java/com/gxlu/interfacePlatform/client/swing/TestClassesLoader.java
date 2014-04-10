package com.gxlu.interfacePlatform.client.swing;

import java.util.Set;

import com.gxlu.interfacePlatform.schedule.plugins.AutoClassLoader;

public class TestClassesLoader {
	public static void main(String[] args) {
		Set<Class<?>> classes=AutoClassLoader.getClasses("com.gxlu.interfacePlatform");
		for(Class<?> clazz :classes){
//			System.out.println(clazz.getCanonicalName());
			
			if(clazz.getCanonicalName()!=null){
				System.out.println(clazz);
			}
		}
	}
}
