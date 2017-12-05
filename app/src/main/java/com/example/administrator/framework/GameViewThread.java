package com.example.administrator.framework;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Administrator on 2017-11-27.
 */

public class GameViewThread extends Thread {
    //접근을 위한 멤버 변수
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean check = false;

    //스레드실행 상태
    private boolean run_ch = false;

    public GameViewThread(SurfaceHolder surfaceHolder, GameView gameView) {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunnig(boolean run) {
        run_ch = run;
    }


    @SuppressLint("WrongCall")
    @Override
    public void run() {
        Canvas canvas;
        while(run_ch) {
            canvas = null;
            try {
                gameView.Update();
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    gameView.onDraw(canvas);
                }
            } finally {
                if(canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
