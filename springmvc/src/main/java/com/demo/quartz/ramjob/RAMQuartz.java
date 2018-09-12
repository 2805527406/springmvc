package com.demo.quartz.ramjob;

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

public class RAMQuartz {
	public static void main(String[] args) throws SchedulerException {
		//创建工厂
		SchedulerFactory sf = new StdSchedulerFactory();
		
		//从工厂中获取调度器是咧
		Scheduler scheduler = sf.getScheduler();
		
		//创建jobDetail
		JobDetail jb = (JobDetail) JobBuilder.newJob(RAMJob.class)
				.withDescription("this is a ram job")//job描述
				.withIdentity("ramJob","ramGroup")//job 的name和group
				.build();
		//任务运行时间，SimpleSchedle类型触发器有效
		long time = System.currentTimeMillis()+3*1000L;//3秒后启动任务
		Date startTime= new Date(time);
		
		//创建Trigger
		Trigger t = TriggerBuilder.newTrigger()
				.withDescription("")
				.withIdentity("ramTrigger","ramTriggerGroup")
//				.withSchedule(SimpleScheduleBuilder.simpleSchedule())
				.startAt(startTime)
				.withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))//每二秒执行一次
				.build();
		
		//注册任务和定时器
		scheduler.scheduleJob(jb,t);
		
		//启动调度器
		scheduler.start();
		System.out.println("启动时间："+new Date());
	}
}
