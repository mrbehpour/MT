package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.MenuDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.Menu;

public class MenuRepo  {
    MenuDao menuDao;
    LiveData<List<Menu>> listLiveData;

    public MenuRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        menuDao=db.menuModel();
        listLiveData=menuDao.getMenus();
    }

    public LiveData<List<Menu>> getMenus() {
        return listLiveData;
    }


    public int insertMenu(Menu menu) {
        return menuDao.insertMenu(menu);
    }


    public void deleteMenu(Menu menu) {
         menuDao.deleteMenu(menu);
    }


    public Menu getMenu(int menuId) {
        return menuDao.getMenu(menuId);
    }
}
