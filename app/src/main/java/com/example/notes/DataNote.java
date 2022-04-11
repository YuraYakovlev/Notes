package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class DataNote implements Parcelable {

    public static final Creator<DataNote> CREATOR = new Creator<DataNote>() {
        @Override
        public DataNote createFromParcel(Parcel in) {
            return new DataNote(in);
        }

        @Override
        public DataNote[] newArray(int size) {
            return new DataNote[size];
        }
    };
    private String id;
    private String name;
    private String description;
    private String date;

    public DataNote(String id, String name, String description, String date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    protected DataNote(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        date = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(date);
    }
}
