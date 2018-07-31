package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.AnswerGroupDtl;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface AnswerGroupDtlDao {

    @Query("select * from AnswerGroupDtl")
    LiveData<List<AnswerGroupDtl>> getAnswerGroupDtls();

    @Insert(onConflict = IGNORE)
    void insertAnswerGroupdtl(AnswerGroupDtl answerGroupDtl);

    @Insert(onConflict = IGNORE)
    List<Long> insertAnswerGroupdtls(List<AnswerGroupDtl> answerGroupDtls);


    @Update
    void updateAnswerGroupDtl(AnswerGroupDtl answerGroupDtl);

    @Delete
    void deleteAnswerGroupDtl(AnswerGroupDtl answerGroupDtl);

    @Query("Delete from AnswerGroupDtl")
    void allDelete();

    @Query("select * from AnswerGroupDtl where AnswerGroupDtlID= :Id")
    LiveData<AnswerGroupDtl> getAnswerGroupDtlById(int Id);

    @Query("Delete from AnswerGroupDtl where AnswerGroupDtlID= :Id")
    void deleteAnswerGroupDtlById(int Id);

    @Query("select * from AnswerGroupDtl where answergroup_id= :answergroupId")
    LiveData<List<AnswerGroupDtl>> getAnswerGroupDtlByAnswerGroupId(int answergroupId);

}
