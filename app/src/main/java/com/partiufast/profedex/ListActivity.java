package com.partiufast.profedex;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> mListItems;
    private TeacherAdapater mTeacherAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mListItems = new ArrayList<String>();
        for(int index = 0; index < 150; index++){
            mListItems.add(index, index+1 + ": ??????????????????");
        }
        mTeacherAdapater = new TeacherAdapater(mListItems);
        mRecyclerView.setAdapter(mTeacherAdapater);



    }
}
