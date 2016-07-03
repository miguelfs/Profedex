package com.partiufast.profedex.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.partiufast.profedex.R;
import com.partiufast.profedex.data.Professor;

import java.util.ArrayList;
import java.util.Arrays;

public class AddTeacherActivity extends AppCompatActivity {
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
                mProfessor = new Professor(mTeacherName.getText().toString(), mTeacherDescription.getText().toString(), mTeacherRoom.getText().toString(),
                        mTeacherEmail.getText().toString(),  Arrays.asList("Calculo 3", "Instrumentação e Técnicas de Medidas"), 8, 8);
                Intent intent = new Intent(AddTeacherActivity.this, MainActivity.class);
                intent.putExtra(NEW_TEACHER_DATA_INTENT, mProfessor);
                startActivity(intent);
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
}
