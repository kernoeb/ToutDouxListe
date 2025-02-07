package com.dutinfo.boisnard.tp12;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.room.Room;

import com.dutinfo.boisnard.tp12.tasks.Task;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class HomeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "task").allowMainThreadQueries().build();
        ArrayList<Task> task = new ArrayList<>(db.taskDAO().getAll());

        CharSequence widgetText;
        CharSequence widgetText2;
        try {
            widgetText = task.get(task.size() - 1).getIntitule();
            widgetText2 = task.get(task.size() - 1).getDescription();
        } catch (Exception e) {
            widgetText = "Aucune tâche";
            widgetText2 = "";
        }
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.home_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText2);
        views.setTextViewText(R.id.appwidget_text2, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        try {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.home_widget);
            Intent configIntent = new Intent(context, MainActivity.class);
            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.relativeWidgetLayout, configPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        } catch (Exception e) {
            System.err.println("Erreur Widget");
        }

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
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

