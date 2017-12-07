package com.example.administrator.framework;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.administrator.networks.ClientWork;
import com.example.administrator.networks.ServerWork;

/**
 * Created by Administrator on 2017-11-27.
 */

public class GameActivity extends Activity {
    GameView gameView;
    public static double size;

    // TODO 네트워크
    public static final int gamePort = 1451;

    private String myName;
    private String myIP;
    private String opponentName;
    private String opponentIP;
    private String isHost;

    private int myPlayerNum = -1;
    public int getMyPlayerNum() { return myPlayerNum; }

    private ServerWork serverWork;
    private ClientWork clientWork;
    public ClientWork getClient() { return clientWork; }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO 네트워크
        myName = getIntent().getStringExtra("myName");
        myIP = getIntent().getStringExtra("myIP");

        opponentName = getIntent().getStringExtra("opponentName");
        opponentIP = getIntent().getStringExtra("opponentIP");
        isHost = getIntent().getStringExtra("isHost");

        if (isHost.equals("yes")) {
            serverWork = new ServerWork(myName, myIP, opponentName, opponentIP, gamePort);
            clientWork = new ClientWork(myIP, gamePort);
            myPlayerNum = 0;
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clientWork = new ClientWork(opponentIP, gamePort);
            myPlayerNum = 1;
        }


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