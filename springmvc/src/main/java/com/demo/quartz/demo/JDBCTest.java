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
					.withDescription("job˵��1")
					.withIdentity("job1", "jobGroup1")
					.build();
			Trigger trigger = TriggerBuilder.newTrigger()
					.withDescription("trigger˵��1")
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

	            //�رյ�����
	            scheduler.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �����ݿ����ҵ��Ѿ����ڵ�job�������¿�������
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
				 //�رյ�����
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

