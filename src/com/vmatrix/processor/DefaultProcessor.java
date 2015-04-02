package com.vmatrix.processor;

import android.content.Intent;

import com.vmatrix.activity.SettingsActivity;
import com.vmatrix.constant.Command;
import com.vmatrix.controller.Controller;
import com.vmatrix.szy.SZYClient;
import com.vmatrix.utils.Utils;

public class DefaultProcessor extends Processor
{

	@Override
	public boolean proceed(Command command)
	{
		switch (command) {
			case SWITCH_TO_SETTINGS_ACTIVITY:
			{
				Utils.launchActivity(SettingsActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
				return true;
			}

			case BACK:
			{
				mActivity.finish();
				return true;
			}

			case DESTORY:
			{
				if (Controller.getInstance().getAliveActivityAmount() == 0)
				{
					SZYClient.getInstance().release();
				}
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
