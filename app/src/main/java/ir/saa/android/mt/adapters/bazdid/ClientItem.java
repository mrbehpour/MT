package ir.saa.android.mt.adapters.bazdid;

public class ClientItem {
    public Long Id;
    public String Name;
    public String Address;
    public String UniqueFieldTitle;
    public String UniqueFieldValue;
    public Integer Pic;
    public Integer SendId;
    public Boolean isTest;
    public Boolean isBazrasi;
    public Boolean isPolommp;
    public Integer GroupId;
    public Integer RowId;
    public Long FollowUpCode;

    public ClientItem(Long id,String name,String address,String uniqueFieldTitle,String uniqueFieldValue,Integer pic,Integer SendId,
                      Integer groupID,Boolean isTest,Boolean isPolomp,Boolean isBazrasi,Long followUpCode,Integer rowId){
        this.Id = id;
        this.Name = name;
        this.Address = address;
        this.UniqueFieldTitle = uniqueFieldTitle;
        this.UniqueFieldValue = uniqueFieldValue;
        this.Pic = pic;
        this.SendId=SendId;
        this.GroupId=groupID;
        this.isBazrasi=isBazrasi;
        this.isTest=isTest;
        this.isPolommp=isPolomp;
        this.FollowUpCode=followUpCode;
        this.RowId=rowId;
    }
}
