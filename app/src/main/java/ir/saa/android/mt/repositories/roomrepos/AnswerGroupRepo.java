package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.AnswerGroupDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.AnswerGroup;

public class AnswerGroupRepo {

    private AnswerGroupDao answerGroupDao;
    private LiveData<List<AnswerGroup>> answerGroups;

    AnswerGroupRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        answerGroupDao=db.answerGroupModel();
        answerGroups=answerGroupDao.getAllAnswerGroups();

    }

    public LiveData<List<AnswerGroup>> getAnswerGroups(){
        return answerGroups;
    }
    public LiveData<AnswerGroup> getAnswerGroup(int Id){
        return answerGroupDao.getAnswerGroupById(Id);
    }

    public List<Integer> insertAnswerGroups(List<AnswerGroup> answerGroups){
        return answerGroupDao.insertAnswerGroups(answerGroups);
    }

    public void insertAnswerGroup(AnswerGroup answerGroup){
        answerGroupDao.insertAnswerGroup(answerGroup);
    }

    public void updateAnswerGroup(AnswerGroup answerGroup){
        answerGroupDao.updateAnswerGroup(answerGroup);
    }

    public void deleteAllAnswerGroup(){
        answerGroupDao.deleteAll();
    }

    public void deleteAnswerGroupById(int Id){
        answerGroupDao.deleteById(Id);
    }

}
