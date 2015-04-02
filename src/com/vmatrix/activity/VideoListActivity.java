package com.vmatrix.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.vmatrix.R;
import com.vmatrix.constant.Command;
import com.vmatrix.controller.Controller;
import com.vmatrix.processor.StartupProcessor;
import com.vmatrix.processor.VideoListProcessor;

public class VideoListActivity extends BasicActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_list);

		mController.setActivityProcessor(this, new VideoListProcessor());

		initializeListeners();
	}

	private void initializeListeners()
	{
		findViewById(R.id.settings).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mController.proceed(Command.SWITCH_TO_SETTINGS_ACTIVITY);
			}
		});

	}
}
