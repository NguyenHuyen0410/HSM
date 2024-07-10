package com.example.hsb.ui.room.activity.edit_room_activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hsb.R;
import com.example.hsb.entities.Room;
import com.example.hsb.utils.ValidateUtil;

public class EditRoomActivity extends AppCompatActivity {

    private EditText name;
    private EditText image;
    private EditText type;
    private EditText description;
    private EditText device;

    private EditRoomActivityViewModel editRoomActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_add);
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
        name = findViewById(R.id.et_name_room);
        image = findViewById(R.id.et_image_room);
        type = findViewById(R.id.et_type_room);
        description = findViewById(R.id.et_description_room);
        device = findViewById(R.id.et_device_room);
        Button deleteBtn = findViewById(R.id.btn_delete_room);
        Button saveBtn = findViewById(R.id.btn_save_room);

        // Initialize ViewModel
        editRoomActivityViewModel = new ViewModelProvider(this).get(EditRoomActivityViewModel.class);

        // Get the room passed to the activity
        Room room = (Room) getIntent().getSerializableExtra("room");
        if (room != null) {
            name.setText(room.getRoomNumber());
            image.setText(room.getRoomImage());
            type.setText(room.getRoomType());
            device.setText(room.getDeviceAccountId());
            description.setText(room.getDescription());
        } else {
            room = new Room();
        }

        Room finalRoom = room;
        saveBtn.setOnClickListener(v -> setUpdateData(finalRoom));

        deleteBtn.setOnClickListener(v -> new AlertDialog.Builder(EditRoomActivity.this)
                .setTitle("Delete room")
                .setMessage("Are you sure you want to delete this room?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // Call ViewModel to delete room
                    editRoomActivityViewModel.deleteRoom(finalRoom.getId());
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());

        // Observe the ViewModel for category updates
        editRoomActivityViewModel.getDeleteStatusLiveData().observe(this, isDeleted -> {
            if (isDeleted != null && isDeleted) {
                Toast.makeText(EditRoomActivity.this, "Room deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Observe the ViewModel for room updates
        editRoomActivityViewModel.getRoomLiveData().observe(this, new Observer<Room>() {
            @Override
            public void onChanged(Room updatedRoom) {
                // Handle the updated account, e.g., show a message or update UI
                Toast.makeText(EditRoomActivity.this, "Room updated successfully", Toast.LENGTH_SHORT).show();
                // Optionally finish the activity or update the UI further
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Navigate back to previous activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpdateData(Room room) {
        boolean isValid =  true;
        // Validate name
//        if (ValidateUtil.isNameValid(name)) {
//            room.setRoomNumber(name.getText().toString());
//        } else {
//            name.setError("Invalid name");
//            isValid = false;
//        }
        if (isValid) {
            // Call ViewModel to update or create room
            if (room.getId() != null) {
                editRoomActivityViewModel.edtRoom(room);
            } else {
                editRoomActivityViewModel.createRoom(room);
            }
        } else {
            Toast.makeText(EditRoomActivity.this, "Please fix the errors above", Toast.LENGTH_SHORT).show();
        }
    }
    public void setData(@Nullable Room room) {
        // Retrieve data from the fields
        String updatedName = name.getText().toString();
        String updatedImage = image.getText().toString();
        String updatedDescription = description.getText().toString();
        String updatedType = type.getText().toString();
        String updateDeviceAccountId = device.getText().toString();

        // Update the category
       room.setRoomNumber(updatedName);
       room.setRoomImage(updatedImage);
       room.setRoomType(updatedType);
       room.setDescription(updatedDescription);
       room.setDeviceAccountId(updateDeviceAccountId);
    }
}