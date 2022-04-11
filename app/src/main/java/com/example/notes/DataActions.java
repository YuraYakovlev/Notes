package com.example.notes;

import java.util.UUID;

public interface DataActions {
    void addListElement(DataNote dataNote);
    void delete(DataNote dataNote);
    void clear();
    void update();
}
