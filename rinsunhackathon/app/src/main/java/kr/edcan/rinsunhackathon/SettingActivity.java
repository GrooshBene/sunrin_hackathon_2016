package kr.edcan.rinsunhackathon;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by bene on 2016. 7. 22..
 */
public class SettingActivity extends Activity {

    final int REQ_CODE_SELECT_IMAGE = 100;
    Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mButton = (Button)findViewById(R.id.mDateBtn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, DateActivity.class);
                startActivity(intent);
            }
        });
    }

    private void savePreferences(String s){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("uri", s);
        edit.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_HOME:
                finish();
                break;
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
