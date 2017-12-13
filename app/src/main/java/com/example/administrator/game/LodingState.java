package com.example.administrator.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.administrator.framework.IState;
import com.example.administrator.networks.ClientWork;

/**
 * Created by Administrator on 2017-12-13.
 */

public class LodingState implements IState {
    @Override
    public void Init() {

    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Canvas canvas) {

    }

    @Override
    public boolean onKeyDwon(int Keycode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ClientWork.write("Ready");
        return false;
    }
}
