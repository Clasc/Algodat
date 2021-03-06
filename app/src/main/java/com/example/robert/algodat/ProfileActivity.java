package com.example.robert.algodat;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robert.algodat.R;
import com.example.robert.algodat.backend.controller.LevelSystem;
import com.example.robert.algodat.main_menu.BaseActivity;

public class ProfileActivity extends BaseActivity {

    private ProgressBar progressbarExams;
    private ProgressBar progressbarPractice;
    private ProgressBar levelProgress;
    int examStatus=0;
    int practiceStatus=0;
    private Handler handler= new Handler();
    private LevelSystem levelSystem;
    private TextView levelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        levelSystem= new LevelSystem(this);
        if(levelSystem.finishesLearn(80)){
            Toast toast= Toast.makeText(this,"Level Up!",Toast.LENGTH_LONG);
            toast.show();
        }
        toolbar.setTitle(levelSystem.getUserName());
        setSupportActionBar(toolbar);
        levelText=(TextView) findViewById(R.id.lvlValue);
        String lvl= String.format("%s",levelSystem.getLevel());
        levelText.setText(lvl);
       // System.out.println(levelSystem.getUserName());
        /*levelSystem.finishesPractice(120);
        levelSystem.winsExam(3000);*/

        System.out.println("Finished Chapters: "+levelSystem.learnedChapters());
        System.out.println("Level: "+levelSystem.getLevel());
        System.out.println("Next level: "+levelSystem.getNextLevel());
        System.out.println("All Xps: "+levelSystem.getSumXp());
        System.out.println("Old Level Xp: "+levelSystem.getOldLvlXp());
        System.out.println("Progress: "+(levelSystem.getSumXp()-levelSystem.getOldLvlXp()));
        System.out.println("Max "+(levelSystem.getNextLevel()+levelSystem.getSumXp()-levelSystem.getOldLvlXp()));

        levelProgress=(ProgressBar) findViewById(R.id.LevelProgress);
        progressbarExams = (ProgressBar) findViewById(R.id.progressExams);
        progressbarPractice = (ProgressBar) findViewById(R.id.progressPractice);
        levelProgress.setMax(levelSystem.getNextLevel()+levelSystem.getSumXp()-levelSystem.getOldLvlXp());
        levelProgress.setProgress(levelSystem.getSumXp()-levelSystem.getOldLvlXp());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        //Up button
        super.onBackPressed();

        return true;
    }
     @Override
     protected void onPostCreate(Bundle savedInstanceState) {
         super.onPostCreate(savedInstanceState);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        levelSystem.save();
    }
}
