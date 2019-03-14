package com.moxi.energyroom.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.moxi.energyroom.R;
import com.moxi.energyroom.listener.OnDialogClickListener;
import com.moxi.energyroom.netty.NettyClient;

public class DialogWindowUtils {
    private static DialogWindowUtils instance = null;
    public static DialogWindowUtils getInstance() {
        if (instance == null) {
            synchronized (DialogWindowUtils.class) {
                if (instance == null) {
                    instance = new DialogWindowUtils();
                }
            }
        }
        return instance;
    }
    private AlertDialog dialogNormal=null;

    /**
     * 显示普通的提示框
     * @param context 上下文
     * @param content 提示内容
     * @param listener 点击按钮监听
     */
    public  void showNormalDialog(final Context context, String content, final OnDialogClickListener listener) {
        if (null!=dialogNormal&&dialogNormal.isShowing()){
            dialogNormal.cancel();
        }
        //创建dialog构造器
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
        //设置title
        normalDialog.setTitle("连接失败");
        //设置icon
        normalDialog.setIcon(R.mipmap.ic_launcher_round);
        //设置内容
        normalDialog.setMessage(content);
        //设置提示框不可取消
        normalDialog.setCancelable(false);
        //设置按钮
        normalDialog.setPositiveButton(context.getString(R.string.re_connect)
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null)
                            listener.onClickButton(1);
                        dialog.dismiss();
                    }
                });
        //创建并显示
        dialogNormal=normalDialog.create();
        dialogNormal.show();
    }
}
