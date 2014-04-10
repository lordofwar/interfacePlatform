package com.gxlu.interfacePlatform.schedule;

public class TestProgress {

	public static void main(String[] args) {
		new ProgressThread(new Schedule()).start();
		
	}
	
}
