package com.example.celia.kenya;

import android.os.AsyncTask;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.content.Context;

import android.view.inputmethod.InputMethodManager;



import com.example.celia.kenya.client.LoginClient;
import com.example.celia.kenya.tools.Global;


public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = (EditText) findViewById(R.id.usernameET);
        final EditText password = (EditText) findViewById(R.id.passwordET);
        final Button loginButton = (Button) findViewById(R.id.loginBtn);
        final Button regButton = (Button) findViewById(R.id.registerBtn);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ValidateUserTask task = new ValidateUserTask();
                task.execute(username.getText().toString(), password.getText().toString());
                //new Connection().execute();
                username.setText("");
                password.setText("");
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(LoginActivity.this, Register.class);
                startActivity(regIntent);
            }
        });

        username.requestFocus();
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.showSoftInput(username, InputMethodManager.SHOW_IMPLICIT);

//        userID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus) {
//                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                }
//            }
//        });
    }


    private class ValidateUserTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            // TODO Auto-generated method stub
            String userID = params[0], password = params[1];
            LoginClient loginClient = new LoginClient("http://kenya-t6ap7bumpv.elasticbeanstalk.com/Login");
            return loginClient.login(userID,password);
        }//close doInBackground

        @Override
        protected void onPostExecute(Integer result) {
            if(result == 1){
                //navigate to Main Menu
                Intent hwIntent = new Intent(LoginActivity.this, MapsActivity.class);
                startActivity(hwIntent);

            }
            else if(result == 0){
                Toast.makeText(getApplicationContext(), "Incorrect Password!",
                        Toast.LENGTH_SHORT).show();

                //incorrect password
            }
            else if(result == -1) {
                Toast.makeText(getApplicationContext(), "User doesn't exist!",
                        Toast.LENGTH_SHORT).show();

                //incorrect userID
            }
            else {
                Toast.makeText(getApplicationContext(), "Error!",
                        Toast.LENGTH_SHORT).show();
                //error
            }
        }
    }

}