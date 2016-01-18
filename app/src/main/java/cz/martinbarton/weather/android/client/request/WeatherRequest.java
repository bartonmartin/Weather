package cz.martinbarton.weather.android.client.request;

import com.google.gson.JsonParseException;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import cz.martinbarton.weather.android.client.parser.WeatherParser;
import cz.martinbarton.weather.android.client.response.Response;
import cz.martinbarton.weather.android.utility.Logcat;


public class WeatherRequest extends Request
{
	private static final String REQUEST_METHOD = "GET";
	private static final String REQUEST_PATH = "free/v2/weather.ashx";
	//KEYS
	private static final String KEY_LOCATION = "q";
	private static final String KEY_FORMAT = "format";
	private static final String KEY_NUM_OF_DAYS = "num_of_days";
	private static final String KEY_INCLUDELOCATION = "includelocation";
	private static final String KEY_TP = "tp";
	private static final String KEY_API_KEY = "key";
	//VALUES
	private static final String VALUE_FORMAT = "json";
	private static final String VALUE_FORECAST = "7";
	private static final String VALUE_TP = "24";
	private static final String VALUE_INCLUDELOCATION = "yes";

	private String mLocation;


	public WeatherRequest(String location)
	{
		mLocation = location;
	}


	@Override
	public String getRequestMethod()
	{
		return REQUEST_METHOD;
	}


	@Override
	public String getAddress()
	{
		StringBuilder builder = new StringBuilder();
		List<NameValuePair> params = new LinkedList<NameValuePair>();

		// params
		params.add(new BasicNameValuePair(KEY_LOCATION, mLocation));
		params.add(new BasicNameValuePair(KEY_FORMAT, VALUE_FORMAT));
		params.add(new BasicNameValuePair(KEY_NUM_OF_DAYS, VALUE_FORECAST));
		params.add(new BasicNameValuePair(KEY_INCLUDELOCATION, VALUE_INCLUDELOCATION));
		params.add(new BasicNameValuePair(KEY_TP, VALUE_TP));
		params.add(new BasicNameValuePair(KEY_API_KEY, API_KEY));
		String paramsString = URLEncodedUtils.format(params, CHARSET);

		// url
		builder.append(API_ENDPOINT);
		builder.append(REQUEST_PATH);
		if(paramsString != null && !paramsString.equals(""))
		{
			builder.append("?");
			builder.append(paramsString);
		}

		Logcat.e("Final adress", builder.toString());
		return builder.toString();
	}


	@Override
	public Response parseResponse(InputStream stream) throws IOException, JsonParseException
	{
		return WeatherParser.parse(stream);
	}


	@Override
	public byte[] getContent()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(CONTENT);

		try
		{
			return builder.toString().getBytes(CHARSET);
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
