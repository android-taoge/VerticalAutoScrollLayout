package com.tangtao.myapplication;

import android.app.Person;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomRecyclerviewAdapter extends RecyclerView.Adapter<CustomRecyclerviewAdapter.CustomViewHolder> {

    private Context mContext;
    private List<PersonBean> mDatas;

    public CustomRecyclerviewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, viewGroup, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, final int position) {
        customViewHolder.title.setText(mDatas.get(position % mDatas.size()).getName());
        Glide.with(mContext).load(mDatas.get(position % mDatas.size()).getAvator()).into(customViewHolder.img);
        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setDatas(List<PersonBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        SquImageView img;
        TextView title;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.tv_content);
        }
    }
}
