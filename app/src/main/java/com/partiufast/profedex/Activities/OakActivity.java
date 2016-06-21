package com.partiufast.profedex.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.partiufast.profedex.R;
import com.partiufast.profedex.Typewriter;

public class OakActivity extends AppCompatActivity {
    Typewriter textView;
    ImageView oakImageView;
    Button mNextOakButton, mJumpOakButton;
    int index = 0;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oak);
        textView = (Typewriter) findViewById(R.id.oak_text_view);
        oakImageView = (ImageView) findViewById(R.id.oak_image_view);
        mNextOakButton = (Button) findViewById(R.id.nextOakButton);
        mJumpOakButton = (Button) findViewById(R.id.jumpOakButton);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/pkmn.ttf");
        textView.setTypeface(font);

        mp = MediaPlayer.create(this, R.raw.oaksmonologue);
        mp.start();

        textView.setCharacterDelay(50);
        textView.animateText(getResources().getString(R.string.oak_text1));
        mJumpOakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OakActivity.this, CentroActivity.class);
                mp.stop();
                startActivity(intent);
            }
        });
        mNextOakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (index) {
                    case 0:
                        textView.animateText(getResources().getString(R.string.oak_text2));
                        index++;
                        break;
                    case 1:
                        textView.animateText(getResources().getString(R.string.oak_text3));
                        index++;
                        break;
                    case 2:
                        textView.animateText(getResources().getString(R.string.oak_text4));
                        index++;
                        break;
                    case 3:
                        textView.animateText(getResources().getString(R.string.oak_text5));
                        index++;
                        break;
                    case 4:
                        textView.animateText(getResources().getString(R.string.oak_text6));
                        index++;
                        break;
                    case 5:
                        textView.animateText(getResources().getString(R.string.oak_text7));
                        mNextOakButton.setVisibility(View.GONE);
                        index++;
                        break;
                    default:
                        index++;
                }
            }
        });



    }


}
