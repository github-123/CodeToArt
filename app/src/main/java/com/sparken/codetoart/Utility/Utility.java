package com.sparken.codetoart.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Utility {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    public static void noConnectionAlert(Context context) {
        AlertDialog.Builder connection_builder = new AlertDialog.Builder(context);
        connection_builder.setMessage("Please check internet connection");
        connection_builder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog exit_alertDialog = connection_builder.create();
        exit_alertDialog.setCancelable(true);
        exit_alertDialog.setCanceledOnTouchOutside(true);
        exit_alertDialog.show();
    }



    public static void closeApp(final Activity activity, final String title, final String message) {

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            }).create().show();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static Fragment showFragment(Activity activity, int layoutId, Class<? extends Fragment> fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = fragmentClass.newInstance();
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(layoutId, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return fragment;
    }

}
