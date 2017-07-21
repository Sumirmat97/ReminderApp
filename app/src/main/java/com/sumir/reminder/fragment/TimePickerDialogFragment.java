package com.sumir.reminder.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Sumir on 31-05-2017.
 */

public class TimePickerDialogFragment extends DialogFragment {

    static final String HOUR = "hour";
    static final String MINS = "mins";

    public static TimePickerDialogFragment newInstance(Calendar time)
    {
        TimePickerDialogFragment fragment = new TimePickerDialogFragment();

        Bundle args = new Bundle();
        args.putInt(HOUR,time.get(Calendar.HOUR_OF_DAY));
        args.putInt(MINS,time.get(Calendar.MINUTE));

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        TimePickerDialog.OnTimeSetListener listener = (TimePickerDialog.OnTimeSetListener)getFragmentManager()
                                                        .findFragmentByTag(TaskEditFragment.DEFAULT_FRAGMENT_TAG);

        Bundle args = getArguments();
        return new TimePickerDialog(getActivity(),listener,args.getInt(HOUR),args.getInt(MINS),false);
    }
}
