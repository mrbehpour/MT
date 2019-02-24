package ir.saa.android.mt.repositories.meterreader;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import ir.saa.android.mt.repositories.IEC.IEC_Constants;

public class MeterUtility {

    public enum connectionStatus {
        getMeterString,
        getReadoutString,
        getP0String,
        sendPass,
        getPassResult,
        getObisString,
        disConnecting,
    }

    public enum readingMode {
        readout,
        programming,
        dlms,
        command,
    }

    public enum meterCommandFormat {
        Obis_Prnts_Semi,
        Prnts_Obis,
        Just_Obis,
        dlms_Obis,
    }

    public static class ObisItem {
        public String tariffName;
        public String mainObis;
        public String secondObis;
        public int floatPoint;
        public int convertType;
    }

    public static class DataItem {
        public String mainObis;
        public String dataStr;
    }

    public static class MeterInfo{
        public String MeterCompany;
        public String MeterType;
        public String MaterSummaryName;
        public String MeterString;
        public String ReadMode;
        public String ValidationRegex;
        public boolean SetDateTime;
        public boolean NeedPassForRead;
        public String ReadObisType;
        public String R_Command;
        public int MakePassAlgorithm;
        public String Pass1;
        public String Pass2;
        public String Pass3;
    }

    public static class ReadData{
        public double Dimand;
        public double Active1;
        public double Active2;
        public double Active3;
        public double Reactive;
        public double Holiday;
        public String CurDate;
        public String CurTime;
        public String SerialNum1;
        public String SerialNum2;
        public double ActiveSum;
        public double ReversEnerji;
        public String FuncErr1;
        public String FuncErr2;
        public String FuncErr3;
        public String FuncErr4;
        public String FuncErr5;
        public double CurVolt1;
        public double CurVolt2;
        public double CurVolt3;
        public double Amp1;
        public double Amp2;
        public double Amp3;
    }

    public static String getMeterSummaryName(String meterString){
        String meterSummaryName="";
        return meterSummaryName;
    }

