/**
 * @author fazel araghi
 * Copyright (c) 2014, SAMiN project.
 */
package ir.saa.android.mt.components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PersianCalendar {

	public static String weekDay = "";
	public String monthName = "";

	int date;
	int month;
	int year;

	public PersianCalendar() {
		Date GregorianDate = new Date();
		calcPersianDate(GregorianDate);
	}

	public PersianCalendar(Date GregorianDate) {
		calcPersianDate(GregorianDate);
	}

	@SuppressWarnings("deprecation")
	private void calcPersianDate(Date GregorianDate) {
		int ld;

		int gregorianYear = GregorianDate.getYear() + 1900;
		int gregorianMonth = GregorianDate.getMonth() + 1;
		int gregorianDate = GregorianDate.getDate();
		int dayOfWeek = GregorianDate.getDay();

		int[] buf1 = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
		int[] buf2 = {0, 31, 60, 91, 121, 152, 182, 213, 144, 274, 305, 335};

		if ((gregorianYear % 4) != 0) {
			date = buf1[gregorianMonth - 1] + gregorianDate;

			if (date > 79) {
				date = date - 79;
				if (date <= 186) {
					switch (date % 31) {
					case 0:
						month = date / 31;
						date = 31;
						break;
					default:
						month = (date / 31) + 1;
						date = (date % 31);
						break;
					}
					year = gregorianYear - 621;
				} else {
					date = date - 186;

					switch (date % 30) {
					case 0:
						month = (date / 30) + 6;
						date = 30;
						break;
					default:
						month = (date / 30) + 7;
						date = (date % 30);
						break;
					}
					year = gregorianYear - 621;
				}
			} else {
				if ((gregorianYear > 1996) && (gregorianYear % 4) == 1) {
					ld = 11;
				} else {
					ld = 10;
				}
				date = date + ld;

				switch (date % 30) {
				case 0:
					month = (date / 30) + 9;
					date = 30;
					break;
				default:
					month = (date / 30) + 10;
					date = (date % 30);
					break;
				}
				year = gregorianYear - 622;
			}
		} else {
			date = buf2[gregorianMonth - 1] + gregorianDate;

			if (gregorianYear >= 1996) {
				ld = 79;
			} else {
				ld = 80;
			}
			if (date > ld) {
				date = date - ld;

				if (date <= 186) {
					switch (date % 31) {
					case 0:
						month = (date / 31);
						date = 31;
						break;
					default:
						month = (date / 31) + 1;
						date = (date % 31);
						break;
					}
					year = gregorianYear - 621;
				} else {
					date = date - 186;

					switch (date % 30) {
					case 0:
						month = (date / 30) + 6;
						date = 30;
						break;
					default:
						month = (date / 30) + 7;
						date = (date % 30);
						break;
					}
					year = gregorianYear - 621;
				}
			}

			else {
				date = date + 10;

				switch (date % 30) {
				case 0:
					month = (date / 30) + 9;
					date = 30;
					break;
				default:
					month = (date / 30) + 10;
					date = (date % 30);
					break;
				}
				year = gregorianYear - 622;
			}

		}

		switch (month) {
		case 1:
			monthName = "فروردین";
			break;
		case 2:
			monthName = "اردیبهشت";
			break;
		case 3:
			monthName = "خرداد";
			break;
		case 4:
			monthName = "تیر";
			break;
		case 5:
			monthName = "مرداد";
			break;
		case 6:
			monthName = "شهریور";
			break;
		case 7:
			monthName = "مهر";
			break;
		case 8:
			monthName = "آبان";
			break;
		case 9:
			monthName = "آذر";
			break;
		case 10:
			monthName = "دی";
			break;
		case 11:
			monthName = "بهمن";
			break;
		case 12:
			monthName = "اسفند";
			break;
		}

		switch (dayOfWeek) {

		case 0:
			weekDay = "یک شنبه";
			break;
		case 1:
			weekDay = "دوشنبه";
			break;
		case 2:
			weekDay = "سه شنبه";
			break;
		case 3:
			weekDay = "چهارشنبه";
			break;
		case 4:
			weekDay = "پنجشنبه";
			break;
		case 5:
			weekDay = "جمعه";
			break;
		case 6:
			weekDay = "شنبه";
			break;
		}
	}

	public static String getCurrentShamsiDate() {
		Locale loc = new Locale("en_US");
		//Utilities util = new Utilities();
		PersianCalendar perCal = new PersianCalendar();
		return String.valueOf(perCal.year) + "/" + String.format(loc, "%02d",
				perCal.month) + "/" + String.format(loc, "%02d", perCal.date);
	}
	public static String getCurrentGregorianDate() {
		Date now = new Date();
		String date = new SimpleDateFormat("yyyy/MM/dd").format(now);

		return date;
	}

	public static String getShamsiDate(Date gregorianDate) {
		Locale loc = new Locale("en_US");
		PersianCalendar perCal = new PersianCalendar(gregorianDate);

		return String.valueOf(perCal.year) + " - " + String.format(loc, "%02d",
				perCal.month) + " - " + String.format(loc, "%02d", perCal.date);	

	}

	public static String getShamsiDate(String gregorianDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		Date gregDate = null;
		try { // parse string date to date type
			gregDate = sdf.parse(gregorianDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Locale loc = new Locale("en_US");
		PersianCalendar perCal = new PersianCalendar(gregDate); 

		return String.valueOf(perCal.year) + " - " + String.format(loc, "%02d",
				perCal.month) + " - " + String.format(loc, "%02d", perCal.date);

	}

	public static String getSimpleShamsiDate(String gregorianDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		Date gregDate = null;
		try { // parse string date to date type
			gregDate = sdf.parse(gregorianDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Locale loc = new Locale("en_US");
		PersianCalendar perCal = new PersianCalendar(gregDate);

		return String.valueOf(perCal.year) + String.format(loc, "%02d",
				perCal.month) +  String.format(loc, "%02d", perCal.date);

	}


	public static String getSimpleShamsiDate6DigitGregorian(String gregorianDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd", Locale.US);
		Date gregDate = null;
		try { // parse string date to date type
			gregDate = sdf.parse(gregorianDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Locale loc = new Locale("en_US");
		PersianCalendar perCal = new PersianCalendar(gregDate);

		return String.valueOf(perCal.year) + "-" + String.format(loc, "%02d", perCal.month) + "-" +  String.format(loc, "%02d", perCal.date);

	}

	public static String getCurrentSimpleTime(){

		Calendar c= Calendar.getInstance();
		String hour= String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		if(hour.length()==1)  hour="0"+hour;
		String minute=String.valueOf( c.get(Calendar.MINUTE));
		if(minute.length()==1)  minute="0"+minute;
		String  second=String.valueOf( c.get(Calendar.SECOND));
		if(second.length()==1)  second="0"+second;

		//return String.format("%s%s%s", hour,minute,second);
		return String.format("%s%s", hour,minute);
	}

	public static String convertToPersianDateFormat(String DateStr){
		if (DateStr.length()==6){
			DateStr = String.format("13%s", DateStr);
		}

		if (DateStr.length()!=8){
			return "";
		}

		if(!DateStr.substring(4,5).equals("0") && !DateStr.substring(4,5).equals("1") && !DateStr.substring(4,5).equals("2")) {
			DateStr = DateStr.substring(0,4)+"0"+DateStr.substring(5,8);
		}

		return  String.format("%s-%s-%s", DateStr.substring(0, 4) , DateStr.substring(4, 6) , DateStr.substring(6, 8));
	}

	public static String convertToTimeFormat(String TimeStr){
		if (TimeStr.length()==4){
			TimeStr = String.format("%s00", TimeStr);
		}

		if (TimeStr.length()==5){
			TimeStr = String.format("0%s", TimeStr);
		}

		return  String.format("%s:%s:%s", TimeStr.substring(0, 2) , TimeStr.substring(2, 4) , TimeStr.substring(4, 6));
	}
	
	public static String getCurrentTime(){

		Calendar c= Calendar.getInstance();
		String hour= String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		if(hour.length()==1)  hour="0"+hour;
		String minute=String.valueOf( c.get(Calendar.MINUTE));
		if(minute.length()==1)  minute="0"+minute;
		String  second=String.valueOf( c.get(Calendar.SECOND));
		if(second.length()==1)  second="0"+second;

		return String.format("%s:%s:%s", hour,minute,second);

	}

	public static String getCurrentSimpleShamsiDate(){

		String date=PersianCalendar.getCurrentShamsiDate();

		String  year=date.substring(0, 4);
		String  month=date.substring(5, 7);
		String  day=date.substring(8, 10);

		return String.format("%s%s%s", year,month,day);
	}
	
	public static String getCurrentSimpleShamsiDate6Digit(){

		String date=PersianCalendar.getCurrentShamsiDate();

		String  year=date.substring(2, 4);
		String  month=date.substring(5, 7);
		String  day=date.substring(8, 10);

		return String.format("%s%s%s", year,month,day);
	}

	public static String[]  getShamsiDateFactors(){
		String date=getCurrentShamsiDate();

		String[] df=new String[4];
		df[0]=date.substring(0, 4);
		df[1]=date.substring(2, 4);
		df[2]=date.substring(5, 7);
		df[3]=date.substring(8, 10);

		return df;
	}

	public static  String[] getGregorianDateFactors(){
		Date now = new Date();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(now);

		String[] df=new String[4];
		df[0]=date.substring(0, 4);
		df[1]=date.substring(2, 4);
		df[2]=date.substring(5, 7);
		df[3]=date.substring(8, 10);

		return df;
	}

	public static String[] getTimeFactors(){

		String[] tf=new String[3];

		Calendar c= Calendar.getInstance();
		tf[0] = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		if(tf[0].length()==1)  tf[0] = "0" + tf[0];

		tf[1] = String.valueOf( c.get(Calendar.MINUTE));
		if(tf[1].length()==1)  tf[1] = "0" + tf[1];

		tf[2] =String.valueOf( c.get(Calendar.SECOND));
		if(tf[2].length()==1)  tf[2] = "0" + tf[2];

		return tf;
	}

	//return the difference between two days
	public static float getNumDaysBetween(String dateStart, String dateEnd){
		float day1 = getDays(dateStart);
		float day2 = getDays(dateEnd);

		return Math.abs(day2-day1);
	}

	public static int checkDateDiff(String dateStart, String dateEnd){
		float diff=0;
		float day1 = getDays(dateStart);
		float day2 = getDays(dateEnd);

		diff=day1-day2;

		int res=0;
		if(diff>0){
			res=1;
		}
		else if(diff==0){
			res=0;
		}
		else{
			res=-1;
		}

		return res;
	}

	//return the days
	public static float getDays(String date){
		//  این تابع تعداد روز ها را نستب به یک تاریخ حساب میکند که در اینجا سال 80 است و بر میگرداند
		//  برای محاسبه اختلاف بین دو تاریخ تعداد روز های هر تاریخ را نسبت به تاریخ مبدا حساب کرده و تفاضل آن ها را حساب می کنیم.
		try{
			if(date.contains("/") || date.contains(".")|| date.contains("-") ){
				date = date.replace("/", "");
				date = date.replace("-", "");
				date = date.replace(".", "");
			}
			float day=0;
			float month = 0;
			float year=0;
			float dayDiffs;

			if(date.length()==8){
				year=Integer.valueOf( date.substring(2, 4)) -80;
				month=Integer.valueOf( date.substring(4, 6));
				day=Integer.valueOf( date.substring(6, 8));
			}
			else if(date.length()==6){
				year=Integer.valueOf( date.substring(0, 2)) -80;
				month=Integer.valueOf( date.substring(2, 4));
				day=Integer.valueOf( date.substring(4, 6));
			}
			else {

			}

			if(month<7)	dayDiffs=365*year+31*month+day;

			else  dayDiffs=365*year+186+30*(month-6) + day;

			return dayDiffs;
		}

		catch(Exception e){ return 0;}
	}

	public static int  timeDiff(String dateStart,String dateStop){
		//dateStart = "09:30:00";
		//dateStop = "09:37:00";

		try {
			int startHour= Integer.valueOf(dateStart.substring(0,2)) * 60 + Integer.valueOf(dateStart.substring(3, 5));

			int endtHour= Integer.valueOf(dateStop.substring(0,2)) * 60 + Integer.valueOf(dateStop.substring(3, 5));

			int result = endtHour- startHour;
			//if(result<0){
			result=Math.abs(result);
			//}
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static String jalaliToGregorian(String PersianDate) {
		int py = Integer.getInteger(PersianDate.substring(0, 4));
		int pm = Integer.getInteger(PersianDate.substring(4, 6));
		int pd = Integer.getInteger(PersianDate.substring(6, 8));

		return "";
	}


	public static Calendar getGregorianCalendar(int year, int month, int day) {
		int g_days_in_month[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int j_days_in_month[] = { 31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29 };
		int gy, gm, gd;
		int jy, jm, jd;
		long g_day_no, j_day_no;
		boolean leap;

		int i;

		jy = year - 979;
		jm = month - 1;
		jd = day - 1;

		j_day_no = 365 * jy + (jy / 33) * 8 + (jy % 33 + 3) / 4;
		for (i = 0; i < jm; ++i)
			j_day_no += j_days_in_month[i];

		j_day_no += jd;

		g_day_no = j_day_no + 79;

		gy = (int) (1600 + 400 * (g_day_no / 146097)); /*
		 * 146097 = 365*400 +
		 * 400/4 - 400/100 +
		 * 400/400
		 */
		g_day_no = g_day_no % 146097;

		leap = true;
		if (g_day_no >= 36525) /* 36525 = 365*100 + 100/4 */
		{
			g_day_no--;
			gy += 100 * (g_day_no / 36524); /* 36524 = 365*100 + 100/4 - 100/100 */
			g_day_no = g_day_no % 36524;

			if (g_day_no >= 365)
				g_day_no++;
			else
				leap = false;
		}

		gy += 4 * (g_day_no / 1461); /* 1461 = 365*4 + 4/4 */
		g_day_no %= 1461;

		if (g_day_no >= 366) {
			leap = false;

			g_day_no--;
			gy += g_day_no / 365;
			g_day_no = g_day_no % 365;
		}

		for (i = 0; g_day_no >= g_days_in_month[i] + parsBooleanToInt(i == 1 && leap); i++)
			g_day_no -= g_days_in_month[i] + parsBooleanToInt(i == 1 && leap);

		gm = i + 1;
		gd = (int) (g_day_no + 1);

		Calendar gregorian = Calendar.getInstance();
		gregorian.set(gy, gm - 1, gd);

		return gregorian;
	}

	private static int parsBooleanToInt(Boolean sample) {
		if (sample)
			return 1;
		else
			return 0;
	}

//	public static String getCurrentDateByLang(){
//		if(G.appLang.equals("fa")){
//			return getCurrentShamsiDate();
//		}else {
//			return getCurrentGregorianDate();
//		}
//	}
	

}
