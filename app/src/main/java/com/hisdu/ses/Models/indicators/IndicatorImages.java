package com.hisdu.ses.Models.indicators;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndicatorImages {

@SerializedName("CheckListImages")
@Expose
private List<CheckListImage> checkListImages = null;

public List<CheckListImage> getCheckListImages() {
return checkListImages;
}

public void setCheckListImages(List<CheckListImage> checkListImages) {
this.checkListImages = checkListImages;
}

}