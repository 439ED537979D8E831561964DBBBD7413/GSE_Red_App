package com.gsered.gse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

public class CheckInternetConnection extends BroadcastReceiver {

    Context ctx=null;
    @Override
    public void onReceive(final Context context, Intent intent) {

        if(ctx != null)
            ctx = context;

        Bundle extras = intent.getExtras();
        NetworkInfo info = (NetworkInfo) extras.getParcelable("networkInfo");
        NetworkInfo.State state = info.getState();
         Log.e("TEST Internet", info.toString() + " " + state.toString());

        if (state == NetworkInfo.State.CONNECTED) {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            Intent i = new Intent(context, InternetStatusActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

    }
}
