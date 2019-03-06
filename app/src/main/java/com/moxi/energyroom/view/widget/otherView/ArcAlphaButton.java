package com.moxi.energyroom.view.widget.otherView;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;

import com.moxi.energyroom.R;
import com.moxi.energyroom.view.widget.extendView.BackAlphaButton;

public class ArcAlphaButton extends BackAlphaButton {
    private boolean isSelect=false;

    public ArcAlphaButton(Context context) {
        this(context,null);
    }

    public ArcAlphaButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ArcAlphaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBack();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed){
            int textsize=getWidth();
            setHeight(textsize);

            setTextSize(textsize/2);
            String value=getText().toString();
            Spannable WordtoSpan = new SpannableString(value);
            int start=value.indexOf("min");
            int end=start+3;
            WordtoSpan.setSpan(new AbsoluteSizeSpan(textsize/4), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(WordtoSpan);
        }
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
        initBack();
    }

    private void initBack(){
        if (isSelect()){
            setBackgroundResource(R.drawable.arc_main_color);
        }else {
            setBackgroundResource(R.drawable.arc_default_color);
        }
    }
}
