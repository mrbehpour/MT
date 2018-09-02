package ir.saa.android.mt.uicontrollers.pojos.Polomp;

import java.io.Serializable;

public class PolompParams implements Serializable {

    public Integer PolompId;
    public Long ClientId;

    public PolompParams(Integer polompId, Long clientId){
        this.ClientId=clientId;
        this.PolompId=polompId;
    }

}
