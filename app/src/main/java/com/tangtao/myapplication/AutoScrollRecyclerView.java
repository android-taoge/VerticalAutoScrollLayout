package com.tangtao.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class AutoScrollRecyclerView extends RecyclerView {


    private boolean running = true;
    private boolean canRun = false;
    private Disposable mAutoTask = null;
    private int mCurrentPosition = -1;


    public AutoScrollRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public AutoScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        startAuto(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (running)
                    stopAuto();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (canRun)
                    startAuto(this);
                break;
        }
        return super.onTouchEvent(e);
    }


    private void startAuto(final AutoScrollRecyclerView recyclerView) {

        if (mAutoTask != null) {
            mAutoTask.isDisposed();
        }

        running = true;
        mAutoTask = Observable.interval(1, 2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {

            @Override
            public void accept(Long aLong) {

                if (mCurrentPosition == 0) {
                    mCurrentPosition = aLong.intValue();
                } else {
                    mCurrentPosition++;
                }
                recyclerView.smoothScrollToPosition(mCurrentPosition);
            }
        });

    }


    private void stopAuto() {
        running = false;
        canRun = true;
        if (mAutoTask != null) {
            mAutoTask.dispose();
            mAutoTask = null;
        }
    }
}
