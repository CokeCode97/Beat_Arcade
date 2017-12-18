package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.ObjectManager;
import com.example.administrator.framework.R;

/**
 * Created by mac on 2017. 12. 13..
 */

public class HP_Red extends Graphic {
    private int width = 368, height = 44;
    public HP_Black hp_black;
    public HP_Red(HP_Black hp_black) {
        super(AppManager.getInstance().getBitmap(R.drawable.hp_red));
        this.hp_black = hp_black;
        this.InitSpriteData(height, width, 1.5f);
        setPosition(hp_black.getX() + 25, 48);
    }

    public void Update(long GameTime) {
    }

    //TODO 체력바 렌더링을 조절함
    //ratio = 최대체력과 현재체력의 비율
    public void hp_Update(double ratio) {
        this.InitSpriteData(height, (int)(width*ratio), 1.5f);
        ObjectManager.hp_Yellow_Vector.get(ObjectManager.hp_Red_Vector.indexOf(this)).setHP_Ratio(ratio);
    }
}

