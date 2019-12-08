package com.tangtao.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
            Log.e("添加了第", "==" + i + "个view");
        }
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    SparseArray<Disposable> tasks = new SparseArray<>();

    @SuppressLint("ClickableViewAccessibility")
    private View getView(final int position) {

        final AutoScrollRecyclerView recyclerView = new AutoScrollRecyclerView(mContext);
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        layoutManager.setSpeedSlow(10);
        recyclerView.setLayoutManager(layoutManager);
        CustomRecyclerviewAdapter adapter = new CustomRecyclerviewAdapter(mContext);
        adapter.setDatas(mLists.get(position));
        recyclerView.setAdapter(adapter);
        //recyclerView.start();
        final Disposable task = startAuto(recyclerView);
       // tasks.put(position, task);
       /* recyclerView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (tasks.keyAt(position) == position) {
                            Disposable cancelTask = tasks.valueAt(position);
                            cancelTask.dispose();
                            cancelTask = null;
                            tasks.removeAt(position);
                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        Disposable startTask = startAuto(recyclerView);
                        tasks.put(position, startTask);
                        return true;
                }
                return false;
            }

        });*/
        return recyclerView;
    }

    private Disposable startAuto(final AutoScrollRecyclerView recyclerView) {
        Disposable mAutoTask = null;

        final int[] mCurrentPosition = {-1};

        if (mAutoTask != null) {
            mAutoTask.isDisposed();
        }

        mAutoTask = Observable.interval(1, 2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {

            @Override
            public void accept(Long aLong) {
                Log.e("wizardev", "accept: " + aLong.intValue());
                if (mCurrentPosition[0] == 0) {
                    mCurrentPosition[0] = aLong.intValue();
                } else {
                    mCurrentPosition[0]++;
                }
                recyclerView.smoothScrollToPosition(mCurrentPosition[0]);
            }
        });
        return mAutoTask;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return false;
            case MotionEvent.ACTION_MOVE:   //表示父类需要
                return true;
            case MotionEvent.ACTION_UP:
                return false;
            default:
                break;
        }
        return false;    //如果设置拦截，除了down,其他都是父类处理
    }


   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("TAG", "You down layout");

                break;
            case MotionEvent.ACTION_UP:
                Log.d("TAG", "You up layout");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("TAG", "You move layout");
        }
        return true;
    }*/


}
