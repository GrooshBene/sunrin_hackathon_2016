package kr.edcan.rinsunhackathon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import java.util.List;

/**
 * Created by bene on 2016. 7. 22..
 */
public class MainActivity extends Activity {
    PackageManager mPackageManager;
    List<ResolveInfo> intentList;
    GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPackageManager = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intentList = getPackageManager().queryIntentActivities(intent, 0);

        mGridView = (GridView)findViewById(R.id.mGridView);
        mGridView.setAdapter(new MyBaseAdapter(this, intentList));

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ResolveInfo clickedResolveInfo = (ResolveInfo)adapterView.getItemAtPosition(i);
                ActivityInfo clickedActivityInfo = clickedResolveInfo.activityInfo;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(intent.CATEGORY_LAUNCHER);
                intent.setClassName(clickedActivityInfo.applicationInfo.packageName, clickedActivityInfo.name);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent);
            }
        });
    }


    public class MyBaseAdapter extends BaseAdapter{

        private Context mContext;
        private List<ResolveInfo> mAppList;

        MyBaseAdapter(Context c, List<ResolveInfo> I){
            mContext = c;
            mAppList = I;
        }

        @Override
        public int getCount() {
            return mAppList.size();
        }

        @Override
        public Object getItem(int i) {
            return mAppList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView mImageView;
            if(view == null){
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(85,85));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setPadding(8,8,8,8);
            }
            else{
                mImageView = (ImageView)view;
            }

            ResolveInfo resolveInfo = mAppList.get(i);

            mImageView.setImageDrawable(resolveInfo.loadIcon(mPackageManager));

            return mImageView;
        }
    }
}
