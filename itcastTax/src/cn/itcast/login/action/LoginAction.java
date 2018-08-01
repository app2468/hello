package cn.itcast.login.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.constant.Constant;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	
	private User user;
	private String loginResult;
	
	@Resource
	private UserService userService;
	
	//��ת����¼ҳ��
	public String toLoginUI(){
		return "loginUI";
	}
	//ע��/�˳�
	public String logout(){
		ServletActionContext.getRequest().getSession().removeAttribute(Constant.USER);
		return toLoginUI();
	}
	
	//��ת��û��Ȩ����ʾҳ��
	public String toNoPermissionUI(){
		return "noPermissionUI";
	}
	
	//��¼
	public String login(){
		if(user != null){
			//1.1����ȡ�ʺš�����
			if(StringUtils.isNotBlank(user.getAccount()) && StringUtils.isNotBlank(user.getPassword())){
				//1.2�������ʺš������ѯ�û���¼
				List<User> list = userService.findUsersByAccountAndPass(user.getAccount(), user.getPassword());
				if(list != null && list.size()>0){
					//1.3����¼�ɹ������û���¼��
					//1.3.1����ȡ�û���Ϣ
					user = list.get(0);
					//1.3.2�������û�id�����û���Ӧ�Ľ�ɫ�б�
					user.setUserRoles(userService.findUserRolesByUserId(user.getId()));
					//1.3.3�����û���Ϣ����session
					ServletActionContext.getRequest().getSession().setAttribute(Constant.USER, user);
					//1.3.4�����û���¼��Ϣ��¼����־�ļ�
					Log log = LogFactory.getLog(getClass());
					log.info("�û�����Ϊ��" + user.getName() + " ���û���¼ϵͳ��");
					//1.3.5����ת��ϵͳ��ҳ���ض���
					return "home";
				} else {
					//1.4����¼ʧ�ܣ�û�в�ѯ���û���¼������ת����¼ҳ�沢��ʾ�û���¼ʧ��
					loginResult = "��¼ʧ�ܣ��ʺŻ��������";
				}
			} else {
				loginResult = "�ʺŻ����벻��Ϊ�գ�";
			}
		} else {
			loginResult = "�ʺš����벻��Ϊ�գ�";
		}
		return toLoginUI();
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLoginResult() {
		return loginResult;
	}

	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}
	
}
