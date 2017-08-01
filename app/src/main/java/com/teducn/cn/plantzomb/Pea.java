package com.teducn.cn.plantzomb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tarena on 2017/7/31.
 */

public class Pea extends Plant {

    ArrayList<ImageView> bullets = new ArrayList<>();
    public Bitmap bulletBitmap;

    public Pea(Context context) {
        super(context);
        costSunCount = 100;
        plantBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.plant_2);
        beginAnimation();
        bulletBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bullet_0);
    }

    @Override
    public void beginFire() {
        super.beginFire();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 回到主线程
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        ImageView bulletIV = new ImageView(context);
                        bulletIV.setImageBitmap(bulletBitmap);
                        AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(50, 50, (int) getX() + 100, (int) getY() + 25);
                        AbsoluteLayout layout = (AbsoluteLayout) getParent();
                        layout.addView(bulletIV, params);
                        bullets.add(bulletIV);
                    }
                });
            }
        }, 1000, 1000);
    }
}
