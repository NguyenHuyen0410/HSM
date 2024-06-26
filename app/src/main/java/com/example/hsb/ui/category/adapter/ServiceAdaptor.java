package com.example.hsb.ui.category.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Service;

import java.util.List;

public class ServiceAdaptor extends RecyclerView.Adapter<ServiceAdaptor.ServiceHolder> {

    private List<Service> childItemList;

    // Constructor
    ServiceAdaptor(List<Service> childItemList) {
        this.childItemList = childItemList;
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        // Here we inflate the corresponding
        // layout of the child item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_child, parent, false);
        return new ServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ServiceHolder serviceCategoryChildHolder,
            int position) {

        // Create an instance of the ChildItem
        // class for the given position
        Service childItem = childItemList.get(position);

        // For the created instance, set title.
        // No need to set the image for
        // the ImageViews because we have
        // provided the source for the images
        // in the layout file itself
        serviceCategoryChildHolder.childItemTitle.setText(childItem.getName());
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

//        ImageView childItemImage;

        ServiceHolder(View itemView) {
            super(itemView);
            childItemTitle = itemView.findViewById(R.id.tv_child_item_service_name);
        }
    }
}
