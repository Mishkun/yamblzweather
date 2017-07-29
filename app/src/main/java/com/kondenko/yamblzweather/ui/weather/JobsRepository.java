package com.kondenko.yamblzweather.ui.weather;

/**
 * Created by Mishkun on 23.07.2017.
 */

public interface JobsRepository {
    void scheduleUpdateJob(int refreshRateHr);
}
