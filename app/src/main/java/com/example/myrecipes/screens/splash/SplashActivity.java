package com.example.myrecipes.screens.splash;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import com.example.myrecipes.R;
import com.example.myrecipes.screens.menuItems.MenuActivity;

public class SplashActivity extends AppCompatActivity {

    private ValueAnimator valueAnimator;
    private ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        logoImageView = findViewById(R.id.logo);

        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                logoImageView.setAlpha(value);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                gotoNextScreen();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        valueAnimator.removeAllUpdateListeners();
        valueAnimator.removeAllListeners();
    }

    private void gotoNextScreen() {
        Intent intentMenu = new Intent(this, MenuActivity.class);
        startActivity(intentMenu);

    }
}