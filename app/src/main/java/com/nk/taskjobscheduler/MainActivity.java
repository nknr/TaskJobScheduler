package com.nk.taskjobscheduler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleJob();
            }
        });
    }


    private void scheduleJob() {


        final JobScheduler jobScheduler;
        jobScheduler = (JobScheduler) getSystemService(
                Context.JOB_SCHEDULER_SERVICE);

        // The JobService that we want to run
        final ComponentName name = new ComponentName(this, MyJobService.class);

        // Schedule the job
        final int result = jobScheduler.schedule(getJobInfo(123, 1, name));

        // If successfully scheduled, log this thing
        if (result == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Scheduled job successfully!");
        }

    }


    private JobInfo getJobInfo(final int id, final long hour, final ComponentName name) {
        final long interval = 6000;//TimeUnit.HOURS.toMillis(hour); // run every hour
        final boolean isPersistent = true; // persist through boot
        final int networkType = JobInfo.NETWORK_TYPE_ANY; // Requires some sort of connectivity

        final JobInfo jobInfo;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobInfo = new JobInfo.Builder(id, name)
                    .setMinimumLatency(interval)
                    //.setRequiredNetworkType(networkType)
                    .setPersisted(isPersistent)
                    .build();
        } else {
            jobInfo = new JobInfo.Builder(id, name)
                    .setPeriodic(interval)
                   // .setRequiredNetworkType(networkType)
                    .setPersisted(isPersistent)
                    .build();
        }

        return jobInfo;
    }
}