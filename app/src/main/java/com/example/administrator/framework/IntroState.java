package com.example.administrator.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import java.util.Random;

/**
 * Created by mac on 2017. 11. 28..
 */

public class IntroState implements IState {

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
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
