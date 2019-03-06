package com.moxi.energyroom.view.widget.otherView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;

import com.moxi.energyroom.R;
import com.moxi.energyroom.view.widget.extendView.AlphaImageView;

public class SelectButton  extends AlphaImageView {
    private boolean isSelect=false;
    private int select_img=-1;
    private int noselect_img=-1;
    public SelectButton(Context context) {
        this(context,null);
    }

    public SelectButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SelectButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context,attrs);
    }

    private void initData(Context context,AttributeSet attrs){
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.select_button);

        if (null!=typedArray){
            select_img=typedArray.getResourceId(R.styleable.select_button_is_select,-1);
            noselect_img=typedArray.getResourceId(R.styleable.select_button_no_select,-1);
            typedArray.recycle();
        }

        settingValue();
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
        settingValue();
    }

    public void  setSelectImg(@DrawableRes int select_img,@DrawableRes int noselect_img){
        this.select_img=select_img;
        this.noselect_img=noselect_img;
        settingValue();
    }
    private void settingValue(){
        if (isSelect()){
            if (select_img!=-1){
                setImageResource(select_img);
            }
        }else {
            if (noselect_img!=-1){
                setImageResource(noselect_img);
            }
        }
    }
}
