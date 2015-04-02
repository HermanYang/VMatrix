package com.vmatrix.adapter;

import java.util.LinkedList;

import szy.utility.NodeInfo;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vmatrix.R;
import com.vmatrix.constant.ModelId;
import com.vmatrix.manager.ModelManager;
import com.vmatrix.model.VideoListModel;
import com.vmatrix.utils.Utils;

public class VideoListAdapter extends BaseAdapter
{
	private LinkedList<NodeInfo> list;
	public VideoListAdapter(LinkedList<NodeInfo> li)
	{
		list = li;
	}
	
	public void setNodeList(LinkedList<NodeInfo> newList)
	{
		list = newList;
	}

	@Override
	public int getCount()
	{
		return list.size() - 1;
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		convertView = Utils.inflate(R.layout.activity_video_list_item, null);
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView indexTextView = (TextView) convertView.findViewById(R.id.index);
		
		String itemName = list.get(position + 1).getsNodeName();
		title.setText(itemName);
		
		indexTextView.setText(Integer.toString(position + 1));
		
		boolean isAlive = list.get(position + 1).isbAlive();
		
		if(!isAlive) {
			convertView.setBackgroundColor(0xffe3e3e3);
			title.setText(itemName + "[离线]");
		}

		return convertView;
	}

}
