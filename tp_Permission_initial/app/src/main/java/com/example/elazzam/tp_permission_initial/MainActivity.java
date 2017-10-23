package com.example.elazzam.tp_permission_initial;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_RECORD_AUDIO = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        }


    }


    //alert Pour expliqué a l'utilisateur que la permission est nécessaire pour enregistrer l'audio 
    public void AlertAudio() {
        AlertDialog alertdialog = new AlertDialog.Builder(this).create();
        alertdialog.setTitle("Permission audio");
        alertdialog.setMessage("Cette permission est nécessaire pour enregistrer l'audio");
        alertdialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Je veux pas !!!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "permission refuser", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });
        alertdialog.setButton(AlertDialog.BUTTON_POSITIVE,"ok ", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},MY_PERMISSION_REQUEST_RECORD_AUDIO);

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
                        Toast.makeText(getApplicationContext(), "pour donner la permission allez sur reglage -> application -> android_permission_initial -> autorisations -> autoriser du microphone",
                                Toast.LENGTH_LONG).show();

                        //l'utilisateur a choisi ne plus jamais redemander
                        //vous pouvez soit activer un peu de recul,
                        //désactiver les fonctionnalités de votre application
                        //ou ouvrez une autre boîte de dialogue expliquant encore une fois l'autorisation et la direction de l'application "je vous conseil pas de le faire"
                    }
                }

                else{
                    //permission accordée
                }

            }

        }
    }




}


