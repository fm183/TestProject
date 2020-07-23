package com.example.testproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.entry.DefaultApplicationLike;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class MainApplicationLike extends DefaultApplicationLike {
    private WeakReference<Activity> activityWeakReference;

    public MainApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate(){
        init(getApplication());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }


    private void init(Application application) {
        String channel = WalleChannelReader.getChannel(getApplication());
        Log.d(getClass().getSimpleName(),"init channel="+channel);
        Bugly.setAppChannel(application, channel);

        long startTime = System.currentTimeMillis();
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(application);
        userStrategy.setUploadProcess(isMainProcess(application));
        Bugly.init(application,"9d27732f22",true);
        Log.e(getClass().getSimpleName(),"onCreate CrashReport userTime="+(System.currentTimeMillis() - startTime)+"ms");
        CrashReport.setIsDevelopmentDevice(application, BuildConfig.DEBUG);

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                activityWeakReference = new WeakReference<>(activity);
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    public String getHeaders(){
        return "";
    }

    public Activity getCurrentActivity(){
        return activityWeakReference == null ? null : activityWeakReference.get();
    }

    private static boolean isMainProcess(Context context) {
        if (null == context) {
            return false;
        }

        String strProcessName = getProcessName(Process.myPid());
        String strPackageName = context.getPackageName();
        if (TextUtils.isEmpty(strProcessName) || TextUtils.isEmpty(strPackageName)) {
            return false;
        }
        return TextUtils.equals(strProcessName, strPackageName);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Beta.unInit();
    }
}
