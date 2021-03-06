package com.moxi.energyroom.view.inter;

import android.content.Context;
import android.os.Handler;


public interface IBaseView {
    /**
     * 获得上下文
     * @return
     */
    Context getcontext();

    /**
     * 界面是否已经关闭
     * @return
     */
    boolean isFinish();

    /**
     *
     * @return
     */
    Handler getThisHandler();
    /**
     * 加载控制view显示
     * @param msg
     */
    void onLodingView(String msg);

    /**
     * 清除加载view
     */
    void removeLodingView();

}
