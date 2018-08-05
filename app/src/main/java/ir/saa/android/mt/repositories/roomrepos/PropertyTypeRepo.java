package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.PropertyTypeDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.PropertyType;

public class PropertyTypeRepo  {

    private PropertyTypeDao propertyTypeDao;
    private LiveData<List<PropertyType>> PropertyTypes;

    public PropertyTypeRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        propertyTypeDao=db.propertyTypeModel();
        PropertyTypes=propertyTypeDao.getPropertyTypes();
    }


    public LiveData<List<PropertyType>> getPropertyTypes() {
        return PropertyTypes;
    }


    public void deleteAll() {
        propertyTypeDao.deleteAll();
    }


    public LiveData<PropertyType> getPropertyTypeById(int Id) {
        return propertyTypeDao.getPropertyTypeById(Id);
    }


    public void deleteById(int Id) {
        propertyTypeDao.deleteById(Id);
    }


    public void deletePropertyType(PropertyType propertyType) {
        propertyTypeDao.deletePropertyType(propertyType);
    }


    public void insertPropertyType(PropertyType propertyType) {
        propertyTypeDao.insertPropertyType(propertyType);
    }


    public List<Long> insertPropertyTypes(List<PropertyType> propertyTypes) {
        return propertyTypeDao.insertPropertyTypes(propertyTypes);
    }


    public void updatePropertyType(PropertyType propertyType) {
        propertyTypeDao.updatePropertyType(propertyType);
    }
}
