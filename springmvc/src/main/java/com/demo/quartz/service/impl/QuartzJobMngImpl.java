package com.demo.quartz.service.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.demo.quartz.dao.IQuartzJobDao;
import com.demo.quartz.entity.JobInfo;
import com.demo.quartz.service.IQuartzJobMng;

public class QuartzJobMngImpl implements IQuartzJobMng{
	@Autowired
	@Qualifier("quartzScheduler")
    private Scheduler scheduler;// 作业调度器

    @Autowired
    private IQuartzJobDao quartzDao;

    /**
     * 删除触发器
     * 
     * @Title: removeTrigdger
     * @Description: TODO
     * @param triggerName
     * @param group
     * @return
     * @return: boolean
     * @throws Exception
     */
    @Override
    public boolean removeTrigdger(String triggerName, String group) throws Exception {

	TriggerKey triggerKey = geTriggerKey(triggerName, group);
	try {
	    scheduler.pauseTrigger(triggerKey);// 停止触发器
	    return scheduler.unscheduleJob(triggerKey);// 移除触发器
	} catch (SchedulerException e) {
	    throw new Exception("删除作业时发生异常", e);
	}
    }

    /**
     * 重启作业
     * 
     * @Title: resumeTrigger
     * @Description: TODO
     * @param triggerName
     * @param group
     * @throws Exception
     * @return: void
     */
    @Override
    public void resumeTrigger(String triggerName, String group) throws Exception {

	TriggerKey triggerKey = geTriggerKey(triggerName, group);

	try {
	    scheduler.resumeTrigger(triggerKey);
	} catch (SchedulerException e) {
	    throw new Exception("重启作业时发生异常", e);
	}
    }

    /**
     * 暂停作业
     * 
     * @Title: pauseTrigger
     * @Description: TODO
     * @param triggerName
     * @param group
     * @return: void
     * @throws Exception
     */
    @Override
    public void pauseTrigger(String triggerName, String group) throws Exception {

	TriggerKey triggerKey = geTriggerKey(triggerName, group);

	try {
	    scheduler.pauseTrigger(triggerKey);
	} catch (SchedulerException e) {
	    throw new Exception("暂停作业时发生异常", e);
	}
    }

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
     * @return: void
     * @throws ParseException
     * @throws ClassNotFoundException
     * @throws SchedulerException
     */
    @Override
    public void scheduleJob(String jobName, String group, String job_class_name, String triggerName,
	    String cronExpression, String description)
		    throws ParseException, ClassNotFoundException, SchedulerException {

	try {
	    JobDetail jobDetail = getJobDetail(jobName, group, job_class_name, description);
	    Trigger trigger = getTrigger(triggerName, group, cronExpression, description);
	    scheduler.scheduleJob(jobDetail, trigger);
	} catch (ClassNotFoundException e) {
	    throw e;
	} catch (ParseException e) {
	    throw e;
	} catch (SchedulerException e) {
	    throw e;
	}
    }

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
     * @throws ParseException
     *             解析异常
     * @throws SchedulerException
     *             调度器异常
     * @return: void
     */
    @Override
    public void rescheduleJob(String triggerName, String group, String cronExpression)
	    throws ParseException, SchedulerException {

	TriggerKey triggerKey = geTriggerKey(triggerName, group);
	try {
	    Trigger newTrigger = getTrigger(triggerName, group, cronExpression);
	    scheduler.rescheduleJob(triggerKey, newTrigger);
	} catch (ParseException e) {
	    throw e;
	} catch (SchedulerException e) {
	    throw e;
	}

    }

    /**
     * 立即执行作业，不考虑时间规则
     * 
     * @Title: triggerJob
     * @Description: TODO
     * @param jobName
     *            作业名称
     * @param group
     *            作业分组
     * @throws SchedulerException
     *             调度器异常
     * @return: void
     */
    @Override
    public void triggerJob(String jobName,String triggerName, String group) throws SchedulerException {

	JobKey jobKey = new JobKey(jobName, group);

	try {
	    scheduler.triggerJob(jobKey);
	    quartzDao.updatePreFireTime(triggerName);//为什么 不是自动更新
	} catch (SchedulerException e) {
	    throw new SchedulerException("立即执行作业时发生异常", e);
	}
    }
    
    /**
     * 查询作业信息
     * 
     * @Title: queryInfo
     * @Description: TODO
     * @param params
     * @return
     * @return: List<JobInfo>
     */
    public List<JobInfo> queryInfo(Map<String, String> params) {

	return quartzDao.getList(params);

    }

    /**
     * 获取出发器
     * 
     * @Title: getTrigger
     * @Description: TODO
     * @param triggerName
     *            触发器名称
     * @param group
     *            触发器分组
     * @param cronExpression
     *            触发器时间规则
     * @return
     * @throws ParseException
     *             解析异常
     * @return: Trigger
     */
    private Trigger getTrigger(String triggerName, String group, String cronExpression) throws ParseException {

	@SuppressWarnings("deprecation")
	CronTriggerImpl triggerImpl = new CronTriggerImpl(triggerName, group);
	try {
	    triggerImpl.setCronExpression(cronExpression);
	} catch (ParseException e) {
	    throw e;
	}
	return triggerImpl;
    }

    /**
     * 获取触发器的key
     * 
     * @Title: geTriggerKey
     * @Description: TODO
     * @param triggerName
     * @param group
     * @return
     * @return: TriggerKey
     */
    private TriggerKey geTriggerKey(String triggerName, String group) {

	TriggerKey triggerKey = new TriggerKey(triggerName, group);

	return triggerKey;
    }

    /**
     * 创建Jobdetail类
     * 
     * @Title: getJobDetail
     * @Description: TODO
     * @param jobName
     *            作业名称
     * @param group
     *            作业所在组
     * @param job_class_name
     *            作业的权限定类名
     * @param description
     * @return
     * @throws ClassNotFoundException
     * @return: JobDetail
     */
    private JobDetail getJobDetail(String jobName, String group, String job_class_name, String description)
	    throws ClassNotFoundException {

	try {
	    @SuppressWarnings({ "deprecation", "unchecked" })
		JobDetailImpl detail = new JobDetailImpl(jobName, group,
		    (Class<? extends Job>) Class.forName(job_class_name));
	    detail.setDescription(description);
	    return detail;
	} catch (ClassNotFoundException e) {
	    throw new ClassNotFoundException(job_class_name + "不存在或者书写不正确", e);
	}
    }

    /**
     * 创建触发器
     * 
     * @Title: getTrigger
     * @Description: TODO
     * @param triggerName
     *            触发器名称
     * @param group
     *            触发器所在组
     * @param cronExpression
     *            触发器的时间规则
     * @return
     * @throws ParseException
     *             解析时间规则异常
     * @return: Trigger
     */
    private Trigger getTrigger(String triggerName, String group, String cronExpression, String description)
	    throws ParseException {

	@SuppressWarnings("deprecation")
	CronTriggerImpl triggerImpl = new CronTriggerImpl(triggerName, group);
	triggerImpl.setDescription(description);
	try {
	    triggerImpl.setCronExpression(cronExpression);
	} catch (ParseException e) {
	    throw e;
	}
	return triggerImpl;
    }

}
