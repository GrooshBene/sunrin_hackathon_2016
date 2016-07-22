package kr.edcan.rinsunhackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by bene on 2016. 7. 22..
 */
public class MainPager extends Fragment implements View.OnClickListener {

    int REQ_CODE_SELECT_IMAGE = 100;
    ImageView mBackground;
    SharedPreferences s;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        long lostDate = getDay(timeSet(), getPref("oDate").toString());
        Log.e("Lostdate", String.valueOf(lostDate));
        view = inflater.inflate(R.layout.activity_main1, container, false);
        ImageView mClockBtn = (ImageView) view.findViewById(R.id.mClockBtn);
        ImageView mSettingBtn = (ImageView) view.findViewById(R.id.mSettingBtn);
        mClockBtn.setOnClickListener(this);
        mSettingBtn.setOnClickListener(this);
        if(lostDate > 180) {
            view = inflater.inflate(R.layout.activity_main1, container, false);
            mBackground = (ImageView) view.findViewById(R.id.mBackground);
            CircleProgressBar cp = (CircleProgressBar) view.findViewById(R.id.cir_progress);
            float dPer = lostDate / 360;
            TextView dday = (TextView) view.findViewById(R.id.dday);
            cp.setProgressWithAnimation(dPer * 100);
            if (lostDate == 0) {
                dday.setText("- DAY");
            } else {
                dday.setText("- " + lostDate);

            }
            mClockBtn.setOnClickListener(this);
            mSettingBtn.setOnClickListener(this);
        }
        else if(lostDate<181&&lostDate>120) {
            view = inflater.inflate(R.layout.activity_main1, container, false);
            mBackground = (ImageView) view.findViewById(R.id.mBackground);
            CircleProgressBar cp = (CircleProgressBar) view.findViewById(R.id.cir_progress);
            float dPer = lostDate / 360;
            TextView dday = (TextView) view.findViewById(R.id.dday);
            cp.setProgressWithAnimation(dPer * 100);
            if (lostDate == 0) {
                dday.setText("- DAY");
            } else {
                dday.setText("- " + lostDate);

            }
            mClockBtn.setOnClickListener(this);
            mSettingBtn.setOnClickListener(this);
        }
        else if(lostDate<121&&lostDate>60){
            view = inflater.inflate(R.layout.activity_main1, container, false);
            mBackground = (ImageView) view.findViewById(R.id.mBackground);
            CircleProgressBar cp = (CircleProgressBar) view.findViewById(R.id.cir_progress);
            float dPer = lostDate / 360;
            TextView dday = (TextView) view.findViewById(R.id.dday);
            cp.setProgressWithAnimation(dPer * 100);
            if (lostDate == 0) {
                dday.setText("- DAY");
            } else {
                dday.setText("- " + lostDate);

            }
            mClockBtn.setOnClickListener(this);
            mSettingBtn.setOnClickListener(this);
        } else if (lostDate < 61 && lostDate > -1) {
            view = inflater.inflate(R.layout.activity_main1, container, false);
            mBackground = (ImageView) view.findViewById(R.id.mBackground);
            CircleProgressBar cp = (CircleProgressBar) view.findViewById(R.id.cir_progress);
            float dPer = lostDate / 360;
            TextView dday = (TextView) view.findViewById(R.id.dday);
            cp.setProgressWithAnimation(dPer * 100);
            if (lostDate == 0) {
                dday.setText("- DAY");
            } else {
                dday.setText("- " + lostDate);

            }
            mClockBtn.setOnClickListener(this);
            mSettingBtn.setOnClickListener(this);
        }
        return view;
    }

//    @Override
//    public void onResume() {
//        long lostDate = getDay(timeSet(), getPref("oDate").toString());
//        Log.e("asdf", lostDate+" "+getPref("oDate").toString());
//        super.onResume();
//        View view = getView();
//        CircleProgressBar cp = (CircleProgressBar) view.findViewById(R.id.cir_progress);
//        float dPer = lostDate / 360;
//        TextView dday = (TextView) view.findViewById(R.id.dday);
//        cp.setProgressWithAnimation(dPer * 100);
//        if (lostDate == 0) {
//            dday.setText("- DAY");
//        } else {
//            dday.setText("- " + lostDate);
//
//        }
//    }

    private String timeSet() {
        Calendar cal = new GregorianCalendar(Locale.KOREA);
        cal.setTime(new Date());
        SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
        String strNow = fm.format(cal.getTime());
        return strNow;
    }

    private long getDay(String now, String end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        long lost = 0;
        try {
            Date st = format.parse(now);
            Date en = format.parse(end);
            long diff = en.getTime() - st.getTime();
            lost = diff / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            lost = 0;
            e.printStackTrace();
        }
        return lost;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedSet();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), getPref("image"));
            mBackground.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mClockBtn:
                Intent intent = new Intent(getActivity(), DateActivity.class);
                startActivity(intent);
                break;
            case R.id.mSettingBtn:
                Intent backgroundIntent = new Intent(Intent.ACTION_PICK);
                backgroundIntent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                backgroundIntent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(backgroundIntent, REQ_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                SavePref(data.getData().toString());
                mBackground.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void SharedSet() {
        s = getActivity().getSharedPreferences("pref", 0);
        editor = s.edit();
    }

    protected void SavePref(String o) {
        editor.putString("image", o);
        editor.commit();
    }

    protected Uri getPref(String t) {
        String v = s.getString(t, "");
        Uri u = Uri.parse(v);
        return u;
    }
}
