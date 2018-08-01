package cn.itcast.home.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.constant.Constant;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.service.ComplainService;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.info.service.InfoService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport {
	
	@Resource
	private UserService userService;
	
	@Resource
	private InfoService infoService;
	
	@Resource
	private ComplainService complainService;
	
	private Map<String,Object> return_map;
	
	private Complain comp;
	private Info info;
	
	//跳转到系统首页
	public String execute(){
		//加载信息类型集合
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		//信息列表
		QueryHelper queryHelper1 = new QueryHelper(Info.class, "");
		queryHelper1.addCondition("state=?", Info.INFO_STATE_PUBLIC);//查询发布状态的信息
		//根据创建时间降序排序
		queryHelper1.addOrderByProperty("createTime", QueryHelper.ORDER_BY_DESC);
		List<Info> infoList = infoService.getPageResult(queryHelper1, 1, 5).getItems();
		ActionContext.getContext().getContextMap().put("infoList", infoList);
		
		
		//我的投诉列表
		//加载投诉状态集合
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(Constant.USER);
		QueryHelper queryHelper2 = new QueryHelper(Complain.class, "");
		queryHelper2.addCondition("compName=?", user.getName());
		queryHelper2.addOrderByProperty("compTime", QueryHelper.ORDER_BY_DESC);
		List<Complain> complainList = complainService.getPageResult(queryHelper2, 1, 6).getItems();
		ActionContext.getContext().getContextMap().put("complainList", complainList);
		
		return "home";
		
	}
	//跳转到我要投诉页面
	public String complainAddUI(){
		return "complainAddUI";
	}

	//无框架    根据部门获取该部门下的所有人员列表
	public void getUserJson(){
		try {
			//1、获取部门值
			String dept = ServletActionContext.getRequest().getParameter("dept");
			if(StringUtils.isNotBlank(dept)){
				//2、根据部门值到用户表中查询相应部门下的用户记录
				QueryHelper queryHelper = new QueryHelper(User.class, "");
				queryHelper.addCondition("dept like ?", "%" + dept);
				List<User> userList = userService.findObjects(queryHelper);
				
				//创建一个json对象
				JSONObject jso = new JSONObject();
				jso.put("msg", "success");
				jso.accumulate("userList", userList);
				
				//3、输出
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html;charset=utf-8");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(jso.toString().getBytes("utf-8"));
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//有框架使用    根据部门获取该部门下的所有人员列表
	public String getUserJson2(){
		try {
			//1、获取部门值
			String dept = ServletActionContext.getRequest().getParameter("dept");
			if(StringUtils.isNotBlank(dept)){
				//2、根据部门值到用户表中查询相应部门下的用户记录
				QueryHelper queryHelper = new QueryHelper(User.class, "");
				queryHelper.addCondition("dept like ?", "%" + dept);
				List<User> userList = userService.findObjects(queryHelper);
				
				return_map=new HashMap<String,Object>();
				return_map.put("msg", "success");
				return_map.put("userList", userList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	return "success";
	
	}
	
	//保存投诉信息
	public void complainAdd(){
		try {
			if(comp!=null){//1、获取投诉信息
				//2、保存投诉信息
				comp.setCompTime(new Timestamp(new Date().getTime()));
				comp.setState(Complain.COMPLAIN_STATE_UNDONE);//默认投诉状态为 待受理
				complainService.save(comp);
				//3、输出
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html;charset=utf-8");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write("success".getBytes("utf-8"));
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//查看信息详细信息
	public String infoViewUI(){
		//加载信息类型集合
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		if(info != null){
			info = infoService.findObjectById(info.getInfoId());
		}
		return "infoViewUI";
	}
	
	//查看投诉详细信息
	public String complainViewUI(){
		//加载信息类型集合
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		if(comp!=null){
			comp=complainService.findObjectById(comp.getCompId());
		}
		return "complainViewUI";
	}
	
	
	
	
	
	
	public Map<String, Object> getReturn_map() {
		return return_map;
	}
	public Complain getComp() {
		return comp;
	}
	public void setComp(Complain comp) {
		this.comp = comp;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	
}
