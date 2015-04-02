package com.vmatrix.szy;

import java.util.LinkedList;

import szy.utility.NodeInfo;
import szy.utility.SdkHandle2;
import szy.utility.SzyUtility;
import android.os.Handler;
import android.os.Message;
import android.text.GetChars;

import com.vmatrix.constant.SZY;
import com.vmatrix.controller.Controller;
import com.vmatrix.utils.Utils;

public class SZYClient
{
	private static SZYClient		mInstance			= null;
	private static Object			INSTANCE_LOCK		= new Object();

	private static final int		SERVER_PORT			= SZY.SERVER_PORT;
	private static final int		ADD_FIRSTLEVEL_NODE	= 11;
	private static final int		ADD_CHILD_NODE		= 12;
	private static final String[]	SERVER_IPADDRESS	= SZY.SERVER_IPADDRESS;

	private Handler					mLoginHandler		= null;
	private Handler					mVideoListHandler	= null;

	private String					sNodeId				= "";
	private String					sNodeName			= "";

	public String getsNodeId()
	{
		return sNodeId;
	}

	public String getsNodeName()
	{
		return sNodeName;
	}

	public static SZYClient getInstance()
	{
		if (mInstance == null)
		{
			synchronized (INSTANCE_LOCK)
			{
				if (mInstance == null)
				{
					mInstance = new SZYClient();
				}
			}
		}
		return mInstance;
	}

	public void setLoginHandler(Handler handler)
	{
		mLoginHandler = handler;
	}

	public void setVideoListHandler(Handler handler)
	{
		mVideoListHandler = handler;
	}

	public void login(String userName, String password)
	{
		SzyUtility.getInstance()
				.init(SERVER_IPADDRESS, SERVER_PORT, szyHandler);
		SzyUtility.getInstance().login(userName, password, getDeviceKey());
	}

	/**
	 * 在离开VideoListActivity时候调用，用于退出sdk，清除缓存
	 */
	public void release()
	{
		SzyUtility.getInstance().release();
	}

	/**
	 * 根据一级节点信息，获取该节点包含的摄像头列表,获取的列表会在VideoListModel中更新
	 * 
	 * @param node
	 */
	public void getChildrenList(NodeInfo node) throws Exception
	{
		if (node != null)
		{
			SzyUtility.getInstance().getNodeList("1", node.getsNodeId(), node.getnSxtCount(), node.getnJieDianCount());
		}
	}

	private SdkHandle2	szyHandler	= new SdkHandle2()
									{
										@SuppressWarnings("rawtypes")
										@Override
										public void nodelistCallback(String sId, int nType,
												LinkedList lNodeInfos)
										{
											Message msg = new Message();
											msg.what = ADD_CHILD_NODE;
											// nType代表节点类型，1是摄像头，0是节点
											msg.arg1 = nType;
											msg.obj = lNodeInfos;
											mVideoListHandler.sendMessage(msg);
										}

										@SuppressWarnings("rawtypes")
										@Override
										public void loginCallback(int nRet, String sMsg, String strUserid,
												String strClubname, LinkedList lNodeInfos)
										{
											switch (nRet) {
												case SzyUtility.LOGIN_SUCCESS:// 登陆成功
												{
													sNodeId = strUserid;
													sNodeName = strClubname;
													Message msg = new Message();
													msg.what = ADD_FIRSTLEVEL_NODE;

													@SuppressWarnings("unchecked")
													LinkedList<NodeInfo> result = (LinkedList<NodeInfo>) lNodeInfos;
													LinkedList<SZYNodeInfo> brieflist = new LinkedList<SZYNodeInfo>();
													if (result != null)
													{
														for (NodeInfo nodeInfo : result)
														{
															SZYNodeInfo myNodeinfo = new SZYNodeInfo(nodeInfo);
															myNodeinfo.setnLevel(1);
															brieflist.add(myNodeinfo);
														}
													}
													msg.obj = getResultList(brieflist);
													mVideoListHandler.sendMessage(msg);
													if (sMsg.length() >= 0)
													{
														Message msg2 = new Message();
														msg2.what = SzyUtility.LOGIN_SUCCESS;
														msg2.obj = sMsg;
														mLoginHandler.sendMessage(msg2);
													}

													break;
												}
												case SzyUtility.LOGIN_RET_MSG_ERROR:// 登录数据异常
												case SzyUtility.LOGIN_PASSWORD_ERROR:// 用户密码出错
												case SzyUtility.LOGIN_USERNAME_ERROR:// 用户名不存在
												case SzyUtility.LOGIN_CHILD_USER_OVERDATE:// 子账号已过期
												case SzyUtility.LOGIN_ONLINE_MAX_NUM:// 在线人数已达上限
												case SzyUtility.LOGIN_OEM_OVERDATE:// OEM已到期
												case SzyUtility.LOGIN_CONNECT_TIMEOUT:// 连接服务器超时
												case SzyUtility.LOGIN_LIST_TIMEOUT:// 获取列表超时
												case SzyUtility.LOGIN_SERVER_ERROR:// 服务器维护
												{
													if (sMsg.length() > 0)
													{
														Message msg2 = new Message();
														msg2.what = nRet;
														msg2.obj = sMsg;
														mLoginHandler.sendMessage(msg2);
													}
													else
													{
														mLoginHandler.sendEmptyMessage(nRet);
													}
													break;
												}
												default:
													break;
											}
										}
									};

	private String getDeviceKey()
	{
		String strKey = Utils.getDeviceId();
		if (strKey == null)
		{
			strKey = String.valueOf(System.currentTimeMillis());
		}
		return strKey;
	}

	/**
	 * 获取用于VideoListActivity展示的list
	 * 
	 * @param brieflist
	 * @return
	 */
	private LinkedList<SZYNodeInfo> getResultList(
			LinkedList<SZYNodeInfo> brieflist)
	{
		LinkedList<SZYNodeInfo> resultList = new LinkedList<SZYNodeInfo>();
		SZYNodeInfo treeNode = new SZYNodeInfo(this.sNodeId, this.sNodeName,
				false, "0", 0, true, true);
		resultList.add(treeNode);
		LinkedList<SZYNodeInfo> linkedListTempNodes = new LinkedList<SZYNodeInfo>();
		for (int i = 0; i < brieflist.size(); i++)
		{
			SZYNodeInfo treeNode1 = brieflist.get(i);
			if (treeNode1.getnLevel() == 1)
			{
				linkedListTempNodes.add(treeNode1);
				resultList.add(treeNode1);
			}
		}
		return resultList;
	}

}