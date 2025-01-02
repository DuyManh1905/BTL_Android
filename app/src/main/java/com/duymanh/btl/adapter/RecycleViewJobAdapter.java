package com.duymanh.btl.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.duymanh.btl.R;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.model.Job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecycleViewJobAdapter extends RecyclerView.Adapter<RecycleViewJobAdapter.HomeViewHolder> {

    private List<Job> list;
    private Context context;

    private ItemListener itemListener;

    public RecycleViewJobAdapter(Context context) {
        list = new ArrayList<>();
        this.context = context;
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
        holder.jobAddress.setText(job.getJobRequirement().getArea());

        long numberDayEnd = getDayToEnd(job.getEndAt());
        if(numberDayEnd<=0){
            holder.soNgayKT.setText("Đã hết hạn ứng tuyển");
        }
        else{
            holder.soNgayKT.setText(getDayToEnd(job.getEndAt())+"");
        }
        String imageUrl = job.getCompany().getAvataURL();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.company2) // Hình ảnh mặc định khi tải
                .error(R.drawable.company2)       // Hình ảnh khi có lỗi
                .into(holder.imgCompany);

        if(job.getJobRequirement()!=null && !job.getJobRequirement().getExperience().equals("0")){
            holder.jobExperience.setText("> "+job.getJobRequirement().getExperience()+" năm kinh nghiệm");
        }
        else{
            holder.jobExperience.setText("khong y/c kinh nghiem");
        }
        //check saved
        checkJobSavedStatus(job.getId(), holder.ic_savedJob);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView jobTitle,jobLocation,jobSalary,jobExperience, jobAddress,soNgayKT;
        private ImageView imgCompany, ic_savedJob;

        public HomeViewHolder(@NonNull View view) {
            super(view);
            soNgayKT = itemView.findViewById(R.id.soNgayKT);
            jobAddress = itemView.findViewById(R.id.jobAddress);
            jobTitle = itemView.findViewById(R.id.job_title);
            jobLocation = itemView.findViewById(R.id.job_location);
            jobSalary = itemView.findViewById(R.id.job_salary);
            imgCompany = itemView.findViewById(R.id.imgCompany);
            jobExperience = itemView.findViewById(R.id.job_experience);
            ic_savedJob = itemView.findViewById(R.id.ic_savedJob);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemListener!=null){
                itemListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public long getDayToEnd(String end){
        long daysBetween = 10;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            // Chuyển đổi ngày từ API thành Date
            Date targetDate = sdf.parse(end);
            // Ngày hiện tại
            Date currentDate = new Date();

            // Tính số ngày
            long diffInMillis = targetDate.getTime() - currentDate.getTime();
            daysBetween = TimeUnit.MILLISECONDS.toDays(diffInMillis);
            Log.d("ldm", "getDayToEnd: "+daysBetween);

        } catch (ParseException e) {
            Log.d("ldm", "getDayToEndBiLoi: "+daysBetween);
            e.printStackTrace();
        }
        return daysBetween;
    }

    private void checkJobSavedStatus(int jobId, ImageView icSavedJob) {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        Call<Boolean> call = apiService.checkUserSaveJob(Integer.parseInt(userId),jobId);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful() && response.body()!=null){
                    boolean isSaved = response.body();
                    if(isSaved) {
                        icSavedJob.setImageResource(R.drawable.ic_bookmark);
                    }
                    else{
                        icSavedJob.setImageResource(R.drawable.ic_bookmark_border);
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("API_ERROR", "Failed to check job saved status", t);
                icSavedJob.setImageResource(R.drawable.ic_bookmark_border);
            }
        });
    }


    public interface ItemListener {
        void onItemClick(View view,int position);
    }
}
