package com.teducn.cn.plantzomb;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by tarena on 2017/7/31.
 */

public class ZombA extends Zomb {
    public ZombA(Context context) {
        super(context);
        HP = 4;
        zombBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zomb_0);
        beginAnimation();
        speed = 1;
    }
}
