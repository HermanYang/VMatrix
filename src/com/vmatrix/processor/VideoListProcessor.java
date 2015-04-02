package com.vmatrix.processor;

import java.util.LinkedList;

import szy.utility.NodeInfo;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.vmatrix.R;
import com.vmatrix.activity.DetailActivity;
import com.vmatrix.activity.VideoListActivity;
import com.vmatrix.constant.Command;
import com.vmatrix.constant.DialogId;
import com.vmatrix.constant.LoginError;
import com.vmatrix.constant.ModelId;
import com.vmatrix.layout.VideoListLayout;
import com.vmatrix.listener.OnChildrenListResponseListener;
import com.vmatrix.listener.OnLoginListener;
import com.vmatrix.manager.DialogManager;
import com.vmatrix.manager.ModelManager;
import com.vmatrix.manager.ResourceManager;
import com.vmatrix.model.LoginModel;
import com.vmatrix.model.VideoListModel;
import com.vmatrix.szy.SZYNodeInfo;
import com.vmatrix.utils.AppData;
import com.vmatrix.utils.Utils;

public class VideoListProcessor extends Processor
{

	@Override
	public boolean proceed(Command command)
	{
		switch (command) {
			case INITIALIZATION:
			{
				final VideoListLayout videoListLayout = (VideoListLayout) mActivity.findViewById(R.id.activity_video_list);
				Handler handler = new Handler();
				handler.post(new Runnable()
				{
					@Override
					public void run()
					{
						videoListLayout.initializeVideoListLayout();
					}
				});

				return true;
			}
			
			case BACK:
			{
				VideoListModel videoListModel = (VideoListModel) mModelManager.getModelById(ModelId.VIDEO_LIST);
				if( videoListModel.getNodeListLevel(videoListModel.getCurrentList()) == 0)
				{
					videoListModel.setCameraList(null);
					videoListModel.setCameraNode(null);
					return false;
				}
				else
				{
					videoListModel.setCameraList(null);
					videoListModel.setCameraNode(null);
					videoListModel.setCurrentList(videoListModel.getParrentList(videoListModel.getCurrentList()));
					VideoListLayout videoListLayout = (VideoListLayout) mActivity.findViewById(R.id.activity_video_list);
					videoListLayout.setList(videoListModel.getCurrentList());
					
					TextView shopName = (TextView) mActivity.findViewById(R.id.shop_name);
					shopName.setText(videoListModel.getShopName());
					return true;
				}

			}
			default:
				return false;
		}

	}

	@Override
	public boolean proceed(Command command, Object... params)
	{
		switch (command) {
			case SHOW_CONTENT:
			{
				try
				{
					final int index = (Integer) params[0];
					
					final VideoListModel videoListModel = (VideoListModel) mModelManager.getModelById(ModelId.VIDEO_LIST);

					final Dialog dialog = mDialogManager.getDialogById(DialogId.LOADING);

					dialog.show();

					videoListModel.fetchChildrenList(index, new OnChildrenListResponseListener()
					{
						@Override
						public void onChildrenListReceived(boolean isCameraNode, LinkedList<NodeInfo> childrenList)
						{
							dialog.dismiss();

							if (isCameraNode)
							{
								if( videoListModel.getParrentList(childrenList).get(index + 1).isbAlive() )
								{
									videoListModel.setCameraList(childrenList);
									videoListModel.setCameraNode(childrenList.get(0));
									Utils.launchActivity(DetailActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
								}
								else
								{
									Utils.makeToast(mResourceManager.getString(R.string.surveillance_list_fetch_error),
											mResourceManager.getInt(R.integer.toast_duration));
									dialog.dismiss();
								}

							}
							else
							{
								if (childrenList == null)
								{
									videoListModel.setCameraList(null);
									videoListModel.setCameraNode(null);
									Utils.makeToast(mResourceManager.getString(R.string.surveillance_list_fetch_error),
											mResourceManager.getInt(R.integer.toast_duration));
								}
								else
								{
									 
									videoListModel.setCameraList(null);
									videoListModel.setCameraNode(null);
									VideoListLayout videoListLayout = (VideoListLayout) mActivity.findViewById(R.id.activity_video_list);
									videoListLayout.setList(childrenList);
									
									TextView shopName = (TextView) mActivity.findViewById(R.id.shop_name);
									shopName.setText(videoListModel.getShopName());
									
								}
								

							}
						}

					});

					return true;
				}
				catch (Exception e)
				{
					return true;
				}

			}
			


			default:
				return false;
		}

	}
}
