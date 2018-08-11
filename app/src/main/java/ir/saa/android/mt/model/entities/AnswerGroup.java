package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.saa.android.mt.model.converters.AnswerGroupDtlConverter;

@Entity
public class AnswerGroup {

    @PrimaryKey
    @NonNull
    public int AnswerGroupID ;


    public String AnswerGroupName ;


    public String Description ;


    public Boolean IsEditable ;


    public Boolean EditDtl ;

}

