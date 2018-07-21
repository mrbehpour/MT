package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.AccessAgentAndroid;
import ir.saa.android.mt.model.entities.RelUser;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface AccessAgentAndroidDao {

    @Query("select * from AccessAgentAndroid")
    LiveData<List<AccessAgentAndroid>> getAccessAgentAndroids();

    @Delete
    void deleteAccessAgentAndroid(AccessAgentAndroid accessAgentAndroid);

    @Insert(onConflict = IGNORE)
    void insertAccessAgentAndroid(AccessAgentAndroid accessAgentAndroid);

    @Update
    void updateAccessAgentAndroid(AccessAgentAndroid accessAgentAndroid);

    @Query("DELETE FROM AccessAgentAndroid")
    void deleteAll();

    @Query("select * from AccessAgentAndroid where user_id= :userid")
    LiveData<AccessAgentAndroid> getAccessAgentAndroidByUserid(int userid);

    @Query("select * from AccessAgentAndroid where ID = :id")
    LiveData<AccessAgentAndroid> getAccessAgentAndroidById(int id);

    @Query("Delete from AccessAgentAndroid where ID= :Id")
    void deleteById(int Id);

}
