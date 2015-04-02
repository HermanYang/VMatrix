package com.vmatrix.qkt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import android.util.Log;

import com.vmatrix.constant.QKT;
import com.vmatrix.utils.AppData;

public class QKTClient
{
	private static int					SOCKET_TIMEOUT		= 10000;
	private static int					CONNECTION_TIMEOUT	= 10000;
	private static DefaultHttpClient	client				= null;
	private static final String			USER_AGENT			= QKT.USER_AGENT;

	private static String				JESSIONID			= "";
	private static String				ALLCOUNT			= "";

	static
	{
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMEOUT);
		client = new DefaultHttpClient(httpParams);
	}

	public static DefaultHttpClient getClient()
	{
		return client;
	}

	public static String getJESSIONID()
	{
		return JESSIONID;
	}

	public static void setJESSIONID(String jESSIONID)
	{
		JESSIONID = jESSIONID;
	}

	public static String getALLCOUNT()
	{
		return ALLCOUNT;
	}

	public static void setALLCOUNT(String aLLCOUNT)
	{
		ALLCOUNT = "\"" + aLLCOUNT + "\"";
	}

	public static JSONObject getYesterDayData(List<BasicNameValuePair> params)
	{
		return get(QKT.YESTERDAY_DATA, params);
	}

	public static JSONObject getRealTimeData(List<BasicNameValuePair> params)
	{
		return get(QKT.REALTIME_DATA, params);
	}

	public static JSONObject getDefaultArea()
	{
		return get(QKT.DEFAULT_AREA, null);
	}

	public static JSONObject getRealTimeSales(List<BasicNameValuePair> params)
	{
		return get(QKT.SALESDATA, params);
	}

	private static String getBaseUrl()
	{
		AppData appdata = AppData.getInstance();
		String port = appdata.getString(AppData.STRING_QKT_PORT, null);
		String ip = appdata.getString(AppData.STRING_QKT_IP, null);

		if (port != null && ip != null)
		{
			return "http://" + ip + ":" + port + "/datawise/";
		}

		return "";
	}

	private static JSONObject get(String url, List<BasicNameValuePair> params)
	{
		HttpGet method;
		if (params != null)
		{
			String param = URLEncodedUtils.format(params, "UTF-8");
			method = new HttpGet(getBaseUrl() + url + "?" + param);
		}
		else
		{
			method = new HttpGet(getBaseUrl() + url);
		}
		method.addHeader("User-Agent", USER_AGENT);
		JSONObject object = null;
		try
		{
			method.setHeader("Cookie", "JSESSIONID=" + getJESSIONID() + ";" + "allcount=" + getALLCOUNT());
			HttpResponse response = client.execute(method);
			HttpEntity entity = response.getEntity();
			InputStream io = entity.getContent();
			String result = "";
			String line = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(io));
			while ((line = reader.readLine()) != null)
			{
				result += line;
			}
			object = new JSONObject(result);
			return object;
		}
		catch (Exception e)
		{
			Log.i("GetClient", e.toString());
			return object;
		}
	}

	public static JSONObject login(List<BasicNameValuePair> params)
	{
		HttpGet method;
		String param = URLEncodedUtils.format(params, "UTF-8");
		method = new HttpGet(getBaseUrl() + QKT.LOGIN + "?" + param);
		method.addHeader("User-Agent", USER_AGENT);
		JSONObject object = null;
		try
		{
			HttpResponse response = client.execute(method);
			List<Cookie> cookies = ((AbstractHttpClient) client).getCookieStore().getCookies();
			HttpEntity entity = response.getEntity();
			InputStream io = entity.getContent();
			String result = "";
			String line = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(io));
			while ((line = reader.readLine()) != null)
			{
				result += line;
			}
			object = new JSONObject(result);
			reader.close();
			// login successfully
			if (object.getInt("code") == 0)
			{
				// save cookies
				for (Cookie cookie : cookies)
				{
					if (cookie.getName().equals("allcount"))
					{
						setALLCOUNT(cookie.getValue());
					}
					else if (cookie.getName().equals("JSESSIONID"))
					{
						setJESSIONID(cookie.getValue());
					}
				}
			}
			return object;
		}
		catch (Exception e)
		{
			Log.i("exception", e.getMessage());
			return null;
		}
	}
}
