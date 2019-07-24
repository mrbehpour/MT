package ir.saa.android.mt.adapters.bazrasi;

import android.location.Location;

public class RemarkItem {

    public Integer Id;
    public String RemarkName;
    public Integer answerGroupId;
    public String remarkValue;
    public String AnswerCaption;



    public RemarkItem(Integer Id,String RemarkName,Integer AnswerGroupId,String RemarkValue,String answerCaption){
        this.Id=Id;
        this.RemarkName=RemarkName;
        this.answerGroupId=AnswerGroupId;
        this.remarkValue=RemarkValue;
        this.AnswerCaption=answerCaption;

    }

}
