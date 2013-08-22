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
		// �������calcStartTime����дcalcArriveTime �˴�����������if��addTime��һֱ�ۼ�
		// ÿ�ε�arriveTime�������¼������� ������һ�ε�
		// ��������������new�ķ������

		// ����ʱ��������೵
		if (ForCalc.isEqual(arriveTime, ForCalc.firstTime2)
				&& ForCalc.betweenMin(now, ForCalc.firstTime2) < 0) {
			theInfoTextView.setText("��೵����"
					+ Math.abs(ForCalc.betweenMin(now, ForCalc.firstTime2))
					+ "���Ӻ󵽴�");
			arriveTextView.setText("��೵��" + arriveTime.showTime());
			arriveTime.addTime(); // �˴����³�������
			theNextTextView.setText("��һ��Σ�" + arriveTime.showTime());
		}
		// ����ʱ������ĩ�೵ ����ǡ�����һ��
		else if (ForCalc.isEqual(arriveTime, ForCalc.lastTime2)) {
			//����ʱ������ĩ�೵
			if (ForCalc.betweenMin(now, ForCalc.lastTime2) >= 0) {
				theInfoTextView.setText("ĩ�೵��ʻ��"
						+ Math.abs(ForCalc.betweenMin(now, ForCalc.lastTime2))
						+ "����");
				arriveTextView.setText("ĩ�೵��" + arriveTime.showTime());
				theNextTextView.setText("���г����Ѿ�����");
			}
			else {
				theInfoTextView.setText("����" + ForCalc.betweenMin(arriveTime, now)
						+ "���Ӻ󵽴�");
				arriveTextView.setText("�����Σ�" + arriveTime.showTime());
				theNextTextView.setText("���������ĩ�೵");
			}
		}

		else {
			theInfoTextView.setText("����" + ForCalc.betweenMin(arriveTime, now)
					+ "���Ӻ󵽴�");
			arriveTextView.setText("�����Σ�" + arriveTime.showTime());
			arriveTime.addTime();// ����25���� ��ʾ��һ��
			theNextTextView.setText("��һ��Σ�" + arriveTime.showTime());
		}
	}

}
