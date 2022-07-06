package com.hisdu.SESCluster.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usman Kurd on 7/17/2018.
 */

public class BoosterDoseObject implements Parcelable {
    private String boosterVaccinated;
    private String remarks;

    public BoosterDoseObject() {
    }

    protected BoosterDoseObject(Parcel in) {
        boosterVaccinated = in.readString();
        remarks = in.readString();
    }

    public String getBoosterVaccinated() {
        return boosterVaccinated;
    }

    public void setBoosterVaccinated(String boosterVaccinated) {
        this.boosterVaccinated = boosterVaccinated;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public static final Creator<BoosterDoseObject> CREATOR = new Creator<BoosterDoseObject>() {
        @Override
        public BoosterDoseObject createFromParcel(Parcel in) {
            return new BoosterDoseObject(in);
        }

        @Override
        public BoosterDoseObject[] newArray(int size) {
            return new BoosterDoseObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(boosterVaccinated);
        dest.writeString(remarks);

    }
}
