package com.teducn.cn.plantzomb;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by tarena on 2017/7/31.
 */

public class ZombD extends Zomb {
    public ZombD(Context context) {
        super(context);
        HP = 16;
        zombBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zomb_3);
        beginAnimation();
        speed = 4;
    }
}
