package cn.itcast.nsfw.complain.service;

import java.util.List;
import java.util.Map;

import cn.itcast.core.service.BaseService;
import cn.itcast.nsfw.complain.entity.Complain;

public interface ComplainService extends BaseService<Complain>{

	//自动受理投诉信息
	public void autoDeal();
    //根据年度查询年度对应的12个月的投诉数
	public List<Map<String,Object>> getStatisticDataByYear(int year);

}
