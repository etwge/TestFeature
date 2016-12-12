package com.example.user.testfeature;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;

public class MyLineChart extends LineChart {

	private Path mHighlightLinePath = new Path();

	public MyLineChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyLineChart(Context context) {
		this(context, null);
	}

	public MyLineChart(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@Override
	protected void init() {
		super.init();

		mRenderer = new LineChartRenderer(this, mAnimator, mViewPortHandler){
			@Override
			protected void drawHighlightLines(Canvas c, float x, float y, ILineScatterCandleRadarDataSet set) {
				// set color and stroke-width
				mHighlightPaint.setColor(set.getHighLightColor());
				mHighlightPaint.setStrokeWidth(set.getHighlightLineWidth());

				// draw highlighted lines (if enabled)
				mHighlightPaint.setPathEffect(set.getDashPathEffectHighlight());

				// draw vertical highlight lines
				if (set.isVerticalHighlightIndicatorEnabled()) {

					// create vertical path
					mHighlightLinePath.reset();
					mHighlightLinePath.moveTo(x, y + 5);
					mHighlightLinePath.lineTo(x, mViewPortHandler.contentBottom());

					c.drawPath(mHighlightLinePath, mHighlightPaint);
				}

				// draw horizontal highlight lines
				if (set.isHorizontalHighlightIndicatorEnabled()) {

					// create horizontal path
					mHighlightLinePath.reset();
					mHighlightLinePath.moveTo(mViewPortHandler.contentLeft(), y);
					mHighlightLinePath.lineTo(mViewPortHandler.contentRight(), y);

					c.drawPath(mHighlightLinePath, mHighlightPaint);
				}
			}
		};
	}

}
