package cn.itcast.nsfw.user.action;

import java.io.File;
import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.constant.Constant;
import cn.itcast.core.exception.ActionException;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.service.RoleService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionContext;

public class UserAction extends BaseAction {

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;

	private User user;

	// ͼ����Ϣ
	private File headImg;//headImg��jspҳ������ж�Ӧ�����ƣ��˴���3��������������get/set����
	private String headImgFileName;
	private String haedImgContentType;
	// ����
	private File userExcel;
	private String userExcelFileName;
	private String userExcelContentType;
	private String[] roleIds;
	//��ѯ
	private String strName;

	//�б�
	public String listUI() throws ActionException {
		try {
			QueryHelper queryHelper = new QueryHelper(User.class, "");
			if(user != null){
				if(StringUtils.isNotBlank(user.getName())){
					//����
					user.setName(URLDecoder.decode(user.getName(),"utf-8"));
					queryHelper.addCondition("name like ?", "%" + user.getName() + "%");
				}
			}
			pageResult = userService.getPageResult(queryHelper, getPageNo(), getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listUI";
	}
	// ��ת������ҳ��
	public String addUI() {
		//���ؽ�ɫ�б�
		ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
		strName=user.getName();
		return "addUI";
	}

	// ��������
	public String add() {
		try {
			if (user != null) {
				if (headImg != null) { // �����û�ͷ��
					// 1����ȡͷ���ļ���struts2:File,FileName,ContentType��
					// 2������ͷ���ļ�
					String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName = UUID.randomUUID().toString()+ headImgFileName.substring(headImgFileName.lastIndexOf("."));
					FileUtils.copyFile(headImg, new File(filePath, fileName));
					// 3�������û�ͷ��·��
					user.setHeadImg("user/" + fileName);
				}
				userService.saveUserAndRole(user,roleIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}

	// ��ת���༭ҳ��
	public String editUI() {
		//���ؽ�ɫ�б�
		ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
		if (user != null && StringUtils.isNotBlank(user.getId())) {
			strName=user.getName();
			user = userService.findObjectById(user.getId());
			//�����ɫ��������
			List<UserRole>list=userService.findUserRolesByUserId(user.getId());
			if(list!=null&&list.size()>0){
				roleIds=new String[list.size()];
				int i=0;
				for(UserRole ur:list){
					roleIds[i++]=ur.getId().getRole().getRoleId();
				}
			}
		}
		return "editUI";
	}

	// ����༭
	public String edit() {
		try {
			if (user != null) {
				// �����û�ͷ��
				if (headImg != null) { // 1����ȡͷ���ļ���struts2:File,FileName,ContentType��
					// 2������ͷ���ļ�
					String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName = UUID.randomUUID().toString()+ headImgFileName.substring(headImgFileName.lastIndexOf("."));
					FileUtils.copyFile(headImg, new File(filePath, fileName));
					// 3�������û�ͷ��·��
					user.setHeadImg("user/" + fileName);
				}
				userService.updateUserAndRole(user,roleIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}

	// ����idɾ��
	public String delete() {
		if (user != null && StringUtils.isNotBlank(user.getId())) {
			strName=user.getName();
			userService.delete(user.getId());
		}
		return "list";
	}

	// ����ɾ��
	public String deleteSelected() {
		if (selectedRow != null) {
			strName=user.getName();
			for (String id : selectedRow) {
				userService.delete(id);
			}
		}
		return "list";
	}

	// �����û��б�
	public void exportExcel() {
		
		try {
			// ��ȡ�û��б�
			// ������������excel
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/x-excel");
			response.setHeader("Content-Disposition", "attachment;filename="+ new String("�û��б�.xls".getBytes(), "ISO-8859-1"));
			ServletOutputStream outputStream = response.getOutputStream();
			userService.exportExcel(userService.findObjects(), outputStream);
			if (outputStream != null) {
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// �����û��б�
	public String importExcel() {
		if (userExcel != null) {
			// �ж��Ƿ���excel�ļ�
			if (userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
				userService.importExcel(userExcel);
			}
		}
		return "list";
	}
	
	//У���ʺ�Ψһ��
	public void verifyAccount(){
		try {
			//1����ȡ�ʺš�id
			if(user != null && StringUtils.isNotBlank(user.getAccount())){
				String res = "true";
				//2�������ʺš�id��ѯ�û���¼
				List<User> userList = userService.findUsersByAccountAndId(user.getAccount(), user.getId());
				if(userList != null && userList.size() > 0){//˵�����ʺ��Ѿ�����
					res = "false";
				}
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html;charset=utf-8");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(res.getBytes());
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String[] getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}


	public File getHeadImg() {
		return headImg;
	}

	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}

	public String getHeadImgFileName() {
		return headImgFileName;
	}

	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}

	public String getHaedImgContentType() {
		return haedImgContentType;
	}

	public void setHaedImgContentType(String haedImgContentType) {
		this.haedImgContentType = haedImgContentType;
	}

	public File getUserExcel() {
		return userExcel;
	}

	public void setUserExcel(File userExcel) {
		this.userExcel = userExcel;
	}

	public String getUserExcelFileName() {
		return userExcelFileName;
	}

	public void setUserExcelFileName(String userExcelFileName) {
		this.userExcelFileName = userExcelFileName;
	}

	public String getUserExcelContentType() {
		return userExcelContentType;
	}

	public void setUserExcelContentType(String userExcelContentType) {
		this.userExcelContentType = userExcelContentType;
	}
	public String[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	

}
