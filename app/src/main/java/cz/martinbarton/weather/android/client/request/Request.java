package cz.martinbarton.weather.android.client.request;

import android.os.Bundle;

import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStream;

import cz.martinbarton.weather.android.WeatherConfig;
import cz.martinbarton.weather.android.client.response.Response;


public abstract class Request
{
	public static final String API_ENDPOINT = WeatherConfig.API_ENDPOINT_PRODUCTION;
	public static final String API_KEY = "3e5a3121206b8c27e4b57e6403e0d";
	public static final String CHARSET = "UTF-8";
	protected static final String CONTENT = "content";


	private Bundle mMetaData = null;


	public abstract String getRequestMethod();

	public abstract String getAddress();

	public abstract Response parseResponse(InputStream stream) throws IOException, JsonParseException;


	public byte[] getContent()
	{
		return null;
	}


	public String getBasicAuthUsername()
	{
		return null;
	}


	public String getBasicAuthPassword()
	{
		return null;
	}


	public boolean isMultipart()
	{
		return false;
	}


	public Bundle getMetaData()
	{
		return mMetaData;
	}


	public void setMetaData(Bundle metaData)
	{
		mMetaData = metaData;
	}
}
