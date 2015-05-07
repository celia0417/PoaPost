package com.example.celia.kenya;

/**
 * Created by celia on 4/3/15.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.celia.kenya.client.RegisterClient;
import com.example.celia.kenya.tools.Global;

public class Register extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // TODO: insert into database (newUser, newDob, pwd)
        // TODO:Also need to add credit attribute!!!!!

        final EditText newUser = (EditText)findViewById(R.id.user_new);
        final EditText pwd = (EditText)findViewById(R.id.pwd_new);
        final EditText pwdConfirm = (EditText)findViewById(R.id.pwd_confirm_new);
        final Button regButton = (Button) findViewById(R.id.registerBtn);


        pwdConfirm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.i("mark","Text changed mark!!!!!");
                // check to see if user confirm password
                if(!pwd.getText().toString().equals(s.toString())) {
                    TextView msg = (TextView)findViewById(R.id.pwd_confirm_msg);
                    msg.setText("Please confirm your password! ");
                }

                if(pwd.getText().toString().equals(s.toString())) {
                    TextView msg = (TextView)findViewById(R.id.pwd_confirm_msg);
                    msg.setText("");
                }
            }
        });


        regButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!newUser.getText().toString().equals("") && newUser.getText().toString() != null &&
                        !pwd.getText().toString().equals("") && pwd.getText().toString() != null) {

                    if (pwd.getText().toString().equals(pwdConfirm.getText().toString())) {
                        new ValidateNewUserIDTask().execute(newUser.getText().toString(), pwd.getText().toString());
                    }
                }else{
                    Toast.makeText(Register.this,
                            "Username or Password Cannot Be Empty!", Toast.LENGTH_LONG).show();
                }

            }

        });

    }

    private class ValidateNewUserIDTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            // TODO Auto-generated method stub
            String userID = params[0], password = params[1];
            Global.userID = userID;
            String url = "http://kenya-t6ap7bumpv.elasticbeanstalk.com/Register";
            RegisterClient client = new RegisterClient(url);
            return client.registerQuery(userID, password);
        }//close doInBackground

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1)
            {
                Intent regIntent = new Intent(Register.this, MapsActivity.class);
                startActivity(regIntent);
            }
            else if(result == 0)
            {
                Toast.makeText(Register.this,
                        "Username already exists!", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(Register.this,
                        "Error Occurs!", Toast.LENGTH_LONG).show();
            }

        }//close onPostExecute
    }// close validateUserTask
}
