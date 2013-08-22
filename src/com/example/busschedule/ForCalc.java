package com.example.busschedule;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ForCalc extends Time {
	public ForCalc(int hour, int minute) {
		super(hour, minute);
		// TODO Auto-generated constructor stub
	}

	public static Calendar calendar=new GregorianCalendar();
	public static Time nowTime=new Time(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
	
    public static final Time firstTime1 = new Time(6, 0);// 早班车6点
	public static final Time lastTime1 = new Time(22, 15);
	
	public static final Time firstTime2 = new Time(5, 25);//往淞虹路方向 早班车5点25到达
	public static final Time lastTime2 = new Time(21, 40);
	
	
	public static int betweenMin(Time nowTime, Time busTime) {
		int min1 = nowTime.getHour() * 60 + nowTime.getMinute();
		int min2 = busTime.getHour() * 60 + busTime.getMinute();
		return min1 - min2;
	}
	
	public static boolean isEqual(Time time1,Time time2){
		if(time1.getHour()==time2.getHour()&&time1.getMinute()==time2.getMinute())
			return true;
		else 
			return false;
	}
	
	
	//以下两个方法略有不同 calcArriveTime是先new  而calcStartTime在if和else if两种情况下不使用new 
	//如果calcArriveTime采用和calcStartTime一样的方法 会导致调用时出错（ToRailway2.toCalc函数调用）
	public static Time calcStartTime(Time calcTime) {
		if(betweenMin(calcTime,firstTime1)<0)
			return firstTime1;
		else if (betweenMin(calcTime, lastTime1)>0) 
			return lastTime1;
		else {
			Time busTime = new Time(firstTime1.getHour(),firstTime1.getMinute());
			while (betweenMin(calcTime, busTime) >= 25) {
				busTime.addTime();
			}
			return busTime;
		}
	}
	
	public static Time calcArriveTime(Time calcTime){
		Time arriveTime;
		if(betweenMin(calcTime,firstTime2)<0)
			arriveTime = new Time(firstTime2.getHour(), firstTime2.getMinute());
		else if (betweenMin(calcTime, lastTime2)>0) 
			arriveTime = new Time(lastTime2.getHour(), lastTime2.getMinute());
		//Time busTime = earlyTime; 如果直接赋值 busTime修改后 earlyTime也变了
		else {
			arriveTime = new Time(firstTime2.getHour(), firstTime2.getMinute());
			while (betweenMin(calcTime, arriveTime) > 0) {
				arriveTime.addTime();
			}
		}
		return arriveTime;
	}
}
