package cz.martinbarton.weather.android.client.parser;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.martinbarton.weather.android.client.response.Response;
import cz.martinbarton.weather.android.client.response.WeatherResponse;
import cz.martinbarton.weather.android.entity.ErrorEntity;
import cz.martinbarton.weather.android.entity.ForecastEntity;
import cz.martinbarton.weather.android.entity.TodayEntity;
import cz.martinbarton.weather.android.entity.WeatherEntity;
import cz.martinbarton.weather.android.utility.Logcat;


public class WeatherParser extends Parser
{
	public static final String TAG = "weather_parser";

	private static final String ATTR_DATA = "data";
	//OBJECT
	private static final String ATTR_CURRENT_CONDITION = "current_condition";
	private static final String ATTR_HUMIDYTY = "humidity";
	private static final String ATTR_PRECIP_MM = "precipMM";
	private static final String ATTR_PRESSURE = "pressure";
	private static final String ATTR_TEMP_C = "temp_C";
	private static final String ATTR_TEMP_F = "temp_F";
	private static final String ATTR_WIND_DIR = "winddir16Point";
	private static final String ATTR_WIND_SPEED_KMPH = "windspeedKmph";
	private static final String ATTR_WIND_SPEED_MILES = "windspeedMiles";
	//OBJECT
	private static final String ATTR_NEAREST_AREA = "nearest_area";
	private static final String ATTR_AREA_NAME = "areaName";
	private static final String ATTR_COUNTRY = "country";
	//OBJECT
	private static final String ATTR_WEATHER = "weather";
	private static final String ATTR_DATE = "date";
	private static final String ATTR_HOURLY = "hourly";
	private static final String ATTR_HEAT_INDEX_C = "HeatIndexC";
	private static final String ATTR_HEAT_INDEX_F = "HeatIndexF";
	private static final String ATTR_WEATHER_DESC = "weatherDesc";
	private static final String ATTR_WEATHER_ICON_URL = "weatherIconUrl";
	private static final String ATTR_VALUE = "value";
	// UNITS
	private static final String UNIT_HUMIDYTY = "%";
	private static final String UNIT_DISTANCE_MM = " mm";
	private static final String UNIT_PRESSURE = " hPa";
	private static final String UNIT_HEAT_C = "°C";
	private static final String UNIT_HEAT_F = "°F";
	private static final String UNIT_SPEED_KM = " km/h";
	private static final String UNIT_SPEED_MPH = " mph";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String DATE_FORMAT_NEW = "EEEE";


	public static Response parse(InputStream stream)
	{
		WeatherResponse response = new WeatherResponse();

		//get string from input stream
		String result = getStringFromInputStream(stream);

		Logcat.i(TAG, result);

		//check error
		ErrorEntity errorEntity = checkErrorInJson(result);
		response.setErrorEntity(errorEntity);

		TodayEntity today = new TodayEntity();
		ForecastEntity forecast = new ForecastEntity();


		//get object if there was no error
		if(errorEntity == null)
		{
			try
			{
				JSONObject jsonObject = new JSONObject(result);

				if(jsonObject.has(ATTR_DATA))
				{
					JSONObject data = jsonObject.getJSONObject(ATTR_DATA);


					if(data.has(ATTR_CURRENT_CONDITION))
					{
						JSONArray array = data.optJSONArray(ATTR_CURRENT_CONDITION);

						if(array != null)
						{
							for(int i = 0; i < array.length(); i++)
							{
								JSONObject object = array.optJSONObject(i);

								if(object != null)
								{
									today.setHumidity(object.getString(ATTR_HUMIDYTY) + UNIT_HUMIDYTY);
									today.setPrecipMM(object.getString(ATTR_PRECIP_MM) + UNIT_DISTANCE_MM);
									today.setPressure(object.getString(ATTR_PRESSURE) + UNIT_PRESSURE);
									today.setTempC(object.getString(ATTR_TEMP_C) + UNIT_HEAT_C);
									today.setTempF(object.getString(ATTR_TEMP_F) + UNIT_HEAT_F);
									today.setWinddir16Point(object.getString(ATTR_WIND_DIR));
									today.setWindspeedKmph(object.getString(ATTR_WIND_SPEED_KMPH) + UNIT_SPEED_KM);
									today.setWindspeedMiles(object.getString(ATTR_WIND_SPEED_MILES) + UNIT_SPEED_MPH);
									today.setWeatherDesc(object.getJSONArray(ATTR_WEATHER_DESC).getJSONObject(0).getString(ATTR_VALUE));
									today.setWeatherIconUrl(object.getJSONArray(ATTR_WEATHER_ICON_URL).getJSONObject(0).getString(ATTR_VALUE));
								}
							}
						}
					}

					if(data.has(ATTR_NEAREST_AREA))
					{
						JSONArray array = data.optJSONArray(ATTR_NEAREST_AREA);

						if(array != null)
						{
							for(int i = 0; i < array.length(); i++)
							{
								JSONObject object = array.optJSONObject(i);

								if(object != null)
								{
									today.setAreaName(object.getJSONArray(ATTR_AREA_NAME).getJSONObject(0).getString(ATTR_VALUE));
									today.setCountry(object.getJSONArray(ATTR_COUNTRY).getJSONObject(0).getString(ATTR_VALUE));
								}
							}
						}
					}

					if(data.has(ATTR_WEATHER))
					{
						JSONArray array = data.optJSONArray(ATTR_WEATHER);
						if(array != null)
						{

							String[] date = new String[array.length()];
							String[] heatIndexC = new String[array.length()];
							String[] heatIndexF = new String[array.length()];
							String[] weatherDesc = new String[array.length()];
							String[] weatherIconUrl = new String[array.length()];

							for(int i = 0; i < array.length(); i++)
							{
								JSONObject object = array.optJSONObject(i);
								if(object != null)
								{


									SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
									Date d = sdf.parse(object.getString(ATTR_DATE));
									sdf = new SimpleDateFormat(DATE_FORMAT_NEW, Locale.US);
									String dayOfTheWeek = sdf.format(d);
									Logcat.e(TAG, dayOfTheWeek);

									date[i] = sdf.format(d);
									heatIndexC[i] = object.getJSONArray(ATTR_HOURLY).getJSONObject(0).getString(ATTR_HEAT_INDEX_C) + UNIT_HEAT_C;
									heatIndexF[i] = object.getJSONArray(ATTR_HOURLY).getJSONObject(0).getString(ATTR_HEAT_INDEX_F) + UNIT_HEAT_F;
									weatherDesc[i] = object.getJSONArray(ATTR_HOURLY).getJSONObject(0).getJSONArray(ATTR_WEATHER_DESC).getJSONObject(0).getString(ATTR_VALUE);
									weatherIconUrl[i] = object.getJSONArray(ATTR_HOURLY).getJSONObject(0).getJSONArray(ATTR_WEATHER_ICON_URL).getJSONObject(0).getString(ATTR_VALUE);
								}
							}

							forecast = new ForecastEntity(date, heatIndexC, heatIndexF, weatherDesc, weatherIconUrl);
						}
					}
					response.setWeatherEntity(new WeatherEntity(today, forecast));
				}
			}
			catch(JSONException exception)
			{
				Log.e(TAG, exception.toString());
				return null;
			}
			catch(ParseException e)
			{
				Log.e(TAG, e.toString());
			}
		}

		return response;
	}
}