package com.example.administrator.framework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.administrator.game.GameState;

/**
 * Created by Administrator on 2017-11-27.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    //게임뷰 스레드& IState 저장
    public GameViewThread gameview_thread;
    private IState istate;
    private GameState gs;

    //게임뷰의 생성자
    public GameView(Context context) {
        super(context);

        //키입력을 받기위해 포커스 지정
        setFocusable(true);

        //앱매니저의 정보를 가져와 게임뷰와 리소스를 세팅
        AppManager.getInstance().setGameView(this);
        AppManager.getInstance().setResources(getResources());

        //홀더를 가져옴
        getHolder().addCallback(this);
        //게임뷰 스레드에 홀더와 게임뷰를 넘겨줌
        gameview_thread = new GameViewThread(getHolder(), this);
        gs = new GameState();
        changeGameState(gs);
        gs.start();
    }

    //화면을 그려주는 온드로우() 메소드
    @Override
    public void onDraw(Canvas canvas) {
        //화면을 검게 칠함
        //canvas.drawColor(Color.BLACK);
        //istate 랜더를 불러들여 istate
        gs.setCheck(true);
        istate.Render(canvas);
    }


    //화면의 업데이트를 적용해줄 업데이트 함수
    public void Update() {
        //istate의 업데이트를 지속적으로 호출하여 istate를 상속받은 모든 클래스들이 update함수를 활용할수 있게 해줌
        //istate.Update();
    }


    //게임뷰의 상태를 바꿔줌
    public void changeGameState(IState istate) {
        //만약 현재 istate에 무엇인가 있다면 파괴하고
        if(this.istate != null) {
            this.istate.Destroy();
        }
        //새 istate를 적용시킴
        istate.Init();
        this.istate = istate;
    }


    //서페이스가 실행될때
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // 스레드를 실행 상태로 만듭니다.
        gameview_thread.setRunnig(true);
        //스레드 시작
        gameview_thread.start();
    }


    //서페이스가 바뀔때
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    //서페이스가 파괴될때
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameview_thread.setRunnig(false);
        while(retry) {
            try {
                    //스레드 중지
                    gameview_thread.join();
                    retry = false;
            } catch(InterruptedException e) {
                // 스레드가 종료되도록 계속 시도
            }
        }
    }


    //입력에 관한 내용
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        istate.onKeyDwon(keyCode, event);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        istate.onTouchEvent(event);
        invalidate();
        return true;
    }
}
