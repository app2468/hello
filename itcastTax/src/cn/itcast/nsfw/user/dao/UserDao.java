package cn.itcast.nsfw.user.dao;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserDao extends BaseDao<User> {

	// 根据帐号或id查询用户记录
	public List<User> findUsersByAccountAndId(String account, String id);

	// 保存用户角色
	public void saveUserRole(UserRole userRole);

	// 删除该用户对应的所有角色
	public void deleteUserRoleByUserId(Serializable id);

	// 根据用户id查找对应的角色
	public List<UserRole> findUserRolesByUserId(String id);

	// 根据用户账号和密码查询
	public List<User> findUsersByAccountAndPass(String account, String password);

}
