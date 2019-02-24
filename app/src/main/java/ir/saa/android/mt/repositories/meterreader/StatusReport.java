package ir.saa.android.mt.repositories.meterreader;

import com.google.gson.Gson;

public class StatusReport {
    public enum progressBarMode {
        reportStatus,
        resetValue,
        setMax,
        setValue,
    }

    public static class progressItem{
        public progressBarMode progMode;
        public String progressText;
        public int progValue;
    }

    public static String createProgressItem(progressBarMode progMode, String progressText, int progValue){
        progressItem progItem=new progressItem();
        progItem.progMode = progMode;
        progItem.progressText = progressText;
        progItem.progValue = progValue;

        Gson gson = new Gson();
        String progJSON = gson.toJson(progItem);
        return progJSON;
    }

    public static progressItem getProgressItem(String progressGson){
        Gson gson = new Gson();
        progressItem progItem= null;
        progItem = gson.fromJson(progressGson, progressItem.class);

        return  progItem;
    }

    public static boolean checkProgressString(String progressGson){
        boolean result = false;
        try{
            Gson gson = new Gson();
            progressItem progItem= null;
            progItem = gson.fromJson(progressGson, progressItem.class);
            result = true;
        }catch (Exception ex){

        }
        return result;
    }
}
