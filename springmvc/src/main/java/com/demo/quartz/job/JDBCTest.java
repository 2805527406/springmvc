package com.demo.quartz.job;

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
		startScheduler();
	}
	
	 /**
     * 开始一个simpleSchedule()调度
     */
	public static void startScheduler() {
		try {
			JobDetail jobDetail = JobBuilder.newJob(JDBCJob.class)
					.withDescription("jdbcJob1 description")
					.withIdentity("jdbcJob1","jdbcJobGroup1")
					//Simple类型的如果JobDetail没有设置.storeDurably(true)，则job在运行完成之后会在数据库中删除
					.storeDurably(true)
					.build();
			Trigger trigger = TriggerBuilder.newTrigger()
					.withDescription("jdbcTrigger description")
					.withIdentity("jdbcTrigger","jdbcTriggerGroup")
					.startNow()
					.withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(5))
					.build();
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			Thread.sleep(3000);
			scheduler.scheduleJob(jobDetail, trigger);
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
			JobKey jobKey = new JobKey("jdbcJob","jdbcJobGroup");
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			if(triggers != null && triggers.size() > 0) {
				for(Trigger trigger:triggers) {
					 if ((trigger instanceof CronTrigger) || (trigger instanceof SimpleTrigger)) {
	                        // 恢复job运行
	                        scheduler.resumeJob(jobKey);
	                    }
				}
				scheduler.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
