package ir.saa.android.mt.uicontrollers.pojos.MeterReader;

import java.util.HashMap;

public class MeterReaderFieldName {
    static HashMap<String,Integer> hashMapCode=new HashMap<>();
    static HashMap<Integer,String> hashMapName=new HashMap<>();

    public static Integer ReadMeterId(String fieldName){
        hashMapCode.put("Dimand",17);
        hashMapCode.put("Active1",1);
        hashMapCode.put("Active2",2);
        hashMapCode.put("Active3",3);
        hashMapCode.put("Reactive",5);
        hashMapCode.put("Holiday",11);
        hashMapCode.put("SerialNum1",28);
        hashMapCode.put("ActiveSum",21);
        hashMapCode.put("ReversEnerji",23);


        return hashMapCode.get(fieldName);
    }
    public String getReadMeterFieldName(Integer readMeterId){
        hashMapName.put(17,"Dimand");
        hashMapName.put(1,"Active1");
        hashMapName.put(2,"Active2");
        hashMapName.put(3,"Active3");
        hashMapName.put(5,"Reactive");
        hashMapName.put(11,"Holiday");
        hashMapName.put(28,"SerialNum1");
        hashMapName.put(21,"ActiveSum");
        hashMapName.put(23,"ReversEnerji");

        return hashMapName.get(readMeterId);
    }
}
