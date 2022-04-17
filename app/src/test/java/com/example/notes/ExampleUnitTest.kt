package com.example.notes

import android.os.Parcelable
import android.os.Parcel
import android.os.Parcelable.Creator
import com.example.notes.DataNote
import com.example.notes.UpdateDataListListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notes.R
import com.example.notes.ListNotesFragment
import com.example.notes.DataActions
import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.DataNotesAdapter.MyViewHolder
import com.example.notes.DataNotesAdapter.OpenDescriptionFragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import android.view.View.OnLongClickListener
import com.example.notes.DataNotesAdapter
import com.example.notes.DescriptionFragment
import android.content.SharedPreferences
import android.view.MenuInflater
import com.example.notes.PreferencesDataActions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.widget.EditText
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.DatePicker
import android.app.DatePickerDialog
import com.google.gson.reflect.TypeToken
import com.google.gson.GsonBuilder
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, (2 + 2).toLong())
    }
}