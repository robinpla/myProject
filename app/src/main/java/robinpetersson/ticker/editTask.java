package robinpetersson.ticker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class editTask extends AppCompatActivity {

    private ArrayList<Integer> mTaskId   = new ArrayList<>();
    private ArrayList<String >  mTaskName = new ArrayList<>();
    private ArrayList<Calendar> taskDue   = new ArrayList<>();
    private ArrayList<String>   timeLeft  = new ArrayList<>();


    private static final String TAG = "editTask";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittask);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();


        EditText et = (EditText)findViewById(R.id.tdet);

        et.setText("test");
        Log.d("ANOTHERTAG", et.getText().toString());
        et.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                //Log.d(TAG, "EditText field changed");
            }
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });


    }

    private void getIncomingIntent()
    {
        //Check all extras, like taskid, etc.
        if(getIntent().hasExtra("task_id"))
        {
            Log.d(TAG, "getIncomingIntent: found intent extras.");
            Calendar dateTime = Calendar.getInstance();;
            Calendar currentDateTime = Calendar.getInstance();

            Integer taskId = Integer.parseInt(getIntent().getStringExtra("task_id"));
            Log.d(TAG, "task_id:"+taskId);

            if (taskId!=0) {
                try {
                    SQLiteDatabase tickerDatabase = this.openOrCreateDatabase("tasksDatabase", MODE_PRIVATE, null);
                    Cursor c = tickerDatabase.rawQuery("SELECT * FROM tasks where TASKID=" + taskId, null);

                    int taskidindex = c.getColumnIndex("taskid");
                    int tasktitleIndex = c.getColumnIndex("tasktitle");
                    int taskdueIndex = c.getColumnIndex("taskdue");

                    c.moveToFirst();
                    while (c != null) {
                        mTaskId.add(c.getInt(taskidindex));
                        mTaskName.add(c.getString(tasktitleIndex));
                        dateTime.setTimeInMillis(c.getInt(taskdueIndex) * 1000l);
                        taskDue.add(dateTime);
                        tickerFunction test = new tickerFunction();
                        timeLeft.add(test.timeDifference(currentDateTime.getTimeInMillis(), dateTime.getTimeInMillis()));
                        c.moveToNext();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Log.d(TAG, "TASK45");
                mTaskId.add(45);
                Log.d(TAG, "TASK47");

                mTaskName.add("new task");
                timeLeft.add("no due date yet");

            }
            TextView taskDescription = findViewById(R.id.taskDescriptionTextView);
            TextView dueTime = findViewById(R.id.dueTextView);
            Log.d(TAG, "TASK46");
            taskDescription.setText(mTaskName.get(0));

            dueTime.setText("Due in " + timeLeft.get(0));
            Log.d(TAG, "TASK48");

        }
    }
}

