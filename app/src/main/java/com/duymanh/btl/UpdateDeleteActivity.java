package com.duymanh.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.duymanh.btl.dal.SQLiteHelper;
import com.duymanh.btl.model.Schedule;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner sp;
    private EditText eTitile, eDate, eTime;
    private Button btUpdate, btBack, btRemove;

    private Schedule schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        btBack.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        eDate.setOnClickListener(this);
        eTime.setOnClickListener(this);

        Intent intent = getIntent();
        schedule = (Schedule) intent.getSerializableExtra("item");
        eTitile.setText(schedule.getTitle());
        eDate.setText(schedule.getDate());
        eTime.setText(schedule.getTime());
        int p=0;
        for(int i=0;i<sp.getCount();i++){
            if(sp.getItemAtPosition(i).toString().equalsIgnoreCase(schedule.getCategory())){
                p=i;
                break;
            }
        }
        sp.setSelection(p);
    }

    private void initView() {
        sp = findViewById(R.id.spCategory);
        eTitile = findViewById(R.id.tvTitle);
        eDate = findViewById(R.id.tvDate);
        eTime = findViewById(R.id.tvTime);

        btUpdate = findViewById(R.id.btUpdate);
        btBack = findViewById(R.id.btBack);
        btRemove = findViewById(R.id.btRemove);
        sp.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.category)));
    }

    @Override
    public void onClick(View v) {
        SQLiteHelper db = new SQLiteHelper(this);
        if(v==eDate){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String date="";
                    if(month>8){
                        date = dayOfMonth+"/"+(month+1)+"/"+year;
                    }
                    else{
                        date = dayOfMonth+"/0"+(month+1)+"/"+year;
                    }
                    eDate.setText(date);
                }
            },year,month,day);
            dialog.show();
        }
        if(v==eTime){
            final Calendar c = Calendar.getInstance();
            int gio = c.get(Calendar.HOUR);
            int phut = c.get(Calendar.MINUTE);
            TimePickerDialog dialog = new TimePickerDialog(UpdateDeleteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String time = hourOfDay+":"+minute;
                    eTime.setText(time);
                }

            },gio,phut,true);
            dialog.show();
        }
        if(v==btBack){
            finish();
        }
        if(v==btUpdate){
            String title = eTitile.getText().toString();
            String date = eDate.getText().toString();
            String time = eTime.getText().toString();
            String cate = sp.getSelectedItem().toString();
            if(!title.isEmpty()){
                int id = schedule.getId();
                Schedule s = new Schedule(id,title,cate,time,date);
                db.update(s);
                finish();
            }
        }
        if(v==btRemove){
            int id = schedule.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Thong bao Xoa");
            builder.setMessage("Ban co chac muon xoa "+schedule.getTitle()+" khong?");
            builder.setIcon(R.drawable.remove);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.delete(id);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}