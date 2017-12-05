package com.example.administrator.framework;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017-11-27.
 */

public interface IState {

    //이 상대로 바뀌었을때 실행
    public void Init();

    //다른 상태로 바뀔때 실행
    public void Destroy();

    //지속적으로 수행
    public void Update();

    //그려야 할것들
    public void Render(Canvas canvas);

    //키보드 입력
    public boolean onKeyDwon(int Keycode, KeyEvent event);

    //터치입력
    public boolean onTouchEvent(MotionEvent event);
}
