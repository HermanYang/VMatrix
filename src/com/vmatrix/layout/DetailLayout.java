package com.vmatrix.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vmatrix.R;
import com.vmatrix.listener.OnPlayViewChanged;
import com.vmatrix.manager.ResourceManager;
import com.vmatrix.utils.Utils;
import com.vmatrix.view.SZYCameraView;

public class DetailLayout extends LinearLayout
{
	private SZYCameraView		mCameraView			= null;
	private OnPlayViewChanged	onPlayViewChanged	= new OnPlayViewChanged()
													{
														@Override
														public void onMiddlePage()
														{
															System.out.println("middle");
															setPageIndicator_forMiddlePage();
															setCameraName(mCameraView.getCameraNode().getsNodeName());
														}

														@Override
														public void onFirstPage()
														{
															System.out.println("first");
															setPageIndicator_forFirstPage();
															setCameraName(mCameraView.getCameraNode().getsNodeName());
														}

														@Override
														public void onEndPage()
														{
															System.out.println("end");
															setPageIndicator_forEndPage();
															setCameraName(mCameraView.getCameraNode().getsNodeName());
														}
													};

	public OnPlayViewChanged getOnPlayViewChanged()
	{
		return onPlayViewChanged;
	}

	@SuppressLint("NewApi")
	public DetailLayout(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@SuppressLint("NewApi")
	public DetailLayout(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public DetailLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public DetailLayout(Context context)
	{
		super(context);
	}

	public void setCameraName(String shopName)
	{
		TextView shopNameTextView = (TextView) findViewById(R.id.shop_name);
		shopNameTextView.setText(shopName);
	}

	public void setVideoView(SZYCameraView cameraView)
	{
		LinearLayout layout = (LinearLayout) findViewById(R.id.playview);
		layout.removeAllViews();

		int width = Utils.getScreenWidth();
		int height = width * 3 / 4;
		LayoutParams params = new LayoutParams(width, height);

		layout.addView(cameraView, params);

		mCameraView = cameraView;
	}

	public void startPlay()
	{
		mCameraView.start();
	}

	public void stopPlay()
	{
		mCameraView.stop();
	}

	public SZYCameraView getCameraView()
	{
		return mCameraView;
	}

	@SuppressLint("NewApi")
	private void setPageIndicator_forFirstPage()
	{
		ImageView imageView_start = (ImageView) findViewById(R.id.nav_start);
		ImageView imageView_middle = (ImageView) findViewById(R.id.nav_middle);
		ImageView imageView_end = (ImageView) findViewById(R.id.nav_end);
		imageView_start.setBackground(ResourceManager.getInstance().getDrawable(R.drawable.nav_selected_bg));
		imageView_middle.setBackground(ResourceManager.getInstance().getDrawable(R.drawable.nav_unselected_bg));
		imageView_end.setBackground(ResourceManager.getInstance().getDrawable(R.drawable.nav_unselected_bg));
	}

	@SuppressLint("NewApi")
	private void setPageIndicator_forMiddlePage()
	{
		ImageView imageView_start = (ImageView) findViewById(R.id.nav_start);
		ImageView imageView_middle = (ImageView) findViewById(R.id.nav_middle);
		ImageView imageView_end = (ImageView) findViewById(R.id.nav_end);
		imageView_start.setBackground(ResourceManager.getInstance().getDrawable(R.drawable.nav_unselected_bg));
		imageView_middle.setBackground(ResourceManager.getInstance().getDrawable(R.drawable.nav_selected_bg));
		imageView_end.setBackground(ResourceManager.getInstance().getDrawable(R.drawable.nav_unselected_bg));
	}

	@SuppressLint("NewApi")
	private void setPageIndicator_forEndPage()
	{
		ImageView imageView_start = (ImageView) findViewById(R.id.nav_start);
		ImageView imageView_middle = (ImageView) findViewById(R.id.nav_middle);
		ImageView imageView_end = (ImageView) findViewById(R.id.nav_end);
		imageView_start.setBackground(ResourceManager.getInstance().getDrawable(R.drawable.nav_unselected_bg));
		imageView_middle.setBackground(ResourceManager.getInstance().getDrawable(R.drawable.nav_unselected_bg));
		imageView_end.setBackground(ResourceManager.getInstance().getDrawable(R.drawable.nav_selected_bg));
	}

	public void setYesterdayVisitorAmount(int amount)
	{
		TextView amountTextView = (TextView) findViewById(R.id.yesterday_visitor_amount);
		amountTextView.setText(Integer.toString(amount));
	}

	public void setTodayVisitorAmount(int amount)
	{
		TextView amountTextView = (TextView) findViewById(R.id.today_visitor_amount);
		amountTextView.setText(Integer.toString(amount));
	}

	public void setYesterdaySale(int sale)
	{
		TextView saleTextView = (TextView) findViewById(R.id.yesterday_sale);
		saleTextView.setText(Integer.toString(sale));
	}

	public void setTodaySale(int sale)
	{
		TextView saleTextView = (TextView) findViewById(R.id.today_sale);
		saleTextView.setText(Integer.toString(sale));
	}

	public void layoutForPortrait()
	{
		LinearLayout title = (LinearLayout) findViewById(R.id.play_title);
		LinearLayout infoContainer = (LinearLayout) findViewById(R.id.infoContainer);
		LinearLayout play = (LinearLayout) findViewById(R.id.playview);
		title.setVisibility(View.VISIBLE);
		infoContainer.setVisibility(View.VISIBLE);
		play.removeAllViews();
		int width = Utils.getScreenWidth();
		int height = width * 3 / 4;
		LayoutParams params = new LayoutParams(width, height);
		play.addView(mCameraView, params);
		play.setVisibility(View.VISIBLE);
	}

	public void layoutForLandscape()
	{
		LinearLayout title = (LinearLayout) findViewById(R.id.play_title);
		LinearLayout infoContainer = (LinearLayout) findViewById(R.id.infoContainer);
		LinearLayout play = (LinearLayout) findViewById(R.id.playview);
		title.setVisibility(View.GONE);
		infoContainer.setVisibility(View.GONE);
		play.removeAllViews();
		int width = Utils.getScreenWidth();
		int height = Utils.getScreenHeight();
		LayoutParams params = new LayoutParams(width, height);
		play.addView(mCameraView, params);
		play.setVisibility(View.VISIBLE);
	}

}
