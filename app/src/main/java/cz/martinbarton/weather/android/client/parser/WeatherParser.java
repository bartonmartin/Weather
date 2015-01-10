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

public class WeatherParser extends Parser {

    public static final String TAG = "weather_parser";

    public static final String ATTR_DATA = "data";
    //OBJECT
    public static final String ATTR_CURRENT_CONDITION = "current_condition";
    public static final String ATTR_HUMIDYTY = "humidity";
    public static final String ATTR_PRECIP_MM = "precipMM";
    public static final String ATTR_PRESSURE = "pressure";
    public static final String ATTR_TEMP_C = "temp_C";
    public static final String ATTR_TEMP_F = "temp_F";
    public static final String ATTR_WIND_DIR = "winddir16Point";
    public static final String ATTR_WIND_SPEED_KMPH = "windspeedKmph";
    public static final String ATTR_WIND_SPEED_MILES = "windspeedMiles";
    //OBJECT
    public static final String ATTR_NEAREST_AREA = "nearest_area";
    public static final String ATTR_AREA_NAME = "areaName";
    public static final String ATTR_COUNTRY = "country";
    //OBJECT
    public static final String ATTR_WEATHER = "weather";
    public static final String ATTR_DATE = "date";
    public static final String ATTR_HOURLY = "hourly";
    public static final String ATTR_HEAT_INDEX_C = "HeatIndexC";
    public static final String ATTR_HEAT_INDEX_F = "HeatIndexF";
    public static final String ATTR_WEATHER_DESC = "weatherDesc";
    public static final String ATTR_WEATHER_ICON_URL = "weatherIconUrl";
    public static final String ATTR_VALUE = "value";


    public static Response parse(InputStream stream) {
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
        if (errorEntity == null) {
            try {
                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.has(ATTR_DATA)) {
                    JSONObject data = jsonObject.getJSONObject(ATTR_DATA);


                    if (data.has(ATTR_CURRENT_CONDITION)) {
                        JSONArray array = data.optJSONArray(ATTR_CURRENT_CONDITION);

                        if (array != null) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.optJSONObject(i);

                                if (object != null) {
                                    today.setHumidity(object.getString(ATTR_HUMIDYTY) + "%");
                                    today.setPrecipMM(object.getString(ATTR_PRECIP_MM) + " mm");
                                    today.setPressure(object.getString(ATTR_PRESSURE) + " hPa");
                                    today.setTempC(object.getString(ATTR_TEMP_C) + "°C");
                                    today.setTempF(object.getString(ATTR_TEMP_F) + "°F");
                                    today.setWinddir16Point(object.getString(ATTR_WIND_DIR));
                                    today.setWindspeedKmph(object.getString(ATTR_WIND_SPEED_KMPH) + " km/h");
                                    today.setWindspeedMiles(object.getString(ATTR_WIND_SPEED_MILES) + " mph");
                                    today.setWeatherDesc(object.getJSONArray(ATTR_WEATHER_DESC).getJSONObject(0).getString(ATTR_VALUE));
                                    today.setWeatherIconUrl(object.getJSONArray(ATTR_WEATHER_ICON_URL).getJSONObject(0).getString(ATTR_VALUE));
                                }
                            }
                        }
                    }

                    if (data.has(ATTR_NEAREST_AREA)) {
                        JSONArray array = data.optJSONArray(ATTR_NEAREST_AREA);

                        if (array != null) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.optJSONObject(i);

                                if (object != null) {
                                    today.setAreaName(object.getJSONArray(ATTR_AREA_NAME).getJSONObject(0).getString(ATTR_VALUE));
                                    today.setCountry(object.getJSONArray(ATTR_COUNTRY).getJSONObject(0).getString(ATTR_VALUE));
                                }
                            }
                        }
                    }

                    if (data.has(ATTR_WEATHER)) {
                        JSONArray array = data.optJSONArray(ATTR_WEATHER);
                        if (array != null) {

                            String[] date = new String[array.length()];
                            String[] heatIndexC = new String[array.length()];
                            String[] heatIndexF = new String[array.length()];
                            String[] weatherDesc = new String[array.length()];
                            String[] weatherIconUrl = new String[array.length()];

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.optJSONObject(i);
                                if (object != null) {


                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                                    Date d = sdf.parse(object.getString(ATTR_DATE));
                                    sdf = new SimpleDateFormat("EEEE", Locale.US);
                                    String dayOfTheWeek = sdf.format(d);
                                    Logcat.e(TAG, dayOfTheWeek);

                                    date[i] = sdf.format(d);
                                    heatIndexC[i] = object.getJSONArray(ATTR_HOURLY).getJSONObject(0).getString(ATTR_HEAT_INDEX_C) + "°C";
                                    heatIndexF[i] = object.getJSONArray(ATTR_HOURLY).getJSONObject(0).getString(ATTR_HEAT_INDEX_F) + "°F";
                                    weatherDesc[i] = object.getJSONArray(ATTR_HOURLY).getJSONObject(0).getJSONArray(ATTR_WEATHER_DESC).getJSONObject(0).getString(ATTR_VALUE);
                                    weatherIconUrl[i] = object.getJSONArray(ATTR_HOURLY).getJSONObject(0).getJSONArray(ATTR_WEATHER_ICON_URL).getJSONObject(0).getString(ATTR_VALUE);
                                }
                            }

                            forecast = new ForecastEntity(date, heatIndexC, heatIndexF, weatherDesc, weatherIconUrl);
                        }
                    }
                    response.setWeatherEntity(new WeatherEntity(today, forecast));
                }
            } catch (JSONException exception) {
                Log.e(TAG, exception.toString());
                return null;
            } catch (ParseException e) {
                Log.e(TAG, e.toString());
            }
        }

        return response;
    }
}