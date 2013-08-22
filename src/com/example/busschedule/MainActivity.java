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

		// ����ʱ��������೵
		if (ForCalc.isEqual(startTime, ForCalc.firstTime1)
				&& ForCalc.betweenMin(now, ForCalc.firstTime1) < 0) {
			infoTextView.setText("��೵����"
					+ Math.abs(ForCalc.betweenMin(now, ForCalc.firstTime1))
					+ "���Ӻ����");
			startTextView.setText("��೵δ����");
			readyTextView.setText("��೵��" + startTime.showTime());
		}
		// ����ʱ������ĩ�೵
		else if (ForCalc.isEqual(startTime, ForCalc.lastTime1)
				&& ForCalc.betweenMin(now, ForCalc.lastTime1) >= 0) {
			infoTextView.setText("ĩ�೵�ѳ���"
					+ Math.abs(ForCalc.betweenMin(now, ForCalc.lastTime1))
					+ "����");
			startTextView.setText("ĩ�೵��" + startTime.showTime());
			readyTextView.setText("ĩ�೵�ѳ���");
		}

		else {
			infoTextView.setText("������ѳ���" + ForCalc.betweenMin(now, startTime)
					+ "����");
			startTextView.setText("������Σ�" + startTime.showTime());
			startTime.addTime();// ����25���� ��ʾ��һ��
			readyTextView.setText("��һ��Σ�" + startTime.showTime());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "������·����");
		menu.add(0, 2, 2, "�˳�");
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
		// ����Ƿ��ؼ�,ֱ�ӷ��ص�����
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			confirmExit();
		}

		return super.onKeyDown(keyCode, event);
	}

	public void confirmExit() {
		new AlertDialog.Builder(MainActivity.this)
				.setMessage("ȷ��Ҫ�˳���( >�n<��)��")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						System.exit(0);
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}

}
