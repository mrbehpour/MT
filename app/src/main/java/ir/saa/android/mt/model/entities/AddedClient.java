package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class AddedClient {

                        public Integer AddDate ;
                        public Integer AddTime ;
                        public Integer AddedClientID;
                        public Integer AgentID;
                        @PrimaryKey
                        public Long ClientID ;
                        public String ClientInfo ;
                        public Long ClientPass ;
                        public Integer ClientSourceID ;
                        public Integer CorrectCustID;
                        public String CustID;
                        public Long FileID;
                        public String FileVersion;
                        public Long FollowUpCode ;
                        public String HandheldModel;
                        public String HeaderInfo ;
                        public String InsertDate;
                        public Boolean IsInFile ;
                        public Integer LineNumber ;
                        public Integer MatchDate;
                        public String MatchType;
                        public Long MeterNumActive ;
                        public Long RecieveFileDtlID ;
                        public Integer RecieveID;
                        public Integer SendID;
                        public String SerialNo;
                        public Long Subscript;
                        public Long TempClientID ;
}
