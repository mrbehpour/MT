package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.CompanyDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.Company;

public class CompanyRepo {

    private CompanyDao companyDao;
    private LiveData<List<Company>> Companies;

    CompanyRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        companyDao=db.companyModel();
        Companies=companyDao.getCompanies();

    }

    public LiveData<List<Company>> getCompanies() {
        return Companies;
    }


    public void updateCompany(Company company) {
        companyDao.updateCompany(company);
    }


    public void insertCompany(Company company) {
        companyDao.insertCompany(company);
    }


    public void deleteCompany(Company company) {
        companyDao.deleteCompany(company);
    }


    public LiveData<Company> getCompanyById(Integer Id) {
        return companyDao.getCompanyById(Id);
    }


    public void deleteCompanyById(Integer Id) {
        companyDao.deleteCompanyById(Id);
    }


    public void deleteAll() {
        companyDao.deleteAll();
    }

    public List<Long> insertCompanies(List<Company> companies){
        return companyDao.insertCompanies(companies);
    }
}
