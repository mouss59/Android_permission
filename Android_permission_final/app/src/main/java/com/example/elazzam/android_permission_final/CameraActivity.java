package com.example.elazzam.android_permission_final;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CameraActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_CAMERA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);

        Button CameraButton = (Button)findViewById(R.id.CameraButton);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)  != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                //permission deja demander mais elle a etait refuser soit on affiche un message pour qu'il autorise a partir des reglage ou bien
                //on explique a l utilisateur pk cette permission est obligatoir pour le fonctionnement de l'application
                //avec une alerte si il accepte on redemande la permission

                 AlertCamera();

            }else{

                //si on a jamais demander la permission c'est l'occasion pour la demander

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},MY_PERMISSION_REQUEST_CAMERA );

            }

        }else {
            CameraButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, 0);

                }
            });

        }
    }

    public void AlertCamera() {
        AlertDialog alertdialog = new AlertDialog.Builder(this).create();
        alertdialog.setTitle("Permission audio");
        alertdialog.setMessage("Cette permission est nécessaire pour enregistrer l'audio ");
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
                ActivityCompat.requestPermissions(CameraActivity.this,new String[]{Manifest.permission.CAMERA},MY_PERMISSION_REQUEST_CAMERA);

            }
        });
        alertdialog.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]);
                    if (showRationale) {
                        AlertCamera();
                    } else if (!showRationale) {
                        Toast.makeText(getApplicationContext(), "pour donner la permission allez sur reglage -> application -> android_permission_record_final -> autorisations -> autoriser du microphone", Toast.LENGTH_LONG).show();
                        finish();
                        // l'utilisateur a refusé la permission
                        //vous pouvez soit activer un peu de recul,
                        //désactiver les fonctionnalités de votre application
                        //ou ouvrez une autre boîte de dialogue expliquant
                        //encore une fois l'autorisation et la direction de l'application
                    }
                }

                else{
                    //permission accordée
                }

            }


        }
    }



}
