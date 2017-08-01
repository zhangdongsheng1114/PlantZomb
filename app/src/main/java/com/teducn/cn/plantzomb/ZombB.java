package com.teducn.cn.plantzomb;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by tarena on 2017/7/31.
 */

public class ZombB extends Zomb{
    public ZombB(Context context) {
        super(context);
        HP = 8;
        zombBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zomb_1);
        beginAnimation();
        speed = 2;
    }
}
