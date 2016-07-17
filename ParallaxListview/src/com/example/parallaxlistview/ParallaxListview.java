package com.example.parallaxlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

/**
 * 
 * @author ZL
 * @version 2016-7-17 ����10:53:15
 */
public class ParallaxListview extends ListView {

	public ParallaxListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ParallaxListview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ParallaxListview(Context context) {
		super(context);
	}
	private int maxHeight;
	private ImageView imageView;
	private int orignalHeight;//ImageView����ĸ߶�
	public void setParallaxImageView( final ImageView imageView){
		this.imageView = imageView;
		
		//�趨���߶�
		imageView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				
				orignalHeight = imageView.getHeight();
				Log.e("tag", "orignalHeight: "+orignalHeight);
				int drawableHeight = imageView.getDrawable().getIntrinsicHeight();//ͼƬ�ĸ߶�
				maxHeight = orignalHeight>drawableHeight?
						orignalHeight*2:drawableHeight;
			}
		});
		
	}
	
	/**
	 * ��listview������ͷ��ʱ��ִ�У����Ի�ȡ�����������ľ���ͷ���
	 * deltaX����������x����ľ���
	 * deltaY����������y����ľ���     ������ʾ������ͷ   ������ʾ�ײ���ͷ
	 * maxOverScrollX:x���������Թ����ľ���
	 * maxOverScrollY��y���������Թ����ľ���
	 * isTouchEvent: true: ����ָ�϶�����     false:��ʾfling�����Ի���;
	 */
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		
//		Log.e("tag", "deltaY: "+deltaY   +   "  isTouchEvent:"+isTouchEvent);
		if(deltaY<0 && isTouchEvent){
			//��ʾ������ͷ���������ֶ��϶���ͷ�����
			//������Ҫ���ϵ�����ImageView�ĸ߶�
			if(imageView!=null){
				int newHeight = imageView.getHeight()-deltaY/3;
				if(newHeight>maxHeight)newHeight = maxHeight;
				
				imageView.getLayoutParams().height = newHeight;
				imageView.requestLayout();//ʹImageView�Ĳ��ֲ�����Ч
			}
		}
		
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
				scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(ev.getAction()==MotionEvent.ACTION_UP){
			//��Ҫ��ImageView�ĸ߶Ȼ����ָ�������߶�
			ValueAnimator animator = ValueAnimator.ofInt(imageView.getHeight(),orignalHeight);
			animator.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animator) {
					//��ȡ������ֵ�����ø�imageview
					int animatedValue = (Integer) animator.getAnimatedValue();
					
					imageView.getLayoutParams().height = animatedValue;
					imageView.requestLayout();//ʹImageView�Ĳ��ֲ�����Ч
				}
			});
			animator.setInterpolator(new OvershootInterpolator(5));//���ԵĲ�ֵ��
			animator.setDuration(350);
			animator.start();
		}
		return super.onTouchEvent(ev);
	}
}
