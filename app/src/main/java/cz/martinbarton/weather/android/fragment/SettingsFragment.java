package cz.martinbarton.weather.android.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import cz.martinbarton.weather.android.R;

/**
 * Created by Martin on 9.1.2015.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.prefs);
    }

}
