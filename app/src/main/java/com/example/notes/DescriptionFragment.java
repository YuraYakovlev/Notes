package com.example.notes;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;


public class DescriptionFragment extends Fragment {

    private static final String ARG_ITEM = "param1";
    private static final String ARG_PARAM2 = "param2";
    public DataActions dataActions = new DataActionsImpl();
    private EditText editName;
    private EditText editDescription;
    private TextView dateTextField;
    private Button save;
    private DataNote mParam1;
    private String mParam2;
    private ListNotesFragment listNotesFragment = new ListNotesFragment();
    DataNote dataNote;


    Calendar dateAndTime = Calendar.getInstance(Locale.getDefault());
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(year, monthOfYear, dayOfMonth);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            String dateText = df.format("dd-MM-yyyy", dateAndTime.getTime()).toString();
            dateTextField.setText(dateText);
        }
    };

    public DescriptionFragment() {
        // Required empty public constructor
    }

    public static DescriptionFragment newInstance(DataNote dataNote1) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ITEM, dataNote1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_ITEM);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editName = view.findViewById(R.id.name_edit_text);
        editDescription = view.findViewById(R.id.description_edit_text);
        dateTextField = view.findViewById(R.id.date_text_view);


        dateTextField.setOnClickListener(view12 -> {
            new DatePickerDialog(getContext(), d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH))
                    .show();
        });

        save = view.findViewById(R.id.btn_save);
        save.setOnClickListener(view1 -> {


            if (dataActions != null) {

                DataNote data = new DataNote(mParam1.getId(), editName.getText().toString(),
                        editDescription.getText().toString(), dateTextField.getText().toString());
                dataActions.addListElement(data);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .remove(this)
                        .commit();
            }
        });

        editName.setText(mParam1.getName());
        editDescription.setText(mParam1.getDescription());
        dateTextField.setText(mParam1.getDate());

    }

}