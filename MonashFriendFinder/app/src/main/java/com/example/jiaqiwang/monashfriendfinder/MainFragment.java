package com.example.jiaqiwang.monashfriendfinder;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiaqiwang.monashfriendfinder.DataStore.Channel;
import com.example.jiaqiwang.monashfriendfinder.DataStore.Item;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.GPSTracker;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.RestClient;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.SharedHelper;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.WeatherServiceCallBack;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.YahooWeatherService;


import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.jiaqiwang.monashfriendfinder.R.id.passwordTextView;

/**
 * Created by Jiaqi Wang on 5/2/2017.
 */

public class MainFragment extends Fragment implements WeatherServiceCallBack, Runnable {
    View vMain;

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private TextView welcomeTextView;
    private TextView dateandtimeTextView;
    private YahooWeatherService service;
    private ProgressDialog dialog;
    private GPSTracker gps;
    private Double lat;
    private Double lon;
    private Handler handler;
    private Context mContext;
    private SharedHelper sh;
    private String studentEmail;
    private JSONObject jsStudent;
    private String studentName;
    private boolean profileDownloaded = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);
        sh = new SharedHelper(vMain.getContext());
        studentEmail = sh.read().get("username");
        getStudentProfileThenInit();
        return vMain;
    }


    public void init() {
        weatherIconImageView = (ImageView) vMain.findViewById(R.id.weatherIconImageView);
        weatherIconImageView.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        dialog.show();
                                                        refreshWeather();
                                                    }
                                                }
        );
        temperatureTextView = (TextView) vMain.findViewById(R.id.temperatureTextView);
        temperatureTextView.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       dialog.show();
                                                       refreshWeather();
                                                   }
                                               }
        );
        dateandtimeTextView = (TextView) vMain.findViewById(R.id.dateandtimeTextView);
        welcomeTextView = (TextView) vMain.findViewById(R.id.welcomeTextView);
        welcomeTextView.setText("Hello " + studentName + ", welcome to Monash Friend Finder. Find your friend NOW!");
        conditionTextView = (TextView) vMain.findViewById(R.id.conditionTextView);
        conditionTextView.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     dialog.show();
                                                     refreshWeather();
                                                 }
                                             }
        );
        locationTextView = (TextView) vMain.findViewById(R.id.locationTextView);
        locationTextView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.show();
                                                    refreshWeather();
                                                }
                                            }
        );

        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(vMain.getContext());
        dialog.setMessage("Loading..");
        dialog.show();

        handler = new Handler() {
            public void handleMessage(Message msg) {
                dateandtimeTextView.setText((String) msg.obj);
            }
        };

    }

    public void refreshWeather() {
        getCurrentLocation();
        service.refreshWeather(String.valueOf(lat), String.valueOf(lon));
    }

    public void getCurrentLocation() {
        gps = new GPSTracker(vMain.getContext());
        if (gps.canGetLocation()) {
            lat = gps.getLatitude();
            lon = gps.getLongitude();
        } else {
            gps.showSettingAlert();
        }
    }

    public void getStudentProfileThenInit() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return RestClient.findStudentByEmail(params[0]);
            }

            @Override
            protected void onPostExecute(String student) {
                try {
                    jsStudent = new JSONObject(student.substring(1, student.length() - 1));
                    studentName = jsStudent.optString("firstname");
                    init();
                    refreshWeather();
                    new Thread(MainFragment.this).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(studentEmail);
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();

        int resourceId = getResources().getIdentifier("drawable/weather_" + item.getCondition().getCode(), null, vMain.getContext().getPackageName());

        @SuppressWarnings("deprecation")
        Drawable weatherDrawable = getResources().getDrawable(resourceId);

        weatherIconImageView.setImageDrawable(weatherDrawable);

        locationTextView.setText(channel.getLocation().getCity() + ", " + channel.getLocation().getRegion() + ", " + channel.getLocation().getCountry());

        temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());

        conditionTextView.setText(item.getCondition().getDescription());
    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(vMain.getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            while (true) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss  dd-MM-yyyy");
                String str = sdf.format(new Date());
                handler.sendMessage(handler.obtainMessage(100, str));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
