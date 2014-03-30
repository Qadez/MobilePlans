package com.mw.mobilePlan;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView vodafoneValue = (TextView) findViewById(R.id.vodafoneValue);
		TextView mobinilValue = (TextView) findViewById(R.id.mobinilValue);
		TextView etisalatValue = (TextView) findViewById(R.id.etisalatValue);
		TextView othersValue = (TextView) findViewById(R.id.othrsValue);
		String[] providersValues = getCallDetails();
		vodafoneValue.setText(providersValues[0]);
		mobinilValue.setText(providersValues[1]);
		etisalatValue.setText(providersValues[2]);
		othersValue.setText(providersValues[3]);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private String[] getCallDetails()
	{

		StringBuffer sb = new StringBuffer();
		// Cursor managedCursor = getContentResolver().query(
		// CallLog.Calls.CONTENT_URI, null, null, null, null);
		// Comment To Test Sync
		Calendar cal = Calendar.getInstance();
		cal.set(2014, Calendar.FEBRUARY, 12, 12, 0, 0);

		Date afterDate = cal.getTime();
		cal.set(2014, Calendar.MARCH, 12, 19, 0, 0);
		Date beforeDate = cal.getTime();

		final String SELECT = CallLog.Calls.DATE + ">?" + " AND " + CallLog.Calls.DATE + "<?";

		Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, new String[] { CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.TYPE, CallLog.Calls.NUMBER, CallLog.Calls._ID }, SELECT,
				new String[] { String.valueOf(afterDate.getTime()), String.valueOf(beforeDate.getTime()) }, CallLog.Calls.DATE + " desc");

		int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
		int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
		int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
		int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
		sb.append("Call Details :");
		List<String> vodafone = Constants.getVodafonePreFix();
		List<String> mobinil = Constants.getMobinilPreFix();
		List<String> etislat = Constants.getEtisalatPreFix();
		int vodafoneValue = 0;
		int mobinilValue = 0;
		int etislatValue = 0;
		int othersValue = 0;
		while(managedCursor.moveToNext())
		{
			String phNumber = managedCursor.getString(number);
			String callType = managedCursor.getString(type);
			String callDate = managedCursor.getString(date);
			Date callDayTime = new Date(Long.valueOf(callDate));
			String callDuration = managedCursor.getString(duration);
			String dir = null;
			int dircode = Integer.parseInt(callType);
			switch(dircode)
			{
			case CallLog.Calls.OUTGOING_TYPE:
				dir = "OUTGOING";
				break;

			case CallLog.Calls.INCOMING_TYPE:
				dir = "INCOMING";
				break;

			case CallLog.Calls.MISSED_TYPE:
				dir = "MISSED";
				break;
			}

			sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- " + dir + " \nCall Date:--- " + callDayTime + " \nCall duration in sec :--- " + callDuration);
			sb.append("\n----------------------------------");
			if(dir != null && dir.equals("OUTGOING"))
			{
				if(CheckNumber(vodafone, phNumber))
				{
					vodafoneValue = (int) (vodafoneValue + Double.parseDouble(getMinutesFromSecounds(callDuration)));
				}
				else if(CheckNumber(etislat, phNumber))
				{
					etislatValue = (int) (etislatValue + Double.parseDouble(getMinutesFromSecounds(callDuration)));
				}
				else if(CheckNumber(mobinil, phNumber))
				{
					mobinilValue = (int) (mobinilValue + Double.parseDouble(getMinutesFromSecounds(callDuration)));
				}
				else
				{
					System.out.println("Others Providers : " + phNumber);
					othersValue = (int) (othersValue + Double.parseDouble(getMinutesFromSecounds(callDuration)));
				}
			}

		}
		managedCursor.close();
		String[] allProviders = new String[] { vodafoneValue + "", mobinilValue + "", etislatValue + "", othersValue + "" };
		return allProviders;

	}

	public static boolean CheckNumber(List<String> providerPrefix, String number)
	{
		for(String prefix : providerPrefix)
		{
			if(number.startsWith(prefix))
				return true;
		}
		return false;
	}

	public static String getMinutesFromSecounds(String secounds)
	{
		double currentValue = Double.parseDouble(secounds);
		currentValue = currentValue / 60;
		double afterDecmil = currentValue - Math.floor(currentValue);
		if(afterDecmil > 0.0000)
		{
			currentValue = currentValue + 1;
		}
		return currentValue + "";
	}
}
