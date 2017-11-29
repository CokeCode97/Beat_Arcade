package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-29.
 */

public class RhythmJudge extends SpriteAnimation {
    GameState gs;
    int note_x = 113, note_y = 200, noteNum;
    float note_speed = 0.01f;

    public RhythmJudge(Bitmap bitmap, GameState gs) {
        //애니메이션 정보설정
        super(bitmap);
        this.InitSpriteData(82, 274, 1, 1);
        //위치 세팅
        setPosition(note_x, note_y);
        this.gs = gs;
    }

    public void Move(float x, float y) {
        this.note_x += x;
        this.note_y += y;
        this.setPosition(note_x, note_y);
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);
        Move(0, -note_speed);
        if(note_y < 170) {
            gs.removeJudge(this);
        }
    }
}

