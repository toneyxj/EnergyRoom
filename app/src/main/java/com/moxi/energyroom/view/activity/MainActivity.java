package com.moxi.energyroom.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.moxi.energyroom.R;
import com.moxi.energyroom.listener.HeatCallback;
import com.moxi.energyroom.presenter.impl.MainAPresenterImpl;
import com.moxi.energyroom.presenter.inter.IMainAPresenter;
import com.moxi.energyroom.utils.ToastUtils;
import com.moxi.energyroom.view.inter.IMainAView;
import com.moxi.energyroom.view.widget.BaseView.XJTextView;
import com.moxi.energyroom.view.widget.GradeSettingView;
import com.moxi.energyroom.view.widget.HotAndHumidityView;
import com.moxi.energyroom.view.widget.otherView.ArcAlphaButton;
import com.moxi.energyroom.view.widget.otherView.HotSettingView;
import com.moxi.energyroom.view.widget.otherView.LodingLayout;


import butterknife.BindView;

public class MainActivity extends BaseActivity implements IMainAView, HeatCallback {
    private IMainAPresenter mIMainAPresenter;

    //时间模块
    @BindView(R.id.hours)
    TextView hours;//时间
    @BindView(R.id.pm_am)
    TextView pm_am;//pm与am
    @BindView(R.id.date)
    TextView date;//日期
    @BindView(R.id.weeks)
    TextView weeks;//周几

    //温度与湿度模块
    @BindView(R.id.cur_temperature)
    HotAndHumidityView cur_temperature;//当前温度
    @BindView(R.id.cur_humidity)
    HotAndHumidityView cur_humidity;//当前湿度
    //热量设定
    @BindView(R.id.heat_liang_ce)
    HotSettingView heat_liang_ce;//两侧
    @BindView(R.id.heat_beihou)
    HotSettingView heat_beihou;// 背后
    //时间设定
    @BindView(R.id.residue_time)
    XJTextView residue_time;//剩余时间
    @BindView(R.id.setting_time_one)
    ArcAlphaButton setting_time_one;//设置时间30
    @BindView(R.id.setting_time_two)
    ArcAlphaButton setting_time_two;//设置时间60
    @BindView(R.id.setting_time_three)
    ArcAlphaButton setting_time_three;//设置时间90
    //主界面其余模块
    @BindView(R.id.zmd)
    GradeSettingView zmd;//照明灯
    @BindView(R.id.ydd)
    GradeSettingView ydd;//阅读灯
    @BindView(R.id.jsd)
    GradeSettingView jsd;//加湿灯
    @BindView(R.id.hqs)
    GradeSettingView hqs;//换气扇
    @BindView(R.id.yb)
    GradeSettingView yb;//氧吧
    @BindView(R.id.lyyx)
    GradeSettingView lyyx;//蓝牙音箱
    private LodingLayout lodingLayout=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainAPresenter = new MainAPresenterImpl(this);

        heat_liang_ce.setTag("两侧");
        heat_beihou.setTag("背后");

        heat_liang_ce.setHeatCallback(this);
        heat_beihou.setHeatCallback(this);

        setting_time_one.setTag(0);
        setting_time_two.setTag(1);
        setting_time_three.setTag(2);
        setting_time_one.setOnClickListener(clickListener);
        setting_time_two.setOnClickListener(clickListener);
        setting_time_three.setOnClickListener(clickListener);

