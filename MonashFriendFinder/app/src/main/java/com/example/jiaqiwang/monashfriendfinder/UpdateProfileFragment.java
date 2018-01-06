package com.example.jiaqiwang.monashfriendfinder;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.SharedHelper;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jiaqi Wang on 5/2/2017.
 */

public class UpdateProfileFragment extends Fragment {
    View vUpdateProfile;
    private EditText studentIdEditText_updateProfile;
    private EditText monashEmailEditText_updateProfile;
    private EditText firstNameEditText_updateProfile;
    private EditText surNameEditText_updateProfile;
    private EditText passwordEditText_updateProfile;
    private EditText passwordConfirmEditText_updateProfile;
    private RadioGroup genderRadioGroup_updateProfile;
    private DatePicker dOBDatePicker_updateProfile;
    private RadioGroup studyModeRadioGroup_updateProfile;
    private EditText currentJobEditText_updateProfile;
    private Spinner courseSpinner_updateProfile;
    private EditText addressEditText_updateProfile;
    private Spinner suburbSpinner_updateProfile;
    private Spinner nationalitySpinner_updateProfile;
    private Spinner nativeLanguageSpinner_updateProfile;
    private Spinner favouriteSportSpinner_updateProfile;
    private EditText favouriteMovieEditText_updateProfile;
    private Spinner favouriteUnitSpinner_updateProfile;
    private Button  submitButton_updateProfile;

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
    private SharedHelper sh;
    private JSONObject jsStudent;
    private String studentEmail;
    private String studid;
    private String subscribedate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vUpdateProfile = inflater.inflate(R.layout.fragment_update_profile, container, false);
        sh = new SharedHelper(vUpdateProfile.getContext());
        studentEmail = sh.read().get("username");
        getStudentProfileThenInit();
        return vUpdateProfile;
    }
    private void init() {

        dbManager = new DBManager(vUpdateProfile.getContext());

        studentIdEditText_updateProfile = (EditText) vUpdateProfile.findViewById(R.id.studentIdEditText_updateProfile);
        studentIdEditText_updateProfile.setEnabled(false);
        studentIdEditText_updateProfile.setText(studid);
        monashEmailEditText_updateProfile = (EditText) vUpdateProfile.findViewById(R.id.monashEmailEditText_updateProfile);
        monashEmailEditText_updateProfile.setEnabled(false);
        monashEmailEditText_updateProfile.setText(studentEmail);
        firstNameEditText_updateProfile = (EditText) vUpdateProfile.findViewById(R.id.firstNameEditText_updateProfile);
        surNameEditText_updateProfile = (EditText) vUpdateProfile.findViewById(R.id.surNameEditText_updateProfile);
        passwordEditText_updateProfile = (EditText) vUpdateProfile.findViewById(R.id.passwordEditText_updateProfile);
        passwordConfirmEditText_updateProfile = (EditText) vUpdateProfile.findViewById(R.id.passwordConfirmEditText_updateProfile);
        addressEditText_updateProfile = (EditText) vUpdateProfile.findViewById(R.id.addressEditText_updateProfile);
        favouriteMovieEditText_updateProfile = (EditText) vUpdateProfile.findViewById(R.id.favouriteMovieEditText_updateProfile);
        currentJobEditText_updateProfile = (EditText) vUpdateProfile.findViewById(R.id.currentJobEditText_updateProfile);

        genderRadioGroup_updateProfile = (RadioGroup) vUpdateProfile.findViewById(R.id.genderRadioGroup_updateProfile);
        for (int i = 0; i < genderRadioGroup_updateProfile.getChildCount(); i++) {
            RadioButton rd = (RadioButton) genderRadioGroup_updateProfile.getChildAt(i);
            if (rd.isChecked()) {
                genderRadioButtonText = rd.getText().toString();
            }
        }
        studyModeRadioGroup_updateProfile = (RadioGroup) vUpdateProfile.findViewById(R.id.studyModeRadioGroup_updateProfile);
        for (int i = 0; i < studyModeRadioGroup_updateProfile.getChildCount(); i++) {
            RadioButton rd = (RadioButton) studyModeRadioGroup_updateProfile.getChildAt(i);
            if (rd.isChecked()) {
                studyModeRadioButtonText = rd.getText().toString();
            }
        }

        dOBDatePicker_updateProfile = (DatePicker) vUpdateProfile.findViewById(R.id.dOBDatePicker_updateProfile);
        Date date = new Date();
        long time = date.getTime();
        dOBDatePicker_updateProfile.setMaxDate(time);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dOBDatePicker_updateProfile.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker arg0, int yy, int mm,
                                      int dd) {
                UpdateProfileFragment.this.year = yy;
                UpdateProfileFragment.this.monthOfYear = mm + 1;
                UpdateProfileFragment.this.dayOfMonth = dd;
            }
        });

        courseSpinner_updateProfile = (Spinner) vUpdateProfile.findViewById(R.id.courseSpinner_updateProfile);
        suburbSpinner_updateProfile = (Spinner) vUpdateProfile.findViewById(R.id.suburbSpinner_updateProfile);
        nationalitySpinner_updateProfile = (Spinner) vUpdateProfile.findViewById(R.id.nationalitySpinner_updateProfile);
        nativeLanguageSpinner_updateProfile = (Spinner) vUpdateProfile.findViewById(R.id.nativeLanguageSpinner_updateProfile);
        favouriteUnitSpinner_updateProfile = (Spinner) vUpdateProfile.findViewById(R.id.favouriteUnitSpinner_updateProfile);

        favouriteSportSpinner_updateProfile = (Spinner) vUpdateProfile.findViewById(R.id.favouriteSportSpinner_updateProfile);
        readData();
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(vUpdateProfile.getContext(), android.R.layout.simple_spinner_item, favouritesSportArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        favouriteSportSpinner_updateProfile.setAdapter(spinnerArrayAdapter);
        favouriteSportSpinner_updateProfile.setOnItemSelectedListener(new SelectSportOnItemSelectedListener());


        submitButton_updateProfile = (Button) vUpdateProfile.findViewById(R.id.submitButton_updateProfile);
        submitButton_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                validate();
                if (passValidation) {
                    submit();
                }
            }
        });

        dialog = new ProgressDialog(vUpdateProfile.getContext());
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
            Toast.makeText(vUpdateProfile.getContext(), sInfo, Toast.LENGTH_LONG).show();

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
                    studid = jsStudent.optString("studid");
                    subscribedate = jsStudent.optString("subscriptiondateandtime");
                    init();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(studentEmail);
    }


    public void readData() {
        try {
            dbManager.open();
        } catch (Exception e) {
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

    private void validate() {

        if (currentJobEditText_updateProfile.getText().toString().trim().isEmpty()) {
            currentJobEditText_updateProfile.setError("Input Unemployed if don't have a job currently");
            passValidation = false;
        }
        if (currentJobEditText_updateProfile.getText().toString().trim().length() > 20) {
            currentJobEditText_updateProfile.setError("Length of Current Job can not be more than 20");
            passValidation = false;
        }
        if (firstNameEditText_updateProfile.getText().toString().trim().isEmpty()) {
            firstNameEditText_updateProfile.setError("Please enter your First Name");
            passValidation = false;
        }
        if (firstNameEditText_updateProfile.getText().toString().trim().length() > 50) {
            firstNameEditText_updateProfile.setError("Length of First Name can not be more than 50");
            passValidation = false;
        }
        if (surNameEditText_updateProfile.getText().toString().trim().length() > 50) {
            surNameEditText_updateProfile.setError("Length of Surname can not be more than 50");
            passValidation = false;
        }
        if (surNameEditText_updateProfile.getText().toString().trim().isEmpty()) {
            surNameEditText_updateProfile.setError("Please enter your Surname");
            passValidation = false;
        }
        if (passwordEditText_updateProfile.getText().toString().trim().isEmpty()) {
            passwordEditText_updateProfile.setError("Please enter your Password");
            passValidation = false;
        }
        if (!passwordEditText_updateProfile.getText().toString().trim().matches("[a-zA-Z0-9]{6,16}$")) {
            passwordEditText_updateProfile.setError("The length of password must be between 6 - 16 and password must not have special characters");
            passValidation = false;
        }
        if (passwordConfirmEditText_updateProfile.getText().toString().trim().isEmpty()) {
            passwordConfirmEditText_updateProfile.setError("Please enter your Password");
            passValidation = false;
        }
        if (!passwordConfirmEditText_updateProfile.getText().toString().trim().equals(passwordConfirmEditText_updateProfile.getText().toString().trim())) {
            passwordConfirmEditText_updateProfile.setError("This password must be the same with the above one");
            passValidation = false;
        }
        if (addressEditText_updateProfile.getText().toString().trim().isEmpty()) {
            addressEditText_updateProfile.setError("Please enter your Address");
            passValidation = false;
        }
        if (addressEditText_updateProfile.getText().toString().trim().length() > 50) {
            addressEditText_updateProfile.setError("Length of address must be less than 50");
            passValidation = false;
        }
        if (favouriteMovieEditText_updateProfile.getText().toString().trim().isEmpty()) {
            favouriteMovieEditText_updateProfile.setError("Please enter your Favourite Movie");
            passValidation = false;
        }
        if (favouriteMovieEditText_updateProfile.getText().toString().trim().length() > 50) {
            favouriteMovieEditText_updateProfile.setError("Length of favourite movie must be less than 50");
            passValidation = false;
        }

    }

    private void submit() {
        dialog.show();
        final String studid = studentIdEditText_updateProfile.getText().toString();
        String firstname = firstNameEditText_updateProfile.getText().toString();
        String surname = surNameEditText_updateProfile.getText().toString();
        String dob = String.valueOf(dayOfMonth + "-" + monthOfYear + "-" + year);
        ;
        String gender = genderRadioButtonText;
        String course = courseSpinner_updateProfile.getSelectedItem().toString();
        String studymode = studyModeRadioButtonText;
        String address = addressEditText_updateProfile.getText().toString();
        String suburb = suburbSpinner_updateProfile.getSelectedItem().toString();
        String nationality = nationalitySpinner_updateProfile.getSelectedItem().toString();
        String nativelanguage = nativeLanguageSpinner_updateProfile.getSelectedItem().toString();
        String favouritesport = favouriteSportSelected;
        String favouritemovie = favouriteMovieEditText_updateProfile.getText().toString();
        String favouriteunit = favouriteUnitSpinner_updateProfile.getSelectedItem().toString();
        String currentjob = currentJobEditText_updateProfile.getText().toString();
        String monashemail = monashEmailEditText_updateProfile.getText().toString();
        String password = passwordEditText_updateProfile.getText().toString();
        String subscriptiondateandtime = subscribedate;

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                StudentProfile sp = new StudentProfile(Integer.parseInt(params[0]), params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11], params[12], params[13], params[14], params[15], params[16], params[17]);
                RestClient.updateStudentProfile(sp,Integer.parseInt(params[0]));
                return "Successfully saved";
            }

            @Override
            protected void onPostExecute(String response) {
                dialog.hide();
                Toast.makeText(vUpdateProfile.getContext(), response, Toast.LENGTH_SHORT).show();
                sh.clear();
                sh.save(studentEmail,passwordEditText_updateProfile.getText().toString());
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
            }
        }.execute(studid, firstname, surname, dob, gender, course, studymode, address, suburb, nationality, nativelanguage, favouritesport, favouritemovie, favouriteunit, currentjob, monashemail, MD5.getMD5x100(password), subscriptiondateandtime);
    }



}