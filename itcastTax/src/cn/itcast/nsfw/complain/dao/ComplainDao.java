package cn.itcast.nsfw.complain.dao;

import java.util.Date;
import java.util.List;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.complain.entity.Complain;

public interface ComplainDao extends BaseDao<Complain> {


	/**
	 * ����Ͷ��״̬��Ͷ��ʱ�����Ͷ��״̬
	 * @param cOMPLAIN_STATE_INVALID
	 * @param cOMPLAIN_STATE_UNDONE
	 * @param date
	 */
	public void updateStateByStateAndBeforeCompTime(String updateState, String whereState, Date compTime);

	//������Ȳ�ѯͶ����
	public List<Object[]> getStatisticDataByYear(int year);


}
