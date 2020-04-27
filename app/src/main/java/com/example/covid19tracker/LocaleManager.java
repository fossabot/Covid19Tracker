package com.example.covid19tracker;

import android.app.Application;
import android.content.res.Configuration;

import java.util.Locale;

public class LocaleManager extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Create a new Locale object
        Locale locale = new Locale("hi-IN");
        Locale.setDefault(locale);
        // Create a new configuration object
        Configuration config = new Configuration();
        // Set the locale of the new configuration
        config.locale = locale;
        // Update the configuration of the Accplication context
        getResources().updateConfiguration(
                config,
                getResources().getDisplayMetrics()
        );
    }
}
