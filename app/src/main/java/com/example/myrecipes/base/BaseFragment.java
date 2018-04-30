package com.example.myrecipes.base;

import android.app.Fragment;
import android.widget.Toast;
import com.example.myrecipes.models.Users.ApiError;
import com.google.gson.Gson;


public class BaseFragment extends Fragment {

    private Gson gson = new Gson();

    public void showToastMessage(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public ApiError parseApiErrorString(String error) {
        return gson.fromJson(error, ApiError.class);
    }

    public void actBasedOnApiErrorCode(ApiError apiError) {
        if (apiError.getStatusCode() == 401 && this instanceof AuthenticatedScreen) {
            AuthenticatedScreen screen = (AuthenticatedScreen) this;
            screen.notLoggedInAnymore();
        }
    }
}
