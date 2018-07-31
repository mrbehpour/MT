package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.AnswerGroup;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface AnswerGroupDao {

    @Query("select * from AnswerGroup")
    LiveData<List<AnswerGroup>> getALLAnswerGroups();

    @Insert(onConflict = IGNORE)
    void insertAnswerGroup(AnswerGroup answerGroup);

    @Insert(onConflict = IGNORE)
    List<Long> insertAnswerGroups(List<AnswerGroup> answerGroup);


    @Update
    void updateAnswerGroup(AnswerGroup answerGroup);

    @Delete
    void deleteAnswerGroup(AnswerGroup answerGroup);

    @Query("select * from AnswerGroup where AnswerGroupID= :Id")
    LiveData<AnswerGroup> getAnswerGroupById(int Id);

    @Query("Delete from AnswerGroup")
    void deleteAll();

    @Query("Delete from AnswerGroup where AnswerGroupID= :Id")
    void deleteById(int Id);
}
