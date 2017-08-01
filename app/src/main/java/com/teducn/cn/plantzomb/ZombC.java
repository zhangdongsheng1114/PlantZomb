package com.teducn.cn.plantzomb;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by tarena on 2017/7/31.
 */

public class ZombC extends Zomb{
    public ZombC(Context context) {
        super(context);
        HP = 8;
        zombBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zomb_2);
        beginAnimation();
        speed = 3;
    }
}
