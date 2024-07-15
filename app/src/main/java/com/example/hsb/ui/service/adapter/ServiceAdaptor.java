package com.example.hsb.ui.service.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hsb.R;
import com.example.hsb.entities.Category;
import com.example.hsb.entities.Service;
import com.example.hsb.entities.Price;
import com.example.hsb.ui.account.activity.edit_account_activity.EditAccountActivity;
import com.example.hsb.ui.service.activity.edit_service_activity.EditServiceActivity;

import java.util.List;

public class ServiceAdaptor extends RecyclerView.Adapter<ServiceAdaptor.ServiceHolder> {

    private final List<Service> ServiceList;
    private final List<Category> categoryList;
    private final Context context;


    public ServiceAdaptor(List<Service> ServiceList, List<Category> categoryList,Context context) {
        this.context = context;
        this.ServiceList = ServiceList;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_service_item, parent, false);
        return new ServiceHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
        Service service = ServiceList.get(position);
        holder.name.setText(service.getName());
        holder.description.setText(service.getDescription());
        StringBuilder imgAddess = new StringBuilder();


        imgAddess.append("https://hotel-service-manage.pockethost.io/api/files/services/");
        imgAddess.append(service.getId());
        imgAddess.append("/");
        imgAddess.append(service.getImage());
        Glide.with(context).load(imgAddess.toString()).into(holder.image);

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditServiceActivity.class);
            intent.putExtra()
            intent.putExtra("service", service);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ServiceList.size();
    }

    public static class ServiceHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView description;
        ImageView image;

        Button editButton;

        public ServiceHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.service_name);
            description = itemView.findViewById(R.id.service_info);
            image = itemView.findViewById(R.id.service_img);
            editButton = itemView.findViewById(R.id.btn_edit_service);

        }
    }
}