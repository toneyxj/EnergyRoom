package com.moxi.energyroom.view.widget.otherView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.moxi.energyroom.R;
import com.moxi.energyroom.view.widget.BaseView.XJTextView;

public class LodingLayout extends RelativeLayout implements View.OnClickListener {
    public LodingLayout(Context context) {
        this(context,null);
    }

    public LodingLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LodingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    private XJTextView hitnMsg;
    private void initLayout(Context context){
        LayoutInflater.from(context).inflate(R.layout.loding_layout,this);
        setOnClickListener(this);
        hitnMsg=(XJTextView) findViewById(R.id.hitn_txt);
    }

    public void setHitnMsg(String msg){
        hitnMsg.setText(msg);
    }

    @Override
    public void onClick(View v) {

    }
}
