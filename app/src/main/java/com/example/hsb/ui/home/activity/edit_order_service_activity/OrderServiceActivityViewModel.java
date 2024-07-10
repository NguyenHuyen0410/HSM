//package com.example.hsb.ui.home.activity.edit_order_service_activity;
//
//
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.example.hsb.entities.Category;
//import com.example.hsb.entities.Room;
//import com.example.hsb.entities.ServiceBill;
//import com.example.hsb.entities.ServiceBillDetail;
//import com.example.hsb.repository.CategoryRepository;
//import com.example.hsb.repository.PriceRepository;
//import com.example.hsb.repository.RoomRepository;
//import com.example.hsb.repository.ServiceBillDetailRepository;
//import com.example.hsb.repository.ServiceBillRepository;
//
//import java.util.List;
//
//import lombok.Getter;
//
//public class OrderServiceActivityViewModel extends ViewModel {
//    @Getter
//    private MutableLiveData<List<ServiceBill>> mServiceBill;
//    private  MutableLiveData<List<Room>> mRoom;
//    @Getter
//    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
//    @Getter
//    private final MutableLiveData<Boolean> deleteStatusLiveData = new MutableLiveData<>();
//    @Getter
//    private ServiceBillRepository serviceBillRepository;
//    @Getter
//    private ServiceBillDetailRepository serviceBillDetailRepository;
//    @Getter
//    private RoomRepository roomRepository ;
//    @Getter
//    private final MutableLiveData<ServiceBillDetail> mServiceBillDetail = new MutableLiveData<>();
//
//    public OrderServiceActivityViewModel() {
//        mRoom = new MutableLiveData<>();
//        initData();
//
//    }
//
//    public void initData() {
//        serviceBillDetailRepository = ServiceBillDetailRepository.getInstance();
//        roomRepository = RoomRepository.getInstance();
//        mRoom = roomRepository.getRooms();
//    }
//
//    public MutableLiveData<List<Room>> getListRoomLiveData() {
//        return mRoom;
//    }
//
//    public ServiceBill getServiceBillLiveData(String field, String value) {
//        mServiceBill = serviceBillRepository.getServiceBill(field,value);
//        return mServiceBill;
//    }
//
//    public void createServiceBillDetail(ServiceBillDetail serviceBillDetail) {
//        serviceBillDetailRepository.createServiceBillDetail(serviceBillDetail, new ServiceBillDetailRepository.CreateServiceBillDetailCallback() {
//            @Override
//            public void onCreateSuccess(ServiceBillDetail newServiceBillDetail) {
//                mServiceBillDetail.postValue(newServiceBillDetail);
//                toastMessageLiveData.postValue("ServiceBillDetail created successfully.");
//            }
//
//            @Override
//            public void onCreateFailure(String errorMessage) {
//                toastMessageLiveData.postValue("Create serviceBillDetail failed: " + errorMessage);
//            }
//        });
//    }
//
//}