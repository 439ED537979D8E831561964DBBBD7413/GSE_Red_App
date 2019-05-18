package com.gsered.gse;

import android.content.Context;
import android.content.pm.PackageManager;

public class updatenotification {

    public static String KEY_UPDATE_ENABLE = "VERSION";
    public static String KEY_UPDATE_VERSION = "VERSION";
    public static String KEY_UPDATE_URL = "VERSION";

    public interface onUpdateCheckListener{
        void onUpdateCheckListener(String urlApp);
    }

    public static Builder with(Context context){
        return new Builder(context);
    }

    private onUpdateCheckListener onUpdateCheckListener;
    private Context context;

    public updatenotification(Context context, onUpdateCheckListener onUpdateCheckListener){
        this.onUpdateCheckListener = onUpdateCheckListener;
        this.context = context;
    }

    public void check(){
        String appversion = getAppVersion(context);
        String purpose = "checkupdate";
        donationbackground donationbackground = new donationbackground(context);
        donationbackground.execute(purpose, appversion);
    }

    private String getAppVersion(Context context){
        String result = "";
        try{
            result = context.getPackageManager().getPackageInfo(context.getPackageName(),0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-","");


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static class Builder{

        private Context context;
        private onUpdateCheckListener onUpdateCheckListener;

        public Builder(Context context){
            this.context = context;
        }

        public Builder onUpdateCheck(onUpdateCheckListener onUpdateCheckListener){
            this.onUpdateCheckListener = onUpdateCheckListener ;
            return this;
        }

        public updatenotification build(){
            return new updatenotification(context, onUpdateCheckListener);
        }

        public updatenotification check(){
            updatenotification updatenotification = build();
            updatenotification.check();

            return updatenotification;
        }
    }
}
