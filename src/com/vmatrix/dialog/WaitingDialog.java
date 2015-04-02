package com.vmatrix.dialog;

import com.vmatrix.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class WaitingDialog extends Dialog
{

	public WaitingDialog(Context context)
	{
		super(context);
		initialize();
	}

	public WaitingDialog(Context context, int theme)
	{
		super(context, theme);
		initialize();
	}

	public WaitingDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener)
	{
		super(context, cancelable, cancelListener);
		initialize();
	}

	private void initialize()
	{
		setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_waiting);

		ImageView image = (ImageView) findViewById(R.id.dialog);

		AnimationDrawable animation = (AnimationDrawable) image.getDrawable();

		animation.start();
	}

}
