package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.RelUser;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface RelUserDao {

    @Query("select * from RelUser")
    LiveData<List<RelUser>> getUsers();

    @Query("select * from RelUser where UserID = :UserID ")
    LiveData<RelUser> getUserById(int UserID);

    @Insert(onConflict = IGNORE)
    void insert1Reluser(RelUser relUser);

    @Update
    void updateRelUser(RelUser relUser);

    @Query("select * from RelUser where UserID = :UserID and HandheldPass= :Password ")
    LiveData<RelUser> getUserByUserAndPassword(int UserID,String Password);

    @Query("DELETE FROM RelUser")
    void deleteAll();

    @Delete
    void deleteUser(RelUser relUser);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceUsers(RelUser... users);


}
