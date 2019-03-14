package com.moxi.energyroom.netty;

import android.os.Message;

import com.moxi.energyroom.listener.HandlerMessageInterface;
import com.moxi.energyroom.otherPresenter.BaseUtils;
import com.moxi.energyroom.utils.TimerUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 时间控制器，控制连接
 */
public class TimeCounter implements HandlerMessageInterface {
    /**
     * 计时器
     */
    private Timer timer;

    /**
     * 记时回调接口
     */
    private TimeCallback listener;
    private int timeInterval =5000;

    @Override
    public void handleMessage(Message msg) {
        if (msg.what==10){
            if (listener!=null)listener.onTimerCallBack();
        }
    }

    private BaseUtils.XJHander handler = new BaseUtils.XJHander(this);

    public TimeCounter(TimeCallback listener) {
        this.listener = listener;
    }
    public TimeCounter(int timeInterval, TimeCallback listener) {
        this.timeInterval=timeInterval;
        this.listener = listener;
    }

    /**

     * 开始计时
     */
    public void startTimer() {
        if (timer == null) {
            timer = new Timer();
        }else {
            return;
        }
        TimerTask timerTask = new TimerTask() {
            // 倒数10秒
            @Override
            public void run() {
                // 定义一个消息传过去
                handler.sendEmptyMessage(10);
            }
        };
        handler.sendEmptyMessage(10);
        timer.schedule(timerTask, timeInterval, timeInterval);
    }

    /**
     * 停止计时
     */
    public void stopTimer() {
        if (timer!=null) {
            timer.cancel();
            timer = null;
            handler.removeCallbacksAndMessages(null);
        }
    }


    public interface TimeCallback {
        /**
         *记时时钟器
         */
        public void onTimerCallBack();

    }

}
