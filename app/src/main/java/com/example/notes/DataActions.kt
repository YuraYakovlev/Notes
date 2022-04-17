package com.example.notes

interface DataActions {
    fun getData(position: Int): DataNote?
    fun getDataNoteList(): MutableList<DataNote?>?
    fun size(): Int
    fun addListElement(dataNote: DataNote?)
    fun delete(position: Int)
    fun clear()
    fun updateElement(dataNote: DataNote?)
    fun fetchList()
    fun setUpdateListListener(listener: UpdateDataListListener?)
}