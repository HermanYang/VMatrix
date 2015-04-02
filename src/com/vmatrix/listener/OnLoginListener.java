package com.vmatrix.listener;

import com.vmatrix.constant.LoginError;

public interface OnLoginListener
{
	void onLoginSucceed();

	void onLoginFailed(LoginError error);
}
