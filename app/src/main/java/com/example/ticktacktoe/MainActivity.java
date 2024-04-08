package com.example.ticktacktoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    short activePlayer = 1; // 1 = X, 2 = O
    short[] board = {0, 0, 0, 0, 0, 0, 0, 0, 0}; // 0 = empty, 1 = X, 2 = O

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSquareClick(View view) {
        TextView turn_tv = findViewById(R.id.turn_tv);
        ImageView square = findViewById(view.getId());
        int squareValue = Integer.parseInt(square.getTag().toString());

        if(board[squareValue] != 0) return;

        board[squareValue] = activePlayer;
        if(activePlayer == 1) {
            activePlayer = 2;
            square.setImageResource(R.drawable.x);
            turn_tv.setText("O turn");
        } else {
            activePlayer = 1;
            square.setImageResource(R.drawable.o);
            turn_tv.setText("X turn");
        }
    }
}