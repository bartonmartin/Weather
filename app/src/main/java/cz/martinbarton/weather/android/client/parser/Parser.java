package cz.martinbarton.weather.android.client.parser;

import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.martinbarton.weather.android.entity.ErrorEntity;


public class Parser
{
	public static final String TAG = "parser_error_check";

	public static final String ATTR_ERROR = "error";
	public static final String ATTR_TYPE = "type";
	public static final String ATTR_MESSAGE = "message";


	public static ErrorEntity checkErrorInJson(String resultJson)
	{
		//check error
		ErrorEntity errorEntity = null;
		try
		{
			if(resultJson.length() != 0)
			{
				JSONTokener jsonObject = new JSONTokener(resultJson);
				Object object = jsonObject.nextValue();
				if(object instanceof JSONObject)
				{
					JSONObject json = (JSONObject) object;
					if(json.has(ATTR_ERROR))
					{
						JSONObject errorJson = json.getJSONObject(ATTR_ERROR);

						String type = null;
						String message = null;

						if(errorJson.has(ATTR_TYPE))
						{
							type = errorJson.getString(ATTR_TYPE);
						}
						if(errorJson.has(ATTR_MESSAGE))
						{
							message = errorJson.getString(ATTR_MESSAGE);
						}

						errorEntity = new ErrorEntity(type, message);
					}
				}
			}
		}
		catch(Exception exception)
		{
			Log.e(TAG, exception.toString());
		}
		return errorEntity;
	}


	public static String getStringFromInputStream(InputStream stream)
	{
		String result = null;
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			StringBuilder sb = new StringBuilder();
			String line = null;

			while((line = reader.readLine()) != null)
			{
				sb.append(line);
			}

			result = sb.toString();
		}
		catch(Exception e)
		{
			Log.e(TAG, e.toString());
			return null;
		}
		return result;
	}

}
