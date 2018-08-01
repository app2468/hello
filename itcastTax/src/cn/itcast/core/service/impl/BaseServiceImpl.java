package cn.itcast.core.service.impl;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.core.service.BaseService;
import cn.itcast.core.util.PageResult;
import cn.itcast.core.util.QueryHelper;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

	
	private BaseDao<T> baseDao;
	//该方法提供给service调用，设置baseDao对象，在baseServiceImpl便可实现对baseDao对象中基本的的调用
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao=baseDao;
	}

	
	@Override
	public void save(T entity) {
		baseDao.save(entity);
	}

	@Override
	public void update(T entity) {
		baseDao.update(entity);
	}

	@Override
	public void delete(Serializable id) {
		baseDao.delete(id);
	}

	@Override
	public List<T> findObjects() {
		return baseDao.findObjects();
	}

	@Override
	public T findObjectById(Serializable id) {
		return baseDao.findObjectById(id);
	}


	@Override
	public List<T> findObjects(String hql, List<Object> parameters) {
		return baseDao.findObjects(hql,parameters);
	}


	@Override
	public List<T> findObjects(QueryHelper queryHelper) {
		return baseDao.findObjects(queryHelper);
	}

	@Override
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize) {
		return baseDao.getPageResult(queryHelper, pageNo, pageSize);
	}

	

}
