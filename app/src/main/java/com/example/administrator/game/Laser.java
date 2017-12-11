package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-12-02.
 */

public class Laser extends SpriteAnimation {
    long time_Make;
    Player player;

    public Laser(Bitmap bitmap, Player player, long makeTime) {
        super(bitmap);
        this.InitSpriteData(150, 150, 1, 1);
        this.time_Make = makeTime;
        this.player = player;
        this.setPosition(player.getX(), player.getY());
    }

    public void Update(long Gametime) {
        if(Gametime - time_Make > 1000) {
            player.remove_Laser(this);
        } else {
            this.setPosition(player.getX(), player.getY());
        }
    }
}
