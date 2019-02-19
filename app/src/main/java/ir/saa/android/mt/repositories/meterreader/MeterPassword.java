package ir.saa.android.mt.repositories.meterreader;

import ir.saa.android.mt.application.Converters;
import ir.saa.android.mt.repositories.IEC.IEC_Constants;

public class MeterPassword {

    public static String GetPasswordStr(MeterUtility.MeterInfo meterInfo, String meterPass, String P0String){
        String SendPassStr="";
        switch (meterInfo.MaterSummaryName){
            case "AMP-E":
                SendPassStr=createMeterCommandStr(IEC_Constants.P2_Command,String.format("(%s)",CalculateAmpyPassword(meterPass,P0String)));
                break;
        }

        return SendPassStr;
    }

    public static String createMeterCommandStr(String pCommand, String passStr) {
        String resultStr = "";
        resultStr = String.format("%s%s%s%s%s", String.valueOf(ASCII.SOH), pCommand, String.valueOf(ASCII.STX), passStr, String.valueOf(ASCII.ETX));
        resultStr += MeterUtility.getBCC(resultStr);
        return resultStr;
    }

    private static String CalculateAmpyPassword(String pass, String p0Str) { // Generate AMPY password{
        String[] Pass_p = new String[4];
        String[] String_p = new String[4];

        for (int i = 0; i < 4; i++) {
            Pass_p[i] = pass.substring(i, i + 1);
            String_p[i] = p0Str.substring(i, i + 1);
        }

        try {
            int Mode = 4;
            int z = 0;
            int x = 0;

            int Long = 0;
            int Count = 0;
            int Wide = 0;
            int i = 0;
            char Temp = 0;

            char aa = '1';
            char b = '2';
            char c;
            c = (char) (b - aa);


            while (Mode != 0) {
                Wide = 0;
                Count = 8;
                Temp = Pass_p[i].charAt(0);
                i++;
                z = z + Temp;

                while (Count != 0)                             // W 0
                // L
                {
                    if ((Temp & 0x01) == 0) //  if the first bite is 0
                    {
                        Long = 0;
                    } else {
                        Long = 1;
                    }
                    Wide = Wide + Long;
                    Temp = (char) ((Temp - Long) / 2);
                    //          Temp = Temp/2;
                    Count--;
                }

                if ((Wide & 0x01) != 0) {
                    z = z + 0x80;
                }
                Mode--;
            }

            z = (0xFFFF - z) + 1;
            //    z++;

            i = 0;
            x = 0;
            Temp = (char) ((char) String_p[i].charAt(0) - '0');
            i++;
            if (Temp > 9)
                Temp = (char) (Temp - 7);
            x = (Temp << 4);

            Temp = (char) ((char) String_p[i].charAt(0) - '0');
            i++;
            if (Temp > 9)
                Temp = (char) (Temp - 7);
            x = x + Temp;
            x = (x << 8);

            Temp = (char) ((char) String_p[i].charAt(0) - '0');
            i++;
            if (Temp > 9)
                Temp = (char) (Temp - 7);

            Temp = (char) (Temp << 4);
            x = x | Temp;

            Temp = (char) ((char) String_p[i].charAt(0) - '0');
            i++;
            if (Temp > 9)
                Temp = (char) (Temp - 7);

            x = x | Temp;

            Count = 13;
            int Temp2;
            while (Count != 0) {
                Temp2 = (char) (x ^ z);
                Temp2 = Temp2 & 0x00FF;
                x = (x & 0xFF00) + Temp2;
                //        x = x + Temp;

                x = (x & 0xFFFE) / 2;

                if ((Temp2 & 0x01) != 0) {
                    x = x | 0x8000;
                }
                Count--;
            }
            z = x;
            i = 0;
            String a = "";
            Temp = (char) ((x >> 12) + '0');
            if (Temp > '9')
                Temp = (char) (Temp + 7);
            a += Temp;

            Temp = (char) (x >> 8);
            Temp = (char) ((Temp & 0x0F) + '0');
            if (Temp > '9')
                Temp = (char) (Temp + 7);
            a += Temp;

            Temp = (char) (x >> 4);
            Temp = (char) ((Temp & 0x0F) + '0');
            if (Temp > '9')
                Temp = (char) (Temp + 7);
            a += Temp;

            Temp = (char) ((x & 0x0F) + '0');
            if (Temp > '9')
                Temp = (char) (Temp + 7);
            a += Temp;
            return a;
        } catch (Exception ex) {
            return "";
        }


    }

