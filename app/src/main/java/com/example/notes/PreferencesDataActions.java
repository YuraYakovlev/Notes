package com.example.notes;

import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreferencesDataActions implements DataActions{

    private final SharedPreferences sharedPreferences;
    private List<DataNote> dataNoteList;
    private final String NOTES_DATA = "NOTES_DATA";
    private ListNotesFragment listNotesFragment;
    private DataNote dataNote;

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
    }

    @Override
    public void delete(int position) {
        dataNoteList.remove(position);
        update();
    }

    @Override
    public void clear() {
        dataNoteList.clear();
        update();
    }

    @Override
    public void updateElement(DataNote dataNote) {
        listNotesFragment.fillElementList(dataNote);
    }

    private void fetch(){
        String json = sharedPreferences.getString(NOTES_DATA, null);
        if (json == null){
            dataNoteList = new ArrayList<>();
        }else {
            Type type = new TypeToken<ArrayList<DataNote>>(){
            }.getType();
            dataNoteList = new GsonBuilder().create().fromJson(json, type);
        }
    }

    private void update(){
        String json = new GsonBuilder().create().toJson(dataNoteList);
        sharedPreferences.edit()
                .putString(NOTES_DATA, json)
                .apply();
    }
}
