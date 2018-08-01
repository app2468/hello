package cn.itcast.nsfw.complain.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.core.util.PageResult;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.complain.dao.ComplainDao;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.service.ComplainService;

@Service("complainService")
public class ComplainServiceImpl extends BaseServiceImpl<Complain> implements ComplainService {

	
	private ComplainDao complainDao;
	
	@Resource
	public void setComplainDao(ComplainDao complainDao) {
         super.setBaseDao(complainDao);
         this.complainDao=complainDao;
	}
	//自动受理投诉信息
	public void autoDeal() {
		//本月1号的时间
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);//1号
		cal.set(Calendar.HOUR_OF_DAY, 0);//0时
		cal.set(Calendar.MINUTE, 0);//0分
		cal.set(Calendar.SECOND, 0);//0秒
		
		complainDao.updateStateByStateAndBeforeCompTime(Complain.COMPLAIN_STATE_INVALID, Complain.COMPLAIN_STATE_UNDONE, cal.getTime());
	}

	@Override
	public List<Map<String, Object>> getStatisticDataByYear(int year) {
		//1、根据年度查询投诉数
		List<Object[]> list = complainDao.getStatisticDataByYear(year);
		//2、解析并返回符合fusionchart格式的json字符串需要的对象
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		if(list != null){
			Map<String, Object> map = null;
			Calendar cal = Calendar.getInstance();
			int curMonth = cal.get(Calendar.MONTH) + 1;//当前月份
			boolean isCurrentYear = (year == cal.get(Calendar.YEAR));//是否当前年度
			int temMonth = 0;
			for(Object[] obj: list){
				map = new HashMap<String, Object>();
				temMonth = Integer.parseInt(obj[0] + "");//月份
				map.put("label", temMonth + "月");
				if(isCurrentYear){//当前年度：将未到的月份的投诉数改为空字符串
					if(temMonth <= curMonth){//已过月份
						map.put("value", obj[1]!=null?obj[1]:0);
					} else {//未到月份，将月份的投诉数改为空字符串
						map.put("value", "");
					}
				} else {//非当前年度；将投诉数出现Null值获取空值的话置为0
					map.put("value", obj[1]!=null?obj[1]:0);
				}
				resultList.add(map);
			}
		}
		return resultList;
	}
	
}
