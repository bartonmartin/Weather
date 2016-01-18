package cz.martinbarton.weather.android.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;

import cz.martinbarton.weather.android.R;
import cz.martinbarton.weather.android.activity.MainActivity;
import cz.martinbarton.weather.android.client.APICallListener;
import cz.martinbarton.weather.android.client.APICallManager;
import cz.martinbarton.weather.android.client.APICallTask;
import cz.martinbarton.weather.android.client.ResponseStatus;
import cz.martinbarton.weather.android.client.request.WeatherRequest;
import cz.martinbarton.weather.android.client.response.Response;
import cz.martinbarton.weather.android.client.response.WeatherResponse;
import cz.martinbarton.weather.android.entity.TodayEntity;
import cz.martinbarton.weather.android.listener.GeolocationListener;
import cz.martinbarton.weather.android.listener.OnLoadDataListener;
import cz.martinbarton.weather.android.task.TaskFragment;
import cz.martinbarton.weather.android.utility.Geolocation;
import cz.martinbarton.weather.android.utility.Logcat;
import cz.martinbarton.weather.android.utility.NetworkManager;


/**
 * Created by Martin on 8.1.2015.
 */
public class TodayFragment extends TaskFragment implements OnLoadDataListener, GeolocationListener, APICallListener
{
	public static final String TAG = TodayFragment.class.getSimpleName();

