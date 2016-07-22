package kr.edcan.rinsunhackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by bene on 2016. 7. 22..
 */
public class ListPager extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    PackageManager mPackageManager;
    List<ResolveInfo> intentList;
    GridView mGridView;
    Button mSettingBtn, mBackgroundSetting;
    String uri;
    ImageView mBackground;
    SharedPreferences s;
    SharedPreferences.Editor editor;
    final int REQ_CODE_SELECT_IMAGE = 100;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPackageManager = getActivity().getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intentList = getActivity().getPackageManager().queryIntentActivities(intent, 0);

        SharedSet();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), getPref());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list, container, false);
        mSettingBtn = (Button)view.findViewById(R.id.mSettingBtn);
        mBackground = (ImageView) view.findViewById(R.id.mBackground);
        mBackgroundSetting = (Button)view.findViewById(R.id.mBackgroundSetting);
        mGridView = (GridView)view.findViewById(R.id.mGridView);

        mGridView.setAdapter(new MyBaseAdapter(getContext(), intentList, mPackageManager));

        mGridView.setOnItemClickListener(this);
        mSettingBtn.setOnClickListener(this);
        mBackgroundSetting.setOnClickListener(this);
        return view;
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


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ResolveInfo clickedResolveInfo = (ResolveInfo)adapterView.getItemAtPosition(i);
        Log.e("ResolveInfo", String.valueOf(clickedResolveInfo));
        ActivityInfo clickedActivityInfo = clickedResolveInfo.activityInfo;
        Log.e("ActivityInfo", clickedActivityInfo.applicationInfo.packageName);

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(intent.CATEGORY_LAUNCHER);
        intent.setClassName(clickedActivityInfo.applicationInfo.packageName, clickedActivityInfo.name);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.mSettingBtn:
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.mBackgroundSetting:
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
}
