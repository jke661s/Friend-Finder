package com.example.jiaqiwang.monashfriendfinder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiaqiwang.monashfriendfinder.ServiceUse.RestClient;

import org.json.JSONArray;


/**
 * Created by Jiaqi Wang on 5/3/2017.
 */

public class FindFriendActivity extends AppCompatActivity {
    private Spinner gender_ff;
    private Spinner course_ff;
    private Spinner studyMode_ff;
    private Spinner suburb_ff;
    private Spinner nationality_ff;
    private Spinner nativelanguage_ff;
    private Spinner favouritesport_ff;
    private Spinner favouriteunit_ff;
    private LinearLayout lo1;
    private LinearLayout lo2;
    private LinearLayout lo3;
    private LinearLayout lo4;
    private LinearLayout lo5;
    private LinearLayout lo6;
    private LinearLayout lo7;
    private LinearLayout lo8;
    private TextView lo1tv;
    private TextView lo2tv;
    private TextView lo3tv;
    private TextView lo4tv;
    private TextView lo5tv;
    private TextView lo6tv;
    private TextView lo7tv;
    private TextView lo8tv;
    private Button goToFind;
    private String keywordResult;
    private LinearLayout loPapa;
    private String[] keywordListArray;
    private String keywordList;
    private ProgressDialog dialog;
    private JSONArray jArray;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findfriend);
        init();
    }

    public void init(){
        gender_ff = (Spinner) findViewById(R.id.gender_ff);
        course_ff = (Spinner) findViewById(R.id.course_ff);
        studyMode_ff = (Spinner) findViewById(R.id.studyMode_ff);
        suburb_ff = (Spinner) findViewById(R.id.suburb_ff);
        nationality_ff = (Spinner) findViewById(R.id.nationality_ff);
        nativelanguage_ff = (Spinner) findViewById(R.id.nativelanguage_ff);
        favouritesport_ff = (Spinner) findViewById(R.id.favouritesport_ff);
        favouriteunit_ff = (Spinner) findViewById(R.id.favouriteunit_ff);
        lo1 = (LinearLayout) findViewById(R.id.lo1);
        lo2 = (LinearLayout) findViewById(R.id.lo2);
        lo3 = (LinearLayout) findViewById(R.id.lo3);
        lo4 = (LinearLayout) findViewById(R.id.lo4);
        lo5 = (LinearLayout) findViewById(R.id.lo5);
        lo6 = (LinearLayout) findViewById(R.id.lo6);
        lo7 = (LinearLayout) findViewById(R.id.lo7);
        lo8 = (LinearLayout) findViewById(R.id.lo8);
        lo1tv = (TextView) findViewById(R.id.lo1tv);
        lo2tv = (TextView) findViewById(R.id.lo2tv);
        lo3tv = (TextView) findViewById(R.id.lo3tv);
        lo4tv = (TextView) findViewById(R.id.lo4tv);
        lo5tv = (TextView) findViewById(R.id.lo5tv);
        lo6tv = (TextView) findViewById(R.id.lo6tv);
        lo7tv = (TextView) findViewById(R.id.lo7tv);
        lo8tv = (TextView) findViewById(R.id.lo8tv);
        goToFind = (Button) findViewById(R.id.goToFind);
        loPapa = (LinearLayout) findViewById(R.id.loPapa) ;
        keywordResult = "";
        keywordList = getIntent().getStringExtra("keywordList");



        keywordListArray = getIntent().getStringArrayExtra("flag");
        for (int i = 0; i < keywordListArray.length; i++) {
            switch (keywordListArray[i]) {
                case "Gender":
                    lo1.setVisibility(View.VISIBLE);
                    break;
                case "Course":
                    lo2.setVisibility(View.VISIBLE);
                    break;
                case "Study Mode":
                    lo3.setVisibility(View.VISIBLE);
                    break;
                case "Suburb":
                    lo4.setVisibility(View.VISIBLE);
                    break;
                case "Nationality":
                    lo5.setVisibility(View.VISIBLE);
                    break;
                case "Native Language":
                    lo6.setVisibility(View.VISIBLE);
                    break;
                case "Favourite Sport":
                    lo7.setVisibility(View.VISIBLE);
                    break;
                case "Favourite Unit":
                    lo8.setVisibility(View.VISIBLE);
                    break;
            }
        }




        goToFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String one = gender_ff.getSelectedItem().toString();
                String two = course_ff.getSelectedItem().toString();
                String three = studyMode_ff.getSelectedItem().toString();
                String four = suburb_ff.getSelectedItem().toString();
                String five = nationality_ff.getSelectedItem().toString();
                String six = nativelanguage_ff.getSelectedItem().toString();
                String seven = favouritesport_ff.getSelectedItem().toString();
                String eight = favouriteunit_ff.getSelectedItem().toString();
                if(!one.equals("")){
                    keywordResult = keywordResult + "," + one;
                };
                if(!two.equals("")){
                    keywordResult = keywordResult + "," + two;
                };
                if(!three.equals("")){
                    keywordResult = keywordResult + "," + three;
                };
                if(!four.equals("")){
                    keywordResult = keywordResult + "," + four;
                };
                if(!five.equals("")){
                    keywordResult = keywordResult + "," + five;
                };
                if(!six.equals("")){
                    keywordResult = keywordResult + "," + six;
                };
                if(!seven.equals("")){
                    keywordResult = keywordResult + "," + seven;
                };
                if(!eight.equals("")){
                    keywordResult = keywordResult + "," + eight;
                };

                int keywordNumber = keywordListArray.length;
                if(!keywordResult.equals("")) {
                    keywordResult = keywordResult.substring(1,keywordResult.length());
                    int resultNumber = keywordResult.split(",").length;
                    if (keywordNumber == resultNumber) {

                        getFriend();
                    } else {
                        keywordResult = "";
                        Toast.makeText(FindFriendActivity.this, "Please select the value", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    keywordResult = "";
                    Toast.makeText(FindFriendActivity.this, "Please select the value", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void getFriend(){
        keywordList = keywordList.replace(" ", "").toLowerCase();
        keywordResult = keywordResult.replace(" ", "%20");
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

                    if(jArray.length() != 0 ){
                        Intent intent = new Intent(FindFriendActivity.this, FindResultActivity.class);
                        intent.putExtra("keywordList",keywordList);
                        intent.putExtra("keywordResult",keywordResult);
                        keywordResult = "";
                        startActivity(intent);
                    }else{
                        keywordResult = "";
                        alert = null;
                        builder = new AlertDialog.Builder(FindFriendActivity.this);
                        alert = builder.setTitle("System Information: ")
                                .setMessage("There are no student in the situation you choose,\nTouch OK to set the situation again.")

                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create();
                        alert.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(keywordList, keywordResult);
    }
}
