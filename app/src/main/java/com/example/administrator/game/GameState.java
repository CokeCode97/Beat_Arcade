package com.example.administrator.game;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.IState;
import com.example.administrator.framework.R;

/**
 * Created by Administrator on 2017-11-28.
 */

public class GameState implements IState {

    private Mob mob;

    @Override
    public void Init() {
        mob = new Mob(AppManager.getInstance().getBitmap(R.drawable.mob_sprite));
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {
        long GameTime = System.currentTimeMillis();
        mob.Update(GameTime);
    }

    @Override
    public void Render(Canvas canvas) {
        mob.Draw(canvas);
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
