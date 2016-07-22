package kr.edcan.rinsunhackathon;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Date;

/**
 * Created by bene on 2016. 7. 22..
 */
public class DateActivity extends Activity implements View.OnClickListener, OnDateSelectedListener {
    LinearLayout mDatePickerLayout;
    TextView mMonth, mDate, mYear;
    ImageView mSubmitButton;
    int month, day, year;
    MaterialCalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mSubmitButton = (ImageView) findViewById(R.id.mSubmitButton);
        mMonth = (TextView) findViewById(R.id.mTextViewMonth);
        mYear = (TextView) findViewById(R.id.mTextViewYear);
        mDate = (TextView) findViewById(R.id.mTextViewDay);
        mCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangedListener(this);

//        mDatePickerLayout = (LinearLayout)findViewById(R.id.mDatePickerLayout);
//        mDatePickerLayout.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mSubmitButton:
                String query = String.format("%04d%02d%02d", year, month, day);
                Log.e("query", query);
                savePreferences(query);
                finish();
                break;
            default:
                break;
        }
    }

//        @Override
//        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//            year = i;
//            month = i1+1;
//            day = i2;
//            mMonth.setText(i1+1+"");
//            mYear.setText(i+"");
//            mDate.setText(i2+"");
//        }

    private void savePreferences(String s) {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("oDate", s);
        edit.commit();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        year = date.getYear();
        month = date.getMonth();
        day = date.getDay();
        mMonth.setText(month+1 + "");
        mYear.setText(year + "");
        mDate.setText(day + "");
    }
}
