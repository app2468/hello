package cn.itcast.nsfw.complain.dao;

import java.util.Date;
import java.util.List;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.complain.entity.Complain;

public interface ComplainDao extends BaseDao<Complain> {


	/**
	 * 根据投诉状态和投诉时间更新投诉状态
	 * @param cOMPLAIN_STATE_INVALID
	 * @param cOMPLAIN_STATE_UNDONE
	 * @param date
	 */
	public void updateStateByStateAndBeforeCompTime(String updateState, String whereState, Date compTime);

	//根据年度查询投诉数
	public List<Object[]> getStatisticDataByYear(int year);


}
