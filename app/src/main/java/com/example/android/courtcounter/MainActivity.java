package com.example.android.courtcounter;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.os.Handler;
import android.os.SystemClock;


public class MainActivity extends Activity implements OnClickListener {

    private String[] listOfObject;
    private TypedArray images;
    private ImageView itemImage;
    private String[] listOfObjectB;
    private TypedArray imagesB;
    private ImageView itemImageB;
    Button start, pause, Reset;
    TextView time;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    boolean stopTimer = false;
    int scoreTeamA = 0;
    int scoreTeamB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.startButton);
        pause = (Button) findViewById(R.id.pauseButton);
        Reset = (Button) findViewById(R.id.Reset);
        time = (TextView) findViewById(R.id.timerValue);
        start.setOnClickListener((OnClickListener) this);
        pause.setOnClickListener((OnClickListener) this);

        listOfObject = getResources().getStringArray(R.array.World_cup_countries);
        images = getResources().obtainTypedArray(R.array.World_Cup_Flags);
        itemImage = (ImageView) findViewById(R.id.team_a_flag);

        final Spinner Team_A_Flag = (Spinner) findViewById(R.id.Choose_Your_Team);
        ArrayAdapter<String> team_A_Selection = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfObject);
        Team_A_Flag.setAdapter(team_A_Selection);
        team_A_Selection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Team_A_Flag.setAdapter(team_A_Selection);
        Team_A_Flag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemImage.setImageResource(images.getResourceId(Team_A_Flag.getSelectedItemPosition(), +1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listOfObjectB = getResources().getStringArray(R.array.World_cup_countriesB);
        imagesB = getResources().obtainTypedArray(R.array.World_Cup_FlagsB);
        itemImageB = (ImageView) findViewById(R.id.team_b_flag);

        final Spinner Team_B_Flag = (Spinner) findViewById(R.id.Choose_Your_Team_B);
        ArrayAdapter<String> team_b_Selection = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfObject);
        Team_B_Flag.setAdapter(team_b_Selection);
        team_b_Selection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Team_B_Flag.setAdapter(team_b_Selection);
        Team_B_Flag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemImageB.setImageResource(images.getResourceId(Team_B_Flag.getSelectedItemPosition(), +1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.startButton:
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
                break;

            case R.id.pauseButton:
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
                break;
        }
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            String localtime = "" + mins + ":" + String.format("%02d", secs)
                    + ":" + String.format("%03d", milliseconds);
            time.setText(localtime);
            if (!stopTimer)
                customHandler.postDelayed(this, 0);
        }
    };

    /**
     * Calculating Team Scores
     */
    public void GoalA(View v) {
        scoreTeamA = scoreTeamA + 1;
        displayForTeamA(scoreTeamA);
    }

    public void GoalB(View v) {
        scoreTeamB = scoreTeamB + 1;
        displayForTeamB(scoreTeamB);
    }

    public void Reset(View V) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);

    }

    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the given score for Team B.
     */
    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }
}




