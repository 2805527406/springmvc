package com.demo.quartz.job;

import java.text.SimpleDateFormat;
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

public class RAMTest {
	public static void main(String[] args) throws SchedulerException {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();
		JobDetail jobDetail = JobBuilder.newJob(RAMJob.class)
				.withDescription("job description")
				.withIdentity("jobRam","jobGroupRam")
				.build();
		Trigger trigger = TriggerBuilder.newTrigger()
				.withDescription("trigger description")
				.withIdentity("triggerRam","triggerGroupRam")
				.startAt(new Date(System.currentTimeMillis() + 3*1000l))
				.withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
				.build();
		
		scheduler.scheduleJob(jobDetail,trigger);
		scheduler.start();
		System.out.println("Æô¶¯Ê±¼ä£º"+new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
	}
}
