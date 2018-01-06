package com.example.jiaqiwang.monashfriendfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiaqiwang.monashfriendfinder.ServiceUse.MD5;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.RestClient;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.SharedHelper;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView emailTextView;
    private TextView passwordTextView;
    private Button loginButton;
    private Button subscribeButton;

    private String usrname;
    private String psw;

    private Context mContext;
    private SharedHelper sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        sh = new SharedHelper(mContext);
        usrname = sh.read().get("username");
        psw = sh.read().get("passwd");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if(usrname != null){
            emailTextView.setText(usrname);
            passwordTextView.setText(psw);
            login();
        }
    }

    public void login() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return RestClient.findStudentByEmail(params[0]);
            }

            @Override
            protected void onPostExecute(String student) {
                String passwordGet = "";
                try {
                    if (!student.equals("[]")) {
                        JSONObject jsStudent = new JSONObject(student.substring(1, student.length() - 1));
                        passwordGet = jsStudent.optString("password");
                        if (passwordGet.equals(MD5.getMD5x100(passwordTextView.getText().toString()))) {
                            Toast.makeText(MainActivity.this, "Successfully Log in", Toast.LENGTH_SHORT).show();
                            sh.clear();
                            sh.save(emailTextView.getText().toString(),passwordTextView.getText().toString());
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Password is incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Email does not exist.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(emailTextView.getText().toString());
    }

    public void init() {
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        passwordTextView = (TextView) findViewById(R.id.passwordTextView);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailTextView.getText().toString().matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"))
                    login();
                else
                    emailTextView.setError("Please input correct email address.");

            }
        });
        subscribeButton = (Button) findViewById(R.id.subscribeButton);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubscriptionActivity.class);
                startActivity(intent);
            }
        });
    }


}
