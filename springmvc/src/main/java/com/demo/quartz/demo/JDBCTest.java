package com.demo.quartz.demo;

import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JDBCTest {
	public static void main(String[] args) {
//		startSchedule();
		resumeJob();
	}
	public static void startSchedule() {
		try {
			JobDetail jobDetail = JobBuilder.newJob(JDBCJob.class)
					.withDescription("job说明1")
					.withIdentity("job1", "jobGroup1")
					.build();
			Trigger trigger = TriggerBuilder.newTrigger()
					.withDescription("trigger说明1")
					.withIdentity("trigger1","triggerGroup1")
					.withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(5))
					.build();
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			scheduler.scheduleJob(jobDetail,trigger);
			 try {
	                Thread.sleep(60000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }

	            //关闭调度器
	            scheduler.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从数据库中找到已经存在的job，并重新开户调度
	 */
	public static void resumeJob() {
		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();
			JobKey jobKey = new JobKey("job1","jobGroup1");
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			if (triggers.size() > 0 ) {
				for(Trigger t : triggers) {
					if((t instanceof CronTrigger || t instanceof SimpleTrigger)) {
						scheduler.resumeJob(jobKey);
					}
				}
				scheduler.start();
				 //关闭调度器
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

