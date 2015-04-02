package com.vmatrix.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;

import com.vmatrix.R;
import com.vmatrix.activity.VideoListActivity;
import com.vmatrix.constant.Command;
import com.vmatrix.constant.DialogId;
import com.vmatrix.constant.LoginError;
import com.vmatrix.constant.ModelId;
import com.vmatrix.constant.QKT;
import com.vmatrix.dialog.WaitingDialog;
import com.vmatrix.listener.OnLoginListener;
import com.vmatrix.manager.DialogManager;
import com.vmatrix.manager.ModelManager;
import com.vmatrix.model.LoginModel;
import com.vmatrix.utils.AppData;
import com.vmatrix.utils.Utils;

public class LoginProcessor extends Processor
{

	@SuppressLint("NewApi")
	@Override
	public boolean proceed(Command command)
	{
		switch (command) {
			case INITIALIZATION:
			{
				String port = mAppData.getString(AppData.STRING_QKT_PORT, null);
				String ip = mAppData.getString(AppData.STRING_QKT_IP, null);

				// 判断QKT的IP和端口是否已经配置
				if (port == null || ip == null || port.isEmpty() || ip.isEmpty())
				{

					String toast = mResourceManager
							.getString(R.string.qkt_ip_and_port_not_exist);
					Utils.makeToast(toast,
							mResourceManager.getInt(R.integer.toast_duration));

				}
				else
				{
					List<String> username_array = new ArrayList<String>(AppData
							.getInstance().getStringArray(
									AppData.STRING_SZY_USERNAME_ARRAY,
									new HashSet<String>()));

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							mActivity, android.R.layout.simple_dropdown_item_1line,
							username_array);
					AutoCompleteTextView autoText = ((AutoCompleteTextView) mActivity
							.findViewById(R.id.username));
					autoText.setAdapter(adapter);
					autoText.setThreshold(1);
					autoText.setDropDownHeight(LayoutParams.WRAP_CONTENT);

					final String username = mAppData.getString(
							AppData.STRING_SZY_USERNAME, null);
					final String password = mAppData.getString(
							AppData.STRING_SZY_PASSWORD, null);

					((EditText) mActivity.findViewById(R.id.username))
							.setText(username);
					((EditText) mActivity.findViewById(R.id.password))
							.setText(password);

					if (username != null && password != null)
					{
						((CheckBox) mActivity.findViewById(R.id.is_remember))
								.setChecked(true);

						final WaitingDialog dialog = (WaitingDialog) DialogManager
								.getInstance().getDialogById(DialogId.LOADING);

						dialog.show();

						LoginModel loginModel = (LoginModel) ModelManager
								.getInstance().getModelById(ModelId.LOGIN);

						loginModel.loginSZY(username, password,
								new OnLoginListener()
								{

									@Override
									public void onLoginSucceed()
									{
										dialog.dismiss();

										String toast = mResourceManager
												.getString(R.string.login_success);
										Utils.makeToast(toast, mResourceManager
												.getInt(R.integer.toast_duration));

										mAppData.setString(AppData.STRING_QKT_USERNAME,
												username);
										mAppData.setString(AppData.STRING_QKT_PASSWORD,
												password);

										Utils.launchActivity(VideoListActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
									}

									@Override
									public void onLoginFailed(LoginError error)
									{
										dialog.dismiss();

										String toast = "";

										switch (error) {
											case PASSWORD_ERROR:
											{
												toast = mResourceManager
														.getString(R.string.login_password_error);
												break;
											}

											case RET_MSG_ERROR:
											{
												toast = mResourceManager
														.getString(R.string.login_ret_msg_error);
												break;
											}

											case USERNAME_ERROR:
											{
												toast = mResourceManager
														.getString(R.string.login_username_error);
												break;
											}

											case ACCOUNT_OVERDUE_ERROR:
											{
												toast = mResourceManager
														.getString(R.string.login_account_overdue_error);
												break;
											}

											case ONLINE_MAX_ERROR:
											{
												toast = mResourceManager
														.getString(R.string.login_online_max_error);
												break;
											}

											case OEM_OVERDUE_ERROR:
											{
												toast = mResourceManager
														.getString(R.string.login_oem_overdue_error);
												break;
											}

											case SERVER_ERROR:
											{
												toast = mResourceManager
														.getString(R.string.login_server_error);
												break;
											}

											case CONNECTION_TIMEOUT_ERROR:
											{
												toast = mResourceManager
														.getString(R.string.login_connection_timeout_error);
												break;
											}

											case VIDEOLIST_TIMEOUT_ERROR:
											{
												toast = mResourceManager
														.getString(R.string.login_videolist_timeout_error);
												break;
											}

											default:
												break;
										}

										Utils.makeToast(toast, mResourceManager
												.getInt(R.integer.toast_duration));
									}
								});

					}

				}

				return true;
			}

			case BACK:
			{
				System.exit(0);
				return true;
			}

			case OPEN_NETWORK_CONFIG_DIALOG:
			{
				Dialog dialog = mDialogManager.getDialogById(DialogId.NETWORK_CONFIG_DIALOG);
				dialog.show();
				return true;
			}

			default:
				return false;
		}
	}

