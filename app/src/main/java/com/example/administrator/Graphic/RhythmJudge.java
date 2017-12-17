package com.example.administrator.Graphic;

import android.graphics.Bitmap;

import com.example.administrator.framework.ObjectManager;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-29.
 */

//리듬판정을 그리는객체
public class RhythmJudge extends SpriteAnimation {
    int note_x = 113, note_y = 200;
    float note_speed = 0.03f;

    //생성될때 GameState와 Bitmap데이터를 가져와 상황에 맞는 이미지 출력
    //bitamp = 출력될 비트맵 데이터
    public RhythmJudge(Bitmap bitmap) {
        //애니메이션 정보설정
        super(bitmap);
        this.InitSpriteData(82, 274, 1, 1);
        //위치 세팅
        setPosition(note_x, note_y);
        //GameState설정
    }

    //Move
    //x, y 이것이 움직일 좌표들
    public void Move(float x, float y) {
        this.note_x += x;
        this.note_y += y;
        this.setPosition(note_x, note_y);
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);

        //170위로 올라가면 파괴
        Move(0, -note_speed);
        if(note_y < 175) {
            ObjectManager.removeJudge(this);
        }
    }
}

