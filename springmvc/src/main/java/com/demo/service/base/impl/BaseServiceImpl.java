package com.demo.service.base.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.demo.dao.base.BaseDao;
import com.demo.service.base.BaseService;
import com.demo.util.Page;
@Transactional
@Service
public class BaseServiceImpl<T,ID extends Serializable> implements BaseService<T, ID> {
	private BaseDao<T,ID> dao;
	public void setDao(BaseDao<T,ID> dao) {
		this.dao = dao;
	}
	protected BaseDao<T,ID> getDao() {
		return this.dao;
	}
	@Transactional
	@Override
	public void saveEntity(T t) {
		getDao().saveEntity(t);
	}
	@Transactional
	@Override
	public void saveOrUpdate(T t) {
		getDao().saveOrUpdate(t);		
	}
	@Transactional
	@Override
	public T get(ID id) {
		return getDao().get(id);
	}
	@Transactional
	@Override
	public boolean contains(T t) {
		return getDao().contains(t);
	}
	@Transactional
	@Override
	public void delete(T t) {
		getDao().delete(t);
	}
	@Transactional
	@Override
	public boolean deleteById(ID Id) {
		return getDao().deleteById(Id);
	}
	@Transactional
	@Override
	public void deleteSql(String sqlString, Object... values) {
		getDao().deleteSql(sqlString, values);
	}
	@Transactional
	@Override
	public void deleteAll(Collection<T> entities) {
		getDao().deleteAll(entities);
	}
	@Transactional
	@Override
	public void queryHql(String hqlString, Object... values) {
		getDao().queryHql(hqlString, values);
	}
	@Transactional
	@Override
	public void querySql(String sqlString, Object... values) {
		getDao().querySql(sqlString, values);
	}
	@Transactional
	@Override
	public T getByHQL(String hqlString, Object... values) {
		return getDao().getByHQL(hqlString, values);
	}
	@Transactional
	@Override
	public T getBySQL(String sqlString, Object... values) {
		return getDao().getBySQL(sqlString, values);
	}
	@Transactional
	@Override
	public List<T> getAllByHQL(String hqlString) {
		return getDao().getAllByHQL(hqlString);
	}
	@Transactional
	@Override
	public List<T> getListByHQL(String hqlString, Object... values) {
		return getDao().getListByHQL(hqlString, values);
	}
	@Transactional
	@Override
	public List<T> getListBySQL(String sqlString, Object... values) {
		return getDao().getListBySQL(sqlString, values);
	}
	@Transactional
	@Override
	public void updateEntity(T t) {
		getDao().updateEntity(t);
	}
	@Transactional
	@Override
	public void updateByHql(String hql, Object... values) {
		getDao().updateByHql(hql, values);
	}
	@Transactional
	@Override
	public Long countByHql(String hql, Object... values) {
		return getDao().countByHql(hql, values);
	}
	@Transactional
	@Override
	public Long countAll(String hql) {
		return getDao().countAll(hql);
	}
	@Transactional
	@Override
	public Page<T> findPageByFetchedHql(String hql, String countHql, int pageNo, int pageSize, Object... values) {
		return getDao().findPageByFetchedHql(hql, countHql, pageNo, pageSize, values);
	}
	
	public T load(ID id) {
		return getDao().load(id);
	}

}
