package com.demo.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.dao.base.BaseDao;
import com.demo.entity.People;
import com.demo.util.Page;
@Repository("baseDao")
@Transactional
public class BaseDaoImpl<T, ID extends Serializable> implements BaseDao<T, ID> {
	  protected Class<T> entityClass;

	    public BaseDaoImpl() {

	    }
	    
	    private SessionFactory sessionFactory;
	    @Autowired
	    private void sessionFactory(SessionFactory sessionFactory) {
	        this.sessionFactory = sessionFactory;
	    }

	    /**
	      * 
	      * @return session
	      */
	     public Session getSession() {
	         //��Ҫ����������ܵõ�CurrentSession,�����Ҳ���aop�������ã���Ҳ���Բ���@Transactional��������ʽ��������
	         return this.sessionFactory.getCurrentSession();
	     }

	    @SuppressWarnings({ "unchecked", "rawtypes" })
	    protected Class getEntityClass() {
	        if (entityClass == null) {
	            Type type = getClass().getGenericSuperclass();  //���䷽ʽ��ȡ����ȫ�� ��com.chat.common.dao.impl.HibernateBaseUtil<com.chat.dao.impl.ChatUserDao, com.chat.dao.impl.ChatUserDao>
	            Type[] t  = ((ParameterizedType) type).getActualTypeArguments();    //��ȡ����ķ��Ͳ���,����Ϊ����
	            entityClass = (Class<T>) t[0];  //��ȡ��һ����������com.chat.dao.impl.ChatUserDao
	            //entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	        }
	        return entityClass;
	    }


	    /**
	     * <����ʵ��>
	     * <��������ʵ��>
	     * @param t ʵ�����
	     * @see com.chat.common.dao.IHibernateBaseUtil#saveEntity(java.lang.Object)
	     */
	    @Override
	    public void saveEntity(T t) {
	        getSession().save(t);   
	        getSession().flush(); //ˢ������ִ��֮�󽫻��ύ���񣬿��Է�ֹ����ѻ��ڻ�����

	    }

	    /**
	     * <������߸���ʵ��>
	     * @param t ʵ��
	     * @see com.chat.common.dao.IHibernateBaseUtil#saveOrUpdate(java.lang.Object)
	     */
	    @Override
	    public void saveOrUpdate(T t) {
	        getSession().saveOrUpdate(t);
	        getSession().flush(); //ˢ������ִ��֮�󽫻��ύ���񣬿��Է�ֹ����ѻ��ڻ�����

	    }

	    /**
	     * <get>
	     * <���ҵ�get����>
	     * @param id ʵ���id
	     * @return ��ѯ������ʵ��
	     * @see com.chat.common.dao.IHibernateBaseUtil#get(java.io.Serializable)
	     */
	    @SuppressWarnings("unchecked")
	    @Override
	    public T get(ID id) {
	        T load = (T)getSession().get(getEntityClass(), id);

	        return load;
	    }

	    /**
	     * <contains>
	     * @param t ʵ��
	     * @return �Ƿ����
	     * @see com.chat.common.dao.IHibernateBaseUtil#contains(java.lang.Object)
	     */
	    @Override
	    public boolean contains(T t) {
	        return getSession().contains(t);
	    }

	    /**
	     * <delete>
	     * <ɾ�����е�t����>
	     * @param t ʵ��
	     * @see com.chat.common.dao.IHibernateBaseUtil#delete(java.lang.Object)
	     */
	    @Override
	    public void delete(T t) {
	        getSession().delete(t);

	    }

	    /**
	     * <delete>
	     * <ɾ�����е�t����>
	     * @param hqlString hql���
	     * @param values ������������
	     * @see com.chat.common.dao.IHibernateBaseUtil#deleteSql(java.lang.Object)
	     */
	    @Override
	    public void deleteSql(String sqlString, Object... values) {
	        Query query = getSession().createQuery(sqlString);
	        if (values != null)
	        {
	            for (int i = 0; i < values.length; i++)
	            {
	                query.setParameter(i, values[i]);
	            }
	        }
	        query.executeUpdate();

	    }

	    /**
	     * <����IDɾ������>
	     * @param Id ʵ��id
	     * @return �Ƿ�ɾ���ɹ�
	     * @see com.chat.common.dao.IHibernateBaseUtil#deleteById(java.io.Serializable)
	     */
	    @Override
	    public boolean deleteById(ID Id) {
	         T t = get(Id);
	         if(t == null){
	             return false;
	         }
	         delete(t);

	        return true;
	    }

	    /**
	     * <ɾ������>
	     * @param entities ʵ���Collection����
	     * @see com.chat.common.dao.IHibernateBaseUtil#deleteAll(java.util.Collection)
	     */
	    @Override
	    public void deleteAll(Collection<T> entities) {
	        for(Object entity : entities) {
	            getSession().delete(entity);
	        }

	    }

	    /**
	     * <ִ��Hql���>
	     * @param hqlString hql���
	     * @param values ������������
	     * @see com.chat.common.dao.IHibernateBaseUtil#queryHql(java.lang.String, java.lang.Object[])
	     */
	    @Override
	    public void queryHql(String hqlString, Object... values) {
	        Query query = getSession().createQuery(hqlString);
	        if (values != null)
	        {
	            for (int i = 0; i < values.length; i++)
	            {
	                query.setParameter(i, values[i]);
	            }
	        }

	        query.executeUpdate();

	    }

	    /**
	     * <ִ��Sql���>
	     * @param sqlString sql���
	     * @param values ������������
	     * @see com.chat.common.dao.IHibernateBaseUtil#querySql(java.lang.String, java.lang.Object[])
	     */
	    @Override
	    public void querySql(String sqlString, Object... values) {
	        Query query = getSession().createSQLQuery(sqlString);
	        if (values != null)
	        {
	            for (int i = 0; i < values.length; i++)
	            {
	                query.setParameter(i, values[i]);
	            }
	        }

	        query.executeUpdate();

	    }

	    /**
	     * <����HQL������Ψһʵ��>
	     * @param hqlString HQL���
	     * @param values ����������Object����
	     * @return ��ѯʵ��
	     * @see com.chat.common.dao.IHibernateBaseUtil#getByHQL(java.lang.String, java.lang.Object[])
	     */
	    @SuppressWarnings("unchecked")
	    @Override
	    public T getByHQL(String hqlString, Object... values) {
	        Query query = getSession().createQuery(hqlString);
	        if (values != null)
	        {
	            for (int i = 0; i < values.length; i++)
	            {
	                query.setParameter(i, values[i]);
	            }
	        }

	        return (T) query.uniqueResult();
	    }

	    /**
	     * <����SQL������Ψһʵ��>
	     * @param sqlString SQL���
	     * @param values ����������Object����
	     * @return ��ѯʵ��
	     * @see com.chat.common.dao.IHibernateBaseUtil#getBySQL(java.lang.String, java.lang.Object[])
	     */
	    @SuppressWarnings("unchecked")
		@Override
	    public T getBySQL(String sqlString, Object... values) {
	        Query query = getSession().createSQLQuery(sqlString);
	        if (values != null)
	        {
	            for (int i = 0; i < values.length; i++)
	            {
	                query.setParameter(i, values[i]);
	            }
	        }

	        return (T) query.uniqueResult();
	    }

	    @SuppressWarnings("unchecked")
		@Override
	    public List<T> getAllByHQL(String hqlString){
	        Query query = getSession().createQuery(hqlString);

	        return query.list();
	    }
	    /**
	     * <����HQL��䣬�õ���Ӧ��list>
	     * @param hqlString HQL���
	     * @param values ����������Object����
	     * @return ��ѯ���ʵ���List����
	     * @see com.chat.common.dao.IHibernateBaseUtil#getListByHQL(java.lang.String, java.lang.Object[])
	     */
	    @SuppressWarnings("unchecked")
		@Override
	    public List<T> getListByHQL(String hqlString, Object... values) {
	        Query query = getSession().createQuery(hqlString);
	        if (values != null)
	        {
	            for (int i = 0; i < values.length; i++)
	            {
	                query.setParameter(i, values[i]);
	            }
	        }

	        return query.list();
	    }

	    /**
	     * <����SQL��䣬�õ���Ӧ��list>
	     * @param sqlString HQL���
	     * @param values ����������Object����
	     * @return ��ѯ���ʵ���List����
	     * @see com.chat.common.dao.IHibernateBaseUtil#getListBySQL(java.lang.String, java.lang.Object[])
	     */
	    @SuppressWarnings("unchecked")
	    @Override
	    public List<T> getListBySQL(String sqlString, Object... values ) {
	        Query query = getSession().createSQLQuery(sqlString);
	        if (values != null)
	        {
	            for (int i = 0; i < values.length; i++)
	            {
	                query.setParameter(i, values[i]);
	            }
	        }

	        return query.list();
	    }

	    /**
	     * <update>
	     * @param t ʵ��
	     * @see com.chat.common.dao.IHibernateBaseUtil#update(java.lang.Object)
	     */
	    @Override
	    public void updateEntity(T t) {
	        getSession().update(t);
	        this.getSession().flush();  //ˢ������ִ��֮�󽫻��ύ���񣬿��Է�ֹ����ѻ��ڻ�����         
	    }

	    /**
	     * ����id����ʵ����
	     * @param hql
	     * @param id
	     */
	    @Override
	    public void updateByHql(String hql,Object... values){
	        Query query = this.getSession().createQuery(hql);
	        if(values != null){
	            for(int i=0;i<values.length;i++){
	                query.setParameter(i, values[i]);
	            }
	        }
	        query.executeUpdate();

	    }

	    /**
	     * <����HQL�Ͳ����õ���¼��>
	     * @param hql HQL���
	     * @param values ����������Object����
	     * @return ��¼����
	     * @see com.chat.common.dao.IHibernateBaseUtil#countByHql(java.lang.String, java.lang.Object[])
	     */
	    @Override
	    public Long countByHql(String hql, Object... values) {
	        Query query = getSession().createQuery(hql);
	        if(values != null){
	            for(int i = 0; i < values.length; i++) {
	                query.setParameter(i, values[i]);
	            }
	        }
	        return (Long) query.uniqueResult();
	    }

	    /**
	     * <ָ��HQL�õ�ȫ����¼��>
	     * @param hql HQL���
	     * @return ��¼����
	     * @see com.chat.common.dao.IHibernateBaseUtil#countAll(java.lang.String, java.lang.Object[])
	     */
	    @Override
	    public Long countAll(String hql) {
	        Query query = getSession().createQuery(hql);         
	        return (Long) query.uniqueResult();
	    }

	    /**
	     * <HQL��ҳ��ѯ>
	     * @param hql HQL���
	     * @param countHql ��ѯ��¼������HQL���
	     * @param pageNo ��һҳ
	     * @param pageSize һҳ������
	     * @param values ����Object�������
	     * @return PageResults�ķ�װ�࣬���������ҳ�����Ϣ�Լ���ѯ������List����
	     * @see com.chat.common.dao.IHibernateBaseUtil#findPageByFetchedHql(java.lang.String, java.lang.String, int, int, java.lang.Object[])
	     */
	    @SuppressWarnings("unchecked")
	    @Override
	    public Page<T> findPageByFetchedHql(String hql, String countHql,
	            int pageNo, int pageSize, Object... values) {
	    	Page<T> retValue = new Page<T>();
	        Query query = getSession().createQuery(hql);
	        if(values != null){
	            for(int i = 0; i < values.length; i++) {
	                query.setParameter(i, values[i]);
	            }
	        }
	        int currentPage = pageNo > 1 ? pageNo : 1;
	        retValue.setCunrrentPage(currentPage);
	        retValue.setPageSize(pageSize);
	        if (countHql == null)
	        {
	            ScrollableResults results = query.scroll();
	            results.last();
	            retValue.setTotalCount(results.getRowNumber() + 1);// �����ܼ�¼��
	        }
	        else
	        {
	            Long count = countByHql(countHql, values);
	            retValue.setTotalCount(count.intValue());
	        }
	        List<T> itemList = query.setFirstResult((currentPage - 1) * pageSize).setMaxResults(pageSize).list();
	        if (itemList == null)
	        {
	            itemList = new ArrayList<T>();
	        }
	        return retValue;
	    }

		@Override
		public T load(ID id) {
			 @SuppressWarnings("unchecked")
			T load = (T)getSession().load(People.class,id);
	        return load;
		}


}
