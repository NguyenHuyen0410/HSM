package com.example.hsb.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hsb.R;
import com.example.hsb.entities.Category;
import com.example.hsb.entities.Price;
import com.example.hsb.entities.Service;
import com.example.hsb.ui.category.activity.CategoryDetail;
import com.example.hsb.ui.home.activity.edit_order_service_activity.OrderServiceActivity;
import com.example.hsb.ui.home.activity.edit_order_service_activity.OrderServiceActivityViewModel;

import java.util.List;

public class ServiceAdaptor extends RecyclerView.Adapter<ServiceAdaptor.ServiceHolder> {

    private List<Service> serviceList;
    private List<Price> priceList;
    private Context context;

    // Constructor
    ServiceAdaptor(List<Service> serviceList, List<Price> priceList , Context context) {
        this.serviceList = serviceList;
        this.priceList = priceList;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        // Here we inflate the corresponding
        // layout of the child item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_view_item, parent, false);
        return new ServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ServiceHolder serviceCategoryChildHolder,
            int position) {

        StringBuilder imgAddess = new StringBuilder();
        Price price;
        // Create an instance of the ChildItem
        // class for the given position
        Service service = serviceList.get(position);
        if (priceList.stream().anyMatch(priceRecord -> priceRecord.getServiceId().equals(service.getId()))) {
            price = priceList.stream().filter(priceRecord -> priceRecord.getServiceId().equals(service.getId()))
                    .findFirst().get();
            serviceCategoryChildHolder.servicePrice.setText(price.getPrice().toString());


            //putting this onclick listenter outside this if methods create errors , needs fixing later
            serviceCategoryChildHolder.btnOrder.setOnClickListener(v -> {
                Intent intent = new Intent(context, OrderServiceActivity.class);
                intent.putExtra("service", service);
                intent.putExtra("price", price);
                context.startActivity(intent);
            });
        }

        imgAddess.append("https://hotel-service-manage.pockethost.io/api/files/services/");
        imgAddess.append(service.getId());
        imgAddess.append("/");
        imgAddess.append(service.getImage());
        Glide.with(context).load(imgAddess.toString()).into(serviceCategoryChildHolder.serviceImage);
        serviceCategoryChildHolder.serviceTitle.setText(service.getName());



    }

    @Override
    public int getItemCount() {
//
//        // This method returns the number
//        // of items we have added
//        // in the ChildItemList
//        // i.e. the number of instances
//        // of the ChildItemList
//        // that have been created
        return serviceList.size();
    }

    // This class is to initialize
    // the Views present
    // in the child RecyclerView
    class ServiceHolder extends RecyclerView.ViewHolder {

        TextView serviceTitle;
        TextView servicePrice;

        ImageView serviceImage;

        ImageButton btnOrder;

//        ImageView serviceImage;

        ServiceHolder(View itemView) {
            super(itemView);
            serviceTitle = itemView.findViewById(R.id.tv_name_service);
            servicePrice = itemView.findViewById(R.id.tv_price_service);
            serviceImage = itemView.findViewById(R.id.iv_image_service);
            btnOrder = itemView.findViewById(R.id.btn_order_service);
//            serviceTitle = itemView.findViewById(R.id.tv_child_item_service_name);
        }
    }
}
