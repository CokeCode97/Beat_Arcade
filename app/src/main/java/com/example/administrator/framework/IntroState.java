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
    Bitmap icon;
    int x;
    int y;
    Random r = new Random();

    @Override
    public void Init() {
        icon = AppManager.getInstance().getBitmap(R.drawable.mob);
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Canvas canvas) {
        canvas.drawBitmap(icon,x,y,null);
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
