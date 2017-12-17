package com.example.administrator.framework;

import com.example.administrator.state.GameState;
import com.example.administrator.networks.ClientWork;

/**
 * Created by Administrator on 2017-12-15.
 */

public class UpdateManager extends Thread {
    //업데이트할지 안할지를 체크하는 변수
    private boolean update_Check = true;

    @Override
    public void run() {
        int timeCheck_XY = 0;
        while (true) {
            if (update_Check) {
                GameView.istate.Update();
                if(ObjectManager.player_Vector.size() > 1) {
                    ClientWork.write("PlayerData " + ObjectManager.player_Vector.get(GameState.getPlayer_Num()).getMove_Check() + " " + ObjectManager.player_Vector.get(GameState.getPlayer_Num()).getAngle());
                    if (timeCheck_XY > 200) {
                        ClientWork.write("PlayerDataXY " + ObjectManager.player_Vector.get(GameState.getPlayer_Num()).getX() + " " + ObjectManager.player_Vector.get(GameState.getPlayer_Num()).getY());

                        timeCheck_XY = 0;
                    }
                    timeCheck_XY++;
                }
                update_Check = false;
            }
        }
    }

    public void setCheck(boolean update_Check) {
        this.update_Check = update_Check;
    }
}
