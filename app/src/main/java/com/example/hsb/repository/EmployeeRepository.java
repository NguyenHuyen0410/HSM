package com.example.hsb.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.Employee;
import com.example.hsb.entities.MstRegion;
import com.example.hsb.entities.Role;
import com.example.hsb.record.EmployeeRecord;
import com.example.hsb.response.ListResponse;
import com.example.hsb.storage.SystemRoles;
import com.example.hsb.utils.DateUtil;

import java.time.LocalDateTime;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeRepository {
    private static EmployeeRepository instance;
    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Employee> mEmployeeLiveData = new MutableLiveData<>();

    public static EmployeeRepository getInstance(){
        if(instance==null){
            instance = new EmployeeRepository();
        }
        return instance;
    }

    public MutableLiveData<Employee> getEmployee(String accountId){
        fetchEmployee(accountId);
        return mEmployeeLiveData;
    }

    public void fetchEmployee(String accountId){
        final Employee[] employee = {new Employee()};
        Call<ListResponse<EmployeeRecord>> call = RetrofitClient.getInstance().getEmployeeServiceApi().getRecords();
        call.enqueue(new Callback<ListResponse<EmployeeRecord>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<EmployeeRecord>> call,@NonNull  Response<ListResponse<EmployeeRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ListResponse<EmployeeRecord> employeeRecordList = response.body();
                    EmployeeRecord employeeRecord = new EmployeeRecord();
                    for(int i = 0; i < employeeRecordList.getItems().size(); i++){
                        if(employeeRecordList.getItems().get(i).getAccountId().equals(accountId)){
                            employeeRecord = employeeRecordList.getItems().get(i);
                        }
                    }
                    employee[0] = setEmployee(employeeRecord);
                    mEmployeeLiveData.setValue(employee[0]);
                } else{
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ListResponse<EmployeeRecord>> call,@NonNull  Throwable t) {
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
    }

    public interface CreateEmployeeCallBack{
        void onCreateSuccess(Employee employee);
        void onCreateFailure(String errorMessage);
    }

    public interface EditEmployeeCallBack{
        void onEditSuccess(Employee employee);
        void onEditFailure(String errorMessage);
    }

    public interface DeleteEmployeeCallBack{
        void onDeleteSuccess();
        void onDeleteFailure(String errorMessage);
    }

    public interface UpdateProfileImageCallback {
        void onUpdateSuccess(String imageName);
        void onUpdateFailure(String errorMessage);
    }

    public void createEmployee(Employee employee, CreateEmployeeCallBack createEmployeeCallBack){
        final EmployeeRecord employeeRecord = new EmployeeRecord();
        employeeRecord.setAccountId(employee.getAccountId());
        Call<EmployeeRecord> createCall = RetrofitClient.getInstance().getEmployeeServiceApi().createRecord(employeeRecord);
        createCall.enqueue(new Callback<EmployeeRecord>() {
            @Override
            public void onResponse(@NonNull Call<EmployeeRecord> call,@NonNull Response<EmployeeRecord> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EmployeeRecord createRecord = response.body();
                    Employee newEmployee = new Employee();
                    newEmployee.setId(createRecord.getId());
                    newEmployee.setAccountId(employee.getAccountId());
                    createEmployeeCallBack.onCreateSuccess(newEmployee);
                } else {
                    // Handle update failure
                    Log.e("CreateEmployee", "Create failed: " + response.message());
                    createEmployeeCallBack.onCreateFailure(response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<EmployeeRecord> call, @NonNull Throwable t) {
                // Handle update failure
                Log.e("CreateEmployee", "Create failed: " + t.getMessage());
                createEmployeeCallBack.onCreateFailure(t.getMessage());
            }
        });

    }

    public void editEmployee(Employee employee, EditEmployeeCallBack editEmployeeCallBack) {
        Call<EmployeeRecord> updateCall = RetrofitClient.getInstance().getEmployeeServiceApi().updateRecord(employee.getId(), setEmployeeRecord(employee));
        updateCall.enqueue(new Callback<EmployeeRecord>() {
            @Override
            public void onResponse(@NonNull Call<EmployeeRecord> call, @NonNull Response<EmployeeRecord> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EmployeeRecord updatedRecord = response.body();
                    editEmployeeCallBack.onEditSuccess(setEmployee(updatedRecord));
                } else {
                    // Handle update failure
                    Log.e("EditEmployee", "Update failed: " + response.message());
                    editEmployeeCallBack.onEditFailure(response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<EmployeeRecord> call,@NonNull Throwable t) {
                // Handle update failure
                Log.e("EditEmployee", "Update call failed: " + t.getMessage());
                editEmployeeCallBack.onEditFailure(t.getMessage());
            }
        });
    }

    public void uploadImage(String employeeId, MultipartBody.Part image, UpdateProfileImageCallback updateProfileImageCallback) {
        Call<EmployeeRecord> uploadCall = RetrofitClient.getInstance().getEmployeeServiceApi().uploadFile(employeeId, image);
        uploadCall.enqueue(new Callback<EmployeeRecord>() {
            @Override
            public void onResponse(@NonNull Call<EmployeeRecord> call,@NonNull Response<EmployeeRecord> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateProfileImageCallback.onUpdateSuccess(response.body().getProfileImage());
                } else {
                    // Handle image upload failure
                    Log.e("UpdateProfileImage", "Image upload failed: " + response.message());
                    updateProfileImageCallback.onUpdateFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<EmployeeRecord> call,@NonNull Throwable t) {
                // Handle image upload failure
                Log.e("UpdateProfileImage", "Image upload call failed: " + t.getMessage());
                updateProfileImageCallback.onUpdateFailure(t.getMessage());
            }
        });
    }
    public void deleteEmployee(String accountId, String employeeId, DeleteEmployeeCallBack deleteEmployeeCallBack){
        Call<Void> call = RetrofitClient.getInstance().getEmployeeServiceApi().deleteRecord(employeeId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchEmployee(accountId);
                    deleteEmployeeCallBack.onDeleteSuccess();
                } else {
                    deleteEmployeeCallBack.onDeleteFailure(response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                deleteEmployeeCallBack.onDeleteFailure(t.getMessage());
            }
        });
    }

    public Employee setEmployee(EmployeeRecord employeeRecord){
        String roleId = employeeRecord.getExpand().getAccount().getRoleId();
        Role role = new Role();
        if(roleId.equals(SystemRoles.MANAGER.getId())){
            role = new Role(SystemRoles.MANAGER.getId(),
                    SystemRoles.MANAGER.getName(),
                    SystemRoles.MANAGER.isDeleted());
        } else if(roleId.equals(SystemRoles.RECEPTIONIST.getId())){
            role = new Role(SystemRoles.RECEPTIONIST.getId(),
                    SystemRoles.RECEPTIONIST.getName(),
                    SystemRoles.RECEPTIONIST.isDeleted());
        } else if(roleId.equals(SystemRoles.CUSTOMER.getId())){
            role = new Role(SystemRoles.CUSTOMER.getId(),
                    SystemRoles.CUSTOMER.getName(),
                    SystemRoles.CUSTOMER.isDeleted());
        }

        Account account = new Account(employeeRecord.getExpand().getAccount().getId(),
                employeeRecord.getExpand().getAccount().getUsername(),
                employeeRecord.getExpand().getAccount().getAccountGmail(),
                employeeRecord.getExpand().getAccount().getAccountPassword(),
                employeeRecord.getExpand().getAccount().getStatus(),
                employeeRecord.getExpand().getAccount().is_deleted(),
                DateUtil.apiDateTimeStringToLocalDateTime(employeeRecord.getExpand().getAccount().getCreated()),
                DateUtil.apiDateTimeStringToLocalDateTime(employeeRecord.getExpand().getAccount().getUpdated()),
                role, employeeRecord.getId(), employeeRecord.getProfileImage());

        MstRegion region = new MstRegion();
        if(employeeRecord.getExpand().getNationality()!=null){
            region = new MstRegion(employeeRecord.getExpand().getNationality().getId(),
                    employeeRecord.getExpand().getNationality().getRegionCode(),
                    employeeRecord.getExpand().getNationality().getRegionName(),
                    employeeRecord.getExpand().getNationality().is_deleted(),
                    DateUtil.apiDateTimeStringToLocalDateTime(employeeRecord.getExpand().getNationality().getCreated()),
                    DateUtil.apiDateTimeStringToLocalDateTime(employeeRecord.getExpand().getNationality().getUpdated()));
        }

        return new Employee(
                employeeRecord.getId() != null ? employeeRecord.getId() : "",
                employeeRecord.getAddress() != null ? employeeRecord.getAddress() : "",
                !employeeRecord.getDob().isEmpty() ? DateUtil.apiDateTimeStringToLocalDateTime(employeeRecord.getDob()) : LocalDateTime.MIN,
                employeeRecord.getFirstName() != null ? employeeRecord.getFirstName() : "",
                employeeRecord.getLastName() != null ? employeeRecord.getLastName() : "",
                employeeRecord.getGender() != null ? employeeRecord.getGender() : "",
                employeeRecord.getPhoneNumber() != null ? employeeRecord.getPhoneNumber() : "",
                employeeRecord.getProfileImage() != null ? employeeRecord.getProfileImage() : "",
                employeeRecord.is_deleted(),
                DateUtil.apiDateTimeStringToLocalDateTime(employeeRecord.getCreated()),
                DateUtil.apiDateTimeStringToLocalDateTime(employeeRecord.getUpdated()),
                employeeRecord.getRemark() != null ? employeeRecord.getRemark() : "",
                !employeeRecord.getStartWorkDate().isEmpty() ? DateUtil.apiDateTimeStringToLocalDateTime(employeeRecord.getStartWorkDate()) : LocalDateTime.MIN,
                account,
                region
        );
    }

    public EmployeeRecord setEmployeeRecord(Employee employee){
        EmployeeRecord employeeRecord = new EmployeeRecord();
        if(employee.getId()!=null){
            employeeRecord.setId(employee.getId());
            employeeRecord.setCreated(DateUtil.localDateTimeToString(employee.getCreatedDate()));
            employeeRecord.setUpdated(DateUtil.localDateTimeToString(employee.getLastModifiedDate()));
        } else{
            employeeRecord.setCreated(DateUtil.localDateTimeToString(LocalDateTime.now()));
            employeeRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        }
        employeeRecord.setAddress(employee.getAddress());
        employeeRecord.setDob(DateUtil.localDateTimeToJsonFormat(employee.getDob()));
        employeeRecord.setFirstName(employee.getFirstName());
        employeeRecord.setLastName(employee.getLastName());

        if(employee.getGender().equals("Nam")) employeeRecord.setGender("male");
        else employeeRecord.setGender("female");

        employeeRecord.setPhoneNumber(employee.getPhoneNumber());
        employeeRecord.setProfileImage(employee.getProfileImage());
        employeeRecord.set_deleted(employee.isDeleted());
        employeeRecord.setRemark(employee.getRemark());
        employeeRecord.setStartWorkDate(DateUtil.localDateTimeToJsonFormat(employee.getStartWorkDate()));
        EmployeeRecord.Expand expand = new EmployeeRecord.Expand(null, null);
        employeeRecord.setExpand(expand);
        return employeeRecord;
    }
}


