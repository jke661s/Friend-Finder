package com.example.jiaqiwang.monashfriendfinder.ServiceUse;

import com.example.jiaqiwang.monashfriendfinder.DataStore.Channel;

/**
 * Created by Jiaqi Wang on 5/2/2017.
 */

public interface WeatherServiceCallBack {
    void serviceSuccess(Channel channel);
    void serviceFailure (Exception exception);
}
