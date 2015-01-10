package cz.martinbarton.weather.android;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class WeatherApplication extends Application {
    private static WeatherApplication mInstance;

    public WeatherApplication() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // force AsyncTask to be initialized in the main thread due to the bug:
        // http://stackoverflow.com/questions/4280330/onpostexecute-not-being-called-in-asynctask-handler-runtime-exception
        try {
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //init image caching
        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
        cacheDir.mkdirs(); // needs android.permission.WRITE_EXTERNAL_STORAGE
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPoolSize(3)
                .memoryCache(new UsingFreqLimitedMemoryCache(8 * 1024 * 1024))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static Context getContext() {
        return mInstance;
    }
}