	private TodayEntity mToday;
	private View mRootView;
	private Geolocation mGeolocation = null;
	private Location mLocation = null;
	private APICallManager mAPICallManager = new APICallManager();
	private SharedPreferences mSharedPreferences;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
		setRetainInstance(true);

		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_today, container, false);
		return mRootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// start geolocation
		if(mLocation == null)
		{
			mGeolocation = null;
			mGeolocation = new Geolocation((LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE), this);
		}

		loadData();
	}


	@Override
	public void onPause()
	{
		super.onPause();

		// stop geolocation
		if(mGeolocation != null) mGeolocation.stop();
	}


	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		mRootView = null;
	}


	@Override
	public void onDestroy()
	{
		super.onDestroy();

		// cancel async tasks
		mAPICallManager.cancelAllTasks();
	}


	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		// save current instance state
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}


	@Override
	public void onLoadData()
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView == null) return; // view was destroyed

				// hide progress and render view
				if(mToday != null)
				{
					renderView();
					showContent();
				}
				else showError();
			}
		});
	}


	private void handleFail(Exception exception)
	{
		int messageId;
		if(exception != null && exception.getClass().equals(UnknownHostException.class))
			messageId = R.string.global_apicall_unknown_host_toast;
		else if(exception != null && exception.getClass().equals(FileNotFoundException.class))
			messageId = R.string.global_apicall_not_found_toast;
		else if(exception != null && exception.getClass().equals(SocketTimeoutException.class))
			messageId = R.string.global_apicall_timeout_toast;
		else if(exception != null && exception.getClass().equals(JsonParseException.class))
			messageId = R.string.global_apicall_parse_fail_toast;
		else if(exception != null && exception.getClass().equals(ParseException.class))
			messageId = R.string.global_apicall_parse_fail_toast;
		else if(exception != null && exception.getClass().equals(NumberFormatException.class))
			messageId = R.string.global_apicall_parse_fail_toast;
		else if(exception != null && exception.getClass().equals(ClassCastException.class))
			messageId = R.string.global_apicall_parse_fail_toast;
		else messageId = R.string.global_apicall_fail_toast;
		Toast.makeText(getActivity(), messageId, Toast.LENGTH_LONG).show();
	}


	private void loadData()
	{
		if(NetworkManager.isOnline(getActivity()))
		{
			if(!mAPICallManager.hasRunningTask(WeatherRequest.class))
			{
				// show progress
				showProgress();

				if(mLocation == null)
				{
					mGeolocation = null;
					mGeolocation = new Geolocation((LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE), this);
				}
				else
				{
					String location = mLocation.getLatitude() + "," + mLocation.getLongitude();

					// execute request
					WeatherRequest request = new WeatherRequest(location);
					mAPICallManager.executeTask(request, this);
				}
			}
		}
		else
		{
			showOffline();
		}
	}


	private void showContent()
	{
		// show content container
		mRootView.findViewById(R.id.fragment_today_container_content).setVisibility(View.VISIBLE);
		mRootView.findViewById(R.id.fragment_today_container_offline).setVisibility(View.GONE);
		mRootView.findViewById(R.id.fragment_today_container_error).setVisibility(View.GONE);
		mRootView.findViewById(R.id.fragment_today_container_progress).setVisibility(View.GONE);
	}


	private void showProgress()
	{
		// show progress container
		mRootView.findViewById(R.id.fragment_today_container_content).setVisibility(View.GONE);
		mRootView.findViewById(R.id.fragment_today_container_offline).setVisibility(View.GONE);
		mRootView.findViewById(R.id.fragment_today_container_error).setVisibility(View.GONE);
		mRootView.findViewById(R.id.fragment_today_container_progress).setVisibility(View.VISIBLE);
	}


	private void showOffline()
	{
		// show offline container
		mRootView.findViewById(R.id.fragment_today_container_content).setVisibility(View.GONE);
		mRootView.findViewById(R.id.fragment_today_container_offline).setVisibility(View.VISIBLE);
		mRootView.findViewById(R.id.fragment_today_container_error).setVisibility(View.GONE);
		mRootView.findViewById(R.id.fragment_today_container_progress).setVisibility(View.GONE);
	}


	private void showError()
	{
		// show empty container
		mRootView.findViewById(R.id.fragment_today_container_content).setVisibility(View.GONE);
		mRootView.findViewById(R.id.fragment_today_container_offline).setVisibility(View.GONE);
		mRootView.findViewById(R.id.fragment_today_container_error).setVisibility(View.VISIBLE);
		mRootView.findViewById(R.id.fragment_today_container_progress).setVisibility(View.GONE);
	}


	private void renderView()
	{

		String preferencesLength = mSharedPreferences.getString(MainActivity.KEY_UNITS_LENGHT, MainActivity.UNITS_LENGHT_M);
		String preferencesTemperature = mSharedPreferences.getString(MainActivity.KEY_UNITS_TEMPERATURE, MainActivity.UNITS_TEMPERATURE_C);

		String temperature = new String();
		String length = new String();

		if(preferencesLength.equals(MainActivity.UNITS_LENGHT_M))
		{
			length = mToday.getWindspeedKmph();
		}
		else
		{
			length = mToday.getWindspeedMiles();
		}

		if(preferencesTemperature.equals(MainActivity.UNITS_TEMPERATURE_C))
		{
			temperature = mToday.getTempC();
		}
		else
		{
			temperature = mToday.getTempF();
		}

		Logcat.i(TAG, "preferencesLength " + preferencesLength);
		Logcat.i(TAG, "preferencesTemperature " + preferencesTemperature);

		((TextView) mRootView.findViewById(R.id.fragment_today_city_state)).setText(mToday.getAreaName() + ", " + mToday.getCountry());
		((TextView) mRootView.findViewById(R.id.fragment_today_weather)).setText(temperature + " | " + mToday.getWeatherDesc());
		((TextView) mRootView.findViewById(R.id.fragment_today_humidity_text)).setText(mToday.getHumidity());
		((TextView) mRootView.findViewById(R.id.fragment_today_precipitation_text)).setText(mToday.getPrecipMM());
		((TextView) mRootView.findViewById(R.id.fragment_today_pressure_text)).setText(mToday.getPressure());
		((TextView) mRootView.findViewById(R.id.fragment_today_wind_speed_text)).setText(length);
		((TextView) mRootView.findViewById(R.id.fragment_today_wind_dir_text)).setText(mToday.getWinddir16Point());

		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(mToday.getWeatherIconUrl(), ((ImageView) mRootView.findViewById(R.id.fragment_today_image)));
	}


	@Override
	public void onAPICallRespond(final APICallTask task, final ResponseStatus status, final Response response)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView == null) return; // view was destroyed

				if(task.getRequest().getClass().equals(WeatherRequest.class))
				{
					WeatherResponse weatherResponse = (WeatherResponse) response;

					// error
					if(weatherResponse.isError())
					{
						Logcat.d(TAG + "onAPICallRespond(ExampleRequest): " + status.getStatusCode() + " " + status.getStatusMessage() +
								" / error / " + weatherResponse.getErrorType() + " / " + weatherResponse.getErrorMessage());

						showError();
					}

					// response
					else
					{
						Logcat.d(TAG + "onAPICallRespond(ExampleRequest): " + status.getStatusCode() + " " + status.getStatusMessage());

						mToday = weatherResponse.getWeatherEntity().getToday();

						// render view
						renderView();
						showContent();
					}
				}

				// finish request
				mAPICallManager.finishTask(task);
			}
		});
	}


	@Override
	public void onAPICallFail(final APICallTask task, final ResponseStatus status, final Exception exception)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView == null) return; // view was destroyed

				if(task.getRequest().getClass().equals(WeatherRequest.class))
				{
					Logcat.d(TAG + "onAPICallFail(ExampleRequest): " + status.getStatusCode() + " " + status.getStatusMessage() +
							" / " + exception.getClass().getSimpleName() + " / " + exception.getMessage());

					showError();
					handleFail(exception);
				}

				// finish request
				mAPICallManager.finishTask(task);
			}
		});
	}


	@Override
	public void onGeolocationRespond(Geolocation geolocation, final Location location)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView == null) return; // view was destroyed

				Logcat.d(TAG + "onGeolocationRespond(): " + location.getProvider() + " / " + location.getLatitude() + " / " + location.getLongitude() + " / " + new Date(location.getTime()).toString());
				mLocation = new Location(location);
				loadData();
			}
		});
	}


	@Override
	public void onGeolocationFail(Geolocation geolocation)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView == null) return; // view was destroyed

				Logcat.d(TAG + "onGeolocationFail()");
				showError();
			}
		});
	}
}