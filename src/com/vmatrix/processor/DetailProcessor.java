package com.vmatrix.processor;

import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vmatrix.R;
import com.vmatrix.activity.ChartsActivity;
import com.vmatrix.activity.LoginActivity;
import com.vmatrix.constant.Command;
import com.vmatrix.constant.LoginError;
import com.vmatrix.constant.ModelId;
import com.vmatrix.layout.DetailLayout;
import com.vmatrix.listener.OnLoginListener;
import com.vmatrix.listener.OnTurnOverUpdatedListener;
import com.vmatrix.manager.ModelManager;
import com.vmatrix.manager.ResourceManager;
import com.vmatrix.model.LoginModel;
import com.vmatrix.model.TurnOverModel;
import com.vmatrix.model.VideoListModel;
import com.vmatrix.utils.AppData;
import com.vmatrix.utils.Utils;
import com.vmatrix.view.SZYCameraView;

public class DetailProcessor extends Processor
{

	@Override
	public boolean proceed(Command command)
	{
		switch (command) {
			case INITIALIZATION:
			{
				final DetailLayout detailLayout = (DetailLayout) mActivity.findViewById(R.id.activity_detail);

				VideoListModel videoListModel = (VideoListModel) ModelManager.getInstance().getModelById(ModelId.VIDEO_LIST);

				detailLayout.setCameraName(videoListModel.getCameraNode().getsNodeName());

				final SZYCameraView cameraView = new SZYCameraView(mActivity);

				// set cameralist to cameraView
				cameraView.setCameraList(videoListModel.getCameraList());
				cameraView.setCameraNode(videoListModel.getCameraNode());
				
				// set playviewChangedListener
				cameraView.setmOnPlayViewChanged(detailLayout.getOnPlayViewChanged());
				detailLayout.setVideoView(cameraView);

				final TurnOverModel turnOverModel = (TurnOverModel) ModelManager.getInstance().getModelById(ModelId.TURNOVER);
				LoginModel loginModel = (LoginModel) ModelManager.getInstance().getModelById(ModelId.LOGIN);

				String username = mAppData.getString(AppData.STRING_QKT_USERNAME, "");
				String password = mAppData.getString(AppData.STRING_QKT_PASSWORD, "");

				loginModel.loginQKT(username, password, new OnLoginListener()
				{
					@Override
					public void onLoginSucceed()
					{
						turnOverModel.updateData(new OnTurnOverUpdatedListener()
						{
							@Override
							public void updated()
							{
								/*
								detailLayout.setYesterdayVisitorAmount(turnOverModel.getYesterdayVisitorAmount());
								detailLayout.setTodayVisitorAmount(turnOverModel.getTodayVisitorAmount());
								detailLayout.setYesterdaySale(turnOverModel.getYesterdaySale());
								detailLayout.setTodaySale(turnOverModel.getTodaySale());
								*/
							}
						});
					}

					@Override
					public void onLoginFailed(LoginError error)
					{
						Utils.makeToast(ResourceManager.getInstance().getString(R.string.login_qtk_connection_error),
								ResourceManager.getInstance().getInt(R.integer.toast_duration));
					}
				});

				
				// add zoom function
//				Button zoomout_btn = (Button) mActivity.findViewById(R.id.zoomout);
//				zoomout_btn.setOnTouchListener(new View.OnTouchListener()
//				{
//
//					@Override
//					public boolean onTouch(View arg0, MotionEvent arg1)
//					{
//						switch (arg1.getAction() & MotionEvent.ACTION_MASK) {
//							case MotionEvent.ACTION_DOWN:
//							{
//								cameraView.startPtzZoomin(cameraView.getCameraZoomSpeed());
//							}
//								break;
//							case MotionEvent.ACTION_UP:
//							{
//								cameraView.stopPtzZoom();
//							}
//						}
//						return true;
//					}
//				});
//
//				Button zoomin_btn = (Button) mActivity.findViewById(R.id.zoomin);
//				zoomin_btn.setOnTouchListener(new View.OnTouchListener()
//				{
//
//					@Override
//					public boolean onTouch(View arg0, MotionEvent arg1)
//					{
//						switch (arg1.getAction() & MotionEvent.ACTION_MASK) {
//							case MotionEvent.ACTION_DOWN:
//							{
//								System.out.println("in down");
//								cameraView.startPtzZoomout(cameraView.getCameraZoomSpeed());
//							}
//								break;
//							case MotionEvent.ACTION_UP:
//							{
//								System.out.println("in up");
//								cameraView.stopPtzZoom();
//							}
//						}
//						return true;
//					}
//				});

//				ControlPanelMovementDetector detector = new ControlPanelMovementDetector();
//				detector.addDetectObject(mActivity.findViewById(R.id.controlButton));
//
//				detector.setmOnControllPanelMovedListener(new OnControllPanelMovedListener()
//				{
//
//					@Override
//					public void OnButtonPressed()
//					{
//						// TODO Auto-generated method stub
//
//					}
//
//					@Override
//					public void OnButtonRelease()
//					{
//						cameraView.stopPtzRotate();
//					}
//
//					@Override
//					public void OnMoveUp()
//					{
//						cameraView.startPtzUp(cameraView.getCameraMoveSpeed());
//					}
//
//					@Override
//					public void OnMoveDown()
//					{
//						cameraView.startPtzDown(cameraView.getCameraMoveSpeed());
//					}
//
//					@Override
//					public void OnMoveLeft()
//					{
//						cameraView.startPtzLeft(cameraView.getCameraMoveSpeed());
//					}
//
//					@Override
//					public void OnMoveRight()
//					{
//						cameraView.startPtzRight(cameraView.getCameraMoveSpeed());
//
//					}
//
//				});

				return true;
			}

			case LAYOUT_PORTRAIT:
			{
				DetailLayout detailLayout = (DetailLayout) mActivity.findViewById(R.id.activity_detail);
				detailLayout.layoutForPortrait();
				return true;
			}

			case LAYOUT_LANDSCAPE:
			{
				DetailLayout detailLayout = (DetailLayout) mActivity.findViewById(R.id.activity_detail);
				detailLayout.layoutForLandscape();
				return true;
			}

			case RESUME:
			{
				((DetailLayout) mActivity.findViewById(R.id.activity_detail)).startPlay();
				return true;
			}

			case PAUSE:
			{
				((DetailLayout) mActivity.findViewById(R.id.activity_detail)).stopPlay();
				return true;
			}
			
			case BACK:
			{
				
				VideoListModel videoListModel = (VideoListModel) mModelManager.getModelById(ModelId.VIDEO_LIST);
				
				videoListModel.setCameraNode(null);
				videoListModel.setCameraList(null);
				videoListModel.setCurrentList(videoListModel.getParrentList(videoListModel.getCurrentList()));
				return false;
			}
			
			case CHARTS:{
				Log.i("DetailProcessor","command charts");
				Utils.launchActivity(ChartsActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
				return true;
			}
			default:
				return false;
		}
	}

	@Override
	public boolean proceed(Command command, Object... params)
	{
		return false;
	}

}
