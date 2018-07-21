package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys =
        {
                @ForeignKey(entity = RelUser.class,parentColumns ="UserID",childColumns = "user_id"),
                @ForeignKey(entity = AgentAccessList.class,parentColumns = "AccessID",childColumns = "agentaccesslist_id")
        })
public class AccessAgentAndroid  {

    @PrimaryKey
    @NonNull
    public int ID ;

    @ColumnInfo(name="agentaccesslist_id")
    public int AgentAccessListId ;


    public int StartDate ;


    public int EndDate ;

    @ColumnInfo(name="user_id")
    public int UserId ;


}
