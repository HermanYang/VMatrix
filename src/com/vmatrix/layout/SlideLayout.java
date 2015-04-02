package com.vmatrix.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class SlideLayout extends LinearLayout
{
	/*
	 * @SuppressWarnings("deprecation") private GestureDetector gesture = new
	 * GestureDetector(new OnSlideListener());
	 */
	public SlideLayout(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi")
	public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
	}

	public SlideLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO Auto-generated method stub
		// return gesture.onTouchEvent(event);
		return true;
	}

}
