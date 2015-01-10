package cz.martinbarton.weather.android.listener;

import android.location.Location;

import cz.martinbarton.weather.android.utility.Geolocation;


public interface GeolocationListener
{
	public void onGeolocationRespond(Geolocation geolocation, Location location);
	public void onGeolocationFail(Geolocation geolocation);
}
