package com.example.administrator.game;

import com.example.administrator.networks.ServerWork;

import java.util.Random;

/**
 * Created by Administrator on 2017-12-15.
 */

public class BulletPatternManager {
    //TODO 랜덤함수를 돌려 그에맞는 탄막을 생성
    public static void makeBarrage() {
        Random random = new Random();
        int pattern_Num = random.nextInt(9);
        switch (pattern_Num) {
            case 0 : {
                for(int i = 0; i < 4; i++) {
                    String bulletData = "BulletMake " + ((90 + 30*i)%360) + " " + 510 + " " + 10;
                    ServerWork.all_Write(bulletData);
                }
                break;
            }

            case 1 : {
                for(int i = 0; i < 7; i++) {
                    String bulletData = "BulletMake " + ((90 + 30*i)%360) + " " + 1185 + " " + 10;
                    ServerWork.all_Write(bulletData);
                }
                break;
            }

            case 2 : {
                for(int i = 0; i < 4; i++) {
                    String bulletData = "BulletMake " + ((180 + 30*i)%360) + " " + 1860 + " " + 10;
                    ServerWork.all_Write(bulletData);
                }
                break;
            }

            case 3 : {
                for(int i = 0; i < 7; i++) {
                    String bulletData = "BulletMake " + ((180 + 30*i)%360) + " " + 1860 + " " + 525;
                    ServerWork.all_Write(bulletData);
                }
                break;
            }

            case 4 : {
                for(int i = 0; i < 4; i++) {
                    String bulletData = "BulletMake " + ((270 + 30*i)%360) + " " + 1860 + " " + 1020;
                    ServerWork.all_Write(bulletData);
                }
                break;
            }

            case 5 : {
                for(int i = 0; i < 7; i++) {
                    String bulletData = "BulletMake " + ((270 + 30*i)%360) + " " + 1185 + " " + 1020;
                    ServerWork.all_Write(bulletData);
                }
                break;
            }

            case 6 : {
                for(int i = 0; i < 4; i++) {
                    String bulletData = "BulletMake " + ((0 + 30*i)%360) + " " + 10 + " " + 1020;
                    ServerWork.all_Write(bulletData);
                }
                break;
            }

            case 7 : {
                for(int i = 0; i < 7; i++) {
                    String bulletData = "BulletMake " + ((0 + 30*i)%360) + " " + 10 + " " + 525;
                    ServerWork.all_Write(bulletData);
                }
                break;
            }

            case 8 : {
                for(int i = 0; i < 12; i++) {
                    String bulletData = "BulletMake " + ((0 + 30*i)%360) + " " + 1185 + " " + 525;
                    ServerWork.all_Write(bulletData);
                }
                break;
            }
        }
    }
}
