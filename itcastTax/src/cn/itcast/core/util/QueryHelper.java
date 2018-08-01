package cn.itcast.core.util;

import java.util.ArrayList;
import java.util.List;

public class QueryHelper {

	
	// from�Ӿ�
	private String fromeClause="";
	// where�Ӿ䣺
	private String whereClause="";
	// order by�Ӿ䣺
	private String orderByClause="";
	//��ѯ������ֵ
	private List<Object> parameters;
	
	public  static String ORDER_BY_ASC="ASC";//����
	public  static String ORDER_BY_DESC="DESC";//����
	
	
//    public QueryHelper(Class clazz,String alias){
//    	fromeClause = "FROM" +  clazz.getSimpleName() + " " + alias;
//    } 
//    
    /**
	 * ����from �Ӿ�
	 * @param clazz ʵ����
	 * @param alias ����
	 * �ر�ע�⣺from ����һ���ո�   �ַ���ƴ��
	 */
	public QueryHelper(Class clazz, String alias){
		fromeClause = "FROM " + clazz.getSimpleName() + " " + alias;
	}
	
	
	/**
	 * ����where�Ӿ�
	 * @param condition ��ѯ�������磺i.title like ?
	 * @param params ��ѯ������?��Ӧ�Ĳ�ѯ����ֵ���磺%����%
	 */
	public void addCondition(String condition, Object... params){
		if (whereClause.length()>0) {//�ǵ�һ����ѯ����
			whereClause += " AND " + condition;
		} else {//��һ����ѯ����
			whereClause = " WHERE " + condition;
		}
		//��ѯ������ֵ
		if(parameters == null){
			parameters = new ArrayList<Object>();
		}
		//������ѯ����ֵ
		if(params != null){
			for(Object param: params){
				parameters.add(param);
			}
		}
	}
	/**
	 * ����order by�Ӿ�
	 * @param property �������ԣ��磺i.createTime
	 * @param order �������У�DESC �� ASC
	 */
	public void addOrderByProperty(String property, String order){
		if (orderByClause.length()>0) {//�ǵ�һ����������
			orderByClause += "," + property + " " + order;
		} else {//��һ����������
			orderByClause = " ORDER BY " + property + " " + order;
		}
	}
	

	//��ȡ��ѯ���
	public String getListHql(){
		return fromeClause + whereClause + orderByClause;
	}
	//��ʱ��ҳ�л�ȡͳ���ܼ�¼���Ĳ�ѯ���
	public String getCountHql(){
		return "SELECT COUNT(*) " + fromeClause + whereClause;
	}
	//��ȡ��ѯ����ж�Ӧ�Ĳ�ѯ����ֵ����
   public List<Object> getParameters(){
	   return  parameters;
   }
}
