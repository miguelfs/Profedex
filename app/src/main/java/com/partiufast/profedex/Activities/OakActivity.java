package com.partiufast.profedex.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.partiufast.profedex.R;
import com.partiufast.profedex.Typewriter;

public class OakActivity extends AppCompatActivity {
    Typewriter textView;
    ImageView oakImageView;
    int index = 0;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oak);
        textView = (Typewriter) findViewById(R.id.oak_text_view);
        oakImageView = (ImageView) findViewById(R.id.oak_image_view);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/pkmn.ttf");
        textView.setTypeface(font);

        mp = MediaPlayer.create(this, R.raw.oaksmonologue);
        mp.start();

        textView.setCharacterDelay(50);
        textView.animateText(getResources().getString(R.string.oak_text1));
        oakImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (index) {
                    case 1:
                        textView.animateText(getResources().getString(R.string.oak_text2));
                        index++;
                        break;
                    case 2:
                        textView.animateText(getResources().getString(R.string.oak_text3));
                        index++;
                        break;
                    case 3:
                        textView.animateText(getResources().getString(R.string.oak_text4));
                        index++;
                        break;
                    case 4:
                        textView.animateText(getResources().getString(R.string.oak_text5));
                        index++;
                        break;
                    case 5:
                        textView.animateText(getResources().getString(R.string.oak_text6));
                        index++;
                        break;
                    case 6:
                        textView.animateText(getResources().getString(R.string.oak_text7));
                        index++;
                        break;
                    case 7:
                        Intent intent = new Intent(OakActivity.this, ListActivity.class);
                        mp.stop();
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        index++;
                }
            }
        });



    }


}
