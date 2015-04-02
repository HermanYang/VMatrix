package com.vmatrix.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.vmatrix.controller.Controller;

public class Utils
{
	public static void launchActivity(Class<?> activityClass, int flags)
	{
		Activity currentActivity = Controller.getInstance().getCurrentActivity();
		Intent intent = new Intent(currentActivity, activityClass);
		intent.setFlags(flags);
		currentActivity.startActivity(intent);
	}

	public static void makeToast(String message, int duration)
	{
		Activity currentActivity = Controller.getInstance().getCurrentActivity();
		Toast.makeText(currentActivity, message, duration).show();
	}

	public static String getDeviceId()
	{
		Activity currentActivity = Controller.getInstance().getCurrentActivity();
		return ((TelephonyManager) currentActivity.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	public static View inflate(int id, ViewGroup root)
	{
		Activity currentActivity = Controller.getInstance().getCurrentActivity();
		LayoutInflater layoutInflater = (LayoutInflater) currentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = layoutInflater.inflate(id, root);

		return view;
	}

	public static int getScreenWidth()
	{
		Activity currentActivity = Controller.getInstance().getCurrentActivity();
		WindowManager wm = (WindowManager) currentActivity.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getWidth();
	}
	
	public static int getScreenHeight(){
		Activity currentActivity = Controller.getInstance().getCurrentActivity();
		WindowManager wm = (WindowManager) currentActivity.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getHeight();
	}
}
