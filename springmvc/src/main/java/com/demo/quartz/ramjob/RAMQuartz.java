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
		//��������
		SchedulerFactory sf = new StdSchedulerFactory();
		
		//�ӹ����л�ȡ����������
		Scheduler scheduler = sf.getScheduler();
		
		//����jobDetail
		JobDetail jb = (JobDetail) JobBuilder.newJob(RAMJob.class)
				.withDescription("this is a ram job")//job����
				.withIdentity("ramJob","ramGroup")//job ��name��group
				.build();
		//��������ʱ�䣬SimpleSchedle���ʹ�������Ч
		long time = System.currentTimeMillis()+3*1000L;//3�����������
		Date startTime= new Date(time);
		
		//����Trigger
		Trigger t = TriggerBuilder.newTrigger()
				.withDescription("")
				.withIdentity("ramTrigger","ramTriggerGroup")
//				.withSchedule(SimpleScheduleBuilder.simpleSchedule())
				.startAt(startTime)
				.withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))//ÿ����ִ��һ��
				.build();
		
		//ע������Ͷ�ʱ��
		scheduler.scheduleJob(jb,t);
		
		//����������
		scheduler.start();
		System.out.println("����ʱ�䣺"+new Date());
	}
}
