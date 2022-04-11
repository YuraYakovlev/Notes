package com.example.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DataNotesAdapter extends RecyclerView.Adapter<DataNotesAdapter.MyViewHolder> {

    List dataNoteSource = new ArrayList<DataNote>();
    public OpenDescriptionFragment openDescriptionFragment = null;


    public void setDataNoteSource(List dataNoteSource) {
        this.dataNoteSource = dataNoteSource;
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
        holder.bind((DataNote) dataNoteSource.get(position));
    }

    @Override
    public int getItemCount() {
        return dataNoteSource.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
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
    }

    interface OpenDescriptionFragment{
        void openDescription(DataNote dataNote);
    }

}
