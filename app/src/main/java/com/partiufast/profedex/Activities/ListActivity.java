package com.partiufast.profedex.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.partiufast.profedex.DividerItemDecoration;
import com.partiufast.profedex.R;
import com.partiufast.profedex.Teacher;
import com.partiufast.profedex.TeacherAdapater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Teacher> mTeacherList = new ArrayList<>();
    private TeacherAdapater mTeacherAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mTeacherAdapater = new TeacherAdapater(mTeacherList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setAdapter(mTeacherAdapater);
        for(int index = 0; index < 150; index++){
            mTeacherList.add(new Teacher("Professor Carvalho", getResources().getString(R.string.lorem_ipsum), "H 216",
                    Arrays.asList("Circuitos Elétricos I", "Instrumentação e Técnicas de Medidas"),8, 8 ));
            mTeacherAdapater.notifyDataSetChanged();
        }
    }
}
