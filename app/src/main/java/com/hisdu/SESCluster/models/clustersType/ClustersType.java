package com.hisdu.SESCluster.models.clustersType;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hisdu.ses.Models.indicators.Indicator;

import java.util.List;


@Table(name = "ClustersType")
public class ClustersType extends Model {

    @Column(name = "ClusterTypeId")
    @SerializedName("ClusterTypeId")
    @Expose
    private Integer clusterTypeId;

    @Column(name = "Name")
    @SerializedName("Name")
    @Expose
    private String name;

    public Integer getClusterTypeId() {
        return clusterTypeId;
    }

    public void setClusterTypeId(Integer clusterTypeId) {
        this.clusterTypeId = clusterTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<ClustersType> getClusterTypes() {
        return new Select()
                .from(ClustersType.class)
                .execute();
    }

}


