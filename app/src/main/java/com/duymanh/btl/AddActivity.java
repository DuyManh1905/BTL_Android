package com.duymanh.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

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

    }
}