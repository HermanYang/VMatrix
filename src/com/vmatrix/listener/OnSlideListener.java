package com.vmatrix.listener;

import com.vmatrix.view.SZYCameraView;

import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSlideListener implements OnGestureListener, OnTouchListener
{
	private SZYCameraView	szyCameraView;

	public OnSlideListener(SZYCameraView szyCameraView)
	{
		super();
		this.szyCameraView = szyCameraView;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY)
	{
		float start = e1.getX();
		float end = e2.getX();
		// call pre
		if (end - start > 30 && Math.abs(velocityX) > 0)
		{
			szyCameraView.previous();
		}
		// call next
		else if (start - end > 30 && Math.abs(velocityX) > 0)
		{
			szyCameraView.next();
		}

		return false;
	}

	@Override
	public boolean onDown(MotionEvent e)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
