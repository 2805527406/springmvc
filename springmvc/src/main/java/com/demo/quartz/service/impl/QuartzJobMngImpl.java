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
    private Scheduler scheduler;// ��ҵ������

    @Autowired
    private IQuartzJobDao quartzDao;

    /**
     * ɾ��������
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
	    scheduler.pauseTrigger(triggerKey);// ֹͣ������
	    return scheduler.unscheduleJob(triggerKey);// �Ƴ�������
	} catch (SchedulerException e) {
	    throw new Exception("ɾ����ҵʱ�����쳣", e);
	}
    }

    /**
     * ������ҵ
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
	    throw new Exception("������ҵʱ�����쳣", e);
	}
    }

    /**
     * ��ͣ��ҵ
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
	    throw new Exception("��ͣ��ҵʱ�����쳣", e);
	}
    }

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
     * @throws ParseException
     *             �����쳣
     * @throws SchedulerException
     *             �������쳣
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
     * ����ִ����ҵ��������ʱ�����
     * 
     * @Title: triggerJob
     * @Description: TODO
     * @param jobName
     *            ��ҵ����
     * @param group
     *            ��ҵ����
     * @throws SchedulerException
     *             �������쳣
     * @return: void
     */
    @Override
    public void triggerJob(String jobName,String triggerName, String group) throws SchedulerException {

	JobKey jobKey = new JobKey(jobName, group);

	try {
	    scheduler.triggerJob(jobKey);
	    quartzDao.updatePreFireTime(triggerName);//Ϊʲô �����Զ�����
	} catch (SchedulerException e) {
	    throw new SchedulerException("����ִ����ҵʱ�����쳣", e);
	}
    }
    
    /**
     * ��ѯ��ҵ��Ϣ
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
     * ��ȡ������
     * 
     * @Title: getTrigger
     * @Description: TODO
     * @param triggerName
     *            ����������
     * @param group
     *            ����������
     * @param cronExpression
     *            ������ʱ�����
     * @return
     * @throws ParseException
     *             �����쳣
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
     * ��ȡ��������key
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
     * ����Jobdetail��
     * 
     * @Title: getJobDetail
     * @Description: TODO
     * @param jobName
     *            ��ҵ����
     * @param group
     *            ��ҵ������
     * @param job_class_name
     *            ��ҵ��Ȩ�޶�����
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
	    throw new ClassNotFoundException(job_class_name + "�����ڻ�����д����ȷ", e);
	}
    }

    /**
     * ����������
     * 
     * @Title: getTrigger
     * @Description: TODO
     * @param triggerName
     *            ����������
     * @param group
     *            ������������
     * @param cronExpression
     *            ��������ʱ�����
     * @return
     * @throws ParseException
     *             ����ʱ������쳣
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
