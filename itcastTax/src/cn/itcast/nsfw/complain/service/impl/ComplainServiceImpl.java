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
	//�Զ�����Ͷ����Ϣ
	public void autoDeal() {
		//����1�ŵ�ʱ��
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);//1��
		cal.set(Calendar.HOUR_OF_DAY, 0);//0ʱ
		cal.set(Calendar.MINUTE, 0);//0��
		cal.set(Calendar.SECOND, 0);//0��
		
		complainDao.updateStateByStateAndBeforeCompTime(Complain.COMPLAIN_STATE_INVALID, Complain.COMPLAIN_STATE_UNDONE, cal.getTime());
	}

	@Override
	public List<Map<String, Object>> getStatisticDataByYear(int year) {
		//1��������Ȳ�ѯͶ����
		List<Object[]> list = complainDao.getStatisticDataByYear(year);
		//2�����������ط���fusionchart��ʽ��json�ַ�����Ҫ�Ķ���
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		if(list != null){
			Map<String, Object> map = null;
			Calendar cal = Calendar.getInstance();
			int curMonth = cal.get(Calendar.MONTH) + 1;//��ǰ�·�
			boolean isCurrentYear = (year == cal.get(Calendar.YEAR));//�Ƿ�ǰ���
			int temMonth = 0;
			for(Object[] obj: list){
				map = new HashMap<String, Object>();
				temMonth = Integer.parseInt(obj[0] + "");//�·�
				map.put("label", temMonth + "��");
				if(isCurrentYear){//��ǰ��ȣ���δ�����·ݵ�Ͷ������Ϊ���ַ���
					if(temMonth <= curMonth){//�ѹ��·�
						map.put("value", obj[1]!=null?obj[1]:0);
					} else {//δ���·ݣ����·ݵ�Ͷ������Ϊ���ַ���
						map.put("value", "");
					}
				} else {//�ǵ�ǰ��ȣ���Ͷ��������Nullֵ��ȡ��ֵ�Ļ���Ϊ0
					map.put("value", obj[1]!=null?obj[1]:0);
				}
				resultList.add(map);
			}
		}
		return resultList;
	}
	
}
