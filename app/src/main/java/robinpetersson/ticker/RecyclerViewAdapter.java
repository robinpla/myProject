package robinpetersson.ticker;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.media.CamcorderProfile.get;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private static final String TAG = "RecyclerViewAdapter";

    private Context mContext;
    private ArrayList<Integer> mTaskId   = new ArrayList<>();
    private ArrayList<String>  mTaskName = new ArrayList<>();
    private ArrayList<String>  mTaskDue  = new ArrayList<>();

    public RecyclerViewAdapter(Context context, ArrayList<Integer> taskId, ArrayList<String> taskName, ArrayList<String> taskDue)
    {
        //Log.d("RVA taskName", mTaskName.get(0));
        mContext = context; mTaskName = taskName; mTaskDue = taskDue; mTaskId = taskId;
        //Log.d("RVA mTaskName", mTaskName.get(0));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.dueTime.setText(mTaskDue.get(position));
        holder.taskName.setText(mTaskName.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, editTask.class);
                intent.putExtra("task_id", Integer.toString(mTaskId.get(position)));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mTaskId.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView taskName;
        TextView dueTime;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskDescriptionTextView);
            dueTime = itemView.findViewById(R.id.dueTimeTextView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

