package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.Setting;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface SettingDao {
    @Query("select * from Setting")
    LiveData<List<Setting>> getSettings();

    @Query("select * from Setting where SettingID =:Id")
    LiveData<Setting> getSettingById(Integer Id);

    @Insert(onConflict = IGNORE)
    void insertSetting(Setting setting);


    @Insert(onConflict = IGNORE)
    List<Long> insertSettings(List<Setting> setting);

    @Update
    void updateSetting(Setting setting);

    @Delete
    void deleteSetting(Setting setting);

    @Query("delete from Setting")
    void deleteAll();

    @Query("delete from Setting where SettingID=:Id")
    void deleteSettingById(Integer Id);


}
