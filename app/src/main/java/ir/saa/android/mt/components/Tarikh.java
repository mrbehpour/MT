package ir.saa.android.mt.components;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;


public class Tarikh {

    public class SolarCalendar {

        public String strWeekDay = "";
        public String strMonth   = "";

        int           date;
        int           day;
        int           month;
        int           year;


        public SolarCalendar()
        {
            Date MiladiDate = new Date();
            calcSolarCalendar(MiladiDate);
        }


        public SolarCalendar(Date MiladiDate)
        {
            calcSolarCalendar(MiladiDate);
        }


        private void calcSolarCalendar(Date MiladiDate) {

            int ld;

            int miladiYear = MiladiDate.getYear() + 1900;
            int miladiMonth = MiladiDate.getMonth() + 1;
            int miladiDate = MiladiDate.getDate();
            int WeekDay = MiladiDate.getDay();

            int[] buf1 = new int[12];
            int[] buf2 = new int[12];

            buf1[0] = 0;
            buf1[1] = 31;
            buf1[2] = 59;
            buf1[3] = 90;
            buf1[4] = 120;
            buf1[5] = 151;
            buf1[6] = 181;
            buf1[7] = 212;
            buf1[8] = 243;
            buf1[9] = 273;
            buf1[10] = 304;
            buf1[11] = 334;

            buf2[0] = 0;
            buf2[1] = 31;
            buf2[2] = 60;
            buf2[3] = 91;
            buf2[4] = 121;
            buf2[5] = 152;
            buf2[6] = 182;
            buf2[7] = 213;
            buf2[8] = 244;
            buf2[9] = 274;
            buf2[10] = 305;
            buf2[11] = 335;

            if ((miladiYear % 4) != 0) {
                date = buf1[miladiMonth - 1] + miladiDate;

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
                        year = miladiYear - 621;
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
                        year = miladiYear - 621;
                    }
                } else {
                    if ((miladiYear > 1996) && (miladiYear % 4) == 1) {
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
                    year = miladiYear - 622;
                }
            } else {
                date = buf2[miladiMonth - 1] + miladiDate;

                if (miladiYear >= 1996) {
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
                        year = miladiYear - 621;
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
                        year = miladiYear - 621;
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
                    year = miladiYear - 622;
                }

            }

            switch (month) {
                case 1:
                    strMonth = (String) G.context.getResources().getText(R.string.Farvardin_Tarikh);
                    break;
                case 2:
                    strMonth = (String) G.context.getResources().getText(R.string.Ordi_Tarikh);
                    break;
                case 3:
                    strMonth = (String) G.context.getResources().getText(R.string.Khordad_Tarikh);
                    break;
                case 4:
                    strMonth = (String) G.context.getResources().getText(R.string.Tir_Tarikh);
                    break;
                case 5:
                    strMonth = (String) G.context.getResources().getText(R.string.Mordad_Tarikh);
                    break;
                case 6:
                    strMonth = (String) G.context.getResources().getText(R.string.Sharivar_Tarikh);
                    break;
                case 7:
                    strMonth = (String) G.context.getResources().getText(R.string.Meher_Tarikh);
                    break;
                case 8:
                    strMonth = (String) G.context.getResources().getText(R.string.Aban_Tarikh);
                    break;
                case 9:
                    strMonth = (String) G.context.getResources().getText(R.string.Azar_Tarikh);
                    break;
                case 10:
                    strMonth = (String) G.context.getResources().getText(R.string.Day_Tarikh);
                    break;
                case 11:
                    strMonth = (String) G.context.getResources().getText(R.string.Bahman_Tarikh);
                    break;
                case 12:
                    strMonth = (String) G.context.getResources().getText(R.string.Esfand_Tarikh);
                    break;
            }

