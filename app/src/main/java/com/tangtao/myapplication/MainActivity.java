package com.tangtao.myapplication;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView show;
    private BottomSheetDialog dialog;
    private RecyclerView recyclerView;
    private EditText edit;
    private List<String> mDatas = new ArrayList<>();

    private VerticalAutoScrollLayout autoScrollLayout;
    private List<List<PersonBean>> mLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //show = findViewById(R.id.show);

        mLists = new ArrayList<>();
        autoScrollLayout = findViewById(R.id.autoScrollLayout);
        for (int i = 0; i < 3; i++) {
            List<PersonBean> list = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                PersonBean person = new PersonBean();
                person.setId(i);
                person.setName("滚动" + j);
                if (j  == 0) {
                    person.setAvator("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1575797941&di=68b60833a3f4ca99d93f7f6e7ea24af5&src=http://pic36.nipic.com/20131224/6315408_120614016384_2.jpg");
                } else if (j==1){
                    person.setAvator("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575808026050&di=fbe16abb04b9566ecae47f7a620cef0e&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F17%2F51%2F02%2F70r58PIChHc_1024.jpg");
                }else {
                    person.setAvator("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575808026051&di=dbc076df0b4ad43118b704b845cd279a&imgtype=0&src=http%3A%2F%2Fpic38.nipic.com%2F20140217%2F18011310_172241656175_2.jpg");
                }
                list.add(person);
            }
            // Log.e("内部list长度","=="+list.size());
            mLists.add(list);
        }
        // Log.e("外部list长度","=="+mLists.size());
        autoScrollLayout.setColumn(3).setLists(mLists);

    }
}
