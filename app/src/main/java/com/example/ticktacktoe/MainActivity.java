package com.example.ticktacktoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    short activePlayer = 1; // 1 = X, 2 = O, 0 = endGame
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

        if(board[squareValue] != 0 || activePlayer == 0) return;

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

        if (CheckWin(board)) {
            turn_tv.setText(activePlayer == 1 ? "O wins!" : "X wins!");
            activePlayer = 0;
        }
    }

    public boolean CheckWin(short[] board){
        short[][] winList = {
                {0,1,2}, {3,4,5}, {6,7,8},
                {0,3,6}, {1,4,7}, {2,5,8},
                {0,4,8}, {2,4,6}
        };

        for (short[] winCondition : winList) {
            if (board[winCondition[0]] != 0 && board[winCondition[0]] == board[winCondition[1]] && board[winCondition[0]] == board[winCondition[2]]) {
                return true;
            }
        }
        return false;
    }
}
