package com.vmatrix.manager;

import android.app.Dialog;

import com.vmatrix.R;
import com.vmatrix.constant.DialogId;
import com.vmatrix.controller.Controller;
import com.vmatrix.dialog.ConfigDialog;
import com.vmatrix.dialog.OptionDialog;
import com.vmatrix.dialog.WaitingDialog;

public class DialogManager
{

	private static DialogManager	mInstance		= null;
	private static Object			INSTANCE_LOCK	= new Object();

	public static DialogManager getInstance()
	{
		if (mInstance == null)
		{
			synchronized (INSTANCE_LOCK)
			{
				if (mInstance == null)
				{
					mInstance = new DialogManager();
				}
			}
		}
		return mInstance;
	}

	public static void destroyInstance()
	{
		mInstance = null;
	}

	public Dialog getDialogById(DialogId id)
	{
		switch (id) {
			case LOADING:
				return new WaitingDialog(Controller.getInstance().getCurrentActivity(), R.style.WaitingDialog);

			case OPTION:
				return new OptionDialog(Controller.getInstance().getCurrentActivity(), R.style.WaitingDialog);

			case NETWORK_CONFIG_DIALOG:
				return new ConfigDialog(Controller.getInstance().getCurrentActivity(), R.style.WaitingDialog);

			default:
				break;
		}
		return null;
	}
}
