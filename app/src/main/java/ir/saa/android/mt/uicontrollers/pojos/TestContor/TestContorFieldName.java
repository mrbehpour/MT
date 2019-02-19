package ir.saa.android.mt.uicontrollers.pojos.TestContor;

import java.lang.reflect.Field;
import java.util.HashMap;

public class TestContorFieldName {
    HashMap<String,Integer> hashMapCode=new HashMap<>();
    HashMap<Integer,String> hashMapName=new HashMap<>();

    public void TestContorFieldName(){



        }

        public Integer getTestId(String fieldName){
            hashMapCode.put("ContorConst",21);
            hashMapCode.put("CTCoeff",23);
            hashMapCode.put("SensorRatio",22);
            hashMapCode.put("testContorParams_RoundNum",24);
            hashMapCode.put("ErrPerc",40);
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
            hashMapCode.put("Q_A",10);
            hashMapCode.put("Q_B",11);
            hashMapCode.put("Q_C",12);
            hashMapCode.put("Pow_A",7);
            hashMapCode.put("Pow_B",8);
            hashMapCode.put("Pow_C",9);
            hashMapCode.put("S_A",16);
            hashMapCode.put("S_B",17);
            hashMapCode.put("S_C",18);
            hashMapCode.put("Time_Period1",32);
            return hashMapCode.get(fieldName);
        }

        public String getTestFieldName(Integer testId){
            hashMapName.put(21,"ContorConst");
            hashMapName.put(23,"CTCoeff");
            hashMapName.put(22,"SensorRatio");
            hashMapName.put(24,"testContorParams_RoundNum");
            hashMapName.put(40,"ErrPerc");
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
            hashMapName.put(10,"Q_A");
            hashMapName.put(11,"Q_B");
            hashMapName.put(12,"Q_C");
            hashMapName.put(7,"Pow_A");
            hashMapName.put(8,"Pow_B");
            hashMapName.put(9,"Pow_C");
            hashMapName.put(16,"S_A");
            hashMapName.put(17,"S_B");
            hashMapName.put(18,"S_C");
            hashMapName.put(32,"Time_Period1");
            return hashMapName.get(testId);
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
