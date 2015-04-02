package com.vmatrix.activity;

import android.os.Bundle;

import com.vmatrix.R;
import com.vmatrix.constant.Command;
import com.vmatrix.controller.Controller;
import com.vmatrix.processor.StartupProcessor;

public class StartupActivity extends BasicActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);

		mController.setActivityProcessor(this, new StartupProcessor());
	}
}