    public static List<ObisItem> getListObis(String meterSummaryName){

        //String jsonArray = "[{\"tariffName\":\"Dimand\",\"mainObis\":\"1.6.0\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Active1\",\"mainObis\":\"1.8.1\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Active2\",\"mainObis\":\"1.8.2\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Active3\",\"mainObis\":\"1.8.3\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Reactive\",\"mainObis\":\"3.8.0\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Holiday\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"SerialNum1\",\"mainObis\":\"0.0.0\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"SerialNum2\",\"mainObis\":\"0.9.1\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurDate\",\"mainObis\":\"1.8.0\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurTime\",\"mainObis\":\"0.9.2\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"ActiveSum\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"ReversEnerji\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr1\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr2\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr3\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr4\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr5\",\"mainObis\":\"32.7\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurVolt1\",\"mainObis\":\"52.7\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurVolt2\",\"mainObis\":\"72.7\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurVolt3\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Amp1\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Amp2\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Amp3\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0}]";
        String jsonArray = "[{\"tariffName\":\"Dimand\",\"mainObis\":\"1.6.1\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Active1\",\"mainObis\":\"1.8.1\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Active2\",\"mainObis\":\"1.8.2\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Active3\",\"mainObis\":\"1.8.3\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Reactive\",\"mainObis\":\"3.8.0\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Holiday\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"SerialNum1\",\"mainObis\":\"C.1.0\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"SerialNum2\",\"mainObis\":\"2.8.0\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurDate\",\"mainObis\":\"0.9.1\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurTime\",\"mainObis\":\"0.9.2\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"ActiveSum\",\"mainObis\":\"1.8.0\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"ReversEnerji\",\"mainObis\":\"2.8.0\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr1\",\"mainObis\":\"F.F\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr2\",\"mainObis\":\"F.F.0\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr3\",\"mainObis\":\"F.F.1\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr4\",\"mainObis\":\"F.F.2\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr5\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurVolt1\",\"mainObis\":\"32.7.0\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurVolt2\",\"mainObis\":\"52.7.0\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurVolt3\",\"mainObis\":\"72.7.0\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Amp1\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Amp2\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Amp3\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0}]";
        //String jsonArray = "[{\"tariffName\":\"Dimand\",\"mainObis\":\"AMP040\",\"secondObis\":\"\",\"floatPoint\":2,\"convertType\":0},{\"tariffName\":\"Active1\",\"mainObis\":\"AMP010\",\"secondObis\":\"\",\"floatPoint\":2,\"convertType\":0},{\"tariffName\":\"Active2\",\"mainObis\":\"AMP011\",\"secondObis\":\"\",\"floatPoint\":2,\"convertType\":0},{\"tariffName\":\"Active3\",\"mainObis\":\"AMP012\",\"secondObis\":\"\",\"floatPoint\":2,\"convertType\":0},{\"tariffName\":\"Reactive\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":2,\"convertType\":0},{\"tariffName\":\"Holiday\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"SerialNum1\",\"mainObis\":\"AMP002\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"SerialNum2\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurDate\",\"mainObis\":\"AMP993\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurTime\",\"mainObis\":\"AMP994\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"ActiveSum\",\"mainObis\":\"AMP030\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"ReversEnerji\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr1\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr2\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr3\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr4\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"FuncErr5\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurVolt1\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurVolt2\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"CurVolt3\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Amp1\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Amp2\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0},{\"tariffName\":\"Amp3\",\"mainObis\":\"\",\"secondObis\":\"\",\"floatPoint\":0,\"convertType\":0}]";

        ObjectMapper objectMapper = new ObjectMapper();
        //Set pretty printing of json
        objectMapper.setSerializationInclusion (JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion (JsonInclude.Include.NON_EMPTY);
        objectMapper.setSerializationInclusion (JsonInclude.Include.NON_DEFAULT);

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        List<ObisItem> obisLst = null;
        try {
            obisLst = objectMapper.readValue(jsonArray, new TypeReference<List<ObisItem>>(){});

        } catch (IOException e) {
            e.printStackTrace();
        }

        return obisLst;
    }

    public static int getListObisCount(List<ObisItem> lstObisItem){
        int c = 0;
        //lstObisItem.stream().filter(o -> o.mainObis()!="").collect(Collectors.toList())

        for(ObisItem obisItem : lstObisItem){
            if(obisItem.mainObis.isEmpty()) c++;
        }
        return c;
    }

    public static MeterInfo getMeterInfo(String meterString){

        MeterInfo meterInfo = new MeterInfo();

//        meterInfo.MeterCompany = "EAA";
//        meterInfo.MeterType = "JAM-300";
//        meterInfo.MaterSummaryName = "JAM-300";
//        meterInfo.MeterString = meterString;
//        meterInfo.ReadMode = readingMode.readout.name();
//        meterInfo.ValidationRegex = "^[FC0-9]{1,2}[.][FC0-9]{1,2}[.]\\d{1,2}[(].*[)]$";
//        meterInfo.SetDateTime = false;
//        meterInfo.NeedPassForRead=false;
//        meterInfo.R_Command="";

        meterInfo.MeterCompany = "ELESTER";
        meterInfo.MeterType = "ABB-1440";
        meterInfo.MaterSummaryName = "ABB-1440";
        meterInfo.MeterString = meterString;
        meterInfo.ReadMode = readingMode.programming.name();;
        meterInfo.ValidationRegex = "^[FC0-9]{1,2}[.][FC0-9]{1,2}[.]\\d{1,2}[(].*[)]$";
        meterInfo.SetDateTime = false;
        meterInfo.NeedPassForRead = false;
        meterInfo.ReadObisType = "Obis_Prnts_Semi";
        meterInfo.R_Command=IEC_Constants.R5_Command;

//        meterInfo.MeterCompany = "AMPY";
//        meterInfo.MeterType = "AMP-E";
//        meterInfo.MaterSummaryName = "AMP-E";
//        meterInfo.MeterString = meterString;
//        meterInfo.ReadMode = readingMode.programming.name();
//        meterInfo.ValidationRegex = "^AMP\\d{3}[(].*[)]$";
//        meterInfo.SetDateTime = false;
//        meterInfo.NeedPassForRead = true;
//        meterInfo.ReadObisType = "Just_Obis";
//        meterInfo.R_Command=IEC_Constants.R1_Command;


        return meterInfo;
    }



    public static void setReadDataValue(ReadData readData, String propName, String value, int floatingPoint){
        try {
            Class<?> c = readData.getClass();

            Field f = null;
            f = c.getDeclaredField(propName);

            f.setAccessible(true);

            if(f.getType().toString().equals("double")){
                f.set(readData, Double.valueOf(value)/ Math.pow(10,floatingPoint));
            }else {
                f.set(readData, value);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e){

        }
    }

    public static List<DataItem> CreateReadDataList(String readStr, MeterInfo meterInfo){
        String[] rowData = readStr.split("\r\n");
        List<DataItem> meterData = new ArrayList();
        Pattern patern = Pattern.compile(meterInfo.ValidationRegex);
        for (String s: rowData) {
            if (patern.matcher(s).matches()) {
                meterData.add(spliteObisValue(s));
            }
        }

        return  meterData;
    }

    public static DataItem spliteObisValue(String ObisReadValue){
        DataItem dataItem = new DataItem();

        int star=0;
        int opnPrntz=0;
        int clsPrntz=0;

        ObisReadValue = RemoveUnitKeywords(ObisReadValue);

        if(ObisReadValue.contains("*")){

            star= ObisReadValue.indexOf(ASCII.STAR);
            opnPrntz= ObisReadValue.indexOf(ASCII.OPEN_PRNTZ);

            dataItem.dataStr = ObisReadValue.substring(opnPrntz +1,star);

        }
        else {

            opnPrntz= ObisReadValue.indexOf(ASCII.OPEN_PRNTZ);
            clsPrntz= ObisReadValue.indexOf(ASCII.CLOSE_PRNTZ);

            dataItem.dataStr = ObisReadValue.substring(opnPrntz +1,clsPrntz);
        }

        if(opnPrntz>0) {
            dataItem.mainObis = ObisReadValue.substring(0, opnPrntz);
        }
        else{
            dataItem.mainObis = "";
        }

        return dataItem;

    }

    public static String CreateReadObisStr(MeterUtility.ObisItem obisItem, MeterUtility.MeterInfo meterInfo){
        String readObisStr="";
        switch (meterCommandFormat.valueOf(meterInfo.ReadObisType)){
            case Obis_Prnts_Semi:
                readObisStr = createMeterCommandStr(meterInfo.R_Command, String.format("%s(;)", obisItem.mainObis));
                break;
            case Prnts_Obis:
                readObisStr = createMeterCommandStr(meterInfo.R_Command, String.format("(%s)", obisItem.mainObis));
                break;
            case Just_Obis:
                readObisStr = createMeterCommandStr(meterInfo.R_Command, obisItem.mainObis);
                break;
            case dlms_Obis:
                readObisStr = obisItem.mainObis;
                break;
        }
        return readObisStr;
    }

    private static String createMeterCommandStr(String pCommand, String command) {
        String passStr = "";
        passStr = String.format("%s%s%s%s%s", String.valueOf(ASCII.SOH), pCommand, String.valueOf(ASCII.STX), command, String.valueOf(ASCII.ETX));
        passStr += getBCC(passStr);
        return passStr;
    }

    public static char getBCC(String BCC) {

        byte bccVal = 0;
        byte[] bcc;
        bcc = BCC.getBytes();

        for (int i = 1; i < BCC.length(); i++) {
            bccVal ^= bcc[i];
        }
        return (char) bccVal;
    }

    public static boolean CheckSendPassResultStr(String resultStr, MeterInfo meterInfo){
        boolean res=false;
        if(meterInfo.NeedPassForRead){
            if(resultStr.contains(String.valueOf(ASCII.ACK))) res = true;
        }
        return res;
    }

    public static String RemoveUnitKeywords(String value) {
        value = value.replace("/", ".");
        value = value.replace("kW", "");
        value = value.replace("kvarh", "");
        value = value.replace("kVARh", "");
        value = value.replace("h", "");
        value = value.replace("V", "");

        return value;
    }

    public static String RemoveReadoutChars(String responseStr) {
//        responseStr = responseStr.replace("0-0:", "");
//        responseStr = responseStr.replace("1-0:", "");
//        responseStr = responseStr.replace("1-1:", "");
//		responseStr = responseStr.replace(".255(", "(");
//        responseStr = responseStr.replace("*FF,2", "");
        responseStr = responseStr.replace("!", "\r\n");
        responseStr = responseStr.replace(String.valueOf(ASCII.STX), "");
        responseStr = responseStr.replace(String.valueOf(ASCII.ETX), "");

        return responseStr;
    }
}
