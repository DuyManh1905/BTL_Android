package com.duymanh.btl.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duymanh.btl.R;
import com.duymanh.btl.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder> {

    private List<Schedule> list;

    private ItemListener itemListener;

    public RecycleViewAdapter() {
        list = new ArrayList<>();
    }


    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<Schedule> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Schedule getSchedule(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Schedule s = list.get(position);
        holder.title.setText(s.getTitle());
        holder.category.setText(s.getCategory());
        holder.time.setText(s.getTime());
        holder.date.setText(s.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title,category,time,date;

        public HomeViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.tvTitle);
            category = view.findViewById(R.id.tvCategory);
            date = view.findViewById(R.id.tvDate);
            time = view.findViewById(R.id.tvTime);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemListener!=null){
                itemListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface ItemListener {
        void onItemClick(View view,int position);
    }
}
