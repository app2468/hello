package cn.itcast.nsfw.role.dao;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.role.entity.Role;

public interface RoleDao extends BaseDao<Role> {

	//根据角色id删除该角色下的所有权限
	public void deleteRolePrivilegesByRoleId(String roleId);

}
