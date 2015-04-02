package com.vmatrix.manager;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.vmatrix.controller.Controller;

public class ResourceManager
{
	private static ResourceManager	mInstance		= null;
	private static Object			INSTANCE_LOCK	= new Object();

	private Resources				mLocalResources	= null;

	public static ResourceManager getInstance()
	{
		if (mInstance == null)
		{
			synchronized (INSTANCE_LOCK)
			{
				if (mInstance == null)
				{
					mInstance = new ResourceManager();
				}
			}
		}
		return mInstance;
	}

	public static void destroyInstance()
	{
		mInstance = null;
	}

	private ResourceManager()
	{
		initilize();
	}

	private void initilize()
	{
		Activity activity = Controller.getInstance().getCurrentActivity();
		mLocalResources = activity.getResources();
	}

	public String getString(int id)
	{
		return mLocalResources.getString(id);
	}

	public int getInt(int id)
	{
		return mLocalResources.getInteger(id);
	}

	public int getDimensionPixelSize(int id)
	{
		return mLocalResources.getDimensionPixelSize(id);
	}

	public String[] getStringArray(int id)
	{
		return mLocalResources.getStringArray(id);
	}

	public Drawable getDrawable(int id)
	{
		return mLocalResources.getDrawable(id);
	}

}
