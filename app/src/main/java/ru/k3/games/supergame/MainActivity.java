package ru.k3.games.supergame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TestFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment = new TestFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }
}
