package com.codereader.samusko.codereader.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codereader.samusko.codereader.R;
import com.codereader.samusko.codereader.fragments.LoginFragment;
import com.codereader.samusko.codereader.model.GitHubUser;

public class LoginActivity extends AppCompatActivity {
    private LoginFragment loginFragment;
    private GitHubUser gitHubUser;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        initViews();

      /* loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.relative_container, loginFragment).commit();*/
    }

    private void initViews() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
