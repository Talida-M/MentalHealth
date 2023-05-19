package com.ensias.hygieia;

import static com.google.common.reflect.Reflection.getPackageName;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;


public class VideoFragment extends Fragment {
    private VideoView mVideoView;
    private Button mNextButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_video_playback_home, container, false);

        mVideoView = root.findViewById(R.id.video_view);
        mNextButton = root.findViewById(R.id.next_button);

        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.mental_health_video;
        Uri uri = Uri.parse(videoPath);

        mVideoView.setVideoURI(uri);
        mVideoView.start();

        // Add media controller
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DoctorHomeActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}


