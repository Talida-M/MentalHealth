package com.ensias.hygieia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        loadVideoFragment();
    }

    private void loadVideoFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        VideoFragment videoFragment = new VideoFragment();
        fragmentTransaction.replace(R.id.fragment_container, videoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}