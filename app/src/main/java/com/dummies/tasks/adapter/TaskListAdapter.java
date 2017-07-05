package com.dummies.tasks.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dummies.tasks.activity.R;
import com.dummies.tasks.fragment.TaskListFragment;
import com.dummies.tasks.interfaces.OnDeleteTask;
import com.dummies.tasks.interfaces.OnEditTask;
import com.dummies.tasks.provider.TaskProvider;
import com.squareup.picasso.Picasso;

/**
 * Created by Sumir on 26-05-2017.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder>
{
    Cursor cursor;
    int titleColumnIndex;
    int notesColumnIndex;
    int idColumnIndex;


    public void swapCursor(Cursor c)
    {
        cursor = c;
        if(cursor != null) {
            cursor.moveToFirst();
            titleColumnIndex = cursor.getColumnIndex(TaskProvider.COLUMN_TITLE);
            notesColumnIndex = cursor.getColumnIndex(TaskProvider.COLUMN_NOTES);
            idColumnIndex = cursor.getColumnIndex(TaskProvider.COLUMN_TASKID);
        }

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_task,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position)
    {
        final Context context = viewHolder.titleView.getContext();
        final long id = getItemId(position);

        cursor.moveToPosition(position);
        viewHolder.titleView.setText(cursor.getString(titleColumnIndex));
        viewHolder.notesView.setText(cursor.getString(notesColumnIndex));

        if(TaskListFragment.internetPresent)
            Picasso.with(context).load(getImageUrlForTask(id)).into(viewHolder.imageView);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ((OnEditTask) context).editTask(id);
            }
        });

        viewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener(

        ) {
            @Override
            public boolean onLongClick(View view) {
                ((OnDeleteTask)context).showDialog(viewHolder.titleView.getText().toString(),id);
                return true;
            }
        });
    }


    @Override
    public long getItemId(int position)
    {
        cursor.moveToPosition(position);
        return cursor.getLong(idColumnIndex);
    }

    public static String getImageUrlForTask(long taskId) {
        return "http://lorempixel.com/600/400/nature/?fakeId=" + taskId;
    }

    @Override
    public int getItemCount() {
         return cursor!=null ? cursor.getCount() : 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView titleView;
        TextView notesView;
        ImageView imageView;

        public ViewHolder(CardView card)
        {
            super(card);
            cardView = card;
            titleView = (TextView)card.findViewById(R.id.text1);
            notesView = (TextView)card.findViewById(R.id.text2);
            imageView = (ImageView)card.findViewById(R.id.image);
        }
    }
}
