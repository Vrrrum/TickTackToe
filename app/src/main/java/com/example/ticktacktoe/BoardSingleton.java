package com.example.ticktacktoe;

import android.view.View;
import android.widget.ImageView;
import java.util.Arrays;

public class BoardSingleton {
    private static BoardSingleton INSTANCE;
    private int[] _boardStates;
    private View _boardView;

    BoardSingleton(View boardView) {
        _boardStates = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        _boardView = boardView;
    }

    public static BoardSingleton getInstance(View boardView) {
        if(INSTANCE == null) {
                INSTANCE = new BoardSingleton(boardView);
        }

        return INSTANCE;
    }
    
    public boolean setSquare(int squareId, int playerId) { // playerId: X-1; O-2; 0-empty
        if(_boardStates[squareId] != 0) return false;
        _boardStates[squareId] = playerId;

        return true;
    }

    public int getSquare(int squareId) {
        return _boardStates[squareId];
    }

    public void Reset() {
        Arrays.fill(_boardStates, 0);
    }

    public void Update() {
        for(int i=0; i<_boardStates.length; i++) {
            ImageView square = _boardView.findViewWithTag(Integer.toString(i));

            if(_boardStates[i] == 1) {
                square.setImageResource(R.drawable.x);
            } else if(_boardStates[i] == 2) {
                square.setImageResource(R.drawable.o);
            } else {
                square.setImageResource(0);
            }
        }
    }
}
