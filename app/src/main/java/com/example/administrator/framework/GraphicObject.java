package com.example.administrator.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.administrator.activities.GameActivity;

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

    //해당 객체의 포지션을 지정
    public void setPosition(int x, int y) {
        this.x = (int)(x * GameActivity.SIZE_X);
        this.y = (int)(y * GameActivity.SIZE_Y);
    }

    //해당 객체를 그려줌
    public void Draw(Canvas canvas) {
        //비트맵 x좌표 y좌표순 마지막은 모름
        canvas.drawBitmap(bitmap, x, y, null);
    }

    //1920으로 통일된 가상의 좌표값을 리턴
    public int getX(){
        return (int)(x/GameActivity.SIZE_X);
    }

    public int getY(){
        return (int)(y/GameActivity.SIZE_Y);
    }

    //실제 좌표값을 리턴 터치좌표를 지정할때 유용
    public int getX_R() {return x;}

    public int getY_R() {return y;}
}
