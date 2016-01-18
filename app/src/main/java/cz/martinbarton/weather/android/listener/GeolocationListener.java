package cz.martinbarton.weather.android.listener;

import android.location.Location;

import cz.martinbarton.weather.android.utility.Geolocation;


public interface GeolocationListener
{
	void onGeolocationRespond(Geolocation geolocation, Location location);
	void onGeolocationFail(Geolocation geolocation);
}
