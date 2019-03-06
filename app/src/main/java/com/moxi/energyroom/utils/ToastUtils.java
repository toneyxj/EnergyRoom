package com.moxi.energyroom.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.moxi.energyroom.R;


/**
 * Created by Administrator on 2016/10/19.
 */
public class ToastUtils {
    // 初始化类实列
    private static ToastUtils instatnce = null;
    private Toast toast;
    private int bottomHeight=108;
    private View view;

    public static ToastUtils getInstance() {
        if (instatnce == null) {
            synchronized (ToastUtils.class) {
                if (instatnce == null) {
                    instatnce = new ToastUtils();
                }
            }
        }
        return instatnce;
    }

    public void  initToast(Context context){
        context=context.getApplicationContext();
        toast=Toast.makeText(context,"",Toast.LENGTH_SHORT);
        bottomHeight= (int) (DensityUtil.getScreenH(context)*0.1);
        if (bottomHeight<=0)bottomHeight=108;
        initToastView(context);

    }

    /**
     * 短时间显示
     * @param value
     */
    public void showToastShort(Object value) {
        showToast(value, false, false);
    }

    /**
     * 长时间显示
     * @param value
     */
    public void showToastLong(Object value) {
      showToast(value,true,false);
    }
    /**
     * 长时间显示
     * @param value
     */
    public void showToast(Object value,boolean isLong,boolean iscenter) {
        if (toast==null)return;
        toast.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.setGravity(iscenter ? Gravity.CENTER : Gravity.BOTTOM, 0, iscenter ? 0 :bottomHeight);
//        toast.setText(value.toString());
        setToastValue(value);
        toast.setView(view);
        toast.show();
    }
    private void initToastView(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
         view = inflater.inflate(R.layout.toast_layout, null);
//        view.setAlpha(0.5f);
    }
    private void setToastValue(Object o){
        TextView toast_name = (TextView) view.findViewById(R.id.toast_name);
        toast_name.setText(o.toString());
    }

}
