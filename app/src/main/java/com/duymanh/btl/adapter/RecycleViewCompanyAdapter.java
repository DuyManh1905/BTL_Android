package com.duymanh.btl.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duymanh.btl.R;
import com.duymanh.btl.model.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecycleViewCompanyAdapter extends RecyclerView.Adapter<RecycleViewCompanyAdapter.HomeViewHolder> {

    private List<Company> list;

    private ItemListener itemListener;

    public RecycleViewCompanyAdapter() {
        list = new ArrayList<>();
    }


    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<Company> list) {
        System.out.println("uuuuu: Da vao day");
        this.list = list;
        notifyDataSetChanged();
    }

    public Company getSchedule(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company,parent,false);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Company company = list.get(position);
        holder.companyName.setText(company.getName());
        holder.companyAdress.setText(company.getAddress());
        if(company.getStype()!=null){
            holder.companyType.setText(company.getStype());
        }
        else{
            holder.companyType.setText("IT - Phần mềm");
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
        holder.iconCompany.setImageResource(images[randomNumber]);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView companyName,companyAdress,companyType;

        private ImageView iconCompany;

        public HomeViewHolder(@NonNull View view) {
            super(view);
            companyName = view.findViewById(R.id.company_name);
            companyAdress = view.findViewById(R.id.company_address);
            companyType = view.findViewById(R.id.company_type);
            iconCompany = view.findViewById(R.id.iconCompany);
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
