package com.demo.quartz.ramjob;


import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RAMJob implements Job{
	private static Logger log = LoggerFactory.getLogger(RAMJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Say hello to Quartz "+ new Date());
	}
	
}
