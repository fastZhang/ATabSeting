package com.zcl.quickindexmaster;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 右边的索引view
 * @author ZL
 */
public class IndexBarView extends View {
	
	private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	private Paint paint;
	/**此view的宽度*/
	private int barViewWidth;
	/**每个字母单元格的高度*/
	private float cellHeight;

	public IndexBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public IndexBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public IndexBarView(Context context) {
		super(context);
		init();
	}

	private void init() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);// 设置抗锯齿
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		paint.setTextAlign(Align.CENTER);// 设置文本的绘制参考点是自身边框底边的中心
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		barViewWidth = getMeasuredWidth();
		cellHeight = getMeasuredHeight() * 1f / indexArr.length;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < indexArr.length; i++) {
			float x = barViewWidth / 2;
			float y = cellHeight / 2 + getTextHeight(indexArr[i]) / 2 + i
					* cellHeight;

			paint.setColor(lastIndex == i ? Color.BLACK : Color.WHITE);

			canvas.drawText(indexArr[i], x, y, paint);
		}
	}

	private int lastIndex = -1;// 记录上次的触摸字母的索引

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			float y = event.getY();
			int index = (int) (y / cellHeight);// 得到字母对应的索引
			if (lastIndex != index) {
				// 说明当前触摸字母和上一个不是同一个字母
				// Log.e("tag", indexArr[index]);
				// 对index做安全性的检查
				if (index >= 0 && index < indexArr.length) {
					if (listener != null) {
						listener.onTouchLetter(indexArr[index]);
					}
				}
			}
			lastIndex = index;
			break;
		case MotionEvent.ACTION_UP:
			// 重置lastIndex
			lastIndex = -1;
			break;
		}
		// 引起重绘
		invalidate();
		return true;
	}

	/**
	 * 获取文本的高度
	 */
	private int getTextHeight(String text) {
		// 获取文本的高度
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		return bounds.height();
	}

	private OnTouchLetterListener listener;

	public void setOnTouchLetterListener(OnTouchLetterListener listener) {
		this.listener = listener;
	}
	/**
	 * 触摸字母的监听器
	 */
	public interface OnTouchLetterListener {
		void onTouchLetter(String letter);
	}

}
