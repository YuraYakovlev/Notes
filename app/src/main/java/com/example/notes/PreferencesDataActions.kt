package com.example.notes

import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import com.google.gson.GsonBuilder
import java.util.ArrayList

class PreferencesDataActions(private val sharedPreferences: SharedPreferences?) : DataActions {
    private var dataNoteList: MutableList<DataNote?>? = null
    private val NOTES_DATA: String? = "NOTES_DATA"
    var updateDataListListener: UpdateDataListListener? = null
    override fun getData(position: Int): DataNote? {
        return dataNoteList?.get(position)
    }

    override fun getDataNoteList(): MutableList<DataNote?>? {
        return dataNoteList
    }

    override fun size(): Int {
        return dataNoteList?.size!!
    }

    override fun addListElement(dataNote: DataNote?) {
        dataNoteList?.add(dataNote)
        update()
        fetch()
    }

    override fun delete(position: Int) {
        dataNoteList?.removeAt(position)
        update()
        fetch()
    }

    override fun clear() {
        dataNoteList?.clear()
        update()
        fetch()
    }

    override fun updateElement(dataNote: DataNote?) {
        for (dataNote1 in dataNoteList!!) {
            if (dataNote?.getId() == dataNote1?.getId()) {
                dataNote1?.setName(dataNote?.getName())
                dataNote1?.setDescription(dataNote?.getDescription())
                dataNote1?.setDate(dataNote?.getDate())
            }
        }
        update()
        fetch()
        if (updateDataListListener != null) updateDataListListener?.updateListEvent()
    }

    private fun fetch() {
        val json = sharedPreferences?.getString(NOTES_DATA, null)
        if (json == null) {
            dataNoteList = ArrayList()
        } else {
            val type = object : TypeToken<ArrayList<DataNote?>?>() {}.type
            if (dataNoteList != null) {
                dataNoteList?.clear()
                val newList = GsonBuilder().create().fromJson<ArrayList<DataNote?>?>(json, type)
                dataNoteList?.addAll(newList)
            } else {
                dataNoteList = GsonBuilder().create().fromJson(json, type)
            }
        }
    }

    private fun update() {
        val json = GsonBuilder().create().toJson(dataNoteList)
        sharedPreferences?.edit()
            ?.putString(NOTES_DATA, json)
            ?.apply()
    }

    override fun fetchList() {
        fetch()
    }

    override fun setUpdateListListener(listener: UpdateDataListListener?) {
        updateDataListListener = listener
    }

    init {
        fetch()
    }
}