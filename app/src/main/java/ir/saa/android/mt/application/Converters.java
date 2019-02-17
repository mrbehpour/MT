package ir.saa.android.mt.application;

import java.lang.reflect.Array;

public class Converters {
    public static byte[] ConvertInt2ByteArray(int num, boolean doReverce) {
        byte[] data = new byte[2];

        if(doReverce){
            data[0] = (byte) (num & 0xFF);
            data[1] = (byte) ((num >> 8) & 0xFF);
        }
        else {
            data[1] = (byte) (num & 0xFF);
            data[0] = (byte) ((num >> 8) & 0xFF);
        }
        return  data;
    }

    public static byte[] ConvertInt4ByteArray(int num) {
        byte[] result = new byte[4];

        result[0] = (byte) ((num >> 24)& 0xFF);
        result[1] = (byte) ((num >> 16)& 0xFF);
        result[2] = (byte) ((num >> 8)& 0xFF);
        result[3] = (byte) (num & 0xFF);


        return  result;
    }

    public static String ArrayByte2Hex(byte[] selArray){
        final StringBuilder builder=new StringBuilder();
        for(byte b : selArray){
            builder.append(String.format("%02x" , b));
        }

        return builder.toString().toUpperCase();

    }

    public static byte[] ConcatenateTwoArray(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        byte[] c = (byte[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    public static byte[] hex2ByteArray2By2(String value) {

        try {
            value = value.toUpperCase();

            byte[] comBuffer = new byte[value.length() / 2];
            for (int i = 0; i < value.length(); i = i + 2) {
                comBuffer[i / 2] = (byte) ((Character.digit(value.charAt(i), 16) << 4) + Character.digit(value.charAt(i + 1), 16));
            }
            return comBuffer;
        }
        catch (Exception ex){
            String a=ex.getMessage();
            return null;
        }

    }

}
