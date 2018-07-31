package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.Company;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface CompanyDao {

    @Query("select * from Company")
    LiveData<List<Company>> getCompanies();

    @Update
    void updateCompany(Company company);

    @Insert(onConflict = IGNORE)
    void insertCompany(Company company);

    @Insert(onConflict = IGNORE)
    List<Long> insertCompanies(List<Company> companies);

    @Delete
    void deleteCompany(Company company);

    @Query("select * from Company where FldID= :Id")
    LiveData<Company> getCompanyById(Integer Id);

    @Query("Delete from Company where FldID= :Id")
    void deleteCompanyById(Integer Id);

    @Query("Delete from Company")
    void deleteAll();

}
