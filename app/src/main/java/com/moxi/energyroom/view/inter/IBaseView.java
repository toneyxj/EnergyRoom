package com.moxi.energyroom.view.inter;

import android.content.Context;

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

}
