package com.example.hsb.ui.room.activity.edit_room_activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Account;
import com.example.hsb.entities.Room;
import com.example.hsb.entities.ServiceBill;
import com.example.hsb.repository.AccountRepository;
import com.example.hsb.repository.RoomRepository;
import java.util.List;
import lombok.Getter;

public class EditRoomActivityViewModel extends ViewModel {
    @Getter
    private final MutableLiveData<Room> mRoom = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> deleteStatusLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Room>> mListRoomLiveData;
    private final RoomRepository roomRepository;
    private final AccountRepository accountRepository;

    public EditRoomActivityViewModel() {
        roomRepository = RoomRepository.getInstance();
        accountRepository = AccountRepository.getInstance();
        mListRoomLiveData = roomRepository.getRooms("","");
    }
    public MutableLiveData<Room> getRoomLiveData() {
        return mRoom;
    }

    public void edtRoom(Room room){
        roomRepository.editRoom(room, new RoomRepository.EditRoomCallBack() {
            @Override
            public void onEditSuccess(Room updatedRoom) {
                mRoom.postValue(updatedRoom);
                toastMessageLiveData.postValue("Room updated successfully.");
            }

            @Override
            public void onEditFailure(String errorMessage) {
                toastMessageLiveData.postValue("Update room failed: " + errorMessage);
            }
        });
    }
    public void createRoom(Room room){
        roomRepository.createRoom(room, new RoomRepository.CreateRoomCallBack() {
            @Override
            public void onCreateSuccess(Room newRoom) {
                mRoom.postValue(newRoom);
                toastMessageLiveData.postValue("Room created successfully.");
            }

            @Override
            public void onCreateFailure(String errorMessage) {
                toastMessageLiveData.postValue("Create room failed: " + errorMessage);
            }
        });
    }
    public void deleteRoom (String roomId){
        roomRepository.deleteRoom(roomId, new RoomRepository.DeleteRoomCallBack() {
            @Override
            public void onDeleteSuccess() {
                deleteStatusLiveData.postValue(true);
            }

            @Override
            public void onDeleteFailure(String errorMessage) {
                toastMessageLiveData.postValue("Delete room failed: " + errorMessage);
            }
        });
    }
}