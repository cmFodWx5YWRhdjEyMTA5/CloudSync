package com.se1.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.se1.dao.DatabaseOperation;
import com.se1.dao.User;
import com.se1.main.MainActivity;
import com.se1.main.R;

public class HomeActivity extends BaseActivity {
    private DatabaseOperation datasource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.title_activity_home);
        /*Start of NavDrawer code*/
        getLayoutInflater().inflate(R.layout.activity_home, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
        /*End of NavDrawer code*/

        //setContentView(R.layout.activity_home);
        final Button removeSignIn   = (Button)findViewById(R.id.removeSignIn);
        final Button boxId   = (Button)findViewById(R.id.boxId);
        datasource = new DatabaseOperation(this);
        datasource.open();
        removeSignIn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        User user=datasource.getUserDetail();
                        if(user!=null && ((user.getEmailId()!= null || user.getEmailId().equalsIgnoreCase("")) && user.getLoggedIn() == 1 ))
                        {
                            datasource.removeSignIn(user.getEmailId());
                        }
                    }

                });




    }
    //Navigate to registration page
    public void goToResetPassword(View view)
    {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }
    //Navigate to registration page
    public void goToLoginPage(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Navigate to registration page
    public void goToDropBoxActivity(View view)
    {
        Intent intent = new Intent(this, DropboxActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
