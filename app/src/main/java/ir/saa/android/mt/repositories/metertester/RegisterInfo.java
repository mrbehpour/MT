package ir.saa.android.mt.repositories.metertester;


public class RegisterInfo {
    public regNames registerName;
    public int registerAddress;
    public int registerLenght;

    public RegisterInfo(){
    }

    public RegisterInfo(regNames _registerName,int _registerAddress,int _registerLenght){
        registerName = _registerName;
        registerAddress = _registerAddress;
        registerLenght = _registerLenght;
    }

    public enum regNames{
        ElecParams_ALL,
        ElecParams_PA,
        ElecParams_PB,
        ElecParams_PC,
        Test_Command
    }
}
