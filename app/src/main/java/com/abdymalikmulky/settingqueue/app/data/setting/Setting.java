package com.abdymalikmulky.settingqueue.app.data.setting;

import com.abdymalikmulky.settingqueue.app.data.DatabaseConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by efishery on 09/05/17.
 */

@Table(database = DatabaseConfig.class)
public class Setting extends BaseModel implements Serializable {

    @Column
    @PrimaryKey(autoincrement = false)
    @SerializedName("id")
    @Expose
    private long id;

    @Column
    @SerializedName("feedWeight")
    @Expose
    int feedWeight;

    @Column
    @SerializedName("fishWeight")
    @Expose
    int fishWeight;

    @Column
    @SerializedName("freq")
    @Expose
    int freq;

    @Column
    @SerializedName("pond_id")
    @Expose
    int pondId;

    @Column
    @SerializedName("created_at")
    @Expose
    long createdAt;


    public Setting() {
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFeedWeight() {
        return feedWeight;
    }

    public void setFeedWeight(int feedWeight) {
        this.feedWeight = feedWeight;
    }

    public int getFishWeight() {
        return fishWeight;
    }

    public void setFishWeight(int fishWeight) {
        this.fishWeight = fishWeight;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public int getPondId() {
        return pondId;
    }

    public void setPondId(int pondId) {
        this.pondId = pondId;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
