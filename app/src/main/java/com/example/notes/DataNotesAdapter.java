package com.example.notes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataNotesAdapter extends RecyclerView.Adapter<DataNotesAdapter.MyViewHolder> {

    public OpenDescriptionFragment openDescriptionFragment = null;
    private DataActions source;
    private int position = -1;
    private Activity activity;

    public DataNotesAdapter(Activity activity, DataActions source) {
        this.source = source;
        this.activity = activity;
    }

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

    interface OpenDescriptionFragment {
        void openDescription(DataNote dataNote);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder { //implements View.OnCreateContextMenuListener{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //activity.registerForContextMenu(itemView);
        }

        public void bind(DataNote dataNote) {
            TextView textView = itemView.findViewById(R.id.data_note_name);
            textView.setText(dataNote.getName());
            itemView.setOnClickListener(view -> {
                openDescriptionFragment.openDescription(dataNote);
            });
            itemView.setOnLongClickListener(view -> {
                position = getLayoutPosition();
                itemView.showContextMenu(500,100);
                return true;
            });
        }

//        @Override
//        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//
//            MenuItem delete = contextMenu.add(Menu.NONE, 2, 2, "Delete");
//            delete.setOnMenuItemClickListener(onEditMenu);
//        }
//
//        private final MenuItem.OnMenuItemClickListener onEditMenu = menuItem -> {
//
//            DataActions dataActions = new DataActionsImpl();
//
//
//            switch (menuItem.getItemId()){
//                case 2:
//                    dataActions.delete(position);
//                    break;
//            }
//
//            return true;
//        };
    }

}
