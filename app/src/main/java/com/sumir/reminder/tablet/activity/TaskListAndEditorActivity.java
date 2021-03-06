package com.sumir.reminder.tablet.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sumir.reminder.activity.R;
import com.sumir.reminder.fragment.AlertDialogFragment;
import com.sumir.reminder.fragment.DeleteDialogFragment;
import com.sumir.reminder.fragment.TaskEditFragment;
import com.sumir.reminder.interfaces.OnDeleteTask;
import com.sumir.reminder.interfaces.OnEditFinished;
import com.sumir.reminder.interfaces.OnEditTask;
import com.sumir.reminder.interfaces.onSaveTask;

/**
 * Created by Sumir on 07-06-2017.
 */

public class TaskListAndEditorActivity extends AppCompatActivity implements onSaveTask,OnEditTask,OnEditFinished,OnDeleteTask {

    TaskEditFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_and_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTablet);
        setSupportActionBar(toolbar);
    }
    @Override
    public void editTask(long id) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentByTag(TaskEditFragment.DEFAULT_FRAGMENT_TAG);
        if(fragment != null) {
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }

        fragment = TaskEditFragment.newInstance(id);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.edit_container, fragment, TaskEditFragment.DEFAULT_FRAGMENT_TAG);

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

    }

    @Override
    public void finishEditingTask() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DialogFragment newFragment = new AlertDialogFragment();
        newFragment.show(fragmentTransaction,"saveDialog");
  }

    @Override
    public void showDialog(String message, long id) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DialogFragment newFragment = DeleteDialogFragment.newInstance(message,id);
        newFragment.show(fragmentTransaction,"deleteDialog");
    }

    @Override
    public void doSave() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = (TaskEditFragment) fragmentManager.findFragmentByTag(TaskEditFragment.DEFAULT_FRAGMENT_TAG);
        fragment.save();


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentByTag(TaskEditFragment.DEFAULT_FRAGMENT_TAG);
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }
}
