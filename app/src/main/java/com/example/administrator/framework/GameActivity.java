package com.example.administrator.framework;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-11-27.
 */

public class GameActivity extends Activity {
    GameView gameView;
    public static double size;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //디스플레이의 해상도를 구함
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        size = (double)width/1920.0;

        gameView = new GameView(this);
        setContentView(gameView);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.gameview_thread.setRunnig(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.gameview_thread.setRunnig(true);
    }
}
