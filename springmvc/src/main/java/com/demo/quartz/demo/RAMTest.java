package com.demo.quartz.demo;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RAMTest {
	private static final Logger logger = LoggerFactory.getLogger(RAMTest.class);
	public static void main(String[] args) throws SchedulerException {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();
		
		JobDetail jobDetail = JobBuilder.newJob(RAMJob.class)
				.withDescription("job的描述")
				.withIdentity("ramJob","ramGroup")
				.build();
		
		long time =System.currentTimeMillis()+3*1000L;
		Date startTie = new Date(time);
		
		Trigger t = TriggerBuilder.newTrigger()
				.withDescription("trigger的描述")
				.withIdentity("ramTrigger","ramTriggerGroup")
				.startAt(startTie)
				.withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
				.build();
		scheduler.scheduleJob(jobDetail,t);
		scheduler.start();
		System.out.println("启动时间："+new Date());
	}
}
