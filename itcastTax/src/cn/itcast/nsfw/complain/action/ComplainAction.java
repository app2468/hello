package cn.itcast.nsfw.complain.action;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.entity.ComplainReply;
import cn.itcast.nsfw.complain.service.ComplainService;

import com.opensymphony.xwork2.ActionContext;

public class ComplainAction extends BaseAction {

	@Resource
	private ComplainService complainService;
	
	private Complain complain;
	private ComplainReply reply;
	
	private String strTitle;
	private String strState;
	private String startTime;
	private String endTime;
	
	private Map<String,Object> statisticMap;
	
	//???
	public String listUI(){
		try {
			//?????????????
			ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
			
			QueryHelper queryHelper = new QueryHelper(Complain.class, "c");
			
			if(StringUtils.isNotBlank(startTime)){
				startTime = decode(startTime);
				queryHelper.addCondition("c.compTime >= ?", DateUtils.parseDate(startTime + ":00", "yyyy-MM-dd HH:mm:ss"));
			}
			if(StringUtils.isNotBlank(endTime)){
				endTime = decode(endTime);
				queryHelper.addCondition("c.compTime <= ?", DateUtils.parseDate(endTime + ":00", "yyyy-MM-dd HH:mm:ss"));
			}
			if(complain != null){
				if(StringUtils.isNotBlank(complain.getState())){
					queryHelper.addCondition("c.state = ?", complain.getState() );
				}
				if(StringUtils.isNotBlank(complain.getCompTitle())){
					complain.setCompTitle(decode(complain.getCompTitle()));
					queryHelper.addCondition("c.compTitle like ?", "%" + complain.getCompTitle() + "%" );
				}
			}
			
			//??????????
			queryHelper.addOrderByProperty("c.state", QueryHelper.ORDER_BY_ASC);
			//??????????????
			queryHelper.addOrderByProperty("c.compTime", QueryHelper.ORDER_BY_ASC);
			
			pageResult = complainService.getPageResult(queryHelper, getPageNo(), getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listUI";
	}

	
	//????????????
	public String dealUI(){
		//?????????????
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		if(complain != null){
			strTitle = complain.getCompTitle();
			strState = complain.getState();
			complain = complainService.findObjectById(complain.getCompId());
		}
		return "dealUI";
	}
	
	
	//???????????
	public String deal(){
		if(complain != null && reply != null){
			//1????????????????? ??????
			Complain tem = complainService.findObjectById(complain.getCompId());
			if(Complain.COMPLAIN_STATE_UNDONE.equals(tem.getState())){//????????????????????????????????
				tem.setState(Complain.COMPLAIN_STATE_DONE);
			}
			//2?????????????
			reply.setComplain(tem);
			reply.setReplyTime(new Timestamp(new Date().getTime()));
			tem.getComplainReplies().add(reply);
			complainService.update(tem);
		}
		return "list";
	}
	

	//???????????
	public String annualStatisticChartUI(){
		return "annualStatisticChartUI";
	}
	
	
	//???????????
	public String getAnnualStatisticData(){
		//1????????
		int year=0;
		HttpServletRequest request = ServletActionContext.getRequest();
		if(request.getParameter("year")!=null){
			year=Integer.parseInt(request.getParameter("year"));
		}else{
			//???????????
			year=Calendar.getInstance().get(Calendar.YEAR);
		}
		//2??????????????12???¡¤??????????????????????
		statisticMap=new HashMap<String,Object>();
		statisticMap.put("msg", "success");
		statisticMap.put("chartData", complainService.getStatisticDataByYear(year));
		return "statisticData";
	}
	
	
	
	
	public Complain getComplain() {
		return complain;
	}


	public void setComplain(Complain complain) {
		this.complain = complain;
	}


	public String getStrTitle() {
		return strTitle;
	}


	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}


	public String getStrState() {
		return strState;
	}


	public void setStrState(String strState) {
		this.strState = strState;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public ComplainReply getReply() {
		return reply;
	}

	public void setReply(ComplainReply reply) {
		this.reply = reply;
	}


	public Map<String, Object> getStatisticMap() {
		return statisticMap;
	}

	 
	
	
}
