package com.example.notes;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DataNotesAdapter extends RecyclerView.Adapter<DataNotesAdapter.MyViewHolder> {

    DataActions source;
    public OpenDescriptionFragment openDescriptionFragment = null;
    private int position = -1;

    public int getPosition() {
        return position;
    }

    public void setDataNoteSource(DataActions dataNoteSource) {
        this.source = dataNoteSource;
    }

    @NonNull
    @Override
    public DataNotesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_data_note, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataNotesAdapter.MyViewHolder holder, int position) {
        holder.bind(source.getData(position));
    }

    @Override
    public int getItemCount() {
        return source.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(DataNote dataNote) {
            TextView textView = itemView.findViewById(R.id.data_note_name);
            textView.setText(dataNote.getName());
            textView.setOnClickListener(view -> {
                openDescriptionFragment.openDescription(dataNote);
            });

        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

        }
    }

    interface OpenDescriptionFragment{
        void openDescription(DataNote dataNote);
    }

}
