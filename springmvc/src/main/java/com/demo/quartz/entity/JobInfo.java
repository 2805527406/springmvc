package com.demo.quartz.entity;

import java.text.SimpleDateFormat;

import com.demo.quartz.service.TriggerState;


public class JobInfo {
	  // 调度器名称
    private String sched_name;
    // 触发器名称
    private String trigger_name;
    // 触发器分组
    private String trigger_group;
    // 作业名称
    private String job_name;
    // 作业分组
    private String job_group;
    // 触发器功能描述
    private String description;
    // 下次执行时间
    private String next_fire_time;
    // 上次执行时间
    private String prev_fire_time;
    // 优先级
    private String priopity;
    // 触发器状态
    private String trigger_state;
    // 触发器类型
    private String trigger_type;
    // 开始时间
    private String start_time;
    // 结束时间
    private String end_time;
    // 时间规则描述
    private String cron_expression;
    // 作业的类名称
    private String job_class_name;

    public String getSched_name() {
	return sched_name;
    }

    public void setSched_name(String sched_name) {
	this.sched_name = sched_name;
    }

    public String getTrigger_name() {
	return trigger_name;
    }

    public void setTrigger_name(String trigger_name) {
	this.trigger_name = trigger_name;
    }

    public String getTrigger_group() {
	return trigger_group;
    }

    public void setTrigger_group(String trigger_group) {
	this.trigger_group = trigger_group;
    }

    public String getJob_name() {
	return job_name;
    }

    public void setJob_name(String job_name) {
	this.job_name = job_name;
    }

    public String getJob_group() {
	return job_group;
    }

    public void setJob_group(String job_group) {
	this.job_group = job_group;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getTrigger_state() {
	return trigger_state;
    }

    public void setTrigger_state(String trigger_state) {
    	if (TriggerState.STATE_WAITING.equals(trigger_state)) {
    	    this.trigger_state = "等待中";
    	}
    	if (TriggerState.STATE_ACQUIRED.equals(trigger_state)) {
    	    this.trigger_state = "执行中";
    	}
    	if (TriggerState.STATE_COMPLETE.equals(trigger_state)) {
    	    this.trigger_state = "执行完成";
    	}

    	if (TriggerState.STATE_BLOCKED.equals(trigger_state)) {
    	    this.trigger_state = "阻塞中";
    	}
    	if (TriggerState.STATE_PAUSED.equals(trigger_state)) {
    	    this.trigger_state = "暂停";
    	}
    }

    public String getTrigger_type() {
	return trigger_type;
    }

    public void setTrigger_type(String trigger_type) {
	this.trigger_type = trigger_type;
    }

    public String getNext_fire_time() {
	return next_fire_time;
    }

    public void setNext_fire_time(String next_fire_time) {
	this.next_fire_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(next_fire_time);
    }

    public String getPrev_fire_time() {
	return prev_fire_time;
    }

    public void setPrev_fire_time(String prev_fire_time) {
	this.prev_fire_time =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prev_fire_time);
    }

    public String getPriopity() {
	return priopity;
    }

    public void setPriopity(String priopity) {
	this.priopity = priopity;
    }

    public String getStart_time() {
	return start_time;
    }

    public void setStart_time(String start_time) {
	this.start_time = start_time;
    }

    public String getEnd_time() {
	return end_time;
    }

    public void setEnd_time(String end_time) {
	this.end_time = end_time;
    }

    public String getCron_expression() {
	return cron_expression;
    }

    public void setCron_expression(String cron_expression) {
	this.cron_expression = cron_expression;
    }

    public String getJob_class_name() {
	return job_class_name;
    }

    public void setJob_class_name(String job_class_name) {
	this.job_class_name = job_class_name;
    }
}
