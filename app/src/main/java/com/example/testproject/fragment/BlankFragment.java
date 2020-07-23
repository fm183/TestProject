package com.example.testproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.testproject.R;
import com.example.testproject.bean.User;
import com.example.testproject.viewmodel.BlankViewModel;

public class BlankFragment extends Fragment {

    private BlankViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.blank_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() == null) {
            return;
        }
        mViewModel = new ViewModelProvider(getActivity()).get(BlankViewModel.class);
        mViewModel.setUser(new User());
        View view = getView();
        if (view == null) {
            return;
        }
        view.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.setUserName("Hello Blank");
            }
        });
    }

}
