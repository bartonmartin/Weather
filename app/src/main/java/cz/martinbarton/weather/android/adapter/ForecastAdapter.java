package cz.martinbarton.weather.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import cz.martinbarton.weather.android.R;
import cz.martinbarton.weather.android.entity.ForecastEntity;

/**
 * Created by Martin on 8.1.2015.
 */
public class ForecastAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mDateList;
    private String[] mTemperatureList;
    private String[] mWeatherDescList;
    private String[] mWeatherIconUrlList;

    public ForecastAdapter(Context context, String[] dateList, String[] temperatureList, String[] weatherDescList, String[] weatherIconUrlList) {
        this.mContext = context;
        this.mDateList = dateList;
        this.mTemperatureList = temperatureList;
        this.mWeatherDescList = weatherDescList;
        this.mWeatherIconUrlList = weatherIconUrlList;
    }

    public ForecastAdapter(Context applicationContext, ForecastEntity mForecast) {
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // inflate view
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adapter_forecast, parent, false);

            // view holder
            ViewHolder holder = new ViewHolder();
            holder.dayTextView = (TextView) view.findViewById(R.id.adapter_forecast_day);
            holder.temperatureTextView = (TextView) view.findViewById(R.id.adapter_forecast_temperature);
            holder.weatherTextView = (TextView) view.findViewById(R.id.adapter_forecast_weather);
            holder.iconImageView = (ImageView) view.findViewById(R.id.adapter_forecast_icon);
            view.setTag(holder);
        }

        // entity
        String day = (String) mDateList[position];
        String temperature = (String) mTemperatureList[position];
        String weather = (String) mWeatherDescList[position];
        String weatherIcon = (String) mWeatherIconUrlList[position];

        if (day != null && temperature != null && weather != null && weatherIcon != null) {
            // view holder
            ViewHolder holder = (ViewHolder) view.getTag();

            // content
            holder.dayTextView.setText(day);
            holder.temperatureTextView.setText(temperature);
            holder.weatherTextView.setText(weather);

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(weatherIcon, holder.iconImageView);
        }

        return view;
    }

    @Override
    public int getCount() {
        if (mDateList != null) return mDateList.length;
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDateList != null) return mDateList[position];
        else return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView dayTextView;
        TextView temperatureTextView;
        TextView weatherTextView;
        ImageView iconImageView;
    }
}
