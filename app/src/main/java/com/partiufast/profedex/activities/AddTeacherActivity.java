package com.partiufast.profedex.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.partiufast.profedex.R;
import com.partiufast.profedex.api.ApiClient;
import com.partiufast.profedex.api.ApiInterface;
import com.partiufast.profedex.data.Message;
import com.partiufast.profedex.data.Professor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTeacherActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText mTeacherName, mTeacherDescription, mTeacherRoom, mTeacherEmail;
    private Button mAddTeacherButton;
    private FloatingActionButton mAddPictureButton;
    private Professor mProfessor;
    public static final String NEW_TEACHER_DATA_INTENT = "NEW_TEACHER_DATA_INTENT";
    private Context mContext;
    private ImageView mTeacherImageView;
    private TextView mHintPictureTextView;
    private Uri uri;
    private String fileMime;
    File tempFile;
    BufferedInputStream buf;
    Bitmap bMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        mContext = getApplicationContext();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTeacherName = (EditText) findViewById(R.id.teacher_name_edit_text);
        mTeacherImageView = (ImageView) findViewById(R.id.pictureImageView);
        mHintPictureTextView = (TextView) findViewById(R.id.hintTextView);
        mTeacherDescription = (EditText) findViewById(R.id.input_description);
        mTeacherRoom = (EditText) findViewById(R.id.room_edit_text);
        mTeacherEmail = (EditText) findViewById(R.id.input_teacher_edit_text);
        mAddTeacherButton = (Button) findViewById(R.id.finish_adding_teacher_button);
        mAddPictureButton = (FloatingActionButton) findViewById(R.id.addPhotoTeacher);
        mAddTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendProfessorData();
                finish();
            }
        });

        mAddPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
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

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public Activity getInstance() {return this;}

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        //File file = new File(getRealPathFromUri(this, fileUri));
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), tempFile);
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, tempFile.getName(), requestFile);
    }

    private void sendProfessorData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        MultipartBody.Part pic = prepareFilePart("picture", uri);
        //RequestBody picture = RequestBody.create(MediaType.parse("image/*"), file);

        RequestBody name = createPartFromString(mTeacherName.getText().toString());
        RequestBody email = createPartFromString(mTeacherEmail.getText().toString());
        RequestBody room = createPartFromString(mTeacherRoom.getText().toString());
        RequestBody description = createPartFromString(mTeacherDescription.getText().toString());

        Call<Message> call = apiService.createProfessorPic(name, email, room, description, pic);

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message>call, Response<Message> response) {
                if (response.body() != null) {
                    if (response.body().isError()) {
                        Toast.makeText(getInstance(), response.body().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getInstance(), "Professor added.",
                                Toast.LENGTH_LONG).show();
                        Log.d(TAG, "OK");
                    }
                }
            }

            @Override
            public void onFailure(Call<Message>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PICTURE);
    }

    public File convertStream(InputStream input) throws IOException {
        byte[] buffer = new byte[input.available()];
        input.read(buffer);
        File temp = File.createTempFile("prefix","."+fileMime, getCacheDir()); //new File(getFilesDir(), "tmp."+fileMime);
        OutputStream outStream = new FileOutputStream(temp);
        outStream.write(buffer);
        return temp;
    }

    public static String getRealPathFromUri(Activity activity, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                uri = data.getData();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                fileMime = mime.getExtensionFromMimeType(getContentResolver().getType(uri));

                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                try {
                    tempFile = convertStream(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                InputStream i = this.getContentResolver().openInputStream(data.getData());
                buf = new BufferedInputStream(i);
                bMap = BitmapFactory.decodeStream(buf);
                mHintPictureTextView.setVisibility(View.INVISIBLE);
                mTeacherImageView.setImageBitmap(bMap);
                mTeacherImageView.setVisibility(View.VISIBLE);
                //TODO: Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }

}
