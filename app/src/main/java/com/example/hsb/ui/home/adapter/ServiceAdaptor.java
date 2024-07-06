package com.example.hsb.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hsb.R;
import com.example.hsb.entities.Category;
import com.example.hsb.entities.Price;
import com.example.hsb.entities.Service;

import java.util.List;

public class ServiceAdaptor extends RecyclerView.Adapter<ServiceAdaptor.ServiceHolder> {

    private List<Service> childItemList;
    private List<Price> priceList;

    private Context context;

    // Constructor
    ServiceAdaptor(List<Service> childItemList, List<Price> priceList , Context context) {
        this.childItemList = childItemList;
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
        Double priceValue = 10.0;
        StringBuilder imgAddess = new StringBuilder();
        // Create an instance of the ChildItem
        // class for the given position
        Service childItem = childItemList.get(position);
        if (priceList.stream().anyMatch(price -> price.getServiceId().equals(childItem.getId()))) {
            priceValue = priceList.stream().filter(price -> price.getServiceId().equals(childItem.getId()))
                    .findFirst().get().getPrice();
        }

        imgAddess.append("https://hotel-service-manage.pockethost.io/api/files/services/");
        imgAddess.append(childItem.getId());
        imgAddess.append("/");
        imgAddess.append(childItem.getImage());
        Glide.with(context).load(imgAddess.toString()).into(serviceCategoryChildHolder.childItemImage);
        serviceCategoryChildHolder.childItemTitle.setText(childItem.getName());
        serviceCategoryChildHolder.childItemPrice.setText(priceValue.toString());


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
        return childItemList.size();
    }

    // This class is to initialize
    // the Views present
    // in the child RecyclerView
    class ServiceHolder extends RecyclerView.ViewHolder {

        TextView childItemTitle;
        TextView childItemPrice;

        ImageView childItemImage;

//        ImageView childItemImage;

        ServiceHolder(View itemView) {
            super(itemView);
            childItemTitle = itemView.findViewById(R.id.tv_name_service);
            childItemPrice = itemView.findViewById(R.id.tv_price_service);
            childItemImage = itemView.findViewById(R.id.iv_image_service);
//            childItemTitle = itemView.findViewById(R.id.tv_child_item_service_name);
        }
    }
}
