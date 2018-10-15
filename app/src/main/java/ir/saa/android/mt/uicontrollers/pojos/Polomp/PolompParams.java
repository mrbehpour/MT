package ir.saa.android.mt.uicontrollers.pojos.Polomp;

import java.io.Serializable;

public class PolompParams implements Serializable {

    public Integer PolompId;
    public Long ClientId;
    public String PolompName;

    public PolompParams(Integer polompId, Long clientId,String polompName){
        this.ClientId=clientId;
        this.PolompId=polompId;
        this.PolompName=polompName;
    }

}
