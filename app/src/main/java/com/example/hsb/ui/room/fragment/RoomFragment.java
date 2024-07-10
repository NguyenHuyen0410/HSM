package com.example.hsb.ui.room.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hsb.R;
import com.example.hsb.entities.Room;
import com.example.hsb.entities.Service;
import com.example.hsb.ui.room.adapter.RoomAdaptor;
import java.util.ArrayList;
import java.util.List;

public class RoomFragment extends Fragment {
    private final List<Room> roomList = new ArrayList<>();
    private RoomAdaptor adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        RoomFragmentViewModel roomFragmentViewModel = new RoomFragmentViewModel();
        // Observe changes in the account list
        roomFragmentViewModel.getListRoomLiveData().observe(getViewLifecycleOwner(), new Observer<List<Room>>() {
            @Override
            public void onChanged(List<Room> rooms) {
                if (rooms != null) {
                    roomList.clear();
                    roomList.addAll(rooms);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.room_list);
        adapter = new RoomAdaptor(roomList, requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}