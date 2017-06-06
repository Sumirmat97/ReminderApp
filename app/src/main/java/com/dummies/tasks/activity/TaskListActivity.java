package com.dummies.tasks.activity;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dummies.tasks.fragment.DeleteDialogFragment;
import com.dummies.tasks.interfaces.OnDeleteTask;
import com.dummies.tasks.interfaces.OnEditTask;

public class TaskListActivity extends AppCompatActivity implements OnEditTask,OnDeleteTask{

    public static final String TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
    }

    @Override
    public void editTask(long id) {
        startActivity(new Intent(this,TaskEditActivity.class).putExtra(TaskEditActivity.EXTRA_TASKID,id));
    }

    @Override
    public void showDialog(String message,long id) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DialogFragment newFragment = DeleteDialogFragment.newInstance(message,id);
        newFragment.show(fragmentTransaction,"deleteDialog");
    }
}
