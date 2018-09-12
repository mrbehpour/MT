package ir.saa.android.mt.model.database;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.database.Cursor;


import ir.saa.android.mt.model.converters.AnswerGroupDtlConverters;
import ir.saa.android.mt.model.daos.AccessAgentAndroidDao;
import ir.saa.android.mt.model.daos.AgentAccessListDao;
import ir.saa.android.mt.model.daos.AnswerGroupDao;
import ir.saa.android.mt.model.daos.AnswerGroupDtlDao;
import ir.saa.android.mt.model.daos.CityDao;
import ir.saa.android.mt.model.daos.ClientDao;
import ir.saa.android.mt.model.daos.ClientTypeDao;
import ir.saa.android.mt.model.daos.CompanyDao;
import ir.saa.android.mt.model.daos.GPSInfoDao;
import ir.saa.android.mt.model.daos.GroupingFormatDao;
import ir.saa.android.mt.model.daos.InspectionDtlDao;
import ir.saa.android.mt.model.daos.InspectionInfoDao;
import ir.saa.android.mt.model.daos.MasterGroupDetailDao;
import ir.saa.android.mt.model.daos.MasterGroupInfoDao;
import ir.saa.android.mt.model.daos.MeterChangeDtlDao;
import ir.saa.android.mt.model.daos.MeterChangeInfoDao;
import ir.saa.android.mt.model.daos.PolompColorDao;
import ir.saa.android.mt.model.daos.PolompDao;
import ir.saa.android.mt.model.daos.PolompDtlDao;
import ir.saa.android.mt.model.daos.PolompGroupDao;
import ir.saa.android.mt.model.daos.PolompGroupingFormatDao;
import ir.saa.android.mt.model.daos.PolompInfoDao;
import ir.saa.android.mt.model.daos.PolompTypeDao;
import ir.saa.android.mt.model.daos.PropertyTypeDao;
import ir.saa.android.mt.model.daos.RegionDao;
import ir.saa.android.mt.model.daos.RelUserDao;
import ir.saa.android.mt.model.daos.RemarkDao;
import ir.saa.android.mt.model.daos.RemarkGroupDao;
import ir.saa.android.mt.model.daos.RemarkTypeDao;
import ir.saa.android.mt.model.daos.SettingDao;
import ir.saa.android.mt.model.daos.TariffDtlDao;
import ir.saa.android.mt.model.daos.TariffInfoDao;
import ir.saa.android.mt.model.daos.TariffTypeDao;
import ir.saa.android.mt.model.daos.TestDtlDao;
import ir.saa.android.mt.model.daos.TestInfoDao;
import ir.saa.android.mt.model.entities.AccessAgentAndroid;
import ir.saa.android.mt.model.entities.AgentAccessList;
import ir.saa.android.mt.model.entities.AnswerGroup;
import ir.saa.android.mt.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.model.entities.City;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.model.entities.ClientType;
import ir.saa.android.mt.model.entities.Company;
import ir.saa.android.mt.model.entities.GPSInfo;
import ir.saa.android.mt.model.entities.GroupingFormat;
import ir.saa.android.mt.model.entities.InspectionDtl;
import ir.saa.android.mt.model.entities.InspectionInfo;
import ir.saa.android.mt.model.entities.MasterGroupDetail;
import ir.saa.android.mt.model.entities.MasterGroupInfo;
import ir.saa.android.mt.model.entities.MeterChangeDtl;
import ir.saa.android.mt.model.entities.MeterChangeInfo;
import ir.saa.android.mt.model.entities.Polomp;
import ir.saa.android.mt.model.entities.PolompColor;
import ir.saa.android.mt.model.entities.PolompDtl;
import ir.saa.android.mt.model.entities.PolompGroup;
import ir.saa.android.mt.model.entities.PolompGroupingFormat;
import ir.saa.android.mt.model.entities.PolompInfo;
import ir.saa.android.mt.model.entities.PolompType;
import ir.saa.android.mt.model.entities.PropertyType;
import ir.saa.android.mt.model.entities.Region;
import ir.saa.android.mt.model.entities.RelUser;
import ir.saa.android.mt.model.entities.Remark;
import ir.saa.android.mt.model.entities.RemarkGroup;
import ir.saa.android.mt.model.entities.RemarkType;
import ir.saa.android.mt.model.entities.Setting;
import ir.saa.android.mt.model.entities.TariffDtl;
import ir.saa.android.mt.model.entities.TariffInfo;
import ir.saa.android.mt.model.entities.TariffType;
import ir.saa.android.mt.model.entities.TestDtl;
import ir.saa.android.mt.model.entities.TestInfo;


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
        PolompGroupingFormat.class,Setting.class,
        InspectionInfo.class,InspectionDtl.class,
        MeterChangeInfo.class,MeterChangeDtl.class,
        PolompInfo.class,GPSInfo.class,
        PolompDtl.class,TariffInfo.class,
        TariffDtl.class,TestInfo.class,TestDtl.class,
        PolompColor.class, PolompType.class},version = 13)
@TypeConverters({AnswerGroupDtlConverters.class})
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

    public abstract SettingDao settingModel();

    public abstract InspectionInfoDao inspectionInfoModel();

    public abstract InspectionDtlDao inspectionDtlModel();

    public abstract MeterChangeInfoDao meterChangeInfoModel();

    public abstract MeterChangeDtlDao meterChangeDtlModel();

    public abstract PolompInfoDao polompInfoModel();

    public abstract PolompDtlDao polompDtlModel();

    public abstract TariffInfoDao tariffInfoModel();

    public abstract TariffDtlDao tariffDtlModel();

    public abstract GPSInfoDao gpsInfoModel();

    public abstract TestInfoDao testInfoModel();

    public abstract TestDtlDao testDtlModel();

    public abstract PolompTypeDao polompTypeModel();

    public abstract PolompColorDao polompColorModel();

    public static MTDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (MTDatabase.class) {
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(), MTDatabase.class, "mt_db")
                                .allowMainThreadQueries()
                                .fallbackToDestructiveMigration()
                                .build();
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
