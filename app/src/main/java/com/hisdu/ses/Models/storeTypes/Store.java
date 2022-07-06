package com.hisdu.ses.Models.storeTypes;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hisdu.ses.Models.indicators.Indicator;

import java.util.ArrayList;
import java.util.List;


@Table(name = "Store")
public class Store extends Model {

    @Column(name = "Server_id")
    @SerializedName("StoreTypeId")
    @Expose
    private Integer storeTypeId;

    @Column(name = "Name")
    @SerializedName("Name")
    @Expose
    private String name;

    @Column(name = "IsActive")
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;

    public Integer getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public static List<Store> getStores(String storeTypeId)

    {
        List<Store> stores = new ArrayList<>();

        if (storeTypeId.equals("1"))

        {
            stores.addAll(new Select()
                    .from(Store.class)
                    .execute());
        }

        else if (storeTypeId.equals("2"))

        {
            stores.addAll(new Select()
                    .from(Store.class)
                    .where("Server_id in (2,3,4)")
                    .execute());
        }

        else if (storeTypeId.equals("3"))

        {
            stores.addAll(new Select()
                    .from(Store.class)
                    .where("Server_id in (3,4)")
                    .execute());
        }

        else if (storeTypeId.equals("4"))

        {
            stores.addAll(new Select()
                    .from(Store.class)
                    .where("Server_id in (4)")
                    .execute());
        }

        return stores;
    }

}
