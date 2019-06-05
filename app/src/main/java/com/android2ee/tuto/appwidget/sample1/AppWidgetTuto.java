/**<ul>
 * <li>AppWidgetTuto</li>
 * <li>com.android2ee.tuto.appwidget.sample1</li>
 * <li>4 avr. 2012</li>
 * 
 * <li>======================================================</li>
 *
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 *
 /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br> 
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 *  Belongs to <strong>Mathias Seguy</strong></br>
 ****************************************************************************************************************</br>
 * This code is free for any usage except training and can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * 
 * *****************************************************************************************************************</br>
 *  Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 *  Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br> 
 *  Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 *  <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */
package com.android2ee.tuto.appwidget.sample1;

import java.io.File;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

public class AppWidgetTuto extends AppWidgetProvider {
	/******************************************************************************************/
	/** SharedPreference Management Attributes ************************************************/
	/******************************************************************************************/

	/**
	 * The root file name of the preference file
	 */
	private static final String WIDGET_NAME = "AppWidgetTuto_";
	/**
	 * The SharedPreference key to store the number of call of onUpdate
	 */
	private static final String ON_UPDATE_CALLED_COUNTER = "onUpdateCalledCounter";
	/**
	 * The SharedPreference key to store the number of ForceUpdate Intents received
	 */
	private static final String ON_FORCE_UPDATE_CALLED_COUNTER = "forceUpdateCounter";
	/**
	 * The SharedPreference key to store the number of click
	 */
	private static final String ON_CLICK_DONE_COUNTER = "clickCounter";
	
	/******************************************************************************************/
	/** Alarm Management attributes ***********************************************************/
	/******************************************************************************************/

	/**
	 * The constant action to set the alarm
	 */
	private final int SET_ALARM = 1;
	/**
	 * The constant action to cancel the alarm
	 */
	private final int CANCEL_ALARM = 2;
	/******************************************************************************************/
	/** Others **************************************************************************/
	/******************************************************************************************/

	/**
	 * The string for the log
	 */
	private final static String TAG = "AppWidgetTuto";
	/**
	 * The constant to ask to rebuild all the instance of the widget
	 */
	private static final Integer ALL_WIDGETS = -1;

