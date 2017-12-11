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

    public static  final  long MILLIS_PER_FRAME = 25;

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

        long now = 0, dt;
        long last = System.currentTimeMillis();
        while(run_ch) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    now = System.currentTimeMillis();
                    dt = (now - last);
                    while(dt < MILLIS_PER_FRAME) {
                        sleep(1);
                        now = System.currentTimeMillis();
                        dt = (now - last);
                    }
                    //gameView.Update();
                    gameView.onDraw(canvas);
                }
            } catch (Exception e) {

            } finally {
                if(canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            last = now;
        }
    }
}
