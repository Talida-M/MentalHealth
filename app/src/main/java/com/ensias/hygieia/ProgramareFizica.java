package com.ensias.hygieia;

import androidx.fragment.app.Fragment;

import android.view.View;

import com.ensias.hygieia.adapter.ProgramareFizicaAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProgramareFizica extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgramareFizicaAdapter adapter;
    View result;


    public ProgramareFizica() {
        // Required empty public constructor
    }


 
}
