package com.example.administrator.game;

import com.example.administrator.framework.GameView;
import com.example.administrator.framework.SoundManager;

import java.util.StringTokenizer;

/**
 * Created by Administrator on 2017-12-13.
 */

public class PacketManager {
    static boolean gamePlay = true;
    // TODO 클라이언트에서 수신한 메세지를 분석하고 그에맞는 일을 처리함
    public static void check_Message(String string) {
        //데이터를 분석하기 위해 스트링 토크나이저를 생성
        StringTokenizer stringTokenizer = new StringTokenizer(string, " ");

        //패킷데이터가 없을때 까지 와일문으로 분석
        while (stringTokenizer.hasMoreTokens()) {
            //이 패킷이 어떤 패킷인지 알아내기 위해 태그를 때어냄
            String tag = stringTokenizer.nextToken();

            //태그에 따라 다른 작동을 함
            switch (tag) {
                case "AllReady": {
                    GameView.changeGameState();
                    break;
                }
                //플레이어의 위치정보를 받아와 그에 맞게 위치를 조정
                case "PlayerData": {
                    int player_Num = Integer.parseInt(stringTokenizer.nextToken());

                    //자신의 캐릭터의 데이터가 아닐경우에만 해당 번호의 캐릭터를 움직임
                    if (GameState.getPlayer_Num() != player_Num) {
                        ObjectManager.player_Vector.get(player_Num).setMove_Check(Boolean.parseBoolean(stringTokenizer.nextToken()));
                        ObjectManager.player_Vector.get(player_Num).setAngle(Double.parseDouble(stringTokenizer.nextToken()));
                    } else {
                        stringTokenizer.nextToken();
                        stringTokenizer.nextToken();
                    }

                    break;
                }

                //서버에서 받아온 값으로 캐릭터의 좌표를 동기화 시킴
                case "PlayerDataXY": {
                    //플레이어들을 받아온 좌표값으로 이동시킴
                    int player_Num = Integer.parseInt(stringTokenizer.nextToken());
                    ObjectManager.player_Vector.get(player_Num).shooting_X = Integer.parseInt(stringTokenizer.nextToken());
                    ObjectManager.player_Vector.get(player_Num).shooting_Y = Integer.parseInt(stringTokenizer.nextToken());
                    ObjectManager.player_Vector.get(player_Num).setPosition((int) (ObjectManager.player_Vector.get(player_Num).shooting_X), (int) (ObjectManager.player_Vector.get(player_Num).shooting_Y));

                    break;
                }

                //서버에서 주기적으로 보내는 공공의적(총알)생성메세지
                case "BulletMake": {
                    double angle = Double.parseDouble(stringTokenizer.nextToken());
                    int x = Integer.parseInt(stringTokenizer.nextToken());
                    int y = Integer.parseInt(stringTokenizer.nextToken());

                    ObjectManager.makeBullet(angle, x, y);
                    break;
                }

                //공격정보를 수신하여 그에 맞게 공격명령
                case "Attack": {
                    int player_Num = Integer.parseInt(stringTokenizer.nextToken());
                    Double damage = Double.parseDouble(stringTokenizer.nextToken());
                    ObjectManager.player_Vector.get(player_Num).make_Laser(System.currentTimeMillis(), damage);

                    break;
                }

                //충돌 판정
                case "Collsion": {
                    int collider_Player = Integer.parseInt(stringTokenizer.nextToken());
                    String collider_Object = stringTokenizer.nextToken();

                    switch (collider_Object) {
                        //레이저와 충돌했을때
                        case "Laser": {
                            Double damage = Double.parseDouble(stringTokenizer.nextToken());
                            //플레이어에게 콤보에 따른 데미지를 줌
                            ObjectManager.player_Vector.get(collider_Player).hit(damage);

                            break;
                        }
                        case "Bullet": {
                            int collider_Index = Integer.parseInt(stringTokenizer.nextToken());
                            //플레이어에게 5의 데미지를 주고
                            ObjectManager.player_Vector.get(collider_Player).hit(5);

                            //50%의 이동속도 감소효과 디버프를 검
                            ObjectManager.player_Vector.get(collider_Player).setSlow(0.5f, System.currentTimeMillis());
                            //만약 불릿벡터 사이즈가 콜리더인덱스보다 크면 콜리더 인덱스의 객체를 파괴함
                            if (ObjectManager.bullet_Vector.size() > collider_Index)
                                ObjectManager.bullet_Vector.remove(collider_Index);

                            break;
                        }
                    }
                    break;
                }

                case "Result" : {
                    SoundManager.getInstance().MediaStop();
                    for(int i = 0; i < 2; i++) {
                        int player_Num = Integer.parseInt(stringTokenizer.nextToken());
                        String result = stringTokenizer.nextToken();
                        if(player_Num == GameState.getPlayer_Num()) {
                            switch (result) {
                                case "WIN": {
                                    WinState winState = new WinState();
                                    GameView.changeState(winState);
                                    break;
                                }

                                case "DEFEAT": {
                                    DefeatState defeatState = new DefeatState();
                                    GameView.changeState(defeatState);
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
    }
}
