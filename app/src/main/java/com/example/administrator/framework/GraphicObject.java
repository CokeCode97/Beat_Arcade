package com.example.administrator.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by mac on 2017. 11. 28..
 */

public class GraphicObject {
    protected Bitmap bitmap;
    protected int x;
    protected int y;

    public GraphicObject(Bitmap bitmap) {
        this.bitmap = bitmap;
        x = 0;
        y = 0;
    }

    public void setPosition(int x, int y) {
        this.x = (int)(x * GameActivity.size);
        this.y = (int)(y * GameActivity.size);
    }

    public void Draw(Canvas canvas) {
        //비트맵 x좌표 y좌표순 마지막은 모름
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public int getX(){
        return (int)(x/GameActivity.size);
    }

    public int getY(){
        return (int)(y/GameActivity.size);
    }

    public int getX_R() {return x;}

    public int getY_R() {return y;}
}
