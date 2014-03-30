package com.mw.mobilePlan;

import java.util.ArrayList;
import java.util.List;

public class Constants {
	// Database Version
	public static final int DATABASE_VERSION = 1;
	// Database Name
	public static final String DATABASE_NAME = "MobilePlan";
	// Call_Log Table Name
	public static final String CALL_LOG_TABLE_NAME="Call_Log";

	public static List<String> getVodafonePreFix() {
		List<String> vodafonePreFix=new ArrayList<String>();
		vodafonePreFix.add("010");
		vodafonePreFix.add("+2010");
		vodafonePreFix.add("002010");
		return vodafonePreFix;
	}
	public static List<String> getMobinilPreFix() {
		List<String> mobinilPreFix=new ArrayList<String>();
		mobinilPreFix.add("012");
		mobinilPreFix.add("+2012");
		mobinilPreFix.add("002012");
		return mobinilPreFix;
	}
	public static List<String> getEtisalatPreFix() {
		List<String> EtisalatPreFix=new ArrayList<String>();
		EtisalatPreFix.add("011");
		EtisalatPreFix.add("+2011");
		EtisalatPreFix.add("002011");
		return EtisalatPreFix;
	}
	public static List<String> getLandLinePreFix() {
		List<String> landLinePreFix=new ArrayList<String>();
		landLinePreFix.add("0020");
		return landLinePreFix;
	}
}
