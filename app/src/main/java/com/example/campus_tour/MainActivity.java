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
    private Button button2, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14, button15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        CharSequence text1 = "This is in Portrait Mode.";
        CharSequence text2 = "This is in Landscape Mode.";
        int duration = Toast.LENGTH_SHORT;

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            Toast toast = Toast.makeText(context, text1, duration);
//            toast.show();

            //建立button，導到Google map 頁面
            Button map = findViewById(R.id.button);
            map.setOnClickListener(mapClick);
        }// 直立式
        else {
            getSupportActionBar().hide();// 設定隱藏標題
//            Toast toast = Toast.makeText(context, text2, duration);
//            toast.show();

            button2 = findViewById(R.id.button2);// 3館
            button2.setOnClickListener(buttonClick);
            button4 = findViewById(R.id.button4);// 哲學之道
            button4.setOnClickListener(buttonClick);
            button5 = findViewById(R.id.button5);// 1館
            button5.setOnClickListener(buttonClick);
            button6 = findViewById(R.id.button6);// 6館
            button6.setOnClickListener(buttonClick);
            button7 = findViewById(R.id.button7);// 5館
            button7.setOnClickListener(buttonClick);
            button8 = findViewById(R.id.button8);// 2館
            button8.setOnClickListener(buttonClick);
            button9 = findViewById(R.id.button9);// 7館
            button9.setOnClickListener(buttonClick);
            button10 = findViewById(R.id.button10);// 無限延伸
            button10.setOnClickListener(buttonClick);
            button11 = findViewById(R.id.button11);// 櫻花巷
            button11.setOnClickListener(buttonClick);
            button12 = findViewById(R.id.button12);// 思新石
            button12.setOnClickListener(buttonClick);
            button13 = findViewById(R.id.button13);// 戲綠塘
            button13.setOnClickListener(buttonClick);
            button14 = findViewById(R.id.button14);// 牡丹亭
            button14.setOnClickListener(buttonClick);
            button15 = findViewById(R.id.button15);// 操場
            button15.setOnClickListener(buttonClick);
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
            else if (v.getId() == button5.getId()){
                image = "building1";
            }// 1館
            else if (v.getId() == button6.getId()){
                image = "building6";
            }// 6館
            else if (v.getId() == button7.getId()){
                image = "building5";
            }// 5館
            else if (v.getId() == button8.getId()){
                image = "building2";
            }// 2館
            else if (v.getId() == button9.getId()){
                image = "building7";
            }// 7館
            else if (v.getId() == button10.getId()){
                image = "infinite";
            }// 無限延伸
            else if (v.getId() == button11.getId()){
                image = "sakura";
            }// 櫻花巷
            else if (v.getId() == button12.getId()){
                image = "stone21";
            }// 思新石
            else if (v.getId() == button13.getId()){
                image = "pond";
            }// 戲綠塘
            else if (v.getId() == button14.getId()){
                image = "gazebo";
            }// 牡丹亭
            else if (v.getId() == button15.getId()){
                image = "playground";
            }// 操場

            bundle.putString("image", image);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    };// 開啟各點資訊

}
