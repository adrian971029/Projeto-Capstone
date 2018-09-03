package com.adrian_971029.infobook.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.adrian_971029.infobook.DetailsActivity;
import com.adrian_971029.infobook.R;
import com.adrian_971029.infobook.model.Volume;
import com.adrian_971029.infobook.model.VolumeInfo;
import com.adrian_971029.infobook.task.WidgetTask;
import com.adrian_971029.infobook.utils.CategoryHelper;
import com.adrian_971029.infobook.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of App Widget functionality.
 */
public class InfoBookWidgetProvider extends AppWidgetProvider {

    private static final String TAG = InfoBookWidgetProvider.class.getSimpleName();
    private WidgetTask mTask;

    @Override
    public void onReceive(Context context, Intent widgetIntent) {
        final String action = widgetIntent.getAction();
        Log.d(TAG, context.getString(R.string.action_received) + action);
        super.onReceive(context, widgetIntent);
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        Log.d(TAG, context.getString(R.string.updating_app_widget));
        // To prevent any ANR timeouts, we perform the update in a Retrofit style or create another service

        mTask = new WidgetTask(context,appWidgetManager,appWidgetIds);
        mTask.execute();

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

