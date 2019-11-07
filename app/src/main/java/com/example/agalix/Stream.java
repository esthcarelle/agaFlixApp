package com.example.agalix;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class Stream extends AppCompatActivity {

    private VideoView video;
    private ImageView play;
    private TextView currentTimer;
    private TextView durationTimer;
    private ProgressBar progress;
    private Uri videoUri;
    private ProgressBar bufferProgress;
    private boolean isPlaying;
    private int current = 0;
    private int duration = 0;

    private String myUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        isPlaying = false;
        video = (VideoView) findViewById(R.id.video);
        play = (ImageView) findViewById(R.id.play);
        currentTimer = (TextView) findViewById(R.id.start_time);
        durationTimer = (TextView) findViewById(R.id.end_time);
        progress = (ProgressBar) findViewById(R.id.progress);
        progress.setMax(100);
        bufferProgress = (ProgressBar) findViewById(R.id.buffer_progress);
        myUrl = getIntent().getStringExtra("url");

        videoUri = Uri.parse(myUrl);

        video.setVideoURI(videoUri);
        video.requestFocus();
        video.start();
        video.setOnInfoListener(new MediaPlayer.OnInfoListener() {

            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {

                switch (what) {
                    case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END: {
                        bufferProgress.setVisibility(View.GONE);
                        return true;
                    }
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START: {
                        bufferProgress.setVisibility(View.VISIBLE);
                        return true;
                    }

                }
                return false;
            }
        });

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                duration = mp.getDuration()/1000;
                String durationString = String.format("%2d:%2d", duration / 60, duration % 60);

                durationTimer.setText(durationString);
            }
        });

        isPlaying = true;
        play.setImageResource(R.drawable.pause);

        new VideoProgress().execute();

        play.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                if(isPlaying){
                    video.pause();
                    play.setImageResource(R.drawable.play);
                    isPlaying = false;
                }
                else{
                    video.start();
                    play.setImageResource(R.drawable.pause);
                    isPlaying = true;
                }
            }
        });
    }

    public class VideoProgress extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids){

            do{
                current = video.getCurrentPosition()/1000;
                try {

                    int currentPercent = current * 100/duration;

                    publishProgress(currentPercent);


                }catch (Exception e){

                }

            } while (progress.getProgress() <= 100);


            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progress.setProgress(values[0]);
        }
    }

}

