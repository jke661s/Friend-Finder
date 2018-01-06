package com.example.jiaqiwang.monashfriendfinder.ServiceUse;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.jiaqiwang.monashfriendfinder.DataStore.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Jiaqi Wang on 5/2/2017.
 */

public class YahooWeatherService {
    private WeatherServiceCallBack callBack;
    private String location;
    private String lat;
    private String lon;
    private Exception error;

    public WeatherServiceCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(WeatherServiceCallBack callBack) {
        this.callBack = callBack;
    }



    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    public YahooWeatherService(WeatherServiceCallBack callBack) {
        this.callBack = callBack;
    }



    public void refreshWeather(String latit, String longi){
        this.lat = latit;
        this.lon = longi;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                String YahooQL = String.format("select * from weather.forecast where woeid in (SELECT woeid FROM geo.places WHERE text=\"(%s,%s)\") and u='c'", params[0], params[1]);

                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YahooQL));

                try {
                    URL url = new URL(endpoint);

                    URLConnection con = url.openConnection();

                    InputStream in = con.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    StringBuilder result = new StringBuilder();

                    String line;

                    while ((line = reader.readLine()) != null){

                        result.append(line);

                    }

                    return result.toString();

                } catch (Exception e) {
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if (s == null && error != null){
                    callBack.serviceFailure(error);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject querryResults = data.optJSONObject("query");

                    int count = querryResults.optInt("count");

                    if (count == 0 ){

                        callBack.serviceFailure(new LocationWeatherException("No weather information found for your city"));

                        return;

                    }

                    Channel channel = new Channel();
                    channel.populate(querryResults.optJSONObject("results").optJSONObject("channel"));

                    callBack.serviceSuccess(channel);


                } catch (JSONException e) {
                    callBack.serviceFailure(e);
                }


            }
        }.execute(lat,lon);

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public class LocationWeatherException extends Exception{

        public LocationWeatherException(String message) {
            super(message);
        }
    }



}
