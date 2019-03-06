package com.moxi.energyroom.model.impl.mian;

import com.moxi.energyroom.model.inter.main.IHotSettingModel;
import com.moxi.energyroom.presenter.inter.IMainAPresenter;

public class HotSettingModelImp implements IHotSettingModel {
    private boolean doubleSideIsOpen = false;
    private boolean backIsOpen = false;
    private int doubleSideHotGrade = 0;
    private int backHotGrade = 0;
    private IMainAPresenter presenter;

    public HotSettingModelImp(IMainAPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void doubleSideIsOpen(boolean is) {
        this.doubleSideIsOpen = is;
    }

    @Override
    public void backIsOpen(boolean is) {
        this.backIsOpen = is;
    }

    @Override
    public void setDoubleSideHotGrade(int grade) {
        this.doubleSideHotGrade = grade;
    }

    @Override
    public void setbackHotGrade(int grade) {
        this.backHotGrade = grade;
    }

    @Override
    public void onDestory() {

    }
}
