package com.moxi.energyroom.view.widget.BaseView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by xj on 2018/9/13.
 */

public class XJTextView extends android.support.v7.widget.AppCompatTextView {
    public XJTextView(Context context) {
        super(context);
    }

    public XJTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XJTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextV(CharSequence text) {
        if (null == text) return;
        setText(text);
    }
}
