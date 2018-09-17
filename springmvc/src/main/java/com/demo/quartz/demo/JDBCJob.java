package com.demo.quartz.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JDBCJob implements Job{
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("job is start ---------------");
		System.out.println("hello quartz "+new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
		System.out.println("job is end -----------------");
	}
}
