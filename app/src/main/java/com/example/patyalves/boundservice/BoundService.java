package com.example.patyalves.boundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Chronometer;

/**
 * Created by logonrm on 12/06/2017.
 */

public class BoundService extends Service {
    private static String LOG_TAG="BoundService";
    private IBinder mBinder=new MyBinder();
    private Chronometer mChronometer;

    @Override
    public void onCreate(){
        super.onCreate();
        Log.v(LOG_TAG,"in onCreate");
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }
 
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG,"in onBind");
        return mBinder;
    }

    public void onRebind(Intent intent){
        Log.v(LOG_TAG,"in onRebind");
        super.onRebind(intent);
    }

    public boolean onUnbind(Intent intent){
        Log.v(LOG_TAG,"in onUnbind");
        return true;
    }

    public void onDestroy(){
        super.onDestroy();
        Log.v(LOG_TAG,"in onDestroy");
        mChronometer.stop();
    }

    public String getTimestamp(){
        long elapsedMillis=SystemClock.elapsedRealtime()-mChronometer.getBase();
        int hours=(int)(elapsedMillis/3600000);
        int minutes=(int)(elapsedMillis-hours*3600000)/60000;
        int seconds=(int)(elapsedMillis-hours*3600000-minutes*60000)/1000;
        int mills =(int)(elapsedMillis-hours*3600000-minutes*60000-seconds*1000);
        return hours+":"+minutes+":"+seconds+":"+mills;

    }

    public class MyBinder extends Binder{
        BoundService getService(){
            return BoundService.this;
        }
    }
}
