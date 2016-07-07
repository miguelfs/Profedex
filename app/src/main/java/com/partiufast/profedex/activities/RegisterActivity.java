package com.partiufast.profedex.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.partiufast.profedex.R;
import com.partiufast.profedex.api.ApiClient;
import com.partiufast.profedex.api.ApiInterface;
import com.partiufast.profedex.app.AppConfig;
import com.partiufast.profedex.app.AppController;
import com.partiufast.profedex.data.Message;
import com.partiufast.profedex.data.User;
import com.partiufast.profedex.helper.SQLiteHandler;
import com.partiufast.profedex.helper.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputRepeatPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputRepeatPassword = (EditText) findViewById(R.id.repeat_password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String repeatPassword = inputRepeatPassword.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    if (password.equals(repeatPassword)) {
                        registerUser(name, email, password);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Passwords do not match!", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Message> call = apiService.register(name, email, password);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message>call, retrofit2.Response<Message> response) {
                if (response.body() != null) {
                    if (!response.body().isError()) {
                        Log.e(TAG,  "Message " + response.body().getMessage());
                        hideDialog();
                        setResult(RESULT_OK);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Error: " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error: Response Error", Toast.LENGTH_LONG).show();
                }
                hideDialog();
            }

            @Override
            public void onFailure(Call<Message>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
