package com.vmatrix.manager;

import java.util.Hashtable;

import com.vmatrix.constant.ModelId;
import com.vmatrix.model.LoginModel;
import com.vmatrix.model.Model;
import com.vmatrix.model.TurnOverModel;
import com.vmatrix.model.VideoListModel;

public class ModelManager
{
	private static ModelManager			mInstance		= null;
	private static Object				INSTANCE_LOCK	= new Object();
	private Hashtable<ModelId, Model>	mModelTable		= null;

	private ModelManager()
	{
		initialize();
	}

	private void initialize()
	{
		mModelTable = new Hashtable<ModelId, Model>();

		mModelTable.put(ModelId.LOGIN, new LoginModel());
		mModelTable.put(ModelId.TURNOVER, new TurnOverModel());
		mModelTable.put(ModelId.VIDEO_LIST, new VideoListModel());
	}

	public static ModelManager getInstance()
	{
		if (mInstance == null)
		{
			synchronized (INSTANCE_LOCK)
			{
				if (mInstance == null)
				{
					mInstance = new ModelManager();
				}
			}
		}
		return mInstance;
	}

	public static void destroyInstance()
	{
		mInstance = null;
	}

	public Model getModelById(ModelId id)
	{
		return mModelTable.get(id);
	}
}
