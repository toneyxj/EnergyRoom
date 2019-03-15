package com.moxi.energyroom.model.impl.mian;

import android.content.Context;

import com.moxi.energyroom.Been.EV_Type;
import com.moxi.energyroom.Been.transmitData.BaseData;
import com.moxi.energyroom.Been.transmitData.HeatFilm;
import com.moxi.energyroom.Been.transmitData.HeatSwitch;
import com.moxi.energyroom.model.inter.main.IHotSettingModel;
import com.moxi.energyroom.netty.NettyClient;
import com.moxi.energyroom.presenter.inter.IHeatSettingPresenter;
import com.moxi.energyroom.presenter.inter.IMainAPresenter;
import com.moxi.energyroom.utils.APPLog;
import com.moxi.energyroom.utils.SharePreferceUtil;
import com.moxi.energyroom.utils.ToastUtils;

/**
 * 加热膜设置
 */
public class HotSettingModelImp implements IHotSettingModel {
    private boolean doubleSideIsOpen = false;
    private boolean backIsOpen = false;
    private int doubleSideHotGrade = -1;
    private int backHotGrade = -1;
    private IHeatSettingPresenter presenter;
    private Context context;

    public HotSettingModelImp(Context context,IHeatSettingPresenter presenter) {
        this.presenter = presenter;
        this.context=context;
    }

    @Override
    public boolean doubleSideIsOpen(boolean isopen) {
        if (doubleSideHotGrade<0)return false;
        if (doubleSideIsOpen==isopen)return true;
        NettyClient.getInstance().sendMessages(new HeatSwitch(EV_Type.EV_FILMSWITCH)
                .setZone(0)
                .setValue(isopen?1:0)
                .setOpcode(1));
        return true;
    }


    @Override
    public boolean backIsOpen(boolean isopen) {
        if (backHotGrade<0)return false;
        if (backIsOpen==isopen)return true;
        NettyClient.getInstance().sendMessages(new HeatSwitch(EV_Type.EV_FILMSWITCH)
                .setZone(1)
                .setValue(isopen?1:0)
                .setOpcode( 1));
        return true;
    }

    @Override
    public void setDoubleSideHotGrade(int grade) {
        grade++;
        if (grade==-1||grade==doubleSideHotGrade)return;
        APPLog.e("setDoubleSideHotGrade-进入发送");
        NettyClient.getInstance().sendMessages(new HeatFilm(EV_Type.EV_HEATFILM)
                .setZone(0)
                .setValue(grade)
                .setOpcode(1));
    }

    @Override
    public void setbackHotGrade(int grade) {
        grade++;
        if (grade==-1||grade==backHotGrade)return;
        APPLog.e("setbackHotGrade-进入发送");
        NettyClient.getInstance().sendMessages(new HeatFilm(EV_Type.EV_HEATFILM)
                .setZone(1)
                .setValue(grade)
                .setOpcode(1));
    }

    @Override
    public boolean isCanStartTime() {
        return (doubleSideHotGrade>=0&&doubleSideIsOpen)||(backHotGrade>=0&&backIsOpen);
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
            } else {
                presenter.heatSeting(film.getZone(),  film.getValue()-1);
                if (film.getZone()==0){
                    this.doubleSideHotGrade = film.getValue();
                    SharePreferceUtil.getInstance(context).setCache("doubleSideHotGrade",doubleSideHotGrade);
                }else if (film.getZone()==1){
                    this.backHotGrade = film.getValue();
                    SharePreferceUtil.getInstance(context).setCache("backHotGrade",backHotGrade);
                }
            }
        }else if (baseData instanceof HeatSwitch){
            HeatSwitch swi= (HeatSwitch) baseData;
            if (swi.getState()==0){
                String hitn="";
                switch (swi.getZone()){
                    case 0:
                        hitn="两侧";
                        break;
                    case 1:
                        hitn="后背";
                        break;
                        default:
                            break;
                }
                ToastUtils.getInstance().showToastShort(hitn+"加热膜操作失败！！");
                presenter.heatSeting(swi.getZone(), swi.getValue() == 0);
            }else {
                //当开关关闭的时候控制显示变灰
                if (swi.getValue()==0) {
                    presenter.heatSeting(swi.getZone(), -1);
                    if (swi.getZone()==0){
                        int dg=doubleSideHotGrade-1;
                        doubleSideHotGrade=-1;
                        setDoubleSideHotGrade(dg);
                    }else {
                        int dg=backHotGrade-1;
                        backHotGrade=-1;
                        setbackHotGrade(dg);
                    }
                }
                if (swi.getZone()==0){
                    this.doubleSideIsOpen = swi.getValue()==1;
                }else if (swi.getZone()==1){
                    this.backIsOpen = swi.getValue()==1;
                }
                presenter.heatSeting(swi.getZone(), swi.getValue() == 1);
                presenter.canStartTime(isCanStartTime());
            }

        }
    }

    @Override
    public void reSendMessage() {
        int doubleSideHotGrade= SharePreferceUtil.getInstance(context).getInt("doubleSideHotGrade",-1)-1;
        int backHotGrade= SharePreferceUtil.getInstance(context).getInt("backHotGrade",-1)-1;
        APPLog.e("doubleSideHotGrade",doubleSideHotGrade);
        APPLog.e("backHotGrade",backHotGrade);
        //未开启设置
        if (doubleSideHotGrade==-1&&backHotGrade==-1)return;
        setDoubleSideHotGrade(doubleSideHotGrade);
        setbackHotGrade(backHotGrade);
    }
}
