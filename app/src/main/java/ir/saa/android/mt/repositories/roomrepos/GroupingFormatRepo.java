package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.GroupingFormatDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.GroupingFormat;

public class GroupingFormatRepo  {

    private GroupingFormatDao groupingFormatDao;
    private LiveData<List<GroupingFormat>> GroupingFormats;

    GroupingFormatRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        groupingFormatDao=db.groupingFormatModel();
        GroupingFormats=groupingFormatDao.getGroupingFormats();
    }

    public LiveData<List<GroupingFormat>> getGroupingFormats() {
        return GroupingFormats;
    }


    public LiveData<GroupingFormat> getGroupingFormatById(Integer Id) {
        return groupingFormatDao.getGroupingFormatById(Id);
    }


    public LiveData<List<GroupingFormat>> getGroupingFormatByMasterGroupId(int Id) {
        return groupingFormatDao.getGroupingFormatByMasterGroupId(Id);
    }


    public LiveData<List<GroupingFormat>> getGroupingFormatByRemarkId(int Id) {
        return groupingFormatDao.getGroupingFormatByRemarkId(Id);
    }


    public LiveData<List<GroupingFormat>> getGroupingFormatByRemarkgroupId(int Id) {
        return groupingFormatDao.getGroupingFormatByRemarkgroupId(Id);
    }


    public void deleteAll() {
        groupingFormatDao.deleteAll();
    }


    public void deleteById(int Id) {
        groupingFormatDao.deleteById(Id);
    }


    public void insertGroupingFormat(GroupingFormat groupingFormat) {
        groupingFormatDao.insertGroupingFormat(groupingFormat);
    }


    public List<Integer> insertGroupingFormats(List<GroupingFormat> groupingFormats) {
        return groupingFormatDao.insertGroupingFormats(groupingFormats);
    }


    public void updateGroupingFormat(GroupingFormat groupingFormat) {
        groupingFormatDao.updateGroupingFormat(groupingFormat);
    }


    public void deleteGroupingFormat(GroupingFormat groupingFormat) {
        groupingFormatDao.deleteGroupingFormat(groupingFormat);
    }
}
