package robinpetersson.ticker;

import android.util.Log;

public class tickerFunction {
    private static final String TAG = "MainActivity";


    public String timeDifference(Long x, Long y) {
        //x should be current date and time, y should be the date and time comparing to
        //feels like it could need some fail safe catches
        //a positive difference means a date in the future
        //a negative difference means a date in the past
        x = x / 1000;
        y = y / 1000;
        long difference = y - x;


        Log.d(TAG, "TICKERVALUE " + y + "-" + x + "=" + difference);

        float years = difference / 31536000f;
        if (years > 1.5) {
            return (String.valueOf(Math.round(years)) + " years");
        }
        if ((years > 0.962) && (years <= 1.5)) {
            return ("1 year");
        }

        float z = difference / 2628288f;
        if (z > 1.5) return (String.valueOf(Math.round(z)) + " months");

        z = difference / 604800f;
        if (z > 1.5) return (String.valueOf(Math.round(z)) + " weeks");

        z = difference / 86400f;
        if (z > 1.5) return (String.valueOf(Math.round(z)) + " days");

        z = difference / 3600f;
        if (z > 1.5) return (String.valueOf(Math.round(z)) + " hours");

        z = difference / 60f;
        return (String.valueOf(Math.round(z)) + " minutes");
        //Some kinds of fail test and beutification would be nice here
    }
}

