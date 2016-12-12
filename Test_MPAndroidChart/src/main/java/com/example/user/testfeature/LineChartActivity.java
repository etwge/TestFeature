package com.example.user.testfeature;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_line_chart);
		MyLineChart chart = (MyLineChart) findViewById(R.id.chart);
		LineData chartData = getChartData();
		showChartData(chartData, chart);
	}

	private void showChartData(LineData chartData, LineChart chart) {
		XAxis xAxis = chart.getXAxis();
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
		//        xAxis.setTypeface(mTf); // 设置字体
		xAxis.setEnabled(true);
		// 上面第一行代码设置了false,所以下面第一行即使设置为true也不会绘制AxisLine
		xAxis.setDrawAxisLine(false);


		// 前面xAxis.setEnabled(false);则下面绘制的Grid不会有"竖的线"（与X轴有关）
		xAxis.setDrawGridLines(false); // 效果如下图
		xAxis.setDrawLabels(true);
		xAxis.setTextColor(Color.WHITE);
		xAxis.setTextSize(10);

		YAxis yAxis = chart.getAxisLeft();
		yAxis.setEnabled(false);

		YAxis yAxis2 = chart.getAxisRight();
		yAxis2.setEnabled(false);

		/**
		 * 设置比例图标示，就是那个一组y的value的
		 */
		Legend mLegend = chart.getLegend();
		if (false) {
			mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
			mLegend.setForm(Legend.LegendForm.LINE);
			mLegend.setFormSize(6f);
			mLegend.setTextColor(Color.BLACK);
		} else {
			mLegend.setEnabled(false);
		}
		chart.setData(chartData);
		chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
			@Override
			public void onValueSelected(Entry e, Highlight h) {
				Log.i("xxx", h.toString());
			}

			@Override
			public void onNothingSelected() {

			}
		});

		Highlight highlight = new Highlight(5,0, 0);

		chart.highlightValue(highlight, false);


//		//设置在Y轴上是否是从0开始显示
//		chart.setStartAtZero(true);
//		//是否在Y轴显示数据，就是曲线上的数据
//		chart.setDrawYValues(true);
//		//设置网格
//		chart.setDrawBorder(true);
//		chart.setBorderPositions(new BorderPosition[] {
//			BorderPosition.BOTTOM});
		//在chart上的右下角加描述
		chart.setDescription(null);
//		chart.getDescription().setText("我们");
		//设置Y轴上的单位
//		chart.setUnit("￥");
		//设置透明度
		chart.setAlpha(0.8f);
		//设置网格底下的那条线的颜色
		chart.setBorderColor(Color.argb(0, 0, 0, 0));
//		//设置Y轴前后倒置
//		chart.setInvertYAxisEnabled(false);
//		//设置高亮显示
//		chart.setHighlightEnabled(true);
		//设置是否可以触摸，如为false，则不能拖动，缩放等
		chart.setTouchEnabled(true);
		//设置是否可以拖拽，缩放
		chart.setDragEnabled(true);
		chart.setScaleEnabled(true);
		//设置是否能扩大扩小
		chart.setPinchZoom(false);

//		xAxis.setLabelCount(7, true);
		/**
		 * 设置LineChart默认显示元素个数
		 */
		chart.setVisibleXRangeMaximum(8);
		chart.setVisibleXRangeMinimum(8);
		chart.getRenderer().getPaintHighlight().setColor(Color.WHITE);
		chart.invalidate();
	}

	private LineData getChartData() {
		YourData[] dataObjects = {new YourData(1, 2), new YourData(2, 8), new YourData(3, 9), new YourData(4, 20)};
		List<Entry> entries = new ArrayList<Entry>();
		for (YourData data : dataObjects) {
			// turn your data into Entry objects
			entries.add(new Entry(data.getX(), data.getY()));
		}
		LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
		dataSet.setColor(Color.BLUE);
		dataSet.setValueTextColor(Color.DKGRAY);

		YourData[] dataObjects2 = {new YourData(3, 2), new YourData(4, 8), new YourData(5, 1), new YourData(6, 10), new YourData(7, 12),new YourData(8, 2), new YourData(9, 8), new YourData(10, 1), new YourData(11, 10), new YourData(12, 12)};
		dataObjects2 = new YourData[12];
		for (int i = 0; i < 12; i++) {
			dataObjects2[i] = new YourData(i + 1, (float) (Math.random() * 100));

		}
		List<Entry> entries2 = new ArrayList<Entry>();
		for (YourData data : dataObjects2) {
			// turn your data into Entry objects
			entries2.add(new Entry(data.getX(), data.getY()));
		}

		LineDataSet dataSet2 = new LineDataSet(entries2, "Label2"); // add entries to dataset
		dataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
		dataSet2.setColor(Color.WHITE);
		dataSet2.setValueTextColor(Color.DKGRAY);
		//		dataSet2.setDrawCubic(true);
		dataSet2.setDrawCircles(true);
		dataSet2.setDrawCircleHole(true);
		dataSet2.setCircleRadius(8);
		dataSet2.setCircleHoleRadius(6);
		dataSet2.setCircleColor(getResources().getColor(R.color.theme));
		dataSet2.setCircleColorHole(Color.WHITE);
		dataSet2.setDrawVerticalHighlightIndicator(true);
		dataSet2.setDrawHorizontalHighlightIndicator(false);
		dataSet2.setHighlightLineWidth(3);
		dataSet2.setHighLightColor(Color.WHITE);
		dataSet2.setCubicIntensity(0.2f);
		dataSet2.setDrawValues(false);
		dataSet2.setLineWidth(3);
		List<ILineDataSet> dataSetList = new ArrayList<>();
//		dataSetList.add(dataSet);
		dataSetList.add(dataSet2);

		LineData lineData = new LineData(dataSet);
		LineData lineData2 = new LineData(dataSetList);
		return lineData2;
	}

}
