package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.TestInfoDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.TestInfo;

public class TestInfoRepo  {

    private TestInfoDao testInfoDao;
    private LiveData<List<TestInfo>> testInfos;

    public TestInfoRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        testInfoDao=db.testInfoModel();
        testInfos=testInfoDao.getTestInfos();
    }

    public LiveData<List<TestInfo>> getTestInfos() {
        return testInfos;
    }


    public LiveData<TestInfo> getTestInfoById(Integer Id) {
        return testInfoDao.getTestInfoById(Id);
    }


    public void deleteAll() {
        testInfoDao.deleteAll();
    }


    public void deleteTestInfoById(Integer Id) {
        testInfoDao.deleteTestInfoById(Id);
    }


    public void deleteTestInfo(TestInfo testInfo) {
        testInfoDao.deleteTestInfo(testInfo);
    }


    public List<Long> insertTestInfos(List<TestInfo> testInfos) {
        return testInfoDao.insertTestInfos(testInfos);
    }


    public void insertTestInfo(TestInfo testInfo) {
        testInfoDao.insertTestInfo(testInfo);
    }


    public void updateTestInfo(TestInfo testInfo) {
        testInfoDao.updateTestInfo(testInfo);
    }
}
