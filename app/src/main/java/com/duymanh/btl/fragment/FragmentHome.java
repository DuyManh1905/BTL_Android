package com.duymanh.btl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duymanh.btl.R;
import com.duymanh.btl.UpdateDeleteActivity;
import com.duymanh.btl.adapter.RecycleViewAdapter;
import com.duymanh.btl.dal.SQLiteHelper;
import com.duymanh.btl.model.Schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FragmentHome extends Fragment implements RecycleViewAdapter.ItemListener {

    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private SQLiteHelper db;
    private TextView cntTask;

    private ImageView imageView;

    private ImageView option;

    private TextView nowDate, helloName;

    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);

        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        cntTask = view.findViewById(R.id.cntTask);
        searchView = view.findViewById(R.id.search);
        imageView = view.findViewById(R.id.imageView5);
        option = view.findViewById(R.id.option);
        registerForContextMenu(imageView);
        registerForContextMenu(option);
        //
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        initView(view);

        List<Schedule> list = db.getByDate(f.format(d));

        adapter.setList(list);
        cntTask.setText("Have "+cntTask(list)+" task");

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }

    private void initView(View view) {
        helloName = view.findViewById(R.id.helloName);
        nowDate = view.findViewById(R.id.nowDate);
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        String day = f.format(d);
        nowDate.setText(day);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Date d = new Date();
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                List<Schedule> list = db.searchByTitleandDate(newText,f.format(d));
                cntTask.setText("Have :"+cntTask(list)+" task");
                adapter.setList(list);
                return false;
            }
        });
    }


    private int cntTask(List<Schedule> list){
        return list.size();
    }
    @Override
    public void onItemClick(View view, int position) {
        Schedule schedule = adapter.getSchedule(position);
        System.out.println("da vao day");
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item",schedule);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        List<Schedule> list = db.getByDate(f.format(d));
        adapter.setList(list);
        cntTask.setText("Have "+cntTask(list)+" task");
    }

}
