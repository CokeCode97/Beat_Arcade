package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by Administrator on 2017-12-01.
 */

public class ShootingBackground extends Graphic {
    public ShootingBackground  () {
        super((AppManager.getInstance().getBitmap(R.drawable.shootingback)));
        this.InitSpriteData(1080, 1420);
        setPosition(500, 0);
    }
}
