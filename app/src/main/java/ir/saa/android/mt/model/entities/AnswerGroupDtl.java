package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
        //(foreignKeys = {@ForeignKey(entity =AnswerGroup.class,parentColumns = "AnswerGroupID",childColumns = "answergroup_id")})
public class AnswerGroupDtl {


    @PrimaryKey
    @NonNull
    @SerializedName("AnswerGroupDtlID")
    @Expose
    public int AnswerGroupDtlID ;

    @SerializedName("AnswerGroupDtlName")
    @Expose
    public String AnswerGroupDtlName ;

    @SerializedName("AnswerGroupID")
    @Expose
    @ColumnInfo(name = "answergroup_id")
    public int AnswerGroupID ;

    @SerializedName("Description")
    @Expose
    public String Description ;

    public int AnswerGroupDtlColor;
}
