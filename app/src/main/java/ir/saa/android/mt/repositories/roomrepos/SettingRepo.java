package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.SettingDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.Setting;

public class SettingRepo  {

    private SettingDao settingDao;
    private LiveData<List<Setting>> Settings;

    public SettingRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);
        settingDao=db.settingModel();
        Settings=settingDao.getSettings();
    }

    public LiveData<List<Setting>> getSettings() {
        return Settings;
    }


    public LiveData<Setting> getSettingById(Integer Id) {
        return settingDao.getSettingById(Id);
    }


    public void insertSetting(Setting setting) {
        settingDao.insertSetting(setting);
    }


    public List<Long> insertSettings(List<Setting> setting) {
        return settingDao.insertSettings(setting);
    }


    public void updateSetting(Setting setting) {
        settingDao.updateSetting(setting);
    }


    public void deleteSetting(Setting setting) {
        settingDao.deleteSetting(setting);
    }


    public void deleteAll() {
        settingDao.deleteAll();
    }


    public void deleteSettingById(Integer Id) {
        settingDao.deleteSettingById(Id);
    }
}
