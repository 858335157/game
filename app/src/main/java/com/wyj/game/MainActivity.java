package com.wyj.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    private final int dotNum = 35;
    private final int dimension = 3;
    private double width = 500;
    private double height = 330;
    private int[][] dot = new int[dotNum+1][dimension];//创建二维点集

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化点集
        for (int i = 1; i <= dotNum; i++) {
            dot[i][0] = (i - 1) % 7 + 1;
            dot[i][1] = (i - 1) / 7 + 1;

            //离中心越远权值越小
            if(i==1||i==7||i==29||i==35){
                dot[i][2]=1;
            }else if(i==2||i==6||i==8||i==14||i==22||i==28||i==30||i==34){
                dot[i][2]=2;
            }else if(i==3||i==5||i==9||i==13||i==15||i==21||i==23||i==27||i==31||i==33){
                dot[i][2]=3;
            }else if(i==4||i==10||i==12||i==16||i==20||i==24||i==26||i==32){
                dot[i][2]=4;
            }else if(i==11||i==17||i==19||i==25){
                dot[i][2]=5;
            }else{
                dot[i][2]=6;
            }
        }


        setContentView(R.layout.activity_main);

        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //强制横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        //获得屏幕宽高
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        int w = wm.getDefaultDisplay().getWidth();  //屏幕宽度
        width = w/7;                         //单位宽度
        int h = wm.getDefaultDisplay().getHeight(); //屏幕高度
        height = h/5;                        //单位高度


        /*//3、获取屏幕的默认分辨率
        Display display = getWindowManager().getDefaultDisplay();
        int w = display.getWidth();//宽度
        int h = display.getHeight();//高度*/

        width = w/7.0;                         //单位宽度

        height = h/5.0;                        //单位高度*/

        /*private Handler mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 0x111://游戏继续
                        break;
                    case 0x110://游戏结束
                        finish();
                        break;
                }
                return false;
            }
        });*/

        //游戏开始
        if(width!=0){
            firstBuild();//第一次绘制，特别卡！！！！
        }


        while(true){
            init();
            build();
            doit();
            if(!judge()){
                break;
            }
        }

    }

    //绘制一个跳板
    private void buildone(int flag, int dimension, int localx, int localy){

        int wid,hei;
        double ral;
        switch (dimension){
            case 1:ral = 3/6.0;break;
            case 2:ral = 3.3/6;break;
            case 3:ral = 3.7/6;break;
            case 4:ral = 4.3/6;break;
            case 5:ral = 5.1/6.0;break;
            case 6:ral = 1;break;
            default:ral = 1;
        }
        wid = (int)( width*ral);
        hei = (int)( height*ral);
        ImageView image1 = new ImageView(this);
        if(flag == 1){
            image1.setImageResource(R.drawable.round);
        }
        LinearLayout.LayoutParams parmas = new LinearLayout.LayoutParams((int)width,(int)height);
        image1.setLayoutParams(parmas);
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(image1.getLayoutParams());
        margin.leftMargin=(int)(width*(localx-1)+width/2.0-wid/2.0);
        margin.topMargin=(int)(height*(localy-1)+height/2.0-hei/2.0);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(margin);
        layoutParams.height = wid;//设置图片的高度
        layoutParams.width = hei; //设置图片的宽度
        image1.setLayoutParams(layoutParams);
        FrameLayout table = findViewById(R.id.table);
        table.addView(image1);
    }

    //第一次加载
    private void firstBuild(){
        buildone(1,6,4,3);//绘制最中心的踏板
        int[] judge = new int[9];

        //循环绘制其它踏板，加上中心的一共10个
round1: for(int i = 1; i < 10; i++ ){
            int ran =(int)(Math.random()*35)+1;
            if( ran == 18 ){
                i--;
                continue;
            }
            judge[i-1]=ran;
            for(int j = 0 ; j < i-1; j++ ){
                if(ran == judge[j]){
                    i--;
                    continue round1;
                }
            }
            int flag = 1;
            buildone(flag,dot[ran][2],dot[ran][0],dot[ran][1]);
        }
    }

    //初始化数据
    private void init(){

    }

    //踏板生成工厂
    private void build(){

    }

    //玩家控制
    private void doit(){

    }

    //判断能否继续
    private boolean judge(){
        return true;
    }


    public void doing(View view) {//方向键控制

    }
}
