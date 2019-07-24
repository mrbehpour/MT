package ir.saa.android.mt.adapters.answerGroup;

public class AnswerGroupItem {

    public int AnswerGroupId;

    public String AnswerGroupName;

    public int AnswerGroupColor;

    public AnswerGroupItem(int _AnswerGroupId,String _AnswerGroupName,int _AnswerGroupColor){
        this.AnswerGroupColor=_AnswerGroupColor;
        this.AnswerGroupId=_AnswerGroupId;
        this.AnswerGroupName=_AnswerGroupName;
    }
}
