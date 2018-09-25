package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.AnswerGroupDtlDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.AnswerGroupDtl;

public class AnswerGroupDtlRepo   {

    private AnswerGroupDtlDao answerGroupDtlDao;
    private LiveData<List<AnswerGroupDtl>> AnswerGroupDtls;

    public AnswerGroupDtlRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);
        answerGroupDtlDao=db.answerGroupDtlModel();
        AnswerGroupDtls=answerGroupDtlDao.getAnswerGroupDtls();
    }

    public LiveData<List<AnswerGroupDtl>> getAnswerGroupDtls() {
        return AnswerGroupDtls;
    }


    public void insertAnswerGroupdtl(AnswerGroupDtl answerGroupDtl) {
        answerGroupDtlDao.insertAnswerGroupdtl(answerGroupDtl);
    }


    public List<Long> insertAnswerGroupdtls(List<AnswerGroupDtl> answerGroupDtls) {
        return answerGroupDtlDao.insertAnswerGroupdtls(answerGroupDtls);
    }


    public void updateAnswerGroupDtl(AnswerGroupDtl answerGroupDtl) {
        answerGroupDtlDao.updateAnswerGroupDtl(answerGroupDtl);
    }


    public void deleteAnswerGroupDtl(AnswerGroupDtl answerGroupDtl) {
        answerGroupDtlDao.deleteAnswerGroupDtl(answerGroupDtl);
    }


    public void allDelete() {
        answerGroupDtlDao.allDelete();
    }


    public LiveData<AnswerGroupDtl> getAnswerGroupDtlById(int Id) {
        return answerGroupDtlDao.getAnswerGroupDtlById(Id);
    }


    public void deleteAnswerGroupDtlById(int Id) {
        answerGroupDtlDao.deleteAnswerGroupDtlById(Id);
    }


    public LiveData<List<AnswerGroupDtl>> getAnswerGroupDtlByAnswerGroupId(int answergroupId) {
        return answerGroupDtlDao.getAnswerGroupDtlByAnswerGroupId(answergroupId);
    }
}
