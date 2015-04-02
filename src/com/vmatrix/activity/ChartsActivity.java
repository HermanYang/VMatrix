package com.vmatrix.activity;

import android.os.Bundle;

import com.vmatrix.R;
import com.vmatrix.processor.ChartsProcessor;

public class ChartsActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_charts);

		mController.setActivityProcessor(this, new ChartsProcessor());
	}
	
}
