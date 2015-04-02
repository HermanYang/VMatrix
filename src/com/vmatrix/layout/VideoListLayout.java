package com.vmatrix.layout;

import java.util.LinkedList;

import szy.utility.NodeInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vmatrix.R;
import com.vmatrix.adapter.VideoListAdapter;
import com.vmatrix.constant.Command;
import com.vmatrix.constant.ModelId;
import com.vmatrix.controller.Controller;
import com.vmatrix.manager.ModelManager;
import com.vmatrix.model.VideoListModel;

@SuppressLint("NewApi")
public class VideoListLayout extends LinearLayout
{
	private VideoListAdapter mListAdapter = null;
	public VideoListLayout(Context context)
	{
		super(context);
		initialize(context);
	}

	public VideoListLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initialize(context);
	}

	public VideoListLayout(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		initialize(context);
	}

	public VideoListLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
		initialize(context);
	}

	private void initialize(Context context)
	{
	}
	
	public void setList(LinkedList<NodeInfo> list)
	{
		((ListView) findViewById(R.id.video_list)).setAdapter(new VideoListAdapter(list));
	}
	
	public void initializeVideoListLayout()
	{
		
		VideoListModel videoListModel = (VideoListModel) ModelManager.getInstance().getModelById(ModelId.VIDEO_LIST);
		
		((ListView) findViewById(R.id.video_list)).setAdapter(new VideoListAdapter(videoListModel.getCurrentList()));
		
		((ListView) findViewById(R.id.video_list)).setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Controller.getInstance().proceed(Command.SHOW_CONTENT, position);
			}
		});

		TextView shopName = (TextView) findViewById(R.id.shop_name);

		shopName.setText(videoListModel.getShopName());
	}
}
