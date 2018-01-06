package com.example.jiaqiwang.monashfriendfinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.WindowDecorActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiaqiwang.monashfriendfinder.DataStore.Friendship;
import com.example.jiaqiwang.monashfriendfinder.DataStore.StudentProfile;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.MD5;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.RestClient;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by Jiaqi Wang on 5/4/2017.
 */

public class FindResultActivity extends AppCompatActivity {
    private String keywordList;
    private String keywordResult;
    private ProgressDialog dialog;
    private String keywordListFinal;
    private TextView fdtv1;
    private TextView fdtv2;
    private TextView fdtv3;
    private TextView fdtv4;
    private TextView fdtv5;
    private TextView fdtv6;
    private Button lastButton;
    private Button nextButton;
    private int lastButtonValue;
    private int nextButtonValue;
    private int wholePageNumber;
    private LinearLayout lnlo;
    private JSONObject jSonOb1;
    private JSONObject jSonOb2;
    private JSONObject jSonOb3;
    private JSONObject jSonOb4;
    private JSONObject jSonOb5;
    private JSONArray jArray;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Button bt1;
    private Button bt2;
    private Button bt3;
    private Button bt4;
    private Button bt5;
    private Button bt6;
    private Button bt7;
    private Button bt8;
    private Context mContext;
    private SharedHelper sh;
    private String email;
    private JSONObject usrJSON;
    private int friendId;
    private JSONObject friJSON;
    private JSONArray fsRecordsJSONArray;
    private Friendship sp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = getApplicationContext();
        sh = new SharedHelper(mContext);
        email = sh.read().get("username");
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_findresult);
        getUserInfo();

    }

    private void getUserInfo() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... email) {
                return RestClient.findStudentByEmail(email[0]);
            }

            @Override
            protected void onPostExecute(String students) {
                try {
                    usrJSON = new JSONArray(students).getJSONObject(0);
                    getAllRecords();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(email);
    }

    private void getFriInfo(int n) {
        int i = 4 * nextButtonValue - 5 + n;
        dialog.show();
        try {
            friJSON = jArray.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getAllRecords() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... param) {
                return RestClient.findAllFriendship();
            }

            @Override
            protected void onPostExecute(String friendships) {
                try {
                    fsRecordsJSONArray = new JSONArray(friendships);
                    getFriendThenInit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private void init() {
        lastButtonValue = 0;
        nextButtonValue = 1;

        lnlo = (LinearLayout) findViewById(R.id.lnlo);

        fdtv1 = (TextView) findViewById(R.id.fdtv1);
        fdtv2 = (TextView) findViewById(R.id.fdtv2);
        fdtv3 = (TextView) findViewById(R.id.fdtv3);
        fdtv4 = (TextView) findViewById(R.id.fdtv4);
        fdtv5 = (TextView) findViewById(R.id.fdtv5);
        fdtv6 = (TextView) findViewById(R.id.fdtv6);

        lastButton = (Button) findViewById(R.id.lastButton);
        nextButton = (Button) findViewById(R.id.nextButton);

        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);
        bt5 = (Button) findViewById(R.id.bt5);
        bt6 = (Button) findViewById(R.id.bt6);
        bt7 = (Button) findViewById(R.id.bt7);
        bt8 = (Button) findViewById(R.id.bt8);
        setText();


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextButtonValue < wholePageNumber) {
                    nextButtonValue = nextButtonValue + 1;
                    lastButtonValue = lastButtonValue + 1;
                    setText();
                } else {
                    Toast.makeText(FindResultActivity.this, "This is the last page", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fdtv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, GoogleAPISearch.main(), Toast.LENGTH_SHORT).show();
            }
        });

        lastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastButtonValue > 0) {
                    lastButtonValue = lastButtonValue - 1;
                    nextButtonValue = nextButtonValue - 1;
                    setText();
                } else {
                    Toast.makeText(FindResultActivity.this, "This is the first page", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 4 * nextButtonValue - 4;
                alert = null;
                builder = new AlertDialog.Builder(FindResultActivity.this);
                try {
                    alert = builder.setTitle("Student Detail: ")
                            .setMessage("First Name:       " + jArray.getJSONObject(i).optString("firstname") +
                                    "\nLast Name:        " + jArray.getJSONObject(i).optString("surname") +
                                    "\nFavourite Movie:  " + jArray.getJSONObject(i).optString("favouritemovie") +
                                    "\nGender:           " + jArray.getJSONObject(i).optString("gender") +
                                    "\nCourse:           " + jArray.getJSONObject(i).optString("course") +
                                    "\nDate of Birth:    " + jArray.getJSONObject(i).optString("dob") +
                                    "\nStudy Mode:       " + jArray.getJSONObject(i).optString("studymode") +
                                    "\nAddress:          " + jArray.getJSONObject(i).optString("address") +
                                    "\nSuburb:           " + jArray.getJSONObject(i).optString("suburb") +
                                    "\nNationality:      " + jArray.getJSONObject(i).optString("nationality") +
                                    "\nNative Language:  " + jArray.getJSONObject(i).optString("nativelanguage") +
                                    "\nFavourite Sport:  " + jArray.getJSONObject(i).optString("favouritesport") +
                                    "\nFavourite Unit:   " + jArray.getJSONObject(i).optString("favouriteunit") +
                                    "\nCurrent Job:      " + jArray.getJSONObject(i).optString("currentjob")
                            )

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alert.show();
            }
        });



        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getFriInfo(1);
                dialog.hide();;
                boolean first = false;
                boolean second = true;
                boolean third = false;
                int[] recordPosition = new int[5000];

                for(int a = 0;a < fsRecordsJSONArray.length();a ++){
                    int rp = 0;
                    try {
                        if( ( usrJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid1").optInt("studid") && friJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid2").optInt("studid")) || ( usrJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid2").optInt("studid") && friJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid1").optInt("studid")) )
{
                        first = true;
                        recordPosition[rp] = a;
                        rp = rp + 1;
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                for (int a = 0; a < recordPosition.length; a++){
                    try {
                        if (fsRecordsJSONArray.getJSONObject(recordPosition[a]).getString("endingdate") == null){
                            second = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        second = false;
                    } ;
                }
                if(first == false){
                    StudentProfile studentid1 = new StudentProfile(usrJSON.optInt("studid"),usrJSON.optString("firstname"),usrJSON.optString("surname"),usrJSON.optString("dob"),usrJSON.optString("gender"),usrJSON.optString("course"),usrJSON.optString("studymode"),usrJSON.optString("address"),usrJSON.optString("suburb"),usrJSON.optString("nationality"),usrJSON.optString("nativelanguage"),usrJSON.optString("favouritesport"),usrJSON.optString("favouritemovie"),usrJSON.optString("favouriteunit"),usrJSON.optString("currentjob"),usrJSON.optString("monashemail"),usrJSON.optString("password"),usrJSON.optString("subscriptiondateandtime"));
                    StudentProfile studentid2 = new StudentProfile(friJSON.optInt("studid"),friJSON.optString("firstname"),friJSON.optString("surname"),friJSON.optString("dob"),friJSON.optString("gender"),friJSON.optString("course"),friJSON.optString("studymode"),friJSON.optString("address"),friJSON.optString("suburb"),friJSON.optString("nationality"),friJSON.optString("nativelanguage"),friJSON.optString("favouritesport"),friJSON.optString("favouritemovie"),friJSON.optString("favouriteunit"),friJSON.optString("currentjob"),friJSON.optString("monashemail"),friJSON.optString("password"),friJSON.optString("subscriptiondateandtime"));
                    Calendar calendar = Calendar.getInstance();
                    String subscriptiondateandtime = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR));
                    if (studentid1.getStudid() < studentid2.getStudid()){
                        Friendship sp = new Friendship(fsRecordsJSONArray.length()+1,subscriptiondateandtime,studentid2,studentid1);
                        RestClient.createFriendship(sp);
                        Toast.makeText(mContext, "Add successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    if(studentid1.getStudid() > studentid2.getStudid()){
                        Friendship sp = new Friendship(fsRecordsJSONArray.length()+1,subscriptiondateandtime,studentid1,studentid2);
                        RestClient.createFriendship(sp);
                        Toast.makeText(mContext, "Add successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    if(studentid1.getStudid() == studentid2.getStudid()){
                        Toast.makeText(mContext, "You cannot add yourself.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(second == false){
                        Toast.makeText(mContext, "Have added already", Toast.LENGTH_SHORT).show();
                    }else{
                        StudentProfile studentid1 = new StudentProfile(usrJSON.optInt("studid"),usrJSON.optString("firstname"),usrJSON.optString("surname"),usrJSON.optString("dob"),usrJSON.optString("gender"),usrJSON.optString("course"),usrJSON.optString("studymode"),usrJSON.optString("address"),usrJSON.optString("suburb"),usrJSON.optString("nationality"),usrJSON.optString("nativelanguage"),usrJSON.optString("favouritesport"),usrJSON.optString("favouritemovie"),usrJSON.optString("favouriteunit"),usrJSON.optString("currentjob"),usrJSON.optString("monashemail"),usrJSON.optString("password"),usrJSON.optString("subscriptiondateandtime"));
                        StudentProfile studentid2 = new StudentProfile(friJSON.optInt("studid"),friJSON.optString("firstname"),friJSON.optString("surname"),friJSON.optString("dob"),friJSON.optString("gender"),friJSON.optString("course"),friJSON.optString("studymode"),friJSON.optString("address"),friJSON.optString("suburb"),friJSON.optString("nationality"),friJSON.optString("nativelanguage"),friJSON.optString("favouritesport"),friJSON.optString("favouritemovie"),friJSON.optString("favouriteunit"),friJSON.optString("currentjob"),friJSON.optString("monashemail"),friJSON.optString("password"),friJSON.optString("subscriptiondateandtime"));
                        Calendar calendar = Calendar.getInstance();
                        String subscriptiondateandtime = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR));
                        if (studentid1.getStudid() < studentid2.getStudid()){
                            Friendship sp = new Friendship(fsRecordsJSONArray.length()+1,subscriptiondateandtime,studentid2,studentid1);
                            RestClient.createFriendship(sp);
                            Toast.makeText(mContext, "Add successfully", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        if(studentid1.getStudid() > studentid2.getStudid()){
                            Friendship sp = new Friendship(fsRecordsJSONArray.length()+1,subscriptiondateandtime,studentid1,studentid2);
                            RestClient.createFriendship(sp);
                            Toast.makeText(mContext, "Add successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        if(studentid1.getStudid() == studentid2.getStudid()){
                            Toast.makeText(mContext, "You cannot add yourself.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 4 * nextButtonValue - 3;
                alert = null;
                builder = new AlertDialog.Builder(FindResultActivity.this);
                try {
                    alert = builder.setTitle("Student Detail: ")
                            .setMessage("First Name:       " + jArray.getJSONObject(i).optString("firstname") +
                                    "\nLast Name:        " + jArray.getJSONObject(i).optString("surname") +
                                    "\nFavourite Movie:  " + jArray.getJSONObject(i).optString("favouritemovie") +
                                    "\nGender:           " + jArray.getJSONObject(i).optString("gender") +
                                    "\nCourse:           " + jArray.getJSONObject(i).optString("course") +
                                    "\nDate of Birth:    " + jArray.getJSONObject(i).optString("dob") +
                                    "\nStudy Mode:       " + jArray.getJSONObject(i).optString("studymode") +
                                    "\nAddress:          " + jArray.getJSONObject(i).optString("address") +
                                    "\nSuburb:           " + jArray.getJSONObject(i).optString("suburb") +
                                    "\nNationality:      " + jArray.getJSONObject(i).optString("nationality") +
                                    "\nNative Language:  " + jArray.getJSONObject(i).optString("nativelanguage") +
                                    "\nFavourite Sport:  " + jArray.getJSONObject(i).optString("favouritesport") +
                                    "\nFavourite Unit:   " + jArray.getJSONObject(i).optString("favouriteunit") +
                                    "\nCurrent Job:      " + jArray.getJSONObject(i).optString("currentjob")
                            )

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alert.show();
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFriInfo(2);
                dialog.hide();;
                boolean first = false;
                boolean second = true;
                boolean third = false;
                int[] recordPosition = new int[5000];

                for(int a = 0;a < fsRecordsJSONArray.length();a ++){
                    int rp = 0;
                    try {
                        if( ( usrJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid1").optInt("studid") && friJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid2").optInt("studid")) || ( usrJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid2").optInt("studid") && friJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid1").optInt("studid")) )
                        {
                            first = true;
                            recordPosition[rp] = a;
                            rp = rp + 1;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                for (int a = 0; a < recordPosition.length; a++){
                    try {
                        if (fsRecordsJSONArray.getJSONObject(recordPosition[a]).getString("endingdate") == null){
                            second = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        second = false;
                    } ;
                }

                if(first == false){
                    StudentProfile studentid1 = new StudentProfile(usrJSON.optInt("studid"),usrJSON.optString("firstname"),usrJSON.optString("surname"),usrJSON.optString("dob"),usrJSON.optString("gender"),usrJSON.optString("course"),usrJSON.optString("studymode"),usrJSON.optString("address"),usrJSON.optString("suburb"),usrJSON.optString("nationality"),usrJSON.optString("nativelanguage"),usrJSON.optString("favouritesport"),usrJSON.optString("favouritemovie"),usrJSON.optString("favouriteunit"),usrJSON.optString("currentjob"),usrJSON.optString("monashemail"),usrJSON.optString("password"),usrJSON.optString("subscriptiondateandtime"));
                    StudentProfile studentid2 = new StudentProfile(friJSON.optInt("studid"),friJSON.optString("firstname"),friJSON.optString("surname"),friJSON.optString("dob"),friJSON.optString("gender"),friJSON.optString("course"),friJSON.optString("studymode"),friJSON.optString("address"),friJSON.optString("suburb"),friJSON.optString("nationality"),friJSON.optString("nativelanguage"),friJSON.optString("favouritesport"),friJSON.optString("favouritemovie"),friJSON.optString("favouriteunit"),friJSON.optString("currentjob"),friJSON.optString("monashemail"),friJSON.optString("password"),friJSON.optString("subscriptiondateandtime"));
                    Calendar calendar = Calendar.getInstance();
                    String subscriptiondateandtime = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR));
                    if (studentid1.getStudid() < studentid2.getStudid()){
                         sp = new Friendship(fsRecordsJSONArray.length()+1,subscriptiondateandtime,studentid2,studentid1);

                        new AsyncTask<Friendship, Void, String>() {
                            @Override
                            protected String doInBackground(Friendship... params) {
                                RestClient.createFriendship(params[0]);
                                return "Successfully Added";
                            }

                            @Override
                            protected void onPostExecute(String response) {
                                dialog.hide();
                                Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }.execute(sp)
                        ;
                    }

                    if(studentid1.getStudid() > studentid2.getStudid()) {

                        if (studentid1.getStudid() < studentid2.getStudid()) {
                            sp = new Friendship(fsRecordsJSONArray.length() + 1, subscriptiondateandtime, studentid1, studentid2);

                            new AsyncTask<Friendship, Void, String>() {
                                @Override
                                protected String doInBackground(Friendship... params) {
                                    RestClient.createFriendship(params[0]);
                                    return "Successfully Added";
                                }

                                @Override
                                protected void onPostExecute(String response) {
                                    dialog.hide();
                                    Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }.execute(sp)
                            ;
                        }
                    }

                    if(studentid1.getStudid() == studentid2.getStudid()){
                        Toast.makeText(mContext, "You cannot add yourself.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(second == false){
                        Toast.makeText(mContext, "Have added already", Toast.LENGTH_SHORT).show();
                    }else{
                        StudentProfile studentid1 = new StudentProfile(usrJSON.optInt("studid"),usrJSON.optString("firstname"),usrJSON.optString("surname"),usrJSON.optString("dob"),usrJSON.optString("gender"),usrJSON.optString("course"),usrJSON.optString("studymode"),usrJSON.optString("address"),usrJSON.optString("suburb"),usrJSON.optString("nationality"),usrJSON.optString("nativelanguage"),usrJSON.optString("favouritesport"),usrJSON.optString("favouritemovie"),usrJSON.optString("favouriteunit"),usrJSON.optString("currentjob"),usrJSON.optString("monashemail"),usrJSON.optString("password"),usrJSON.optString("subscriptiondateandtime"));
                        StudentProfile studentid2 = new StudentProfile(friJSON.optInt("studid"),friJSON.optString("firstname"),friJSON.optString("surname"),friJSON.optString("dob"),friJSON.optString("gender"),friJSON.optString("course"),friJSON.optString("studymode"),friJSON.optString("address"),friJSON.optString("suburb"),friJSON.optString("nationality"),friJSON.optString("nativelanguage"),friJSON.optString("favouritesport"),friJSON.optString("favouritemovie"),friJSON.optString("favouriteunit"),friJSON.optString("currentjob"),friJSON.optString("monashemail"),friJSON.optString("password"),friJSON.optString("subscriptiondateandtime"));
                        Calendar calendar = Calendar.getInstance();
                        String subscriptiondateandtime = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR));
                        if (studentid1.getStudid() < studentid2.getStudid()){
                            sp = new Friendship(fsRecordsJSONArray.length()+1,subscriptiondateandtime,studentid2,studentid1);

                            new AsyncTask<Friendship, Void, String>() {
                                @Override
                                protected String doInBackground(Friendship... params) {
                                    RestClient.createFriendship(params[0]);
                                    return "Successfully Added";
                                }

                                @Override
                                protected void onPostExecute(String response) {
                                    dialog.hide();
                                    Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }.execute(sp)
                            ;
                        }

                        if(studentid1.getStudid() > studentid2.getStudid()) {

                            if (studentid1.getStudid() < studentid2.getStudid()) {
                                sp = new Friendship(fsRecordsJSONArray.length() + 1, subscriptiondateandtime, studentid1, studentid2);

                                new AsyncTask<Friendship, Void, String>() {
                                    @Override
                                    protected String doInBackground(Friendship... params) {
                                        RestClient.createFriendship(params[0]);
                                        return "Successfully Added";
                                    }

                                    @Override
                                    protected void onPostExecute(String response) {
                                        dialog.hide();
                                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }.execute(sp)
                                ;
                            }
                        }

                        if(studentid1.getStudid() == studentid2.getStudid()){
                            Toast.makeText(mContext, "You cannot add yourself.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 4 * nextButtonValue - 2;
                alert = null;
                builder = new AlertDialog.Builder(FindResultActivity.this);
                try {
                    alert = builder.setTitle("Student Detail: ")
                            .setMessage("First Name:       " + jArray.getJSONObject(i).optString("firstname") +
                                    "\nLast Name:        " + jArray.getJSONObject(i).optString("surname") +
                                    "\nFavourite Movie:  " + jArray.getJSONObject(i).optString("favouritemovie") +
                                    "\nGender:           " + jArray.getJSONObject(i).optString("gender") +
                                    "\nCourse:           " + jArray.getJSONObject(i).optString("course") +
                                    "\nDate of Birth:    " + jArray.getJSONObject(i).optString("dob") +
                                    "\nStudy Mode:       " + jArray.getJSONObject(i).optString("studymode") +
                                    "\nAddress:          " + jArray.getJSONObject(i).optString("address") +
                                    "\nSuburb:           " + jArray.getJSONObject(i).optString("suburb") +
                                    "\nNationality:      " + jArray.getJSONObject(i).optString("nationality") +
                                    "\nNative Language:  " + jArray.getJSONObject(i).optString("nativelanguage") +
                                    "\nFavourite Sport:  " + jArray.getJSONObject(i).optString("favouritesport") +
                                    "\nFavourite Unit:   " + jArray.getJSONObject(i).optString("favouriteunit") +
                                    "\nCurrent Job:      " + jArray.getJSONObject(i).optString("currentjob")
                            )

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alert.show();
            }
        });

        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFriInfo(3);
                dialog.hide();;
                boolean first = false;
                boolean second = true;
                boolean third = false;
                int[] recordPosition = new int[5000];

                for(int a = 0;a < fsRecordsJSONArray.length();a ++){
                    int rp = 0;
                    try {
                        if( ( usrJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid1").optInt("studid") && friJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid2").optInt("studid")) || ( usrJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid2").optInt("studid") && friJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid1").optInt("studid")) )
                        {
                            first = true;
                            recordPosition[rp] = a;
                            rp = rp + 1;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                for (int a = 0; a < recordPosition.length; a++){
                    try {
                        if (fsRecordsJSONArray.getJSONObject(recordPosition[a]).getString("endingdate") == null){
                            second = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        second = false;
                    } ;
                }


                if(first == false){
                    StudentProfile studentid1 = new StudentProfile(usrJSON.optInt("studid"),usrJSON.optString("firstname"),usrJSON.optString("surname"),usrJSON.optString("dob"),usrJSON.optString("gender"),usrJSON.optString("course"),usrJSON.optString("studymode"),usrJSON.optString("address"),usrJSON.optString("suburb"),usrJSON.optString("nationality"),usrJSON.optString("nativelanguage"),usrJSON.optString("favouritesport"),usrJSON.optString("favouritemovie"),usrJSON.optString("favouriteunit"),usrJSON.optString("currentjob"),usrJSON.optString("monashemail"),usrJSON.optString("password"),usrJSON.optString("subscriptiondateandtime"));
                    StudentProfile studentid2 = new StudentProfile(friJSON.optInt("studid"),friJSON.optString("firstname"),friJSON.optString("surname"),friJSON.optString("dob"),friJSON.optString("gender"),friJSON.optString("course"),friJSON.optString("studymode"),friJSON.optString("address"),friJSON.optString("suburb"),friJSON.optString("nationality"),friJSON.optString("nativelanguage"),friJSON.optString("favouritesport"),friJSON.optString("favouritemovie"),friJSON.optString("favouriteunit"),friJSON.optString("currentjob"),friJSON.optString("monashemail"),friJSON.optString("password"),friJSON.optString("subscriptiondateandtime"));
                    Calendar calendar = Calendar.getInstance();
                    String subscriptiondateandtime = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR));
                    if (studentid1.getStudid() < studentid2.getStudid()){
                        Friendship sp = new Friendship(fsRecordsJSONArray.length()+1,subscriptiondateandtime,studentid2,studentid1);
                        RestClient.createFriendship(sp);
                        Toast.makeText(mContext, "Add successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    if(studentid1.getStudid() > studentid2.getStudid()){
                        Friendship sp = new Friendship(fsRecordsJSONArray.length()+1,subscriptiondateandtime,studentid1,studentid2);
                        RestClient.createFriendship(sp);
                        Toast.makeText(mContext, "Add successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    if(studentid1.getStudid() == studentid2.getStudid()){
                        Toast.makeText(mContext, "You cannot add yourself.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(second == false){
                        Toast.makeText(mContext, "Have added already", Toast.LENGTH_SHORT).show();
                    }else{
                        StudentProfile studentid1 = new StudentProfile(usrJSON.optInt("studid"),usrJSON.optString("firstname"),usrJSON.optString("surname"),usrJSON.optString("dob"),usrJSON.optString("gender"),usrJSON.optString("course"),usrJSON.optString("studymode"),usrJSON.optString("address"),usrJSON.optString("suburb"),usrJSON.optString("nationality"),usrJSON.optString("nativelanguage"),usrJSON.optString("favouritesport"),usrJSON.optString("favouritemovie"),usrJSON.optString("favouriteunit"),usrJSON.optString("currentjob"),usrJSON.optString("monashemail"),usrJSON.optString("password"),usrJSON.optString("subscriptiondateandtime"));
                        StudentProfile studentid2 = new StudentProfile(friJSON.optInt("studid"),friJSON.optString("firstname"),friJSON.optString("surname"),friJSON.optString("dob"),friJSON.optString("gender"),friJSON.optString("course"),friJSON.optString("studymode"),friJSON.optString("address"),friJSON.optString("suburb"),friJSON.optString("nationality"),friJSON.optString("nativelanguage"),friJSON.optString("favouritesport"),friJSON.optString("favouritemovie"),friJSON.optString("favouriteunit"),friJSON.optString("currentjob"),friJSON.optString("monashemail"),friJSON.optString("password"),friJSON.optString("subscriptiondateandtime"));
                        Calendar calendar = Calendar.getInstance();
                        String subscriptiondateandtime = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR));
                        if (studentid1.getStudid() < studentid2.getStudid()){
                            Friendship sp = new Friendship(fsRecordsJSONArray.length()+1,subscriptiondateandtime,studentid2,studentid1);
                            RestClient.createFriendship(sp);
                            Toast.makeText(mContext, "Add successfully", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        if(studentid1.getStudid() > studentid2.getStudid()){
                            Friendship sp = new Friendship(fsRecordsJSONArray.length()+1,subscriptiondateandtime,studentid1,studentid2);
                            RestClient.createFriendship(sp);
                            Toast.makeText(mContext, "Add successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        if(studentid1.getStudid() == studentid2.getStudid()){
                            Toast.makeText(mContext, "You cannot add yourself.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 4 * nextButtonValue - 1;
                alert = null;
                builder = new AlertDialog.Builder(FindResultActivity.this);
                try {
                    alert = builder.setTitle("Student Detail: ")
                            .setMessage("First Name:       " + jArray.getJSONObject(i).optString("firstname") +
                                    "\nLast Name:        " + jArray.getJSONObject(i).optString("surname") +
                                    "\nFavourite Movie:  " + jArray.getJSONObject(i).optString("favouritemovie") +
                                    "\nGender:           " + jArray.getJSONObject(i).optString("gender") +
                                    "\nCourse:           " + jArray.getJSONObject(i).optString("course") +
                                    "\nDate of Birth:    " + jArray.getJSONObject(i).optString("dob") +
                                    "\nStudy Mode:       " + jArray.getJSONObject(i).optString("studymode") +
                                    "\nAddress:          " + jArray.getJSONObject(i).optString("address") +
                                    "\nSuburb:           " + jArray.getJSONObject(i).optString("suburb") +
                                    "\nNationality:      " + jArray.getJSONObject(i).optString("nationality") +
                                    "\nNative Language:  " + jArray.getJSONObject(i).optString("nativelanguage") +
                                    "\nFavourite Sport:  " + jArray.getJSONObject(i).optString("favouritesport") +
                                    "\nFavourite Unit:   " + jArray.getJSONObject(i).optString("favouriteunit") +
                                    "\nCurrent Job:      " + jArray.getJSONObject(i).optString("currentjob")
                            )

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alert.show();
            }
        });

        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFriInfo(4);
                dialog.hide();;
                boolean first = false;
                boolean second = true;
                boolean third = false;
                int[] recordPosition = new int[5000];

                for(int a = 0;a < fsRecordsJSONArray.length();a ++){
                    int rp = 0;
                    try {
                        if( ( usrJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid1").optInt("studid") && friJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid2").optInt("studid")) || ( usrJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid2").optInt("studid") && friJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(a).optJSONObject("studentid1").optInt("studid")) )
                        {
                            first = true;
                            recordPosition[rp] = a;
                            rp = rp + 1;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                for (int a = 0; a < recordPosition.length; a++){
                    try {
                        if (fsRecordsJSONArray.getJSONObject(recordPosition[a]).getString("endingdate") == null){
                            second = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        second = false;
                    } ;
                }


                if(first == false){
                    StudentProfile studentid1 = new StudentProfile(usrJSON.optInt("studid"),usrJSON.optString("firstname"),usrJSON.optString("surname"),usrJSON.optString("dob"),usrJSON.optString("gender"),usrJSON.optString("course"),usrJSON.optString("studymode"),usrJSON.optString("address"),usrJSON.optString("suburb"),usrJSON.optString("nationality"),usrJSON.optString("nativelanguage"),usrJSON.optString("favouritesport"),usrJSON.optString("favouritemovie"),usrJSON.optString("favouriteunit"),usrJSON.optString("currentjob"),usrJSON.optString("monashemail"),usrJSON.optString("password"),usrJSON.optString("subscriptiondateandtime"));
                    StudentProfile studentid2 = new StudentProfile(friJSON.optInt("studid"),friJSON.optString("firstname"),friJSON.optString("surname"),friJSON.optString("dob"),friJSON.optString("gender"),friJSON.optString("course"),friJSON.optString("studymode"),friJSON.optString("address"),friJSON.optString("suburb"),friJSON.optString("nationality"),friJSON.optString("nativelanguage"),friJSON.optString("favouritesport"),friJSON.optString("favouritemovie"),friJSON.optString("favouriteunit"),friJSON.optString("currentjob"),friJSON.optString("monashemail"),friJSON.optString("password"),friJSON.optString("subscriptiondateandtime"));
                    Calendar calendar = Calendar.getInstance();
                    String subscriptiondateandtime = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR));
                    if (studentid1.getStudid() < studentid2.getStudid()){
                        Friendship sp = new Friendship(fsRecordsJSONArray.length()+1,subscriptiondateandtime,studentid2,studentid1);
                        RestClient.createFriendship(sp);
                        Toast.makeText(mContext, "Add successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    if(studentid1.getStudid() > studentid2.getStudid()){
                        Friendship sp = new Friendship(fsRecordsJSONArray.length()+1,subscriptiondateandtime,studentid1,studentid2);
                        RestClient.createFriendship(sp);
                        Toast.makeText(mContext, "Add successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    if(studentid1.getStudid() == studentid2.getStudid()){
                        Toast.makeText(mContext, "You cannot add yourself.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(second == false){
                        Toast.makeText(mContext, "Have added already", Toast.LENGTH_SHORT).show();
                    }else{
                        StudentProfile studentid1 = new StudentProfile(usrJSON.optInt("studid"),usrJSON.optString("firstname"),usrJSON.optString("surname"),usrJSON.optString("dob"),usrJSON.optString("gender"),usrJSON.optString("course"),usrJSON.optString("studymode"),usrJSON.optString("address"),usrJSON.optString("suburb"),usrJSON.optString("nationality"),usrJSON.optString("nativelanguage"),usrJSON.optString("favouritesport"),usrJSON.optString("favouritemovie"),usrJSON.optString("favouriteunit"),usrJSON.optString("currentjob"),usrJSON.optString("monashemail"),usrJSON.optString("password"),usrJSON.optString("subscriptiondateandtime"));
                        StudentProfile studentid2 = new StudentProfile(friJSON.optInt("studid"),friJSON.optString("firstname"),friJSON.optString("surname"),friJSON.optString("dob"),friJSON.optString("gender"),friJSON.optString("course"),friJSON.optString("studymode"),friJSON.optString("address"),friJSON.optString("suburb"),friJSON.optString("nationality"),friJSON.optString("nativelanguage"),friJSON.optString("favouritesport"),friJSON.optString("favouritemovie"),friJSON.optString("favouriteunit"),friJSON.optString("currentjob"),friJSON.optString("monashemail"),friJSON.optString("password"),friJSON.optString("subscriptiondateandtime"));
                        Calendar calendar = Calendar.getInstance();
                        String subscriptiondateandtime = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR));
                        if (studentid1.getStudid() < studentid2.getStudid()){
                            Friendship sp = new Friendship(fsRecordsJSONArray.length()+1,subscriptiondateandtime,studentid2,studentid1);
                            RestClient.createFriendship(sp);
                            Toast.makeText(mContext, "Add successfully", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        if(studentid1.getStudid() > studentid2.getStudid()){
                            Friendship sp = new Friendship(fsRecordsJSONArray.length()+1,subscriptiondateandtime,studentid1,studentid2);
                            RestClient.createFriendship(sp);
                            Toast.makeText(mContext, "Add successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        if(studentid1.getStudid() == studentid2.getStudid()){
                            Toast.makeText(mContext, "You cannot add yourself.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }

    private void getFriendThenInit() {

        keywordList = getIntent().getStringExtra("keywordList");
        keywordResult = getIntent().getStringExtra("keywordResult");
        keywordResult = keywordResult.replace(" ", "%20");
        keywordListFinal = keywordList.replace(" ", "").toLowerCase();
        dialog = new ProgressDialog(this);
        dialog.show();
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return RestClient.findByAnyKeywords(params[0], params[1]);
            }

            @Override
            protected void onPostExecute(String students) {
                try {
                    dialog.hide();
                    jArray = new JSONArray(students);
                    init();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(keywordListFinal, keywordResult);
    }

    private void setText() {

        if (jArray.length() % 4 == 0) {
            wholePageNumber = jArray.length() / 4;
        } else {
            wholePageNumber = jArray.length() / 4 + 1;
        }
        int[] idArray = new int[]{R.id.fdtv1, R.id.fdtv2, R.id.fdtv3, R.id.fdtv4, R.id.fdtv5, R.id.fdtv6, R.id.fdtv7, R.id.fdtv8, R.id.fdtv9, R.id.fdtv10, R.id.fdtv11, R.id.fdtv12, R.id.fdtv13, R.id.fdtv14, R.id.fdtv15, R.id.fdtv16, R.id.fdtv17, R.id.fdtv18, R.id.fdtv19, R.id.fdtv20, R.id.fdtv21, R.id.fdtv22, R.id.fdtv23, R.id.fdtv24};
        int[] loArray = new int[]{R.id.lnlo1, R.id.lnlo2, R.id.lnlo3, R.id.lnlo4};
        for (int i = 0; i < 4; i++) {
            int flag = i + (nextButtonValue - 1) * 4;
            if (flag < jArray.length()) {
                try {
                    JSONObject studentjObject = jArray.getJSONObject(flag);
                    LinearLayout lol = (LinearLayout) findViewById(loArray[i]);
                    lol.setVisibility(View.VISIBLE);
                    TextView tv1 = (TextView) findViewById(idArray[6 * i + 1]);
                    tv1.setText(studentjObject.optString("firstname"));
                    TextView tv2 = (TextView) findViewById(idArray[6 * i + 3]);
                    tv2.setText(studentjObject.optString("surname"));
                    TextView tv3 = (TextView) findViewById(idArray[6 * i + 5]);
                    tv3.setText(studentjObject.optString("favouritemovie"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }


    }
}
