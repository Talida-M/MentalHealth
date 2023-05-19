package com.ensias.hygieia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ensias.hygieia.adapter.ConsultationAdapter;
import com.ensias.hygieia.model.Fisa_Medicala;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ConsultationFragmentPage extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference FicheRef;
    private ConsultationAdapter adapter;
    View result;

    public ConsultationFragmentPage() {
        // Required empty public constructor
    }

    public static ConsultationFragmentPage newInstance() {
        ConsultationFragmentPage fragment = new ConsultationFragmentPage();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        result = inflater.inflate(R.layout.fragment_consultation_page, container, false);
        setUpRecyclerView();
        return result;
    }

    private void setUpRecyclerView() {

        String email_id = getActivity().getIntent().getExtras().getString("patient_email");
        FicheRef = db.collection("Patient").document(email_id).collection("MyMedicalFolder");
        Query query = FicheRef.whereEqualTo("type", "Consultation");

        FirestoreRecyclerOptions<Fisa_Medicala> options = new FirestoreRecyclerOptions.Builder<Fisa_Medicala>()
                .setQuery(query, Fisa_Medicala.class)
                .build();


        RecyclerView recyclerView = result.findViewById(R.id.conslutationRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}