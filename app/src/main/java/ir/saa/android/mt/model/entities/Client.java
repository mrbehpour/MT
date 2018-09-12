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
    public Long ClientID ;

    public Long SubScript ;

    public Integer RegionID ;

    public Integer ClientCity ;

    public Integer ClientRow ;

    public Integer Mamoor ;

    public Integer RoozKar ;

    @ColumnInfo(name = "tarifftype_id")
    public Integer TariffTypeID ;

    @ColumnInfo(name = "clienttype_id")
    public Integer ClientTypeID ;

    public Long FileID ;

    public Long ClientPass ;

    public Integer Amp ;

    public Long MeterNumActive ;

    @ColumnInfo(name = "mastergroupdtl_id")
    public Integer MasterGroupDtlID ;

    public String CustId ;

    public Integer Faz ;

    public String Name ;

    public String Address ;

    public Long Tel ;

    public Long PostalCode ;

    public String Pelak ;

    public Integer Demand ;

    public Integer NumContract ;

    public Integer Zarib ;

    public Integer SendId;

    public Integer MxmeterCode ;

    public Integer MxMeterZarib ;

    public Integer PosType ;

    public Integer DurationType ;

    public Integer ActiveTariffCount ;

    public byte Status ;

    public Integer InsDateContor ;

    public Integer ChngDateContor ;

    public Integer NumDigitContor ;

    public Integer KindVolt ;

    public Integer IDInst ;

    public Integer LastReadDate ;

    public Integer Active1 ;

    public Integer Active2 ;

    public Integer Active3 ;

    public Integer ActiveT1 ;

    public Integer MxValue ;

    public Integer UseAvrA ;

    public Integer UseAvrR ;

}
