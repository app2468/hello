package cn.itcast.core.dao;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.util.PageResult;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;

public interface  BaseDao<T> {
	
	//����
	public void save(T entity);
	//����
	public void update(T entity);
	//ɾ��
	public void delete(Serializable id);
    //��ѯ
	public List<T> findObjects();
	//����id��ѯ
	public T findObjectById(Serializable id);
	//��ѯ�б�
	public List<T> findObjects(String hql, List<Object> parameters);
	//��ѯ�б�--��ѯ����
	public List<T> findObjects(QueryHelper queryHelper);
	//��ҳ��ѯ�б�-��ѯ����queryHelper
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,int pageSize);
	

}
