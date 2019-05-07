package com.friki.mbuthia.propertyfindapp;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class ServiceSettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_services);
    }
}
