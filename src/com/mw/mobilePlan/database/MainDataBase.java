package com.mw.mobilePlan.database;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mw.mobilePlan.Constants;

public class MainDataBase extends SQLiteOpenHelper
{

	public MainDataBase(Context context)
	{
		super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// SQL statement to create Call_Log table AS ID, mobile_number,
		// Provider, Call_Time, Duration,Call_Type Store_Date
		String CREATE_BOOK_TABLE = "Create Table " + Constants.CALL_LOG_TABLE_NAME
				+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT,mobile_number TEXT  NOT NULL,Provider TEXT  NOT NULL,Call_Time TEXT  NOT NULL,Duration INTEGER  NOT NULL ,Call_Type TEXT NOT NULL,Store_Date TEXT DEFAULT 0";

		// create books table
		db.execSQL(CREATE_BOOK_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub

	}

	public void addMobileRecord(String mobileNumber, String provider, Date callTime, int duration, String callType)
	{
		SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("mobile_number", mobileNumber);
		values.put("Provider", provider);
		values.put("Call_Time", fmtOut.format(callTime));
		values.put("Duration", duration);
		values.put("Call_Type", callType);
		values.put("Store_Date", fmtOut.format(new Date()));
		db.insert(Constants.CALL_LOG_TABLE_NAME, null, values);
	}
}
