package com.vmatrix.model;

import java.util.ArrayList;
import java.util.LinkedList;

import szy.utility.NodeInfo;
import android.annotation.SuppressLint;
import android.os.Handler;

import com.vmatrix.constant.SZY;
import com.vmatrix.listener.OnChildrenListResponseListener;
import com.vmatrix.szy.SZYClient;
import com.vmatrix.szy.SZYNodeInfo;

public class VideoListModel extends Model {
	private LinkedList<NodeInfo> 				mCameraList = null;
	private LinkedList<NodeInfo> 				mCurrentList = null;
	private LinkedList<LinkedList<NodeInfo>> 	mNodeLists = null;
	
	private NodeInfo mCameraNode = null;
	private OnChildrenListResponseListener mCameraListResponseListener = null;
	private SZYClient mSZYClient = null;

	public VideoListModel() {
		mSZYClient = SZYClient.getInstance();
		mSZYClient.setVideoListHandler(new SZYLogicHandler());
		mNodeLists = new LinkedList<LinkedList<NodeInfo>>();
	}

	public ArrayList<String> getVideoList() {
		ArrayList<String> videoList = new ArrayList<String>();
		for (int i = 0; i < mCurrentList.size(); ++i) {
			if (i != 0) {
				videoList.add(mCurrentList.get(i).getsNodeName());
			}
		}
		return videoList;
	}

	public String getShopName() {
		return mCurrentList.get(0).getsNodeName();
	}

	public String getNodeName(int index) {
		return mCurrentList.get(index + 1).getsNodeName();
	}

	public void fetchChildrenList(int index, OnChildrenListResponseListener listener) {
		mCameraListResponseListener = listener;
		try {
			mSZYClient.getChildrenList(mCurrentList.get(index + 1));
		} catch (Exception e) {
		}
	}

	public LinkedList<NodeInfo> getCameraList() {
		return mCameraList;
	}

	public void setCameraNode(NodeInfo cameraNode) {
		mCameraNode = cameraNode;
	}

	public NodeInfo getCameraNode() {
		return mCameraNode;
	}

	public LinkedList<NodeInfo> getCurrentList() {
		return mCurrentList;
	}

	public void setCameraList(LinkedList<NodeInfo> childrenList) {
		mCameraList = childrenList;
	}

	public void setCurrentList(LinkedList<NodeInfo> list) {
		mCurrentList = list;
	}
	
	public LinkedList<NodeInfo> getParrentList(LinkedList<NodeInfo> list)
	{
		if(mNodeLists.indexOf(list) > 0)
		{
			return mNodeLists.get(mNodeLists.indexOf(list) - 1);
		}
		else 
		{
			return mNodeLists.get(0);
		}
	}
	
	public int getNodeListLevel(LinkedList<NodeInfo> list)
	{
		return mNodeLists.indexOf(list);
	}
	
	public void clear()
	{
		mNodeLists.clear();
	}

	public void addNodeList(LinkedList<NodeInfo> list) {
		if( mNodeLists.indexOf(list) == -1)
		{
			int index = mNodeLists.indexOf(mCurrentList);
			if(index >= 0 && mNodeLists.size() >= index + 1)
			{
				mNodeLists.add(index + 1, list);
			}
			else
			{
				mNodeLists.add(list);
			}
		}
	}
	
	@SuppressLint("HandlerLeak")
	private class SZYLogicHandler extends Handler {
		@SuppressWarnings({ "unchecked", "unused" })
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SZY.ADD_FIRSTLEVEL_NODE:
				clear();
				addNodeList((LinkedList<NodeInfo>) msg.obj);
				setCurrentList((LinkedList<NodeInfo>) msg.obj);
				break;

			case SZY.ADD_CHILD_NODE:
				addNodeList((LinkedList<NodeInfo>) msg.obj);
				setCurrentList((LinkedList<NodeInfo>) msg.obj);
				switch (msg.arg1) {
				case 0: {
					mCameraListResponseListener.onChildrenListReceived(false,
							(LinkedList<NodeInfo>) msg.obj);
					break;
				}

				case 1: {
					if (mCameraListResponseListener != null) {
						if (msg != null) {
							mCameraListResponseListener.onChildrenListReceived(
									true, (LinkedList<NodeInfo>) msg.obj);
						} else {
							mCameraListResponseListener.onChildrenListReceived(
									false, (LinkedList<NodeInfo>) msg.obj);
						}
					}
					break;
				}
				}

				break;

			default:
				break;
			}
		}

	}

}
