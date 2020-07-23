package com.example.testproject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.testproject.adapter.UserListAdapter;
import com.example.testproject.bean.User;
import com.example.testproject.fragment.Blank2Fragment;
import com.example.testproject.fragment.BlankFragment;
import com.example.testproject.serviceapi.DataApi;
import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView( R.layout.activity_main);
        user = new User();
        user.setName("tt1");
        user.setPassword("tt1");

        user.setName("tt2");

        List<User> userList = new ArrayList<>();
        for (int i = 0;i < 30;i ++){
            User user = new User();
            user.setName("tt"+i);
            user.setPassword("ttPassword"+i);
            userList.add(user);
        }
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        UserListAdapter userListAdapter = new UserListAdapter(userList);
        recyclerView.setAdapter(userListAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        MyPageAdapter myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(myPageAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                CrashReport.testJavaCrash();
                String channel = WalleChannelReader.getChannel(MainActivity.this);
                Toast.makeText(MainActivity.this,"onPageSelectedssss77777 position="+position+",channelName="+channel,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.tv_1).setOnClickListener(view -> viewPager.setCurrentItem(0));
        findViewById(R.id.tv_2).setOnClickListener(view -> viewPager.setCurrentItem(1));


       /* HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(*//*"http://fanyi.youdao.com/"*//*"http://203.187.160.133:9011/")
                .client(okHttpClient).*//*addConverterFactory(GsonConverterFactory.create()).*//*build();
        Call<ResponseBody> call = retrofit.create(DataApi.class).getData();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                ResponseBody translation = response.body();
                Log.d(MainActivity.class.getSimpleName(),"onResponse  response="+(translation == null ? null : translation.toString()));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });*/
    }

    public static String getUmengChannel(Context context) {
        if (context == null) {
            return "";
        }
        try {
            Bundle bundle = getAppcalitionMetaDataInfo(context).applicationInfo.metaData;
            Object channel = bundle.get("UMENG_CHANNEL");
            return channel == null ? "" : channel.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static PackageInfo getAppcalitionMetaDataInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(MainActivity.class.getSimpleName(),"onDestroy");

    }


    private static class MyPageAdapter extends FragmentStatePagerAdapter {


        private MyPageAdapter(@NonNull FragmentManager fm) {
            super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new BlankFragment();
            }
            return new Blank2Fragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    public class UserPresenter {

        public void onUserNameClick(User user){
            Toast.makeText(MainActivity.this,user.getName(),Toast.LENGTH_SHORT).show();
        }

        public void afterNameChange(Editable s){
            user.setName(s.toString());
        }

        public void afterPasswordChange(Editable s){
            user.setPassword(s.toString());
        }

    }

}
