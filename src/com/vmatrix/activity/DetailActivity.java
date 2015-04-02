package com.vmatrix.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.vmatrix.R;
import com.vmatrix.constant.Command;
import com.vmatrix.processor.DetailProcessor;

@SuppressLint("NewApi")
public class DetailActivity extends BasicActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_play);

		mController.setActivityProcessor(this, new DetailProcessor());

		initializeListeners();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
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

		findViewById(R.id.back).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mController.proceed(Command.BACK);
			}
		});

		findViewById(R.id.switch2control).setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				Button button = (Button) v;
				mController.proceed(Command.CHARTS);
//				LinearLayout controlContainer = (LinearLayout) findViewById(R.id.controlContainer);
//				ScrollView infoDisplay = (ScrollView) findViewById(R.id.infoDisplay);
				
				/*
				{

					button.setText("返回");

					DisplayMetrics dm = new DisplayMetrics();
					getWindowManager().getDefaultDisplay().getMetrics(dm);

					controlContainer.setVisibility(View.VISIBLE);
					infoDisplay.setVisibility(View.INVISIBLE);
				}
				else if (button.getText().equals("返回"))
				{

					button.setText("报表展示");

					controlContainer.setVisibility(View.INVISIBLE);
					infoDisplay.setVisibility(View.VISIBLE);
				}*/
				
			}
		});
	}
}
