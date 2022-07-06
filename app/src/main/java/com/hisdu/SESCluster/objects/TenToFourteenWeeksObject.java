package com.hisdu.SESCluster.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usman Kurd on 7/17/2018.
 */

public class TenToFourteenWeeksObject implements Parcelable {
    private String pentaVaccinated;
    private String pentaDateOfVaccination;
    private String pcv10Vaccinated;
    private String pcv10DateOfVaccination;
    private String opvVaccinated;
    private String opvDateOfVaccination;
    private String rotaVaccinated;
    private String rotaDateOfVaccinated;
    private String remarks;

    public TenToFourteenWeeksObject() {
    }

    protected TenToFourteenWeeksObject(Parcel in) {
        pentaVaccinated = in.readString();
        pentaDateOfVaccination = in.readString();
        pcv10Vaccinated = in.readString();
        pcv10DateOfVaccination = in.readString();
        opvVaccinated = in.readString();
        opvDateOfVaccination = in.readString();
        rotaVaccinated = in.readString();
        rotaDateOfVaccinated = in.readString();
        remarks = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pentaVaccinated);
        dest.writeString(pentaDateOfVaccination);
        dest.writeString(pcv10Vaccinated);
        dest.writeString(pcv10DateOfVaccination);
        dest.writeString(opvVaccinated);
        dest.writeString(opvDateOfVaccination);
        dest.writeString(rotaVaccinated);
        dest.writeString(rotaDateOfVaccinated);
        dest.writeString(remarks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TenToFourteenWeeksObject> CREATOR = new Creator<TenToFourteenWeeksObject>() {
        @Override
        public TenToFourteenWeeksObject createFromParcel(Parcel in) {
            return new TenToFourteenWeeksObject(in);
        }

        @Override
        public TenToFourteenWeeksObject[] newArray(int size) {
            return new TenToFourteenWeeksObject[size];
        }
    };

    public String getPentaVaccinated() {
        return pentaVaccinated;
    }

    public void setPentaVaccinated(String pentaVaccinated) {
        this.pentaVaccinated = pentaVaccinated;
    }

    public String getPentaDateOfVaccination() {
        return pentaDateOfVaccination;
    }

    public void setPentaDateOfVaccination(String pentaDateOfVaccination) {
        this.pentaDateOfVaccination = pentaDateOfVaccination;
    }

    public String getPcv10Vaccinated() {
        return pcv10Vaccinated;
    }

    public void setPcv10Vaccinated(String pcv10Vaccinated) {
        this.pcv10Vaccinated = pcv10Vaccinated;
    }

    public String getPcv10DateOfVaccination() {
        return pcv10DateOfVaccination;
    }

    public void setPcv10DateOfVaccination(String pcv10DateOfVaccination) {
        this.pcv10DateOfVaccination = pcv10DateOfVaccination;
    }

    public String getOpvVaccinated() {
        return opvVaccinated;
    }

    public void setOpvVaccinated(String opvVaccinated) {
        this.opvVaccinated = opvVaccinated;
    }

    public String getOpvDateOfVaccination() {
        return opvDateOfVaccination;
    }

    public void setOpvDateOfVaccination(String opvDateOfVaccination) {
        this.opvDateOfVaccination = opvDateOfVaccination;
    }

    public String getRotaVaccinated() {
        return rotaVaccinated;
    }

    public void setRotaVaccinated(String rotaVaccinated) {
        this.rotaVaccinated = rotaVaccinated;
    }

    public String getRotaDateOfVaccinated() {
        return rotaDateOfVaccinated;
    }

    public void setRotaDateOfVaccinated(String rotaDateOfVaccinated) {
        this.rotaDateOfVaccinated = rotaDateOfVaccinated;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
