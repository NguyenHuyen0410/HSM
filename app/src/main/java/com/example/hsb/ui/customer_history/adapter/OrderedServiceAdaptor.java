package com.example.hsb.ui.customer_history.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Service;
import com.example.hsb.entities.ServiceBillDetail;
import com.example.hsb.ui.customer_history.activity.OrderedServiceDetail;

import java.util.List;

public class OrderedServiceAdaptor extends RecyclerView.Adapter<OrderedServiceAdaptor.ServiceBillDetailHolder> {

    private List<ServiceBillDetail> serviceBillDetailList;
    private List<Service> serviceList;
    private Context context;

    public OrderedServiceAdaptor(List<ServiceBillDetail> serviceBillDetailList, List<Service> serviceList , Context context) {
        this.context = context;
        this.serviceBillDetailList = serviceBillDetailList;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceBillDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_request_view_item, parent, false);
        return new ServiceBillDetailHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceBillDetailHolder holder, int position) {
        ServiceBillDetail serviceBillDetail = serviceBillDetailList.get(position);
        String date;
        String time;
        for (Service service : serviceList) {
            if (service.getId().equals(serviceBillDetail.getServiceId())) {
                holder.name.setText(service.getName());
                if (serviceBillDetail.getProcessDate() != null) {
                    date = serviceBillDetail.getProcessDate().getDayOfMonth () +"/" + serviceBillDetail.getProcessDate().getMonthValue();
                    time = serviceBillDetail.getProcessDate().getHour () +":" + serviceBillDetail.getProcessDate().getMinute();
                } else {
                    date = "Not ready";
                    time = "Not ready";
                }

                holder.processDate.setText(date);
                holder.processTime.setText(time);
                holder.status.setText(serviceBillDetail.getStatus());

                holder.layout.setOnClickListener(v -> {
                    Intent intent = new Intent(context, OrderedServiceDetail.class);
                    intent.putExtra("service", service);
                    intent.putExtra("serviceBillDetail", serviceBillDetail);
                    context.startActivity(intent);
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return serviceBillDetailList.size();
    }

    public class ServiceBillDetailHolder extends RecyclerView.ViewHolder {

        TextView name;
        //        ImageView images;
        TextView processDate;

        TextView processTime;

        TextView status;

        LinearLayout layout;

        public ServiceBillDetailHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_service_name);
            processDate = itemView.findViewById(R.id.tv_process_date);
            processTime = itemView.findViewById(R.id.tv_process_time);
            status = itemView.findViewById(R.id.tv_process_status);
            layout = itemView.findViewById(R.id.layout_item);
        }
    }
}