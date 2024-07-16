package com.example.hsb.ui.service.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Price;
import com.example.hsb.entities.Service;
import com.example.hsb.ui.service.adapter.ServiceAdaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceFragment extends Fragment {
    private final List<Service> serviceList = new ArrayList<>();
    private final List<Price> priceList= new ArrayList<>();
    private ServiceAdaptor adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, container, false);

        ServiceFragmentViewModel serviceFragmentViewModel = new ServiceFragmentViewModel();
        // Observe changes in the account list
        serviceFragmentViewModel.getListServiceLiveData().observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> services) {
                if (services != null) {
                    serviceList.clear();
                    serviceList.addAll(services.stream().filter(service -> !service.isDeleted()).collect(Collectors.toList()));
                    adapter.notifyDataSetChanged();
                }
            }
        });
        serviceFragmentViewModel.getListPriceLiveData().observe(getViewLifecycleOwner(), new Observer<List<Price>>() {
            @Override
            public void onChanged(List<Price> prices) {
                if (prices != null) {
                    priceList.clear();
                    priceList.addAll(prices);
                    adapter.notifyDataSetChanged();
                }
            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.service_list);
        adapter = new ServiceAdaptor(serviceList,priceList ,requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}

