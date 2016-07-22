package kr.edcan.rinsunhackathon;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import java.util.List;

/**
 * Created by bene on 2016. 7. 22..
 */
public class MyBaseAdapter extends BaseAdapter {

        private Context mContext;
        private List<ResolveInfo> mAppList;
        private PackageManager mPackageManager;


        MyBaseAdapter(Context c, List<ResolveInfo> I, PackageManager P){
            mContext = c;
            mAppList = I;
            mPackageManager = P;
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
