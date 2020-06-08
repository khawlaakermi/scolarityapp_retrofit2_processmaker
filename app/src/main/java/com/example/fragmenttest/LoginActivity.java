package com.example.fragmenttest;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;
    private String mEmail;
    private String mPassword;
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private View mLoginStatusView;
    private TextView mLoginStatusMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (EditText) findViewById(R.id.regName);
        mPasswordView = (EditText) findViewById(R.id.regpassword);
        mLoginFormView = findViewById(R.id.login);
        mLoginStatusView = findViewById(R.id.progressBar);
        findViewById(R.id.regBtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptlogin();
                    }
                });
    }


    public void attemptlogin() {
        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mEmail = mEmailView.getText().toString();
        mPassword = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;
// Check for a valid password.
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (mPassword.length() < 4) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(mEmail, mPassword);
            mAuthTask.execute((Void) null);
        }

    }

//progressshow

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            mLoginStatusView.setVisibility(View.VISIBLE);
            mLoginStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginStatusView.setVisibility(show ? View.VISIBLE
                                    : View.GONE);
                        }
                    });

            mLoginFormView.setVisibility(View.VISIBLE);

        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            // mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    // Asynctask
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        private String token;
        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
// TODO: attempt authentication against a network service.
            // pour récupérer un jeton
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType,
                    "{\n\t\"grant_type\":\"password\"," +
                            "\n\t\"username\": \"" + mEmail +
                            "\",\n\t\"password\": \"" + mPassword +
                            "\",\n\t\"scope\": \"*\", \n\t\"client_id\":\"WMZNSSETCJDPTZSVETRNOPGYFKMAKHHQ\"," +
                            " \n\t\"client_secret\":\"5813427175e8e5d18452a90035077331\"\n}");

            Request request = new Request.Builder()
                    .url("http://process.isiforge.tn/isi/oauth2/token")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Cookie", "PM-TabPrimary=101010010")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String resp = response.body().string();
                    JSONObject object = (JSONObject) new JSONTokener(resp).nextValue();
                    token = (String) object.get("access_token");
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                preferences.edit().putString("TOKEN",token).apply();
                Toast.makeText(getApplicationContext(), "Welcome to your espace ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.putExtra("token", token);
                getApplicationContext().startActivity(intent);

            } else {

                Toast.makeText(getApplicationContext(), "Access fail", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
