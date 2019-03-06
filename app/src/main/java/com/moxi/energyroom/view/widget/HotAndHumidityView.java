package com.moxi.energyroom.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moxi.energyroom.R;

public class HotAndHumidityView extends RelativeLayout {

    private ImageView item_logo;
    private TextView value_v;
    private TextView describe_v;
    //显示值
    private String value = "";
    //控件描述文字
    private String describe = "";
    //logo图片资源id
    private int logo_pic = -1;

    public HotAndHumidityView(Context context) {
        this(context, null);
    }

    public HotAndHumidityView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HotAndHumidityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_hot_and_humidity, this);
        item_logo=(ImageView)findViewById(R.id.item_logo);
        value_v=(TextView) findViewById(R.id.value);
        describe_v=(TextView) findViewById(R.id.describe);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.hotandhumidity);
        if (null != typedArray) {
            logo_pic = typedArray.getResourceId(R.styleable.hotandhumidity_item_logo, -1);
            value = typedArray.getString(R.styleable.hotandhumidity_show_value);
            describe = typedArray.getString(R.styleable.hotandhumidity_describe);
            typedArray.recycle();
        }
        initdata();
    }

    private void initdata(){
        if (logo_pic!=-1)
            item_logo.setImageResource(logo_pic);
        value_v.setText(value);
        describe_v.setText(describe);
    }

    /**
     * 设置显示值
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
        value_v.setText(value);
    }

    /**
     * 获得显示值
     * @return
     */
    public String getValue(){
        return value;
    }

    /**
     * 设置控件描述
     * @param describe
     */
    public void setDescribe(String describe) {
        this.describe = describe;
        describe_v.setText(describe);
    }

    /**
     * 设置显示的图片
     * @param logo_pic
     */
    public void setLogo_pic(int logo_pic) {
        this.logo_pic = logo_pic;
        if (logo_pic!=-1)
            item_logo.setImageResource(logo_pic);
    }
}
