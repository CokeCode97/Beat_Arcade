package com.example.administrator.Graphic;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by mac on 2017. 12. 17..
 */

public class LodingBarFront extends Graphic {
    int lodingbar_Width = 349;
    float loding_Per = 0;
    public LodingBarFront() {
        super(AppManager.getInstance().getBitmap(R.drawable.lodingbar_front));
        this.InitSpriteData(25, 0, 3f);
        this.setPosition(435, 815);
    }

    public void Update() {
        if(loding_Per <= 1) {
            loding_Per += 0.01f;
            this.InitSpriteData(25, (int)(lodingbar_Width*loding_Per), 3f);
        }
    }
}
