//package com.example.hsb.ui.adapter;
//
//import android.annotation.SuppressLint;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.hsb.R;
//import com.example.hsb.entities.Service;
//
//import java.util.List;
//
//public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {
//    List<Service> services;
//    @SuppressLint("NotifyDataSetChanged")
//    public ServiceAdapter(List<Service> services) {
//        this.services = services;
//    }
//    @NonNull
//    @Override
//    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_card, parent, false);
//        return new ServiceHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
//        holder.serviceImg.setImageResource(services.get(position).getImage());
//        holder.serviceName.setText(services.get(position).getName());
//        holder.serviceDescription.setText(services.get(position).getDescription());
//    }
//
//    @Override
//    public int getItemCount() {
//        return services.size();
//    }
//
//    class ServiceHolder extends RecyclerView.ViewHolder { //dai dien cho 1 row_chapter
//        ImageView serviceImg;
//        TextView serviceName;
//        TextView serviceDescription;
//
//        public ServiceHolder(@NonNull View itemView) {
//            super(itemView);
//            serviceImg = itemView.findViewById(R.id.service_img);
//            serviceName = itemView.findViewById(R.id.service_name);
//            serviceDescription = itemView.findViewById(R.id.service_des);
//            itemView.setOnClickListener(new View.OnClickListener() { //bắt cả row, bắt chữ thì truyền textView
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    Log.d("chapter_name", ": " + services.get(pos).getName());
//                }
//            });
//        }
//    }
//}
