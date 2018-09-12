package com.demo.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JDBCJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("jdbc job work start--------------------");
		System.out.println("jdbc job work time:"+new Date());
		System.out.println("jdbc job wokr end--------------------");
	}

}