        zmd.setOnClickListener(clickListener);
        ydd.setOnClickListener(clickListener);
        jsd.setOnClickListener(clickListener);
        hqs.setOnClickListener(clickListener);
        yb.setOnClickListener(clickListener);
        lyyx.setOnClickListener(clickListener);

    }

    @Override
    int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    void viewAbout() {
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    /**
     * 隐藏底部虚拟按键
     */
    private void hideBottomButton() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    void clickView(View view) {
        if (view instanceof ArcAlphaButton) {
            try {
                if (heat_liang_ce.isSwitch()||heat_beihou.isSwitch()) {
                    Object tag = view.getTag();
                    if (null != tag) {
                        int index = (int) tag;
                        mIMainAPresenter.settingTimeGrade(index);
                        return;
                    }
                }else {
                    ToastUtils.getInstance().showToastShort("请开启热量设定！！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        switch (view.getId()) {
            case R.id.zmd:
                mIMainAPresenter.bottomZMD(!zmd.isSelect());
                break;
            case R.id.ydd:
                mIMainAPresenter.bottomYDD(!ydd.isSelect());
                break;
            case R.id.jsd:
                mIMainAPresenter.bottomJSD(!jsd.isSelect());
                break;
            case R.id.hqs:
                mIMainAPresenter.bottomHQS(!hqs.isSelect());
                break;
            case R.id.yb:
                mIMainAPresenter.bottomYB(!yb.isSelect());
                break;
            case R.id.lyyx:
                mIMainAPresenter.bottomLYYX(!lyyx.isSelect());
                break;
            default:
                break;
        }
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public boolean isFinish() {
        return this.isFinish();
    }

    @Override
    public Handler getThisHandler() {
        return getHander();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIMainAPresenter.viewIsFinish();
    }

    @Override
    public void curSystemTime(String hours, String amOrPm, String data, String sw) {
        this.hours.setText(hours);
        this.pm_am.setText(amOrPm);
        this.date.setText(data);
        this.weeks.setText(sw);
    }

    @Override
    public void curTemperature(String value) {
        cur_temperature.setValue(value);
    }

    @Override
    public void curHumidity(String value) {
        cur_humidity.setValue(value);
    }

    @Override
    public void heatSeting(int orientation, boolean isOpen, int grade) {
        if (orientation==0){
            heat_liang_ce.setSwitch(isOpen);
            heat_liang_ce.setGrade(grade);
        }else {
            heat_beihou.setSwitch(isOpen);
            heat_beihou.setGrade(grade);
        }
        /**
         * 都是关闭状态
         */
        if (!heat_beihou.isSwitch()&&!heat_liang_ce.isSwitch()){
            settingTimeIndex(-1);
            mIMainAPresenter.settingTimeGrade(-1);
        }
    }

    @Override
    public void residueTime(Spannable time) {
        residue_time.setText(time);
    }

    /**
     * 加热时间控制
     * @param index -1等于时间到
     */
    @Override
    public void settingTimeIndex(int index) {
        setting_time_one.setSelect(false);
        setting_time_two.setSelect(false);
        setting_time_three.setSelect(false);
        if (index < 0) return;
        switch (index) {
            case 1:
                setting_time_two.setSelect(true);
                break;
            case 2:
                setting_time_three.setSelect(true);
                break;
            default:
                setting_time_one.setSelect(true);
                break;
        }
    }

    @Override
    public void closeHeat() {
        settingTimeIndex(-1);

        heat_liang_ce.setSwitch(false);
        heat_beihou.setSwitch(false);
    }

    @Override
    public void bottomZMD(boolean is) {
        zmd.setSelect(is);
    }

    @Override
    public void bottomYDD(boolean is) {
        ydd.setSelect(is);
    }

    @Override
    public void bottomJSD(boolean is) {
        jsd.setSelect(is);
    }

    @Override
    public void bottomHQS(boolean is) {
        hqs.setSelect(is);
    }

    @Override
    public void bottomYB(boolean is) {
        yb.setSelect(is);
    }

    @Override
    public void bottomLYYX(boolean is) {
        lyyx.setSelect(is);
    }

    @Override
    public void onConnectSucess() {

    }

    @Override
    public void onConnectFail() {

    }


    @Override
    public void onLodingView(String msg) {
        if (lodingLayout==null) {
            ViewGroup viewL = (ViewGroup) getWindow().getDecorView();
            lodingLayout = new LodingLayout(getcontext());
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            lodingLayout.setLayoutParams(params);
            lodingLayout.setBackgroundResource(R.color.tany_black);
            viewL.addView(lodingLayout);
        }
        lodingLayout.setVisibility(View.VISIBLE);
        lodingLayout.setHitnMsg(msg);
    }

    @Override
    public void removeLodingView() {
        lodingLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    //热量调节监听开始
    @Override
    public void onClickObj(Object tag, int grade) {
        String t = tag.toString();
        if (t.equals("两侧")) {
            mIMainAPresenter.heatSetingGradeLiangce(grade);
        } else {
            mIMainAPresenter.heatSetingGradeBeihou(grade);
        }
    }

    @Override
    public void openOrClose(Object tag, boolean open) {
        String t = tag.toString();
        if (t.equals("两侧")) {
            mIMainAPresenter.heatSetingLiangce(open, heat_liang_ce.getGrade());
        } else {
            mIMainAPresenter.heatSetingBeihou(open, heat_beihou.getGrade());
        }
    }


}
