package com.vmatrix.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import com.vmatrix.constant.Command;
import com.vmatrix.controller.Controller;

public class BasicActivity extends Activity
{
	protected Controller	mController	= null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mController = Controller.getInstance();
		mController.registerActivity(this);
		mController.markCurrentActivity(this);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		mController.markCurrentActivity(this);
		mController.proceed(Command.INITIALIZATION);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mController.unRegisterActivity(this);
		mController.proceed(Command.DESTORY);
	}

	@Override
	public void onBackPressed()
	{
		mController.proceed(Command.BACK);
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
		mController.proceed(Command.RESTART);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		mController.proceed(Command.PAUSE);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		mController.proceed(Command.STOP);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		mController.markCurrentActivity(this);
		mController.proceed(Command.RESUME);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			mController.proceed(Command.LAYOUT_PORTRAIT);
		}

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			mController.proceed(Command.LAYOUT_LANDSCAPE);
		}
	}



}
