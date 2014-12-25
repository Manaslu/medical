package com.teamsun.thunderreport.scheduler;

import java.util.Date;



public class EmsRtpShutdownJob implements JobService{

	public void doJob() {
		// TODO Auto-generated method stub
		System.out.println("do job"+(new Date()).toString());
	}

}
