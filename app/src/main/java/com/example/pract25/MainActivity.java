package com.example.pract25;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mAmogusSound, mClownSound, mDorimeSound, mNekoArkSound, mRickRollSound, mSaulSound;
    private int mStreamID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
           // Для устройств до Android 5
            createOldSoundPool();
      } else {
           // Для новых устройств
           createNewSoundPool();
        }

        mAssetManager = getAssets();
        mAmogusSound = loadSound("Amogus.mp3");
        mClownSound = loadSound("Clown.mp3");
       mDorimeSound = loadSound("Dorime.mp3");
       mNekoArkSound = loadSound("NekoArk.mp3");
        mRickRollSound = loadSound("RickRoll.mp3");
       mSaulSound = loadSound("Saul.mp3");

        ImageButton amogusImageButton = findViewById(R.id.image_Amogus);
       amogusImageButton.setOnClickListener(onClickListener);

        ImageButton dorimeImageButton = findViewById(R.id.image_dorime);
        dorimeImageButton.setOnClickListener(onClickListener);

        ImageButton catImageButton = findViewById(R.id.image_clown);
        catImageButton.setOnClickListener(onClickListener);

        ImageButton duckImageButton = findViewById(R.id.image_nekoark);
        duckImageButton.setOnClickListener(onClickListener);

        ImageButton sheepImageButton = findViewById(R.id.image_saul);
        sheepImageButton.setOnClickListener(onClickListener);

        ImageButton dogImageButton = findViewById(R.id.image_rickroll);
        dogImageButton.setOnClickListener(onClickListener);


    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            if (id == R.id.image_Amogus) {
                playSound(mAmogusSound);
            } else if (id == R.id.image_dorime) {
                playSound(mDorimeSound);
            } else if (id == R.id.image_rickroll) {
                playSound(mRickRollSound);
            } else if (id == R.id.image_saul) {
                playSound(mSaulSound);
            } else if (id == R.id.image_nekoark) {
                playSound(mNekoArkSound);
            } else if (id == R.id.image_clown) {
                playSound(mClownSound);
            }
        }
    };
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mAmogusSound = loadSound("Amogus.mp3");
        mClownSound = loadSound("Clown.mp3");
        mDorimeSound = loadSound("Dorime.mp3");
        mNekoArkSound = loadSound("NekoArk.mp3");
        mRickRollSound = loadSound("RickRoll.mp3");
        mSaulSound = loadSound("Saul.mp3");

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
}