package com.example.notes;

import java.util.ArrayList;
import java.util.List;

public class DataActionsImpl implements DataActions{


    private List<DataNote> dataNoteList = new ArrayList<>();

    public List<DataNote> getDataNoteList() {
        return dataNoteList;
    }

    @Override
    public DataNote getData(int position) {
        return dataNoteList.get(position);
    }

    @Override
    public int size() {
        return dataNoteList.size();
    }

    @Override
    public void addListElement(DataNote dataNote) {
        dataNoteList.add(dataNote);
    }

    @Override
    public void delete(int position) {
        dataNoteList.remove(position);
    }

    @Override
    public void clear() {
        dataNoteList.clear();
    }

    @Override
    public void updateElement(DataNote dataNote) {

    }
}
