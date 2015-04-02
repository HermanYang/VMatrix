package com.vmatrix.utils;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.vmatrix.R;

public class NavBar
{

	private View					target;
	private int						num;

	private ArrayList<ImageView>	images	= new ArrayList<ImageView>();

	public NavBar(Context context, View target, int num)
	{

		this.target = target;
		this.num = num;

		LinearLayout linearLayout = new LinearLayout(context);
		RelativeLayout.LayoutParams relativeParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 20);
		relativeParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		linearLayout.setLayoutParams(relativeParam);

		for (int i = 0; i < num; i++)
		{
			ImageView image = new ImageView(context);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(14, 14);
			params.setMargins(5, 5, 5, 5);

			image.setLayoutParams(params);
			image.setBackgroundResource(R.drawable.nav_unselected_bg);

		}

	}

}
