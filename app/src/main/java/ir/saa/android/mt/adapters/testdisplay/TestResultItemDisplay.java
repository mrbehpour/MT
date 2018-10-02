package ir.saa.android.mt.adapters.testdisplay;

public class TestResultItemDisplay {
    public int RoundNum;
    public double ErrPerc;
    public double PF_A;
    public double PF_B;
    public double PF_C;
    public String MeterEnergy_Period1_A;
    public String MeterEnergy_Period1_B;
    public String MeterEnergy_Period1_C;
    public String Time_Period1;
    public String AIRMS_Period1;
    public String BIRMS_Period1;
    public String CIRMS_Period1;
    public String NIRMS_Period1;
    public String AVRMS_Period1;
    public String BVRMS_Period1;
    public String CVRMS_Period1;
    public String ANGLE0_Period1;
    public String ANGLE1_Period1;
    public String ANGLE2_Period1;
    public String Period_Period1_A;
    public String Period_Period1_B;
    public String Period_Period1_C;
    public double Pow_A;
    public double Pow_B;
    public double Pow_C;
    public Boolean Active;
    public Boolean SinglePhase;
    public Boolean FisrtTest;
    public int CTCoeff;
    public int ContorConst;
    public int SensorRatio;
    public int RoundNumForTest;
    public double Q_A;
    public double Q_B;
    public double Q_C;
    public double S_A;
    public double S_B;
    public double S_C;

    public TestResultItemDisplay(int RoundNum, double ErrPerc, double PF_A, double PF_B, double PF_C, String MeterEnergy_Period1_A,String MeterEnergy_Period1_B,
                                 String MeterEnergy_Period1_C,String Time_Period1,String AIRMS_Period1, String BIRMS_Period1,String CIRMS_Period1, String NIRMS_Period1,
                                 String AVRMS_Period1,String BVRMS_Period1,String CVRMS_Period1,String ANGLE0_Period1,String ANGLE1_Period1, String ANGLE2_Period1,
                                 String Period_Period1_A,String Period_Period1_B,String Period_Period1_C,double Pow_A,double Pow_B,double Pow_C,Boolean Active,
                                 Boolean SinglePhase,Boolean FisrtTest,int CTCoeff, int ContorConst, int SensorRatio, int RoundNumForTest,Double Q_A,Double Q_B,Double Q_C,
                                 Double S_A,Double S_B,Double S_C){


        this.RoundNum=RoundNum;
        this.ErrPerc=ErrPerc;
        this.PF_A=PF_A;
        this.PF_B=PF_B;
        this.PF_C=PF_C;
        this.MeterEnergy_Period1_A=MeterEnergy_Period1_A;
        this.MeterEnergy_Period1_B=MeterEnergy_Period1_B;
        this.MeterEnergy_Period1_C=MeterEnergy_Period1_C;
        this.Time_Period1=Time_Period1;
        this.AIRMS_Period1=AIRMS_Period1;
        this.BIRMS_Period1=BIRMS_Period1;
        this.CIRMS_Period1=CIRMS_Period1;
        this.NIRMS_Period1=NIRMS_Period1;
        this.AVRMS_Period1=AVRMS_Period1;
        this.BVRMS_Period1=BVRMS_Period1;
        this.CVRMS_Period1=CVRMS_Period1;
        this.ANGLE0_Period1=ANGLE0_Period1;
        this.ANGLE1_Period1=ANGLE1_Period1;
        this.ANGLE2_Period1=ANGLE2_Period1;
        this.Period_Period1_A=Period_Period1_A;
        this.Period_Period1_B=Period_Period1_B;
        this.Period_Period1_C=Period_Period1_C;
        this.Pow_A=Pow_A;
        this.Pow_B=Pow_B;
        this.Pow_C=Pow_C;
        this.Active=Active;
        this.SinglePhase=SinglePhase;
        this.FisrtTest=FisrtTest;
        this.CTCoeff=CTCoeff;
        this.ContorConst=ContorConst;
        this.SensorRatio=SensorRatio;
        this.RoundNumForTest=RoundNumForTest;
        this.Q_A=Q_A;
        this.Q_B=Q_B;
        this.Q_C=Q_C;
        this.S_A=S_A;
        this.S_B=S_B;
        this.S_C=S_C;

    }


}
