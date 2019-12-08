package com.tangtao.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AutoScrollRecyclerView extends RecyclerView {

    private int mState;
    private OnScrollListener mScrollListener;


    public AutoScrollRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public AutoScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScrollListener = new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mState = newState;
            }
        };
        addOnScrollListener(mScrollListener);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        //return mState != 0;
        //return super.onInterceptTouchEvent(e);
        return mState == 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                return mState == 0;

            case MotionEvent.ACTION_MOVE:
                return mState == 0;

            case MotionEvent.ACTION_POINTER_UP:
                return false;
        }
        return true;
    }
}
