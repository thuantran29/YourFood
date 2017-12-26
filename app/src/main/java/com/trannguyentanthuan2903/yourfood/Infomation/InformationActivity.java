package com.trannguyentanthuan2903.yourfood.Infomation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.trannguyentanthuan2903.yourfood.R;
import com.trannguyentanthuan2903.yourfood.Utils.SquareImageView;
import com.trannguyentanthuan2903.yourfood.Utils.UniversalImageLoader;
import com.trannguyentanthuan2903.yourfood.models.Photo;

public class InformationActivity extends AppCompatActivity {
    private static final String TAG = "InformationActivity";

    SquareImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        img = (SquareImageView) findViewById(R.id.info_image);
        UniversalImageLoader.setImage(getPhotoFromBundle().getImage_path(),img,null,"");

    }

    private Photo getPhotoFromBundle(){
        Log.d(TAG, "getPhotoFromBundle: ");
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(getString(R.string.information));
        if(bundle != null) {
            return bundle.getParcelable(getString(R.string.photo));
        }else{
            return null;
        }
    }

}
