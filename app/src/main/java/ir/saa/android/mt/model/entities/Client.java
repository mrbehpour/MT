package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
//        (foreignKeys = {@ForeignKey(entity = ClientType.class,parentColumns = "FldID",childColumns = "clienttype_id"),
//                       @ForeignKey(entity = TariffType.class,parentColumns = "FldID",childColumns = "tarifftype_id"),
//                       @ForeignKey(entity = MasterGroupDetail.class,parentColumns = "MasterGroupDtlID",childColumns = "mastergroupdtl_id")
//})
public class Client  {

    @PrimaryKey
    @NonNull
    public long ClientID ;

    public long SubScript ;

    public int RegionID ;

    public int ClientCity ;

    public int ClientRow ;

    public int Mamoor ;

    public int RoozKar ;

    @ColumnInfo(name = "tarifftype_id")
    public int TariffTypeID ;

    @ColumnInfo(name = "clienttype_id")
    public int ClientTypeID ;

    public long FileID ;

    public long ClientPass ;

    public long Amp ;

    public long MeterNumActive ;

    @ColumnInfo(name = "mastergroupdtl_id")
    public int MasterGroupDtlID ;

    public String CustId ;

    public int Faz ;

    public String Name ;

    public String Address ;

    public long Tel ;

    public long PostalCode ;

    public String Pelak ;

    public int Demand ;

    public int NumContract ;

    public int Zarib ;

    public int SendId;

    public int MxmeterCode ;

    public int MxMeterZarib ;

    public int PosType ;

    public int DurationType ;

    public int ActiveTariffCount ;

    public byte Status ;

    public int InsDateContor ;

    public int ChngDateContor ;

    public int NumDigitContor ;

    public int KindVolt ;

    public int IDInst ;

    public int LastReadDate ;

    public int Active1 ;

    public int Active2 ;

    public int Active3 ;

    public int ActiveT1 ;

    public int MxValue ;

    public int UseAvrA ;

    public int UseAvrR ;

    public Long FollowUpCode;

    public boolean forcibleMasterGroup;

}
