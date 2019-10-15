package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class BlockTest {

    @PrimaryKey(autoGenerate = true)
    public int BlockTestId;

    public int BlockId;

    public Long ClientId;
}
