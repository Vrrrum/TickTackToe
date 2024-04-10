package com.example.ticktacktoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    int activePlayer = 1; // 1 = X, 2 = O, 0 = end of game
    int[] board = {0, 0, 0, 0, 0, 0, 0, 0, 0}; // 0 = empty, 1 = X, 2 = O
    TextView turn_tv = null;
    int MovesCount = 9;
    GridLayout gridLayout;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        turn_tv = findViewById(R.id.turn_tv);

        // Popup
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.end_popup, null);
    }

    public void onSquareClick(View view) {
        ImageView square = findViewById(view.getId());
        int squareValue = Integer.parseInt(square.getTag().toString());

        if(board[squareValue] != 0 || activePlayer == 0) return;

        board[squareValue] = activePlayer;
        if(activePlayer == 1) {
            activePlayer = 2;
            square.setImageResource(R.drawable.x);
            if(CheckDraw()){
                turn_tv.setText(R.string.draw);
                ShowPopup("Draw!");
            }else {
                turn_tv.setText(R.string.o_round);
            }
        } else {
            activePlayer = 1;
            square.setImageResource(R.drawable.o);
            if(CheckDraw()){
                turn_tv.setText(R.string.draw);
                ShowPopup("Draw!");
            }else {
                turn_tv.setText(R.string.x_round);
            }
        }

        if (CheckWin(board)) {
            turn_tv.setText(activePlayer == 1 ? "O wins!" : "X wins!");
            ShowPopup(activePlayer == 1 ? "O wins!" : "X wins!");
            activePlayer = 0;
        }

    }

    public boolean CheckWin(int[] board){
        int[][] winList = {
                {0,1,2}, {3,4,5}, {6,7,8},
                {0,3,6}, {1,4,7}, {2,5,8},
                {0,4,8}, {2,4,6}
        };

        for (int[] winCondition : winList) {
            if (board[winCondition[0]] != 0 && board[winCondition[0]] == board[winCondition[1]] && board[winCondition[0]] == board[winCondition[2]]) {
                return true;
            }
        }
        return false;
    }

    public boolean CheckDraw(){
        MovesCount--;

        if(MovesCount == 0){
            activePlayer = 0;
            return true;
        }

        return false;
    };

    public void RestartGame() {
        Arrays.fill(board, 0);

        ((ImageView) findViewById(R.id.sqr1)).setImageResource(0);
        ((ImageView) findViewById(R.id.sqr2)).setImageResource(0);
        ((ImageView) findViewById(R.id.sqr3)).setImageResource(0);
        ((ImageView) findViewById(R.id.sqr4)).setImageResource(0);
        ((ImageView) findViewById(R.id.sqr5)).setImageResource(0);
        ((ImageView) findViewById(R.id.sqr6)).setImageResource(0);
        ((ImageView) findViewById(R.id.sqr7)).setImageResource(0);
        ((ImageView) findViewById(R.id.sqr8)).setImageResource(0);
        ((ImageView) findViewById(R.id.sqr9)).setImageResource(0);

        activePlayer = 1;
        turn_tv.setText(R.string.x_round);
        MovesCount = 9;
    }

    public void ShowPopup(String popupText) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.end_popup, null);


        int width = 850;
        int height = 1000;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        TextView tv1 = popupView.findViewById(R.id.textPopup_tv);
        tv1.setText(popupText);

        Button restart_btn = popupView.findViewById(R.id.restart_btn);
        restart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                RestartGame();
            }
        });

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

}
