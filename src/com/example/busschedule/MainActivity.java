package com.example.busschedule;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Time now = new Time(ForCalc.nowTime.getHour(),
				ForCalc.nowTime.getMinute());

		toCalc(now);

		TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker1);

		timePicker
				.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
					public void onTimeChanged(TimePicker view, int hourOfDay,
							int minute) {
						Time newTime = new Time(hourOfDay, minute);
						toCalc(newTime);
					}
				});

	}

	public void toCalc(Time now) {
		final TextView infoTextView = (TextView) findViewById(R.id.infoText);
		final TextView startTextView = (TextView) findViewById(R.id.startText);
		final TextView readyTextView = (TextView) findViewById(R.id.readyText);

		Time startTime = ForCalc.calcStartTime(now);

		// 现在时间早于早班车
		if (ForCalc.isEqual(startTime, ForCalc.firstTime1)
				&& ForCalc.betweenMin(now, ForCalc.firstTime1) < 0) {
			infoTextView.setText("早班车将在"
					+ Math.abs(ForCalc.betweenMin(now, ForCalc.firstTime1))
					+ "分钟后出发");
			startTextView.setText("早班车未出发");
			readyTextView.setText("早班车：" + startTime.showTime());
		}
		// 现在时间晚于末班车
		else if (ForCalc.isEqual(startTime, ForCalc.lastTime1)
				&& ForCalc.betweenMin(now, ForCalc.lastTime1) >= 0) {
			infoTextView.setText("末班车已出发"
					+ Math.abs(ForCalc.betweenMin(now, ForCalc.lastTime1))
					+ "分钟");
			startTextView.setText("末班车：" + startTime.showTime());
			readyTextView.setText("末班车已出发");
		}

		else {
			infoTextView.setText("本班次已出发" + ForCalc.betweenMin(now, startTime)
					+ "分钟");
			startTextView.setText("出发班次：" + startTime.showTime());
			startTime.addTime();// 增加25分钟 显示下一班
			readyTextView.setText("下一班次：" + startTime.showTime());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "往淞虹路方向");
		menu.add(0, 2, 2, "退出");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, ToRailway2.class);
			startActivity(intent);
		} else if (item.getItemId() == 2) {
			confirmExit();
		}
		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			confirmExit();
		}

		return super.onKeyDown(keyCode, event);
	}

	public void confirmExit() {
		new AlertDialog.Builder(MainActivity.this)
				.setMessage("确定要退出吗？( >﹏<。)～")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						System.exit(0);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}

}
