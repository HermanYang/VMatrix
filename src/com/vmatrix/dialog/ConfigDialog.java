package com.vmatrix.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.vmatrix.R;
import com.vmatrix.constant.Command;
import com.vmatrix.controller.Controller;
import com.vmatrix.utils.AppData;

public class ConfigDialog extends Dialog
{

	public ConfigDialog(Context context)
	{
		super(context);
	}

	public ConfigDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener)
	{
		super(context, cancelable, cancelListener);
	}

	public ConfigDialog(Context context, int theme)
	{
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_config);

		setOnShowListener(new OnShowListener()
		{
			@Override
			public void onShow(DialogInterface dialog)
			{
				((EditText) findViewById(R.id.ip)).setText(AppData.getInstance().getString(AppData.STRING_QKT_IP, null));
				((EditText) findViewById(R.id.port)).setText(AppData.getInstance().getString(AppData.STRING_QKT_PORT, null));
			}
		});

		findViewById(R.id.optionYes).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String ip = ((EditText) findViewById(R.id.ip)).getText().toString();
				String port = ((EditText) findViewById(R.id.port)).getText().toString();

				Controller.getInstance().proceed(Command.CONFIG_NETWORK, ip, port);

				dismiss();
			}
		});

		findViewById(R.id.optionNo).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				dismiss();
			}
		});
	}

}
