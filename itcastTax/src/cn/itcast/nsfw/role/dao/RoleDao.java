package cn.itcast.nsfw.role.dao;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.role.entity.Role;

public interface RoleDao extends BaseDao<Role> {

	//���ݽ�ɫidɾ���ý�ɫ�µ�����Ȩ��
	public void deleteRolePrivilegesByRoleId(String roleId);

}