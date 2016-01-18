package cz.martinbarton.weather.android.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cz.martinbarton.weather.android.R;
import cz.martinbarton.weather.android.adapter.DrawerAdapter;
import cz.martinbarton.weather.android.fragment.ForecastFragment;
import cz.martinbarton.weather.android.fragment.TodayFragment;


/**
 * Created by Martin on 8.1.2015.
 */
public class MainActivity extends ActionBarActivity
{
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerListView;
	private Toolbar mToolbar;
	private String[] mTitles;

	public static final String KEY_UNITS_TEMPERATURE = "units_temperature";
	public static final String KEY_UNITS_LENGHT = "units_length";
	public static final String UNITS_TEMPERATURE_C = "Celsius";
	public static final String UNITS_LENGHT_M = "Meters";



	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);

		setupDrawer(savedInstanceState);
	}


	private class DrawerItemClickListener implements ListView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			selectDrawerItem(position);
		}
	}


	@Override
	public void setTitle(CharSequence title)
	{

		getSupportActionBar().setTitle(title);
	}


	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.action_settings:
				showSettings();
				return true;
			case R.id.action_about:
				showAbout();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}


	private void showSettings()
	{
		startActivity(new Intent(this, SettingsActivity.class));
	}


	private void showAbout()
	{
		// create and show the dialog
		ContextThemeWrapper themedContext = new ContextThemeWrapper(this, android.support.v7.appcompat.R.style.Theme_AppCompat_Light);
		AlertDialog.Builder builder = new AlertDialog.Builder(themedContext);
		builder.setTitle(getResources().getString(R.string.action_about));
		builder.setMessage(getResources().getString(R.string.placeholder_about_text))
				.setCancelable(false)
				.setPositiveButton(getResources().getString(R.string.placegolder_about_positive), new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}


	private void setupDrawer(Bundle savedInstanceState)
	{
		Integer[] icons = new Integer[2];
		icons[0] = R.drawable.ic_drawer_today_dark;
		icons[1] = R.drawable.ic_drawer_forecast_dark;

		// reference
		mTitles = getResources().getStringArray(R.array.view_sidemenu_items);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerListView = (ListView) findViewById(R.id.sidemenu);

		// set drawer
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerListView.setAdapter(new DrawerAdapter(this, mTitles, icons));
		mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		if(savedInstanceState == null)
		{
			selectDrawerItem(0);
		}
	}


	private void selectDrawerItem(int position)
	{
		// update the main content by replacing fragments
		Fragment fragment = null;

		if(position == 0)
		{
			// Today Fragment
			fragment = new TodayFragment();

		}
		else
		{
			// Forecast Fragment
			fragment = new ForecastFragment();
		}

		//show and replace fragment
		if(fragment != null)
		{
			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commitAllowingStateLoss();
		}

		// update selected item and title, then close the drawer
		mDrawerListView.setItemChecked(position, true);
		setTitle(mTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerListView);
	}
}
