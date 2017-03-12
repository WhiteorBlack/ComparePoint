package com.blm.comparepoint;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blm.comparepoint.interfacer.OnClickListener;
import com.blm.comparepoint.interfacer.OnItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by 41508 on 2017/3/6.
 */

public abstract class BaseRecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private OnClickListener onClickListener;
    public List dataList;

    public BaseRecyAdapter(List dataList){
        this.dataList=dataList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayout(),parent,false));
    }

    public abstract int getLayout();

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v,holder.getLayoutPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(v,holder.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setOnItemClickListener(OnItemClickListener l){
        this.onItemClickListener=l;
    }

    public void setOnClickListener(OnClickListener l){
        this.onClickListener=l;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       public View parent;
        public ViewHolder(View itemView) {
            super(itemView);
            this.parent=itemView;
        }

        public void setText(String text,int viewId){
            ((TextView)parent.findViewById(viewId)).setText(text);
        }

        public void setText(int resId,int viewId){
            ((TextView)parent.findViewById(viewId)).setText(resId);
        }

        public void setImage(String url,int viewId){
            Glide.with(parent.getContext()).load(url).into((ImageView)parent.findViewById(viewId));
        }

        public void setImage(int resId,int viewId){
            Glide.with(parent.getContext()).load(resId).into((ImageView)parent.findViewById(viewId));
        }

        public void setOnClick(int resId, final int pos){
            if (onClickListener!=null){
                parent.findViewById(resId).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.OnClick(v,pos);
                    }
                });
            }
        }

        public TextView getTextView(int resId){
            return ((TextView)parent.findViewById(resId));
        }

        public ImageView getImageView(int resId){
            return ((ImageView) parent.findViewById(resId));
        }

        public Button getButton(int resId){
            return ((Button) parent.findViewById(resId));
        }

        public View getView(int resId){
            return parent.findViewById(resId);
        }
    }
}
