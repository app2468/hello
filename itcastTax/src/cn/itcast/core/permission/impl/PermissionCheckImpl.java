package cn.itcast.core.permission.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.itcast.core.permission.PermissionCheck;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.entity.RolePrivilege;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.service.UserService;

public class PermissionCheckImpl implements PermissionCheck{
	
    @Resource
	private UserService userService;
	
    
    @Override
    public boolean isAccessble(User user, String code) {
		//1、获取用户所对应的权限集合
    	List<UserRole> list = user.getUserRoles();
    	if(list==null){
    		list=userService.findUserRolesByUserId(user.getId());
    	}
		//2、将code与用户权限集合进行对比
		Role role=null;
		for(UserRole ur:list){
			role=ur.getId().getRole();
			for(RolePrivilege rp:role.getRolePrivileges()){
				if(code.equals(rp.getId().getCode())){
					//有权限
					return true;
				}
			}
			
		}
		return false;
	}


}
