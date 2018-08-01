package cn.itcast.nsfw.user.service;

import java.io.File;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.itcast.core.service.BaseService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserService extends BaseService<User>{


	// 导出用户列表
	public void exportExcel(List<User> userList,ServletOutputStream outputStream);

	// 导入用户列表到数据库中
	public void importExcel(File userExcel);

	// 根据帐号或id查询用户记录
	public List<User> findUsersByAccountAndId(String account, String id);

	// 保存用户及对应的角色
	public void saveUserAndRole(User user, String... roleIds);

	// 更新用户及其角色
	public void updateUserAndRole(User user, String... roleIds);

	// 根据用户id查找对应的角色
	public List<UserRole> findUserRolesByUserId(String id);

	// 根据用户账号和密码查询
	public List<User> findUsersByAccountAndPass(String account, String password);

}
