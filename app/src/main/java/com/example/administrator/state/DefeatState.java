package com.example.administrator.state;

import android.content.Context;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.administrator.framework.IState;
import com.example.administrator.framework.ObjectManager;

/**
 * Created by mac on 2017. 12. 17..
 */

public class DefeatState implements IState {
    @Override
    public void Init(Context context) {

    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Canvas canvas) {
        ObjectManager.resultBack.Draw(canvas);
        ObjectManager.defeat.Draw(canvas);
    }

    @Override
    public boolean onKeyDwon(int Keycode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
