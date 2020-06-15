package com.example.jellobadgeview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import bouncing.jello.badge.JelloBadgeView;
import bouncing.jello.badge.JelloInterpolatorTestView;


public class MainActivity extends AppCompatActivity {

    JelloBadgeView jelloBadgeView;
    JelloInterpolatorTestView jelloInterpolatorTestView;

    Handler handler;
    int unreadNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jelloBadgeView = findViewById(R.id.jello_view);
        jelloBadgeView.highFrequency = true;
        jelloInterpolatorTestView = findViewById(R.id.jello_test_view);

        animateJello();
    }

    private void animateJello() {
        unreadNum++;
        if (unreadNum == 101) unreadNum = 1;
        jelloBadgeView.setNumber(unreadNum);
        jelloInterpolatorTestView.animateJello();

        if (handler == null) handler = new Handler();
        handler.postDelayed(this::animateJello, 1000);
    }
}
