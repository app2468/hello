package cn.itcast.nsfw.complain;

import java.util.Timer;

public class MyTimer {
	public static void doTask(){
		Timer timer=new Timer();
		//延后1秒执行，时间间隔为2秒，当任务未完成则等待任务完成后再执行下一次任务
	    timer.schedule(new MyTask(), 1000,2000);
	}
	public static void main(String [] args){
		doTask();
	}

}
