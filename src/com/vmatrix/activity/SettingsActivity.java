package com.vmatrix.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.vmatrix.R;
import com.vmatrix.constant.Command;
import com.vmatrix.processor.SettingsProcessor;

public class SettingsActivity extends BasicActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		mController.setActivityProcessor(this, new SettingsProcessor());

		initializeListeners();
	}

	private void initializeListeners()
	{
		findViewById(R.id.logout).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mController.proceed(Command.LOGOUT);
			}
		});

		findViewById(R.id.back).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mController.proceed(Command.BACK);
			}
		});
	}
}
