package com.moxi.energyroom.view.widget.BaseView;

import android.content.Context;
import android.util.AttributeSet;

import com.moxi.energyroom.utils.GlideUtils;

/**
 * Created by xj on 2018/9/13.
 */

public class XJImageView extends android.support.v7.widget.AppCompatImageView {
    public XJImageView(Context context) {
        super(context);
    }

    public XJImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XJImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void loadImage(String path){
        if (path.startsWith("http://")||path.startsWith("https://")){
            //网络图片
            GlideUtils.getInstance().loadImage(getContext(),this,path);
        }else {
            //本地图片
            GlideUtils.getInstance().locatonPic(getContext(),this,path);
        }
    }
}
