package com.example.busschedule;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

public class ToRailway2 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toreailway2);

		Time now = new Time(ForCalc.nowTime.getHour(),
				ForCalc.nowTime.getMinute());

		toCalc(now);

		TimePicker timePicker2 = (TimePicker) findViewById(R.id.timePicker2);

		timePicker2
				.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
					public void onTimeChanged(TimePicker view, int hourOfDay,
							int minute) {
						Time newTime = new Time(hourOfDay, minute);
						toCalc(newTime);
					}
				});

	}

	public void toCalc(Time now) {
		final TextView theInfoTextView = (TextView) findViewById(R.id.arriveInfoText);
		final TextView arriveTextView = (TextView) findViewById(R.id.arriveText);
		final TextView theNextTextView = (TextView) findViewById(R.id.nextText);

		// Time arriveTime = new Time(ForCalc.calcArriveTime(now).getHour(),
		// ForCalc.calcArriveTime(now).getMinute());
		Time arriveTime = ForCalc.calcArriveTime(now);
		// 如果按照calcStartTime方法写calcArriveTime 此处会出错：下面的if中addTime会一直累加
		// 每次的arriveTime不是重新计算所得 而是上一次的
		// 不过可以用上面new的方法解决

		// 现在时间早于早班车
		if (ForCalc.isEqual(arriveTime, ForCalc.firstTime2)
				&& ForCalc.betweenMin(now, ForCalc.firstTime2) < 0) {
			theInfoTextView.setText("早班车将在"
					+ Math.abs(ForCalc.betweenMin(now, ForCalc.firstTime2))
					+ "分钟后到达");
			arriveTextView.setText("早班车：" + arriveTime.showTime());
			arriveTime.addTime(); // 此处导致出错！！！
			theNextTextView.setText("下一班次：" + arriveTime.showTime());
		}
		// 现在时间晚于末班车 或者恰好最后一班
		else if (ForCalc.isEqual(arriveTime, ForCalc.lastTime2)) {
			//现在时间晚于末班车
			if (ForCalc.betweenMin(now, ForCalc.lastTime2) >= 0) {
				theInfoTextView.setText("末班车已驶离"
						+ Math.abs(ForCalc.betweenMin(now, ForCalc.lastTime2))
						+ "分钟");
				arriveTextView.setText("末班车：" + arriveTime.showTime());
				theNextTextView.setText("所有车次已经结束");
			}
			else {
				theInfoTextView.setText("将在" + ForCalc.betweenMin(arriveTime, now)
						+ "分钟后到达");
				arriveTextView.setText("到达班次：" + arriveTime.showTime());
				theNextTextView.setText("本班次已是末班车");
			}
		}

		else {
			theInfoTextView.setText("将在" + ForCalc.betweenMin(arriveTime, now)
					+ "分钟后到达");
			arriveTextView.setText("到达班次：" + arriveTime.showTime());
			arriveTime.addTime();// 增加25分钟 显示下一班
			theNextTextView.setText("下一班次：" + arriveTime.showTime());
		}
	}

}
