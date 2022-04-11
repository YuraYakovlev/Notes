package com.example.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListNotesFragment extends Fragment implements DataActions, DataNotesAdapter.OpenDescriptionFragment {

    DataNotesAdapter adapter = new DataNotesAdapter();
    List<DataNote> dataNoteList = new ArrayList<>();
    DescriptionFragment descriptionFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter.setDataNoteSource(dataNoteList);
        adapter.openDescriptionFragment = this;
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_data_note_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);

        decoration.setDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.separator));

        recyclerView.addItemDecoration(decoration);

        recyclerView.setAdapter(adapter);

        descriptionFragment = new DescriptionFragment();
        descriptionFragment.dataActions = this;

        FloatingActionButton floatingButton = view.findViewById(R.id.btn_new_note);

        floatingButton.setOnClickListener(v -> {
            String uuid = UUID.randomUUID().toString();
            dataNoteList.add(new DataNote(uuid, "New Note", "", ""));
            update();
        });
    }


    @Override
    public void openDescription(DataNote dataNote) {
        descriptionFragment = DescriptionFragment.newInstance(dataNote);
        descriptionFragment.dataActions = this;
        requireActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, descriptionFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addListElement(DataNote dataNote) {
        openDescription(dataNote);
        for (DataNote dataNote1 : dataNoteList) {
            if (dataNote.getId().equals(dataNote1.getId())) {
                dataNote1.setName(dataNote.getName());
                dataNote1.setDescription(dataNote.getDescription());
                dataNote1.setDate(dataNote.getDate());
            }
        }
        update();
    }

    @Override
    public void delete(DataNote dataNote) {
        dataNoteList.remove(dataNote);
    }

    @Override
    public void clear() {
        dataNoteList.clear();
        update();
    }

    @Override
    public void update() {
        adapter.notifyDataSetChanged();
    }
}