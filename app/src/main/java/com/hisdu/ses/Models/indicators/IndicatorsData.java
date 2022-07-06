package com.hisdu.ses.Models.indicators;



import java.util.List;

import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndicatorsData {

@SerializedName("AppModuleTypeId")
@Expose
public Integer appModuleTypeId;
@SerializedName("AppModuleId")
@Expose
public Integer appModuleId;
@SerializedName("Name")
@Expose
public String name;
@SerializedName("FieldType")
@Expose
public String FieldType;
@SerializedName("SequenceNo")
@Expose
public Integer sequenceNo;
@SerializedName("IsActive")
@Expose
public Boolean isActive;
@SerializedName("Indicators")
@Expose
private List<Indicator> indicators = null;

    public String getFieldType() {
        return FieldType;
    }

    public void setFieldType(String fieldType) {
        FieldType = fieldType;
    }

    public Integer getAppModuleTypeId() {
return appModuleTypeId;
}

public void setAppModuleTypeId(Integer appModuleTypeId) {
this.appModuleTypeId = appModuleTypeId;
}

public Integer getAppModuleId() {
return appModuleId;
}

public void setAppModuleId(Integer appModuleId) {
this.appModuleId = appModuleId;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public Integer getSequenceNo() {
return sequenceNo;
}

public void setSequenceNo(Integer sequenceNo) {
this.sequenceNo = sequenceNo;
}

public Boolean getIsActive() {
return isActive;
}

public void setIsActive(Boolean isActive) {
this.isActive = isActive;
}

public List<Indicator> getIndicators() {
return indicators;
}

public void setIndicators(List<Indicator> indicators) {
this.indicators = indicators;
}

}


