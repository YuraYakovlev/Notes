package com.example.notes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.DataNotesAdapter.OpenDescriptionFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class ListNotesFragment : Fragment(), OpenDescriptionFragment, UpdateDataListListener {
    var dataActions: DataActions? = null
    private var adapter: DataNotesAdapter? = null
    private var descriptionFragment: DescriptionFragment? = null
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_list_notes, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view_data_note_list)
        adapter = DataNotesAdapter(dataActions)
        registerForContextMenu(recyclerView)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> {
                dataActions?.clear()
                update()
                true
            }
            R.id.action_close -> {
                activity?.finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        dataActions = PreferencesDataActions(sharedPreferences)
        dataActions?.setUpdateListListener(this)
        adapter?.setDataNoteSource(dataActions)
        adapter?.openDescriptionFragment = this
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_data_note_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val decoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        Objects.requireNonNull(
            context?.let {
                AppCompatResources.getDrawable(
                    it, R.drawable.separator
                )
            }
        )?.let {
            decoration.setDrawable(
                it
            )
        }
        recyclerView.addItemDecoration(decoration)
        recyclerView.adapter = adapter
        descriptionFragment = DescriptionFragment()
        descriptionFragment!!.dataActions = dataActions
        val floatingButton: FloatingActionButton = view.findViewById(R.id.btn_new_note)
        floatingButton.setOnClickListener { v: View? ->
            val uuid = UUID.randomUUID().toString()
            (dataActions as PreferencesDataActions).addListElement(
                DataNote(
                    uuid,
                    resources.getString(R.string.notes_name),
                    "",
                    ""
                )
            )
            update()
        }
    }

    override fun openDescription(dataNote: DataNote?) {
        descriptionFragment = DescriptionFragment.Companion.newInstance(dataNote)
        descriptionFragment?.dataActions = dataActions
        descriptionFragment?.let {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, it)
                .addToBackStack(null)
                .commit()
        }
        fillElementList(dataNote)
    }

    private fun fillElementList(dataNote: DataNote?) {
        for (dataNote1 in dataActions?.getDataNoteList()!!) {
            if (dataNote?.getId() == dataNote1?.id) {
                dataNote1?.name = dataNote?.getName()
                dataNote1?.description = dataNote?.getDescription()
                dataNote1?.date = dataNote?.getDate()
            }
        }
    }

    private fun update() {
        dataActions?.fetchList()
        adapter?.setDataNoteSource(dataActions)
        adapter?.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.add(Menu.NONE, R.id.action_delete, 2, "Delete")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_delete) {
            if (adapter?.getPosition()!! >= 0) dataActions?.delete(adapter?.getPosition()!!)
        }
        adapter?.getPosition()?.let { adapter?.notifyItemRemoved(it) }
        return super.onContextItemSelected(item)
    }

    override fun updateListEvent() {
        update()
    }
}