package cn.itcast.nsfw.complain;

import java.util.Timer;

public class MyTimer {
	public static void doTask(){
		Timer timer=new Timer();
		//�Ӻ�1��ִ�У�ʱ����Ϊ2�룬������δ�����ȴ�������ɺ���ִ����һ������
	    timer.schedule(new MyTask(), 1000,2000);
	}
	public static void main(String [] args){
		doTask();
	}

}
