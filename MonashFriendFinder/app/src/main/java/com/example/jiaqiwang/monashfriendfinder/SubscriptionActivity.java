package com.example.jiaqiwang.monashfriendfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jiaqiwang.monashfriendfinder.DataStore.DBManager;
import com.example.jiaqiwang.monashfriendfinder.DataStore.StudentProfile;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.MD5;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.RestClient;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jiaqi Wang on 5/1/2017.
 */

public class SubscriptionActivity extends AppCompatActivity {

    private EditText studentIdEditText_subscription;
    private EditText monashEmailEditText_subscription;
    private EditText firstNameEditText_subscription;
    private EditText surNameEditText_subscription;
    private EditText passwordEditText_subscription;
    private EditText passwordConfirmEditText_subscription;
    private RadioGroup genderRadioGroup_subscription;
    private DatePicker dOBDatePicker_subscription;
    private RadioGroup studyModeRadioGroup_subscription;
    private EditText currentJobEditText_subscription;
    private Spinner courseSpinner_subscription;
    private EditText addressEditText_subscription;
    private Spinner suburbSpinner_subscription;
    private Spinner nationalitySpinner_subscription;
    private Spinner nativeLanguageSpinner_subscription;
    private Spinner favouriteSportSpinner_subscription;
    private EditText favouriteMovieEditText_subscription;
    private Spinner favouriteUnitSpinner_subscription;
    private Button submitButton_subscription;
    private boolean passValidation;
    private ProgressDialog dialog;

    private int year;
    private int monthOfYear;
    private int dayOfMonth;

    private String genderRadioButtonText;
    private String studyModeRadioButtonText;

    protected DBManager dbManager;

