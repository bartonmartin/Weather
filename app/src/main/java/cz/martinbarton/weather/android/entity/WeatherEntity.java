package cz.martinbarton.weather.android.entity;


public class WeatherEntity
{

	private TodayEntity mToday;
	private ForecastEntity mForecast;


	public WeatherEntity(TodayEntity today, ForecastEntity forecast)
	{
		mToday = today;
		mForecast = forecast;
	}


	public WeatherEntity()
	{

	}


	public TodayEntity getToday()
	{
		return mToday;
	}


	public ForecastEntity getForecast()
	{
		return mForecast;
	}


	public void setToday(TodayEntity today)
	{
		this.mToday = today;
	}


	public void setForecast(ForecastEntity mForecast)
	{
		this.mForecast = mForecast;
	}
}
