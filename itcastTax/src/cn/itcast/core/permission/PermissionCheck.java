package cn.itcast.core.permission;

import cn.itcast.nsfw.user.entity.User;
/**
 * 
 * @author 隔壁老刘
 * 检测user用户是否对某个模块访问
 *user 用户
 *code 对应的权限值
 */
public interface PermissionCheck {
	public abstract boolean isAccessble(User user,String code);

}
