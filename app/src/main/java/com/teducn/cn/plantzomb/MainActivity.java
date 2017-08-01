package com.teducn.cn.plantzomb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Plant.MyCallback {

    int zombCount;
    ArrayList<Plant> plants = new ArrayList<>();
    private ArrayList<Zomb> zombs = new ArrayList<>();
    @BindViews({R.id.zomb1, R.id.zomb2, R.id.zomb3, R.id.zomb4})
    List<ImageView> plantIVs;

    @BindView(R.id.sunCountTV)
    TextView sunCountTV;
    @BindView(R.id.activity_main)
    AbsoluteLayout activityMain;

    Plant dragPlant;
    @BindView(R.id.boxesLayout)
    GridLayout boxesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // 隐藏标题栏
        getSupportActionBar().hide();
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initUI();
        moveAction();
        addZomb();
    }

    private void addZomb() {
        // 创建添加僵尸的Timer
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int type = zombCount++ / 30;
                        Zomb z = null;
                        switch (type) {
                            case 0:
                                z = new ZombA(MainActivity.this);
                                break;
                            case 1:
                                z = new ZombB(MainActivity.this);
                                break;
                            case 2:
                                z = new ZombC(MainActivity.this);
                                break;
                            default:
                                z = new ZombD(MainActivity.this);
                                break;
                        }
                        z.setX(activityMain.getWidth());
                        int line = (int) (Math.random() * 5) + 1;

                        z.setY(line * 170);
                        activityMain.addView(z);
                        zombs.add(z);
                    }
                });
            }
        }, 1500, 3000);
    }

    private void moveAction() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 遍历所有的子弹
                        for (Plant plant : plants) {
                            // 判断是否是射手类型
                            if (plant instanceof Pea) {
                                for (ImageView bulletIV : ((Pea) plant).bullets) {
                                    bulletIV.setX(bulletIV.getX() + 6);
                                    if (bulletIV.getX() > activityMain.getWidth()) {
                                        activityMain.removeView(bulletIV);
                                        ((Pea) plant).bullets.remove(bulletIV);
                                        break;
                                    }
                                    // 遍历每一个僵尸让子弹和僵尸进行碰撞检测
                                    for (Zomb zomb : zombs) {
                                        Rect zombRect = new Rect();
                                        Rect bulletRect = new Rect();
                                        zomb.getGlobalVisibleRect(zombRect);
                                        bulletIV.getGlobalVisibleRect(bulletRect);
                                        // 判断子弹和僵尸的显示范围是否交叉
                                        if (zombRect.intersect(bulletRect)) {
                                            // 判断当前射手类型是否为寒冰射手
                                            if (plant instanceof Ice && zomb.getAlpha() == 1) {
                                                zomb.speed *= .5;
                                                zomb.setAlpha(0.5f);
                                            }
                                            zomb.HP--;
                                            activityMain.removeView(bulletIV);
                                            ((Pea) plant).bullets.remove(bulletIV);
                                            if (zomb.HP <= 0) {
                                                activityMain.removeView(zomb);
                                                zombs.remove(zomb);
                                            }
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                        //移动僵尸
                        for (Zomb zomb : zombs) {
                            zomb.setX(zomb.getX() - zomb.speed);
                            if (zomb.getX() <= -zomb.getWidth()) {
                                activityMain.removeView(zomb);
                                zombs.remove(zomb);
                                //在增强for循环中如果修改了集合（增加 或 删除） 必须跳出循环 不然就 crash
                                break;
                            }
                            // 僵尸和植物的碰撞
                            for (Plant plant : plants) {
                                Rect zombRect = new Rect();
                                Rect plantRect = new Rect();
                                plant.getGlobalVisibleRect(plantRect);
                                zomb.getGlobalVisibleRect(zombRect);

                                if (plantRect.contains(zombRect.centerX(), zombRect.centerY()) && zomb.speed != 0) {
                                    zomb.eatAction(plant);
                                }
                            }
                        }
                    }
                });
            }
        }, 0, 1000 / 60);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int currentSunCount = Integer.parseInt(sunCountTV.getText().toString());
                for (int i = 0; i < plantIVs.size(); i++) {
                    ImageView plantIV = plantIVs.get(i);
                    Rect rect = new Rect();
                    plantIV.getGlobalVisibleRect(rect);
                    // 判断用户触摸屏幕的点是否点击到了某个植物
                    if (rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                        switch (i) {
                            case 0:
                                if (currentSunCount < 50) {
                                    return super.onTouchEvent(event);
                                }
                                dragPlant = new Sunflower(MainActivity.this);
                                break;
                            case 1:
                                if (currentSunCount < 100) {
                                    return super.onTouchEvent(event);
                                }
                                dragPlant = new Pea(MainActivity.this);
                                break;
                            case 2:
                                if (currentSunCount < 175) {
                                    return super.onTouchEvent(event);
                                }
                                dragPlant = new Ice(MainActivity.this);
                                break;
                            case 3:
                                if (currentSunCount < 125) {
                                    return super.onTouchEvent(event);
                                }
                                dragPlant = new Nut(MainActivity.this);
                                break;
                        }
                        // 设置显示范围
                        dragPlant.setLayoutParams(plantIV.getLayoutParams());
                        activityMain.addView(dragPlant);
                        dragPlant.setmCallback(this);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (dragPlant != null) {
                    dragPlant.setX(event.getRawX() - dragPlant.getWidth() / 2);
                    dragPlant.setY(event.getRawY() - dragPlant.getHeight() / 2);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (dragPlant != null) {
                    for (int i = 0; i < boxesLayout.getChildCount(); i++) {
                        View box = boxesLayout.getChildAt(i);
                        Rect r = new Rect();
                        box.getGlobalVisibleRect(r);
                        if (r.contains((int) event.getRawX(), (int) event.getRawY()) && box.getTag() == null) {
                            // 设置Tag为1标记已经扔到坑里了
                            dragPlant.setTag(1);
                            // 让坑自己记住自己内部已经有植物了
                            box.setTag(dragPlant);

                            int[] location = new int[2];
                            // 获得控件居屏幕的坐标
                            box.getLocationOnScreen(location);
                            dragPlant.setX(location[0]);
                            dragPlant.setY(location[1]);

                            // 让植物开火
                            dragPlant.beginFire();
                            // 记录种下的所有植物，为了实现子弹的移动和僵尸的碰撞
                            plants.add(dragPlant);
                            // 花钱逻辑
                            addSunCount(-dragPlant.costSunCount);
                        }
                    }
                    if (dragPlant.getTag() == null) {
                        activityMain.removeView(dragPlant);
                    }
                }
                dragPlant = null;
                break;
        }
        return super.onTouchEvent(event);
    }

    private void initUI() {
        // 得到购买植物的完整图片
        Bitmap plantBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.seedpackets);
        int w = plantBitmap.getWidth() / 18;
        for (int i = 0; i < plantIVs.size(); i++) {
            ImageView plantIV = plantIVs.get(i);
            int x = 0;
            switch (i) {
                case 0:
                    x = 0;
                    break;
                case 1:
                    x = 2 * w;
                    break;
                case 2:
                    x = 3 * w;
                    break;
                case 3:
                    x = 5 * w;
                    break;
            }
            // 截取图片
            Bitmap subPlantBitmap = Bitmap.createBitmap(plantBitmap, x, 0, w, plantBitmap.getHeight());
            // 把截取出来的图片是显示imageView上
            plantIV.setImageBitmap(subPlantBitmap);
        }
    }

    @Override
    public void plantDead(final Plant plant) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityMain.removeView(plant);
                plants.remove(plant);
            }
        });
    }

    @Override
    public void addSunCount(int count) {
        int currentCount = Integer.parseInt(sunCountTV.getText().toString());
        sunCountTV.setText("" + (count + currentCount));
    }
}
