package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

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


    public List<AnswerGroupDtl> answerGroupDtls;

}
