package com.example.user.testfeature;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class BarChartActivity extends Activity {

	BarChart mBarChart;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_chart);
		mBarChart = (BarChart) findViewById(R.id.barChart);
		BarData mBarData = getBarData(4, 100);
		showBarChart(mBarChart, mBarData);
	}

	private void showBarChart(BarChart barChart, BarData barData) {
		barChart.setDrawBorders(false);  ////是否在折线图上添加边框

//		barChart.setDescription(null);// 数据描述

		// 如果没有数据的时候，会显示这个，类似ListView的EmptyView
//		barChart.setNoDataTextDescription("You need to provide data for the chart.");

		barChart.setDrawGridBackground(false); // 是否显示表格颜色
//		barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

		barChart.setTouchEnabled(true); // 设置是否可以触摸

		barChart.setDragEnabled(true);// 是否可以拖拽
		barChart.setScaleEnabled(true);// 是否可以缩放

		barChart.setPinchZoom(false);//

		//      barChart.setBackgroundColor();// 设置背景

		barChart.setDrawBarShadow(true);

		barChart.setData(barData); // 设置数据

		Legend mLegend = barChart.getLegend(); // 设置比例图标示

		mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
		mLegend.setFormSize(6f);// 字体
		mLegend.setTextColor(Color.BLACK);// 颜色



		barChart.setDrawBarShadow(false);
		barChart.setDrawValueAboveBar(true);

		barChart.getDescription().setEnabled(false);

		// if more than 60 entries are displayed in the chart, no values will be
		// drawn
		barChart.setMaxVisibleValueCount(60);

		// scaling can now only be done on x- and y-axis separately
		barChart.setPinchZoom(false);

		barChart.setDrawGridBackground(false);

//		IAxisValueFormatter custom = new MyAxisValueFormatter();

		XAxis xAxis = barChart.getXAxis();
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//		xAxis.setTypeface(mTfLight);
		xAxis.setDrawGridLines(false);
		xAxis.setGranularity(1f); // only intervals of 1 day
		xAxis.setLabelCount(7);
//		xAxis.setValueFormatter(xAxisFormatter);


		YAxis leftAxis = barChart.getAxisLeft();
		leftAxis.setLabelCount(8, false);
//		leftAxis.setValueFormatter(custom);
		leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
		leftAxis.setSpaceTop(15f);
		leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

		YAxis rightAxis = barChart.getAxisRight();
		rightAxis.setDrawGridLines(false);
		rightAxis.setLabelCount(8, false);
//		rightAxis.setValueFormatter(custom);
		rightAxis.setSpaceTop(15f);
		rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


		//      X轴设定
		//      XAxis xAxis = barChart.getXAxis();
		//      xAxis.setPosition(XAxisPosition.BOTTOM);

		barChart.animateX(2500); // 立即执行的动画,x轴
	}

	private BarData getBarData(int count, float range) {
		ArrayList<String> xValues = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			xValues.add("第" + (i + 1) + "季度");
		}

		ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();

		for (int i = 0; i < count; i++) {
			float value = (float) (Math.random() * range/*100以内的随机数*/) + 3;
			yValues.add(new BarEntry(i, value));
		}

		// y轴的数据集合
		BarDataSet barDataSet = new BarDataSet(yValues, "测试饼状图");

//		barDataSet.setColor(Color.rgb(114, 188, 223));

		ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
		barDataSets.add(barDataSet); // add the datasets

		BarData barData = new BarData(barDataSet);

		return barData;
	}

}
