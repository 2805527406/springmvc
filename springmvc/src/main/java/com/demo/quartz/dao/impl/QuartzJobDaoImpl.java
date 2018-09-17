package com.demo.quartz.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.demo.quartz.dao.IQuartzJobDao;
import com.demo.quartz.entity.JobInfo;

public class QuartzJobDaoImpl implements IQuartzJobDao{

	@Autowired
    private DataSource dataSource;
	
	 public static final String QRTZ_SQL = "select tr.*,cr.CRON_EXPRESSION,jo.JOB_CLASS_NAME from QRTZ_TRIGGERS tr,QRTZ_CRON_TRIGGERS cr,QRTZ_JOB_DETAILS jo  "
			    + " where cr.SCHED_NAME=tr.SCHED_NAME and cr.TRIGGER_NAME=tr.TRIGGER_NAME and cr.TRIGGER_GROUP=tr.TRIGGER_GROUP  "
			    + " and jo.SCHED_NAME=tr.SCHED_NAME and jo.JOB_NAME=tr.JOB_NAME and jo.JOB_GROUP=tr.JOB_GROUP";
	 
	  /**
	     * 根据条件查询Quartz作业的信息
	     * 
	     * @Title: getList
	     * @Description: TODO
	     * @param params 参数集合
	     * @return
	     * @return: List<JobDetail>
	     */
	    @Override
	    public List<JobInfo> getList(Map<String, String> params) {
	    	String sql = QRTZ_SQL;
	    if(params!=null&&params.containsKey("keyword")){
	    	 sql = QRTZ_SQL + " and (jo.JOB_NAME like '%" + params.get("keyword") + "%' or jo.JOB_CLASS_NAME like '%" + params.get("keyword") + "%')";
	    }
	    List<Map<String, Object>> resultsList = getJdbcTemplate().queryForList(sql);
		if (resultsList == null || resultsList.size() == 0) {

		    return null;
		}

		List<JobInfo> details = new ArrayList<JobInfo>(resultsList.size());
		
		for (Map<String, Object> map : resultsList) {
		    details.add(createJobDetail(map));
		}

		return details;

	    }

	    /**
	     * 创建作业明细信息
	     * 
	     * @Title: createJobDetail
	     * @Description: TODO
	     * @param map
	     * @return
	     * @return: JobDetail
	     */
	    private JobInfo createJobDetail(Map<String, Object> map) {

		JobInfo detail = new JobInfo();

		detail.setSched_name(map.get("sched_name") + "");
		detail.setTrigger_name(map.get("trigger_name") + "");
		detail.setTrigger_group(map.get("trigger_group") + "");
		detail.setDescription(map.get("description") + "");
		detail.setJob_name(map.get("job_name") + "");
		detail.setJob_group(map.get("job_group") + "");
		detail.setNext_fire_time(map.get("next_fire_time") + "");
		detail.setPrev_fire_time(map.get("prev_fire_time") + "");
		detail.setCron_expression(map.get("cron_expression") + "");
		detail.setPriopity(map.get("priopity") + "");
		detail.setEnd_time(map.get("end_time") + "");
		detail.setStart_time(map.get("start_time") + "");
		detail.setJob_class_name(map.get("job_class_name") + "");
		detail.setTrigger_state(map.get("trigger_state") + "");
		detail.setTrigger_type(map.get("trigger_type") + "");
		return detail;
	    }

	    private JdbcTemplate getJdbcTemplate() {

		return new JdbcTemplate(dataSource);
	    }

		@Override
		public void updatePreFireTime(String triggerName) {
			String sql = "update QRTZ_TRIGGERS set PREV_FIRE_TIME = " + Calendar.getInstance().getTimeInMillis()+" where TRIGGER_NAME = '" + triggerName+"'";
			getJdbcTemplate().update(sql);
		}

}
