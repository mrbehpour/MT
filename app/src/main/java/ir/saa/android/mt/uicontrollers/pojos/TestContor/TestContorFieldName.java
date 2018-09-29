package ir.saa.android.mt.uicontrollers.pojos.TestContor;

import java.lang.reflect.Field;
import java.util.HashMap;

public class TestContorFieldName {
    HashMap<String,Integer> hashMapCode=new HashMap<>();
    HashMap<Integer,String> hashMapName=new HashMap<>();

    public void TestContorFieldName(){
        hashMapCode.put("ContorConst",21);
        hashMapCode.put("CTCoeff",23);
        hashMapCode.put("SensorRatio",22);
        hashMapCode.put("testContorParams_RoundNum",24);
        hashMapCode.put("ErrPerc",41);
        hashMapCode.put("PF_A",13);
        hashMapCode.put("PF_B",14);
        hashMapCode.put("PF_C",15);
        hashMapCode.put("RoundNum",29);
        hashMapCode.put("AIRMS_Period1",4);
        hashMapCode.put("BIRMS_Period1",5);
        hashMapCode.put("CIRMS_Period1",6);
        hashMapCode.put("NIRMS_Period1",43);
        hashMapCode.put("AVRMS_Period1",1);
        hashMapCode.put("BVRMS_Period1",2);
        hashMapCode.put("CVRMS_Period1",3);
        hashMapCode.put("Period_Period1_A",20);

        hashMapName.put(21,"ContorConst");
        hashMapName.put(23,"CTCoeff");
        hashMapName.put(22,"SensorRatio");
        hashMapName.put(24,"testContorParams_RoundNum");
        hashMapName.put(41,"ErrPerc");
        hashMapName.put(13,"PF_A");
        hashMapName.put(14,"PF_B");
        hashMapName.put(15,"PF_C");
        hashMapName.put(29,"RoundNum");
        hashMapName.put(4,"AIRMS_Period1");
        hashMapName.put(5,"BIRMS_Period1");
        hashMapName.put(6,"CIRMS_Period1");
        hashMapName.put(43,"NIRMS_Period1");
        hashMapName.put(1,"AVRMS_Period1");
        hashMapName.put(2,"BVRMS_Period1");
        hashMapName.put(3,"CVRMS_Period1");
        hashMapName.put(20,"Period_Period1_A");
        }

        public Integer getTestId(String fieldName){

            return hashMapCode.get(fieldName);
        }



    public static boolean set(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }
}
