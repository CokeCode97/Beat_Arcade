package com.example.administrator.Graphic;

import android.graphics.Bitmap;

import com.example.administrator.framework.Graphic;

/**
 * Created by mac on 2017. 12. 17..
 */

public class LodingImage extends Graphic {
    public LodingImage(Bitmap bitmap) {
        super(bitmap);
        this.InitSpriteData(350, 350, 1.6f);
        this.setPosition(680, 130);
    }
}
