package com.example.administrator.game;

import com.example.administrator.framework.ObjectManager;
import com.example.administrator.state.GameState;
import com.example.administrator.state.LodingState;
import com.example.administrator.networks.ClientWork;

/**
 * Created by mac on 2017. 12. 16..
 */

public class NoteMakeThread extends Thread {
    int i = 0;

    public void run() {
        long now = 0, dt;
        long last = System.currentTimeMillis();
        while(true) {
            try {
                int delay = 0;
                int note_num = 0;

                if (LodingState.noteDataVector.size() > i) {
                    note_num = LodingState.noteDataVector.get(i).get("num");
                    delay = LodingState.noteDataVector.get(i).get("delay");
                    now = System.currentTimeMillis();
                    dt = (now - last);
                    while (dt < delay) {
                        sleep(1);
                        now = System.currentTimeMillis();
                        dt = (now - last);
                    }

                    ObjectManager.MakeNote(note_num);
                    i++;
                } else {
                    sleep(5000);
                    if(ObjectManager.player_Vector.get(GameState.getPlayer_Num()).getHP_Pre() < ObjectManager.player_Vector.get(GameState.getEnemy_Num()).getHP_Pre()) {
                        ClientWork.write("Die");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            last = now;
        }
    }
}
