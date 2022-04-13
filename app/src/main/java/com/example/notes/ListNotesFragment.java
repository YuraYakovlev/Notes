package com.example.notes;

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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;
import java.util.UUID;

public class ListNotesFragment extends Fragment implements DataNotesAdapter.OpenDescriptionFragment {

    private DataActions dataActions = new DataActionsImpl();
   // List<DataNote> dataNoteList = new ArrayList<>();
    private DataNotesAdapter adapter = new DataNotesAdapter();
    private DescriptionFragment descriptionFragment;
    ItemTouchHelper.Callback callback;


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
        if (id == R.id.action_clear){
            dataActions.clear();
            update();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter.setDataNoteSource(dataActions);
        adapter.openDescriptionFragment = this;
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_data_note_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);

        decoration.setDrawable(Objects.requireNonNull(AppCompatResources.getDrawable(getContext(), R.drawable.separator)));

        recyclerView.addItemDecoration(decoration);

        recyclerView.setAdapter(adapter);

        descriptionFragment = new DescriptionFragment();
        //descriptionFragment.dataActions = this;

        FloatingActionButton floatingButton = view.findViewById(R.id.btn_new_note);

        floatingButton.setOnClickListener(v -> {
            String uuid = UUID.randomUUID().toString();
            dataActions.addListElement(new DataNote(uuid,"New Note", "", ""));
            //dataNoteList.add(new DataNote(uuid, "New Note", "", ""));
           update();
        });
    }


    @Override
    public void openDescription(DataNote dataNote) {
        descriptionFragment = DescriptionFragment.newInstance(dataNote);
        //descriptionFragment.dataActions = this;
        requireActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, descriptionFragment)
                .addToBackStack(null)
                .commit();
        fillListElement(dataNote);
    }

    public void fillListElement(DataNote dataNote) {
        for (DataNote dataNote1 : dataActions.getDataNoteList()) {
            if (dataNote.getId().equals(dataNote1.getId())) {
                dataNote1.setName(dataNote.getName());
                dataNote1.setDescription(dataNote.getDescription());
                dataNote1.setDate(dataNote.getDate());
            }
        }
    }
//
//    @Override
//    public void delete(int position) {
//        dataNoteList.remove(position);
//    }
//
//    @Override
//    public void clear() {
//        dataNoteList.clear();
//        update();
//    }
//

    public void update() {
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
        if (id == R.id.action_delete){
            //delete(adapter.getPosition());
        }
        adapter.notifyItemRemoved(adapter.getPosition());
        return super.onContextItemSelected(item);
    }
}