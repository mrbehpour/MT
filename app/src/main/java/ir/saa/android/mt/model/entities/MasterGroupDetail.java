package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;



@Entity(foreignKeys ={@ForeignKey(entity = MasterGroupInfo.class,parentColumns = "MasterGroupID",childColumns = "mastergroupinf_id")})
public class MasterGroupDetail {

    @PrimaryKey
    public Integer MasterGroupDtlID ;

    public Integer ChoiceID ;

    public String ChoiceValue ;

   // public RealmList<ir.saa.android.mt.database.contracts.Client> Client ;

   // public InspectionCourse InspectionCourse ;

   // public Integer InspectionCourseID ;



    @ColumnInfo(name = "mastergroupinf_id")
    public Integer MasterGroupInfID ;

    //private MasterGroupInfo MasterGroupInfo ;

    //private RealmList<RelMasterGroupPolomp> RelMasterGroupPolomp ;


}