	/******************************************************************************************/
	/** Constructors **************************************************************************/
	/******************************************************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.appwidget.AppWidgetProvider#onUpdate(android.content.Context,
	 * android.appwidget.AppWidgetManager, int[])
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.e(TAG, "buildAppWidgetView context"+context+" appWidgetIds "+appWidgetIds.length);
		// build the View
		buildAppWidgetView(context, ALL_WIDGETS, ON_UPDATE_CALLED_COUNTER);
	}

	/******************************************************************************************/
	/** Building the view **************************************************************************/
	/******************************************************************************************/
	/**
	 * This method is called each time the view has to be build (the AppWidget is updated)
	 * 
	 * @param context
	 *            The context
	 * @param widgetId
	 *            The AppWidgetId of the element to update (use the ALL_WIDGETS to increment them
	 *            all)
	 * @param elementToIncrement
	 *            The element to increment can be
	 *            ON_UPDATE_CALLED_COUNTER,ON_FORCE_UPDATE_CALLED_COUNTER,CLICK_COUNTER depending on
	 *            which method called this
	 */
	private void buildAppWidgetView(Context context, int widgetId, String elementToIncrement) {
		Log.e(TAG, "buildAppWidgetView ");
		// retrieve the AppWidget Manager
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		// Find its componentName
		ComponentName componentName = new ComponentName(context, AppWidgetTuto.class);
		// And then retrieve the list of AppWidget instance id
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
		// The name of the preference file
		String myPreferences;
		// The SharedPreferences object
		SharedPreferences preferences;
		// The shared preference editor
		SharedPreferences.Editor editor;
		// the onUpdateCalled Counter
		int onUpdateCalledCounter;
		// the forceUpdate Counter
		int forceUpdateCounter;
		// the click Counter
		int clickCounter;
		// the element to increment
		int elementToIncrementCounter;
		// the number of AppWidget instance
		final int N = appWidgetIds.length;
		// The remote views to build and bind to the AppWidget
		RemoteViews views;
		// the id of the current AppWidget instance
		int appWidgetId;
		// Perform this loop procedure for each App Widget that belongs to this provider
		for (int i = 0; i < N; i++) {
			// Retrieve the id of the current instance of the AppWidget
			appWidgetId = appWidgetIds[i];

			// Be sure to update only the targeted AppWidget instance
			if ((widgetId == ALL_WIDGETS) || (widgetId == appWidgetId)) {

				// Managing preference
				// ---------------------
				// Build the name of the SharedPreference object associated to that instance :
				myPreferences = WIDGET_NAME + appWidgetId;
				// get the SharedPreference of the instance
				preferences = context.getSharedPreferences(myPreferences, Activity.MODE_PRIVATE);

				// Increment the element to increment
				elementToIncrementCounter = preferences.getInt(elementToIncrement, 0);
				// increment it
				elementToIncrementCounter++;
				// Get the SharedPreference Editor to store it
				editor = preferences.edit();
				// Update the variable elementToIncrement
				editor.putInt(elementToIncrement, elementToIncrementCounter);
				// And commit
				editor.commit();
				// Then Retrieve the variables
				// ON_UPDATE_CALLED_COUNTER,ON_FORCE_UPDATE_CALLED_COUNTER
				// and
				// CLICK_COUNTER
				onUpdateCalledCounter = preferences.getInt(ON_UPDATE_CALLED_COUNTER, 0);
				forceUpdateCounter = preferences.getInt(ON_FORCE_UPDATE_CALLED_COUNTER, 0);
				clickCounter = preferences.getInt(ON_CLICK_DONE_COUNTER, 0);
				// Managing the RemoteViews
				// ------------------------
				views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
				// To change the value of element within your RemoteViews, you need to call such
				// methods
				// on the RemoteViews where you specify what you do on which element
				// You can not directly retrieve an element
				// Set the text to the textView txtOnUpdateCount
				views.setTextViewText(R.id.txtOnUpdateCount,
						String.format(context.getString(R.string.onupdatecall), onUpdateCalledCounter));
				// Set the text to the textView txtForceUpdateCount
				views.setTextViewText(R.id.txtForceUpdateCount,
						String.format(context.getString(R.string.forceupdatecall), forceUpdateCounter));
				// Set the text to the textView txtOnUpdateCount
				views.setTextViewText(R.id.txtClickCount,
						String.format(context.getString(R.string.click), clickCounter));
				// Set the text to the textView textView (the title)
				views.setTextViewText(R.id.textView, String.format(context.getString(R.string.title), appWidgetId));

				// Add A Listener on the ImageView
				// --------------------------------
				// Define the Intent
				Log.e(TAG, "creation de intentClick1 : "+context.getResources().getString(R.string.INTENT_CLICK1));
				Intent intentClick1 = new Intent(context.getResources().getString(R.string.INTENT_CLICK1));
				// Add data within the Intent to know which AppWidget instance launched the Intent
				intentClick1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
				// Build the associated PendingIntent
				PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intentClick1, 0);
				// Bind the PendingIntent to the ImageView imageView
				views.setOnClickPendingIntent(R.id.imageView, pendingIntent);
				
//				String lat="42.0";
//				String longi="1.39";
//				Uri geo=Uri.parse("geo:"+lat+","+longi);
//				Intent intentClick2 = new Intent(Intent.ACTION_VIEW,geo);
//				PendingIntent pendingIntent2 = PendingIntent.getActivity(context, appWidgetId, intentClick2, 0);
//				views.setOnClickPendingIntent(R.id.imageView, pendingIntent2);
				// Tell the AppWidgetManager to perform an update on the current app widget
				appWidgetManager.updateAppWidget(appWidgetId, views);
			}
		}
	}

	/******************************************************************************************/
	/** Receiving Intents **************************************************************************/
	/******************************************************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.appwidget.AppWidgetProvider#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e(TAG, "onReceive "+intent.getAction());
		super.onReceive(context, intent);
		// Manage the Intent
		if (context.getResources().getString(R.string.INTENT_CLICK1).equals(intent.getAction())) {
			// A click has been done on the ImageView
			// Find the AppWidget instance id that Lauched the Intent
			int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, ALL_WIDGETS);
			// Rebuild the view
			buildAppWidgetView(context, appWidgetId, ON_CLICK_DONE_COUNTER);
		} else if (context.getResources().getString(R.string.INTENT_UPDATE).equals(intent.getAction())) {
			// Rebuild the view
			buildAppWidgetView(context, ALL_WIDGETS, ON_FORCE_UPDATE_CALLED_COUNTER);
		}
	}

	/******************************************************************************************/
	/** Alarm Management **************************************************************************/
	/******************************************************************************************/
	/**
	 * Set or Unset the Alarm that launch the ForceUpdate Intent
	 * 
	 * @param context
	 *            the current context
	 * @param action
	 *            The action to do CANCEL_ALARM or SET_ALARM (int constant)
	 */
	private void manageAlarm(Context context, int action) {
		// retrieve the AlarmManager
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		// Define its Intent
		Intent intent = new Intent(context.getResources().getString(R.string.INTENT_UPDATE));
		intent.setAction(context.getResources().getString(R.string.INTENT_UPDATE));
		// Define the associated PendingIntent
		PendingIntent pendingIntentUpdate = PendingIntent.getBroadcast(context, 0, intent, 0);
		// Do the action associated to the method call
		if (CANCEL_ALARM == action) {
			// cancel the alarm bind to that PendingIntent
			alarmManager.cancel(pendingIntentUpdate);
		} else if (SET_ALARM == action) {
			// Setting up the alarm with the mode "times don't matter when device asleep"
			// The first alarm will be llaunched in half a day
			// and will repeat each half a day
			// and will launch the PendingIntent define above
			alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, AlarmManager.INTERVAL_HALF_DAY,
					AlarmManager.INTERVAL_HALF_DAY, pendingIntentUpdate);
			//Or if you want to update it each 2s without waking up the device
//			alarmManager.setRepeating(AlarmManager.RTC, 5000,2000, pendingIntentUpdate);
			
		}
	}

	/******************************************************************************************/
	/** Life cycle management **************************************************************************/
	/******************************************************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.appwidget.AppWidgetProvider#onDeleted(android.content.Context, int[])
	 */
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// Called when the AppWidget instance listed in appWidgetIds are remove from the home screen
		// Delete the SharedPreference files associated to those instances
		Log.e(TAG, "onDeleted");
		super.onDeleted(context, appWidgetIds);

		// The name of the SharedPreference file
		String myPreferences;
		// The sharedPreference as a file
		File sharedPreferencefile, sharedPreferenceBackupfile;
		// The number of deleted widgets
		final int N = appWidgetIds.length;
		// The current id of the AppWidget instance
		int appWidgetId;
		// Destruction des fichiers SharedPreference associés à ces instances
		// Destroy all the SharedPreference file associted to tha AppWidget instance
		for (int i = 0; i < N; i++) {
			// get the current id of the AppWidget instance
			appWidgetId = appWidgetIds[i];
			// First flush the SharedPreference object
			// --------------------------------------
			// Find its name
			myPreferences = WIDGET_NAME + appWidgetId;
			// clear the SharedPreference file (logical clean up)
			context.getSharedPreferences(myPreferences, Activity.MODE_PRIVATE).edit().clear().commit();
			// Then delete the associated file (physical clean up)
			// ok first retrieve then
			sharedPreferencefile = new File("/data/data/" + context.getPackageName() + "/shared_prefs/" + myPreferences
					+ ".xml");
			sharedPreferenceBackupfile = new File("/data/data/" + context.getPackageName() + "/shared_prefs/"
					+ myPreferences + ".bak");
			// and then delete
			boolean isSharedPrefDeleted = sharedPreferencefile.delete();
			boolean isSharedPrefBckDeleted = sharedPreferenceBackupfile.delete();
			// And test the return
			if (isSharedPrefDeleted) {
				Log.e(TAG, "SharedPreferences :" + myPreferences + " succesfully deleted");
			} else {
				Log.e(TAG, "Failed: SharedPreferences :" + myPreferences + " not deleted");
			}
			if (isSharedPrefBckDeleted) {
				Log.e(TAG, "SharedPreferences Backup :" + myPreferences + " succesfully deleted");
			} else {
				Log.e(TAG, "Failed: SharedPreferences Backup :" + myPreferences + " not deleted");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.appwidget.AppWidgetProvider#onDisabled(android.content.Context)
	 */
	@Override
	public void onDisabled(Context context) {
		// Called when the last instance of the AppWidget has been remove from the home screen
		super.onDisabled(context);
		// disable the Alarm update
		manageAlarm(context, CANCEL_ALARM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.appwidget.AppWidgetProvider#onEnabled(android.content.Context)
	 */
	@Override
	public void onEnabled(Context context) {
		// Called when the first instance of the AppWidget has been installed on the home screen
		super.onEnabled(context);
		// setting up the Alarm
		manageAlarm(context, SET_ALARM);
	}

}