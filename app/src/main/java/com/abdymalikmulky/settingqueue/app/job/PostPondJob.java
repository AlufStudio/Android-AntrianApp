package com.abdymalikmulky.settingqueue.app.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import timber.log.Timber;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 5/3/17.
 */

public class PostPondJob extends Job{
    private long localId;
    private String text;

    public PostPondJob(String text) {
        super(new Params(Priority.HIGH).requireNetwork().persist().groupBy(Group.SAVE_POND));
        this.text= text;
        localId = System.currentTimeMillis();
    }

    @Override
    public void onAdded() {
        Timber.d("Added | %s - %s", text, localId);
    }

    @Override
    public void onRun() throws Throwable {
        Timber.d("Run | %s - %s", text, localId);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.d("Cancel | JobJob %s", String.valueOf(cancelReason));
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
