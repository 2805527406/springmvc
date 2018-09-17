package com.demo.quartz.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.quartz.SchedulerException;

import com.demo.quartz.entity.JobInfo;

public interface IQuartzJobMng {
	/**
	 * 删除触发器
	 * 
	 * @param triggerName
	 * @param group
	 * @return
	 * @throws Exception 
	 */
	public boolean removeTrigdger(String triggerName, String group) throws Exception;
	
	/**
	 * 重启作业
	 * 
	 * @param triggerName
	 * @param group
	 * @throws Exception 
	 */
	public void resumeTrigger(String triggerName, String group) throws Exception;
	
	/**
	 * 暂停作业
	 * 
	 * @param triggerName
	 * @param group
	 * @throws Exception 
	 */
	public void pauseTrigger(String triggerName, String group) throws Exception;
	
	/**
	 * 新增作业
	 * 
	 * @Title: scheduleJob
	 * @Description: TODO
	 * @param jobName
	 *            作业名称
	 * @param group
	 *            作业和触发器所在分组
	 * @param job_class_name
	 *            作业的类名称，必须是全限定类名 如：com.acca.xxx.service（不能是接口）
	 * @param triggerName
	 *            触发器名称
	 * @param cronExpression
	 *            时间规则
	 * @param description
	 *            描述
	 * @throws SchedulerException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public void scheduleJob(String jobName, String group,
			String job_class_name, String triggerName, String cronExpression,
			String description) throws ParseException, ClassNotFoundException, SchedulerException;
	
	/**
	 * 更新触发器的时间规则
	 * 
	 * @Title: rescheduleJob
	 * @Description: TODO
	 * @param triggerName
	 *            触发器名称
	 * @param group
	 *            触发器分组
	 * @param cronExpression
	 *            时间规则
	 * @throws SchedulerException 
	 * @throws ParseException 
	 */
	public void rescheduleJob(String triggerName, String group,
			String cronExpression) throws ParseException, SchedulerException;
	
	/**
	 * * 立即执行作业，不考虑时间规则
	 * 
	 * @Title: triggerJob
	 * @Description: TODO
	 * @param jobName
	 *            作业名称
	 * @param group
	 *            作业分组
	 * @throws SchedulerException 
	 */
	public void triggerJob(String jobName,String triggerName,  String group) throws SchedulerException;
	  /**
     * 查询作业信息
     * 
     * @Title: queryInfo
     * @Description: TODO
     * @param params
     * @return
     * @return: List<JobInfo>
     */
	public List<JobInfo> queryInfo(Map<String, String> params);

}
