package com.moxi.energyroom.otherPresenter;

import android.os.Handler;
import android.os.Message;


import com.moxi.energyroom.listener.HandlerMessageInterface;
import com.moxi.energyroom.view.widget.utils.ViewAlphaInterface;

import java.lang.ref.WeakReference;

/**
 * base操作工具类
 */

public class BaseUtils {

    /**
     * 全部类使用handler
     */
    public static class XJHander extends Handler {
        private WeakReference<HandlerMessageInterface> reference;

        public XJHander(HandlerMessageInterface context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            HandlerMessageInterface activity = (HandlerMessageInterface) reference.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    public static class ViewHandler extends Handler {
        private WeakReference<ViewAlphaInterface> reference;

        public ViewHandler(ViewAlphaInterface context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            ViewAlphaInterface activity = (ViewAlphaInterface) reference.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }

    }
}
