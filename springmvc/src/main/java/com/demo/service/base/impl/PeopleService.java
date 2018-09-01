package com.demo.service.base.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.base.PeopleDao;
import com.demo.entity.People;
@Transactional
@Service
public class PeopleService extends BaseServiceImpl<People,Integer>{
	@Autowired
	public void setDao(PeopleDao dao) {
		super.setDao(dao);
	}
	
	protected PeopleDao getDao() {
		return (PeopleDao)super.getDao();
	}
	
	public People load(Integer id) {
		return getDao().load(id);
	}
}
