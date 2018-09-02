package ir.saa.android.mt.adapters.bazdid;

public class ClientItem {
    public Long Id;
    public String Name;
    public String Address;
    public String UniqueFieldTitle;
    public String UniqueFieldValue;
    public Integer Pic;
    public Integer SendId;

    public ClientItem(Long id,String name,String address,String uniqueFieldTitle,String uniqueFieldValue,Integer pic,Integer SendId){
        this.Id = id;
        this.Name = name;
        this.Address = address;
        this.UniqueFieldTitle = uniqueFieldTitle;
        this.UniqueFieldValue = uniqueFieldValue;
        this.Pic = pic;
        this.SendId=SendId;
    }
}
