package com.tangtao.myapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class SquImageView extends android.support.v7.widget.AppCompatImageView {
    public SquImageView(Context context) {
        this(context,null);
    }

    public SquImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SquImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
