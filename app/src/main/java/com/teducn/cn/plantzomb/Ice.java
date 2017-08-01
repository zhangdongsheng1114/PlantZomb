package com.teducn.cn.plantzomb;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by tarena on 2017/7/31.
 */

public class Ice extends Pea {
    public Ice(Context context) {
        super(context);
        costSunCount = 175;
        plantBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.plant_3);
        beginAnimation();
        bulletBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bullet_1);
    }
}
