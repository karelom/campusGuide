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
            case "building1":
                image.setImageDrawable(getResources().getDrawable(R.drawable.yzu_building1));
                description.setText(getResources().getString(R.string.image3_content));
                break;
            case "building6":
                image.setImageDrawable(getResources().getDrawable(R.drawable.yzu_building6));
                description.setText(getResources().getString(R.string.image4_content));
                break;
            case "building5":
                image.setImageDrawable(getResources().getDrawable(R.drawable.yzu_building5));
                description.setText(getResources().getString(R.string.image5_content));
                break;
            case "building2":
                image.setImageDrawable(getResources().getDrawable(R.drawable.yzu_building2));
                description.setText(getResources().getString(R.string.image6_content));
                break;
            case "building7":
                image.setImageDrawable(getResources().getDrawable(R.drawable.yzu_building7));
                description.setText(getResources().getString(R.string.image7_content));
                break;
            case "infinite":
                image.setImageDrawable(getResources().getDrawable(R.drawable.infinite));
                description.setText(getResources().getString(R.string.image8_content));
                break;
            case "sakura":
                image.setImageDrawable(getResources().getDrawable(R.drawable.sakura_alley));
                description.setText(getResources().getString(R.string.image9_content));
                break;
            case "stone21":
                image.setImageDrawable(getResources().getDrawable(R.drawable.stone21));
                description.setText(getResources().getString(R.string.image10_content));
                break;
            case "pond":
                image.setImageDrawable(getResources().getDrawable(R.drawable.pond));
                description.setText(getResources().getString(R.string.image11_content));
                break;
            case "gazebo":
                image.setImageDrawable(getResources().getDrawable(R.drawable.peony_gazebo));
                description.setText(getResources().getString(R.string.image12_content));
                break;
            case "playground":
                image.setImageDrawable(getResources().getDrawable(R.drawable.playground));
                description.setText(getResources().getString(R.string.image13_content));
                break;
            default :
                break;
        }
    }
}
