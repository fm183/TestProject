package com.example.testproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testproject.R;
import com.example.testproject.bean.User;
import com.example.testproject.viewmodel.BlankViewModel;

public class Blank2Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() == null) {
            return;
        }
        View view = getView();
        if (view == null) {
            return;
        }
        final TextView tv = view.findViewById(R.id.tv);

        BlankViewModel blankViewModel = new ViewModelProvider(getActivity()).get(BlankViewModel.class);
        blankViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                tv.setText(user.getName());
            }
        });
    }
}
