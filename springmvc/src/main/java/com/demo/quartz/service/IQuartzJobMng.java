package com.demo.quartz.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.quartz.SchedulerException;

import com.demo.quartz.entity.JobInfo;

public interface IQuartzJobMng {
	/**
	 * ɾ��������
	 * 
	 * @param triggerName
	 * @param group
	 * @return
	 * @throws Exception 
	 */
	public boolean removeTrigdger(String triggerName, String group) throws Exception;
	
	/**
	 * ������ҵ
	 * 
	 * @param triggerName
	 * @param group
	 * @throws Exception 
	 */
	public void resumeTrigger(String triggerName, String group) throws Exception;
	
	/**
	 * ��ͣ��ҵ
	 * 
	 * @param triggerName
	 * @param group
	 * @throws Exception 
	 */
	public void pauseTrigger(String triggerName, String group) throws Exception;
	
	/**
	 * ������ҵ
	 * 
	 * @Title: scheduleJob
	 * @Description: TODO
	 * @param jobName
	 *            ��ҵ����
	 * @param group
	 *            ��ҵ�ʹ��������ڷ���
	 * @param job_class_name
	 *            ��ҵ�������ƣ�������ȫ�޶����� �磺com.acca.xxx.service�������ǽӿڣ�
	 * @param triggerName
	 *            ����������
	 * @param cronExpression
	 *            ʱ�����
	 * @param description
	 *            ����
	 * @throws SchedulerException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public void scheduleJob(String jobName, String group,
			String job_class_name, String triggerName, String cronExpression,
			String description) throws ParseException, ClassNotFoundException, SchedulerException;
	
	/**
	 * ���´�������ʱ�����
	 * 
	 * @Title: rescheduleJob
	 * @Description: TODO
	 * @param triggerName
	 *            ����������
	 * @param group
	 *            ����������
	 * @param cronExpression
	 *            ʱ�����
	 * @throws SchedulerException 
	 * @throws ParseException 
	 */
	public void rescheduleJob(String triggerName, String group,
			String cronExpression) throws ParseException, SchedulerException;
	
	/**
	 * * ����ִ����ҵ��������ʱ�����
	 * 
	 * @Title: triggerJob
	 * @Description: TODO
	 * @param jobName
	 *            ��ҵ����
	 * @param group
	 *            ��ҵ����
	 * @throws SchedulerException 
	 */
	public void triggerJob(String jobName,String triggerName,  String group) throws SchedulerException;
	  /**
     * ��ѯ��ҵ��Ϣ
     * 
     * @Title: queryInfo
     * @Description: TODO
     * @param params
     * @return
     * @return: List<JobInfo>
     */
	public List<JobInfo> queryInfo(Map<String, String> params);

}
