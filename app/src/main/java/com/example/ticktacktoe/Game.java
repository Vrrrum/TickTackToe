package com.example.ticktacktoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Arrays;

public class Game extends AppCompatActivity {

    private int _gameStateId = 1; // 1 = X, 2 = O, 0 = end of game
    private BoardSingleton _boardInstance;
    private TextView _turn_tv;
    private int _movesCount = 0;
    private LayoutInflater _inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        _boardInstance = BoardSingleton.getInstance(findViewById(R.id.board_gl));

        _turn_tv = findViewById(R.id.turn_tv);

        _inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = _inflater.inflate(R.layout.end_popup, null);
    }

    public void onSquareClick(@NonNull View view) {
        ImageView square = findViewById(view.getId());
        int squareValue = Integer.parseInt(square.getTag().toString());

        if(_boardInstance.setSquare(squareValue, _gameStateId)) {
            _gameStateId = (_gameStateId == 1)? 2:1;

            if(_gameStateId == 1) {
                _turn_tv.setText(R.string.x_round);
            }else{
                _turn_tv.setText(R.string.o_round);
            }

            _boardInstance.Update();
        }

        if(CheckDraw()) {
            ShowPopup("Draw!");
            return;
        }

        if(CheckWin()) {
            ShowPopup(_gameStateId == 1 ? "O wins!" : "X wins!");
            return;
        }

        _movesCount++;
    }

    public boolean CheckWin() {
        if (_movesCount > 3) {
            int[][] winList = {
                    {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                    {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                    {0, 4, 8}, {2, 4, 6}
            };

            for (int[] winCondition : winList) {
                if (_boardInstance.getSquare(winCondition[0]) != 0 &&
                        _boardInstance.getSquare(winCondition[0]) == _boardInstance.getSquare(winCondition[1]) &&
                        _boardInstance.getSquare(winCondition[0]) == _boardInstance.getSquare(winCondition[2])) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean CheckDraw(){
        if(_movesCount == 8){
            _gameStateId = 0;
            return true;
        }

        return false;
    }

    public void RestartGame() {
        _gameStateId = 1;
        _turn_tv.setText(R.string.x_round);
        _movesCount = 0;
        _boardInstance.Reset();
        _boardInstance.Update();
    }

    public void ShowPopup(String popupText) {

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.end_popup, null);

        int width = 850;
        int height = 1000;
        boolean focusable = true;
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
