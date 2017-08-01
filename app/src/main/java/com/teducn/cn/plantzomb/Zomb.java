package com.teducn.cn.plantzomb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tarena on 2017/7/31.
 */

public class Zomb extends ImageView {

    Bitmap zombBitmap;
    float speed;
    float oldSpeed;
    int HP;

    public void eatAction(final Plant plant) {
        oldSpeed = speed;
        // 停止
        speed = 0;
        // 让植物掉血
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                plant.HP--;
                if (plant.HP <= 0) {
                    plant.deadAction();
                    speed = oldSpeed;
                    // 让timer停止
                    this.cancel();
                }
            }
        }, 0, 100);
    }

    public Zomb(Context context) {
        super(context);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(120, 180);
        setLayoutParams(lp);
    }

    public void beginAnimation() {
        AnimationDrawable ad = new AnimationDrawable();
        int w = zombBitmap.getWidth() / 8;
        for (int i = 0; i < 8; i++) {
            Bitmap subBitmap = Bitmap.createBitmap(zombBitmap, i * w, 0, w, zombBitmap.getHeight());
            // 创建做动画的对象
            BitmapDrawable bd = new BitmapDrawable(getResources(), subBitmap);
            ad.addFrame(bd, 100);
        }
        // 设置是否重复
        ad.setOneShot(false);
        // 把做动画的对象给到将是图片
        setImageDrawable(ad);
        ad.start();
        // 如果bitmap使用比较频繁，尽量在使用之后释放掉
        zombBitmap.recycle();
    }
}