    private static String CalculateRENENPassword(String password, String randomNum) {
        // It's an algorithm to calculate the final password by      P0 AND password
        byte num1;
        String str3 = "";

        String enPassWord = "";
        int i;
        short a;
        short newByte;
        String byteStr;

        //------------------------------------------
        if (password.substring(4, 6).equals("00")) {         //اگر 5امین و 6امین کاراکتر رمز کنتور هردو دارای مقدار 0 باشند
            short rnd6 = 0;
            String tmpPassWord = "";

            for (i = 0; i < 6; i++)             //در این حلقه یک بایت مقدار دهی می شود و طی محاسبات زیر مقدارش تعیین می شود
            {
                //  rnd6 = (short)(( ( (Short.valueOf(rnd6) + Short.valueOf(randomNum.substring(num_counter * 2, (num_counter * 2)+2), 0x10))   > 0x100)  ? ((Short.valueOf(rnd6) + Short.valueOf(randomNum.substring(num_counter * 2, (num_counter * 2)+2), 0x10)) - 0x100) : (Short.valueOf(rnd6) + Short.valueOf(randomNum.substring(num_counter * 2, (num_counter * 2)+2), 0x10) )) & 0xFF) ;

                if (((Short.valueOf(rnd6) + Short.valueOf(randomNum.substring(i * 2, (i * 2) + 2), 0x10)) > 0x100))

                    rnd6 = (short) (((Integer.valueOf(rnd6) + Integer.valueOf(randomNum.substring(i * 2, (i * 2) + 2), 0x10)) - 0x100) & 0xFF);
                else {
                    rnd6 = (short) ((Integer.valueOf(rnd6) + Integer.valueOf(randomNum.substring(i * 2, (i * 2) + 2), 0x10)));
                    short ab = (short) 200;
                }
            }

            for (i = 0; i < 6; i++) {
                //         در اینجا تکه های دو کاراکتری از رشته های پسورد و عدد رندم کنتور را بر می دارد و به صورت متناظر با هم اکس اور می کند و در متغیر می ریزد
                a = (short) (int) (Short.valueOf(randomNum.substring((5 - i) * 2, ((5 - i) * 2) + 2), 0x10) ^ Short.valueOf(password.substring(i * 2, (i * 2) + 2), 0x10));

                newByte = (a >= rnd6) ? (short) ((int) (a - rnd6)) : (short) ((int) ((a + 0x100) - rnd6));
                byteStr = String.format("%02x", newByte);      //      نیوبایت محاسبه شده را همه ی حرف هایش را حروف کوچک می کنیم و اگر کمتر از دو کاراکتر در خود داشت در سمت چپ آن 0 اضافه می کنیم و در "بایت استر" می ریزیم
                if (i == 2)     //else در سومین باری که حلقه اجرا می شود داخل این شرط می رود و در سایر موارد داخل
                {
                    tmpPassWord = tmpPassWord + password.substring(4, 6);
                } else {
                    tmpPassWord = tmpPassWord + byteStr;
                }
            }
            for (i = 0; i < 6; i++) {
                a = (short) ((int) (Short.valueOf(randomNum.substring(i * 2, (i * 2) + 2), 0x10) ^ Short.valueOf(tmpPassWord.substring(i * 2, (i * 2) + 2), 0x10)));
                newByte = (a > 0x55) ? (short) ((int) ((a + 170) - 0x100)) : (short) ((int) (a + 170));
                byteStr = String.format("%02x", newByte);
                enPassWord = enPassWord + byteStr.substring(1, 2) + byteStr.substring(0, 1);
            }
        }


        //------------------------------------------
        if (password.substring(4, 6).equals("01"))   //currentPriority
        {
            for (int index = 0; index < 6; ++index) {
                int num2 = (((byte) (Integer.valueOf(randomNum.substring(index * 2, (index * 2) + 2), 16) ^ Integer.valueOf(password.substring(index * 2, (index * 2) + 2), 16))) & 0xFF);
                num1 = (int) num2 > 85 ? (byte) ((int) num2 + 170 - 256) : (byte) ((int) num2 + 170);
                String str4 = String.format("%02x", num1);
                enPassWord = enPassWord + str4.substring(1, 2) + str4.substring(0, 1);
            }
        }

        return enPassWord.toUpperCase();
    }

