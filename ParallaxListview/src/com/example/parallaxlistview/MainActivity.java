package com.example.parallaxlistview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ParallaxListview listview;
	private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listview = (ParallaxListview) findViewById(R.id.listview);

		listview.setOverScrollMode(ListView.OVER_SCROLL_NEVER);// 永远不显示蓝色阴影

		// 添加header
		View headerView = View.inflate(this, R.layout.layout_header, null);
		ImageView imageView = (ImageView) headerView
				.findViewById(R.id.imageView);
		listview.setParallaxImageView(imageView);

		listview.addHeaderView(headerView);

		listview.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, indexArr));
	}

}
