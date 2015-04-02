package com.vmatrix.processor;

import android.app.Activity;

import com.vmatrix.constant.Command;
import com.vmatrix.manager.DialogManager;
import com.vmatrix.manager.ModelManager;
import com.vmatrix.manager.ResourceManager;
import com.vmatrix.utils.AppData;

public abstract class Processor
{
	static protected DialogManager		mDialogManager		= null;
	static protected ResourceManager	mResourceManager	= null;
	static protected ModelManager		mModelManager		= null;
	static protected AppData			mAppData			= null;

	protected Activity					mActivity			= null;

	public void setActivity(Activity activity)
	{
		mActivity = activity;
	}

	public abstract boolean proceed(Command command);

	public abstract boolean proceed(Command command, Object... params);

}
