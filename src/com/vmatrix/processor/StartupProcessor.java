package com.vmatrix.processor;

import android.content.Intent;
import android.os.Handler;

import com.vmatrix.R;
import com.vmatrix.activity.LoginActivity;
import com.vmatrix.constant.Command;
import com.vmatrix.manager.DialogManager;
import com.vmatrix.manager.ModelManager;
import com.vmatrix.manager.ResourceManager;
import com.vmatrix.utils.AppData;
import com.vmatrix.utils.Utils;

public class StartupProcessor extends Processor
{

	@Override
	public boolean proceed(Command command)
	{
		switch (command) {
			case INITIALIZATION:
			{
				mResourceManager = ResourceManager.getInstance();
				mDialogManager = DialogManager.getInstance();
				mModelManager = ModelManager.getInstance();
				mAppData = AppData.getInstance();

				Handler handle = new Handler();
				int delayMillis = mResourceManager.getInt(R.integer.delay_duration);
				handle.postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						Utils.launchActivity(LoginActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
					}
				}, delayMillis);
				return true;
			}

			default:
				return false;
		}
	}

	@Override
	public boolean proceed(Command command, Object... params)
	{
		return false;
	}

}
