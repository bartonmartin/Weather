package cz.martinbarton.weather.android.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import cz.martinbarton.weather.android.R;
import cz.martinbarton.weather.android.fragment.SettingsFragment;


/**
 * Created by Martin on 8.1.2015.
 */
public class SettingsActivity extends ActionBarActivity
{
	public static final String TAG = "SettingsActivity";

	private Toolbar mToolbar;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, new SettingsFragment())
				.commit();
	}


	@Override
	public void setTitle(CharSequence title)
	{
		getSupportActionBar().setTitle(title);
	}
}
