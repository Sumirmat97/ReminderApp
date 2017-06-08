package com.dummies.tasks.tablet.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.dummies.tasks.activity.R;
import com.dummies.tasks.fragment.AlertDialogFragment;
import com.dummies.tasks.fragment.DeleteDialogFragment;
import com.dummies.tasks.fragment.TaskEditFragment;
import com.dummies.tasks.interfaces.OnDeleteTask;
import com.dummies.tasks.interfaces.OnEditFinished;
import com.dummies.tasks.interfaces.OnEditTask;
import com.dummies.tasks.interfaces.onSaveTask;

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
        fragment = TaskEditFragment.newInstance(id);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.edit_container,fragment,TaskEditFragment.DEFAULT_FRAGMENT_TAG);

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
        fragment.save();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentByTag(TaskEditFragment.DEFAULT_FRAGMENT_TAG);
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }
}
