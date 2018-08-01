package cn.itcast.core.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.core.constant.Constant;
import cn.itcast.core.permission.PermissionCheck;
import cn.itcast.nsfw.user.entity.User;


public class LoginFilter implements Filter{

	@Override
	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		//判断是否是登录请求
		String uri = request.getRequestURI();
		if(!uri.contains("/sys/login_")){//非登录请求
			//判断session中是否有用户信息
			if(request.getSession().getAttribute(Constant.USER) != null){//已经登录
				//判断是否访问纳税服务系统
				if(uri.contains("/nsfw/")){
					User user = (User)request.getSession().getAttribute(Constant.USER);
					//访问纳税服务系统,需要进行权限判断
					//WebApplicationContextUtils获取的是随着应服务器启动时实例化的ioc容器
					WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
					PermissionCheck pc = (PermissionCheck)context.getBean("permissionCheck");
					if(pc.isAccessble(user, "nsfw")){
						//有权限
						chain.doFilter(request, response);
					} else {
						//没有权限，跳转到没有权限提示页面
						response.sendRedirect(request.getContextPath() + "/sys/login_toNoPermissionUI.action");
					}
				}else{
					//非纳税服务系统
					chain.doFilter(request, response);
				}
			}else{//没有登录，跳转到登录页面
				response.sendRedirect(request.getContextPath() + "/sys/login_toLoginUI.action");				
			}
			
		}else{
			//登录请求
			chain.doFilter(request, response);
			
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
