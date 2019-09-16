package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.model.entities.ClientWithAction;
import ir.saa.android.mt.model.entities.ClientWithTarif;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ClientDao {
    @Query("select * from Client")
    LiveData<List<Client>> getClientsLiveData();

    @Query("select * from Client")
    List<Client> getClients();

    @Query("Select " +
            "Client.ClientID," +
            "Client.tarifftype_id," +
            "Client.Active1," +
            "Client.Active2," +
            "Client.Active3," +
            "Client.ActiveT1," +
            "Client.ActiveTariffCount," +
            "Client.Address," +
            "Client.Amp," +
            "Client.ChngDateContor," +
            "Client.ClientCity," +
            "Client.ClientPass," +
            "Client.ClientRow," +
            "Client.clienttype_id," +
            "Client.CustId," +
            "Client.Demand," +
            "Client.DurationType," +
            "Client.Faz," +
            "Client.FileID," +
            "Client.IDInst," +
            "Client.Status," +
            "Client.SubScript," +
            "Client.clienttype_id," +
            "Client.ClientRow," +
            "Client.ClientPass," +
            "Client.Name," +
            "Client.NumContract," +
            "Client.NumDigitContor," +
            "Client.InsDateContor," +
            "Client.KindVolt," +
            "Client.Tel," +
            "Client.PostalCode," +
            "Client.Name," +
            "Client.UseAvrR," +
            "Client.Mamoor," +
            "Client.LastReadDate," +
            "Client.MeterNumActive," +
            "Client.MxmeterCode," +
            "Client.MxMeterZarib," +
            "Client.MxValue," +
            "Client.Pelak," +
            "Client.PosType," +
            "Client.RegionID," +
            "Client.Zarib , "+
            "Client.SendId, "+
            "Client.UseAvrA, "+
            "Client.RoozKar," +
            "Client.mastergroupdtl_id," +
            "Client.RoozKar, " +
            "Client.FollowUpCode,"+
            "Client.forcibleMasterGroup ,"+
            "(select PolompInfoID from PolompInfo where PolompInfo.ClientID=Client.ClientID limit 1 ) as isPolomp,  "+
            "(select SendID from TestInfo where TestInfo.ClientID=Client.ClientID limit 1) as isTest, "+
            "(select SendID from InspectionInfo where InspectionInfo.ClientID=Client.ClientID limit 1) as isBazrasi, "+
            "(select SendID from TariffInfo where TariffInfo.ClientID=Client.ClientID limit 1) as isTariff "+
            "from Client "
    )
    LiveData<List<ClientWithAction>> getClientsWithActionLiveData();

    @Query("Select " +
            "Client.ClientID," +
            "Client.tarifftype_id," +
            "Client.Active1," +
            "Client.Active2," +
            "Client.Active3," +
            "Client.ActiveT1," +
            "Client.ActiveTariffCount," +
            "Client.Address," +
            "Client.Amp," +
            "Client.ChngDateContor," +
            "Client.ClientCity," +
            "Client.ClientPass," +
            "Client.ClientRow," +
            "Client.clienttype_id," +
            "Client.CustId," +
            "Client.Demand," +
            "Client.DurationType," +
            "Client.Faz," +
            "Client.FileID," +
            "Client.IDInst," +
            "Client.Status," +
            "Client.SubScript," +
            "Client.clienttype_id," +
            "Client.ClientRow," +
            "Client.ClientPass," +
            "Client.Name," +
            "Client.NumContract," +
            "Client.NumDigitContor," +
            "Client.InsDateContor," +
            "Client.KindVolt," +
            "Client.Tel," +
            "Client.PostalCode," +
            "Client.Name," +
            "Client.UseAvrR," +
            "Client.Mamoor," +
            "Client.LastReadDate," +
            "Client.MeterNumActive," +
            "Client.MxmeterCode," +
            "Client.MxMeterZarib," +
            "Client.MxValue," +
            "Client.Pelak," +
            "Client.PosType," +
            "Client.RegionID," +
            "Client.Zarib , "+
            "Client.SendId, "+
            "Client.UseAvrA, "+
            "Client.RoozKar," +
            "Client.mastergroupdtl_id," +
            "Client.RoozKar, " +
            "Client.FollowUpCode,"+
            "Client.forcibleMasterGroup ,"+
            "(select PolompInfoID from PolompInfo where PolompInfo.ClientID=Client.ClientID limit 1 ) as isPolomp,  "+
            "(select SendID from TestInfo where TestInfo.ClientID=Client.ClientID limit 1) as isTest, "+
            "(select SendID from InspectionInfo where InspectionInfo.ClientID=Client.ClientID limit 1) as isBazrasi, "+
            "(select SendID from TariffInfo where TariffInfo.ClientID=Client.ClientID limit 1) as isTariff "+
            "from Client " +
            "where RegionID=:regionId"
    )
    LiveData<List<ClientWithAction>> getClientsWithActionWithRegionIdLiveData(Integer regionId);

    @Query("select * from Client where RegionID=:regionId")
    List<Client> getClientsWithRegionId(Integer regionId);

    @Query("select * from Client where RegionID=:regionId")
    LiveData<List<Client>> getClientsWithRegionIdLiveData(Integer regionId);
    @Query("Select " +
            "Client.ClientID," +
            "Client.tarifftype_id," +
            "Client.Active1," +
            "Client.Active2," +
            "Client.Active3," +
            "Client.ActiveT1," +
            "Client.ActiveTariffCount," +
            "Client.Address," +
            "Client.Amp," +
            "Client.ChngDateContor," +
            "Client.ClientCity," +
            "Client.ClientPass," +
            "Client.ClientRow," +
            "Client.clienttype_id," +
            "Client.CustId," +
            "Client.Demand," +
            "Client.DurationType," +
            "Client.Faz," +
            "Client.FileID," +
            "Client.IDInst," +
            "Client.Status," +
            "Client.SubScript," +
            "Client.clienttype_id," +
            "Client.ClientRow," +
            "Client.ClientPass," +
            "Client.Name," +
            "Client.NumContract," +
            "Client.NumDigitContor," +
            "Client.InsDateContor," +
            "Client.KindVolt," +
            "Client.Tel," +
            "Client.PostalCode," +
            "Client.Name," +
            "Client.UseAvrR," +
            "Client.Mamoor," +
            "Client.LastReadDate," +
            "Client.MeterNumActive," +
            "Client.MxmeterCode," +
            "Client.MxMeterZarib," +
            "Client.MxValue," +
            "Client.Pelak," +
            "Client.PosType," +
            "Client.RegionID," +
            "Client.RoozKar," +
            "Client.mastergroupdtl_id," +
            "Client.RoozKar," +
            "Client.forcibleMasterGroup ,"+
            "Client.FollowUpCode,"+
            "AnswerGroupDtlName as TariffTypeName " +
            "from Client " +
            "left JOIN AnswerGroupDtl on Client.tarifftype_id=AnswerGroupDtl.Description "+
            "left JOIN AnswerGroup on AnswerGroup.AnswerGroupID=AnswerGroupDtl.answergroup_id and AnswerGroup.Description='Tariff'"+

             "where ClientID= :clientId"   )
    LiveData<ClientWithTarif> getClientWithTarif(Long clientId);

    @Update
    void updateClient(Client client);

    @Insert(onConflict = IGNORE)
    void insertClient(Client client);

    @Insert(onConflict = IGNORE)
    List<Long> insertClients(List<Client> clients);

    @Delete
    void deleteClient(Client client);

    @Query("select * from Client where ClientID = :clientId")
    Client getClientById(long clientId);

    @Query("select * from Client " +
            "where (:subscript=-1 or  SubScript = :subscript) and " +
            "(:fileid=-1 or FileID=:fileid) and " +
            "(:contornum=-1 or MeterNumActive=:contornum) and " +
            "(:shenas='-1' or CustId=:shenas) and " +
            "(:clientpass=-1 or ClientPass=:clientpass) and" +
            "(:param=-1)")
    Client getClientByUniq(long subscript,Long fileid,long contornum,String shenas,long clientpass,long param);

    @Query("Delete From Client")
    void deleteAll();

    @Query("Delete from Client where ClientID= :clientId")
    void deleteById(long clientId);

}
