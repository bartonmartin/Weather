package cz.martinbarton.weather.android.entity;

/**
 * Created by Martin on 9.1.2015.
 */
public class ForecastEntity
{
	private String[] mDate;
	private String[] mHeatIndexC;
	private String[] mHeatIndexF;
	private String[] mWeatherDesc;
	private String[] mWeatherIconUrl;


	public ForecastEntity(String[] date, String[] heatIndexC, String[] heatIndexF, String[] weatherDesc, String[] weatherIconUrl)
	{
		this.mDate = date;
		this.mHeatIndexC = heatIndexC;
		this.mHeatIndexF = heatIndexF;
		this.mWeatherDesc = weatherDesc;
		this.mWeatherIconUrl = weatherIconUrl;
	}


	public ForecastEntity()
	{

	}


	public String[] getDate()
	{
		return mDate;
	}


	public void setDate(String[] date)
	{
		this.mDate = date;
	}


	public String[] getHeatIndexC()
	{
		return mHeatIndexC;
	}


	public void setHeatIndexC(String[] heatIndexC)
	{
		this.mHeatIndexC = heatIndexC;
	}


	public String[] getHeatIndexF()
	{
		return mHeatIndexF;
	}


	public void setHeatIndexF(String[] heatIndexF)
	{
		this.mHeatIndexF = heatIndexF;
	}


	public String[] getWeatherDesc()
	{
		return mWeatherDesc;
	}


	public void setWeatherDesc(String[] weatherDesc)
	{
		this.mWeatherDesc = weatherDesc;
	}


	public String[] getWeatherIconUrl()
	{
		return mWeatherIconUrl;
	}


	public void setWeatherIconUrl(String[] weatherIconUrl)
	{
		this.mWeatherIconUrl = weatherIconUrl;
	}
}
