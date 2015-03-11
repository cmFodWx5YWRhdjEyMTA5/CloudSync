package com.se1.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.se1.dao.DatabaseOperation;
import com.se1.dao.User;
import com.se1.main.MainActivity;
import com.se1.main.R;

public class ResetPasswordActivity extends ActionBarActivity {
    private DatabaseOperation datasource;
    int noOfAttempts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getSupportActionBar().setTitle(R.string.title_activity_reset_password);
        final Button resetPassword   = (Button)findViewById(R.id.resetPassword);
        final EditText oldPassword   = (EditText)findViewById(R.id.oldPassText);
        final EditText newPassword   = (EditText)findViewById(R.id.newPassText);
        noOfAttempts=0;
        datasource = new DatabaseOperation(this);
        datasource.open();

        resetPassword.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        User user=datasource.getUserDetail();
                        if(newPassword.getText().toString().equals("") || newPassword.getText().toString() == null )
                        {
                            Toast.makeText(getApplicationContext(), "Please enter new Password",
                                    Toast.LENGTH_LONG).show();
                        }
                        else if(user!=null && ((user.getEmailId()!= null || user.getEmailId().equalsIgnoreCase(""))))
                        {
                            if(user.getPassword().equals(oldPassword.getText().toString()))
                            {
                                datasource.resetPassword(user.getEmailId(),newPassword.getText().toString());
                                Toast.makeText(getApplicationContext(), "Password is Successfully Reset ",
                                        Toast.LENGTH_LONG).show();
                                goToHomePage();
                            }
                            else {
                                setNoOfAttempts();
                                if(noOfAttempts == 3)
                                {
                                    goToLoginPage(user.getEmailId());
                                }
                                Toast.makeText(getApplicationContext(), "Please Enter Valid Password",
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                    }

                });
    }
    //Navigate to Login page
    public void goToHomePage()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    //Navigate to Login page
    public void goToLoginPage(String emaildId)
    {
        datasource.removeSignIn(emaildId);// Go to login page and remove signup option
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public int getNoOfAttempts() {
        return noOfAttempts;
    }

    public void setNoOfAttempts() {
        noOfAttempts++;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reset_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
