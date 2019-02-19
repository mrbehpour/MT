package ir.saa.android.mt.repositories.metertester;

public class TestResult {
    public int RoundNum;
    //درصد خطا
    public double ErrPerc;
    //ضریب توان
    public double PF_A;
    public double PF_B;
    public double PF_C;
    //انرژی
    public String MeterEnergy_Period1_A;
    public String MeterEnergy_Period1_B;
    public String MeterEnergy_Period1_C;

    public String Time_Period1;
    //جریان
    public String AIRMS_Period1;
    public String BIRMS_Period1;
    public String CIRMS_Period1;
    public String NIRMS_Period1;
    //ولتاژ
    public String AVRMS_Period1;
    public String BVRMS_Period1;
    public String CVRMS_Period1;
    //زاویه
    public String ANGLE0_Period1;
    public String ANGLE1_Period1;
    public String ANGLE2_Period1;

    public String Period_Period1_A;
    public String Period_Period1_B;
    public String Period_Period1_C;
    //توان اکتیو
    public double Pow_A;
    public double Pow_B;
    public double Pow_C;
    //توان رآکتیو
    public double Q_A;
    public double Q_B;
    public double Q_C;
    //توان ظاهری
    public double S_A;
    public double S_B;
    public double S_C;
}
