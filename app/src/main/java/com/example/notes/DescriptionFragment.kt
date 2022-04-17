package com.example.notes

import android.os.Bundle
import android.widget.TextView
import android.widget.EditText
import android.app.DatePickerDialog.OnDateSetListener
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.text.format.DateFormat
import android.view.*
import android.widget.Button
import androidx.core.view.get
import androidx.fragment.app.Fragment
import java.util.*

class DescriptionFragment : Fragment() {
    var dataActions: DataActions? = null
    private var editName: EditText? = null
    private var editDescription: EditText? = null
    private var dateTextField: TextView? = null
    private var save: Button? = null
    private var mParam1: DataNote? = null
    private var mParam2: String? = null
    var dateAndTime: Calendar = Calendar.getInstance(Locale.getDefault())
    var d: OnDateSetListener? = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        dateAndTime[year, monthOfYear] = dayOfMonth
        dateAndTime[Calendar.MONTH] = monthOfYear
        dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth
        val df = DateFormat()
        val dateText = DateFormat.format("dd-MM-yyyy", dateAndTime.time).toString()
        dateTextField?.text = dateText
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (arguments != null) {
            mParam1 = arguments?.getParcelable(ARG_ITEM)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val item: MenuItem = menu.findItem(R.id.action_clear)
        item.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editName = view.findViewById(R.id.name_edit_text)
        editDescription = view.findViewById(R.id.description_edit_text)
        dateTextField = view.findViewById(R.id.date_text_view)
        dateTextField?.setOnClickListener {
            context?.let {
                DatePickerDialog(
                    it, d,
                    dateAndTime[Calendar.YEAR],
                    dateAndTime[Calendar.MONTH],
                    dateAndTime[Calendar.DAY_OF_MONTH]
                )
                    .show()
            }
        }
        save = view.findViewById(R.id.btn_save)
        save?.setOnClickListener {
            if (dataActions != null) {
                val data = DataNote(
                    mParam1?.getId(),
                    editName?.text.toString(),
                    editDescription?.text.toString(),
                    dateTextField?.text.toString()
                )
                dataActions?.updateElement(data)
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.remove(this)
                    ?.commit()
            }
        }
        editName?.setText(mParam1?.getName())
        editDescription?.setText(mParam1?.getDescription())
        dateTextField?.text = mParam1?.getDate()
    }

    companion object {
        private val ARG_ITEM: String? = "param1"
        private val ARG_PARAM2: String? = "param2"
        fun newInstance(dataNote1: DataNote?): DescriptionFragment? {
            val fragment = DescriptionFragment()
            val args = Bundle()
            args.putParcelable(ARG_ITEM, dataNote1)
            fragment.arguments = args
            return fragment
        }
    }
}