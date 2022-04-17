package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.DataNotesAdapter.MyViewHolder

class DataNotesAdapter(private var source: DataActions?) :
    RecyclerView.Adapter<MyViewHolder?>() {
    var openDescriptionFragment: OpenDescriptionFragment? = null
    private var position = -1
    fun setPosition(position: Int) {
        this.position = position
    }

    fun getPosition(): Int {
        return position
    }

    fun setDataNoteSource(dataNoteSource: DataActions?) {
        source = dataNoteSource
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_data_note, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(source?.getData(position))
    }

    override fun getItemCount(): Int {
        return source?.size()!!
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataNote: DataNote?) {
            val textView = itemView.findViewById<TextView?>(R.id.data_note_name)
            textView.text = dataNote?.getName()
            itemView.setOnClickListener {
                openDescriptionFragment?.openDescription(
                    dataNote
                )
            }
            itemView.setOnLongClickListener {
                setPosition(layoutPosition)
                itemView.showContextMenu(500f, 100f)
                true
            }
        }
    }

    interface OpenDescriptionFragment {
        fun openDescription(dataNote: DataNote?)
    }
}