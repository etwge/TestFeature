package com.example.user.testfeature;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

	private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(new SimpleAdapter(this, getData(), android.R.layout.simple_list_item_1, new String[]{"title"},new int[]{android.R.id.text1}));
    	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position == 0){
					startActivity(LineChartActivity.class);
				}else if(position == 1){
					startActivity(PicChartActivity.class);
				}else if(position == 2){
					startActivity(BarChartActivity.class);
				}
			}
		});
	}

	private void startActivity(Class<?> cls) {
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

	private List<? extends Map<String, ?>> getData() {
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> item1 = new LinkedHashMap<>();
		Map<String, String> item2 = new LinkedHashMap<>();
		Map<String, String> item3 = new LinkedHashMap<>();
		item1.put("title", "LineChart");
		item2.put("title", "PieChart");
		item3.put("title", "BarChart");
		list.add(item1);
		list.add(item2);
		list.add(item3);
		return list;
	}
}
