package com.example.campus_tour;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GuideActivity extends AppCompatActivity {
    ImageView image;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        getSupportActionBar().hide();// 設定隱藏標題
        image = findViewById(R.id.imageView2);
        description = findViewById(R.id.textView);

        Bundle bundle =this.getIntent().getExtras();// 取得 intent 中的 bundle 物件
        String imageChoose = bundle.getString("image");
        chooseLocation(imageChoose);

        Button back = findViewById(R.id.button3);
        back.setOnClickListener(backClick);
    }

    private View.OnClickListener backClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GuideActivity.this.finish();
        }
    };// 返回鍵

    private void chooseLocation(String imageChoose){
        switch(imageChoose){
            case "building3":
                image.setImageDrawable(getResources().getDrawable(R.drawable.yzu_building3));
                description.setText(getResources().getString(R.string.image1_content));
                break;
            case "pathOfPhilosophy":
                image.setImageDrawable(getResources().getDrawable(R.drawable.path_of_philosophy));
                description.setText(getResources().getString(R.string.image2_content));
                break;
            default :
                break;
        }
    }
}