            switch (WeekDay) {

                case 0:
                    strWeekDay = (String) G.context.getResources().getText(R.string.Sun_Tarikh);
                    break;
                case 1:
                    strWeekDay = (String) G.context.getResources().getText(R.string.Mon_Tarikh);
                    break;
                case 2:
                    strWeekDay = (String) G.context.getResources().getText(R.string.Tue_Tarikh);
                    break;
                case 3:
                    strWeekDay = (String) G.context.getResources().getText(R.string.Wed_Tarikh);
                    break;
                case 4:
                    strWeekDay = (String) G.context.getResources().getText(R.string.Thu_Tarikh);
                    break;
                case 5:
                    strWeekDay = (String) G.context.getResources().getText(R.string.Fri_Tarikh);
                    break;
                case 6:
                    strWeekDay = (String) G.context.getResources().getText(R.string.Sat_Tarikh);
                    break;
            }

        }

    }

    public static String getCurrentShamsidate() {
        Locale loc = new Locale("en_US");
        Tarikh util = new Tarikh();
        SolarCalendar sc = util.new SolarCalendar();
        return String.valueOf(sc.year) + "/" + String.format(loc, "%02d",
                sc.month) + "/" + String.format(loc, "%02d", sc.date);
    }
    public static String getCurrentShamsidatetime() {
        Locale loc = new Locale("en_US");
        Tarikh util = new Tarikh();
        SolarCalendar sc = util.new SolarCalendar();
        return String.valueOf(sc.year) + "/" + String.format(loc, "%02d",
                sc.month) + "/" + String.format(loc, "%02d", sc.date) +"  " + getFullTime();
    }
    public static String getCurrentShamsidatetimeWithoutSlash() {
        Locale loc = new Locale("en_US");
        Tarikh util = new Tarikh();
        SolarCalendar sc = util.new SolarCalendar();
        return String.valueOf(sc.year) + String.format(loc, "%02d",
                sc.month) + String.format(loc, "%02d", sc.date)  + getFullTime().replace(":","");
    }
    public static String getFullTime(){
    	String localTime = "";
    	Calendar cal = Calendar.getInstance(TimeZone.getDefault()); //TimeZone.getTimeZone("GMT+3:30")
    	Date currentLocalTime = cal.getTime();
    	DateFormat date = new SimpleDateFormat("HH:mm:ss"); 
    	// you can get seconds by adding  "...:ss" to it
    	date.setTimeZone(TimeZone.getDefault());

    	localTime = date.format(currentLocalTime); 
    	return localTime;
    }
    public static String getTime(){
    	String localTime = "";
    	Calendar cal = Calendar.getInstance(TimeZone.getDefault());
    	Date currentLocalTime = cal.getTime();
    	DateFormat date = new SimpleDateFormat("HH:mm"); 
    	// you can get seconds by adding  "...:ss" to it
    	date.setTimeZone(TimeZone.getDefault());

    	localTime = date.format(currentLocalTime); 
    	return localTime;
    }

    public static String getTimeWithoutColon(){
    	String localTime = "";
    	Calendar cal = Calendar.getInstance(TimeZone.getDefault());
    	Date currentLocalTime = cal.getTime();
    	DateFormat date = new SimpleDateFormat("HHmm"); 
    	date.setTimeZone(TimeZone.getDefault());

    	localTime = date.format(currentLocalTime); 
    	return localTime;
    }
    public static String getCurrentShamsidateWithoutSlash() {
        Locale loc = new Locale("en_US");
        Tarikh util = new Tarikh();
        SolarCalendar sc = util.new SolarCalendar();
        return String.valueOf(sc.year) + String.format(loc, "%02d",
                sc.month) + String.format(loc, "%02d", sc.date);
    }

    public static String getFullCurrentShamsidate() {
        Locale loc = new Locale("en_US");
        Tarikh util = new Tarikh();
        SolarCalendar sc = util.new SolarCalendar();
        String res = String.format("امروز %s ،  %s %s %s", sc.strWeekDay, String.format(loc, "%02d", sc.date), sc.strMonth, String.valueOf(sc.year));
        return res;
    }

    public String getShamsiDate(Date MiladiDate)
    {
        Locale loc = new Locale("en_US");
        Tarikh util = new Tarikh();
        SolarCalendar sc = util.new SolarCalendar(MiladiDate);
        return String.valueOf(sc.year) + "/" + String.format(loc, "%02d",
                sc.month) + "/" + String.format(loc, "%02d", sc.date);
    }

    public String getShamsiDate(String MiladiDate)
    {
        Locale loc = new Locale("en_US");
        SimpleDateFormat s = new SimpleDateFormat("yyy-MM-dd");

        Tarikh util = new Tarikh();
        SolarCalendar sc;
        try {
            sc = util.new SolarCalendar(s.parse(MiladiDate));
            return String.valueOf(sc.year) + "/" + String.format(loc, "%02d",
                    sc.month) + "/" + String.format(loc, "%02d", sc.date);
        }
        catch (ParseException e) {
            return MiladiDate;
            //e.printStackTrace();
        }

    }
    public String getCurrentDateTimeNoneSpace() {
    	DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
    	Calendar calobj = Calendar.getInstance();
    	return df.format(calobj.getTime());
    }

    public static String getSlashedStringDate(String nonSlashStrDate){
        if(nonSlashStrDate.trim().length()==0)
            return "";
        if(nonSlashStrDate.length()==8)
            return new StringBuilder(nonSlashStrDate).insert(4, "/").insert(7, "/").toString();
        else
            return new StringBuilder(nonSlashStrDate).insert(2, "/").insert(5, "/").toString();
    }
    public static String getColonedStringTime(String nonColonedStrTime){
        if(nonColonedStrTime.trim().length()==0)
            return "";
        return new StringBuilder(nonColonedStrTime).insert(2, ":").toString();
    }

    public static Long addMinutesToDateTime(String strDateTime,Integer minutes){
        Integer year = Integer.valueOf(strDateTime.substring(0,4));
        Integer month = Integer.valueOf(strDateTime.substring(4,6));
        Integer day = Integer.valueOf(strDateTime.substring(6,8));
        Integer hour = Integer.valueOf(strDateTime.substring(8,10));
        Integer min = Integer.valueOf(strDateTime.substring(10,12));
        JalaliCalendar jalaliCalendar = new JalaliCalendar(year, month, day, hour, min, 0);
        jalaliCalendar.add(Calendar.MINUTE, minutes);
        jalaliCalendar.get(Calendar.YEAR);

        String resDateTime = String.format("%s%s%s%s%s",
                jalaliCalendar.get(Calendar.YEAR),
                jalaliCalendar.get(Calendar.MONTH)<10?"0"+String.valueOf(jalaliCalendar.get(Calendar.MONTH)):String.valueOf(jalaliCalendar.get(Calendar.MONTH)),
                jalaliCalendar.get(Calendar.DAY_OF_MONTH)<10?"0"+String.valueOf(jalaliCalendar.get(Calendar.DAY_OF_MONTH)):String.valueOf(jalaliCalendar.get(Calendar.DAY_OF_MONTH)),
                jalaliCalendar.get(Calendar.HOUR_OF_DAY)<10?"0"+String.valueOf(jalaliCalendar.get(Calendar.HOUR_OF_DAY)):String.valueOf(jalaliCalendar.get(Calendar.HOUR_OF_DAY)),
                jalaliCalendar.get(Calendar.MINUTE)<10?"0"+String.valueOf(jalaliCalendar.get(Calendar.MINUTE)):String.valueOf(jalaliCalendar.get(Calendar.MINUTE))
        );
        return Long.parseLong(resDateTime);
    }
    public static Long subMinutesFromDateTime(String strDateTime,Integer minutes){
        Integer year = Integer.valueOf(strDateTime.substring(0,4));
        Integer month = Integer.valueOf(strDateTime.substring(4,6));
        Integer day = Integer.valueOf(strDateTime.substring(6,8));
        Integer hour = Integer.valueOf(strDateTime.substring(8,10));
        Integer min = Integer.valueOf(strDateTime.substring(10,12));
        JalaliCalendar jalaliCalendar = new JalaliCalendar(year, month, day, hour, min, 0);
        jalaliCalendar.add(Calendar.MINUTE, -minutes);
        //jalaliCalendar.get(Calendar.YEAR);

        String resDateTime = String.format("%s%s%s%s%s",
                jalaliCalendar.get(Calendar.YEAR),
                jalaliCalendar.get(Calendar.MONTH)<10?"0"+String.valueOf(jalaliCalendar.get(Calendar.MONTH)):String.valueOf(jalaliCalendar.get(Calendar.MONTH)),
                jalaliCalendar.get(Calendar.DAY_OF_MONTH)<10?"0"+String.valueOf(jalaliCalendar.get(Calendar.DAY_OF_MONTH)):String.valueOf(jalaliCalendar.get(Calendar.DAY_OF_MONTH)),
                jalaliCalendar.get(Calendar.HOUR_OF_DAY)<10?"0"+String.valueOf(jalaliCalendar.get(Calendar.HOUR_OF_DAY)):String.valueOf(jalaliCalendar.get(Calendar.HOUR_OF_DAY)),
                jalaliCalendar.get(Calendar.MINUTE)<10?"0"+String.valueOf(jalaliCalendar.get(Calendar.MINUTE)):String.valueOf(jalaliCalendar.get(Calendar.MINUTE))
        );
        return Long.parseLong(resDateTime);
    }
    public static Long getLongDateTime(String strDateTime){
        Integer year = Integer.valueOf(strDateTime.substring(0,4));
        Integer month = Integer.valueOf(strDateTime.substring(4,6));
        Integer day = Integer.valueOf(strDateTime.substring(6,8));
        Integer hour = Integer.valueOf(strDateTime.substring(8,10));
        Integer min = Integer.valueOf(strDateTime.substring(10,12));
        JalaliCalendar jalaliCalendar = new JalaliCalendar(year, month, day, hour, min, 0);

        String resDateTime = String.format("%s%s%s%s%s",
                jalaliCalendar.get(Calendar.YEAR),
                jalaliCalendar.get(Calendar.MONTH)<10?"0"+String.valueOf(jalaliCalendar.get(Calendar.MONTH)):String.valueOf(jalaliCalendar.get(Calendar.MONTH)),
                jalaliCalendar.get(Calendar.DAY_OF_MONTH)<10?"0"+String.valueOf(jalaliCalendar.get(Calendar.DAY_OF_MONTH)):String.valueOf(jalaliCalendar.get(Calendar.DAY_OF_MONTH)),
                jalaliCalendar.get(Calendar.HOUR_OF_DAY)<10?"0"+String.valueOf(jalaliCalendar.get(Calendar.HOUR_OF_DAY)):String.valueOf(jalaliCalendar.get(Calendar.HOUR_OF_DAY)),
                jalaliCalendar.get(Calendar.MINUTE)<10?"0"+String.valueOf(jalaliCalendar.get(Calendar.MINUTE)):String.valueOf(jalaliCalendar.get(Calendar.MINUTE))
        );
        return Long.parseLong(resDateTime);
    }
    public static boolean isDateValid(String strDate){
        boolean isValid = true;
        try {
            if(Integer.valueOf(strDate.split("/")[1]) > 12){
                isValid = false;
            }else if(Integer.valueOf(strDate.split("/")[2]) > 31){
                isValid = false;
            }
        }catch (Exception ex){
            isValid = false;
        }
        return isValid;
    }
    public static boolean isTimeValid(String strDate){
        boolean isValid = true;
        try {
            if(Integer.valueOf(strDate.split(":")[0]) > 23){
                isValid = false;
            }else if(Integer.valueOf(strDate.split(":")[1]) > 59){
                isValid = false;
            }
        }catch (Exception ex){
            isValid = false;
        }
        return isValid;
    }
}