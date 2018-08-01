package cn.itcast.nsfw.user.dao;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserDao extends BaseDao<User> {

	// �����ʺŻ�id��ѯ�û���¼
	public List<User> findUsersByAccountAndId(String account, String id);

	// �����û���ɫ
	public void saveUserRole(UserRole userRole);

	// ɾ�����û���Ӧ�����н�ɫ
	public void deleteUserRoleByUserId(Serializable id);

	// �����û�id���Ҷ�Ӧ�Ľ�ɫ
	public List<UserRole> findUserRolesByUserId(String id);

	// �����û��˺ź������ѯ
	public List<User> findUsersByAccountAndPass(String account, String password);

}
