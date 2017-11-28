package com.example.administrator.game;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.IState;
import com.example.administrator.framework.R;

import java.util.Vector;

/**
 * Created by Administrator on 2017-11-28.
 */

public class GameState implements IState {

    Vector<Mob> mv = new Vector<Mob>();
    long Last = System.currentTimeMillis();

    @Override
    public void Init() {
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {
        long GameTime = System.currentTimeMillis();
        for(Mob mob : mv) {
            mob.Update(GameTime);
        }
        MakeMob();
    }

    public void MakeMob() {
        if(System.currentTimeMillis() - Last >= 1000) {
            Last = System.currentTimeMillis();
            Mob mob = new Mob();
            mv.add(mob);
        }
    }

    @Override
    public void Render(Canvas canvas) {
        for(Mob mob : mv) {
            mob.Draw(canvas);
        }
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
