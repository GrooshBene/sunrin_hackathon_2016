package kr.edcan.rinsunhackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Date;

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
        View view = inflater.inflate(R.layout.activity_main1, container, false);
        mBackground = (ImageView)view.findViewById(R.id.mBackground);
        ImageView mClockBtn = (ImageView)view.findViewById(R.id.mClockBtn);
        ImageView mSettingBtn = (ImageView)view.findViewById(R.id.mSettingBtn);
        mClockBtn.setOnClickListener(this);
        mSettingBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
        s = getActivity().getSharedPreferences("data", 0);
        editor = s.edit();
    }

    protected void SavePref(String o){
        editor.putString("image", o);
        editor.commit();
    }

    protected Uri getPref(){
        String v = s.getString("image", "");
        Uri u = Uri.parse(v);
        return u;
    }
}
