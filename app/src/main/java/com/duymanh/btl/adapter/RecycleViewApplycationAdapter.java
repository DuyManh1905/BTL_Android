package com.duymanh.btl.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.duymanh.btl.R;
import com.duymanh.btl.dto.ApplicationFormDTO;
import com.duymanh.btl.model.ApplicationForm;
import com.duymanh.btl.model.Company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecycleViewApplycationAdapter extends RecyclerView.Adapter<RecycleViewApplycationAdapter.HomeViewHolder> {

    private List<ApplicationForm> list;

    private ItemListener itemListener;

    public RecycleViewApplycationAdapter() {
        list = new ArrayList<>();
    }


    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<ApplicationForm> list) {
        System.out.println("vvvv: Da vao day");
        this.list = list;
        notifyDataSetChanged();
    }

    public ApplicationForm getSchedule(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_application_form,parent,false);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        ApplicationForm applicationForm = list.get(position);
        holder.companyName.setText(applicationForm.getJob().getCompany().getName());
        holder.jobTitle.setText(applicationForm.getJob().getTitle());
        holder.jobAddress.setText(applicationForm.getJob().getJobRequirement().getArea());
        holder.job_salary.setText(applicationForm.getJob().getSalary());
        holder.job_end_date.setText(convertDate(applicationForm.getJob().getEndAt()));

        if (applicationForm.getStatus() == 0) {
            holder.status.setText("Đang chờ được xét");
        } else if (applicationForm.getStatus() == 1) {
            holder.status.setText("Đã được chấp nhận");
            holder.status.setBackgroundResource(R.drawable.normal_button_green);
            holder.status.setTextColor(Color.WHITE); // Đổi màu chữ thành trắng
        }


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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView jobTitle,companyName,jobAddress, job_salary, job_end_date;
        private ImageView imgCompany;
        private LinearLayout xemCongTy, xemLaiCV;

        private Button status;

        public HomeViewHolder(@NonNull View view) {
            super(view);
            companyName = view.findViewById(R.id.company_name);
            jobTitle = view.findViewById(R.id.job_title);
            jobAddress = view.findViewById(R.id.jobAddress);
            job_salary = view.findViewById(R.id.job_salary);
            job_end_date = view.findViewById(R.id.job_end_date);
            imgCompany = view.findViewById(R.id.imgCompany);
            xemCongTy = view.findViewById(R.id.xemCongTy);
            xemLaiCV = view.findViewById(R.id.xemCV);
            status = view.findViewById(R.id.status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListener != null) {
                        itemListener.onItemClick(v, getAdapterPosition()); // Đảm bảo onItemClick vẫn được gọi
                    }
                }
            });

            // Set listeners cho các nút
            xemCongTy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListener != null) {
                        itemListener.onViewCompanyClick(v, getAdapterPosition());
                    }
                }
            });

            xemLaiCV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListener != null) {
                        itemListener.onViewCVClick(v, getAdapterPosition());
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            if(itemListener!=null){
                itemListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface ItemListener {
        void onItemClick(View view, int position);
        void onViewCompanyClick(View view, int position);  // Thêm sự kiện cho nút Xem Công Ty
        void onViewCVClick(View view, int position);  // Thêm sự kiện cho nút Xem CV
    }


    public static String convertDate(String inputDate) {
        try {
            // Định dạng đầu vào của bạn (bao gồm cả thời gian)
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            // Định dạng đầu ra bạn mong muốn
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

            // Chuyển đổi chuỗi đầu vào thành đối tượng Date
            java.util.Date date = inputFormat.parse(inputDate);

            // Chuyển đối tượng Date thành chuỗi theo định dạng mới
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "01/01/1990";
        }
    }
}
