package cz.martinbarton.weather.android.client.response;

import cz.martinbarton.weather.android.entity.WeatherEntity;


/**
 * Created by Martin on 9.1.2015.
 */
public class WeatherResponse extends Response
{
	private WeatherEntity mWeatherEntity;


	public WeatherEntity getWeatherEntity()
	{
		return mWeatherEntity;
	}


	public void setWeatherEntity(WeatherEntity mWeatherEntity)
	{
		this.mWeatherEntity = mWeatherEntity;
	}

}
