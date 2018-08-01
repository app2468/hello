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
	
	//��ת��ϵͳ��ҳ
	public String execute(){
		//������Ϣ���ͼ���
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		//��Ϣ�б�
		QueryHelper queryHelper1 = new QueryHelper(Info.class, "");
		queryHelper1.addCondition("state=?", Info.INFO_STATE_PUBLIC);//��ѯ����״̬����Ϣ
		//���ݴ���ʱ�併������
		queryHelper1.addOrderByProperty("createTime", QueryHelper.ORDER_BY_DESC);
		List<Info> infoList = infoService.getPageResult(queryHelper1, 1, 5).getItems();
		ActionContext.getContext().getContextMap().put("infoList", infoList);
		
		
		//�ҵ�Ͷ���б�
		//����Ͷ��״̬����
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(Constant.USER);
		QueryHelper queryHelper2 = new QueryHelper(Complain.class, "");
		queryHelper2.addCondition("compName=?", user.getName());
		queryHelper2.addOrderByProperty("compTime", QueryHelper.ORDER_BY_DESC);
		List<Complain> complainList = complainService.getPageResult(queryHelper2, 1, 6).getItems();
		ActionContext.getContext().getContextMap().put("complainList", complainList);
		
		return "home";
		
	}
	//��ת����ҪͶ��ҳ��
	public String complainAddUI(){
		return "complainAddUI";
	}

	//�޿��    ���ݲ��Ż�ȡ�ò����µ�������Ա�б�
	public void getUserJson(){
		try {
			//1����ȡ����ֵ
			String dept = ServletActionContext.getRequest().getParameter("dept");
			if(StringUtils.isNotBlank(dept)){
				//2�����ݲ���ֵ���û����в�ѯ��Ӧ�����µ��û���¼
				QueryHelper queryHelper = new QueryHelper(User.class, "");
				queryHelper.addCondition("dept like ?", "%" + dept);
				List<User> userList = userService.findObjects(queryHelper);
				
				//����һ��json����
				JSONObject jso = new JSONObject();
				jso.put("msg", "success");
				jso.accumulate("userList", userList);
				
				//3�����
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
	
	//�п��ʹ��    ���ݲ��Ż�ȡ�ò����µ�������Ա�б�
	public String getUserJson2(){
		try {
			//1����ȡ����ֵ
			String dept = ServletActionContext.getRequest().getParameter("dept");
			if(StringUtils.isNotBlank(dept)){
				//2�����ݲ���ֵ���û����в�ѯ��Ӧ�����µ��û���¼
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
	
	//����Ͷ����Ϣ
	public void complainAdd(){
		try {
			if(comp!=null){//1����ȡͶ����Ϣ
				//2������Ͷ����Ϣ
				comp.setCompTime(new Timestamp(new Date().getTime()));
				comp.setState(Complain.COMPLAIN_STATE_UNDONE);//Ĭ��Ͷ��״̬Ϊ ������
				complainService.save(comp);
				//3�����
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
	
	
	//�鿴��Ϣ��ϸ��Ϣ
	public String infoViewUI(){
		//������Ϣ���ͼ���
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		if(info != null){
			info = infoService.findObjectById(info.getInfoId());
		}
		return "infoViewUI";
	}
	
	//�鿴Ͷ����ϸ��Ϣ
	public String complainViewUI(){
		//������Ϣ���ͼ���
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
