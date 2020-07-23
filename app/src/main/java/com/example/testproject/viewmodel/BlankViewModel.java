package com.example.testproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testproject.bean.User;

public class BlankViewModel extends ViewModel {

    private final MutableLiveData<User> mutableLiveData = new MutableLiveData<>();

    public void setUser(User user){
        mutableLiveData.setValue(user);
    }

    public void setUserName(String name){
        User user = mutableLiveData.getValue();
        if (user == null) {
            return;
        }
        user.setName(name);
        setUser(user);
    }

    public LiveData<User> getLiveData(){
        return mutableLiveData;
    }

}
