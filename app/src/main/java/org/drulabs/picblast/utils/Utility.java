package org.drulabs.picblast.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import org.drulabs.picblast.BuildConfig;

import java.io.File;

/**
 * Created by kaushald on 17/11/17.
 */

public class Utility {

    public static boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(
//                Context.CONNECTIVITY_SERVICE);
//        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//        if (mWifi.isConnected()) {
//            return true;
//        } else {
//            NetworkInfo mMobileData = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            return mMobileData.isConnected();
//        }

        return isWiFiConnected(context);
    }

    public static boolean isWiFiConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return mWifi.isConnected();
    }

    public static void requestPermission(String strPermission, int perCode, Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, strPermission)) {
            Toast.makeText(activity, strPermission + " is required for app to " +
                    "function", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(activity, new String[]{strPermission}, perCode);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{strPermission}, perCode);
        }
    }

    public static boolean checkPermission(String strPermission, Context _c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(_c, strPermission);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static String getFilePathFromURI(Context context, Uri fileUri) {

        Uri newUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".provider",
                new File(fileUri.toString()));
        return newUri.toString();

//        try {
//            Cursor cursor = context.getContentResolver().query(fileUri, new String[]{MediaStore
//                    .Images.Media.DATA}, null, null, null);
//            if (cursor.moveToFirst()) {
//                int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
//                String filePath = cursor.getString(columnIndex);
//                cursor.close();
//                return filePath;
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }

}
