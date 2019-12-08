package com.tangtao.myapplication;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

public class CustomLinearLayoutManager extends LinearLayoutManager {

    private float moveSpeed = 25f;


    public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }


    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new CustomLinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }


    public void setSpeedSlow(int n) {
        moveSpeed = 25f * n;
    }


    private class CustomLinearSmoothScroller extends LinearSmoothScroller {
        public CustomLinearSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return moveSpeed / displayMetrics.densityDpi;
        }
    }
}
