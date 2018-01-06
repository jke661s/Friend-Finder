package com.example.jiaqiwang.monashfriendfinder.DataStore;

import org.json.JSONObject;

/**
 * Created by Jiaqi Wang on 5/2/2017.
 */

public class Location implements JSONPopulator {

    private String city;
    private String country;
    private  String region;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public void populate(JSONObject data) {

        city = data.optString("city");
        country = data.optString("country");
        region = data.optString("region");

    }
}