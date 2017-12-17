package com.example.administrator.framework;

import android.graphics.Rect;

/**
 * Created by mac on 2017. 12. 13..
 */

public class CollisionManager {
    //TODO 충돌감지 메소드
    //rt = 각각 충돌검사를 할 객체들의 Rect
    public static boolean collision_Check(Rect rt1, Rect rt2) {
        if(rt1.right >= rt2.left && rt1.left <= rt2.right && rt1.top <= rt2.bottom && rt1.bottom >= rt2.top) {
            return true;
        }
        return false;
    }
}
