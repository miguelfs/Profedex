package com.partiufast.profedex.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.partiufast.profedex.DividerItemDecoration;
import com.partiufast.profedex.R;
import com.partiufast.profedex.Teacher;
import com.partiufast.profedex.TeacherAdapater;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeacherListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeacherListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Teacher> mTeacherList = new ArrayList<>();
    private TeacherAdapater mTeacherAdapater;

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

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.teacherRecyclerView);
        mTeacherAdapater = new TeacherAdapater(mTeacherList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRecyclerView.setAdapter(mTeacherAdapater);
        for(int index = 0; index < 150; index++){
            mTeacherList.add(new Teacher("Professor Carvalho", getResources().getString(R.string.lorem_ipsum), "H 216",
                    Arrays.asList("Circuitos Elétricos I", "Instrumentação e Técnicas de Medidas"),8, 8 ));
            mTeacherAdapater.notifyDataSetChanged();
        }
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
}