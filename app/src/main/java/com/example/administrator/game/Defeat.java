package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by mac on 2017. 12. 17..
 */

public class Defeat extends Graphic {
    int[] a = new int[3];
    public Defeat() {
        super(AppManager.getInstance().getBitmap(R.drawable.defeat));
        this.InitSpriteData(639, 576, 1);
        setPosition(672, 220);
    }
}
