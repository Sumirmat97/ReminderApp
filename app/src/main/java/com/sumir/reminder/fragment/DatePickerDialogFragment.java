package com.sumir.reminder.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Sumir on 31-05-2017.
 */

public class DatePickerDialogFragment extends DialogFragment {

    static final String YEAR = "year";
    static final String MONTH = "month";
    static final String DAY = "day";

    public static DatePickerDialogFragment newInstance(Calendar date){
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt(YEAR,date.get(Calendar.YEAR));
        args.putInt(MONTH,date.get(Calendar.MONTH));
        args.putInt(DAY,date.get(Calendar.DAY_OF_MONTH));

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        DatePickerDialog.OnDateSetListener callback = (DatePickerDialog.OnDateSetListener)getFragmentManager()
                                                        .findFragmentByTag(TaskEditFragment.DEFAULT_FRAGMENT_TAG);
        Bundle args = getArguments();
        return new DatePickerDialog(getActivity(),callback,args.getInt(YEAR),args.getInt(MONTH),args.getInt(DAY));

    }


    public int show(android.support.v4.app.FragmentTransaction fragmentTransaction, String string)
    {
        int retValue = super.show(fragmentTransaction,string);
        return retValue;
    }

}

