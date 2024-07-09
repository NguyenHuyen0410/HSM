package com.example.hsb.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.Room;
import com.example.hsb.record.RoomRecord;
import com.example.hsb.response.ListResponse;
import com.example.hsb.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomRepository {
    private static RoomRepository instance;
    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Room> mRoomLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Room>> mRoomListLiveData = new MutableLiveData<>();
    public static RoomRepository getInstance(){
        if(instance == null){
            instance = new RoomRepository();
        }
        return instance;
    }

    public MutableLiveData<Room> getRoom(String value, String field){
        fetchRoom(value, field);
        return mRoomLiveData;
    }

    public MutableLiveData<List<Room>> getRooms(String value, String field){
        fetchRooms(value, field);
        return mRoomListLiveData;
    }

    public void fetchRoom(String value, String field){
        String expand = "device_account_id";
        String filter = field+"='"+value+"'";
        Call<RoomRecord> call = RetrofitClient.getInstance().getRoomServiceApi().getRecord(expand, filter);
        call.enqueue(new Callback<RoomRecord>() {
            @Override
            public void onResponse(@NonNull Call<RoomRecord> call, @NonNull Response<RoomRecord> response) {
                if(response.isSuccessful() && response.body() != null){
                    RoomRecord record = response.body();
                    mRoomLiveData.setValue(setRoom(record));
                } else{
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<RoomRecord> call, @NonNull Throwable t) {
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
    }

    public void fetchRooms(String value, String field){
        String expand = "device_account_id";
        String filter = field == null || field.isEmpty()  ? null :  field+"='"+value+"'";
        Call<ListResponse<RoomRecord>> call = RetrofitClient.getInstance().getRoomServiceApi().getRecords(expand,filter);
        call.enqueue(new Callback<ListResponse<RoomRecord>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<RoomRecord>> call, @NonNull Response<ListResponse<RoomRecord>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<RoomRecord> roomRecordList = response.body().getItems();
                    List<Room> rooms = new ArrayList<>();
                    for(RoomRecord roomRecords: roomRecordList){
                        Room room = setRoom(roomRecords);
                        rooms.add(room);
                    }
                    mRoomListLiveData.setValue(rooms);
                } else{
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ListResponse<RoomRecord>> call, @NonNull Throwable t) {
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
    }

    public interface EditRoomCallBack{
        void onEditSuccess(Room updatedRoom);
        void onEditFailure(String errorMessage);
    }

    public interface CreateRoomCallBack{
        void onCreateSuccess(Room newRoom);
        void onCreateFailure(String errorMessage);
    }

    public interface DeleteRoomCallBack{
        void onDeleteSuccess();
        void onDeleteFailure(String errorMessage);
    }

    public interface UpdateRoomImageCallback {
        void onUpdateSuccess(String imageName);
        void onUpdateFailure(String errorMessage);
    }

    public void editRoom(Room room, EditRoomCallBack editRoomCallBack){
        Call<RoomRecord> call = RetrofitClient.getInstance().getRoomServiceApi().updateRecord(room.getId(), setRoomRecord(room));
        call.enqueue(new Callback<RoomRecord>() {
            @Override
            public void onResponse(@NonNull Call<RoomRecord> call, @NonNull Response<RoomRecord> response) {
                if(response.isSuccessful() && response.body()!=null){
                    editRoomCallBack.onEditSuccess(setRoom(response.body()));
                } else{
                    editRoomCallBack.onEditFailure(response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<RoomRecord> call, @NonNull Throwable t) {
                editRoomCallBack.onEditFailure(t.getMessage());
            }
        });
    }

    public void uploadRoomImage(String roomId, MultipartBody.Part image, UpdateRoomImageCallback updateRoomImageCallback){
        Call<RoomRecord> call = RetrofitClient.getInstance().getRoomServiceApi().uploadFile(roomId, image);
        call.enqueue(new Callback<RoomRecord>() {
            @Override
            public void onResponse(@NonNull Call<RoomRecord> call, @NonNull Response<RoomRecord> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Notify success
                    updateRoomImageCallback.onUpdateSuccess(response.body().getRoomImage());
                } else{
                    updateRoomImageCallback.onUpdateFailure(response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<RoomRecord> call, @NonNull Throwable t) {
                updateRoomImageCallback.onUpdateFailure(t.getMessage());
            }
        });
    }

    public void createRoom(Room room, CreateRoomCallBack createRoomCallBack){
        Call<RoomRecord> call = RetrofitClient.getInstance().getRoomServiceApi().createRecord(setRoomRecord(room));
        call.enqueue(new Callback<RoomRecord>() {
            @Override
            public void onResponse(@NonNull Call<RoomRecord> call, @NonNull Response<RoomRecord> response) {
                if(response.isSuccessful() && response.body()!=null){
                    createRoomCallBack.onCreateSuccess(setRoom(response.body()));
                } else{
                    createRoomCallBack.onCreateFailure(response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<RoomRecord> call, @NonNull Throwable t) {
                createRoomCallBack.onCreateFailure(t.getMessage());
            }
        });
    }

    public void deleteRoom(String roomId, DeleteRoomCallBack deleteRoomCallBack){
        Call<Void> call = RetrofitClient.getInstance().getServiceBillServiceApi().deleteRecord(roomId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()){
                    deleteRoomCallBack.onDeleteSuccess();
                } else{
                    deleteRoomCallBack.onDeleteFailure(response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                deleteRoomCallBack.onDeleteFailure(t.getMessage());
            }
        });
    }

    public RoomRecord setRoomRecord(Room room){
        RoomRecord roomRecord = new RoomRecord();
        if(!room.getId().isEmpty()){
            roomRecord.setId(room.getId());
            roomRecord.setCreated(DateUtil.localDateTimeToJsonFormat(room.getCreated()));
            roomRecord.setUpdated(DateUtil.localDateTimeToJsonFormat(room.getUpdated()));
        } else{
            roomRecord.setCreated(DateUtil.localDateTimeToJsonFormat(LocalDateTime.now()));
            roomRecord.setUpdated(DateUtil.localDateTimeToJsonFormat(LocalDateTime.now()));
        }
        roomRecord.set_deleted(room.is_deleted());
        roomRecord.setRoomNumber(room.getRoomNumber());
        roomRecord.setRoomType(room.getRoomType());
        roomRecord.setRoomCapacity(room.getRoomCapacity());
        roomRecord.setRoomArea(roomRecord.getRoomArea());
        roomRecord.setRoomImage(roomRecord.getRoomImage());
        roomRecord.setDescription(roomRecord.getDescription());
        roomRecord.setDeviceAccountId(room.getDeviceAccountId());
        roomRecord.setStatus(room.getStatus());
        roomRecord.setRemark(room.getRemark());
        return roomRecord;
    }

    public Room setRoom(RoomRecord roomRecord){
        Account account = new Account(
                roomRecord.getExpand().getAccount().getId(),
                roomRecord.getExpand().getAccount().getUsername(),
                roomRecord.getExpand().getAccount().getAccountGmail(),
                roomRecord.getExpand().getAccount().getAccountPassword(),
                roomRecord.getExpand().getAccount().getStatus(),
                roomRecord.getExpand().getAccount().is_deleted(),
                DateUtil.apiDateTimeStringToLocalDateTime(roomRecord.getExpand().getAccount().getCreated()),
                DateUtil.apiDateTimeStringToLocalDateTime(roomRecord.getExpand().getAccount().getUpdated()),
                roomRecord.getExpand().getAccount().getRoleId()
        );
        return new Room(
                roomRecord.getId(),
                DateUtil.apiDateTimeStringToLocalDateTime(roomRecord.getCreated()),
                DateUtil.apiDateTimeStringToLocalDateTime(roomRecord.getUpdated()),
                roomRecord.is_deleted(),
                roomRecord.getRoomNumber(),
                roomRecord.getRoomType(),
                roomRecord.getRoomCapacity(),
                roomRecord.getRoomArea(),
                roomRecord.getRoomImage(),
                roomRecord.getDescription(),
                roomRecord.getDeviceAccountId(),
                roomRecord.getStatus(),
                roomRecord.getRemark(),
                account
        );
    }
}
