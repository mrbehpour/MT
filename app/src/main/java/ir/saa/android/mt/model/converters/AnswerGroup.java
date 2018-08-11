package ir.saa.android.mt.model.converters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.saa.android.mt.model.entities.AnswerGroupDtl;

public class AnswerGroup {
    public int AnswerGroupID ;


    public String AnswerGroupName ;


    public String Description ;


    public Boolean IsEditable ;


    public Boolean EditDtl ;


    //@SerializedName("answerGroupDtls")
    @Expose
    private List<AnswerGroupDtl> answerGroupDtls;

    public List<AnswerGroupDtl> getAnswerGroupDtls() {
        return answerGroupDtls;
    }

    public void setAnswerGroupDtls(List<AnswerGroupDtl> answerGroupDtls) {
        this.answerGroupDtls = answerGroupDtls;
    }
}
