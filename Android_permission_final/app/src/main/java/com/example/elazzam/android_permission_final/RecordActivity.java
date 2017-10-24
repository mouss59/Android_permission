package com.example.elazzam.android_permission_final;

import android.Manifest;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;


public class RecordActivity extends AppCompatActivity {

    private Button play, stop, record;
    private MediaRecorder myAudioRecorder;
    private String mFileName=null;


    private static final int MY_PERMISSION_REQUEST_RECORD_AUDIO = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mFileName = getExternalCacheDir().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";


        play = (Button)findViewById(R.id.play);
        stop = (Button)findViewById(R.id.stop);
        record = (Button)findViewById(R.id.record);

        stop.setEnabled(false);
        play.setEnabled(false);







        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)  != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)){
                //permission deja demander mais elle a etait refuser soit on affiche un message pour qu'il autorise a partir des reglage ou bien
                //on explique a l utilisateur pk cette permission est obligatoir pour le fonctionnement de l'application
                //avec une alerte si il accepte on redemande la permission

                AlertAudio();


            }else{

                //si on a jamais demander la permission c'est l'occasion pour la demander

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},MY_PERMISSION_REQUEST_RECORD_AUDIO );

            }

        }else {
            //dans le cas ou la permission est accordée

            Toast.makeText(getApplicationContext(), "utiliser l application", Toast.LENGTH_LONG).show();

            onRestart();
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myAudioRecorder.setOutputFile(mFileName);

            record.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        myAudioRecorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myAudioRecorder.start();
                    record.setEnabled(false);
                    stop.setEnabled(true);

                    Toast.makeText(getApplicationContext(),"entrain d'enregistrer", Toast.LENGTH_LONG).show();
                }
            });



            stop.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    myAudioRecorder= null;
                    record.setEnabled(true);
                    stop.setEnabled(false);
                    play.setEnabled(true);
                    Toast.makeText(getApplicationContext(),"enregistrer avec succé", Toast.LENGTH_LONG).show();

                }
            });


            play.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    MediaPlayer mediaPlayer = new MediaPlayer();


                    try {
                        mediaPlayer.setDataSource(mFileName);
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                        Toast.makeText(getApplicationContext(), "lecture en cours", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });

        }


    }



    public void AlertAudio(){
        AlertDialog alertdialog = new AlertDialog.Builder(this).create();
        alertdialog.setTitle("Permission audio");
        alertdialog.setMessage("Cette permission est nécessaire pour enregistrer l'audio");
        alertdialog.setButton(AlertDialog.BUTTON_NEGATIVE, "plus tard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "permission refusée", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });
        alertdialog.setButton(AlertDialog.BUTTON_POSITIVE,"ok ", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
                ActivityCompat.requestPermissions(RecordActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},MY_PERMISSION_REQUEST_RECORD_AUDIO);

            }
        });
        alertdialog.show();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_RECORD_AUDIO: {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]);
                    if (showRationale) {
                        AlertAudio();
                    } else if (!showRationale) {
                        Toast.makeText(getApplicationContext(), "pour donner la permission allez sur reglage -> application -> android_permission_record_final -> autorisations -> autoriser microphone", Toast.LENGTH_LONG).show();
                        finish();
                        // l'utilisateur a refusé la permission
                        //vous pouvez soit activer un peu de recul,
                        //désactiver les fonctionnalités de votre application
                        //ou ouvrez une autre boîte de dialogue expliquant
                        //encore une fois l'autorisation et la direction de l'application
                    }
                } else {
                    //permission accordée
                }

            }

        }
    }


}
