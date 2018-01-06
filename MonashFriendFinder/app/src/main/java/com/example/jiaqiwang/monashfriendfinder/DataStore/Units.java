package com.example.jiaqiwang.monashfriendfinder.DataStore;

import org.json.JSONObject;

/**
 * Created by Jiaqi Wang on 5/2/2017.
 */

public class Units implements JSONPopulator {

    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public void populate(JSONObject data) {

        temperature = data.optString("temperature");

    }
}