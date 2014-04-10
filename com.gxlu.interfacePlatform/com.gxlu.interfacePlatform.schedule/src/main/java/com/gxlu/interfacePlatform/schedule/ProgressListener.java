package com.gxlu.interfacePlatform.schedule;

public interface ProgressListener {

	/**
	 * While each schedule provide the feedback of progress using setProgress,this interface will be run.
	 * @param rate
	 */
	public void onChangingProgress(Schedule schedule , int rate);
}
