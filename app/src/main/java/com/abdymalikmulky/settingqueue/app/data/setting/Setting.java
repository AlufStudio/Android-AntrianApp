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
    long pondId;

    @Column
    @SerializedName("created_at")
    @Expose
    String createdAt;

    @Column
    @SerializedName("client_id")
    @Expose
    String clientId;

    @Column
    String pondClientId;

    @Column
    private String syncState;

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

    public long getPondId() {
        return pondId;
    }

    public void setPondId(long pondId) {
        this.pondId = pondId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSyncState() {
        return syncState;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPondClientId() {
        return pondClientId;
    }

    public void setPondClientId(String pondClientId) {
        this.pondClientId = pondClientId;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "id=" + id +
                ", feedWeight=" + feedWeight +
                ", fishWeight=" + fishWeight +
                ", freq=" + freq +
                ", pondId=" + pondId +
                ", createdAt='" + createdAt + '\'' +
                ", clientId='" + clientId + '\'' +
                ", pondClientId='" + pondClientId + '\'' +
                ", syncState='" + syncState + '\'' +
                '}';
    }
}
