package com.example.busschedule;

public class Time {
	public Time(int hour,int minute) {
		// TODO Auto-generated constructor stub
		this.hour=hour;
		this.minute=minute;
	}
	
	private int minute;
	private int hour;
	
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	public void setTime(int hour,int minute){
		this.hour=hour;
		this.minute=minute;
	}
	
	/**
	 * ����25����
	 */
	public void addTime(){//ÿ���25����
		minute+=25;
		if(minute>=60){
			hour+=1;
			minute-=60;
		}
	}
	
	public String showTime(){
		String s="";
		if(minute==0)
			s=hour+"��";
		else
			s=hour+"��"+minute+"��";
		return s;
	}
}
