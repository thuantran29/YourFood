package com.trannguyentanthuan2903.yourfood.Likes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.trannguyentanthuan2903.yourfood.R;
import com.trannguyentanthuan2903.yourfood.Utils.BottomNavigationViewHelper;

/**
 * Created by Administrator on 9/18/2017.
 */

public class LikeActivity extends AppCompatActivity {
    private static final String TAG = "LikeActivity";
    private static final int ACTIVITY_NUM=3;
    private Context mContext = LikeActivity.this;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");
        setupBottomNavigationView();
    }
    /*
   * BottomNavigation
   * */
    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting bottom");
        BottomNavigationViewEx bottomNavi = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavi);
        BottomNavigationViewHelper.enableNavigation(mContext,this ,bottomNavi);
        Menu menu = bottomNavi.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
