package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ir.saa.android.mt.model.entities.Menu;

@Dao
public interface MenuDao {

    @Query("Select * from Menu order by Menu.OrderId")
    LiveData<List<Menu>> getMenus();

    @Insert
    Long insertMenu(Menu menu);


    @Delete
    void deleteMenu(Menu menu);

    @Query("Select * From Menu where MenuId=:menuId")
    Menu getMenu(int menuId);
}
