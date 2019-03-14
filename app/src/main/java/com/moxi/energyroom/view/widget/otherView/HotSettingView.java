package com.moxi.energyroom.view.widget.otherView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moxi.energyroom.R;
import com.moxi.energyroom.listener.HeatCallback;
import com.moxi.energyroom.listener.XJOnClickListener;
import com.moxi.energyroom.view.widget.WiperSwitch;

public class HotSettingView extends LinearLayout {
    public HotSettingView(Context context) {
        this(context, null);
    }

    public HotSettingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HotSettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
    }

    private ImageView top_image;
    private TextView middle_txt;
    private WiperSwitch switch_view;
    private SelectButton select_l;
    private SelectButton select_m;
    private SelectButton select_h;

    //属性变量
    private int topImg = -1;
    private String middleTxt = "";
    private boolean isSwitch = false;
    //依次等级0,1,2
    private int grade = -1;

    private HeatCallback heatCallback;

    private void initData(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_heat_setting, this);

        //控件初始化
        top_image=(ImageView) findViewById(R.id.top_image);
        middle_txt=(TextView) findViewById(R.id.middle_txt);
        switch_view=(WiperSwitch) findViewById(R.id.switch_view);
        select_l=(SelectButton) findViewById(R.id.select_l);
        select_m=(SelectButton) findViewById(R.id.select_m);
        select_h=(SelectButton) findViewById(R.id.select_h);

        select_l.setTag(0);
        select_m.setTag(1);
        select_h.setTag(2);
        //设置顶级监听
        select_l.setOnClickListener(clickListener);
        select_m.setOnClickListener(clickListener);
        select_h.setOnClickListener(clickListener);

        switch_view.setOnChangedListener(new WiperSwitch.OnChangedListener() {
            @Override
            public void OnChanged(WiperSwitch wiperSwitch, boolean checkState) {
                if (null!=heatCallback){
                    heatCallback.openOrClose(HotSettingView.this.getTag(),checkState);
                }
//                wiperSwitch.setChecked(isSwitch);
            }
        });

        //获得属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.hot_setting);
        if (null != array) {
            topImg = array.getResourceId(R.styleable.hot_setting_top_img, -1);
            middleTxt = array.getString(R.styleable.hot_setting_middle_txt);
            isSwitch = array.getBoolean(R.styleable.hot_setting_select, false);
            grade = array.getInteger(R.styleable.hot_setting_select_grade, 0);

            array.recycle();
        }
        initTopImage();
        initMiddleTxt();
        initSwich();
        initGrade();
    }
    private XJOnClickListener clickListener=new XJOnClickListener() {
        @Override
        public void onclickView(View view) {
            if (!isSwitch())return;
            if (view instanceof SelectButton){
                try {
                    int curGrade= (int) view.getTag();
                    if (null!=heatCallback){
                        heatCallback.onClickObj(HotSettingView.this.getTag(),curGrade);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };

    public int getTopImg() {
        return topImg;
    }

    public void setTopImg(int topImg) {
        this.topImg = topImg;
        initTopImage();
    }

    public String getMiddleTxt() {
        return middleTxt;
    }

    public void setMiddleTxt(String middleTxt) {
        this.middleTxt = middleTxt;
        initMiddleTxt();
    }

    public boolean isSwitch() {
        return isSwitch;
    }

    public void setSwitch(boolean aSwitch) {
        if (isSwitch==aSwitch)return;
        isSwitch = aSwitch;
        initSwich();
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        if (this.grade==grade)return;
        this.grade = grade;
        initGrade();
    }

    private void initTopImage() {
        if (topImg != -1) {
            top_image.setImageResource(topImg);
        } else {
            top_image.setImageBitmap(null);
        }
    }

    private void initMiddleTxt() {
        if (middleTxt != null)
            middle_txt.setText(middleTxt);
    }

    private void initSwich() {
        switch_view.setChecked(isSwitch);
//        initGrade();
    }

    public void setHeatCallback(HeatCallback heatCallback) {
        this.heatCallback = heatCallback;
    }

    private void initGrade() {
        select_l.setSelect(false);
        select_m.setSelect(false);
        select_h.setSelect(false);

//        if (!isSwitch)return;
        switch (grade) {
            case 1:
                select_m.setSelect(true);
                break;
            case 2:
                select_h.setSelect(true);
                break;
            default:
                select_l.setSelect(true);
                break;
        }
    }
}