    private static String CalculateElesterPassword(String Pass_p, String String_p) {  // Generate Elseter password

        String newPass = "";

        byte b;
        try {
            //StringBuilder sb_temp = new StringBuilder();
            byte[] strCont = new byte[8];
            for (int i = 0; i < String_p.length(); i += 2) {

                b = (byte) (Integer.parseInt(String_p.substring(i, i + 2), 16) & 0xff);

//				b= (byte)((Character.digit(String_p.charAt(i),16))+
//							Character.digit(String_p.charAt(i+1),16));
                strCont[i / 2] = b;

            }

            byte[] strPass;
            strPass = Pass_p.getBytes();

            byte[] strXOR = new byte[8];

            for (int i = 0; i < Pass_p.length(); i++) {
                strXOR[i] = (byte) (strPass[i] ^ strCont[i]);
            }

            byte lastByte = strXOR[7];
            for (int i = 0; i < strXOR.length; i++) {
                strXOR[i] = (byte) (strXOR[i] + lastByte);
                lastByte = strXOR[i];
            }

            newPass = Converters.ArrayByte2Hex(strXOR).toUpperCase();

            return newPass;
        } catch (Exception ex) {
            return newPass;
        }


    }

//    private static String CalculateACE2000Password(String key, String password) {
//
//        String result = "";
//        Long num = 0L;
//        Long num2 = OverFlowHandler.toLongUInt32(Long.parseLong("9e3779b9", 16));
//        Long num3 = OverFlowHandler.toLongUInt32(Long.parseLong("20", 16));
//        String str = password.substring(0, 8);
//        String str2 = password.substring(8, 16);
//        String str3 = key.substring(0, 8);
//        String str4 = key.substring(8, 16);
//        String str5 = key.substring(16, 24);
//        String str6 = key.substring(24, 32);
//        Long num4 = OverFlowHandler.toLongUInt32(Long.parseLong(str, 16));
//        Long num5 = OverFlowHandler.toLongUInt32(Long.parseLong(str2, 16));
//        Long num6 = OverFlowHandler.toLongUInt32(Long.parseLong(str3, 16));
//        Long num7 = OverFlowHandler.toLongUInt32(Long.parseLong(str4, 16));
//        Long num8 = OverFlowHandler.toLongUInt32(Long.parseLong(str5, 16));
//        Long num9 = OverFlowHandler.toLongUInt32(Long.parseLong(str6, 16));
//
//        while (num3 > 0) {
//            num3 = num3 - 1;
//            num = OverFlowHandler.toLongUInt32(num + num2);
//            num4 = OverFlowHandler.toLongUInt32(num4 + OverFlowHandler.toLongUInt32((((OverFlowHandler.toLongUInt32(OverFlowHandler.toLongUInt32(num5 << 4)) + num6)) ^ OverFlowHandler.toLongUInt32(num5 + num)) ^ (OverFlowHandler.toLongUInt32(OverFlowHandler.toLongUInt32(num5 >> 5) + num7))));
//            num5 = OverFlowHandler.toLongUInt32(num5 + OverFlowHandler.toLongUInt32((((OverFlowHandler.toLongUInt32(OverFlowHandler.toLongUInt32(num4 << 4)) + num8)) ^ OverFlowHandler.toLongUInt32(num4 + num)) ^ (OverFlowHandler.toLongUInt32(OverFlowHandler.toLongUInt32(num4 >> 5) + num9))));
//        }
//        result = OverFlowHandler.longToHexString(num4, 8) + OverFlowHandler.longToHexString(num5, 8);
//        return result.toUpperCase();
//    }
}
