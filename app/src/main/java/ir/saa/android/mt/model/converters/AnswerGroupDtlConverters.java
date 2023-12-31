package ir.saa.android.mt.model.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.model.entities.AnswerGroupDtl;

public class AnswerGroupDtlConverters {
    @TypeConverter
    public static List<AnswerGroupDtl> fromString(String value) {
        Type listType = new TypeToken<List<AnswerGroupDtl>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromArrayLisr(List<AnswerGroupDtl> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
