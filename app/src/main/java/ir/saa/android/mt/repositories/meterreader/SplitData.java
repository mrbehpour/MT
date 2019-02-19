package ir.saa.android.mt.repositories.meterreader;

import java.util.List;

public class SplitData {

    public static MeterUtility.ReadData splitReadData(String readStr, MeterUtility.MeterInfo meterInfo){
        List<MeterUtility.DataItem> lstData = MeterUtility.CreateReadDataList(readStr,meterInfo);
        List<MeterUtility.ObisItem> lstObis = MeterUtility.getListObis(meterInfo.MaterSummaryName);
        MeterUtility.ReadData readData = new MeterUtility.ReadData();

        for (MeterUtility.ObisItem obis: lstObis) {
            if(!obis.mainObis.isEmpty()) {
                for (MeterUtility.DataItem dataItem : lstData) {
                    if (dataItem.mainObis.contains(obis.mainObis)) {
                        MeterUtility.setReadDataValue(readData, obis.tariffName, dataItem.dataStr, obis.floatPoint);
                        break;
                    }
                }
            }
        }

        return readData;
    }

    public static String splitDataInOpenPrntsStar(String checkData) {
        String value = "";
        try {
            value = checkData.substring(checkData.indexOf(String.valueOf(ASCII.OPEN_PRNTZ)) + 1, checkData.indexOf(ASCII.STAR));
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
