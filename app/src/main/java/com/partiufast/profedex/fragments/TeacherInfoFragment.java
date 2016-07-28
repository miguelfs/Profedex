package com.partiufast.profedex.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.partiufast.profedex.InfoAdapter;
import com.partiufast.profedex.R;
import com.partiufast.profedex.api.ApiClient;
import com.partiufast.profedex.api.ApiInterface;
import com.partiufast.profedex.app.AppController;
import com.partiufast.profedex.data.Comment;
import com.partiufast.profedex.data.CommentResponse;
import com.partiufast.profedex.data.CommentSent;
import com.partiufast.profedex.data.Message;
import com.partiufast.profedex.data.PicturePath;
import com.partiufast.profedex.data.Professor;
import com.partiufast.profedex.data.Rating;
import com.partiufast.profedex.data.RatingResponse;
import com.partiufast.profedex.views.RatingView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private ImageButton commentButton;
    private EditText commentEditText;
    private InfoAdapter.ProfessorViewHolder professorViewHolder;
    private List<InfoAdapter.CommentViewHolder> commentHolderList;
    private List<String> pictureURLs = new ArrayList<>();


    ArrayList<Rating> ratings = new ArrayList<>();
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

        // Comment List
        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.comments);
        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        rv.setLayoutManager(llm);

        comments.add(professor);
        adapter = new InfoAdapter(comments, this);
        rv.setAdapter(adapter);
        getCommentData();

        commentButton = (ImageButton) rootView.findViewById(R.id.comment_button);
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

    public void setProfessorViewHolder(InfoAdapter.ProfessorViewHolder p) {
        professorViewHolder = p;
    }

    public void addCommentViewHolder(InfoAdapter.CommentViewHolder c) {
        commentHolderList.add(c);
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
        Call<Message> call =
                apiService.createRating( professor.getID(),
                        AppController.getInstance().user.getId(),
                        rating.getRatingTypeID(), rating.getUserRating());

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
        Call<CommentSent> call = apiService.createComment(professor.getID(),
                            AppController.getInstance().user.getId(),
                            commentEditText.getText().toString());

        call.enqueue(new Callback<CommentSent>() {
            @Override
            public void onResponse(Call<CommentSent>call, Response<CommentSent> response) {
                if (response.body() != null) {
                    if (response.body().isError()) {
                        Toast.makeText(getActivity(), response.body().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Comment posted.",
                                Toast.LENGTH_LONG).show();
                        Log.d(TAG, "OK");

                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String dateTime = sdf1.format(new Date());
                        Comment c = new Comment(response.body().getID(), commentEditText.getText().toString(), dateTime, professor.getID(),
                                AppController.getInstance().user.getId(),  AppController.getInstance().user.getName(), 0);

                        comments.add(c);
                        adapter.notifyDataSetChanged();
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
            public void onFailure(Call<CommentSent>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public void sendCommentVote(final Comment comment, int value) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Message> call =
                apiService.voteComment(professor.getID(),
                        comment.getID(), AppController.getInstance().user.getId(), value);

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message>call, Response<Message> response) {
                if (response.body() != null) {
                    if (response.body().isError()) {
                        Toast.makeText(getActivity(), response.body().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        showToast("Voted");
                    }
                }
                else {
                    showToast( "Connection error.");
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

    public void getPicturePaths() {
        Log.d(TAG, "GETTING PATHS");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PicturePath>> call = apiService.getPictureList(professor.getID());
        call.enqueue(new Callback<List<PicturePath>>() {
            @Override
            public void onResponse(Call<List<PicturePath>>call, Response<List<PicturePath>> response) {
                if (response.body() != null) {
                        for(PicturePath p : response.body()) {
                            pictureURLs.add(p.getPicturePath());
                        }
                        if ( response.body().size() > 0 ) {
                            showToast(response.body().get(0).picturePath);
                            getPictureData();
                        }
                        else
                            showToast("No picture for this professor");
                        Log.d(TAG, "OK");
                }
                else {
                    showToast( "Connection error.");
                    Log.d(TAG, "ERROR PICTURE");
                }
            }
            @Override
            public void onFailure(Call<List<PicturePath>>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString() + "ERROR PICTURE");
            }
        });
    }

    /*
     * Gets the first picture from list
     */
    private void getPictureData() {
        Log.d(TAG, "GETTING PICTURE");
        if (pictureURLs.size() > 0) {
            Log.d(TAG, "THERE IS A PATH");
            Picasso.with(getContext()).load(ApiClient.getClient().baseUrl() + pictureURLs.get(0))
                    .placeholder( R.drawable.progress_animation )
                    .into(professorViewHolder.profImg);
        }
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
