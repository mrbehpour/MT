package ir.saa.android.mt.model.entities;

import java.util.List;

public class ClientAllInfo {

    public int Id ;

    //public string Guid ;
    public Integer SendId;

    //public Integer

    public AddedClient AddedClient ;

    public String RecordStringInfo ;

    public Client Client ;

    public InspectionInfo InspectionInfo ;

    public List<InspectionDtl> InspectionDtls ;

    public MeterChangeInfo MeterChangeInfo ;

    public List<MeterChangeDtl> MeterChangeDtls ;

    public PolompInfo PolompInfo ;

    public GPSInfo GpsInfo ;

    public List<PolompDtl> PolompDtls ;

    public TariffInfo TariffInfo ;

    public List<TariffDtl> TariffDtls ;

    public List<TestInfo> TestInfos ;

    public List<List<TestDtl>> TestDtlsList ;
}
