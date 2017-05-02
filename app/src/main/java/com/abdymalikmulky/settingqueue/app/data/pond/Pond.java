
package com.abdymalikmulky.settingqueue.app.data.pond;

import com.abdymalikmulky.settingqueue.app.data.DatabaseConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = DatabaseConfig.class)
public class Pond extends BaseModel{

    @Column
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @Column
    @SerializedName("name")
    @Expose
    private String name;

    @Column
    @SerializedName("user_id")
    @Expose
    private int userId;

    @Column
    @SerializedName("client_id")
    @Expose
    private String clientId;

    @Column
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @Column
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Pond{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", clientId='" + clientId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
