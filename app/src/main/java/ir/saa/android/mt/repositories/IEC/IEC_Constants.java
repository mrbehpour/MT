package ir.saa.android.mt.repositories.IEC;

import ir.saa.android.mt.repositories.meterreader.ASCII;

public class IEC_Constants {

    public static final String startCommStr = new String(new char[]{'/','?','!', ASCII.CR,ASCII.LF});
    public static final String ABORT_CONNECTION = new String(new char[]{ASCII.SOH,'B','0',ASCII.ETX,'q'});
    public static final String ABORT_CONNECTION1 = new String(new char[]{'B','0',ASCII.ETX});
    public static final String EndOfReadOut = new String(new char[]{'!',ASCII.CR,ASCII.LF});

    public static final String dlmsStartCommStr =  "(9600,8,n,1)" + String.valueOf(ASCII.LF);
    public static final String evan1010StartCommStr =  "(4800,8,n,1)" + String.valueOf(ASCII.LF);

    public static final String P1_Command = "P1";
    public static final String P2_Command = "P2";
    public static final String R1_Command = "R1";
    public static final String R2_Command = "R2";
    public static final String R3_Command = "R3";
    public static final String R5_Command = "R5";
    public static final String W1_Command = "W1";
    public static final String W2_Command = "W2";
    public static final String W5_Command = "W5";
    public static final String E2_Command = "E2";

    public static final String OB_P01_Command = "P.01";
    public static final String OB_P02_Command = "P.02";
    public static final String OB_990101_Command = "99.1.0";
    public static final String OB_990102_Command = "99.2.0";
    public static final String OB_990101_Command_V2 = "1-0:99.1.0.255";
    public static final String OB_990102_Command_V2 = "1-0:99.2.0.255";
}
