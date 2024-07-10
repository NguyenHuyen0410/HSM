package com.example.hsb.ui.customer_history.fragment;

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
import com.example.hsb.entities.Service;
import com.example.hsb.entities.ServiceBillDetail;
import com.example.hsb.ui.customer_history.adapter.OrderedServiceAdaptor;

import java.util.ArrayList;
import java.util.List;

public class ServiceHistoryFragment extends Fragment {

    private List<ServiceBillDetail> serviceBillDetailList = new ArrayList<>();
    private List<Service> serviceList = new ArrayList<>();
    private OrderedServiceAdaptor adapter;
    private ServiceHistoryFragmentViewModel serviceHistoryFragmentViewModel;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_history, container, false);

        recyclerView = view.findViewById(R.id.rv_service_list);

        serviceHistoryFragmentViewModel = new ServiceHistoryFragmentViewModel(null,"ryh7idmam2q3k4m");


        // Observe changes in the ServiceBillDetail list
        serviceHistoryFragmentViewModel.getListServiceBillDetailLiveData().observe(getViewLifecycleOwner(), new Observer<List<ServiceBillDetail>>() {
            @Override
            public void onChanged(@Nullable List<ServiceBillDetail> serviceBillDetails) {
                if (serviceBillDetailList != null) {
                    serviceBillDetailList.clear();
                    serviceBillDetailList.addAll(serviceBillDetails);
                    adapter.notifyDataSetChanged();
                    System.out.println("Category list updated: " + serviceBillDetails.size() + " categories");
                }
            }
        });

        serviceHistoryFragmentViewModel.getListServiceLiveData().observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
            @Override
            public void onChanged(@Nullable List<Service> services) {
                if (serviceList != null) {
                    serviceList.clear();
                    serviceList.addAll(services);
                    adapter.notifyDataSetChanged();
                    System.out.println("service list updated: " + services.size() + " services");
                }
            }
        });

        adapter = new OrderedServiceAdaptor(serviceBillDetailList, serviceList, requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}