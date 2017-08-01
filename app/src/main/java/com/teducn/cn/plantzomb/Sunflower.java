package com.teducn.cn.plantzomb;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tarena on 2017/7/31.
 */

public class Sunflower extends Plant {
    public Sunflower(Context context) {
        super(context);
        plantBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.plant_0);
        beginAnimation();
        costSunCount = 50;
    }

    @Override
    public void beginFire() {
        super.beginFire();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final ImageView sunIV = new ImageView(context);
                        sunIV.setScaleType(ScaleType.FIT_XY);
                        // 获取向日葵的位置
                        int[] location = new int[2];
                        getLocationOnScreen(location);
                        AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(80, 80, (int) getX(), (int) getY() + 60);
                        sunIV.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.sun));
//                        sunIV.setBackgroundColor(Color.TRANSPARENT);  // 设置图片背景透明
//                        sunIV.setX(location[0]);
//                        sunIV.setY(location[1]);
                        final AbsoluteLayout mainLayout = (AbsoluteLayout) getParent();
                        mainLayout.addView(sunIV, params);
                        sunIV.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mainLayout.removeView(sunIV);
                                // 让界面中的textView发生变化
                                mCallback.addSunCount(25);
                            }
                        });
                        // 添加3秒倒计时删除
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainLayout.removeView(sunIV);
                                    }
                                });
                            }
                        }, 3000);
                    }
                });
            }
        }, 3000, 5000);  // 第一个参数代表多少毫秒后执行，第二个参数表示重复多少秒后执行，如果只有一个参数表示多少秒后执行后不再重复
    }
}