    private String[] favouritesSportArray;
    private String favouriteSportSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        init();
//        insertData();
    }

    private void init() {

        dbManager = new DBManager(this);

        studentIdEditText_subscription = (EditText) findViewById(R.id.studentIdEditText_subscription);
        monashEmailEditText_subscription = (EditText) findViewById(R.id.monashEmailEditText_subscription);
        firstNameEditText_subscription = (EditText) findViewById(R.id.firstNameEditText_subscription);
        surNameEditText_subscription = (EditText) findViewById(R.id.surNameEditText_subscription);
        passwordEditText_subscription = (EditText) findViewById(R.id.passwordEditText_subscription);
        passwordConfirmEditText_subscription = (EditText) findViewById(R.id.passwordConfirmEditText_subscription);
        addressEditText_subscription = (EditText) findViewById(R.id.addressEditText_subscription);
        favouriteMovieEditText_subscription = (EditText) findViewById(R.id.favouriteMovieEditText_subscription);
        currentJobEditText_subscription = (EditText) findViewById(R.id.currentJobEditText_subscription);

        genderRadioGroup_subscription = (RadioGroup) findViewById(R.id.genderRadioGroup_subscription);
        studyModeRadioGroup_subscription = (RadioGroup) findViewById(R.id.studyModeRadioGroup_subscription);

        dOBDatePicker_subscription = (DatePicker) findViewById(R.id.dOBDatePicker_subscription);
        Date date = new Date();
        long time = date.getTime();
        dOBDatePicker_subscription.setMaxDate(time);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dOBDatePicker_subscription.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker arg0, int yy, int mm,
                                      int dd) {
                SubscriptionActivity.this.year = yy;
                SubscriptionActivity.this.monthOfYear = mm + 1;
                SubscriptionActivity.this.dayOfMonth = dd;
            }
        });

        courseSpinner_subscription = (Spinner) findViewById(R.id.courseSpinner_subscription);
        suburbSpinner_subscription = (Spinner) findViewById(R.id.suburbSpinner_subscription);
        nationalitySpinner_subscription = (Spinner) findViewById(R.id.nationalitySpinner_subscription);
        nativeLanguageSpinner_subscription = (Spinner) findViewById(R.id.nativeLanguageSpinner_subscription);
        favouriteUnitSpinner_subscription = (Spinner) findViewById(R.id.favouriteUnitSpinner_subscription);

        favouriteSportSpinner_subscription = (Spinner) findViewById(R.id.favouriteSportSpinner_subscription);
        readData();
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, favouritesSportArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        favouriteSportSpinner_subscription.setAdapter(spinnerArrayAdapter);
        favouriteSportSpinner_subscription.setOnItemSelectedListener(new SelectSportOnItemSelectedListener());


        submitButton_subscription = (Button) findViewById(R.id.submitButton_subscription);
        submitButton_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < genderRadioGroup_subscription.getChildCount(); i++) {
                    RadioButton rd = (RadioButton) genderRadioGroup_subscription.getChildAt(i);
                    if (rd.isChecked()) {
                        genderRadioButtonText = rd.getText().toString();
                    }
                }
                for (int i = 0; i < studyModeRadioGroup_subscription.getChildCount(); i++) {
                    RadioButton rd = (RadioButton) studyModeRadioGroup_subscription.getChildAt(i);
                    if (rd.isChecked()) {
                        studyModeRadioButtonText = rd.getText().toString();
                    }
                }
                validate();
                if (passValidation) {
                    submit();
                }
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setMessage("Submitting...");

        passValidation = true;


    }

    private class SelectSportOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
            String sInfo = adapter.getItemAtPosition(position).toString();
            favouriteSportSelected = sInfo;
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            String sInfo = "Nothing selected";
            Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();

        }
    }

    public void insertData() {
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbManager.insertSport("1", "Badminton");
        dbManager.insertSport("2", "Basketball");
        dbManager.insertSport("3", "Football");
        dbManager.insertSport("4", "Table Tennis");
        dbManager.insertSport("5", "Swimming");
        dbManager.close();
    }

    public void readData() {
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor c = dbManager.getAllSports();
        int count = 0;
        if (c.moveToFirst()) {
            do {
                count = count + 1;
            } while (c.moveToNext());
        }
        String[] sportList = new String[count];
        if (c.moveToFirst()) {
            int i = 0;
            do {
                sportList[i] = c.getString(1);
                i = i + 1;
            } while (c.moveToNext());
        }
        favouritesSportArray = sportList;
        dbManager.close();
    }

    private void submit() {
        dialog.show();
        String studid = studentIdEditText_subscription.getText().toString();
        String firstname = firstNameEditText_subscription.getText().toString();
        String surname = surNameEditText_subscription.getText().toString();
        String dob = String.valueOf(dayOfMonth + "-" + monthOfYear + "-" + year);
        ;
        String gender = genderRadioButtonText;
        String course = courseSpinner_subscription.getSelectedItem().toString();
        String studymode = studyModeRadioButtonText;
        String address = addressEditText_subscription.getText().toString();
        String suburb = suburbSpinner_subscription.getSelectedItem().toString();
        String nationality = nationalitySpinner_subscription.getSelectedItem().toString();
        String nativelanguage = nativeLanguageSpinner_subscription.getSelectedItem().toString();
        String favouritesport = favouriteSportSelected;
        String favouritemovie = favouriteMovieEditText_subscription.getText().toString();
        String favouriteunit = favouriteUnitSpinner_subscription.getSelectedItem().toString();
        String currentjob = currentJobEditText_subscription.getText().toString();
        String monashemail = monashEmailEditText_subscription.getText().toString();
        String password = passwordEditText_subscription.getText().toString();
        Calendar calendar = Calendar.getInstance();
        String subscriptiondateandtime = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR));

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                StudentProfile sp = new StudentProfile(Integer.parseInt(params[0]), params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11], params[12], params[13], params[14], params[15], params[16], params[17]);
                RestClient.createStudentProfile(sp);
                return "Successfully registered";
            }

            @Override
            protected void onPostExecute(String response) {
                dialog.hide();
                Toast.makeText(SubscriptionActivity.this, response, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SubscriptionActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }.execute(studid, firstname, surname, dob, gender, course, studymode, address, suburb, nationality, nativelanguage, favouritesport, favouritemovie, favouriteunit, currentjob, monashemail, MD5.getMD5x100(password), subscriptiondateandtime);
    }

    private void validate() {
        if (studentIdEditText_subscription.getText().toString().trim().isEmpty()) {
            studentIdEditText_subscription.setError("Please enter your Student ID");
            passValidation = false;

        }
        if (!studentIdEditText_subscription.getText().toString().trim().matches("[0-9]{8}")) {
            studentIdEditText_subscription.setError("Please enter a correct Student ID");
            passValidation = false;
        }
        if (!studentIdEditText_subscription.getText().toString().trim().isEmpty() && studentIdEditText_subscription.getText().toString().trim().matches("[0-9]{8}")) {
            new AsyncTask<Integer, Void, String>() {
                @Override
                protected String doInBackground(Integer... params) {
                    return RestClient.findStudentById(params[0]);
                }

                @Override
                protected void onPostExecute(String student) {
                    if (student != "") {
                        studentIdEditText_subscription.setError("This ID has been registered. Please check your Student ID and try again.");
                        passValidation = false;
                    }
                }
            }.execute(Integer.parseInt(studentIdEditText_subscription.getText().toString()));
        }
        if (currentJobEditText_subscription.getText().toString().trim().isEmpty()) {
            currentJobEditText_subscription.setError("Input Unemployed if don't have a job currently");
            passValidation = false;
        }
        if (currentJobEditText_subscription.getText().toString().trim().length() > 20) {
            currentJobEditText_subscription.setError("Length of Current Job can not be more than 20");
            passValidation = false;
        }

        if (monashEmailEditText_subscription.getText().toString().trim().isEmpty()) {
            monashEmailEditText_subscription.setError("Please enter your Monash Email");
            passValidation = false;
        }
        if (!monashEmailEditText_subscription.getText().toString().trim().matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
            monashEmailEditText_subscription.setError("Please enter a correct Email");
            passValidation = false;
        }
        if (firstNameEditText_subscription.getText().toString().trim().isEmpty()) {
            firstNameEditText_subscription.setError("Please enter your First Name");
            passValidation = false;
        }
        if (firstNameEditText_subscription.getText().toString().trim().length() > 50) {
            firstNameEditText_subscription.setError("Length of First Name can not be more than 50");
            passValidation = false;
        }
        if (surNameEditText_subscription.getText().toString().trim().length() > 50) {
            surNameEditText_subscription.setError("Length of Surname can not be more than 50");
            passValidation = false;
        }
        if (surNameEditText_subscription.getText().toString().trim().isEmpty()) {
            surNameEditText_subscription.setError("Please enter your Surname");
            passValidation = false;
        }
        if (passwordEditText_subscription.getText().toString().trim().isEmpty()) {
            passwordEditText_subscription.setError("Please enter your Password");
            passValidation = false;
        }
        if (!passwordEditText_subscription.getText().toString().trim().matches("[a-zA-Z0-9]{6,16}$")) {
            passwordEditText_subscription.setError("The length of password must be between 6 - 16 and password must not have special characters");
            passValidation = false;
        }
        if (passwordConfirmEditText_subscription.getText().toString().trim().isEmpty()) {
            passwordConfirmEditText_subscription.setError("Please enter your Password");
            passValidation = false;
        }
        if (!passwordConfirmEditText_subscription.getText().toString().trim().equals(passwordEditText_subscription.getText().toString().trim())) {
            passwordConfirmEditText_subscription.setError("This password must be the same with the above one");
            passValidation = false;
        }
        if (addressEditText_subscription.getText().toString().trim().isEmpty()) {
            addressEditText_subscription.setError("Please enter your Address");
            passValidation = false;
        }
        if (addressEditText_subscription.getText().toString().trim().length() > 50) {
            addressEditText_subscription.setError("Length of address must be less than 50");
            passValidation = false;
        }
        if (favouriteMovieEditText_subscription.getText().toString().trim().isEmpty()) {
            favouriteMovieEditText_subscription.setError("Please enter your Favourite Movie");
            passValidation = false;
        }
        if (favouriteMovieEditText_subscription.getText().toString().trim().length() > 50) {
            favouriteMovieEditText_subscription.setError("Length of favourite movie must be less than 50");
            passValidation = false;
        }

    }

}
