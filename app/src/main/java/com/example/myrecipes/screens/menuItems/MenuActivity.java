package com.example.myrecipes.screens.menuItems;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.myrecipes.R;
import com.example.myrecipes.localstorage.LocalStorageManager;
import com.example.myrecipes.screens.auth.AuthenticationActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
   private ImageView imageViewSpoon;
   private ImageView imageViewFork;
   private ImageView imageViewKnife;
    private LocalStorageManager localStorageManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        localStorageManager = LocalStorageManager.getInstance(this);
        setContentView(R.layout.activity_menu);
        imageViewSpoon=findViewById(R.id.spoon);
        imageViewFork=findViewById(R.id.fork);
        imageViewKnife=findViewById(R.id.knife);
        imageViewSpoon.setOnClickListener(this);
        imageViewFork.setOnClickListener(this);
        imageViewKnife.setOnClickListener(this);
    }
    private void logout() {
        LocalStorageManager.getInstance(this).deleteUser();
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.spoon) {
            Intent intentSearch = new Intent(this, MainActivity.class);
            v.getContext().startActivity(intentSearch);
        } else if (v.getId() == R.id.fork) {
            if(localStorageManager.getUser()!=null) {
                Intent intentProfile = new Intent(this, ProfileActivity.class);
                v.getContext().startActivity(intentProfile);
            }else{
                Intent intent = new Intent(this, AuthenticationActivity.class);
                startActivity(intent);
            }

        } else if (v.getId() == R.id.knife) {
           logout();
        }
    }




}
