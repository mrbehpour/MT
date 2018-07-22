package ir.saa.android.mt.repositories.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class AnswerGroup {

    @PrimaryKey
    @NonNull
    public int AnswerGroupID ;


    public String AnswerGroupName ;


    public String Description ;


    public Boolean IsEditable ;


    public Boolean EditDtl ;

}
