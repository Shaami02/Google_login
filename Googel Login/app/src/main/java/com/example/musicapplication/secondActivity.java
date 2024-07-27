package com.example.musicapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class secondActivity extends AppCompatActivity {
     TextView name ,songsname,totalTimeTextView,currentTimeTextView;


      boolean isBound=false;

    Button play_1,Nxet_song,revars_song;
     SeekBar seekBar;
    Button singout;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    MediaPlayer mediaPlayer;
    int currentSongIndex=0;
    String nextSongName;


    int songsplay[]={
            R.raw.tu_hai_kaha,R.raw.shikayat,R.raw.ruaan
    };


    int currentSongName;
    private boolean isPlaying = false;

 Handler  handler=new Handler();



        List<String>SongList= Arrays.asList("tu_hai_kha","shikayat","Ruaan");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //listView = findViewById(R.id.songlist);





        name=(TextView) findViewById(R.id.Name);
        songsname=(TextView) findViewById(R.id.Song);
        currentTimeTextView=findViewById(R.id.currentTimeTextView);
        totalTimeTextView=findViewById(R.id.totalTimeTextView);
        singout=(Button) findViewById(R.id.singout);
        play_1=(Button)findViewById(R.id.play_1);
        Nxet_song=findViewById(R.id.next);
        revars_song=findViewById(R.id.Rever);
        seekBar=findViewById(R.id.seek);


        mediaPlayer =MediaPlayer.create(secondActivity.this,songsplay[currentSongIndex]);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        play_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mediaPlayer.start();
                if (isPlaying) {
                    pauseAudio();
                    play_1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.play, 0, 0, 0);

                } else {
                    playAudio();
                    play_1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pause, 0, 0, 0);
                    updateseekbar();

                }
            }
        });

        Nxet_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying){
                    pauseAudio();
                    play_1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.play,0,0,0);
                }else {
                    playAudio();
                    play_1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pause, 0, 0, 0);
                }
                playNextSong();
            }
        });
        revars_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying){
                    pauseAudio();
                    play_1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.play,0,0,0);
                }else {
                    playAudio();
                    play_1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pause, 0, 0, 0);
                }
                ReversButton();
                playAudio();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        if(account!=null){
            String personName=account.getDisplayName();
            String personEmail=account.getEmail();
            name.setText(personName);
        }
     singout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             singOut();
         }
     });
    }
    void singOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                startActivity(new Intent(secondActivity.this,MainActivity.class));
            }
        });
    }

    private void playAudio() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;

        }
    }

    private void pauseAudio() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public void updateseekbar(){
        seekBar.setMax(mediaPlayer.getDuration());
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null && mediaPlayer.isPlaying()) {
                    UpdatecurrentTime();
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(currentPosition);
                    if (currentPosition >= mediaPlayer.getDuration()) {
                        playNextSong();
                    }
                }
                    handler.postDelayed(this, 100);
            }
        };
        handler.postDelayed(runnable,100);
        int totelTime=mediaPlayer.getDuration();
        totalTimeTextView.setText(formatTime(totelTime));
    }
    public void ReversUpdateSeekbar(){
        seekBar.setMax(mediaPlayer.getDuration());
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null && mediaPlayer.isPlaying()) {
                    UpdatecurrentTime();
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(currentPosition);
                    if (currentPosition >= mediaPlayer.getDuration()) {
                        ReversButton();
                    }
                }
                handler.postDelayed(this, 100);
            }
        };
        handler.postDelayed(runnable,100);
        int totelTime=mediaPlayer.getDuration();
        totalTimeTextView.setText(formatTime(totelTime));
    }
    public void playNextSong() {
        if (currentSongIndex < songsplay.length - 1) {
            currentSongIndex++;
        } else {
            currentSongIndex = 0;
        }
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(this, songsplay[currentSongIndex]);
        mediaPlayer.start();
        updateseekbar();
        updateSongName();
    }
   public void ReversButton(){
        currentSongIndex--;
        if(currentSongIndex>=0){
            mediaPlayer.stop();
            mediaPlayer.release();

            mediaPlayer=MediaPlayer.create(this,songsplay[currentSongIndex]);
            mediaPlayer.start();
        }else {

            currentSongIndex= songsplay.length-1;
            mediaPlayer.release();

            mediaPlayer=MediaPlayer.create(this,songsplay[currentSongIndex]);
            mediaPlayer.start();
        }
        ReversUpdateSeekbar();
        Reverssongname();

        
  /*   if(currentSongIndex>0){
         currentSongIndex--;
     }else {
       currentSongIndex =  songsplay.length-1;
     }
     mediaPlayer.stop();
     mediaPlayer.reset();
     mediaPlayer=MediaPlayer.create(this,songsplay[currentSongIndex]);
     mediaPlayer.start();
     Reverssongname();
     ReversUpdateSeekbar();*/

   }
    public  void UpdatecurrentTime(){
        int currenttime=mediaPlayer.getCurrentPosition();
        currentTimeTextView.setText(formatTime(currenttime));
    }
    public String formatTime(int millis){
      int seconds=(millis/1000)%60;
      int mint=(millis/(1000*60))%60;
      return String.format(Locale.getDefault(),"%2d:%02d",mint,seconds);
    }
    public void updateSongName() {

        currentSongName=(currentSongName+1) % SongList.size();
        nextSongName=SongList.get(currentSongName);
        songsname.setText(nextSongName);
    }
    public void Reverssongname(){
        currentSongName=(currentSongName-1) % SongList.size();
        nextSongName=SongList.get(currentSongName);
        songsname.setText(nextSongName);
    }




}