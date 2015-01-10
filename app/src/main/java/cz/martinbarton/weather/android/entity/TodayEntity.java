package cz.martinbarton.weather.android.entity;

/**
 * Created by Martin on 9.1.2015.
 */
public class TodayEntity {

    private String mHumidity;
    private String mPrecipMM;
    private String mPressure;
    private String mTempC;
    private String mTempF;
    private String mWeatherDesc;
    private String mWeatherIconUrl;
    private String mWinddir16Point;
    private String mWindspeedKmph;
    private String mWindspeedMiles;
    private String mAreaName;
    private String mCountry;


    public TodayEntity(String humidity, String precipMM, String pressure, String tempC, String tempF, String weatherDesc, String weatherIconUrl, String winddir16Point, String windspeedKmph, String windspeedMiles, String areaName, String country) {
        this.mHumidity = humidity;
        this.mPrecipMM = precipMM;
        this.mPressure = pressure;
        this.mTempC = tempC;
        this.mTempF = tempF;
        this.mWeatherDesc = weatherDesc;
        this.mWeatherIconUrl = weatherIconUrl;
        this.mWinddir16Point = winddir16Point;
        this.mWindspeedKmph = windspeedKmph;
        this.mWindspeedMiles = windspeedMiles;
        this.mAreaName = areaName;
        this.mCountry = country;

    }

    public TodayEntity() {

    }


    public String getHumidity() {
        return mHumidity;
    }

    public String getPrecipMM() {
        return mPrecipMM;
    }

    public String getPressure() {
        return mPressure;
    }

    public String getTempC() {
        return mTempC;
    }

    public String getTempF() {
        return mTempF;
    }

    public String getWeatherDesc() {
        return mWeatherDesc;
    }

    public String getWeatherIconUrl() {
        return mWeatherIconUrl;
    }

    public String getWinddir16Point() {
        return mWinddir16Point;
    }

    public String getWindspeedKmph() {
        return mWindspeedKmph;
    }

    public String getWindspeedMiles() {
        return mWindspeedMiles;
    }

    public String getAreaName() {
        return mAreaName;
    }

    public String getCountry() {
        return mCountry;
    }


    public void setHumidity(String humidity) {
        this.mHumidity = humidity;
    }

    public void setPrecipMM(String precipMM) {
        this.mPrecipMM = precipMM;
    }

    public void setPressure(String pressure) {
        this.mPressure = pressure;
    }

    public void setTempC(String tempC) {
        this.mTempC = tempC;
    }

    public void setTempF(String tempF) {
        this.mTempF = tempF;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.mWeatherDesc = weatherDesc;
    }

    public void setWeatherIconUrl(String weatherIconUrl) {
        this.mWeatherIconUrl = weatherIconUrl;
    }

    public void setWinddir16Point(String winddir16Point) {
        this.mWinddir16Point = winddir16Point;
    }

    public void setWindspeedKmph(String windspeedKmph) {
        this.mWindspeedKmph = windspeedKmph;
    }

    public void setWindspeedMiles(String windspeedMiles) {
        this.mWindspeedMiles = windspeedMiles;
    }

    public void setAreaName(String areaName) {
        this.mAreaName = areaName;
    }

    public void setCountry(String country) {
        this.mCountry = country;
    }
}
