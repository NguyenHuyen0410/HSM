package com.example.hsb.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.hsb.R;
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
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    private MutableLiveData<Employee> mEmployeeLiveData = new MutableLiveData<>();

    public static EmployeeRepository getInstance(){
        if(instance==null){
            instance = new EmployeeRepository();
        }
        return instance;
    }

    public MutableLiveData<Employee> getEmployeeList(){
        fetchEmployees();
        return mEmployeeLiveData;
    }

    public MutableLiveData<Employee> getEmployee(String accountId){
        fetchEmployee(accountId);
        return mEmployeeLiveData;
    }

    public void fetchEmployees(){};

    public void fetchEmployee(String accountId){
        final Employee[] employee = {new Employee()};
        String expand = "account_id,nationality_id";
        String filter = "account_id='"+accountId+"'";
        Call<ListResponse<EmployeeRecord>> call = RetrofitClient.getInstance().getEmployeeServiceApi().getRecords(expand, filter);
        call.enqueue(new Callback<ListResponse<EmployeeRecord>>() {
            @Override
            public void onResponse(Call<ListResponse<EmployeeRecord>> call, Response<ListResponse<EmployeeRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ListResponse<EmployeeRecord> employeeRecordList = response.body();
                    EmployeeRecord employeeRecord = employeeRecordList.getItems().get(0);
                    employee[0] = setEmployee(employeeRecord);
                    mEmployeeLiveData.setValue(employee[0]);
                } else{
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<ListResponse<EmployeeRecord>> call, Throwable t) {
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
            public void onResponse(Call<EmployeeRecord> call, Response<EmployeeRecord> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EmployeeRecord createRecord = response.body();
                    Employee newEmployee = new Employee();
                    newEmployee.setAccountId(createRecord.getAccountId());
                    createEmployeeCallBack.onCreateSuccess(newEmployee);
                } else {
                    // Handle update failure
                    Log.e("CreateEmployee", "Create failed: " + response.message());
                    createEmployeeCallBack.onCreateFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<EmployeeRecord> call, Throwable t) {
                // Handle update failure
                Log.e("CreateEmployee", "Create failed: " + t.getMessage());
                createEmployeeCallBack.onCreateFailure(t.getMessage());
            }
        });

    }

    public void editEmployee(Employee employee, EditEmployeeCallBack editEmployeeCallBack) {
        final EmployeeRecord employeeRecord = setEmployeeRecord(employee);

        Call<EmployeeRecord> updateCall = RetrofitClient.getInstance().getEmployeeServiceApi().updateRecord(employeeRecord.getId(), employeeRecord);
        updateCall.enqueue(new Callback<EmployeeRecord>() {
            @Override
            public void onResponse(Call<EmployeeRecord> call, Response<EmployeeRecord> response) {
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
            public void onFailure(Call<EmployeeRecord> call, Throwable t) {
                // Handle update failure
                Log.e("EditEmployee", "Update call failed: " + t.getMessage());
                editEmployeeCallBack.onEditFailure(t.getMessage());
            }
        });
    }

    public void uploadImage(Employee employee, MultipartBody.Part image, UpdateProfileImageCallback updateProfileImageCallback) {
        Call<EmployeeRecord> uploadCall = RetrofitClient.getInstance().getEmployeeServiceApi().uploadFile(employee.getId(), image);
        uploadCall.enqueue(new Callback<EmployeeRecord>() {
            @Override
            public void onResponse(Call<EmployeeRecord> call, Response<EmployeeRecord> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Update profile image in the record
                    String imageName = response.body().getProfileImage();
                    employee.setProfileImage(imageName);
                    // Notify success
                    updateProfileImageCallback.onUpdateSuccess(imageName);
                } else {
                    // Handle image upload failure
                    Log.e("UpdateProfileImage", "Image upload failed: " + response.message());
                    updateProfileImageCallback.onUpdateFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<EmployeeRecord> call, Throwable t) {
                // Handle image upload failure
                Log.e("UpdateProfileImage", "Image upload call failed: " + t.getMessage());
                updateProfileImageCallback.onUpdateFailure(t.getMessage());
            }
        });
    }
    public void deleteEmployee(String employeeId, DeleteEmployeeCallBack deleteEmployeeCallBack){
        Call<Void> call = RetrofitClient.getInstance().getEmployeeServiceApi().deleteRecord(employeeId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchEmployees();
                    deleteEmployeeCallBack.onDeleteSuccess();
                } else {
                    deleteEmployeeCallBack.onDeleteFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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

        MstRegion region = new MstRegion(employeeRecord.getExpand().getRegion().getId(),
                employeeRecord.getExpand().getRegion().getRegionCode(),
                employeeRecord.getExpand().getRegion().getRegionName(),
                employeeRecord.getExpand().getRegion().is_deleted(),
                DateUtil.apiDateTimeStringToLocalDateTime(employeeRecord.getExpand().getRegion().getCreated()),
                DateUtil.apiDateTimeStringToLocalDateTime(employeeRecord.getExpand().getRegion().getUpdated()));

        return new Employee(employeeRecord.getId(),
                employeeRecord.getAddress(),
                DateUtil.apiDateTimeStringToLocalDateTime(employeeRecord.getDob()),
                employeeRecord.getFirstName(),
                employeeRecord.getLastName(),
                employeeRecord.getGender(),
                employeeRecord.getPhoneNumber(),
                employeeRecord.getProfileImage(),
                employeeRecord.is_deleted(),
                DateUtil.apiDateTimeStringToLocalDateTime(employeeRecord.getCreated()),
                DateUtil.apiDateTimeStringToLocalDateTime(employeeRecord.getUpdated()),
                employeeRecord.getRemark(),
                DateUtil.apiDateTimeStringToLocalDateTime(employeeRecord.getStartWorkDate()),
                account,
                region);
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


