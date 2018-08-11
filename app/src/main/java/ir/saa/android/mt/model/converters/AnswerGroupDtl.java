package ir.saa.android.mt.model.converters;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswerGroupDtl {

    //@SerializedName("AnswerGroupDtlID")
    @Expose
    private int AnswerGroupDtlID;

    //SerializedName("AnswerGroupDtlName")
    @Expose
    private String AnswerGroupDtlName;

    //@SerializedName("AnswerGroupID")
    @Expose
    //@ColumnInfo(name = "answergroup_id")
    private int AnswerGroupID;


    //@SerializedName("Description")
    @Expose
    private String Description;

    public int getAnswerGroupDtlID() {
        return AnswerGroupDtlID;
    }

    public void setAnswerGroupDtlID(int answerGroupDtlID) {
        AnswerGroupDtlID = answerGroupDtlID;
    }

    public String getAnswerGroupDtlName() {
        return AnswerGroupDtlName;
    }

    public void setAnswerGroupDtlName(String answerGroupDtlName) {
        AnswerGroupDtlName = answerGroupDtlName;
    }

    public int getAnswerGroupID() {
        return AnswerGroupID;
    }

    public void setAnswerGroupID(int answerGroupID) {
        AnswerGroupID = answerGroupID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
