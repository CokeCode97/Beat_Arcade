package com.example.administrator.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Administrator on 2017-12-01.
 */

public class Graphic extends GraphicObject {
    private Rect rect;      //사각영역
    private  int spriteWidth;
    private  int spriteHeight;
    private float size = 0;

    public Graphic(Bitmap bitmap) {
        super(bitmap);
        rect = new Rect(0, 0, 0, 0);
    }

    public void InitSpriteData(int Height, int Width, float size) {
        spriteHeight = Height;
        spriteWidth = Width;
        rect.top = 0;
        rect.bottom = spriteHeight;
        rect.left = 0;
        rect.right = spriteWidth;
        this.size = size;
    }

    @Override
    public void Draw(Canvas canvas) {
        Rect dest = new Rect(x, y, (int)(x+spriteWidth*GameActivity.size*size), (int)(y+spriteHeight*GameActivity.size*size));
        canvas.drawBitmap(bitmap, rect, dest, null);
    }

    public int getSpriteWidth() {
        return (int)(spriteWidth*GameActivity.size);
    }

    public int getSpriteHeight() {
        return (int)(spriteHeight*GameActivity.size);
    }
}
