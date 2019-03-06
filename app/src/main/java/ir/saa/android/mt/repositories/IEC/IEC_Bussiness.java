package ir.saa.android.mt.repositories.IEC;

import ir.saa.android.mt.repositories.meterreader.ASCII;
import ir.saa.android.mt.repositories.meterreader.MeterUtility;

public class IEC_Bussiness {

    public static char getNewBaudRateFromMeterString(String meterString) {
        char newBaudrate = '0';
        try {
            newBaudrate =  meterString.charAt(3);
        } catch (Exception ex) {
            newBaudrate = 'X';
        }

        return newBaudrate;
    }

    public static String makeAcknowledgementString(char V, char newBaudrate,MeterUtility.readingMode readMode) {
        String result = "";

        result += ASCII.ACK;
        result += V;
        result += newBaudrate;
        result += readMode == MeterUtility.readingMode.readout?'0':'1';
        result += ASCII.CR;
        result += ASCII.LF;
        return result;
    }

}
