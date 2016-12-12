package com.example.test_volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
	public static final String TAG = "TEST_RESP";
    public static final long DEFAULT_MILLISECONDS = 10_000L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(new StringRequest("http://192.168.8.83:8080/testHttpGet.php?x=14&y=20", new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.i(TAG, "result:" + response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.i(TAG, "request error");
			}
		}));
	}
}
