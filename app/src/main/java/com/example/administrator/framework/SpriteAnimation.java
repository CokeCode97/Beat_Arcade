package com.example.administrator.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by mac on 2017. 11. 28..
 */

public class SpriteAnimation extends GraphicObject {
    private Rect rect;      //사각영역
    private int fps;        //초당 프레임
    private int iFrames;    //프레임개수

    private int currentFrame;   //최근 프레임
    private long frameTimer;
    private  int spriteWidth;
    private  int spriteHeight;


    public SpriteAnimation(Bitmap bitmap) {
        super(bitmap);
        //멤버변수 초기화
        rect = new Rect(0, 0, 0, 0);
        frameTimer = 0;
        currentFrame = 0;
    }

    //스프라이트 애니메이션이 시작될때 멤버변수들을 세팅해줌
    public void InitSpriteData(int Height, int Width, int theFPS, int theFrmaeCount) {
        spriteHeight = Height;
        spriteWidth = Width;
        rect.top = 0;
        rect.bottom = spriteHeight;
        rect.left = 0;
        rect.right = spriteWidth;
        fps = 1000 / theFPS;
        iFrames = theFrmaeCount;
    }


    //사각형 범위만큼을 그려줌
    @Override
    public void Draw(Canvas canvas) {
        Rect dest = new Rect(x, y, (int)(x+spriteWidth*GameActivity.size), (int)(y+spriteHeight*GameActivity.size));
        canvas.drawBitmap(bitmap, rect, dest, null);
    }


    public void Update(long GameTime) {
        //모든 프레임을 한번씩 출력하도록 해줌
        if(GameTime > frameTimer + fps) {
            frameTimer = GameTime;
            currentFrame += 1;
            if(currentFrame >= iFrames) {
                currentFrame = 0;
            }
        }
        rect.left = currentFrame * spriteWidth;
        rect.right = rect.left + spriteWidth;
    }

    public int getSpriteWidth() {
        return (int)(spriteWidth*GameActivity.size);
    }

    public int getSpriteHeight() {
        return (int)(spriteHeight*GameActivity.size);
    }
}
