package com.sumir.reminder.activity;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sumir.reminder.fragment.AlertDialogFragment;
import com.sumir.reminder.fragment.TaskEditFragment;
import com.sumir.reminder.interfaces.OnEditFinished;
import com.sumir.reminder.interfaces.onSaveTask;


public class TaskEditActivity extends AppCompatActivity implements OnEditFinished,onSaveTask {
    public static final String EXTRA_TASKID = "taskId";
    TaskEditFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar2));

        long id =  getIntent().getLongExtra(TaskEditActivity.EXTRA_TASKID,0L);
        fragment = TaskEditFragment.newInstance(id);

        String fragmentTag = TaskEditFragment.DEFAULT_FRAGMENT_TAG;

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.container,fragment,fragmentTag).commit();
        }
    }

    @Override
    public void finishEditingTask() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DialogFragment newFragment = new AlertDialogFragment();
        newFragment.show(fragmentTransaction,"saveDialog");

    }

    @Override
    public void doSave() {
        fragment.save();
        this.finish();
    }
}
