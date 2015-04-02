package com.vmatrix.listener;

import java.util.LinkedList;

import com.vmatrix.szy.SZYNodeInfo;

import szy.utility.NodeInfo;

public interface OnChildrenListResponseListener
{
	void onChildrenListReceived(boolean succeed, LinkedList<NodeInfo> obj);
}
