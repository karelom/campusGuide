package com.example.campus_tour;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        CharSequence text1 = "This is in Portrait Mode.";
        CharSequence text2 = "This is in Landscape Mode.";
        int duration = Toast.LENGTH_SHORT;

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast toast = Toast.makeText(context, text1, duration);
            toast.show();
        } else {
            Toast toast = Toast.makeText(context, text2, duration);
            toast.show();
        }

    }
}
