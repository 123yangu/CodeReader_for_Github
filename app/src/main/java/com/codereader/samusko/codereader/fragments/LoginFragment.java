package com.codereader.samusko.codereader.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codereader.samusko.codereader.R;
import com.codereader.samusko.codereader.activities.LoginActivity;
import com.codereader.samusko.codereader.activities.UserActivity;
import com.codereader.samusko.codereader.api.RetrofitManager;
import com.codereader.samusko.codereader.model.Account;
import com.codereader.samusko.codereader.utility.Utilities;

import java.util.concurrent.ExecutionException;

public class LoginFragment extends Fragment {
    Button btnLogIn;
    EditText edtxtUserLogin;
    EditText edtxtUserPassword;

    ProgressDialog progressDialog;
    private TextInputLayout inputLayoutLogin, inputLayoutPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait. Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        btnLogIn = (Button) view.findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(onClickBtnLogIn);

        // TextInputLayouts
        inputLayoutLogin = (TextInputLayout) view.findViewById(R.id.input_layout_login);
        inputLayoutPassword = (TextInputLayout) view.findViewById(R.id.input_layout_password);

        edtxtUserLogin = (EditText) view.findViewById(R.id.editTextUserLogin);
        edtxtUserPassword = (EditText) view.findViewById(R.id.editTextPassword);

    }

    View.OnClickListener onClickBtnLogIn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            progressDialog.show();

            submitForm();

        }
    };

    private void submitForm() {

        String user_login = edtxtUserLogin.getText().toString();
        String user_password = edtxtUserPassword.getText().toString();



        if (!validateLogin()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }


        if (Utilities.isOnline(getActivity())) {

            TaskCheckCredentials taskCheck = new TaskCheckCredentials(); // checking credentials

            try {
                // if credentials are valid than set credentials and start new activity
                if (taskCheck.execute(user_login, user_password).get()) {

                    Account.setCredentials(getActivity(), user_login, user_password);

                    // starting UserActivity for displaying data
                    Intent startUserActivity = new Intent(getActivity(), UserActivity.class);
                    // startUserActivity.putExtra("userlogin", user_login);

                    progressDialog.dismiss();
                    startActivity(startUserActivity);

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), R.string.toast_incorrect_credentials, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else
            Toast.makeText(getActivity(), "Network isn't available!", Toast.LENGTH_SHORT);
    }

    private boolean validateLogin() {
        if (edtxtUserLogin.getText().toString().trim().isEmpty()) {
            inputLayoutLogin.setError(getString(R.string.err_msg_login));
            requestFocus(edtxtUserLogin);
            return false;
        } else {
            inputLayoutLogin.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (edtxtUserPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(edtxtUserPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    class TaskCheckCredentials extends AsyncTask<String, Void, Boolean> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(LoginActivity.context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please wait. Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            String login = params[0];
            String password = params[1];

            boolean isValid = RetrofitManager.checkCredentials(login, password);

            return isValid;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();

        }
    }
}
