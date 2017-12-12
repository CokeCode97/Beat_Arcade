package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by mac on 2017. 12. 12..
 */

public class Hp_Red extends Graphic {
    int width = 235, height = 11;
    public Hp_Red(int x) {
        super(AppManager.getInstance().getBitmap(R.drawable.hp_red));
        this.InitSpriteData(height, width, 2.5f);
        setPosition(556 + x, 54);
    }

    //체력바 렌더링을 조절함
    public void hp_Update(double ratio) {
        this.InitSpriteData(height, (int)(width*ratio), 2.5f);
    }
}
