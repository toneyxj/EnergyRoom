package com.moxi.energyroom.listener;

public interface OnDialogClickListener {
    /**
     * 弹出框点击项目，1为tre，0为false，其余按钮自己定义
     * @param button
     */
    void onClickButton(int button);
}
