package com.vmatrix.qkt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

/**
 * 
 * @author Cai Yuanfeng
 *
 */
public class QKTUrlParamHelper
{
	private static SimpleDateFormat	dayFormater		= new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat	timeFormater	= new SimpleDateFormat("HH:mm:ss");

	// 获取截至到调用时间为止的当日客流量信息所需的参数列表
	public static List<BasicNameValuePair> getRealTimeParamsforVisitors(int AreaID)
	{
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("startDate", getToday()));
		params.add(new BasicNameValuePair("endDate", getToday()));
		params.add(new BasicNameValuePair("startTime", "00:00:00"));
		params.add(new BasicNameValuePair("endTime", getEndTime()));
		params.add(new BasicNameValuePair("interval", "allday"));
		params.add(new BasicNameValuePair("areaId", String.valueOf(AreaID)));
		params.add(new BasicNameValuePair("showEntry", String.valueOf(false)));
		return params;
	}

	// 获取截至到调用时间为止的当日销售量信息所需的参数列表
	public static List<BasicNameValuePair> getRealTimeParamsforSales(int AreaID)
	{
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("startDate", getToday()));
		params.add(new BasicNameValuePair("areaId", String.valueOf(AreaID)));
		return params;
	}

	// 获取昨日情况所需的参数列表
	public static List<BasicNameValuePair> getYesterDayParams(int AreaID)
	{
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("areaId", String.valueOf(AreaID)));
		params.add(new BasicNameValuePair("startDate", getYesterDay()));
		return params;
	}

	// 获取登录所需的参数列表
	public static List<BasicNameValuePair> getLoginParams(String userName, String password)
	{
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("username", userName));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("loginType", "Ajax"));
		return params;
	}

	private static String getToday()
	{
		return dayFormater.format(new Date());
	}

	private static String getYesterDay()
	{
		return dayFormater.format(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
	}

	private static String getEndTime()
	{
		return timeFormater.format(new Date());
	}
}
