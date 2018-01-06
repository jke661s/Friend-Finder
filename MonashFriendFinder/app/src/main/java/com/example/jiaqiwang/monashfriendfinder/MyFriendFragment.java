package com.example.jiaqiwang.monashfriendfinder;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiaqiwang.monashfriendfinder.DataStore.Friendship;
import com.example.jiaqiwang.monashfriendfinder.DataStore.StudentProfile;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.RestClient;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Jiaqi Wang on 5/5/2017.
 */

public class MyFriendFragment extends Fragment {
    View vMyFriend;
    private Context mContext;
    private SharedHelper sh;
    private String email;
    private JSONObject usrJSON;
    private JSONArray fsRecordsJSONArray;
    private ProgressDialog dialog;
    private JSONArray jArray;
    private int friNumber = 0;
    private List<JSONObject> friList;
    private int wholePageNumber;
    private int lastButtonValue;
    private int nextButtonValue;
    private Button lastButton;
    private Button nextButton;
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
    private JSONObject friJSON;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        vMyFriend = inflater.inflate(R.layout.fragment_my_friend, container, false);
        mContext = vMyFriend.getContext();
        sh = new SharedHelper(mContext);
        email = sh.read().get("username");
        friList = new ArrayList<>();
        getUserInfo();

        return vMyFriend;


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

                    for (int i = 0; i < fsRecordsJSONArray.length(); i++) {
                        if (usrJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(i).optJSONObject("studentid1").optInt("studid") || usrJSON.optInt("studid") == fsRecordsJSONArray.getJSONObject(i).optJSONObject("studentid2").optInt("studid")) {
                            try {
                                if (fsRecordsJSONArray.getJSONObject(i).getString("endingdate") == null) {

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                JSONObject jObj = fsRecordsJSONArray.getJSONObject(i);
                                friList.add(jObj);
                            }
                            ;
                        }
                    }

                    init();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private void init() {
        lastButtonValue = 0;
        nextButtonValue = 1;

        dialog = new ProgressDialog(vMyFriend.getContext());
        setText();

        bt1 = (Button) vMyFriend.findViewById(R.id.bt1qq);
        bt2 = (Button) vMyFriend.findViewById(R.id.bt2qq);
        bt3 = (Button) vMyFriend.findViewById(R.id.bt3qq);
        bt4 = (Button) vMyFriend.findViewById(R.id.bt4qq);
        bt5 = (Button) vMyFriend.findViewById(R.id.bt5qq);
        bt6 = (Button) vMyFriend.findViewById(R.id.bt6qq);
        bt7 = (Button) vMyFriend.findViewById(R.id.bt7qq);
        bt8 = (Button) vMyFriend.findViewById(R.id.bt8qq);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 4 * nextButtonValue - 4;
                alert = null;
                builder = new AlertDialog.Builder(vMyFriend.getContext());
                JSONObject fp = friList.get(i);
                JSONObject studentjObject;
                if(usrJSON.optInt("studid") == fp.optJSONObject("studentid1").optInt("studid")){
                    studentjObject = fp.optJSONObject("studentid2");
                }else{
                    studentjObject = fp.optJSONObject("studentid1");
                }


                try {
                    alert = builder.setTitle("Student Detail: ")
                            .setMessage("First Name:       " + studentjObject.optString("firstname") +
                                    "\nLast Name:        " + studentjObject.optString("surname") +
                                    "\nFavourite Movie:  " + studentjObject.optString("favouritemovie") +
                                    "\nGender:           " + studentjObject.optString("gender") +
                                    "\nCourse:           " + studentjObject.optString("course") +
                                    "\nDate of Birth:    " + studentjObject.optString("dob") +
                                    "\nStudy Mode:       " + studentjObject.optString("studymode") +
                                    "\nAddress:          " + studentjObject.optString("address") +
                                    "\nSuburb:           " + studentjObject.optString("suburb") +
                                    "\nNationality:      " + studentjObject.optString("nationality") +
                                    "\nNative Language:  " + studentjObject.optString("nativelanguage") +
                                    "\nFavourite Sport:  " + studentjObject.optString("favouritesport") +
                                    "\nFavourite Unit:   " + studentjObject.optString("favouriteunit") +
                                    "\nCurrent Job:      " + studentjObject.optString("currentjob")
                            )

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert.show();
            }
        });

        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 4 * nextButtonValue - 1;
                alert = null;
                builder = new AlertDialog.Builder(vMyFriend.getContext());
                JSONObject fp = friList.get(i);
                JSONObject studentjObject;
                if(usrJSON.optInt("studid") == fp.optJSONObject("studentid1").optInt("studid")){
                    studentjObject = fp.optJSONObject("studentid2");
                }else{
                    studentjObject = fp.optJSONObject("studentid1");
                }


