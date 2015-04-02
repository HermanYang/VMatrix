package com.vmatrix.dialog;

import com.vmatrix.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class OptionDialog extends Dialog {

	public OptionDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public OptionDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public OptionDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_option);
	}
	
}
