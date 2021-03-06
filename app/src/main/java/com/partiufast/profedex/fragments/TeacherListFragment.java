package com.partiufast.profedex.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.partiufast.profedex.DividerItemDecoration;
import com.partiufast.profedex.EndlessRecyclerViewScrollListener;
import com.partiufast.profedex.R;
import com.partiufast.profedex.activities.MainActivity;
import com.partiufast.profedex.api.ApiInterface;
import com.partiufast.profedex.api.ApiClient;
import com.partiufast.profedex.data.Professor;
import com.partiufast.profedex.TeacherAdapter;
import com.partiufast.profedex.data.ProfessorResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeacherListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeacherListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherListFragment extends Fragment {

    private static final String TAG = TeacherListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager; //RecyclerView.LayoutManager
    private ArrayList<Professor> mProfessorList = new ArrayList<>();
    private Professor mAddedProfessor;
    private TeacherAdapter mTeacherAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TeacherListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpacesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherListFragment newInstance(String param1, String param2) {
        TeacherListFragment fragment = new TeacherListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView =  inflater.inflate(R.layout.fragment_teachers, container, false);
        //((MainActivity)getActivity()).hideFAB(false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.teacherRecyclerView);
        mTeacherAdapter = new TeacherAdapter(mProfessorList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRecyclerView.setAdapter(mTeacherAdapter);

        mProfessorList.clear();
        getProfessorData("professor_id", "asc", 0, 20);

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                getProfessorData("professor_id", "asc", (page+1)*10 , 10);
                Log.d(TAG, "Got from " + (page+1)*10 + "to" + ((page+2)*10));
            }
        });

        if (mAddedProfessor != null)
            addTeacherToList(mAddedProfessor);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionTeacher(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteractionTeacher(Uri uri);
    }

    public void addTeacherToList(Professor professor){
        mProfessorList.add(professor);
        mTeacherAdapter.notifyDataSetChanged();
    }

    public void setNewTeacherToList(Professor professor) {
        mAddedProfessor = professor;
    }

    /**
     * Get professor list from server
     */
    private void getProfessorData(String sort, String order, int start, int limit ) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ProfessorResponse> call = apiService.getProfessors(sort, order, start, limit);
        call.enqueue(new Callback<ProfessorResponse>() {
            @Override
            public void onResponse(Call<ProfessorResponse>call, Response<ProfessorResponse> response) {
                List<Professor> professors = response.body().getProfessors();
                Log.d(TAG, "Number of professors received: " + professors.size());
                mProfessorList.addAll(professors);
                mTeacherAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ProfessorResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public void refreshData() {
        mProfessorList.clear();
        getProfessorData("professor_id", "asc", 0, 100);
    }
}
