package com.example.notes;

import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreferencesDataActions implements DataActions {

    private final SharedPreferences sharedPreferences;
    private List<DataNote> dataNoteList;
    private final String NOTES_DATA = "NOTES_DATA";
    UpdateDataListListener updateDataListListener;

    public PreferencesDataActions(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        fetch();
    }

    @Override
    public DataNote getData(int position) {
        return dataNoteList.get(position);
    }

    @Override
    public List<DataNote> getDataNoteList() {
        return dataNoteList;
    }

    @Override
    public int size() {
        return dataNoteList.size();
    }

    @Override
    public void addListElement(DataNote dataNote) {
        dataNoteList.add(dataNote);
        update();
        fetch();
    }

    @Override
    public void delete(int position) {
        dataNoteList.remove(position);
        update();
        fetch();
    }

    @Override
    public void clear() {
        dataNoteList.clear();
        update();
        fetch();
    }

    @Override
    public void updateElement(DataNote dataNote) {
        for (DataNote dataNote1 : dataNoteList) {
            if (dataNote.getId().equals(dataNote1.getId())) {
                dataNote1.setName(dataNote.getName());
                dataNote1.setDescription(dataNote.getDescription());
                dataNote1.setDate(dataNote.getDate());
            }
        }
        update();
        fetch();
        if (updateDataListListener != null)
            updateDataListListener.updateListEvent();
    }

    private void fetch() {
        String json = sharedPreferences.getString(NOTES_DATA, null);
        if (json == null) {
            dataNoteList = new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<DataNote>>() {
            }.getType();
            if (dataNoteList != null) {
                dataNoteList.clear();
                ArrayList<DataNote> newList = new GsonBuilder().create().fromJson(json, type);
                dataNoteList.addAll(newList);
            } else {
                dataNoteList = new GsonBuilder().create().fromJson(json, type);
            }

        }
    }

    private void update() {
        String json = new GsonBuilder().create().toJson(dataNoteList);
        sharedPreferences.edit()
                .putString(NOTES_DATA, json)
                .apply();
    }

    public void fetchList() {
        fetch();
    }

    @Override
    public void setUpdateListListener(UpdateDataListListener listener) {
        updateDataListListener = listener;
    }
}
