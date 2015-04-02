package com.vmatrix.utils;

import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.vmatrix.controller.Controller;

public class AppData
{
	public final static String			PREFERENCECES_FILE_NAME		= "vmatrix";
	public final static String			BOOLEAN_FIRST_INSTALLED		= "first_installed";

	public final static String			STRING_SZY_USERNAME			= "szy_username";
	public final static String			STRING_SZY_PASSWORD			= "szy_password";
	public final static String			STRING_QKT_USERNAME			= "qkt_username";
	public final static String			STRING_QKT_PASSWORD			= "qkt_password";
	public final static String			STRING_SZY_USERNAME_ARRAY	= "szy_username_list";

	public final static String			STRING_QKT_IP				= "qkt_ip";
	public final static String			STRING_QKT_PORT				= "qkt_port";

	private static AppData				mInstance					= null;
	private static Object				INSTANCE_LOCK				= new Object();

	private SharedPreferences			mSharedPreferences			= null;
	private SharedPreferences.Editor	mEditor						= null;

	private AppData()
	{
		Controller controller = Controller.getInstance();
		Context context = controller.getCurrentActivity();
		mSharedPreferences = context.getSharedPreferences(PREFERENCECES_FILE_NAME, Context.MODE_PRIVATE);
		mEditor = mSharedPreferences.edit();

		initializePreferenceItems();
	}

	public static AppData getInstance()
	{
		if (mInstance == null)
		{
			synchronized (INSTANCE_LOCK)
			{
				if (mInstance == null)
				{
					mInstance = new AppData();
				}
			}
		}
		return mInstance;
	}

	public static void destoryInstance()
	{
		mInstance = null;
	}

	public void setBoolean(String key, boolean value)
	{
		mEditor.putBoolean(key, value);
		mEditor.commit();
	}

	public void setString(String key, String value)
	{
		mEditor.putString(key, value);
		mEditor.commit();
	}

	@SuppressLint("NewApi")
	public void setStringArray(String key, Set<String> values)
	{
		mEditor.putStringSet(key, values);
		mEditor.commit();
	}

	@SuppressLint("NewApi")
	public Set<String> getStringArray(String key, Set<String> defalutValues)
	{
		return mSharedPreferences.getStringSet(key, defalutValues);
	}

	public boolean getBoolean(String key, boolean defalutValue)
	{
		return mSharedPreferences.getBoolean(key, defalutValue);
	}

	public String getString(String key, String defaultValue)
	{
		return mSharedPreferences.getString(key, defaultValue);
	}

	private void initializePreferenceItems()
	{
		if (!mSharedPreferences.contains(BOOLEAN_FIRST_INSTALLED))
		{
			setBoolean(BOOLEAN_FIRST_INSTALLED, true);
		}

	}
}
