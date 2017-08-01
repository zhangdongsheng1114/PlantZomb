package com.teducn.cn.plantzomb;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by tarena on 2017/7/31.
 */

public class Nut extends Plant {
    public Nut(Context context) {
        super(context);
        HP = 60 * 10;
        costSunCount = 125;
        plantBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.plant_5);
        beginAnimation();
    }
}
