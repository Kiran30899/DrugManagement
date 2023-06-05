package com.project.drugmanagement.HomeScreen.DrugOfficer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.drugmanagement.R;
import com.project.drugmanagement.databinding.FragmentDrugOfficerWholesalerBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_drugOfficer_wholesaler#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_drugOfficer_wholesaler extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentDrugOfficerWholesalerBinding wholesalerBinding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_drugOfficer_wholesaler() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_drugOfficer_wholesaler.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_drugOfficer_wholesaler newInstance(String param1, String param2) {
        fragment_drugOfficer_wholesaler fragment = new fragment_drugOfficer_wholesaler();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_drug_officer_wholesaler, container, false);
        wholesalerBinding = FragmentDrugOfficerWholesalerBinding.inflate(inflater,container,false);
        return  wholesalerBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wholesalerBinding.btnpurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_drugOfficer_wholesaler_to_fragment_do_wholesaler_purchase);
            }
        });

        wholesalerBinding.btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_drugOfficer_wholesaler_to_fragment_do_wholesaler_sell);
            }
        });
    }
}