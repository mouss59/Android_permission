package com.example.elazzam.android_permission_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button Record,Camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    Record = (Button) findViewById(R.id.Record);
    Camera = (Button) findViewById(R.id.Camera);



        Record.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyIntent=new Intent(v.getContext() ,RecordActivity.class);
                startActivity(MyIntent);
            }
        });

        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),CameraActivity.class);
                startActivity(intent);
            }
        });





    }
}
