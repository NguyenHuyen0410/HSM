package com.example.hsb.ui.room.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.hsb.R;
import com.example.hsb.entities.Room;
import com.example.hsb.ui.category.activity.CategoryDetail;
import com.example.hsb.ui.room.activity.RoomDetail;

import java.util.List;

public class RoomAdaptor extends RecyclerView.Adapter<RoomAdaptor.RoomHolder> {

    private final List<Room> roomList;
    private final Context context;

    public RoomAdaptor(List<Room> roomList, Context context) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomAdaptor.RoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_room_item, parent, false);
        return new RoomAdaptor.RoomHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdaptor.RoomHolder holder, int position) {
        StringBuilder imgAddess = new StringBuilder();


        Room room = roomList.get(position);
        imgAddess.append("https://hotel-service-manage.pockethost.io/api/files/rooms/");
        imgAddess.append(room.getId());
        imgAddess.append("/");
        imgAddess.append(room.getRoomImage());
        Glide.with(context).load(imgAddess.toString()).into(holder.images);

        holder.name.setText(room.getRoomNumber());
        holder.type.setText(room.getRoomType());
//        holder.area.setText(room.getRoomArea());
        holder.button.setOnClickListener(v -> {
            Intent intent = new Intent(context, RoomDetail.class);
            intent.putExtra("room", room);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView images;
        TextView type;
        TextView area;
        Button button;

        public RoomHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_room_name);
            images = itemView.findViewById(R.id.img_room);
            type = itemView.findViewById(R.id.tv_room_type);
            area = itemView.findViewById(R.id.tv_room_area);
            button = itemView.findViewById(R.id.btn_room_view);
        }
    }
}