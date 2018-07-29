package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.Client;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ClientDao {
    @Query("select * from Client")
    LiveData<List<Client>> getClients();

    @Update
    void updateClient(Client client);

    @Insert(onConflict = IGNORE)
    void insertClient(Client client);

    @Insert(onConflict = IGNORE)
    List<Long> insertClients(List<Client> clients);

    @Delete
    void deleteClient(Client client);

    @Query("select * from Client where ClientID = :clientId")
    LiveData<Client> getClientById(long clientId);

    @Query("Delete From Client")
    void deleteAll();

    @Query("Delete from Client where ClientID= :clientId")
    void deleteById(long clientId);

}
