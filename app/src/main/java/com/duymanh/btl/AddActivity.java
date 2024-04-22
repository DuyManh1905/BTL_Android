package com.duymanh.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner sp;
    private EditText eTitile, eDate, eTime;
    private Button btUpdate, btCancel;

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //
        final Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);

        initView();
        btCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        eDate.setOnClickListener(this);
        eTime.setOnClickListener(this);
    }

    private void initView() {
        sp = findViewById(R.id.spCategory);
        eTitile = findViewById(R.id.tvTitle);
        eDate = findViewById(R.id.tvDate);
        eTime = findViewById(R.id.tvTime);

        btUpdate = findViewById(R.id.btUpdate);
        btCancel = findViewById(R.id.btCancel);
        sp.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.category)));

    }

    @Override
    public void onClick(View v) {
        if(v==eDate){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            TimePickerDialog dialog = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String time = hourOfDay+":"+minute;
                    eTime.setText(time);
                }

            },gio,phut,true);
            dialog.show();
        }
        if(v==btCancel){
            finish();
        }
        if(v==btUpdate){
            String title = eTitile.getText().toString();
            String date = eDate.getText().toString();
            String time = eTime.getText().toString();
            String cate = sp.getSelectedItem().toString();
            if(!title.isEmpty()){
                Schedule schedule = new Schedule(title,cate,time,date);
                SQLiteHelper db = new SQLiteHelper(this);
                db.addSchedule(schedule);

                System.out.println("DAY LA TIME:"+time);
                String [] time_spilt=time.split(":");
                int gio=Integer.parseInt(time_spilt[0]);
                int phut=Integer.parseInt(time_spilt[1]);


                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY,gio);
                calendar.set(Calendar.MINUTE,phut);
                calendar.set(Calendar.SECOND,0);

                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

                Intent intent = new Intent(AddActivity.this,MyReceiver.class);
                intent.setAction("myAction");
                intent.putExtra("Title","Ban co task moi!!");
                intent.putExtra("Description", eTitile.getText().toString());
                intent.putExtra("time",gio+":"+phut);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this,
                        0, intent, PendingIntent.FLAG_IMMUTABLE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                finish();
            }
        }
    }
}