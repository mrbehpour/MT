package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.AgentAccessListDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.AgentAccessList;

public class AgentAccessListRepo {
    private AgentAccessListDao agentAccessListDao;
    private LiveData<List<AgentAccessList>> agentAccessLists;

    AgentAccessListRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        agentAccessListDao=db.agentAccessListModel();
        agentAccessLists=agentAccessListDao.getAgentAccessList();
    }

    public LiveData<List<AgentAccessList>> getAgentAccessList(){
        return agentAccessLists;
    }

    public LiveData<AgentAccessList>  getAgentAccessListById(int Id){
        return agentAccessListDao.getAgentAccessListById(Id);
    }

    public void deleteAllAgentAccessList(){
        agentAccessListDao.deleteAll();
    }
    public List<Long> insertAgentAccessLists(List<AgentAccessList> agentAccessLists){
        return agentAccessListDao.insertAgentAccessLists(agentAccessLists);
    }

    public void updateAgentAccessList(AgentAccessList agentAccessList){
        agentAccessListDao.updateAgentAccessList(agentAccessList);
    }

    public void deleteAgentAccessListById(int Id){
        agentAccessListDao.deleteById(Id);
    }

    public void insertAgentAccessList(AgentAccessList agentAccessList){
           agentAccessListDao.insertAgentAccessList(agentAccessList);
    }

}
