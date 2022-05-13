package com.example.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.Objects;
import java.util.UUID;

public class ListNotesFragment extends Fragment
        implements DataNotesAdapter.OpenDescriptionFragment,
        UpdateDataListListener {

    public DataActions dataActions;
    // List<DataNote> dataNoteList = dataActions.getDataNoteList();
    private DataNotesAdapter adapter = new DataNotesAdapter(getActivity(), dataActions);
    private DescriptionFragment descriptionFragment;
    private SharedPreferences sharedPreferences = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_list_notes, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_clear) {
            dataActions.clear();
            update();
            return true;
        } else if (id == R.id.action_close) {
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        dataActions = new PreferencesDataActions(sharedPreferences);
        dataActions.setUpdateListListener(this);

        adapter.setDataNoteSource(dataActions);
        adapter.openDescriptionFragment = this;
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_data_note_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);

        decoration.setDrawable(Objects.requireNonNull(AppCompatResources.getDrawable(getContext(), R.drawable.separator)));

        recyclerView.addItemDecoration(decoration);

        recyclerView.setAdapter(adapter);

        descriptionFragment = new DescriptionFragment();
        descriptionFragment.dataActions = dataActions;

        FloatingActionButton floatingButton = view.findViewById(R.id.btn_new_note);

        floatingButton.setOnClickListener(v -> {
            String uuid = UUID.randomUUID().toString();
            dataActions.addListElement(new DataNote(uuid, "New Note", "", ""));
            //dataNoteList.add(new DataNote(uuid, "New Note", "", ""));
            update();
        });
    }


    @Override
    public void openDescription(DataNote dataNote) {
        descriptionFragment = DescriptionFragment.newInstance(dataNote);
        descriptionFragment.dataActions = dataActions;
        requireActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, descriptionFragment)
                .addToBackStack(null)
                .commit();
        fillElementList(dataNote);
    }

    public void fillElementList(DataNote dataNote) {
        for (DataNote dataNote1 : dataActions.getDataNoteList()) {
            if (dataNote.getId().equals(dataNote1.getId())) {
                dataNote1.setName(dataNote.getName());
                dataNote1.setDescription(dataNote.getDescription());
                dataNote1.setDate(dataNote.getDate());
            }
        }
    }

    public void update() {
        dataActions.fetchList();
        adapter.setDataNoteSource(dataActions);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            dataActions.delete(adapter.getPosition());
        }
        adapter.notifyItemRemoved(adapter.getPosition());
        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }
}