package cn.itcast.core.dao.impl;

import java.io.Serializable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.core.util.PageResult;
import cn.itcast.core.util.QueryHelper;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	private Class<T> clazz;

	public BaseDaoImpl() {
		//使用反射得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();// UserDaoImpl<User>
		clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}

	@Override
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	@Override
	public void delete(Serializable id) {
		getHibernateTemplate().delete(findObjectById(id));
	}

	@Override
	public List<T> findObjects() {
		Query query = getSession().createQuery("FROM " + clazz.getSimpleName());
		return query.list();
	}

	@Override
	public T findObjectById(Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	@Override
	public List<T> findObjects(String hql, List<Object> parameters){
		Query listQuery = getSession().createQuery(hql);
		if(parameters!=null){
			for(int i=0;i<parameters.size();i++){
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		return listQuery.list();
	}
    //查询
	@Override
	public List<T> findObjects(QueryHelper queryHelper) {
		Query query = getSession().createQuery(queryHelper.getListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
    //分页
	@Override
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize) {
		Query query = getSession().createQuery(queryHelper.getListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		//设置记录起始索引号
		if(pageNo < 1) pageNo = 1;
		query.setFirstResult((pageNo-1)*pageSize);
		//本次查询记录大小
		query.setMaxResults(pageSize);
		List items = query.list();
		
		//总记录数
		Query countQuery = getSession().createQuery(queryHelper.getCountHql());
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		long totalCount = (Long)countQuery.uniqueResult();
		//构造返回的分页对象
		return new PageResult(totalCount, pageNo, pageSize, items);
	}

}
