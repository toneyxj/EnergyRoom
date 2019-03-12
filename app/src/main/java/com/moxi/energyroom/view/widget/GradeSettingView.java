package com.moxi.energyroom.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.moxi.energyroom.R;
import com.moxi.energyroom.view.widget.BaseView.XJImageView;
import com.moxi.energyroom.view.widget.BaseView.XJTextView;
import com.moxi.energyroom.view.widget.extendView.AlphaRelatieLayout;

public class GradeSettingView extends AlphaRelatieLayout {
    public GradeSettingView(Context context) {
        this(context,null);
    }

    public GradeSettingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GradeSettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context,attrs);
    }

    private XJImageView image;
    private XJTextView describe_v;

    private int image_focus=-1;
    private int image_default=-1;
    private int txt_focus=-1;
    private int txt_default=-1;
    private String describe;
    /**
     * 是否选中控件
     */
    private boolean isSelect=false;

    private void initData(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.widget_other_setting,this);

        image=(XJImageView)findViewById(R.id.image);
        describe_v=(XJTextView) findViewById(R.id.describe);

        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.grade_setting);

        if (null!=typedArray){
            image_focus=typedArray.getResourceId(R.styleable.grade_setting_image_focus,-1);
            image_default=typedArray.getResourceId(R.styleable.grade_setting_image_default,-1);
            txt_focus=typedArray.getColor(R.styleable.grade_setting_txt_focus,getResources().getColor(R.color.black));
            txt_default=typedArray.getColor(R.styleable.grade_setting_txt_default,getResources().getColor(R.color.font_3));
            describe=typedArray.getString(R.styleable.grade_setting_grade_text);

            typedArray.recycle();
        }
        initDataSetting();
    }

    public void setDescribe(String describe) {
        this.describe = describe;
        describe_v.setText(describe);
    }

    public void setDescribeColor(@DrawableRes int txt_focus,@DrawableRes int txt_default){
            this.txt_focus=txt_focus;
            this.txt_default=txt_default;
        initDataSetting();
    }
    public void setImage(@ColorRes int image_focus,@ColorRes int image_default){
            this.image_focus=image_focus;
            this.image_default=image_default;
        initDataSetting();
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
        initDataSetting();
    }

    private void initDataSetting(){
        if (isSelect){
            if (image_focus==-1){
                image.setImageBitmap(null);
            }else {
                image.setImageResource(image_focus);
                image.setAlpha(1f);
            }
            describe_v.setTextColor(txt_focus);
        }else {
            if (image_default==-1){
                image.setImageBitmap(null);
            }else {
                image.setImageResource(image_default);
                image.setAlpha(0.5f);
            }
            describe_v.setTextColor(txt_default);
        }
        describe_v.setText(describe);
    }
}
