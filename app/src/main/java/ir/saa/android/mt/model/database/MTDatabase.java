package ir.saa.android.mt.model.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ir.saa.android.mt.model.daos.AccessAgentAndroidDao;
import ir.saa.android.mt.model.daos.AgentAccessListDao;
import ir.saa.android.mt.model.daos.AnswerGroupDao;
import ir.saa.android.mt.model.daos.AnswerGroupDtlDao;
import ir.saa.android.mt.model.daos.CityDao;
import ir.saa.android.mt.model.daos.RelUserDao;
import ir.saa.android.mt.model.entities.AccessAgentAndroid;
import ir.saa.android.mt.model.entities.AgentAccessList;
import ir.saa.android.mt.model.entities.AnswerGroup;
import ir.saa.android.mt.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.model.entities.City;
import ir.saa.android.mt.model.entities.RelUser;

@Database(entities ={RelUser.class, AccessAgentAndroid.class,
                     AgentAccessList.class, AnswerGroup.class,
                     AnswerGroupDtl.class, City.class},version = 1)
public abstract class MTDatabase extends RoomDatabase {

    private static MTDatabase INSTANCE;

    public abstract RelUserDao userModel();

    public abstract AccessAgentAndroidDao accessAgentAndroidModel();

    public abstract AgentAccessListDao agentAccessListModel();

    public abstract AnswerGroupDao answerGroupModel();

    public abstract AnswerGroupDtlDao answerGroupDtlDao();

    public abstract CityDao cityModel();

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
