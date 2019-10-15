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
    public Boolean isTariff;
    public Boolean isBlock;
    public Boolean isBlockTest;
    public Integer GroupId;
    public Integer RowId;
    public Long FollowUpCode;
    public boolean forcibleMasterGroup;

    public ClientItem(Long id,String name,String address,String uniqueFieldTitle,String uniqueFieldValue,Integer pic,Integer SendId,
                      Integer groupID,Boolean isTest,Boolean isPolomp,Boolean isBazrasi,boolean IsTariff,Boolean isBlock,Boolean isBlockTest,Long followUpCode,Integer rowId,
                      boolean forcibleMasterGroup){
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
        this.isTariff=IsTariff;
        this.isBlock=isBlock;
        this.isPolommp=isPolomp;
        this.FollowUpCode=followUpCode;
        this.isBlockTest = isBlockTest;
        this.RowId=rowId;
        this.forcibleMasterGroup=forcibleMasterGroup;
    }
}
