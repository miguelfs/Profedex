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
import android.widget.TextView;

import com.partiufast.profedex.CommentAdapter;
import com.partiufast.profedex.R;
import com.partiufast.profedex.api.ApiClient;
import com.partiufast.profedex.api.ApiInterface;
import com.partiufast.profedex.data.Comment;
import com.partiufast.profedex.data.CommentResponse;
import com.partiufast.profedex.data.Professor;
import com.partiufast.profedex.data.ProfessorResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PROFESSOR = "professor_data";
    private static final String TAG = TeacherListFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private Professor professor;
    private TextView profName;
    private TextView profDescription;
    ArrayList<Comment> comments = new ArrayList<>();
    CommentAdapter adapter;

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

        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.comments);
        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        rv.setLayoutManager(llm);
        getCommentData();
        //comments.add(new Comment(1,1,"asdfasdfasdfasdfasdf"));
        //comments.add(new Comment(2,2,"asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf"));
        //comments.add(new Comment(3,3,"asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf"));
        //comments.add(new Comment(4,4,"asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf"));
        adapter = new CommentAdapter(comments);
        rv.setAdapter(adapter);

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

    private void getCommentData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<CommentResponse> call = apiService.getComments(professor.getID());
        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse>call, Response<CommentResponse> response) {
                List<Comment> commenta = response.body().getComments();
                Log.d(TAG, "Number of professors received: " + commenta.size());
                comments.addAll(commenta);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommentResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
