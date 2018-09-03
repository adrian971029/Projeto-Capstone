package com.adrian_971029.infobook.task;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
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
import com.adrian_971029.infobook.utils.CategoryHelper;
import com.adrian_971029.infobook.utils.Constants;
import com.adrian_971029.infobook.widget.InfoBookWidgetProvider;
import com.squareup.picasso.Picasso;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WidgetTask  extends AsyncTask<Void, Void, Void> {

    private static final String TAG = WidgetTask.class.getSimpleName();
    private static final String VOLUME_INFO = "volume_info";
    private static VolumeInfo volumeInfo;

    final Context context;
    final AppWidgetManager appWidgetManager;
    final int[] appWidgetIds;

    public WidgetTask(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        this.context = context;
        this.appWidgetManager = appWidgetManager;
        this.appWidgetIds = appWidgetIds;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Call<Volume> volumeResponseCall = CategoryHelper.defineCategoria(Constants.ADVENTURE);

        final Random r = new Random();

        volumeResponseCall.enqueue(new Callback<Volume>() {

            @Override
            public void onResponse(Call<Volume> call, Response<Volume> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, context.getString(R.string.error) + response.code());
                } else {
                    Volume volumeResponse = response.body();
                    if (volumeResponse != null) {
                        int pos = r.nextInt(volumeResponse.getItems().size());

                        final RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.info_book_widget_provider);
                        volumeInfo = new VolumeInfo();
                        volumeInfo = volumeResponse.getItems().get(pos).getVolumeInfo();
                        String images = "";

                        if (volumeInfo.getImageLinks() != null) {
                            if(volumeInfo.getImageLinks().getExtraLarge() != null) {
                                images = volumeInfo.getImageLinks().getExtraLarge();
                            } else if(volumeInfo.getImageLinks().getLarge() != null) {
                                images = volumeInfo.getImageLinks().getLarge();
                            } else if(volumeInfo.getImageLinks().getMedium() != null) {
                                images = volumeInfo.getImageLinks().getMedium();
                            } else if(volumeInfo.getImageLinks().getSmall() != null) {
                                images = volumeInfo.getImageLinks().getSmall();
                            } else if(volumeInfo.getImageLinks().getThumbnail() != null) {
                                images = volumeInfo.getImageLinks().getThumbnail();
                            } else {
                                images = volumeInfo.getImageLinks().getSmallThumbnail();
                            }
                        }

                        Picasso.with(context)
                                .load(images)
                                .into(updateViews, R.id.item_img_book,appWidgetIds);

                        updateViews.setTextViewText(R.id.item_tv_book_title, volumeInfo.getTitle());
                        updateViews.setTextViewText(R.id.item_tv_book_author, volumeInfo.getAuthors().get(0));

                        // Notify the widget that the list view needs to be updated.
                        final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
                        final ComponentName cn = new ComponentName(context, InfoBookWidgetProvider.class);
                        mgr.updateAppWidget(cn, updateViews);

                        for (int appWidgetId : appWidgetIds) {
                            Bundle extras = new Bundle();
                            extras.putParcelable(VOLUME_INFO, volumeInfo);
                            Intent intent = new Intent(context, DetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                            intent.putExtras(extras);
                            PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.info_book_widget_provider);
                            views.setOnClickPendingIntent(R.id.layout_item_book, pendingIntent);
                            appWidgetManager.updateAppWidget(appWidgetId, views);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<Volume> call, Throwable t) {
                Log.e(TAG, context.getString(R.string.error) + t.getMessage());
            }

        });
        return  null;
    }

}