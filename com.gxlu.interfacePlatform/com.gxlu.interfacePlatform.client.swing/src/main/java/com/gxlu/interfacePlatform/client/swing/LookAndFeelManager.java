package com.gxlu.interfacePlatform.client.swing;

import javax.swing.UIManager;

public class LookAndFeelManager {
	public static void setLookAndFeel(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		} 
	}
}
