package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.AgentAccessList;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface AgentAccessListDao {
    @Query("select * from AgentAccessList")
    LiveData<List<AgentAccessList>> getAgentAccessList();


    @Insert(onConflict = IGNORE)
    void insertAgentAccessList(AgentAccessList agentAccessList);

    @Insert(onConflict = IGNORE)
    List<Integer> insertAgentAccessLists(List<AgentAccessList> agentAccessLists);

    @Delete
    void deleteAgentAccessList(AgentAccessList agentAccessList);


    @Update
    void updateAgentAccessList(AgentAccessList agentAccessList);

    @Query("Delete From AgentAccessList")
    void deleteAll();

    @Query("select * from AgentAccessList where AccessID = :accessId")
    LiveData<AgentAccessList> getAgentAccessListById(Integer accessId);

    @Query("delete from AgentAccessList where AccessID= :accessId")
    void deleteById(Integer accessId);


}
