package ir.saa.android.mt.repositories.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = {@ForeignKey(entity =AnswerGroup.class,parentColumns = "AnswerGroupID",childColumns = "answergroup_id")})
public class AnswerGroupDtl {

    @PrimaryKey
    @NonNull
    public int AnswerGroupDtlID ;

    public String AnswerGroupDtlName ;

    @ColumnInfo(name = "answergroup_id")
    public int AnswerGroupID ;

    public String Description ;
}
