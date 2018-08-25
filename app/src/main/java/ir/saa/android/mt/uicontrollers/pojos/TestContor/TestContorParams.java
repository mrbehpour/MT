package ir.saa.android.mt.uicontrollers.pojos.TestContor;

import java.io.Serializable;

public class TestContorParams implements Serializable {
    public Boolean Active;
    public Boolean SinglePhase;
    public Boolean FisrtTest;
    public int CTCoeff;
    public int ContorConst;
    public int SensorRatio;
    public int RoundNum;

    public TestContorParams(Boolean Active,Boolean SinglePhase,Boolean FisrtTest,int CTCoeff,int ContorConst,int SensorRatio,int RoundNum){
        this.Active=Active;
        this.SinglePhase=SinglePhase;
        this.FisrtTest=FisrtTest;
        this.CTCoeff=CTCoeff;
        this.ContorConst=ContorConst;
        this.SensorRatio=SensorRatio;
        this.RoundNum=RoundNum;
    }
}
