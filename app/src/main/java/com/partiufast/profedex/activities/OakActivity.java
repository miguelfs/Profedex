package com.partiufast.profedex.activities;

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
    int oakIndex = 1;
    String[] mOakArray;
    MediaPlayer mIntroductionMusic, mClickButtonSound;

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

        mOakArray = getResources().getStringArray(R.array.oak_array);
        mIntroductionMusic = MediaPlayer.create(this, R.raw.oaksmonologue);
        mClickButtonSound = MediaPlayer.create(this, R.raw.clickcompact);
        mIntroductionMusic.start();

        textView.setCharacterDelay(50);
        textView.animateText(getResources().getString(R.string.oak_text1));
        mJumpOakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(OakActivity.this, MainActivity.class);
                Intent intent = new Intent(OakActivity.this, CentroActivity.class);
                finish();
                mIntroductionMusic.stop();
                startActivity(intent);
            }
        });
        mNextOakButton.setSoundEffectsEnabled(false);
        mNextOakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickButtonSound.start();
                    if (index % 2 == 0){
                        if (!textView.isTextFullyPrinted()) {
                            textView.updateToFullText();
                            }
                        else{
                            textView.animateText(mOakArray[oakIndex]);
                            oakIndex++;
                        }
                    }
                    else{
                        textView.animateText(mOakArray[oakIndex]);
                        oakIndex++;
                    }
                index++;
                if (index == 13)
                mNextOakButton.setVisibility(View.GONE);
            }
        });



    }


}
