package cn.itcast.nsfw.user.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import cn.itcast.core.dao.impl.BaseDaoImpl;
import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	@Override
	public List<User> findUsersByAccountAndId(String account, String id) {
		String sSQL = "FROM User WHERE account = ?";
		if(StringUtils.isNotBlank(id)){
			sSQL += " AND id!=?";
		}
		Query query = getSession().createQuery(sSQL);
		    query.setParameter(0, account);
		if(StringUtils.isNotBlank(id)){
			query.setParameter(1, id);
		}
		return query.list();
	}

	@Override
	public void saveUserRole(UserRole userRole) {
		getHibernateTemplate().save(userRole);
	}

	@Override
	public void deleteUserRoleByUserId(Serializable id) {
		Query query = getSession().createQuery("DELETE FROM UserRole WHERE id.userId=?");
		query.setParameter(0, id);
		query.executeUpdate();
	}

	@Override
	public List<UserRole> findUserRolesByUserId(String id) {
		Query query=getSession().createQuery("FROM UserRole WHERE id.userId=?");
	    query.setParameter(0, id);
	 	return query.list();
	}

	@Override
	public List<User> findUsersByAccountAndPass(String account,String password) {
		Query query=getSession().createQuery("FROM User WHERE account=? and password=? and state=?");
	    query.setParameter(0, account);
	    query.setParameter(1, password);
	    query.setParameter(2, User.USER_STATE_VALID);//有效用户才能登录系统
	 	return query.list();	
	 	}

}
