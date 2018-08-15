package ir.saa.android.mt.uicontrollers.pojos.TestContor;

public class TestContorParams {
    public Boolean Active;
    public Boolean SinglePhase;
    public int TestNum;
    public int ContorCoeff;
    public int ContorConst;
    public int ContorRatio;
    public int RoundNum;

    public TestContorParams(Boolean Active,Boolean SinglePhase,int TestNum,int ContorCoeff,int ContorConst,int ContorRatio,int RoundNum){
        this.Active=Active;
        this.SinglePhase=SinglePhase;
        this.TestNum=TestNum;
        this.ContorCoeff=ContorCoeff;
        this.ContorConst=ContorConst;
        this.ContorRatio=ContorRatio;
        this.RoundNum=RoundNum;
    }
}
