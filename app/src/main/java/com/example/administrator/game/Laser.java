package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-12-02.
 */

public class Laser extends SpriteAnimation {
    long makeTime;
    Mob m;
    public Laser(Mob m, long makeTime) {
        super((AppManager.getInstance().getBitmap(R.drawable.circle2)));
        this.InitSpriteData(150, 150, 1, 1);
        this.makeTime = makeTime;
        this.m = m;
        this.setPosition(m.getX(), m.getY());
    }

    public void Update(long Gametime) {
        if(Gametime - makeTime > 1000) {
            m.deleteLaser(this);
        }
    }
}
