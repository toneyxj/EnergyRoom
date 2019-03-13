package com.moxi.energyroom.model.impl.mian;

import android.content.Context;

import com.moxi.energyroom.Been.EV_Type;
import com.moxi.energyroom.Been.transmitData.BaseData;
import com.moxi.energyroom.Been.transmitData.HeatFilm;
import com.moxi.energyroom.model.inter.main.IHotSettingModel;
import com.moxi.energyroom.netty.NettyClient;
import com.moxi.energyroom.presenter.inter.IHeatSettingPresenter;
import com.moxi.energyroom.presenter.inter.IMainAPresenter;
import com.moxi.energyroom.utils.SharePreferceUtil;
import com.moxi.energyroom.utils.ToastUtils;

/**
 * 加热膜设置
 */
public class HotSettingModelImp implements IHotSettingModel {
    private boolean doubleSideIsOpen = true;
    private boolean backIsOpen = true;
    private int doubleSideHotGrade = -1;
    private int backHotGrade = -1;
    private IHeatSettingPresenter presenter;
    private Context context;

    public HotSettingModelImp(Context context,IHeatSettingPresenter presenter) {
        this.presenter = presenter;
        this.context=context;

        doubleSideIsOpen= SharePreferceUtil.getInstance(context).getBoolean("doubleSideIsOpen",true);
        backIsOpen= SharePreferceUtil.getInstance(context).getBoolean("backIsOpen",true);
        doubleSideHotGrade= SharePreferceUtil.getInstance(context).getInt("doubleSideHotGrade",0);
        backHotGrade= SharePreferceUtil.getInstance(context).getInt("backHotGrade",0);
    }

    @Override
    public void doubleSideIsOpen(boolean isopen,int grade) {
        this.doubleSideIsOpen = isopen;
        this.doubleSideHotGrade = grade;
        NettyClient.getInstance().sendMessages(new HeatFilm(EV_Type.EV_HEATFILM)
                .setZone(0)
                .setValue(grade + 1)
                .setOpcode(isopen ? 1 : 0));
        SharePreferceUtil.getInstance(context).setCache("doubleSideIsOpen",doubleSideIsOpen);
        SharePreferceUtil.getInstance(context).setCache("doubleSideHotGrade",doubleSideHotGrade);
    }

    @Override
    public void backIsOpen(boolean isopen,int grade) {

        this.backIsOpen = isopen;
        this.backHotGrade = grade;
        NettyClient.getInstance().sendMessages(new HeatFilm(EV_Type.EV_HEATFILM)
                .setZone(1)
                .setValue(grade + 1)
                .setOpcode(isopen ? 1 : 0));
        SharePreferceUtil.getInstance(context).setCache("backIsOpen",backIsOpen);
        SharePreferceUtil.getInstance(context).setCache("backHotGrade",backHotGrade);
    }

    @Override
    public void setDoubleSideHotGrade(int grade) {
        this.doubleSideHotGrade = grade;
        NettyClient.getInstance().sendMessages(new HeatFilm(EV_Type.EV_HEATFILM)
                .setZone(0)
                .setValue(grade + 1)
                .setOpcode(1));
        SharePreferceUtil.getInstance(context).setCache("doubleSideHotGrade",doubleSideHotGrade);
    }

    @Override
    public void setbackHotGrade(int grade) {
        this.backHotGrade = grade;
        NettyClient.getInstance().sendMessages(new HeatFilm(EV_Type.EV_HEATFILM)
                .setZone(1)
                .setValue(grade + 1)
                .setOpcode(1));
        SharePreferceUtil.getInstance(context).setCache("backHotGrade",backHotGrade);
    }

    @Override
    public void onDestory() {

    }

    @Override
    public void backMessage(BaseData baseData) {
        if (baseData instanceof HeatFilm) {
            HeatFilm film = (HeatFilm) baseData;
            if (film.getState() == 0) {
                ToastUtils.getInstance().showToastShort("热量设置失败！！");
                presenter.heatSeting(film.getZone(), film.getOpcode() == 0, film.getValue()-1);
            } else {
                presenter.heatSeting(film.getZone(), film.getOpcode() == 1, film.getValue()-1);
            }
        }
    }

    @Override
    public void reSendMessage() {
        //未开启设置
        if (doubleSideHotGrade==-1&&backHotGrade==-1)return;
        doubleSideIsOpen(doubleSideIsOpen,doubleSideHotGrade);
        doubleSideIsOpen(backIsOpen,backHotGrade);
    }
}
