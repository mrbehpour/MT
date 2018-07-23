package ir.saa.android.mt.repositories.model.daos;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.repositories.model.entities.ClientType;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ClientTypeDao {

    @Query("select * from ClientType")
    LiveData<List<ClientType>> getClientTypes();


    @Update
    void updateClientType(ClientType clientType);

    @Insert(onConflict = IGNORE)
    void insertClientType(ClientType clientType);

    @Delete
    void deleteClientType(ClientType clientType);

    @Query("Delete from ClientType")
    void deleteAll();

    @Query("select * from ClientType where FldID= :Id")
    LiveData<ClientType> getClientTypeById(byte Id);

    @Query("Delete from ClientType where FldID= :Id")
    void deleteClientTypeById(byte Id);





}
