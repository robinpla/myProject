package robinpetersson.ticker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;



//Ways to describe FREQUENZY:
//Every 1, 2, 3, ... minutes, hours, days, weeks, months, years
//Every fractions of a whole - 1/10, 1/9, 1/8, 1/7, ..., 1/2
//Times per minute, hours, day, week, month, and year
//On specific times, dates, days of week,
//Random minimum to maximum range (ex. every 5 days to 8 days)

//Snap due dates to odd, even, Monday-Sunday,
//On the edit screen it should tell you date and time of several future due dates

//When task is marked at complete, option is to set new due date from completion time or due date.
//Tasks should be able to be grouped together - Water: Front Door,
//You should be able to postpone a task by certain times (1 day, 1 week, etc)
//Sequential tasks - The task description changes through a sequence as each task in the sequence is completed
//Expiration?
//When to notify:
//How to notify:



public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    private ArrayList<Integer>  mTaskId   = new ArrayList<>();
    private ArrayList<String >  mTaskName = new ArrayList<>();
    private ArrayList<Calendar> taskDue   = new ArrayList<>();
    private ArrayList<String>   timeLeft  = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "TICKERDEBUG onCreate started");

        ImageView img = (ImageView) findViewById(R.id.addTaskButton);
        img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("CLICK: ", "CLICK");
                Intent intent = new Intent(v.getContext(), editTask.class); //this , editTask.class
                intent.putExtra("task_id", "0");
                startActivity(intent);
            }
        });

        initImageBitmaps();

    }

    private void initImageBitmaps(){
        Log.d(TAG, "TICKERDEBUG initImageBitmap started");

        //Temporary variable to handle date and time
        Calendar dateTime = Calendar.getInstance();;

        //Current date and time
        Calendar currentDateTime = Calendar.getInstance();
        Log.i("TICKERVALUE currentDateTime", Long.toString(currentDateTime.getTimeInMillis()));

        try {
            SQLiteDatabase tickerDatabase = this.openOrCreateDatabase("tasksDatabase", MODE_PRIVATE, null);

            tickerDatabase.execSQL("DROP TABLE IF EXISTS tasks");

            tickerDatabase.execSQL("CREATE TABLE IF NOT EXISTS tasks (taskid INT, tasktitle VARCHAR, taskdue INT)");

            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (1, 'Clean Pond',              1550304328)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (2, 'Replace Filter',          1569305428)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (3, 'Whiten Teeth',            1579306528)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (4, 'Tune Piano',              1589307628)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (5, 'Check earthquake kits',   1550304328)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (6, 'Fertilize Lawn',          1649305428)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (7, 'Weed Garden',             1579306528)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (8, 'Maintain Kitchen Frains', 1599307628)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (9, 'Clean Pond',              1550314328)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (10, 'Replace Filter',         1550324328)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (11, 'Whiten Teeth',           1550334328)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (12, 'Tune Piano',             1550344328)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (13, 'Check earthquake kits',  1550354328)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (14, 'Fertilize Lawn',         1550364328)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (15, 'Weed Garden',            1550374328)");
            tickerDatabase.execSQL("INSERT INTO tasks (taskid, tasktitle, taskdue) VALUES (16, 'Maintain Kitchen Frains',1550384328)");


            Cursor c = tickerDatabase.rawQuery("SELECT * FROM tasks", null);

            int taskidindex = c.getColumnIndex("taskid");
            int tasktitleIndex = c.getColumnIndex("tasktitle");
            int taskdueIndex = c.getColumnIndex("taskdue");

            c.moveToFirst();
            while (c!=null)
            {
                Log.i("TICKERSQL taskid", Integer.toString(c.getInt(taskidindex)));
                Log.i("TICKERSQL tasktitle", c.getString(tasktitleIndex));
                Log.i("TICKERSQL duedate", Integer.toString(c.getInt(taskdueIndex)));

                mTaskId.add(c.getInt(taskidindex));
                mTaskName.add(c.getString(tasktitleIndex));
                Log.i("MTASKNAME", mTaskName.get(mTaskName.size()-1));

                dateTime.setTimeInMillis(c.getInt(taskdueIndex) * 1000l);
                taskDue.add(dateTime);

                tickerFunction test = new tickerFunction();
                timeLeft.add(test.timeDifference(currentDateTime.getTimeInMillis(), dateTime.getTimeInMillis()));

                c.moveToNext();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        initRecyclerView();




    }

    private void initRecyclerView(){
        Log.d(TAG, "TICKERDEBUG initRecyclerView started");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Log.d("CHECK: ", mTaskName.get(0));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mTaskId, mTaskName, timeLeft);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
