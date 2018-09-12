package com.demo.quartz.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWordTest {
	  private static Scheduler scheduler;
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:/spring-quartz.xml");
		 scheduler = (StdScheduler)ac.getBean("scheduler");

         startSchedule();
	}
	
	 public static void startSchedule() {
	        try {
	            // 1������һ��JobDetailʵ����ָ��Quartz
	            JobDetail jobDetail = JobBuilder.newJob(HelloWordJob.class)
	            // ����ִ����
	                    .withIdentity("job1_1", "jGroup1")
	                    // ��������������
	                    .build();

	            // ����������
	            //SimpleScheduleBuilder builder = SimpleScheduleBuilder
	                    // ����ִ�д���
	                    //.repeatSecondlyForTotalCount(5);

	            CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");
	            // 2������Trigger
	            Trigger trigger = TriggerBuilder.newTrigger()
	                    .withIdentity("trigger1_1", "tGroup1").startNow()
	                    .withSchedule(builder)
	                    .build();

	            // 3������Scheduler
	            scheduler = StdSchedulerFactory.getDefaultScheduler();
	            scheduler.start();
	            // 4������ִ��
	            scheduler.scheduleJob(jobDetail, trigger);

	        } catch (SchedulerException e) {
	            e.printStackTrace();
	        }
	    }
}