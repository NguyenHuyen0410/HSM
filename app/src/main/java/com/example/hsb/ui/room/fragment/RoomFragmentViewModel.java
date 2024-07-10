package com.example.hsb.ui.room.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Category;
import com.example.hsb.entities.Room;
import com.example.hsb.repository.CategoryRepository;
import com.example.hsb.repository.RoomRepository;

import java.util.List;

import lombok.Getter;

public class RoomFragmentViewModel extends ViewModel {
    private MutableLiveData<List<Room>> mListRoomLiveData;
    // LiveData for toast messages
    @Getter
    private MutableLiveData<String> toastMessageLiveData;

    public RoomFragmentViewModel() {
        mListRoomLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData() {
        RoomRepository roomRepository = RoomRepository.getInstance();
        mListRoomLiveData = roomRepository.getRooms("", "");
    }

    public MutableLiveData<List<Room>> getListCategoryLiveData() {
        return mListRoomLiveData;
    }
}
