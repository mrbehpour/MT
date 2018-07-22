package ir.saa.android.mt.repositories.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ir.saa.android.mt.repositories.model.daos.AccessAgentAndroidDao;
import ir.saa.android.mt.repositories.model.daos.AgentAccessListDao;
import ir.saa.android.mt.repositories.model.daos.AnswerGroupDao;
import ir.saa.android.mt.repositories.model.daos.AnswerGroupDtlDao;
import ir.saa.android.mt.repositories.model.daos.CityDao;
import ir.saa.android.mt.repositories.model.daos.ClientDao;
import ir.saa.android.mt.repositories.model.daos.ClientTypeDao;
import ir.saa.android.mt.repositories.model.daos.CompanyDao;
import ir.saa.android.mt.repositories.model.daos.GroupingFormatDao;
import ir.saa.android.mt.repositories.model.daos.MasterGroupDetailDao;
import ir.saa.android.mt.repositories.model.daos.MasterGroupInfoDao;
import ir.saa.android.mt.repositories.model.daos.PolompDao;
import ir.saa.android.mt.repositories.model.daos.PolompGroupDao;
import ir.saa.android.mt.repositories.model.daos.PolompGroupingFormatDao;
import ir.saa.android.mt.repositories.model.daos.PropertyTypeDao;
import ir.saa.android.mt.repositories.model.daos.RegionDao;
import ir.saa.android.mt.repositories.model.daos.RelUserDao;
import ir.saa.android.mt.repositories.model.daos.RemarkDao;
import ir.saa.android.mt.repositories.model.daos.RemarkGroupDao;
import ir.saa.android.mt.repositories.model.daos.RemarkTypeDao;
import ir.saa.android.mt.repositories.model.daos.TariffTypeDao;
import ir.saa.android.mt.repositories.model.entities.AccessAgentAndroid;
import ir.saa.android.mt.repositories.model.entities.AgentAccessList;
import ir.saa.android.mt.repositories.model.entities.AnswerGroup;
import ir.saa.android.mt.repositories.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.repositories.model.entities.City;
import ir.saa.android.mt.repositories.model.entities.Client;
import ir.saa.android.mt.repositories.model.entities.ClientType;
import ir.saa.android.mt.repositories.model.entities.Company;
import ir.saa.android.mt.repositories.model.entities.GroupingFormat;
import ir.saa.android.mt.repositories.model.entities.MasterGroupDetail;
import ir.saa.android.mt.repositories.model.entities.MasterGroupInfo;
import ir.saa.android.mt.repositories.model.entities.Polomp;
import ir.saa.android.mt.repositories.model.entities.PolompGroup;
import ir.saa.android.mt.repositories.model.entities.PolompGroupingFormat;
import ir.saa.android.mt.repositories.model.entities.PropertyType;
import ir.saa.android.mt.repositories.model.entities.Region;
import ir.saa.android.mt.repositories.model.entities.RelUser;
import ir.saa.android.mt.repositories.model.entities.Remark;
import ir.saa.android.mt.repositories.model.entities.RemarkGroup;
import ir.saa.android.mt.repositories.model.entities.RemarkType;
import ir.saa.android.mt.repositories.model.entities.TariffType;

@Database(entities ={RelUser.class, AccessAgentAndroid.class,
                     AgentAccessList.class, AnswerGroup.class,
                     AnswerGroupDtl.class, City.class,
                     Client.class, ClientType.class,
                     Company.class, Region.class,
                     TariffType.class,MasterGroupDetail.class,
                     MasterGroupInfo.class, GroupingFormat.class,
                     Remark.class,PropertyType.class,
                     RemarkType.class, RemarkGroup.class,
                     Polomp.class, PolompGroup.class,
                     PolompGroupingFormat.class},version = 1)
public abstract class MTDatabase extends RoomDatabase {

    private static MTDatabase INSTANCE;

    public abstract RelUserDao userModel();

    public abstract AccessAgentAndroidDao accessAgentAndroidModel();

    public abstract AgentAccessListDao agentAccessListModel();

    public abstract AnswerGroupDao answerGroupModel();

    public abstract AnswerGroupDtlDao answerGroupDtlModel();

    public abstract CityDao cityModel();

    public abstract ClientDao clientModel();

    public abstract ClientTypeDao clientTypeModel ();

    public abstract CompanyDao companyModel();

    public abstract RegionDao regionModel();

    public abstract TariffTypeDao tariffTypeModel();

    public abstract MasterGroupDetailDao masterGroupDetailModel();

    public abstract MasterGroupInfoDao masterGroupInfoModel();

    public abstract GroupingFormatDao groupingFormatModel();

    public abstract RemarkDao remarkModel();

    public abstract PropertyTypeDao propertyTypeModel();

    public abstract RemarkTypeDao remarkTypeModel();

    public abstract RemarkGroupDao remarkGroupModel();

    public abstract PolompDao polompModel();

    public abstract PolompGroupDao polompGroupModel();

    public abstract PolompGroupingFormatDao polompGroupingFormatModel();

    public static MTDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), MTDatabase.class)
                            // To simplify the codelab, allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
