package com.example.administrator.Graphic;

import android.graphics.Bitmap;

import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.ObjectManager;

/**
 * Created by Administrator on 2017-12-15.
 */

public class SlowEffect extends Graphic {
    //만들어진 시간
    long time_Make;

    public SlowEffect(Bitmap bitmap, long time_Make, int x , int y) {
        super(bitmap);
        this.time_Make = time_Make;
        this.InitSpriteData(100, 100, 1);
        this.setPosition(x, y);
    }

    //만들어진지 1초가 지나면 파괴
    public void Update(long GameTime) {
        if(GameTime - time_Make > 1000) {
            ObjectManager.removeSlow_Effect(this);
        }
    }
}
