package com.example.ticktacktoe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class MainActivity extends AppCompatActivity implements CallApi.APICallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvHint = findViewById(R.id.tvHint);
        Animation pulseAnimation = new ScaleAnimation(1f, 1.15f, 1f, 1.15f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        pulseAnimation.setDuration(700);
        pulseAnimation.setRepeatMode(Animation.REVERSE);
        pulseAnimation.setRepeatCount(Animation.INFINITE);
        tvHint.startAnimation(pulseAnimation);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Game.class);
                startActivity(intent);
            }
        });

        CallApi apiCallTaskForFunnyMessage = new CallApi(new CallApi.APICallback() {
            @Override
            public void onResult(String result) {
                TextView tvHint = findViewById(R.id.tvHint);
                tvHint.setText(result);
            }
        });
        apiCallTaskForFunnyMessage.execute("Write (max 7 words) funny message");
        CallApi apiCallTaskForTitle = new CallApi(new CallApi.APICallback() {
            @Override
            public void onResult(String result) {
                TextView title = findViewById(R.id.tvFunFact);
                title.setText(result);
            }
        });
        apiCallTaskForTitle.execute("Write fun fact about tic tac toe be creative");

    }

    @Override
    public void onResult(String result) {

    }
}
