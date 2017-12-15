package com.example.administrator.game;

import com.example.administrator.framework.GameView;
import com.example.administrator.framework.IState;
import com.example.administrator.networks.ClientWork;

/**
 * Created by Administrator on 2017-12-15.
 */

public class UpdateManager extends Thread {
    //업데이트할지 안할지를 체크하는 변수
    private boolean update_Check = true;

    private IState iState;

    @Override
    public void run() {
        int timeCheck_XY = 0;
        while (true) {
            if (update_Check) {
                iState.Update();
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

    //게임뷰의 상태를 바꿔줌
    public void changeState(IState istate) {
        //새 istate를 적용시킴
        this.iState = istate;
    }
}
