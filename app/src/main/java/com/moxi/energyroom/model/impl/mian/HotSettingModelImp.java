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
                .setOpcode(isopen ? 1 : 0));
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
        if (grade==-1)return;
        NettyClient.getInstance().sendMessages(new HeatFilm(EV_Type.EV_HEATFILM)
                .setZone(0)
                .setValue(grade + 1)
                .setOpcode(1));
    }

    @Override
    public void setbackHotGrade(int grade) {
        if (grade==-1)return;
        NettyClient.getInstance().sendMessages(new HeatFilm(EV_Type.EV_HEATFILM)
                .setZone(1)
                .setValue(grade + 1)
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
//                presenter.heatSeting(film.getZone(),  film.getValue()-1);
            } else {
                presenter.heatSeting(film.getZone(),  film.getValue()-1);
                if (film.getZone()==0){
                    this.doubleSideHotGrade = film.getValue()-1;
                    SharePreferceUtil.getInstance(context).setCache("doubleSideHotGrade",doubleSideHotGrade);
                }else if (film.getZone()==1){
                    this.backHotGrade = film.getValue()-1;
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
                ToastUtils.getInstance().showToastShort(hitn+"加热膜关闭失败！！");
                presenter.heatSeting(swi.getZone(), swi.getValue() == 0);
            }else {
                if (swi.getZone()==0){
                    this.doubleSideIsOpen = swi.getValue()==1;
//                    SharePreferceUtil.getInstance(context).setCache("doubleSideIsOpen",doubleSideIsOpen);
                }else if (swi.getZone()==1){
                    this.backIsOpen = swi.getValue()==1;
//                    SharePreferceUtil.getInstance(context).setCache("backIsOpen",backIsOpen);
                }
                presenter.heatSeting(swi.getZone(), swi.getValue() == 1);
                presenter.canStartTime(isCanStartTime());
            }

        }
    }

    @Override
    public void reSendMessage() {
        int doubleSideHotGrade= SharePreferceUtil.getInstance(context).getInt("doubleSideHotGrade",1);
        int backHotGrade= SharePreferceUtil.getInstance(context).getInt("backHotGrade",1);
        //未开启设置
        if (doubleSideHotGrade==-1&&backHotGrade==-1)return;
        setDoubleSideHotGrade(doubleSideHotGrade);
        setbackHotGrade(backHotGrade);
    }
}