	@SuppressLint("NewApi")
	@Override
	public boolean proceed(Command command, Object... params)
	{
		switch (command) {

			case LOGIN:
			{
				String port = mAppData.getString(AppData.STRING_QKT_PORT, null);
				String ip = mAppData.getString(AppData.STRING_QKT_IP, null);

				// 判断QKT的IP和端口是否已经配置
//				if (port == null || ip == null || port.isEmpty() || ip.isEmpty())
//				{
//
//					String toast = mResourceManager
//							.getString(R.string.qkt_ip_and_port_not_exist);
//					Utils.makeToast(toast,
//							mResourceManager.getInt(R.integer.toast_duration));
//
//					return true;
//				}
				
				//TODO
				
//				else  
				{
					final WaitingDialog dialog = (WaitingDialog) DialogManager
							.getInstance().getDialogById(DialogId.LOADING);

					final String username = (String) params[0];
					final String password = (String) params[1];

					if (username.length() <= 0 || password.length() <= 0)
					{
						Utils.makeToast(mResourceManager
								.getString(R.string.login_account_empty_error),
								mResourceManager.getInt(R.integer.toast_duration));
						return true;
					}

					dialog.show();

					final boolean remember_account = ((CheckBox) mActivity
							.findViewById(R.id.is_remember)).isChecked();

					LoginModel loginModel = (LoginModel) ModelManager.getInstance()
							.getModelById(ModelId.LOGIN);
					loginModel.loginSZY(username, password, new OnLoginListener()
					{

						@Override
						public void onLoginSucceed()
						{
							dialog.dismiss();

							String toast = mResourceManager
									.getString(R.string.login_success);
							Utils.makeToast(toast, mResourceManager
									.getInt(R.integer.toast_duration));

							if (remember_account)
							{
								mAppData.setString(AppData.STRING_SZY_USERNAME,
										username);
								mAppData.setString(AppData.STRING_SZY_PASSWORD,
										password);
							}

							mAppData.setString(AppData.STRING_QKT_USERNAME,
									username);
							mAppData.setString(AppData.STRING_QKT_PASSWORD,
									password);

							Utils.launchActivity(VideoListActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);

							List<String> username_array = new ArrayList<String>(
									mAppData.getStringArray(
											AppData.STRING_SZY_USERNAME_ARRAY,
											new HashSet<String>()));
							if (username_array.indexOf(username) < 0)
							{
								username_array.add(username);
								mAppData.setStringArray(
										AppData.STRING_SZY_USERNAME_ARRAY,
										new HashSet<String>(username_array));
							}

						}

						@Override
						public void onLoginFailed(LoginError error)
						{
							dialog.dismiss();

							String toast = "";

							switch (error) {
								case PASSWORD_ERROR:
								{
									toast = mResourceManager
											.getString(R.string.login_password_error);
									break;
								}

								case RET_MSG_ERROR:
								{
									toast = mResourceManager
											.getString(R.string.login_ret_msg_error);
									break;
								}

								case USERNAME_ERROR:
								{
									toast = mResourceManager
											.getString(R.string.login_username_error);
									break;
								}

								case ACCOUNT_OVERDUE_ERROR:
								{
									toast = mResourceManager
											.getString(R.string.login_account_overdue_error);
									break;
								}

								case ONLINE_MAX_ERROR:
								{
									toast = mResourceManager
											.getString(R.string.login_online_max_error);
									break;
								}

								case OEM_OVERDUE_ERROR:
								{
									toast = mResourceManager
											.getString(R.string.login_oem_overdue_error);
									break;
								}

								case SERVER_ERROR:
								{
									toast = mResourceManager
											.getString(R.string.login_server_error);
									break;
								}

								case CONNECTION_TIMEOUT_ERROR:
								{
									toast = mResourceManager
											.getString(R.string.login_connection_timeout_error);
									break;
								}

								case VIDEOLIST_TIMEOUT_ERROR:
								{
									toast = mResourceManager
											.getString(R.string.login_videolist_timeout_error);
									break;
								}

								default:
									break;
							}

							Utils.makeToast(toast, mResourceManager
									.getInt(R.integer.toast_duration));
						}
					});

				}
				return true;
			}

			case CONFIG_NETWORK:
			{
				String ip = (String) params[0];
				String port = (String) params[1];

				mAppData.setString(AppData.STRING_QKT_IP, ip);
				mAppData.setString(AppData.STRING_QKT_PORT, port);

				return true;
			}
			default:
				return false;
		}
	}
}
