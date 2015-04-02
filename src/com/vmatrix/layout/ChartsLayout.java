package com.vmatrix.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class ChartsLayout extends LinearLayout {

	WebView webView;
	public WebView getWebView() {
		return webView;
	}
	public void setWebView(WebView webView) {
		this.webView = webView;
	}
	public ChartsLayout(Context context) {
		super(context);
	}
	
	@SuppressLint("NewApi")
	public ChartsLayout(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@SuppressLint("NewApi")
	public ChartsLayout(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public ChartsLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

}
