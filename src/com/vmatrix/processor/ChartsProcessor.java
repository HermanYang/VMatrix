package com.vmatrix.processor;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vmatrix.R;
import com.vmatrix.constant.Command;

public class ChartsProcessor extends Processor {

	@Override
	public boolean proceed(Command command) {
		// TODO Auto-generated method stub
		switch (command) {
		case INITIALIZATION:
		{
			Log.i("ChartsProcessor", "chartsprocessor init webview");
//			ChartsLayout chartsLayout=(ChartsLayout) mActivity.findViewById(R.id.activity_charts);

			//config webview
			
			WebView webView = (WebView) mActivity.findViewById(R.id.chartView);
			webView.getSettings().setJavaScriptEnabled(true);
			
			webView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					
					view.loadUrl(url);
					return true;
				}
			});
			
			webView.loadUrl("file:///android_asset/html/index.htm");
			
			return true;
		}
		default:
			break;
	}
	return false;
	}

	@Override
	public boolean proceed(Command command, Object... params) {
		// TODO Auto-generated method stub
		return false;
	}


}
