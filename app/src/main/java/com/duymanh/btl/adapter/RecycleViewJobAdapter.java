package com.duymanh.btl.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duymanh.btl.R;
import com.duymanh.btl.model.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecycleViewJobAdapter extends RecyclerView.Adapter<RecycleViewJobAdapter.HomeViewHolder> {

    private List<Job> list;

    private ItemListener itemListener;

    public RecycleViewJobAdapter() {
        list = new ArrayList<>();
    }


    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<Job> list) {
        System.out.println("uuuuu: Da vao day");
        this.list = list;
        notifyDataSetChanged();
    }

    public Job getSchedule(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job,parent,false);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Job job = list.get(position);
        holder.jobTitle.setText(job.getTitle());
        holder.jobLocation.setText(job.getCompany().getName());
        holder.jobSalary.setText(job.getSalary());
        Random r = new Random();
        int randomNumber = (int)(Math.random() * 5);
        int[] images = {
                R.drawable.company1,
                R.drawable.company2,
                R.drawable.company3,
                R.drawable.company4,
                R.drawable.company5
        };
        holder.imgCompany.setImageResource(images[randomNumber]);

        if(job.getJobRequirement()!=null){
            holder.jobExperience.setText("> "+job.getJobRequirement().getExperience()+" năm kinh nghiệm");
        }
        else{
            holder.jobExperience.setText("khong y/c kinh nghiem");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView jobTitle,jobLocation,jobSalary,jobExperience;
        private ImageView imgCompany;

        public HomeViewHolder(@NonNull View view) {
            super(view);
            jobTitle = itemView.findViewById(R.id.job_title);
            jobLocation = itemView.findViewById(R.id.job_location);
            jobSalary = itemView.findViewById(R.id.job_salary);
            imgCompany = itemView.findViewById(R.id.imgCompany);
            jobExperience = itemView.findViewById(R.id.job_experience);
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
