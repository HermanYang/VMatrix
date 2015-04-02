package com.vmatrix.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.vmatrix.listener.OnTurnOverUpdatedListener;
import com.vmatrix.qkt.QKTUrlParamHelper;
import com.vmatrix.qkt.QKTClient;

public class TurnOverModel extends Model
{

	private int							mYesterdaySale			= 0;
	private int							mTodaySale				= 0;
	private int							mYesterdayVisitorAmount	= 0;
	private int							mTodayVisitorAmount		= 0;

	private OnTurnOverUpdatedListener	mListener				= null;

	public TurnOverModel()
	{
		initialize();
	}

	private void initialize()
	{
	}

	public void updateData(OnTurnOverUpdatedListener onTurnOverUpdatedListener)
	{
		mListener = onTurnOverUpdatedListener;
		new AsynTurnOverUpdator().execute();
	}

	public int getTodaySale()
	{
		return mTodaySale;
	}

	public int getTodayVisitorAmount()
	{
		return mTodayVisitorAmount;
	}

	public int getYesterdaySale()
	{
		return mYesterdaySale;
	}

	public int getYesterdayVisitorAmount()
	{
		return mYesterdayVisitorAmount;
	}

	class AsynTurnOverUpdator extends AsyncTask<Void, Integer, String>
	{
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result)
		{
			super.onPostExecute(result);
			if (mListener != null)
			{
				mListener.updated();
			}
		}

		@Override
		protected String doInBackground(Void... params)
		{
			try
			{
				JSONObject jsonObj = QKTClient.getDefaultArea();

				if (jsonObj != null)
				{
					int areaId = 0;
					areaId = jsonObj.getInt("areaId");

					jsonObj = QKTClient.getYesterDayData(QKTUrlParamHelper.getYesterDayParams(areaId));
					if (jsonObj != null)
					{
						if (jsonObj.getString("dashboard_area") == null)
						{
							// mMessage = "Get yesterday data error";
						}

						mYesterdaySale = jsonObj.getInt("revenue");
						mYesterdayVisitorAmount = jsonObj.getInt("visitor");
					}

					jsonObj = QKTClient.getRealTimeData(QKTUrlParamHelper.getRealTimeParamsforVisitors(areaId));
					if (jsonObj != null)
					{
						JSONArray jsonArray = jsonObj.getJSONArray("series");
						if (jsonArray != null)
						{
							for (int i = 0; i < jsonArray.length(); ++i)
							{
								jsonObj = (JSONObject) jsonArray.get(i);
								if (jsonObj.getString("name").equals("进"))
								{
									jsonArray = jsonObj.getJSONArray("data");
									if (jsonArray != null)
									{
										mTodayVisitorAmount = jsonArray.getInt(0);
									}
									break;
								}
							}
						}

					}

					jsonObj = QKTClient.getRealTimeSales(QKTUrlParamHelper.getRealTimeParamsforSales(areaId));
					if (jsonObj != null)
					{
						if (jsonObj.getInt("code") == 1)
						{
							mTodaySale = jsonObj.getInt("revenue");
						}
					}

				}

			}
			catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

}