                try {
                    alert = builder.setTitle("Student Detail: ")
                            .setMessage("First Name:       " + studentjObject.optString("firstname") +
                                    "\nLast Name:        " + studentjObject.optString("surname") +
                                    "\nFavourite Movie:  " + studentjObject.optString("favouritemovie") +
                                    "\nGender:           " + studentjObject.optString("gender") +
                                    "\nCourse:           " + studentjObject.optString("course") +
                                    "\nDate of Birth:    " + studentjObject.optString("dob") +
                                    "\nStudy Mode:       " + studentjObject.optString("studymode") +
                                    "\nAddress:          " + studentjObject.optString("address") +
                                    "\nSuburb:           " + studentjObject.optString("suburb") +
                                    "\nNationality:      " + studentjObject.optString("nationality") +
                                    "\nNative Language:  " + studentjObject.optString("nativelanguage") +
                                    "\nFavourite Sport:  " + studentjObject.optString("favouritesport") +
                                    "\nFavourite Unit:   " + studentjObject.optString("favouriteunit") +
                                    "\nCurrent Job:      " + studentjObject.optString("currentjob")
                            )

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert.show();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 4 * nextButtonValue - 3;
                alert = null;
                builder = new AlertDialog.Builder(vMyFriend.getContext());
                JSONObject fp = friList.get(i);
                JSONObject studentjObject;
                if(usrJSON.optInt("studid") == fp.optJSONObject("studentid1").optInt("studid")){
                    studentjObject = fp.optJSONObject("studentid2");
                }else{
                    studentjObject = fp.optJSONObject("studentid1");
                }


                try {
                    alert = builder.setTitle("Student Detail: ")
                            .setMessage("First Name:       " + studentjObject.optString("firstname") +
                                    "\nLast Name:        " + studentjObject.optString("surname") +
                                    "\nFavourite Movie:  " + studentjObject.optString("favouritemovie") +
                                    "\nGender:           " + studentjObject.optString("gender") +
                                    "\nCourse:           " + studentjObject.optString("course") +
                                    "\nDate of Birth:    " + studentjObject.optString("dob") +
                                    "\nStudy Mode:       " + studentjObject.optString("studymode") +
                                    "\nAddress:          " + studentjObject.optString("address") +
                                    "\nSuburb:           " + studentjObject.optString("suburb") +
                                    "\nNationality:      " + studentjObject.optString("nationality") +
                                    "\nNative Language:  " + studentjObject.optString("nativelanguage") +
                                    "\nFavourite Sport:  " + studentjObject.optString("favouritesport") +
                                    "\nFavourite Unit:   " + studentjObject.optString("favouriteunit") +
                                    "\nCurrent Job:      " + studentjObject.optString("currentjob")
                            )

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert.show();
            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 4 * nextButtonValue - 2;
                alert = null;
                builder = new AlertDialog.Builder(vMyFriend.getContext());
                JSONObject fp = friList.get(i);
                JSONObject studentjObject;
                if(usrJSON.optInt("studid") == fp.optJSONObject("studentid1").optInt("studid")){
                    studentjObject = fp.optJSONObject("studentid2");
                }else{
                    studentjObject = fp.optJSONObject("studentid1");
                }


                try {
                    alert = builder.setTitle("Student Detail: ")
                            .setMessage("First Name:       " + studentjObject.optString("firstname") +
                                    "\nLast Name:        " + studentjObject.optString("surname") +
                                    "\nFavourite Movie:  " + studentjObject.optString("favouritemovie") +
                                    "\nGender:           " + studentjObject.optString("gender") +
                                    "\nCourse:           " + studentjObject.optString("course") +
                                    "\nDate of Birth:    " + studentjObject.optString("dob") +
                                    "\nStudy Mode:       " + studentjObject.optString("studymode") +
                                    "\nAddress:          " + studentjObject.optString("address") +
                                    "\nSuburb:           " + studentjObject.optString("suburb") +
                                    "\nNationality:      " + studentjObject.optString("nationality") +
                                    "\nNative Language:  " + studentjObject.optString("nativelanguage") +
                                    "\nFavourite Sport:  " + studentjObject.optString("favouritesport") +
                                    "\nFavourite Unit:   " + studentjObject.optString("favouriteunit") +
                                    "\nCurrent Job:      " + studentjObject.optString("currentjob")
                            )

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert.show();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFriInfo(1);
                StudentProfile studentid1 = new StudentProfile(usrJSON.optInt("studid"),usrJSON.optString("firstname"),usrJSON.optString("surname"),usrJSON.optString("dob"),usrJSON.optString("gender"),usrJSON.optString("course"),usrJSON.optString("studymode"),usrJSON.optString("address"),usrJSON.optString("suburb"),usrJSON.optString("nationality"),usrJSON.optString("nativelanguage"),usrJSON.optString("favouritesport"),usrJSON.optString("favouritemovie"),usrJSON.optString("favouriteunit"),usrJSON.optString("currentjob"),usrJSON.optString("monashemail"),usrJSON.optString("password"),usrJSON.optString("subscriptiondateandtime"));
                StudentProfile studentid2 = new StudentProfile(friJSON.optInt("studid"),friJSON.optString("firstname"),friJSON.optString("surname"),friJSON.optString("dob"),friJSON.optString("gender"),friJSON.optString("course"),friJSON.optString("studymode"),friJSON.optString("address"),friJSON.optString("suburb"),friJSON.optString("nationality"),friJSON.optString("nativelanguage"),friJSON.optString("favouritesport"),friJSON.optString("favouritemovie"),friJSON.optString("favouriteunit"),friJSON.optString("currentjob"),friJSON.optString("monashemail"),friJSON.optString("password"),friJSON.optString("subscriptiondateandtime"));
                Calendar calendar = Calendar.getInstance();
                String endingDate = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR));
                int i = 4 * nextButtonValue - 4;
                JSONObject fp = friList.get(i);
                String startingDate = fp.optString("startingdate");
                int id = fp.optInt("friendshipid");
                if (studentid1.getStudid() < studentid2.getStudid()){
                    Friendship sp = new Friendship(id,startingDate,endingDate,studentid2,studentid1);
                    RestClient.deleteFriendship(sp,id);
                    Toast.makeText(mContext, "Delete successfully", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
                }

                if(studentid1.getStudid() > studentid2.getStudid()){
                    Friendship sp = new Friendship(id,startingDate,endingDate,studentid1,studentid2);
                    RestClient.deleteFriendship(sp,id);
                    Toast.makeText(mContext, "Delete successfully", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
                }

            }
        });


        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFriInfo(2);
                StudentProfile studentid1 = new StudentProfile(usrJSON.optInt("studid"),usrJSON.optString("firstname"),usrJSON.optString("surname"),usrJSON.optString("dob"),usrJSON.optString("gender"),usrJSON.optString("course"),usrJSON.optString("studymode"),usrJSON.optString("address"),usrJSON.optString("suburb"),usrJSON.optString("nationality"),usrJSON.optString("nativelanguage"),usrJSON.optString("favouritesport"),usrJSON.optString("favouritemovie"),usrJSON.optString("favouriteunit"),usrJSON.optString("currentjob"),usrJSON.optString("monashemail"),usrJSON.optString("password"),usrJSON.optString("subscriptiondateandtime"));
                StudentProfile studentid2 = new StudentProfile(friJSON.optInt("studid"),friJSON.optString("firstname"),friJSON.optString("surname"),friJSON.optString("dob"),friJSON.optString("gender"),friJSON.optString("course"),friJSON.optString("studymode"),friJSON.optString("address"),friJSON.optString("suburb"),friJSON.optString("nationality"),friJSON.optString("nativelanguage"),friJSON.optString("favouritesport"),friJSON.optString("favouritemovie"),friJSON.optString("favouriteunit"),friJSON.optString("currentjob"),friJSON.optString("monashemail"),friJSON.optString("password"),friJSON.optString("subscriptiondateandtime"));
                Calendar calendar = Calendar.getInstance();
                String endingDate = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR));
                int i = 4 * nextButtonValue - 3;
                JSONObject fp = friList.get(i);
                String startingDate = fp.optString("startingdate");
                int id = fp.optInt("friendshipid");
                if (studentid1.getStudid() < studentid2.getStudid()){
                    Friendship sp = new Friendship(id,startingDate,endingDate,studentid2,studentid1);
                    RestClient.deleteFriendship(sp,id);
                    Toast.makeText(mContext, "Delete successfully", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
                }

                if(studentid1.getStudid() > studentid2.getStudid()){
                    Friendship sp = new Friendship(id,startingDate,endingDate,studentid1,studentid2);
                    RestClient.deleteFriendship(sp,id);
                    Toast.makeText(mContext, "Delete successfully", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
                }

            }
        });

        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFriInfo(3);
                StudentProfile studentid1 = new StudentProfile(usrJSON.optInt("studid"),usrJSON.optString("firstname"),usrJSON.optString("surname"),usrJSON.optString("dob"),usrJSON.optString("gender"),usrJSON.optString("course"),usrJSON.optString("studymode"),usrJSON.optString("address"),usrJSON.optString("suburb"),usrJSON.optString("nationality"),usrJSON.optString("nativelanguage"),usrJSON.optString("favouritesport"),usrJSON.optString("favouritemovie"),usrJSON.optString("favouriteunit"),usrJSON.optString("currentjob"),usrJSON.optString("monashemail"),usrJSON.optString("password"),usrJSON.optString("subscriptiondateandtime"));
                StudentProfile studentid2 = new StudentProfile(friJSON.optInt("studid"),friJSON.optString("firstname"),friJSON.optString("surname"),friJSON.optString("dob"),friJSON.optString("gender"),friJSON.optString("course"),friJSON.optString("studymode"),friJSON.optString("address"),friJSON.optString("suburb"),friJSON.optString("nationality"),friJSON.optString("nativelanguage"),friJSON.optString("favouritesport"),friJSON.optString("favouritemovie"),friJSON.optString("favouriteunit"),friJSON.optString("currentjob"),friJSON.optString("monashemail"),friJSON.optString("password"),friJSON.optString("subscriptiondateandtime"));
                Calendar calendar = Calendar.getInstance();
                String endingDate = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR));
                int i = 4 * nextButtonValue - 2;
                JSONObject fp = friList.get(i);
                String startingDate = fp.optString("startingdate");
                int id = fp.optInt("friendshipid");
                if (studentid1.getStudid() < studentid2.getStudid()){
                    Friendship sp = new Friendship(id,startingDate,endingDate,studentid2,studentid1);
                    RestClient.deleteFriendship(sp,id);
                    Toast.makeText(mContext, "Delete successfully", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
                }

                if(studentid1.getStudid() > studentid2.getStudid()){
                    Friendship sp = new Friendship(id,startingDate,endingDate,studentid1,studentid2);
                    RestClient.deleteFriendship(sp,id);
                    Toast.makeText(mContext, "Delete successfully", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
                }

            }
        });

        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFriInfo(4);
                StudentProfile studentid1 = new StudentProfile(usrJSON.optInt("studid"),usrJSON.optString("firstname"),usrJSON.optString("surname"),usrJSON.optString("dob"),usrJSON.optString("gender"),usrJSON.optString("course"),usrJSON.optString("studymode"),usrJSON.optString("address"),usrJSON.optString("suburb"),usrJSON.optString("nationality"),usrJSON.optString("nativelanguage"),usrJSON.optString("favouritesport"),usrJSON.optString("favouritemovie"),usrJSON.optString("favouriteunit"),usrJSON.optString("currentjob"),usrJSON.optString("monashemail"),usrJSON.optString("password"),usrJSON.optString("subscriptiondateandtime"));
                StudentProfile studentid2 = new StudentProfile(friJSON.optInt("studid"),friJSON.optString("firstname"),friJSON.optString("surname"),friJSON.optString("dob"),friJSON.optString("gender"),friJSON.optString("course"),friJSON.optString("studymode"),friJSON.optString("address"),friJSON.optString("suburb"),friJSON.optString("nationality"),friJSON.optString("nativelanguage"),friJSON.optString("favouritesport"),friJSON.optString("favouritemovie"),friJSON.optString("favouriteunit"),friJSON.optString("currentjob"),friJSON.optString("monashemail"),friJSON.optString("password"),friJSON.optString("subscriptiondateandtime"));
                Calendar calendar = Calendar.getInstance();
                String endingDate = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR));
                int i = 4 * nextButtonValue - 1;
                JSONObject fp = friList.get(i);
                String startingDate = fp.optString("startingdate");
                int id = fp.optInt("friendshipid");
                if (studentid1.getStudid() < studentid2.getStudid()){
                    Friendship sp = new Friendship(id,startingDate,endingDate,studentid2,studentid1);
                    RestClient.deleteFriendship(sp,id);
                    Toast.makeText(mContext, "Delete successfully", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
                }

                if(studentid1.getStudid() > studentid2.getStudid()){
                    Friendship sp = new Friendship(id,startingDate,endingDate,studentid1,studentid2);
                    RestClient.deleteFriendship(sp,id);
                    Toast.makeText(mContext, "Delete successfully", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
                }

            }
        });







        lastButton = (Button) vMyFriend.findViewById(R.id.lastButtonqq);
        nextButton = (Button) vMyFriend.findViewById(R.id.nextButtonqq);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextButtonValue < wholePageNumber) {
                    nextButtonValue = nextButtonValue + 1;
                    lastButtonValue = lastButtonValue + 1;
                    setText();
                } else {
                    Toast.makeText(vMyFriend.getContext(), "This is the last page", Toast.LENGTH_SHORT).show();
                }
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
                    Toast.makeText(vMyFriend.getContext(), "This is the first page", Toast.LENGTH_SHORT).show();
                }
            }
        });







    }

    private void getFriInfo(int n) {
        int i = 4 * nextButtonValue - 5 + n;

        try {
            friJSON = friList.get(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setText() {
        if (friList.size() % 4 == 0) {
            wholePageNumber = friList.size() / 4;
        } else {
            wholePageNumber = friList.size() / 4 + 1;
        }

        int[] idArray = new int[]{R.id.fdtv1qq, R.id.fdtv2qq, R.id.fdtv3qq, R.id.fdtv4qq, R.id.fdtv5qq, R.id.fdtv6qq, R.id.fdtv7qq, R.id.fdtv8qq, R.id.fdtv9qq, R.id.fdtv10qq, R.id.fdtv11qq, R.id.fdtv12qq, R.id.fdtv13qq, R.id.fdtv14qq, R.id.fdtv15qq, R.id.fdtv16qq, R.id.fdtv17qq, R.id.fdtv18qq, R.id.fdtv19qq, R.id.fdtv20qq, R.id.fdtv21qq, R.id.fdtv22qq, R.id.fdtv23qq, R.id.fdtv24qq};
        int[] loArray = new int[]{R.id.lnlo1qq, R.id.lnlo2qq, R.id.lnlo3qq, R.id.lnlo4qq};


        for (int i = 0; i < 4; i++) {
            int flag = i + (nextButtonValue - 1) * 4;
            if (flag < friList.size()) {
                try {
                    JSONObject fp = friList.get(flag);
                    JSONObject studentjObject;
                    if(usrJSON.optInt("studid") == fp.optJSONObject("studentid1").optInt("studid")){
                        studentjObject = fp.optJSONObject("studentid2");
                    }else{
                        studentjObject = fp.optJSONObject("studentid1");
                    }


                    LinearLayout lol = (LinearLayout) vMyFriend.findViewById(loArray[i]);
                    lol.setVisibility(View.VISIBLE);
                    TextView tv1 = (TextView) vMyFriend.findViewById(idArray[6 * i + 1]);
                    tv1.setText(studentjObject.optString("firstname"));
                    TextView tv2 = (TextView) vMyFriend.findViewById(idArray[6 * i + 3]);
                    tv2.setText(studentjObject.optString("surname"));
                    TextView tv3 = (TextView) vMyFriend.findViewById(idArray[6 * i + 5]);
                    tv3.setText(studentjObject.optString("favouritemovie"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }


}
