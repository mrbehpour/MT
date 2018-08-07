package ir.saa.android.mt.model.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import ir.saa.android.mt.model.entities.AnswerGroupDtl;

public class AnswerGroupDtlConverter {
    private static Gson gson;
     //gson = new Gson();

    @TypeConverter
    public static List<AnswerGroupDtl> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<AnswerGroupDtl>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<AnswerGroupDtl> someObjects) {
        return gson.toJson(someObjects);
    }
}
