package com.demo.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job{
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("spring quartz starts work ");
		System.out.println("work time : "+new Date());
		System.out.println("spring quartz finish work ");
	}
}
