package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

import ir.saa.android.mt.model.entities.Bazdid;
import ir.saa.android.mt.model.entities.Report;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface BazdidDao {

    @Query("Select * from Bazdid")
    LiveData<List<Bazdid>> getBazdids();


    @Insert(onConflict = IGNORE)
    Long insertBazdid(Bazdid  bazdid);

    @Update
    void updateBazdid(Bazdid bazdid);


    @Query("Select * from Bazdid where ClientId= :iD")
    Bazdid getBazdid(Long iD);

    @Delete
    void deleteBazdid(Bazdid bazdid);

    @Query("Delete from Bazdid")
    void deleteAll();

    @Query("Delete from Bazdid where ClientId= :iD")
    void deleteBazdidById(Long iD);


    @Query("Select * from Bazdid where isSend= :hasSend")
    List<Bazdid> getBazdidIsSend(Boolean hasSend);

    @Query("Select client.RegionID," +
            "Count(Client.ClientID) as ClientCount," +
            "Count(TestInfo.ClientID) as TestCount, " +
            "Count(PolompInfo.ClientID) as PolompCount," +
            "Count(TariffInfo.ClientID) as TriffCount," +
            "Count(InspectionInfo.ClientID) as BazrasiCount"+
            " from Client " +
            "Left join (select distinct * from TestInfo) as TestInfo on TestInfo.ClientID=Client.ClientID " +
            "left join (select distinct * from PolompInfo) as PolompInfo on PolompInfo.ClientID=Client.ClientID " +
            "left join (select distinct * from InspectionInfo) as InspectionInfo on InspectionInfo.ClientID=Client.ClientID " +
            "left join (select distinct * from TariffInfo) as TariffInfo on TariffInfo.ClientID=Client.ClientID " +
            "Where Client.RegionID=:regionId " +
            "group by Client.RegionID"
    )
    LiveData<Report> getReportByRegionId(Integer regionId);

    @Query("Select " +
            "Count(Client.ClientID) as ClientCount," +
            "Count(TestInfo.ClientID) as TestCount, " +
            "Count(PolompInfo.ClientID) as PolompCount," +
            "Count(TariffInfo.ClientID) as TriffCount," +
            "Count(InspectionInfo.ClientID) as BazrasiCount"+
            " from Client " +
            "Left join (select distinct * from TestInfo) as TestInfo on TestInfo.ClientID=Client.ClientID " +
            "left join (select distinct * from PolompInfo) as PolompInfo on PolompInfo.ClientID=Client.ClientID " +
            "left join (select distinct * from InspectionInfo) as InspectionInfo on InspectionInfo.ClientID=Client.ClientID " +
            "left join (select distinct * from TariffInfo) as TariffInfo on TariffInfo.ClientID=Client.ClientID "

    )
    LiveData<Report> getReport();


}
