package com.tangtao.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.List;


public class VerticalAutoScrollLayout extends LinearLayout {

    private Context mContext;
    private int mColumn = 3;
    private List<List<PersonBean>> mLists;


    public VerticalAutoScrollLayout(Context context) {
        this(context, null);
    }

    public VerticalAutoScrollLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalAutoScrollLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);
        this.mContext = context;

    }

    public VerticalAutoScrollLayout setColumn(int mColumn) {
        this.mColumn = mColumn;
        return this;
    }

    public void setLists(List<List<PersonBean>> mLists) {
        removeAllViews();
        this.mLists = mLists;
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 0, 10, 0);
        layoutParams.weight = 1;
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int childWidth = display.getWidth() / 3;
        layoutParams.height = childWidth + dip2px(mContext, 40);
        setLayoutParams(layoutParams);
        for (int i = 0; i < mColumn; i++) {
            View childView = getView(i);
            if (childView == null) {
                throw new NullPointerException();
            }
            addView(childView, i, layoutParams);
        }
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    @SuppressLint("ClickableViewAccessibility")
    private View getView(final int position) {

        final AutoScrollRecyclerView recyclerView = new AutoScrollRecyclerView(mContext);
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        layoutManager.setSpeedSlow(10);
        recyclerView.setLayoutManager(layoutManager);
        CustomRecyclerviewAdapter adapter = new CustomRecyclerviewAdapter(mContext);
        adapter.setDatas(mLists.get(position));
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN | MotionEvent.ACTION_UP:
                return false;
        }
        return false;
    }




}
