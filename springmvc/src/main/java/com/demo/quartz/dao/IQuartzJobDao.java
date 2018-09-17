package com.demo.quartz.dao;

import java.util.List;
import java.util.Map;

import com.demo.quartz.entity.JobInfo;

/**
 * 
 * @author Administrator
 *
 */
public interface IQuartzJobDao {
	List<JobInfo> getList(Map<String, String> params);

	void updatePreFireTime(String triggerName);
}
