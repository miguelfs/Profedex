package com.partiufast.profedex.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.partiufast.profedex.R;
import com.partiufast.profedex.data.Professor;

public class TeacherInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PROFESSOR = "professor_data";

    // TODO: Rename and change types of parameters
    private Professor professor;
    private TextView profName;
    private TextView profDescription;

    private OnFragmentInteractionListener mListener;

    public TeacherInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TeacherInfoFragment newInstance(Professor professor) {
        TeacherInfoFragment fragment = new TeacherInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROFESSOR, professor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            professor = (Professor)getArguments().getSerializable(ARG_PROFESSOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_teacher_info, container, false);
        profName = (TextView) rootView.findViewById(R.id.teacher_name_text_view);
        profDescription = (TextView) rootView.findViewById(R.id.description_teacher_text_view);
        profName.setText(professor.getName());
        profDescription.setText(professor.getDescription());
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
