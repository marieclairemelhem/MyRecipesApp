package com.example.myrecipes.screens;

import android.app.Fragment;
import android.widget.Toast;

public class BaseFragment extends Fragment {

    public void showToastMessage(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
