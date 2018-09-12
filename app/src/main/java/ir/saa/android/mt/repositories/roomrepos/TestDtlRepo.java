package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.TestDtlDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.TestAllInfo;
import ir.saa.android.mt.model.entities.TestDtl;

public class TestDtlRepo implements TestDtlDao {

    private TestDtlDao testDtlDao;
    private LiveData<List<TestDtl>> testDtls;

    public TestDtlRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        testDtlDao=db.testDtlModel();
        testDtls=testDtlDao.getTestDtls();
    }

    public LiveData<List<TestDtl>> getTestDtls() {
        return testDtls;
    }


    public LiveData<TestDtl> getTestDtlById(Integer Id) {
        return testDtlDao.getTestDtlById(Id);
    }


    public LiveData<List<TestDtl>> getTestDtlByTestInfoId(Integer Id) {
        return testDtlDao.getTestDtlByTestInfoId(Id);
    }


    public void deleteAll() {
        testDtlDao.deleteAll();
    }


    public void deleteTestDtlById(Integer Id) {
        testDtlDao.deleteTestDtlById(Id);
    }


    public void deleteTestDtl(TestDtl testDtl) {
        testDtlDao.deleteTestDtl(testDtl);
    }


    public List<Long> insertTestDtls(List<TestDtl> testDtls) {
        return testDtlDao.insertTestDtls(testDtls);
    }


    public void insertTestDtl(TestDtl testDtl) {
        testDtlDao.insertTestDtl(testDtl);
    }


    public void updateTestDtl(TestDtl testDtl) {
        testDtlDao.updateTestDtl(testDtl);

    }

    public List<TestAllInfo> getTestAllInfoWithSendId(Long ClientId,Integer SendId){
        return testDtlDao.getTestAllInfoWithSendId(ClientId,SendId);
    }
}
