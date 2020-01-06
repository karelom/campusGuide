package com.example.campus_tour;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
/*support & androidx 會衝突*/
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button2, button4;

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

            //建立button，導到Google map 頁面
            Button map = findViewById(R.id.button);
            map.setOnClickListener(mapClick);
        }// 直立式
        else {
            getSupportActionBar().hide();// 設定隱藏標題
            Toast toast = Toast.makeText(context, text2, duration);
            toast.show();

            button2 = findViewById(R.id.button2);// 3館
            button2.setOnClickListener(buttonClick);

            button4 = findViewById(R.id.button4);// 哲學之道
            button4.setOnClickListener(buttonClick);
        }// 橫式地圖

    }

    private View.OnClickListener mapClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        }
    };// 開啟Google map

    private View.OnClickListener buttonClick = new View.OnClickListener() {
        String image;

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(MainActivity.this, GuideActivity.class);
            Bundle bundle = new Bundle();

            if (v.getId() == button2.getId()){
                image = "building3";
            }// 3館
            else if (v.getId() == button4.getId()){
                image = "pathOfPhilosophy";
            }// 哲學之道

            bundle.putString("image", image);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    };// 開啟各點資訊

}
