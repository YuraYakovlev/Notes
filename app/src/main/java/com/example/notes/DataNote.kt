package com.example.notes

import android.os.Parcelable
import android.os.Parcel
import android.os.Parcelable.Creator

open class DataNote : Parcelable {
    internal var id: String?
    internal var name: String?
    internal var description: String?
    internal var date: String?

    constructor(id: String?, name: String?, description: String?, date: String?) {
        this.id = id
        this.name = name
        this.description = description
        this.date = date
    }

    protected constructor(`in`: Parcel?) {
        id = `in`?.readString()
        name = `in`?.readString()
        description = `in`?.readString()
        date = `in`?.readString()
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    override fun writeToParcel(parcel: Parcel?, i: Int) {
        parcel?.writeString(id)
        parcel?.writeString(name)
        parcel?.writeString(description)
        parcel?.writeString(date)
    }

    companion object {
        val CREATOR: Creator<DataNote?>? = object : Creator<DataNote?> {
            override fun createFromParcel(`in`: Parcel?): DataNote? {
                return DataNote(`in`)
            }

            override fun newArray(size: Int): Array<DataNote?>? {
                return arrayOfNulls<DataNote?>(size)
            }
        }
    }

     object CREATOR : Creator<DataNote> {
        override fun createFromParcel(parcel: Parcel): DataNote {
            return DataNote(parcel)
        }

        override fun newArray(size: Int): Array<DataNote?> {
            return arrayOfNulls(size)
        }
    }
}