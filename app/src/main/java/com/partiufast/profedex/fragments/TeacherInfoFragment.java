package com.partiufast.profedex.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.partiufast.profedex.InfoAdapter;
import com.partiufast.profedex.R;
import com.partiufast.profedex.api.ApiClient;
import com.partiufast.profedex.api.ApiInterface;
import com.partiufast.profedex.app.AppController;
import com.partiufast.profedex.data.Comment;
import com.partiufast.profedex.data.CommentResponse;
import com.partiufast.profedex.data.Message;
import com.partiufast.profedex.data.Professor;
import com.partiufast.profedex.data.Rating;
import com.partiufast.profedex.data.RatingResponse;
import com.partiufast.profedex.views.RatingView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherInfoFragment extends Fragment implements RatingView.OnRatingSend{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PROFESSOR = "professor_data";
    private static final String TAG = TeacherListFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private Professor professor;
    private TextView profName;
    private TextView profDescription;
    private ImageView profImg;
    ArrayList<Rating> ratings = new ArrayList<>();
    private Button commentButton;
    private EditText commentEditText;
    ArrayList<Object> comments = new ArrayList<>();
    InfoAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public TeacherInfoFragment getInstance() {
        return this;
    }

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
//        profName = (TextView) rootView.findViewById(R.id.teacher_name_text_view);
//        profDescription = (TextView) rootView.findViewById(R.id.description_teacher_text_view);
//        profImg = (ImageView) rootView.findViewById(R.id.professor_picture);
//        profName.setText(professor.getName());
//        profDescription.setText(professor.getDescription());

        // Comment List
        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.comments);
        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        rv.setLayoutManager(llm);

        comments.add(professor);
        adapter = new InfoAdapter(comments, this);
        rv.setAdapter(adapter);
        getCommentData();

        // set header for RecyclerView
        //RecyclerViewHeader header = (RecyclerViewHeader) rootView.findViewById(R.id.header);
        //header.attachTo(rv);

        commentButton = (Button) rootView.findViewById(R.id.comment_button);
        commentEditText = (EditText) rootView.findViewById(R.id.comment_edit_text);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommentData();
            }
        });
        // Ratings
        getRatingData();

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
                List<Comment> comment = response.body().getComments();
                Log.d(TAG, "Number of professors received: " + comment.size());
                comments.addAll(comment);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommentResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    private void getRatingData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<RatingResponse> call = apiService.getRatings(professor.getID());
        call.enqueue(new Callback<RatingResponse>() {
            @Override
            public void onResponse(Call<RatingResponse>call, Response<RatingResponse> response) {
                if (response.body() != null) {
                    List<Rating> rating = response.body().getRatings();
                    Log.d(TAG, "Number of ratings received: " + rating.size());
                    ratings.addAll(rating);
                    for (Rating r : ratings) {
                        RatingView rv = new RatingView(getActivity(), r);
                        rv.setOnRatingSend(getInstance());
                        LinearLayout linearLayout = (LinearLayout)  getView().findViewById(R.id.rating_layout);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.gravity = Gravity.CENTER_HORIZONTAL;
                        linearLayout.addView(rv);
                        //linearLayout.setLayoutParams(params);
                        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                        rv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
                else {
                    Log.d(TAG, "ERROR");
                }
            }

            @Override
            public void onFailure(Call<RatingResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public void sendRatingData(Rating rating) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "Rating " + rating.getUserRating());
        Call<Message> call = apiService.createRating(professor.getID(), AppController.getInstance().user.getId(), rating.getRatingTypeID(), rating.getUserRating());
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message>call, Response<Message> response) {
                if (response.body() != null) {
                    Log.d(TAG, "OK");
                }
                else {
                    Log.d(TAG, "ERROR");
                }
            }
            @Override
            public void onFailure(Call<Message>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public void sendCommentData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Message> call = apiService.createComment(professor.getID(), AppController.getInstance().user.getId(), commentEditText.getText().toString());
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message>call, Response<Message> response) {
                if (response.body() != null) {
                    if (response.body().isError()) {
                        Toast.makeText(getActivity(), response.body().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Comment posted.",
                                Toast.LENGTH_LONG).show();
                        Log.d(TAG, "OK");
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Connection error.",
                            Toast.LENGTH_LONG).show();
                    Log.d(TAG, "ERROR");
                }
            }
            @Override
            public void onFailure(Call<Message>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public void sendCommentVote(Comment comment, int value) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Message> call = apiService
                .voteComment(professor.getID(), comment.getID(), AppController.getInstance().user.getId(), value);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message>call, Response<Message> response) {
                if (response.body() != null) {
                    if (response.body().isError()) {
                        Toast.makeText(getActivity(), response.body().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Voted",
                                Toast.LENGTH_LONG).show();
                        Log.d(TAG, "OK");
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Connection error.",
                            Toast.LENGTH_LONG).show();
                    Log.d(TAG, "ERROR");
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
