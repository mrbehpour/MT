package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Menu {
    @PrimaryKey
    @NonNull
    public int MenuId;

    public boolean CanView;

    public boolean IsForce;

    public String KeyName;

    public String Name;

    public int OrderId;
}
