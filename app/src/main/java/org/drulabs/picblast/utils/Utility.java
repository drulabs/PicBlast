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
import android.widget.Toast;

/**
 * Created by kaushald on 17/11/17.
 */

public class Utility {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            return true;
        } else {
            NetworkInfo mMobileData = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return mMobileData.isConnected();
        }

        //return isWiFiConnected(context);
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
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(fileUri, projection, null, null,
                    null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            String picturePath = cursor.getString(columnIndex);
            if (picturePath == null) {
                return fileUri.getPath();
            } else {
                return picturePath;
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

}
