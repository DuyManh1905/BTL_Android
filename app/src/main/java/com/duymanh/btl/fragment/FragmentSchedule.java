package com.duymanh.btl.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duymanh.btl.AddActivity;
import com.duymanh.btl.R;
import com.duymanh.btl.UpdateDeleteActivity;
import com.duymanh.btl.adapter.RecycleViewAdapter;
import com.duymanh.btl.dal.SQLiteHelper;
import com.duymanh.btl.model.Schedule;

import java.util.Calendar;
import java.util.List;

public class FragmentSchedule extends Fragment implements RecycleViewAdapter.ItemListener,View.OnClickListener{

    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tvCount;
    private Button btSearch;
    private SearchView searchView;
    private EditText eFrom, eTo;
    private Spinner spCategory;

    private SQLiteHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule,container,false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);

        initView(view);

        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        List<Schedule> list = db.getAll();
        adapter.setList(list);
        tvCount.setText("Have :"+count(list)+" task");
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
        //

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Schedule> list = db.searchByTitle(newText);
                tvCount.setText("Have :"+count(list)+" task");
                adapter.setList(list);
                return false;
            }
        });
        eFrom.setOnClickListener(this);
        eTo.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cate = spCategory.getItemAtPosition(position).toString();
                List<Schedule> list;
                if(!cate.equalsIgnoreCase("all")){
                    list=db.searchByCategory(cate);
                }
                else{
                    list=db.getAll();
                }
                adapter.setList(list);
                tvCount.setText("Have :"+count(list)+" task");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private int count(List<Schedule> list){
        return list.size();
    }

    private void initView(View view) {
        tvCount = view.findViewById(R.id.tvCount);
        btSearch = view.findViewById(R.id.btSearch);
        searchView = view.findViewById(R.id.search);
        eFrom = view.findViewById(R.id.eFrom);
        eTo = view.findViewById(R.id.eTo);
        spCategory = view.findViewById(R.id.spCategory);
        String arr[] = getResources().getStringArray(R.array.category);
        String arr1[] = new String[arr.length+1];
        arr1[0]="All";
        for(int i=0;i<arr.length;i++){
            arr1[i+1]=arr[i];
        }
        spCategory.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_spinner,arr1));
    }


    @Override
    public void onItemClick(View view, int position) {
        System.out.println("kkkkk");
        Schedule schedule = adapter.getSchedule(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item",schedule);
        startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
        List<Schedule> list = db.getAll();
        adapter.setList(list);
    }


    @Override
    public void onClick(View v) {
        if(v==eFrom){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String date="";
                    if(month>8){
                        date = dayOfMonth+"/"+(month+1)+"/"+year;
                    }
                    else{
                        date = dayOfMonth+"/0"+(month+1)+"/"+year;
                    }
                    eFrom.setText(date);
                }
            },year,month,day);
            dialog.show();
        }
        if(v==eTo){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String date="";
                    if(month>8){
                        date = dayOfMonth+"/"+(month+1)+"/"+year;
                    }
                    else{
                        date = dayOfMonth+"/0"+(month+1)+"/"+year;
                    }
                    eTo.setText(date);
                }
            },year,month,day);
            dialog.show();
        }
        if(v==btSearch){
            String from = eFrom.getText().toString();
            String to = eTo.getText().toString();
            if(!from.isEmpty() && !to.isEmpty()){
                List<Schedule> list = db.searchByDateFromTo(from,to);
                adapter.setList(list);
                tvCount.setText("Have :"+count(list)+" task");

            }
        }
    }
}
