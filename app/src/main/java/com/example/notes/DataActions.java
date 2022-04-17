package com.example.notes;

import java.util.List;
import java.util.UUID;

public interface DataActions {
    DataNote getData(int position);
    List<DataNote> getDataNoteList();
    int size();
    void addListElement(DataNote dataNote);
    void delete(int position);
    void clear();
    void updateElement(DataNote dataNote);
    void fetchList();
    void setUpdateListListener(UpdateDataListListener listener);
}
