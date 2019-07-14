package ir.saa.android.mt.adapters.moshahedat;

public class MoshahedatItem {

    public Integer Id;
    public String RemarkName;
    public Integer answerGroupId;
    public String remarkValue;
    public String AnswerCaption;
    public Integer PropertyTypeID;

    public MoshahedatItem(Integer Id,String RemarkName,Integer AnswerGroupId,String RemarkValue,String answerCaption
            ,Integer propertyTypeID){
        this.Id=Id;
        this.RemarkName=RemarkName;
        this.answerGroupId=AnswerGroupId;
        this.remarkValue=RemarkValue;
        this.AnswerCaption=answerCaption;
        this.PropertyTypeID=propertyTypeID;

    }
}
