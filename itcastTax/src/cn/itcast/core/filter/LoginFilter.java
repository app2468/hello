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
		//�ж��Ƿ��ǵ�¼����
		String uri = request.getRequestURI();
		if(!uri.contains("/sys/login_")){//�ǵ�¼����
			//�ж�session���Ƿ����û���Ϣ
			if(request.getSession().getAttribute(Constant.USER) != null){//�Ѿ���¼
				//�ж��Ƿ������˰����ϵͳ
				if(uri.contains("/nsfw/")){
					User user = (User)request.getSession().getAttribute(Constant.USER);
					//������˰����ϵͳ,��Ҫ����Ȩ���ж�
					//WebApplicationContextUtils��ȡ��������Ӧ����������ʱʵ������ioc����
					WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
					PermissionCheck pc = (PermissionCheck)context.getBean("permissionCheck");
					if(pc.isAccessble(user, "nsfw")){
						//��Ȩ��
						chain.doFilter(request, response);
					} else {
						//û��Ȩ�ޣ���ת��û��Ȩ����ʾҳ��
						response.sendRedirect(request.getContextPath() + "/sys/login_toNoPermissionUI.action");
					}
				}else{
					//����˰����ϵͳ
					chain.doFilter(request, response);
				}
			}else{//û�е�¼����ת����¼ҳ��
				response.sendRedirect(request.getContextPath() + "/sys/login_toLoginUI.action");				
			}
			
		}else{
			//��¼����
			chain.doFilter(request, response);
			
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
