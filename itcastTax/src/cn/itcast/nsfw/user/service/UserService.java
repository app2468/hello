package cn.itcast.nsfw.user.service;

import java.io.File;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.itcast.core.service.BaseService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserService extends BaseService<User>{


	// �����û��б�
	public void exportExcel(List<User> userList,ServletOutputStream outputStream);

	// �����û��б����ݿ���
	public void importExcel(File userExcel);

	// �����ʺŻ�id��ѯ�û���¼
	public List<User> findUsersByAccountAndId(String account, String id);

	// �����û�����Ӧ�Ľ�ɫ
	public void saveUserAndRole(User user, String... roleIds);

	// �����û������ɫ
	public void updateUserAndRole(User user, String... roleIds);

	// �����û�id���Ҷ�Ӧ�Ľ�ɫ
	public List<UserRole> findUserRolesByUserId(String id);

	// �����û��˺ź������ѯ
	public List<User> findUsersByAccountAndPass(String account, String password);

}
