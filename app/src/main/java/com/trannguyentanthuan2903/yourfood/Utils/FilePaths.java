package com.trannguyentanthuan2903.yourfood.Utils;

import android.os.Environment;

/**
 * Created by Administrator on 9/22/2017.
 */

public class FilePaths {

    //"storage/emulated/0"
    public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();

    public String PICTURES = ROOT_DIR + "/Pictures";
    public String CAMERA = ROOT_DIR + "/DCIM/camera";
    public String FIREBASE_IMAGE_STORAGE = "photos/users/";
}