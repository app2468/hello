package cn.itcast.nsfw.info.action;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.exception.ActionException;
import cn.itcast.core.util.PageResult;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.info.service.InfoService;

import com.opensymphony.xwork2.ActionContext;

public class InfoAction extends BaseAction {
	
	@Resource
	private InfoService infoService;
	
	private Info info;
	//��ѯ
	private String strTitle;

	
	//�б�
	public String listUI() throws ActionException {
		//������Ϣ���ͼ���
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		try {
			QueryHelper queryHelper = new QueryHelper(Info.class, "i");
			if(info != null){
				if(StringUtils.isNotBlank(info.getTitle())){
					//����
					info.setTitle(URLDecoder.decode(info.getTitle(),"utf-8"));
					queryHelper.addCondition("i.title like ?", "%" + info.getTitle() + "%");
				}
			}
			queryHelper.addOrderByProperty("i.createTime", QueryHelper.ORDER_BY_DESC);
			pageResult = infoService.getPageResult(queryHelper, getPageNo(), getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listUI";
	}
	
	//��ת������ҳ��
	public String addUI(){
		//������Ϣ���ͼ���
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		strTitle=info.getTitle();
		info = new Info();
		info.setCreateTime(new Timestamp(new Date().getTime()));
		return "addUI";
	}
	//��������
	public String add(){
		try {
			//�����û�
			if(info != null){
				infoService.save(info);
			}
			info=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//��ת���༭ҳ��
	public String editUI(){
		//������Ϣ���ͼ���
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		if(info != null && StringUtils.isNotBlank(info.getInfoId())){
		    //�����ѯ��������
			strTitle=info.getTitle();
			info = infoService.findObjectById(info.getInfoId());
		}
		return "editUI";
	}
	//����༭
	public String edit(){
		try {
			if(info != null){
				infoService.update(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//����idɾ��
	public String delete(){
		if(info != null && StringUtils.isNotBlank(info.getInfoId())){
			strTitle=info.getTitle();
			infoService.delete(info.getInfoId());
		}
		return "list";
	}
	//����ɾ��
	public String deleteSelected(){
		if(selectedRow != null){
			strTitle=info.getTitle();
			for(String id: selectedRow){
				infoService.delete(id);
			}
		}
		return "list";
	}
	//�첽��Ϣ����
	public void publicInfo(){
		try {
			if(info != null){
				//�Ȳ��� �ٸ���  ����
				Info tem = infoService.findObjectById(info.getInfoId());
				tem.setState(info.getState());
				infoService.update(tem);
				//�˺�Ψһ��У��\���
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html;charset=utf-8");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write("����״̬�ɹ�".getBytes("utf-8"));
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	public String getStrTitle() {
		return strTitle;
	}

	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}

	
	
	
}
