package com.vmatrix.model;

import org.json.JSONException;
import org.json.JSONObject;

import szy.utility.SzyUtility;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;

import com.vmatrix.constant.LoginError;
import com.vmatrix.listener.OnLoginListener;
import com.vmatrix.qkt.QKTUrlParamHelper;
import com.vmatrix.qkt.QKTClient;
import com.vmatrix.szy.SZYClient;

public class LoginModel extends Model
{
	private OnLoginListener	mSZYOnLoginListener	= null;
	private OnLoginListener	mQKTOnLoginListener	= null;
	private SZYClient		mSZYClient			= null;

	public LoginModel()
	{
		mSZYClient = SZYClient.getInstance();
		mSZYClient.setLoginHandler(new SZYLoginHandler());
	}

	public void loginSZY(final String username, final String password, OnLoginListener listener)
	{
		mSZYOnLoginListener = listener;

		mSZYClient.login(username, password);
	}

	public void loginQKT(final String username, final String password, OnLoginListener listener)
	{
		mQKTOnLoginListener = listener;
		new AsynLogin().execute(username, password);
	}

	class AsynLogin extends AsyncTask<String, Integer, Void>
	{
		private boolean	mIsLogged	= false;

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			mIsLogged = false;
		}

		@Override
		protected Void doInBackground(String... params)
		{
			String username = params[0];
			String password = params[1];

			JSONObject jsonObj = QKTClient.login(QKTUrlParamHelper.getLoginParams(username, password));

			if (jsonObj != null)
			{
				try
				{
					if (jsonObj.getInt("code") == 0)
					{
						mIsLogged = true;
					}
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);
			if (mIsLogged)
			{
				if (mQKTOnLoginListener != null)
				{

					mQKTOnLoginListener.onLoginSucceed();
				}
			}
			else
			{
				if (mQKTOnLoginListener != null)
				{
					mQKTOnLoginListener.onLoginFailed(LoginError.QKT_CONNECTION_FAILURE);
				}
			}
		}
	}

	@SuppressLint("HandlerLeak")
	class SZYLoginHandler extends Handler
	{
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what) {
				case SzyUtility.LOGIN_SUCCESS:// 登陆成功
				{
					if (mSZYOnLoginListener != null)
					{
						mSZYOnLoginListener.onLoginSucceed();
					}
					break;
				}

				case SzyUtility.LOGIN_RET_MSG_ERROR:// 登录数据异常
				{
					if (mSZYOnLoginListener != null)
					{
						mSZYOnLoginListener.onLoginFailed(LoginError.RET_MSG_ERROR);
					}
					break;
				}

				case SzyUtility.LOGIN_PASSWORD_ERROR:// 用户密码出错
				{
					if (mSZYOnLoginListener != null)
					{
						mSZYOnLoginListener.onLoginFailed(LoginError.PASSWORD_ERROR);
					}
					break;
				}

				case SzyUtility.LOGIN_USERNAME_ERROR:// 用户名不存在
				{
					if (mSZYOnLoginListener != null)
					{
						mSZYOnLoginListener.onLoginFailed(LoginError.USERNAME_ERROR);
					}
					break;
				}

				case SzyUtility.LOGIN_CHILD_USER_OVERDATE:// 子账号已过期
				{

					if (mSZYOnLoginListener != null)
					{
						mSZYOnLoginListener.onLoginFailed(LoginError.ACCOUNT_OVERDUE_ERROR);
					}
					break;
				}

				case SzyUtility.LOGIN_ONLINE_MAX_NUM:// 在线人数已达上限
				{
					if (mSZYOnLoginListener != null)
					{
						mSZYOnLoginListener.onLoginFailed(LoginError.ONLINE_MAX_ERROR);
					}
					break;
				}

				case SzyUtility.LOGIN_OEM_OVERDATE:// OEM已到期
				{

					if (mSZYOnLoginListener != null)
					{
						mSZYOnLoginListener.onLoginFailed(LoginError.OEM_OVERDUE_ERROR);
					}
					break;
				}

				case SzyUtility.LOGIN_SERVER_ERROR:// 服务器维护
				{
					if (mSZYOnLoginListener != null)
					{
						mSZYOnLoginListener.onLoginFailed(LoginError.SERVER_ERROR);
					}
					break;
				}

				case SzyUtility.LOGIN_CONNECT_TIMEOUT:// 连接服务器超时
				{
					if (mSZYOnLoginListener != null)
					{
						mSZYOnLoginListener.onLoginFailed(LoginError.CONNECTION_TIMEOUT_ERROR);
					}
					break;
				}

				case SzyUtility.LOGIN_LIST_TIMEOUT:// 获取列表超时
				{
					if (mSZYOnLoginListener != null)
					{
						mSZYOnLoginListener.onLoginFailed(LoginError.VIDEOLIST_TIMEOUT_ERROR);
					}
					break;
				}

				default:
					break;
			}
		}

	}

}
