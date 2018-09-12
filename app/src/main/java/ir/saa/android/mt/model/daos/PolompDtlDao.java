package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.PolompAllInfo;
import ir.saa.android.mt.model.entities.PolompDtl;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface PolompDtlDao {

    @Query("Select * from PolompDtl")
    LiveData<List<PolompDtl>> getPolompDtls();

    @Query("Select * From PolompDtl where PolompDtlID=:Id")
    LiveData<PolompDtl> getPolompDtlById(Integer Id);

    @Query("Select * from PolompDtl where PolompInfoID=:Id")
    LiveData<List<PolompDtl>> getPolompDtlByPolompInfoId(Integer Id);

    @Query("Delete From PolompDtl")
    void deleteAll();

    @Query("Delete From PolompDtl Where PolompDtlID=:Id")
    void deletePolompDtlById(Integer Id);

    @Delete
    void deletePolompDtl(PolompDtl polompDtl);

    @Insert(onConflict = IGNORE)
    List<Long> insertPolompDtls(List<PolompDtl> polompDtls);

    @Insert(onConflict = IGNORE)
    Long insertPolompDtl(PolompDtl polompDtl);

    @Update
    void updatePolompDtl(PolompDtl polompDtl);

    @Query("select * from PolompInfo" +
            " inner join PolompDtl on PolompInfo.PolompInfoID=PolompDtl.PolompInfoID " +
            "Where PolompInfo.ClientID=:clientId and PolompDtl.PolompID=:polompId")
    LiveData<PolompAllInfo> getPolompAllInfoLiveData(Long clientId, Integer polompId);

    @Query("select * from PolompInfo" +
            " inner join PolompDtl on PolompInfo.PolompInfoID=PolompDtl.PolompInfoID " +
            "Where PolompInfo.ClientID=:clientId and PolompDtl.PolompID=:polompId")
    PolompAllInfo getPolompAllInfo(Long clientId, Integer polompId);


    @Query("select * from PolompInfo" +
            " inner join PolompDtl on PolompInfo.PolompInfoID=PolompDtl.PolompInfoID " +
            "Where PolompInfo.ClientID=:clientId and PolompInfo.SendID=:SendId")
    List<PolompAllInfo> getPolompAllInfoWithSendId(Long clientId, Integer SendId);

}
