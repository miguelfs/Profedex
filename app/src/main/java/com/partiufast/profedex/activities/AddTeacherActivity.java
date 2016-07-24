package com.partiufast.profedex.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.partiufast.profedex.R;
import com.partiufast.profedex.api.ApiClient;
import com.partiufast.profedex.api.ApiInterface;
import com.partiufast.profedex.data.Message;
import com.partiufast.profedex.data.Professor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTeacherActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText mTeacherName, mTeacherDescription, mTeacherRoom, mTeacherEmail;
    private Button mAddTeacherButton;
    private Professor mProfessor;
    public static final String NEW_TEACHER_DATA_INTENT = "NEW_TEACHER_DATA_INTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTeacherName = (EditText) findViewById(R.id.teacher_name_edit_text);
        mTeacherDescription = (EditText) findViewById(R.id.input_description);
        mTeacherRoom = (EditText) findViewById(R.id.room_edit_text);
        mTeacherEmail = (EditText) findViewById(R.id.input_teacher_edit_text);
        mAddTeacherButton = (Button) findViewById(R.id.finish_adding_teacher_button);
        mAddTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendProfessorData();
                //Intent intent = new Intent(AddTeacherActivity.this, MainActivity.class);
                //intent.putExtra(NEW_TEACHER_DATA_INTENT, mProfessor);
                //startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Activity getInstance() {return this;}

    private void sendProfessorData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Message> call = apiService.createProfessor(mTeacherName.getText().toString(),
                mTeacherEmail.getText().toString(),
                mTeacherDescription.getText().toString(),
                mTeacherRoom.getText().toString());

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message>call, Response<Message> response) {
                if (response.body() != null) {
                    if (response.body().isError()) {
                        Toast.makeText(getInstance(), response.body().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getInstance(), "Professor added.",
                                Toast.LENGTH_LONG).show();
                        Log.d(TAG, "OK");
                    }
                }
            }

            @Override
            public void onFailure(Call<Message>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
