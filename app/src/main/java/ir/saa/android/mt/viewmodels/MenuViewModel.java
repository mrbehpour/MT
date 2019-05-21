package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ir.saa.android.mt.model.entities.Menu;
import ir.saa.android.mt.repositories.roomrepos.MenuRepo;

public class MenuViewModel extends AndroidViewModel {
    MenuRepo menuRepo;
    public MenuViewModel(@NonNull Application application) {
        super(application);

        if(menuRepo==null){
            menuRepo=new MenuRepo(application);
        }
    }

    public LiveData<List<Menu>> getMenus(){
        return menuRepo.getMenus();
    }
}
