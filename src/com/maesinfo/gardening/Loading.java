package com.maesinfo.gardening;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Loading extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loading);
	}

	public void commit(View target) {
		Intent i = new Intent(Loading.this, Main.class);
		startActivity(i);
		this.finish();
	}
}
