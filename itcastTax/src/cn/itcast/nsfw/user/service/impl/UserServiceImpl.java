package cn.itcast.nsfw.user.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.core.util.ExcelUtil;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.entity.UserRoleId;
import cn.itcast.nsfw.user.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	
	private UserDao userDao;

	@Resource
	//在userServiceImpl中，通过自动注入userDao的同时，将userDao传递给BaseServiceImpl转换为BaseDao
	public void setUserDao(UserDao userDao) {
		super.setBaseDao(userDao);
		this.userDao=userDao;
	}


	@Override
	public void delete(Serializable id) {
		//删除用户对应的角色
		userDao.deleteUserRoleByUserId(id);
		//删除用户
		userDao.delete(id);
	}


	@Override
	public void exportExcel(List<User> userList,ServletOutputStream outputStream) {
		ExcelUtil.exportExcel(userList, outputStream);
	}

	@Override
	public void importExcel(File userExcel) {
		try {
			FileInputStream fileInputStream = new FileInputStream(userExcel);
			// 1、读取工作簿
			Workbook workbook = WorkbookFactory.create(userExcel);
			// 2、读取工作表
			Sheet sheet = workbook.getSheetAt(0);
			// 3、读取行
			if (sheet.getPhysicalNumberOfRows() > 2) {
				// 4、读取单元格
				User user = null;
				Row row = null;
				for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
					row = sheet.getRow(i);
					user = new User();
					String name = row.getCell(0).getStringCellValue();
					user.setName(name);
					String account = row.getCell(1).getStringCellValue();
					user.setAccount(account);
					String dept = row.getCell(2).getStringCellValue();
					user.setDept(dept);
					String gender = row.getCell(3).getStringCellValue();
					user.setGender("男".equals(gender));

					String mobile;
					try {
						
						mobile = row.getCell(4).getStringCellValue();
					} catch (Exception e) {
						//科学计数方式的数值
						double dMobile = row.getCell(4).getNumericCellValue();
						//BigDecimal将科学计数方式的数值转为一个正常的数值并转为字符串
						mobile = BigDecimal.valueOf(dMobile).toString();
					}
					user.setMobile(mobile);
					
					String email = row.getCell(5).getStringCellValue();
					user.setName(email);
					
					Date birthday = row.getCell(6).getDateCellValue();
					user.setBirthday(birthday);

					//设置默认状态为 有效
					user.setState(User.USER_STATE_VALID);
					user.setPassword("123456");
					
					save(user);
				}
			}
			workbook.close();
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}


	@Override
	public List<User> findUsersByAccountAndId(String account, String id) {
		return userDao.findUsersByAccountAndId(account, id);
	}
	
	
	@Override
	public void saveUserAndRole(User user, String... roleIds) {
		//1、保存用户
		save(user);
		//2、保存用户角色
		if(roleIds != null){
			for(String roleId :roleIds){
				userDao.saveUserRole(new UserRole(new UserRoleId(new Role(roleId), user.getId())));
			}
		}
	}

	@Override
	public void updateUserAndRole(User user, String... roleIds) {
		//1、删除该用户对应的所有角色
		userDao.deleteUserRoleByUserId(user.getId());
		//2、更新用户
		update(user);
		//3、保存用户角色
		if(roleIds != null){
			for(String roleId :roleIds){
				userDao.saveUserRole(new UserRole(new UserRoleId(new Role(roleId), user.getId())));
			}
		}

	}

	@Override
	public List<UserRole> findUserRolesByUserId(String id) {
		return userDao.findUserRolesByUserId(id);
	}

	@Override
	public List<User> findUsersByAccountAndPass(String account,String password) {
		return userDao.findUsersByAccountAndPass(account,password);
	}

	

}
