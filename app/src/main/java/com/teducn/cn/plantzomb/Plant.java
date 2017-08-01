package com.teducn.cn.plantzomb;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Timer;

/**
 * Created by tarena on 2017/7/31.
 */

public class Plant extends ImageView {

    int costSunCount;
    Bitmap plantBitmap;
    Activity context;
    MyCallback mCallback;
    Timer timer;
    int HP = 30;

    public void deadAction() {
        mCallback.plantDead(this);
        if (timer != null) {
            timer.cancel();
        }
    }

    public void setmCallback(MyCallback mCallback) {
        this.mCallback = mCallback;
    }

    public interface MyCallback {
        public abstract void addSunCount(int count);

        public abstract void plantDead(Plant plant);
    }

    public Plant(Context context) {
        super(context);
        this.context = (Activity) context;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(120, 200);
        setLayoutParams(lp);
    }

    public void beginFire() {

    }

    public void beginAnimation() {
        AnimationDrawable ad = new AnimationDrawable();
        int w = plantBitmap.getWidth() / 8;
        for (int i = 0; i < 8; i++) {
            Bitmap subBitmap = Bitmap.createBitmap(plantBitmap, i * w, 0, w, plantBitmap.getHeight());
            BitmapDrawable bd = new BitmapDrawable(getResources(), subBitmap);
            ad.addFrame(bd, 100);
        }
        ad.setOneShot(false);
        setImageDrawable(ad);
        ad.start();
        plantBitmap.recycle();
    }
}
