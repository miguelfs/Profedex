package com.partiufast.profedex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.partiufast.profedex.R;

public class CentroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        universityListView();

    }

    private void universityListView() {

        String[] universidade = {"Centro de Tecnologia", "Centro 2", "Centro 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.lista_centro, universidade);
        //setListAdapter(new ArrayAdapter<String>(this,
         //       R.layout.lista_centro, results));

        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if(position == 0)
                {

                    Intent myIntent = new Intent(CentroActivity.this, MainActivity.class);
                    startActivityForResult(myIntent, 0);
                    finish();
                }

                if(position == 1)
                {

                    Intent myIntent =  new Intent(CentroActivity.this, OakActivity.class);
                    startActivityForResult(myIntent, 0);
                }

                if(position == 2)
                {

                    Intent myIntent =  new Intent(CentroActivity.this, MainActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        });
    }

}
