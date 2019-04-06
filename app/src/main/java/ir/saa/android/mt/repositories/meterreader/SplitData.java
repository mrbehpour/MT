package ir.saa.android.mt.repositories.meterreader;

import java.util.Arrays;
import java.util.List;

import ir.saa.android.mt.model.entities.DigitalMeters;
import ir.saa.android.mt.model.entities.MetersObis;

public class SplitData {

    public static MeterUtility.ReadData splitReadData(String readStr, DigitalMeters meterInfo, String meterObisStr){
        List<MeterUtility.DataItem> lstData = MeterUtility.CreateReadDataList(readStr,meterInfo);
        List<MeterUtility.ObisItem> lstObis = MeterUtility.getListObis(meterObisStr);
        MeterUtility.ReadData readData = new MeterUtility.ReadData();

        for (MeterUtility.ObisItem obis: lstObis) {
            if(!obis.mainObis.isEmpty()) {
                for (MeterUtility.DataItem dataItem : lstData) {
                    if (dataItem.mainObis.equals(obis.mainObis)) {

                        if(!obis.secondObis.isEmpty()   ) {
                            if(obis.secondObis.startsWith("+")) {
                                dataItem.dataStr = dataItem.dataStr + findPartTwoObis(lstData, obis);
                            }else if(obis.secondObis.startsWith("$")){
                                dataItem.dataStr = findSubstringObis(dataItem.dataStr, obis);
                            }
                        }

                        MeterUtility.setReadDataValue(readData,  dataItem.dataStr , obis);
                        break;
                    }
                }
            }
        }

        return readData;
    }

    public static String findPartTwoObis(List<MeterUtility.DataItem> lstData, MeterUtility.ObisItem obis){

        String strPlus="";

        for (MeterUtility.DataItem dataItem : lstData) {
            if (dataItem.mainObis.equals(obis.secondObis.replace("+","").trim())) {
                strPlus = dataItem.dataStr;
                break;
            }
        }

        return  strPlus.trim();
    }

    public static String findSubstringObis(String dataStr, MeterUtility.ObisItem obis){

        String[] subPos = obis.secondObis.replace("$","") .split(",");
        String newDataStr="";

        for(int i=0;i<subPos.length;i=i+2){
//					dataStr=String.format(subPos[0], Arrays.copyOfRange(subPos,1,subPos.length));
            newDataStr += dataStr.substring(Integer.valueOf(subPos[i]),Integer.valueOf(subPos[i+1]));
        }

        return newDataStr;

    }

    public static String splitDataInOpenPrntsStar(String checkData) {
        String value = "";
        int openPrntsIndex=checkData.indexOf(String.valueOf(ASCII.OPEN_PRNTZ));
        int closePrntsIndex=checkData.indexOf(String.valueOf(ASCII.CLOSE_PRNTZ));
        int starIndex=checkData.indexOf(String.valueOf(ASCII.STAR));

        try {
            if(openPrntsIndex>0 && starIndex>0 && openPrntsIndex<starIndex) {
                value = checkData.substring(openPrntsIndex + 1, starIndex);
            }else if(openPrntsIndex>0 && closePrntsIndex>0 && openPrntsIndex<closePrntsIndex){
                value = checkData.substring(openPrntsIndex + 1, closePrntsIndex);
            }
        }
        catch (Exception ex){

        }
        return value;
    }

    public static String splitDataInSTXOpenPrnts(String checkData) {
        String value = "";
        try {
            value = checkData.substring(checkData.indexOf(ASCII.STX) + 1, checkData.indexOf(ASCII.CLOSE_PRNTZ));
        }
        catch (Exception ex){

        }
        return value;
    }

    public static String splitDataInPrnts(String checkData) {
        String value = "";
        try {
            value = checkData.substring(checkData.indexOf(ASCII.OPEN_PRNTZ) + 1, checkData.indexOf(ASCII.CLOSE_PRNTZ));
        }
        catch (Exception ex){

        }
        return value;
    }

    public static String splitDataInSTX_ETX(String checkData) {
        String value = "";
        value = checkData.substring(checkData.indexOf(ASCII.STX) + 1, checkData.indexOf(ASCII.ETX));
        return value;
    }
}
