package com.zcl.quickindexmaster;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.OvershootInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.zcl.quickindexmaster.IndexBarView.OnTouchLetterListener;

public class MainActivity extends Activity {
	
	private IndexBarView quickIndexBar;
	private ListView listview;
	private TextView currentWord;

	private ArrayList<FriendBean> friends = new ArrayList<FriendBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		quickIndexBar = (IndexBarView) findViewById(R.id.quickIndexBar);
		listview = (ListView) findViewById(R.id.listview);
		currentWord = (TextView) findViewById(R.id.currentWord);

		// 1.准备数据
		fillList();
		// 2.对数据进行排序
		Collections.sort(friends);
		// 3.设置Adapter
		listview.setAdapter(new MyAdapter(this, friends));

		quickIndexBar.setOnTouchLetterListener(new OnTouchLetterListener() {
			@Override
			public void onTouchLetter(String letter) {
				// 根据当前触摸的字母，去集合中找那个item的首字母和letter一样，然后将对应的item放到屏幕顶端
				for (int i = 0; i < friends.size(); i++) {
					String firstWord = friends.get(i).getPinyin().charAt(0)+ "";
					if (letter.equals(firstWord)) {
						// 说明找到了，那么应该讲当前的item放到屏幕顶端
						listview.setSelection(i);
						break;// 只需要找到第一个就行
					}
				}

				// 显示当前触摸的字母
				showCurrentWord(letter);
			}
		});

		// 通过缩小currentWord来隐藏
		ViewHelper.setScaleX(currentWord, 0);
		ViewHelper.setScaleY(currentWord, 0);
	}

	private boolean isScale = false;
	private Handler handler = new Handler();

	protected void showCurrentWord(String letter) {
		currentWord.setText(letter);
		if (!isScale) {
			isScale = true;
			ViewPropertyAnimator.animate(currentWord).scaleX(1f)
					.setInterpolator(new OvershootInterpolator())
					.setDuration(450).start();
			ViewPropertyAnimator.animate(currentWord).scaleY(1f)
					.setInterpolator(new OvershootInterpolator())
					.setDuration(450).start();
		}

		// 先移除之前的任务
		handler.removeCallbacksAndMessages(null);

		// 延时隐藏currentWord
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				ViewPropertyAnimator.animate(currentWord).scaleX(0f)
						.setDuration(450).start();
				ViewPropertyAnimator.animate(currentWord).scaleY(0f)
						.setDuration(450).start();
				isScale = false;
			}
		}, 1500);
	}

	private void fillList() {
		// 虚拟数据
		friends.add(new FriendBean("李伟"));
		friends.add(new FriendBean("张三"));
		friends.add(new FriendBean("阿三"));
		friends.add(new FriendBean("阿四"));
		friends.add(new FriendBean("段誉"));
		friends.add(new FriendBean("段正淳"));
		friends.add(new FriendBean("张三丰"));
		friends.add(new FriendBean("陈坤"));
		friends.add(new FriendBean("林俊杰1"));
		friends.add(new FriendBean("陈坤2"));
		friends.add(new FriendBean("王二a"));
		friends.add(new FriendBean("林俊杰a"));
		friends.add(new FriendBean("张四"));
		friends.add(new FriendBean("林俊杰"));
		friends.add(new FriendBean("王二"));
		friends.add(new FriendBean("王二b"));
		friends.add(new FriendBean("赵四"));
		friends.add(new FriendBean("杨坤"));
		friends.add(new FriendBean("赵子龙"));
		friends.add(new FriendBean("杨坤1"));
		friends.add(new FriendBean("李伟1"));
		friends.add(new FriendBean("宋江"));
		friends.add(new FriendBean("宋江1"));
		friends.add(new FriendBean("李伟3"));
	}

}
