package com.swufe.owner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.os.Bundle;
public class Start extends AppCompatActivity {
    private ImageView welcomeImg = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
                welcomeImg = (ImageView) findViewById(R.id.wrap);
        AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);
        anima.setDuration(3000);
        welcomeImg.startAnimation(anima);
        anima.setAnimationListener(new AnimationImpl());
    }
    private class AnimationImpl implements AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            welcomeImg.setBackgroundResource(R.mipmap.ss1);
        }
        @Override
        public void onAnimationEnd(Animation animation) {
            skip();
        }
        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }
    private void skip() {
        startActivity(new Intent(this, MainActivity.class));//动画开屏后返回APP主界面
        finish();
    }
}

