package com.vmatrix.processor;

import android.content.Intent;

import com.vmatrix.activity.LoginActivity;
import com.vmatrix.constant.Command;
import com.vmatrix.utils.AppData;
import com.vmatrix.utils.Utils;

public class SettingsProcessor extends Processor
{

	@Override
	public boolean proceed(Command command)
	{
		switch (command) {
			case LOGOUT:
			{
				mAppData.setString(AppData.STRING_SZY_USERNAME, null);
				mAppData.setString(AppData.STRING_SZY_PASSWORD, null);
				Utils.launchActivity(LoginActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);

				return true;
			}

			default:
				break;
		}
		return false;
	}

	@Override
	public boolean proceed(Command command, Object... params)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
