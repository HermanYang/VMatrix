package com.vmatrix.detector;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.vmatrix.listener.OnControllPanelMovedListener;

@SuppressLint("NewApi")
public class ControlPanelMovementDetector implements OnTouchListener
{

	private int								width;
	private int								height;
	private int								lastX							= 0;
	private int								lastY							= 0;
	private LinearLayout.LayoutParams		initLayoutParams;
	private OnControllPanelMovedListener	mOnControllPanelMovedListener	= null;

	public void setmOnControllPanelMovedListener(OnControllPanelMovedListener listener)
	{
		mOnControllPanelMovedListener = listener;
	}

	public void addDetectObject(View view)
	{
		view.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		// TODO Auto-generated method stub

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
			{
				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();
				width = ((View) view.getParent()).getWidth();
				height = ((View) view.getParent()).getHeight();
				mOnControllPanelMovedListener.OnButtonPressed();
				System.out.println(width + "," + height);
				initLayoutParams = (LayoutParams) view.getLayoutParams();
				break;
			}

			case MotionEvent.ACTION_UP:
			{
				view.setLayoutParams(initLayoutParams);
				System.out.println("action_up");
				mOnControllPanelMovedListener.OnButtonRelease();
				break;
			}

			case MotionEvent.ACTION_MOVE:
			{
				int dx = (int) event.getRawX() - lastX;
				int dy = (int) event.getRawY() - lastY;

				int left = view.getLeft();
				int top = view.getTop();
				int right = view.getRight();
				int bottom = view.getBottom();

				if (dx > 0 && dx > Math.abs(dy))
				{
					right = right + dx;
					left = left + dx;
					
					if(Math.abs(dx) >= (width/2 - view.getWidth()/2)) {
						mOnControllPanelMovedListener.OnMoveRight();
						System.out.println("right");
					}
						
				}
				if (dx < 0 && Math.abs(dx) > Math.abs(dy))
				{
					right = right + dx;
					left = left + dx;
					
					if(Math.abs(dx) >= (width/2 - view.getWidth()/2)) {
						mOnControllPanelMovedListener.OnMoveLeft();
						System.out.println("left");
					}
						
				}
				if (dy > 0 && dy > Math.abs(dx))
				{
					top = top + dy;
					bottom = bottom + dy;
					
					if(Math.abs(dy) >= (height/2 - view.getHeight()/2)) {
						mOnControllPanelMovedListener.OnMoveDown();
						System.out.println("down");
					}
						
				}
				if (dy < 0 && Math.abs(dy) > Math.abs(dx))
				{
					top = top + dy;
					bottom = bottom + dy;
					
					if(Math.abs(dy) >= (height/2 - view.getHeight()/2)) {
						mOnControllPanelMovedListener.OnMoveUp();
						System.out.println("up");
					}
						
				}

				if (left < 0)
				{
					left = 0;
					right = left + view.getWidth();
				}
				if (top < 0)
				{
					top = 0;
					bottom = top + view.getHeight();
				}
				if (right > width)
				{
					right = width;
					left = right - view.getWidth();
				}
				if (bottom > height)
				{
					bottom = height;
					top = bottom - view.getHeight();
				}

				view.layout(left, top, right, bottom);
				break;
			}
		}

		return true;
	}

}
