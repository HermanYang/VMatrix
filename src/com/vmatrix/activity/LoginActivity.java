package com.vmatrix.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.vmatrix.R;
import com.vmatrix.constant.Command;
import com.vmatrix.processor.LoginProcessor;

public class LoginActivity extends BasicActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mController.setActivityProcessor(this, new LoginProcessor());
		initializeListeners();
	}

	private void initializeListeners()
	{
		findViewById(R.id.loginButton).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String username = ((EditText) findViewById(R.id.username)).getEditableText().toString();
				String password = ((EditText) findViewById(R.id.password)).getEditableText().toString();
				mController.proceed(Command.LOGIN, username, password);
			}
		});
		
		findViewById(R.id.ip_config).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mController.proceed(Command.OPEN_NETWORK_CONFIG_DIALOG);
			}
		});

	}
}
