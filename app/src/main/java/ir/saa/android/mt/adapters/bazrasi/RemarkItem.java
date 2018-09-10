package ir.saa.android.mt.adapters.bazrasi;

public class RemarkItem {

    public Integer Id;
    public String RemarkName;
    public Integer answerGroupId;
    public String remarkValue;

    public RemarkItem(Integer Id,String RemarkName,Integer AnswerGroupId,String RemarkValue){
        this.Id=Id;
        this.RemarkName=RemarkName;
        this.answerGroupId=AnswerGroupId;
        this.remarkValue=RemarkValue;
    }

}
