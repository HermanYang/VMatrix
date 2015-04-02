package com.vmatrix.view;

import java.util.LinkedList;

import szy.utility.NodeInfo;
import szy.utility.PlayListener;
import szy.utility.PlayView;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.vmatrix.R;
import com.vmatrix.constant.DialogId;
import com.vmatrix.listener.OnCameraFetchedListener;
import com.vmatrix.listener.OnPlayViewChanged;
import com.vmatrix.listener.OnSlideListener;
import com.vmatrix.manager.DialogManager;
import com.vmatrix.manager.ResourceManager;
import com.vmatrix.szy.SZYNodeInfo;
import com.vmatrix.utils.Utils;

public class SZYCameraView extends PlayView
{
	private NodeInfo				mNode			= null;
	private LinkedList<NodeInfo>	mCameraList		= null;
	@SuppressWarnings("deprecation")
	private GestureDetector			gesture			= new GestureDetector(new OnSlideListener(
															this));

	private Dialog					mLoadingDialog	= null;

	public OnPlayViewChanged getmOnPlayViewChanged()
	{
		return mOnPlayViewChanged;
	}

	public void setmOnPlayViewChanged(OnPlayViewChanged mOnPlayViewChanged)
	{
		this.mOnPlayViewChanged = mOnPlayViewChanged;
	}

	private OnPlayViewChanged	mOnPlayViewChanged	= null;

	public int getCameraMoveSpeed()
	{
		return cameraMoveSpeed;
	}

	public void setCameraMoveSpeed(int cameraMoveSpeed)
	{
		this.cameraMoveSpeed = cameraMoveSpeed;
	}

	public int getCameraZoomSpeed()
	{
		return cameraZoomSpeed;
	}

	public void setCameraZoomSpeed(int cameraZoomSpeed)
	{
		this.cameraZoomSpeed = cameraZoomSpeed;
	}

	private int	cameraMoveSpeed	= 10;

	private int	cameraZoomSpeed	= 10;

	public void setCameraList(LinkedList<NodeInfo> linkedList)
	{
		this.mCameraList = linkedList;
	}

	public SZYCameraView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		initialize(context);
	}

	public SZYCameraView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initialize(context);
	}

	public SZYCameraView(Context context)
	{
		super(context);
		initialize(context);
	}

	private void initialize(Context context)
	{
		mLoadingDialog = DialogManager.getInstance().getDialogById(
				DialogId.LOADING);

		setScaleType(false);
		setPlayListener(new PlayListener()
		{
			@Override
			public void infoCallback(int type, String sInfo)
			{
				if (mLoadingDialog.isShowing())
				{
					mLoadingDialog.dismiss();
				}
			}
		});
	}

	public void setCameraNode(NodeInfo node)
	{
		mNode = node;
	}

	public NodeInfo getCameraNode(){
		return mNode;
	}
	
	public void start()
	{
		mLoadingDialog.show();
		int playReturnCode = startPlay(mNode.getsParentId(), mNode.getsNodeId());
		if (playReturnCode != SXT_PLAY_SUCCESS)
		{

			if (mLoadingDialog.isShowing())
			{
				mLoadingDialog.dismiss();
				if (playReturnCode == SXT_AT_TREM)
					Utils.makeToast(ResourceManager.getInstance().getString(R.string.camera_out_of_date),
							ResourceManager.getInstance().getInt(R.integer.toast_duration));
				if (playReturnCode == SXT_NOT_ONLINE)
					Utils.makeToast(ResourceManager.getInstance().getString(R.string.camera_not_online),
							ResourceManager.getInstance().getInt(R.integer.toast_duration));
			}
		}
	}

	public void stop()
	{
		Stop();
		if (mLoadingDialog.isShowing())
		{
			mLoadingDialog.dismiss();
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event)
	{
		return gesture.onTouchEvent(event);
	}

	public void next()
	{
		// set mNode to next then call stop(),start()
		int currentNode = mCameraList.indexOf(mNode);
		if (currentNode < mCameraList.size() - 1)
		{
			stop();
			setCameraNode(mCameraList.get(currentNode + 1));
			if (mCameraList.indexOf(mNode) == mCameraList.size() - 1)
			{
				mOnPlayViewChanged.onEndPage();
			}
			else
			{
				mOnPlayViewChanged.onMiddlePage();
			}
			start();
		}
	}

	public void previous()
	{
		// set mNode to pre then call stop() , start()
		int currentNode = mCameraList.indexOf(mNode);
		if (currentNode > 0)
		{
			stop();
			setCameraNode(mCameraList.get(currentNode - 1));
			if (mCameraList.indexOf(mNode) == 0)
			{
				mOnPlayViewChanged.onFirstPage();
			}
			else
			{
				mOnPlayViewChanged.onMiddlePage();
			}
			start();
		}
	}

	// 控制摄像头的上下左右移动,放大缩小
	@Override
	public void startPtzUp(int nSpeed)
	{
		super.startPtzUp(nSpeed);
	}

	@Override
	public void startPtzDown(int nSpeed)
	{
		super.startPtzDown(nSpeed);
	}

	@Override
	public void startPtzLeft(int nSpeed)
	{
		super.startPtzLeft(nSpeed);
	}

	@Override
	public void startPtzRight(int nSpeed)
	{
		super.startPtzRight(nSpeed);
	}

	@Override
	public void startPtzZoomin(int nSpeed)
	{
		super.startPtzZoomin(nSpeed);
	}

	@Override
	public void startPtzZoomout(int nSpeed)
	{
		super.startPtzZoomout(nSpeed);
	}

	// 停止旋转
	@Override
	public void stopPtzRotate()
	{
		System.out.println("stopPtzRotate");
		super.stopPtzRotate();
	}

	// 停止放大或者缩小
	@Override
	public void stopPtzZoom()
	{
		super.stopPtzZoom();
	}

}
