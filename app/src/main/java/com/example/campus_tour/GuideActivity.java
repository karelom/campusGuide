package com.example.campus_tour;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //設定隱藏標題
        getSupportActionBar().hide();

        Button back = findViewById(R.id.button3);
        back.setOnClickListener(backClick);

        TextView description = findViewById(R.id.textView);
        description.setText("元智大學三館教學樓");
    }

    private View.OnClickListener backClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GuideActivity.this.finish();
        }
    };// 返回鍵
}
