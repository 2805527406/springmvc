package com.demo.dao.base;

import org.springframework.stereotype.Repository;

import com.demo.dao.base.impl.BaseDaoImpl;
import com.demo.entity.People;

@Repository("peopleDao")
public class PeopleDao extends BaseDaoImpl<People,Integer>{
	
}
