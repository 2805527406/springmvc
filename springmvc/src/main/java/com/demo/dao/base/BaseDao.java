package com.demo.dao.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.demo.util.Page;

public interface BaseDao<T, ID extends Serializable>{
	 /**
     * <����ʵ��>
     * <��������ʵ��>
     * @param t ʵ�����
     */
    public void saveEntity(T t);

    /**
     * <������߸���ʵ��>
     * @param t ʵ��
     */
    public void saveOrUpdate(T t);


    /**
     * <get>
     * <���ҵ�get����>
     * @param id ʵ���id
     * @return ��ѯ������ʵ��
     */
    public T get(ID id);
    public T load(ID id);

    /**
     * <contains>
     * @param t ʵ��
     * @return �Ƿ����
     */
    public boolean contains(T t);

    /**
     * <delete>
     * <ɾ�����е�t����>
     * @param t ʵ��
     */
    public void delete(T t);

    /**
     * <����IDɾ������>
     * @param Id ʵ��id
     * @return �Ƿ�ɾ���ɹ�
     */
    public boolean deleteById(ID Id);

    /**
     * <�����û���ɾ������>
     * @param Id
     * @return boolean
     */
    public void deleteSql(String sqlString, Object... values);

    /**
     * <ɾ������>
     * @param entities ʵ���Collection����
     */
    public void deleteAll(Collection<T> entities);

    /**
     * <ִ��Hql���>
     * @param hqlString hql
     * @param values ������������
     */
    public void queryHql(String hqlString, Object... values); 

    /**
     * <ִ��Sql���>
     * @param sqlString sql
     * @param values ������������
     */
    public void querySql(String sqlString, Object... values); 


    /**
     * <����HQL������Ψһʵ��>
     * @param hqlString HQL���
     * @param values ����������Object����
     * @return ��ѯʵ��
     */
    public T getByHQL(String hqlString, Object... values);

    /**
     * <����SQL������Ψһʵ��>
     * @param sqlString SQL���
     * @param values ����������Object����
     * @return ��ѯʵ��
     */
    public T getBySQL(String sqlString, Object... values);

    /**
     * 
     * @param hqlString
     * @return ��ѯ����
     */
    public List<T> getAllByHQL(String hqlString);

    /**
     * <����HQL��䣬�õ���Ӧ��list>
     * @param hqlString HQL���
     * @param values ����������Object����
     * @return ��ѯ���ʵ���List����
     */
    public List<T> getListByHQL(String hqlString, Object... values);

    /**
     * <����SQL��䣬�õ���Ӧ��list>
     * @param sqlString HQL���
     * @param values ����������Object����
     * @return ��ѯ���ʵ���List����
     */
    public List<T> getListBySQL(String sqlString, Object... values);


    /**
     * <update>
     * @param t ʵ��
     */
    public void updateEntity(T t);

    /**
     * ����id����ʵ����
     * @param hql
     * @param id
     */
    public void updateByHql(String hql,Object... values);
    /**
     * <����HQL�õ���¼��>
     * @param hql HQL���
     * @param values ����������Object����
     * @return ��¼����
     */
    public Long countByHql(String hql, Object... values);

    /**
     * <����HQL�õ�ȫ����¼��>
     * @param hql
     * @return ��¼����
     */
    public Long countAll(String hql);

    /**
     * <HQL��ҳ��ѯ>
     * @param hql HQL���
     * @param countHql ��ѯ��¼������HQL���
     * @param pageNo ��һҳ
     * @param pageSize һҳ������
     * @param values ����Object�������
     * @return PageResults�ķ�װ�࣬���������ҳ�����Ϣ�Լ���ѯ������List����
     */
    public Page<T> findPageByFetchedHql(String hql, String countHql, int pageNo, int pageSize, Object... values);

}
