package com.kondenko.yamblzweather.ui.weather;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.pwittchen.weathericonview.WeatherIconView;
import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.utils.WeatherUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mishkun on 09.08.2017.
 */

class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private static final String TAG = ForecastAdapter.class.getSimpleName();
    private final static DateFormat containerDateFormat = new SimpleDateFormat("dd.MM", Locale.getDefault());
    private final SortedList<Weather> weatherSortedList;
    private final Context context;

    ForecastAdapter(List<Weather> items, Context context) {
        this.context = context;
        weatherSortedList = new SortedList<>(Weather.class, new SortedListAdapterCallback<Weather>(this) {
            @Override
            public int compare(Weather o1, Weather o2) {
                return (int) (o1.timestamp() - o2.timestamp());
            }

            @Override
            public boolean areContentsTheSame(Weather oldItem, Weather newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Weather item1, Weather item2) {
                return item1.equals(item2);
            }
        });
        weatherSortedList.addAll(items);
    }

    void setWeather(List<Weather> data) {
        weatherSortedList.beginBatchedUpdates();
        weatherSortedList.clear();
        weatherSortedList.addAll(data);
        Log.d(TAG, "setWeather" + data.get(0));
        weatherSortedList.endBatchedUpdates();
        notifyDataSetChanged();
    }


    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_forecast, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.weatherIconView.setIconSize(32);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ForecastAdapter.ViewHolder holder, int position) {
        Weather weather = weatherSortedList.get(position);
        holder.weather = weather;
        holder.dateView.setText(containerDateFormat.format(weather.timestamp()*1000L));
        holder.dayTemp.setText(String.format("%.1f°C", weather.dayTemperature().celsiusDegrees()));
        holder.nightTemp.setText(String.format("%.1f°C", weather.nightTemperature().celsiusDegrees()));
        holder.weatherIconView.setIconResource(context.getString(WeatherUtils.getIconStringResource(weather.weatherConditions())));
    }

    @Override
    public int getItemCount() {
        return weatherSortedList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView dateView;
        final TextView nightTemp;
        final TextView dayTemp;
        final WeatherIconView weatherIconView;
        Weather weather;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            dateView = (TextView) view.findViewById(R.id.forecast_date_text);
            dayTemp = (TextView) view.findViewById(R.id.forecast_day_temp);
            nightTemp = (TextView) view.findViewById(R.id.forecast_night_temp);
            weatherIconView = (WeatherIconView) view.findViewById(R.id.weather_forecast_icon_condition);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + dateView.getText() + "'";
        }
    }
}
