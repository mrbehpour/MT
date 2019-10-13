package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.BlockTestDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.BlockTest;

public class BlockTestRepo  {
    BlockTestDao blockTestDao;
    LiveData<List<BlockTest>> listLiveData;
    public BlockTestRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        blockTestDao=db.blockTestModel();
        listLiveData=blockTestDao.getBlockTestLiveData();
    }


    public LiveData<List<BlockTest>> getBlockTestLiveData() {
        return listLiveData;
    }


    public List<BlockTest> getBlockTest() {
        return blockTestDao.getBlockTest();
    }


    public List<BlockTest> getBlockTestByClientId(Long clientId) {
        return blockTestDao.getBlockTestByClientId(clientId);
    }


    public LiveData<List<BlockTest>> getBlockTestByClientIdLiveData(Long clientId) {
        return blockTestDao.getBlockTestByClientIdLiveData(clientId);
    }


    public long insertBlockTest(BlockTest blockTest) {
        return blockTestDao.insertBlockTest(blockTest);
    }


    public void updateBlockTest(BlockTest blockTest) {
        blockTestDao.updateBlockTest(blockTest);
    }


    public void deleteBlockTest(BlockTest blockTest) {
        blockTestDao.deleteBlockTest(blockTest);
    }


    public void deleteAll() {
        blockTestDao.deleteAll();
    }


    public void deleteById(int id) {
        blockTestDao.deleteById(id);
    }


    public void deleteByClientId(Long clientId) {
        blockTestDao.deleteByClientId(clientId);
    }

    public LiveData<List<BlockTest>> getBlockTestByBlockIdListLiveData(int blockId){
        return blockTestDao.getBlockTestByBlockIdLiveData(blockId);
    }
    public List<BlockTest> getBlockTestByBlockId(int blockId,Long ClientId){
        return blockTestDao.getBlockTestByBlockId(blockId,ClientId);
    }

    public List<BlockTest> getBlockTestByBlockIdAndClientId(int blockId,Long ClientId){
        return blockTestDao.getBlockTestByBlockIdAndClientId(blockId, ClientId);
    }
}
