package com.example.hsb.ui.room.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.example.hsb.R;
import com.example.hsb.entities.Room;
import com.example.hsb.ui.room.activity.edit_room_activity.EditRoomActivity;

public class RoomDetail extends AppCompatActivity {
    private TextView name;
    private ImageView image;
    private TextView type;
    private TextView description;
    private TextView device;
    private Button updateBtn;
    private Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set navigation icon (arrow) to be white
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.arrow_white);
        if (upArrow != null) {
            upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        // Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        name = findViewById(R.id.room_name);
        description = findViewById(R.id.room_desc);
        image = findViewById(R.id.room_image);
        type = findViewById(R.id.room_info);
        device = findViewById(R.id.device_account_id);
        updateBtn = findViewById(R.id.btn_update_room);

        // Get the category passed to the activity
        room = (Room) getIntent().getSerializableExtra("room");


        name.setText(room.getRoomNumber());
        description.setText(room.getDescription());
        type.setText(room.getRoomType());
        device.setText(room.getDeviceAccountId());
        StringBuilder imgAddress = new StringBuilder();
        imgAddress.append("https://hotel-service-manage.pockethost.io/api/files/room/");
        imgAddress.append(room.getId());
        imgAddress.append("/");
        imgAddress.append(room.getRoomImage());
        Glide.with(this).load(imgAddress.toString()).into(image);

        updateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(RoomDetail.this, EditRoomActivity.class);
            intent.putExtra("room", room);
            startActivity(intent);
        });
    }
}
