package com.hisdu.SESCluster.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usman Kurd on 7/17/2018.
 */

public class NineToTenMonthsObjects implements Parcelable {
    private String measlesOneVaccinated;
    private String IPV2Vaccinated;
    private String TCVVaccinated;
    private String measlesDateOfVaccination;
    private String remarks;

    public NineToTenMonthsObjects() {
    }

    protected NineToTenMonthsObjects(Parcel in) {
        measlesOneVaccinated = in.readString();
        IPV2Vaccinated = in.readString();
        TCVVaccinated = in.readString();
        measlesDateOfVaccination = in.readString();
        remarks = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(measlesOneVaccinated);
        dest.writeString(IPV2Vaccinated);
        dest.writeString(TCVVaccinated);
        dest.writeString(measlesDateOfVaccination);
        dest.writeString(remarks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NineToTenMonthsObjects> CREATOR = new Creator<NineToTenMonthsObjects>() {
        @Override
        public NineToTenMonthsObjects createFromParcel(Parcel in) {
            return new NineToTenMonthsObjects(in);
        }

        @Override
        public NineToTenMonthsObjects[] newArray(int size) {
            return new NineToTenMonthsObjects[size];
        }
    };

    public String getMeaslesOneVaccinated() {
        return measlesOneVaccinated;
    }

    public void setMeaslesOneVaccinated(String measlesOneVaccinated) {
        this.measlesOneVaccinated = measlesOneVaccinated;
    }

    public String getMeaslesDateOfVaccination() {
        return measlesDateOfVaccination;
    }

    public void setMeaslesDateOfVaccination(String measlesDateOfVaccination) {
        this.measlesDateOfVaccination = measlesDateOfVaccination;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIPV2Vaccinated() {
        return IPV2Vaccinated;
    }

    public void setIPV2Vaccinated(String IPV2Vaccinated) {
        this.IPV2Vaccinated = IPV2Vaccinated;
    }

    public String getTCVVaccinated() {
        return TCVVaccinated;
    }

    public void setTCVVaccinated(String TCVVaccinated) {
        this.TCVVaccinated = TCVVaccinated;
    }
}
