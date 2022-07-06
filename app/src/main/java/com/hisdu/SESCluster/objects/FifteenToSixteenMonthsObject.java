package com.hisdu.SESCluster.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usman Kurd on 7/17/2018.
 */

public class FifteenToSixteenMonthsObject implements Parcelable {
    private String measlesTwoVaccinated;
    private String measles2DateOfVaccination;
    private String remarks;

    public FifteenToSixteenMonthsObject() {
    }

    protected FifteenToSixteenMonthsObject(Parcel in) {
        measlesTwoVaccinated = in.readString();
        measles2DateOfVaccination = in.readString();
        remarks = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(measlesTwoVaccinated);
        dest.writeString(measles2DateOfVaccination);
        dest.writeString(remarks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FifteenToSixteenMonthsObject> CREATOR = new Creator<FifteenToSixteenMonthsObject>() {
        @Override
        public FifteenToSixteenMonthsObject createFromParcel(Parcel in) {
            return new FifteenToSixteenMonthsObject(in);
        }

        @Override
        public FifteenToSixteenMonthsObject[] newArray(int size) {
            return new FifteenToSixteenMonthsObject[size];
        }
    };

    public String getMeaslesTwoVaccinated() {
        return measlesTwoVaccinated;
    }

    public void setMeaslesTwoVaccinated(String measlesTwoVaccinated) {
        this.measlesTwoVaccinated = measlesTwoVaccinated;
    }

    public String getMeasles2DateOfVaccination() {
        return measles2DateOfVaccination;
    }

    public void setMeasles2DateOfVaccination(String measles2DateOfVaccination) {
        this.measles2DateOfVaccination = measles2DateOfVaccination;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
