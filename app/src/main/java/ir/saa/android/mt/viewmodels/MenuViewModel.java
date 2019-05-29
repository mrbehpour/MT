package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ir.saa.android.mt.model.entities.Menu;
import ir.saa.android.mt.model.entities.Setting;
import ir.saa.android.mt.repositories.roomrepos.MenuRepo;
import ir.saa.android.mt.repositories.roomrepos.SettingRepo;

public class MenuViewModel extends AndroidViewModel {
    MenuRepo menuRepo;
    SettingRepo settingRepo;
    public MenuViewModel(@NonNull Application application) {
        super(application);

        if(menuRepo==null){
            menuRepo=new MenuRepo(application);
        }
        if(settingRepo==null){
            settingRepo=new SettingRepo(application);
        }
    }

    public LiveData<List<Menu>> getMenus(){
        return menuRepo.getMenus();
    }

    public LiveData<Setting> getSettingByKey(String settingKey){
        return settingRepo.getSettingByKey(settingKey);
    }
}
