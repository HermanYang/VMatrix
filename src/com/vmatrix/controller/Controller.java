package com.vmatrix.controller;

import java.util.Hashtable;

import android.app.Activity;

import com.vmatrix.constant.Command;
import com.vmatrix.processor.DefaultProcessor;
import com.vmatrix.processor.Processor;

public class Controller extends Processor
{
	private static Controller				mInstance			= null;
	private static Object					INSTANCE_LOCK		= new Object();
	private Hashtable<Activity, Processor>	mProcessorTables	= null;
	private Activity						mCurrentActivity	= null;
	private Processor						mDefaultProcessor	= null;
	private Hashtable<Activity, Boolean>	mActivityTables		= null;

	private Controller()
	{
		initialize();
	}

	private void initialize()
	{
		mProcessorTables = new Hashtable<Activity, Processor>();
		mActivityTables = new Hashtable<Activity, Boolean>();
		mDefaultProcessor = new DefaultProcessor();
	}

	public static Controller getInstance()
	{
		if (mInstance == null)
		{
			synchronized (INSTANCE_LOCK)
			{
				if (mInstance == null)
				{
					mInstance = new Controller();
				}
			}
		}
		return mInstance;
	}

	public static void destroyInstance()
	{
		mInstance = null;
	}

	public void markCurrentActivity(Activity activity)
	{
		mCurrentActivity = activity;
	}

	public void registerActivity(Activity activity)
	{
		if (mActivityTables.get(activity) == null)
		{
			mActivityTables.put(activity, true);
		}
	}

	public void unRegisterActivity(Activity activity)
	{
		if (mActivityTables.get(activity) != null)
		{
			mActivityTables.remove(activity);
		}
	}

	public int getAliveActivityAmount()
	{
		return mActivityTables.size();
	}

	public void setActivityProcessor(Activity activity, Processor processor)
	{
		mProcessorTables.put(activity, processor);
	}

	@Override
	public boolean proceed(Command command)
	{
		Processor processor = mProcessorTables.get(mCurrentActivity);
		if (processor != null)
		{
			processor.setActivity(mCurrentActivity);
			if (processor.proceed(command) == false)
			{
				mDefaultProcessor.setActivity(mCurrentActivity);
				return mDefaultProcessor.proceed(command);
			}
		}

		return false;
	}

	@Override
	public boolean proceed(Command command, Object... params)
	{
		Processor processor = mProcessorTables.get(mCurrentActivity);
		if (processor != null)
		{
			processor.setActivity(mCurrentActivity);
			if (processor.proceed(command, params) == false)
			{
				return mDefaultProcessor.proceed(command, params);
			}
		}

		return false;
	}

	public Activity getCurrentActivity()
	{
		return mCurrentActivity;
	}

}
